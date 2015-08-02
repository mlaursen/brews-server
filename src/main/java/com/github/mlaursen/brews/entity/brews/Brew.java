package com.github.mlaursen.brews.entity.brews;

import java.util.Date;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import com.github.mlaursen.brews.entity.GeneratedIdNamedEntity;

@Entity
@XmlRootElement
public class Brew extends GeneratedIdNamedEntity {
  private Double originalGravity;
  private Double finalGravity;
  private Double abv;
  private Date dateBrewed;
  private Date primaryFermentationEnd;
  private Date secondaryFermentationEnd;
  private Date dateDrinkable;
  private Integer numberOfBottles;

  public Brew() {
  }

  public Double getOriginalGravity() {
    return originalGravity;
  }

  public void setOriginalGravity(Double originalGravity) {
    this.originalGravity = originalGravity;
  }

  public Double getFinalGravity() {
    return finalGravity;
  }

  public void setFinalGravity(Double finalGravity) {
    this.finalGravity = finalGravity;
  }

  public Double getAbv() {
    return abv;
  }

  public void setAbv(Double abv) {
    this.abv = abv;
  }

  public Date getDateBrewed() {
    return dateBrewed;
  }

  public void setDateBrewed(Date dateBrewed) {
    this.dateBrewed = dateBrewed;
  }

  public Date getPrimaryFermentationEnd() {
    return primaryFermentationEnd;
  }

  public void setPrimaryFermentationEnd(Date primaryFermentationEnd) {
    this.primaryFermentationEnd = primaryFermentationEnd;
  }

  public Date getSecondaryFermentationEnd() {
    return secondaryFermentationEnd;
  }

  public void setSecondaryFermentationEnd(Date secondaryFermentationEnd) {
    this.secondaryFermentationEnd = secondaryFermentationEnd;
  }

  public Date getDateDrinkable() {
    return dateDrinkable;
  }

  public void setDateDrinkable(Date dateDrinkable) {
    this.dateDrinkable = dateDrinkable;
  }

  public Integer getNumberOfBottles() {
    return numberOfBottles;
  }

  public void setNumberOfBottles(Integer numberOfBottles) {
    this.numberOfBottles = numberOfBottles;
  }

}
