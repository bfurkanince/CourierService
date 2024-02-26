package com.migros.courier.model;

import java.util.List;

public class CourierModel extends BaseModel {

  private static final long serialVersionUID = 12387183;

  private String id;
  private String name;
  private List<LocationModel> locations;

  public CourierModel() {
  }

  public CourierModel(final String id, final String name) {
    this.id = id;
    this.name = name;
  }

  public CourierModel(String id, String name, List<LocationModel> locations) {
    this.id = id;
    this.name = name;
    this.locations = locations;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<LocationModel> getLocations() {
    return locations;
  }

  public void setLocations(List<LocationModel> locations) {
    this.locations = locations;
  }

  public static class Builder {

    private String id;
    private String name;
    private List<LocationModel> locations;

    public Builder setId(String id) {
      this.id = id;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setLocations(List<LocationModel> locations) {
      this.locations = locations;
      return this;
    }

    public CourierModel build() {
      return new CourierModel(id, name, locations);
    }
  }
}
