package com.github.mlaursen.brews.util;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class DatabaseProducer {
  @Produces
  @PersistenceContext(unitName = "brews")
  @BrewsDB
  protected EntityManager em;
}
