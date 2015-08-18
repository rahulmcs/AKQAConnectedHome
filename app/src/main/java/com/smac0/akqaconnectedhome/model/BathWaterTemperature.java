
package com.smac0.akqaconnectedhome.model;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Expose;

/**
 * Bath Water Temperature POJO
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
