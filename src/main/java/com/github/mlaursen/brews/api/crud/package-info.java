/**
 * This is a package for all CRUD (Create, Retrieve, Update, Delete) web service requests. There should
 * be NO real logic in this package. It should ONLY be the simple requests. Any logic should go into the 
 * <code>com.github.mlaursen.mybrews.api.process</code> package.
 * 
 * <p>I created a Generic CRUD Resource that handles 95% (bsing percent <a href="http://xkcd.com/1252/">Increased Risk</a>)
 * That defines default implementations for creating, updating, retrieving and deleting an entity.
 * 
 * <p>There is an interesting problem that I have no idea how to fix though. It seems that the interfaces I created 
 * for standardizing the web service calls all need to be implemented by the same class, otherwise there
 * is some metadata problem with Jersey and all other web service calls fail... Would be nice to figure out how that works.
 * If you create a new webservice in that class and define the {@code Path, Type, Consumes, Produces}, it works. There is only
 * a problem if you implement a standard.
 * 
 * @author laursenm
 */
package com.github.mlaursen.brews.api.crud;