/**
 * 
 */
package com.github.mlaursen.brews.api.crud;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.github.mlaursen.brews.entity.GeneratedIdEntity;

/**
 * Defines the standards for a Deleteable resource.
 * 
 * @author mlaursen
 *
 */
public interface DeleteableResource<E extends GeneratedIdEntity> {
  /**
   * Attempts to delete an entity by the given entity id.
   * 
   * <p>The url should look as follows:
   * <pre>
   *    /api/{entityName}/{entityId}
   * </pre>
   * @param id the entity's id to delete
   * @return a HttpServletResponse representing the result of the delete action
   */
  @DELETE
  @Path("/{entityId}")
  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  Response delete(@PathParam("entityId") Long id);
}
