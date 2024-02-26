package com.migros.courier.model;

import java.time.LocalDateTime;

public class CourierLocationModel extends BaseModel {

  private static final long serialVersionUID = 12387118;

  private String courierId;
  private LocationModel location;
  private LocalDateTime time;

  public CourierLocationModel() {
  }

  public CourierLocationModel(String courierId, LocationModel location, LocalDateTime time) {
    this.courierId = courierId;
    this.location = location;
    this.time = time;
  }

  public String getCourierId() {
    return courierId;
  }

  public void setCourierId(String courierId) {
    this.courierId = courierId;
  }

  public LocationModel getLocation() {
    return location;
  }

  public void setLocation(LocationModel location) {
    this.location = location;
  }

  public LocalDateTime getTime() {
    return time;
  }

  public void setTime(LocalDateTime time) {
    this.time = time;
  }

  public static class Builder {

    private String courierId;
    private LocationModel location;
    private LocalDateTime time;

    public Builder setCourierId(String courierId) {
      this.courierId = courierId;
      return this;
    }

    public Builder setLocation(LocationModel location) {
      this.location = location;
      return this;
    }

    public Builder setTime(LocalDateTime time) {
      this.time = time;
      return this;
    }

    public CourierLocationModel build() {
      return new CourierLocationModel(courierId, location, time);
    }
  }
}
