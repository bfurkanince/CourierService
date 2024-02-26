package com.migros.courier.model;

public class StoreModel extends BaseModel {

  private static final long serialVersionUID = 12387185;

  private String name;
  private LocationModel location;

  public StoreModel() {
  }

  public StoreModel(String name, LocationModel location) {
    this.name = name;
    this.location = location;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocationModel getLocation() {
    return location;
  }

  public void setLocation(LocationModel location) {
    this.location = location;
  }

  public static class Builder {

    private String name;
    private LocationModel location;

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setLocation(LocationModel location) {
      this.location = location;
      return this;
    }

    public StoreModel build() {
      return new StoreModel(name, location);
    }
  }
}
