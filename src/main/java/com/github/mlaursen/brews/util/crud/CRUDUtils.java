package com.github.mlaursen.brews.util.crud;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.logging.Logger;

import javax.ejb.EJBException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.github.mlaursen.brews.entity.GeneratedIdEntity;
import com.github.mlaursen.brews.util.ResponseBuilder;

public class CRUDUtils {
  private static final String LOCATION_HEADER = "Location";
  private static final String LOCATION_HEADER_FORMAT = "/api/%ss/%d";

  private CRUDUtils() {}
  
  /**
   * Binds all the parameters to a given query.
   * @param q the query to bind to
   * @param parameters a map of binding name and object to bind
   */
  public static void bindParameters(Query q, Map<String, Object> parameters) {
    if(parameters != null && !parameters.isEmpty()) {
      for(Entry<String, Object> parameter : parameters.entrySet()) {
        q.setParameter(parameter.getKey(), parameter.getValue());
      }
    }
  }
  
  /**
   * Attempts to find an entity by the given id
   * @param id the id
   * @return the Entity or null
   */
  public static <E extends GeneratedIdEntity> E findById(Long id, Class<E> entityClass, EntityManager em) {
    return em.find(entityClass, id);
  }
  
  /**
   * Attempts to find a single entity with the given named query
   * @param namedQuery the named query to execute
   * @param ec the entity class
   * @param em The entity manager
   * @param logger the logger
   * @return the found entity or null
   */
  public static <E extends GeneratedIdEntity> E findOneResult(String namedQuery, Class<E> ec, EntityManager em, Logger logger) {
    return findOneResult(namedQuery, null, ec, em, logger);
  }
  
  /**
   * Attempts to find a single entity with the given named query and the parameters
   * @param namedQuery the named query to execute
   * @param parameters the parameters to bind (Allows null)
   * @param entityClass the entity class
   * @param em The entity manager
   * @param logger the logger
   * @return the found entity or null
   */
  public static <E extends GeneratedIdEntity> E findOneResult(String namedQuery, Map<String, Object> parameters, Class<E> entityClass, EntityManager em, Logger logger) {
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
  public static <E extends GeneratedIdEntity> List<E> findResultList(String namedQuery, Class<E> entityClass, EntityManager em) {
    return findResultList(namedQuery, null, entityClass, em);
  }
  
  /**
   * Finds a list of entities wiht the given named query and optional parameters
   * @param namedQuery the named query to execute
   * @param parameters the parameters to bind in the query (Allows null)
   * @return the List of entity results
   */
  public static <E extends GeneratedIdEntity> List<E> findResultList(String namedQuery, Map<String, Object> parameters, Class<E> entityClass, EntityManager em) {
    TypedQuery<E> q = em.createNamedQuery(namedQuery, entityClass);
    bindParameters(q, parameters);
    
    return q.getResultList();
  }
  
  /**
   * 
   * @param entity
   * @param em
   * @param logger
   * @return
   */
  public static <E extends GeneratedIdEntity> Response create(E entity, EntityManager em, Logger logger) {
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
          String.format(LOCATION_HEADER_FORMAT, entity.getClass().getSimpleName().toLowerCase(), entity.getId())).build();
    } catch(EJBException e) {
      logger.error(e);
      
      return Response.status(Status.NOT_FOUND).build();
    }
  }
  
  /**
   * 
   * @param id
   * @param entityClass
   * @param em
   * @param logger
   * @return
   */
  public static <E extends GeneratedIdEntity> Response retrieve(Long id, Class<E> entityClass, EntityManager em, Logger logger) {
    Status status = Status.BAD_REQUEST;
    if(logger.isDebugEnabled()) {
      logger.debug("Starting the retrieval call for " + entityClass);
    }
    
    if(id == null) {
      logger.error("There is a null id when attempting to get " + entityClass);
      
      return Response.status(status).entity("There is a null id when attempting to get " + entityClass).build();
    }
    
    E entity = findById(id, entityClass, em);
    if(entity == null) {
      status = Status.NOT_FOUND;
    } else {
      status = Status.OK;
    }
    
    return ResponseBuilder.buildResponse(status, entity);
  }
  
  /**
   * 
   * @param entityClass
   * @param em
   * @return
   */
  public static <E extends GeneratedIdEntity> Response retrieveAll(Class<E> entityClass, EntityManager em) {
    return ResponseBuilder.buildResponse(Status.OK, em.createQuery("SELECT ec FROM " + entityClass.getSimpleName() + " ec", entityClass).getResultList());
  }

  /**
   * 
   * @param id
   * @param entity
   * @param entityClass
   * @param em
   * @param logger
   * @return
   */
  public static <E extends GeneratedIdEntity> Response update(Long id, E entity, Class<E> entityClass, EntityManager em, Logger logger) {
    Status status = Status.BAD_REQUEST;
    if(entity == null) {
      logger.error("The entity to update was null for " + entityClass);
      
      return Response.status(Status.NO_CONTENT).build();
    }
    
    if(id == null) {
      logger.error("The entity's id does not exist. Do a create call instead.");
      
      return Response.status(Status.NOT_FOUND).build();
    }
    
    E fromDB = findById(id, entityClass, em);
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
  
  /**
   * 
   * @param id
   * @param entityClass
   * @param em
   * @param logger
   * @return
   */
  public static <E extends GeneratedIdEntity> Response delete(Long id, Class<E> entityClass, EntityManager em, Logger logger) {
    Status status = Status.BAD_REQUEST;
    if(id == null) {
      logger.error("Attempting to delete a " + entityClass + "with no id.");
      
      return Response.status(Status.NOT_FOUND).build();
    }
    
    E fromDB = findById(id, entityClass, em);
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
