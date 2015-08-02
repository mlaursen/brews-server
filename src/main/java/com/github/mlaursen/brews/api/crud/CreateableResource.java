/**
 * 
 */
package com.github.mlaursen.brews.api.crud;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.mlaursen.brews.entity.GeneratedIdEntity;

/**
 * An interface declaring the standard for saying that an Entity can be created.
 * 
 * @author mlaursen
 *
 */
public interface CreateableResource<E extends GeneratedIdEntity> {

  /**
   * Attempts to create an entity from JSON. If there was a successful creation,
   * the {@link HttpServletResponse#SC_CREATED} should be returned along with the
   * created entity as json. Otherwise, {@link HttpServletResponse#SC_BAD_REQUEST}
   * will be returned.
   * 
   * <p>The url should look as follows:
   * <pre>
   *    /api/{entityName}
   * </pre>
   * 
   * @param entity the entity to create.
   * @return a HttpServletResponse representing the result of the creation action and a Location
   *         header of containing the new id. Ex: <code>/api/{entityName}/{id}
   */
  @POST
  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  Response create(E entity);
}
