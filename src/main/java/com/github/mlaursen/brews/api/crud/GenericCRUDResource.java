/**
 * 
 */
package com.github.mlaursen.brews.api.crud;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJBException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.jboss.logging.Logger;

import com.github.mlaursen.brews.api.BaseResource;
import com.github.mlaursen.brews.entity.GeneratedIdEntity;
import com.github.mlaursen.brews.util.ResponseBuilder;

/**
 * A neat little Generic CRUD Resource used for a lot of the normal CRUD requests. Each Resource that wants this logic
 * just needs to extend this class and create a public constructor with the entity they manage in it. Right now, the
 * Generic CRUD Resource can only work on entities that are a subclass of {@link GeneratedIdEntity} because
 * I am searching by <code>id</code>. It would be helpful to change it to PK and search by PK if needed.
 * 
 * <p>An example:<pre>
 *   public YeastResource() {
 *     super(Yeast.class);
 *   }
 * </pre>
 * 
 * <p>This Generic CRUD Resource also defines a few methods to be used in subclasses for common uses. Examples:
 * binding parameters, executing a named query with parameters...
 * 
 * @author mlaursen
 */
public abstract class GenericCRUDResource<E extends GeneratedIdEntity> extends BaseResource implements CreateableResource<E>, RetrievableResource, UpdateableResource<E>, DeleteableResource<E>, AllRetrievableResource {
  private static final Logger logger = Logger.getLogger(GenericCRUDResource.class);
  private static final String LOCATION_HEADER = "Location";
  private static final String LOCATION_HEADER_FORMAT = "/api/%ss/%d";
  
  private Class<E> entityClass;
  
  protected GenericCRUDResource(Class<E> entityClass) {
    this.entityClass = entityClass;
  }
  
  /**
   * Attempts to find an entity by the given id
   * @param id the id
   * @return the Entity or null
   */
  public E findById(Long id) {
    return em.find(entityClass, id);
  }
  
  /**
   * Binds all the parameters to a given query.
   * @param q the query to bind to
   * @param parameters a map of binding name and object to bind
   */
  protected void bindParameters(Query q, Map<String, Object> parameters) {
    if(parameters != null && !parameters.isEmpty()) {
      for(Entry<String, Object> parameter : parameters.entrySet()) {
        q.setParameter(parameter.getKey(), parameter.getValue());
      }
    }
  }
  
  /**
   * Attempts to find a single entity with the given named query
   * @param namedQuery the named query to execute
   * @return the found entity or null
   */
  public E findOneResult(String namedQuery) {
    return findOneResult(namedQuery, null);
  }
  
  /**
   * Attempts to find a single entity with the given named query and the parameters
   * @param namedQuery the named query to execute
   * @param parameters the parameters to bind (Allows null)
   * @return the found entity or null
   */
  public E findOneResult(String namedQuery, Map<String, Object> parameters) {
    E foundEntity = null;
    try {
      TypedQuery<E> q = em.createNamedQuery(namedQuery, entityClass);
      bindParameters(q, parameters);
      
      foundEntity = q.getSingleResult();
    } catch(NoResultException e) {
      if(logger.isDebugEnabled()) {
        logger.debug("Unable to find entity " + entityClass + " with the parameters " + parameters);
      }
    }
    
    return foundEntity;
  }
  
  /**
   * Finds a list of entities for the given named query
   * @param namedQuery the named query to execute
   * @return the List of entity results
   */
  public List<E> findResultList(String namedQuery) {
    return findResultList(namedQuery, null);
  }
  
  /**
   * Finds a list of entities wiht the given named query and optional parameters
   * @param namedQuery the named query to execute
   * @param parameters the parameters to bind in the query (Allows null)
   * @return the List of entity results
   */
  public List<E> findResultList(String namedQuery, Map<String, Object> parameters) {
    TypedQuery<E> q = em.createNamedQuery(namedQuery, entityClass);
    bindParameters(q, parameters);
    
    return q.getResultList();
  }
  
  @Override
  public Response create(E entity) {
    Status status = Status.BAD_REQUEST;
    if(logger.isDebugEnabled()) {
      logger.debug("Starting create call of " + entity);
    }
    
    if(entity == null) {
      logger.error("The given entity is null");
      
      return Response.status(Status.NO_CONTENT).build();
    }
    
    if(entity.getId() != null) {
      logger.error("The given entity has an id. Do an update call instead of create.");
      
      return Response.status(status).entity("The given entity has an id. Do an update call instead of create.").build();
    }
    
    try {
      em.persist(entity);
      
      return Response.status(Status.CREATED).header(LOCATION_HEADER, 
          String.format(LOCATION_HEADER_FORMAT, entityClass.getSimpleName().toLowerCase(), entity.getId())).build();
    } catch(EJBException e) {
      logger.error(e);
      
      return Response.status(Status.NOT_FOUND).build();
    }
  }
  
  @Override
  public Response retrieve(Long id) {
    Status status = Status.BAD_REQUEST;
    if(logger.isDebugEnabled()) {
      logger.debug("Starting the retrieval call for " + entityClass);
    }
    
    if(id == null) {
      logger.error("There is a null id when attempting to get " + entityClass);
      
      return Response.status(status).entity("There is a null id when attempting to get " + entityClass).build();
    }
    
    E entity = findById(id);
    if(entity == null) {
      status = Status.NOT_FOUND;
    } else {
      status = Status.OK;
    }
    
    return ResponseBuilder.buildResponse(status, entity);
  }
  
  @Override
  public Response retrieveAll() {
    return ResponseBuilder.buildResponse(Status.OK, em.createQuery("SELECT ec FROM " + entityClass.getSimpleName() + " ec", entityClass).getResultList());
  }

  
  @Override
  public Response update(Long id, E entity) {
    Status status = Status.BAD_REQUEST;
    if(entity == null) {
      logger.error("The entity to update was null for " + entityClass);
      
      return Response.status(Status.NO_CONTENT).build();
    }
    
    if(id == null) {
      logger.error("The entity's id does not exist. Do a create call instead.");
      
      return Response.status(Status.NOT_FOUND).build();
    }
    
    E fromDB = findById(id);
    if(fromDB == null) {
      status = Status.NOT_FOUND;
      logger.error("The given entity does not exist in the database. " + entity);
      
      return Response.status(status).entity("The given entity does not exist in the database. " + entity).build();
    }
    
    try {
      entity.setId(id);
      em.merge(entity);
      
      status = Status.OK;
    } catch(EJBException e) {
      logger.error(e);
      
      status = Status.INTERNAL_SERVER_ERROR;
    }
    
    return Response.status(status).build();
  }
  
  @Override
  public Response delete(Long id) {
    Status status = Status.BAD_REQUEST;
    if(id == null) {
      logger.error("Attempting to delete a " + entityClass + "with no id.");
      
      return Response.status(Status.NOT_FOUND).build();
    }
    
    E fromDB = findById(id);
    if(fromDB == null) {
      status = Status.NOT_FOUND;
      
      return Response.status(status).build();
    }
    
    try {
      em.remove(fromDB);
      
      status = Status.OK;
    } catch (EJBException e) {
      
      status = Status.INTERNAL_SERVER_ERROR;
    }
    
    return Response.status(status).build();
  }
}
