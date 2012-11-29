package it.cnr.isti.zigbee.zcl.library.impl.measureament_sensing;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.measureament_sensing.IlluminanceMeasurement;
import it.cnr.isti.zigbee.zcl.library.impl.attribute.Attributes;
import it.cnr.isti.zigbee.zcl.library.impl.core.AttributeImpl;
import it.cnr.isti.zigbee.zcl.library.impl.core.ZCLClusterBase;

public class IlluminanceMeasurementCluster extends ZCLClusterBase implements IlluminanceMeasurement {

	private final AttributeImpl measuredValue;
	private final AttributeImpl minMeasuredValue;
	private final AttributeImpl maxMeasuredValue;
	private final AttributeImpl tolerance;
	private final AttributeImpl lightSensorType;

	private final Attribute[] attributes;

	public IlluminanceMeasurementCluster(ZigBeeDevice zbDevice) {

		super(zbDevice);
		measuredValue = new AttributeImpl(zbDevice,this,Attributes.MEASURED_VALUE_UNSIGNED_16_BIT);
		minMeasuredValue = new AttributeImpl(zbDevice,this,Attributes.MIN_MEASURED_VALUE_UNSIGNED_16_BIT);
		maxMeasuredValue = new AttributeImpl(zbDevice,this,Attributes.MAX_MEASURED_VALUE_UNSIGNED_16_BIT);
		tolerance = new AttributeImpl(zbDevice,this,Attributes.TOLERANCE);
		lightSensorType = new AttributeImpl(zbDevice,this,Attributes.LIGHT_SENSOR_TYPE);

		attributes = new AttributeImpl[]{measuredValue, minMeasuredValue, maxMeasuredValue, tolerance, lightSensorType};
	}

	public Attribute getMeasuredValue() {

		return measuredValue;
	}

	public Attribute getMinMeasuredValue() {

		return minMeasuredValue;
	}

	public Attribute getMaxMeasuredValue() {

		return maxMeasuredValue;
	}

	public Attribute getTolerance() {

		return tolerance;
	}

	public Attribute getLightSensorType() {

		return lightSensorType;
	}

	@Override
	public short getId() {

		return IlluminanceMeasurement.ID;
	}

	@Override
	public String getName() {

		return IlluminanceMeasurement.NAME;
	}

	@Override
	public Attribute[] getStandardAttributes() {

		return attributes;
	}
}