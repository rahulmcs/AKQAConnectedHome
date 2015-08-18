
package com.smac0.akqaconnectedhome.fragments.listeners;

/*
 * Listener Interface used for registering notifications related to various parameters of the Bath
 */
public interface BathSensorListener {

    void onColdWaterTemperatureChanged(int temperature);

    void onHotWaterTemperatureChanged(int temperature);

    void onWaterLevelChanged(int waterLevel);
}
