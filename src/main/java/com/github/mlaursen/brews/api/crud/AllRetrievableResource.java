/**
 * 
 */
package com.github.mlaursen.brews.api.crud;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * An interface for declaring that an entity can be have all results returned.
 * 
 * @author mlaursen
 * @see GenericCRUDResource
 */
public interface AllRetrievableResource {

  /**
   * Gets all of the specified entity.
   * 
   * <p>The url should be as follows:
   * <pre>
   *    /api/{entityName}
   * </pre>
   * 
   * @return a List of the Entity
   */
  @GET
  @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  Response retrieveAll();
}
