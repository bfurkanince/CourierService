package com.migros.courier.service.implementation;

import com.migros.courier.exception.BusinessException;
import com.migros.courier.model.ApiResponseModel;
import com.migros.courier.model.CourierLocationModel;
import com.migros.courier.model.CourierLogModel;
import com.migros.courier.model.CourierModel;
import com.migros.courier.model.LocationModel;
import com.migros.courier.model.StoreModel;
import com.migros.courier.service.CourierService;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DefaultCourierService implements CourierService, InitializingBean {

  private static final double MAXIMUM_DISTANCE_FROM_STORE = 100;
  private static final long MAXIMUM_SECONDS_FOR_LAST_ENTRANCE = 60;
  private static final int RADIUS_OF_THE_EARTH = 6371;
  private static List<CourierLogModel> courierLogModels;
  private List<StoreModel> stores;
  private List<CourierModel> couriers;

  @Override
  public void afterPropertiesSet() throws Exception {
    stores = generateStoreModels();
    couriers = generateCourierModels();
    courierLogModels = new ArrayList<>();
  }

  @Override
  public ResponseEntity<ApiResponseModel> addLocation(final CourierLocationModel courierLocationModel) {
    try {
      final CourierModel courierModel = findCourierById(courierLocationModel.getCourierId());
      for (StoreModel store : stores) {
        final LocationModel locationOfStore = store.getLocation();
        final LocationModel locationOfCourier = courierLocationModel.getLocation();
        final double totalDistance = calculateDistance(locationOfStore, locationOfCourier);
        if (totalDistance <= MAXIMUM_DISTANCE_FROM_STORE) {
          final Optional<CourierLogModel> courierLogModel = findCourierLog(courierModel, store);
          if (courierLogModel.isPresent()) {
            final CourierLogModel courierLog = courierLogModel.get();
            boolean isWithinOneMinute = isWithinOneMinuteOfLastEntrance(courierLog);
            if (!isWithinOneMinute) {
              courierLogModels.add(new CourierLogModel(courierModel, store, LocalDateTime.now()));
              return ResponseEntity.ok(new ApiResponseModel.Builder().setMessage("Courier location added successfully.").setSuccess(true).build());
            }
          } else {
            courierLogModels.add(new CourierLogModel(courierModel, store, LocalDateTime.now()));
            return ResponseEntity.ok(new ApiResponseModel.Builder().setMessage("Courier location added successfully.").setSuccess(true).build());
          }
        }
      }
      throw new BusinessException("Courier location could not be added successfully.");
    } catch (final Exception exception) {
      return new ResponseEntity<>(new ApiResponseModel.Builder().setMessage(exception.getMessage()).setSuccess(false).build(), HttpStatus.BAD_REQUEST);
    }
  }

  @Override
  public ResponseEntity<ApiResponseModel> getTotalTravelDistance(final String courierId) {
    try {
      final CourierModel courier = findCourierById(courierId);
      double totalDistance = 0;
      for (int index = 0; index < courier.getLocations().size() - 1; index++) {
        final LocationModel locationModelFirst = courier.getLocations().get(index);
        final LocationModel locationModelEnd = courier.getLocations().get(index + 1);
        totalDistance = totalDistance + calculateDistance(locationModelFirst, locationModelEnd);
      }
      return ResponseEntity.ok(new ApiResponseModel.Builder().setMessage("Distance successfully founded : " + totalDistance).setSuccess(true).build());
    } catch (final Exception exception) {
      return new ResponseEntity<>(new ApiResponseModel.Builder().setMessage(exception.getMessage()).setSuccess(false).build(), HttpStatus.BAD_REQUEST);
    }
  }

  private boolean isWithinOneMinuteOfLastEntrance(final CourierLogModel courierLogModel) {
    final LocalDateTime currentTime = LocalDateTime.now();
    final LocalDateTime lastEntranceTime = courierLogModel.getTime();
    final Duration duration = Duration.between(lastEntranceTime, currentTime);
    return duration.getSeconds() < MAXIMUM_SECONDS_FOR_LAST_ENTRANCE;
  }

  private double calculateDistance(final LocationModel loc1, final LocationModel loc2) {
    double lat1 = loc1.getLatitude();
    double lon1 = loc1.getLongitude();
    double lat2 = loc2.getLatitude();
    double lon2 = loc2.getLongitude();
    double latDistance = Math.toRadians(lat2 - lat1);
    double lonDistance = Math.toRadians(lon2 - lon1);
    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
        + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
        * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    return RADIUS_OF_THE_EARTH * c * 1000;
  }

  private CourierModel findCourierById(final String courierId) {
    return couriers.stream().filter(courier -> courier.getId().equals(courierId)).findFirst()
        .orElseThrow(() -> new BusinessException("Courier not found for id :" + courierId));
  }

  private Optional<CourierLogModel> findCourierLog(CourierModel courier, StoreModel store) {
    return courierLogModels.stream()
        .filter(log -> log.getCourier().equals(courier) && log.getStore().equals(store))
        .findFirst();
  }

  private List<CourierModel> generateCourierModels() {
    LocationModel locationModelRecord1 = generateLocationModel(42.9923307, 24.1244229);
    LocationModel locationModelRecord2 = generateLocationModel(44.9925307, 22.2244229);
    LocationModel locationModelRecord3 = generateLocationModel(45.9923307, 30.1244229);
    LocationModel locationModelRecord5 = generateLocationModel(46.9923307, 32.1244229);
    LocationModel locationModelRecord6 = generateLocationModel(46.9923307, 36.1244229);
    CourierModel courierModelRecord1 = generateCourierModel("1", "Ahmet", List.of(locationModelRecord1, locationModelRecord2, locationModelRecord3));
    CourierModel courierModelRecord2 = generateCourierModel("2", "Mehmet", List.of(locationModelRecord5, locationModelRecord6));
    return new ArrayList<>(List.of(courierModelRecord1, courierModelRecord2));
  }

  private List<StoreModel> generateStoreModels() {
    StoreModel storeModelRecord1 = generateStoreModel("Ataşehir MMM Migros", 40.9923307, 29.1244229);
    StoreModel storeModelRecord2 = generateStoreModel("Novada MMM Migros", 40.986106, 29.1161293);
    StoreModel storeModelRecord3 = generateStoreModel("Beylikdüzü 5M Migros", 41.0066851, 28.6552262);
    StoreModel storeModelRecord4 = generateStoreModel("Ortaköy MMM Migros", 41.055783, 29.0210292);
    StoreModel storeModelRecord5 = generateStoreModel("Caddebostan MMM Migros", 40.9632463, 29.0630908);
    return new ArrayList<>(List.of(storeModelRecord1, storeModelRecord2, storeModelRecord3, storeModelRecord4, storeModelRecord5));
  }

  private CourierModel generateCourierModel(final String id, final String name, final List<LocationModel> locationModels) {
    return new CourierModel.Builder().setId(id).setName(name).setLocations(locationModels).build();
  }

  private StoreModel generateStoreModel(final String storeName, final double latitude, final double longitude) {
    return new StoreModel.Builder().setName(storeName).setLocation(generateLocationModel(latitude, longitude)).build();
  }

  private LocationModel generateLocationModel(final double latitude, final double longitude) {
    return new LocationModel.Builder().setLatitude(latitude).setLongitude(longitude).build();
  }

}
