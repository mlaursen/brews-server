/**
 * 
 */
package com.github.mlaursen.brews.api.crud;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * Defines the standards for an entity that can be retrieved.
 * 
 * @author mlaursen
 *
 */
public interface RetrievableResource {

  /**
   * Attempts to find an entity by the given id.
   * 
   * <p>The url should look as follows:
   * <pre>
   *    /api/{entityName}/{entityId}
   * </pre>
   * @param id the entity's id
   * @return a Response with the entity or null
   */
  @GET
  @Path("/{entityId}")
  Response retrieve(@PathParam("entityId") Long id);
}
