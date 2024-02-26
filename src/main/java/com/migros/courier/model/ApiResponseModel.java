package com.migros.courier.model;

public class ApiResponseModel {

  private boolean success;
  private String message;

  public ApiResponseModel() {
  }

  public ApiResponseModel(final String message, boolean success) {
    this.message = message;
    this.success = success;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public static class Builder {

    private boolean success;
    private String message;

    public Builder setSuccess(boolean success) {
      this.success = success;
      return this;
    }

    public Builder setMessage(String message) {
      this.message = message;
      return this;
    }

    public ApiResponseModel build() {
      return new ApiResponseModel(message, success);
    }
  }
}
