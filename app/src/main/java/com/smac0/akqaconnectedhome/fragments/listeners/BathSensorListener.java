
package com.smac0.akqaconnectedhome.fragments.listeners;

public interface BathSensorListener {

    void onColdWaterTemperatureChanged(int temperature);

    void onHotWaterTemperatureChanged(int temperature);

    void onWaterLevelChanged(int waterLevel);
}
