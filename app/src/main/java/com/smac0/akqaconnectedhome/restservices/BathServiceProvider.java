
package com.smac0.akqaconnectedhome.restservices;

import com.google.inject.Provider;
import com.smac0.akqaconnectedhome.application.Properties;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Provides a Retrofit instance of the BathService endpoint to get the BathTemperature values from
 * the Bath Server
 */
public class BathServiceProvider implements Provider<BathService> {
    @Override
    public BathService get() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Properties.REST_SERVICE_BASE_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(mRequestInterceptor).build();

        return restAdapter.create(BathService.class);
    }

    RequestInterceptor mRequestInterceptor = new RequestInterceptor() {
        @Override
        public void intercept(RequestFacade requestFacade) {
            requestFacade.addHeader("User-Agent", Properties.USER_AGENT);
        }
    };
}
