package com.migros.courier.controller.v1;

import static com.migros.courier.controller.BaseController.COURIER_API_URL;

import com.migros.courier.controller.BaseController;
import com.migros.courier.factory.ServiceFactory;
import com.migros.courier.model.ApiResponseModel;
import com.migros.courier.model.CourierLocationModel;
import com.migros.courier.service.CourierService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = COURIER_API_URL)
public class CourierController extends BaseController {

  private static final String DEFAULT_COURIER_SERVICE_NAME = "DefaultCourier";

  private final ServiceFactory serviceFactory;

  public CourierController(final ServiceFactory serviceFactory) {
    Assert.notNull(serviceFactory, "serviceFactory must not be null.");
    this.serviceFactory = serviceFactory;
  }

  @PostMapping("/locations")
  public ResponseEntity<ApiResponseModel> addLocation(@RequestBody final CourierLocationModel courierLocationModel) {
    final CourierService service = serviceFactory.createService(DEFAULT_COURIER_SERVICE_NAME);
    return service.addLocation(courierLocationModel);
  }

  @GetMapping("/{courierId}/total-distance")
  public ResponseEntity<ApiResponseModel> getTotalTravelDistance(@PathVariable final String courierId) {
    final CourierService service = serviceFactory.createService(DEFAULT_COURIER_SERVICE_NAME);
    return service.getTotalTravelDistance(courierId);
  }

}
