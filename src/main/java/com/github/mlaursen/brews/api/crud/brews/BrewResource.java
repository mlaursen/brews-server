package com.github.mlaursen.brews.api.crud.brews;

import javax.ejb.Stateless;
import javax.ws.rs.Path;

import com.github.mlaursen.brews.api.crud.GenericCRUDResource;
import com.github.mlaursen.brews.entity.brews.Brew;

@Stateless
@Path("/brews")
public class BrewResource extends GenericCRUDResource<Brew> {
  
  public BrewResource() {
    super(Brew.class);
  }

}
