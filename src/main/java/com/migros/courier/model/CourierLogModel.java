package com.migros.courier.model;

import java.time.LocalDateTime;

public class CourierLogModel extends BaseModel {

  private static final long serialVersionUID = 12387188;

  private CourierModel courier;
  private StoreModel store;
  private LocalDateTime time;

  public CourierLogModel() {
  }

  public CourierLogModel(final CourierModel courier, final StoreModel store, final LocalDateTime time) {
    this.courier = courier;
    this.store = store;
    this.time = time;
  }

  public CourierModel getCourier() {
    return courier;
  }

  public void setCourier(CourierModel courier) {
    this.courier = courier;
  }

  public StoreModel getStore() {
    return store;
  }

  public void setStore(StoreModel store) {
    this.store = store;
  }

  public LocalDateTime getTime() {
    return time;
  }

  public void setTime(LocalDateTime time) {
    this.time = time;
  }

  public static class Builder {

    private CourierModel courier;
    private StoreModel store;
    private LocalDateTime time;

    public Builder setCourier(CourierModel courier) {
      this.courier = courier;
      return this;
    }

    public Builder setStore(StoreModel store) {
      this.store = store;
      return this;
    }

    public Builder setTime(LocalDateTime time) {
      this.time = time;
      return this;
    }

    public CourierLogModel build() {
      return new CourierLogModel(courier, store, time);
    }
  }
}
