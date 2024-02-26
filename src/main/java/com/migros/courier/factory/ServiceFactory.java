package com.migros.courier.factory;

import com.migros.courier.exception.BusinessException;
import com.migros.courier.service.CourierService;
import com.migros.courier.service.implementation.DefaultCourierService;
import org.springframework.stereotype.Component;

@Component
public class ServiceFactory {

  private static final String DEFAULT_COURIER_SERVICE_NAME = "DefaultCourier";

  public CourierService createService(final String serviceName){
    try {
      if (DEFAULT_COURIER_SERVICE_NAME.equals(serviceName)) {
        final DefaultCourierService courierService = new DefaultCourierService();
        courierService.afterPropertiesSet();
        return courierService;
      } else {
        throw new BusinessException("undefined service name.");
      }
    } catch (final Exception exception) {
      throw new BusinessException(exception.getMessage());
    }
  }
}
