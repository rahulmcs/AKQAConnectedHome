
package com.smac0.akqaconnectedhome.restservices;

import com.smac0.akqaconnectedhome.model.BathWaterTemperature;

import retrofit.http.GET;
import rx.Observable;

/**
 * Bath REST Service interface as required by Retrofit.
 */
public interface BathService {
    @GET("/mobile-test/bath")
    Observable<BathWaterTemperature> getBathTemperature();
}
