
package com.smac0.akqaconnectedhome.model;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

/**
 * Created by rahulchaudhari on 15/08/15.
 */
public class BathWaterTemperature {

    @SerializedName("hot_water")
    @Expose
    private final Integer mHotWaterTemperature;

    @SerializedName("cold_water")
    @Expose
    private final Integer mColdWaterTemperature;

    public BathWaterTemperature(Integer coldWaterTemperature, Integer hotWaterTemperature) {
        mColdWaterTemperature = coldWaterTemperature;
        mHotWaterTemperature = hotWaterTemperature;
    }

    public Integer getHotWaterTemperature() {
        return mHotWaterTemperature;
    }

    public Integer getColdWaterTemperature() {
        return mColdWaterTemperature;
    }

}
