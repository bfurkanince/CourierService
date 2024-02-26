package com.migros.courier.service;

import com.migros.courier.model.ApiResponseModel;
import com.migros.courier.model.CourierLocationModel;
import org.springframework.http.ResponseEntity;

public interface CourierService {

  ResponseEntity<ApiResponseModel> addLocation(final CourierLocationModel courierLocationModel);
  ResponseEntity<ApiResponseModel> getTotalTravelDistance(final String courierId);

}
