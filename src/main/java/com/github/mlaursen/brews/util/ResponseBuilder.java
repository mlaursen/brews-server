/**
 * 
 */
package com.github.mlaursen.brews.util;

import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.github.mlaursen.brews.entity.GeneratedIdEntity;

/**
 * Utility class for building responses with messages/entities.
 * 
 * @author mlaursen
 *
 */
public class ResponseBuilder {
  private ResponseBuilder() {}
  
  /**
   * Builds a Response with the given status and the entity in the given media type
   * @param status the Response status (Example: OK)
   * @param entity the entity to return. (Allows null)
   * @param type the MediaType to form the response in
   * @param <E> any entity that extends the {@link GeneratedIdEntity}
   * @return a Response with the given data
   */
  public static <E extends GeneratedIdEntity> Response buildResponse(Status status, E entity, MediaType type) {
    return Response.status(status).type(type).entity(entity).build();
  }
  
  /**
   * Builds a Response with the given status and the entity as json.
   * 
   * @param status the Response status (Example: OK)
   * @param entity the entity to return. (Allows null)
   * @param <E> any entity that extends the {@link GeneratedIdEntity}
   * @return a Response with the given data
   */
  public static <E extends GeneratedIdEntity> Response buildResponse(Status status, E entity) {
    return buildResponse(status, entity, MediaType.APPLICATION_JSON_TYPE);
  }
  
  public static <E extends GeneratedIdEntity> Response buildResponse(Status satus, List<E> entity, MediaType type) {
    return Response.status(satus).type(type).entity(entity).build();
  }
  
  public static <E extends GeneratedIdEntity> Response buildResponse(Status status, List<E> entity) {
    return buildResponse(status, entity, MediaType.APPLICATION_JSON_TYPE);
  }
}
