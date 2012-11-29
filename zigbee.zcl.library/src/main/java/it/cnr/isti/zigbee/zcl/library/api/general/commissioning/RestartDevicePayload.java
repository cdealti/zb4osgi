package it.cnr.isti.zigbee.zcl.library.api.general.commissioning;

public interface RestartDevicePayload {

	RestartDeviceOptions getOptions();
	int getDelay();
	int getJitter();

}