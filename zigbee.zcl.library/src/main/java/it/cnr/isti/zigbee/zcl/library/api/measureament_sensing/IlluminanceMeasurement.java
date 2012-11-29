package it.cnr.isti.zigbee.zcl.library.api.measureament_sensing;

import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.ZCLCluster;

public interface IlluminanceMeasurement extends ZCLCluster {

	static final short ID = 0x0400;
	static final String NAME = "Illuminance Measurement";
	static final String DESCRIPTION = "Attributes and commands for configuring the measurement of illuminance and reporting illuminance measurement.";

	public Attribute getMeasuredValue();
	public Attribute getMinMeasuredValue();	
	public Attribute getMaxMeasuredValue();
	public Attribute getTolerance();	
	public Attribute getLightSensorType();
}