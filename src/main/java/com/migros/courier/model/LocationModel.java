package com.migros.courier.model;

public class LocationModel extends BaseModel {

  private static final long serialVersionUID = 12387184;

  private double latitude;
  private double longitude;

  public LocationModel() {
  }

  public LocationModel(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public static class Builder {

    private double latitude;
    private double longitude;

    public Builder setLatitude(double latitude) {
      this.latitude = latitude;
      return this;
    }

    public Builder setLongitude(double longitude) {
      this.longitude = longitude;
      return this;
    }

    public LocationModel build() {
      return new LocationModel(latitude, longitude);
    }
  }

}
