/**
 * 
 */
package com.github.mlaursen.brews.api.crud;

import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.github.mlaursen.brews.api.BaseResource;
import com.github.mlaursen.brews.entity.GeneratedIdEntity;
import com.github.mlaursen.brews.util.crud.CRUDUtils;

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
  
  private Class<E> entityClass;
  
  protected GenericCRUDResource(Class<E> entityClass) {
    this.entityClass = entityClass;
  }
  
  @Override
  public Response create(E entity) {
    return CRUDUtils.create(entity, em, logger);
  }
  
  @Override
  public Response retrieve(Long id) {
    return CRUDUtils.retrieve(id, entityClass, em, logger);
  }
  
  @Override
  public Response retrieveAll() {
    return CRUDUtils.retrieveAll(entityClass, em);
  }

  
  @Override
  public Response update(Long id, E entity) {
    return CRUDUtils.update(id, entity, entityClass, em, logger);
  }
  
  @Override
  public Response delete(Long id) {
    return CRUDUtils.delete(id, entityClass, em, logger);
  }
}
