package com.github.mlaursen.brews.api;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import com.github.mlaursen.brews.util.BrewsDB;

/**
 * A simple class that defines the entity manager to be used in Web Resources.
 * 
 * @author laursenm
 *
 */
public abstract class BaseResource {
  
  @Inject
  @BrewsDB
  protected EntityManager em;
}
