/**
 * 
 */
package com.github.mlaursen.brews.api;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * This class is how java watches web services. I set the base path of watching web service requests to be
 * <code>/api</code>. Currently, the configuration finds EndsPoints by searching all classes inside of the
 * <code>com.github.mlaursen.mybrews.api</code> package. To override this default configuration, override {@link #getClasses()}.
 * 
 * 
 * @author mlaursen
 */
@ApplicationPath("/api")
public class ApiConfiguration extends Application {

}
