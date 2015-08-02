/**
 * 
 */
package com.github.mlaursen.brews.entity;

import javax.persistence.MappedSuperclass;

/**
 * Abstract class for any Entity that has a name column and a generated 
 * 
 * @author mlaursen
 * @see GeneratedIdEntity
 */
@MappedSuperclass
public abstract class GeneratedIdNamedEntity extends GeneratedIdEntity implements NamedEntity {
  
  private String name;
  
  @Override
  public void setName(String name) {
    this.name = name;
  }
  
  @Override
  public String getName() {
    return name;
  }
}
