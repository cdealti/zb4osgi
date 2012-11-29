package it.cnr.isti.zigbee.ha.device.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Groups;
import it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.IlluminanceMeasurement;
import it.cnr.isti.zigbee.ha.device.api.lighting.LightSensor;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HAProfile;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.ha.driver.core.reflection.AbstractDeviceDescription;
import it.cnr.isti.zigbee.ha.driver.core.reflection.DeviceDescription;

import org.osgi.framework.BundleContext;

public class LightSensorDevice extends HADeviceBase implements LightSensor {

	private IlluminanceMeasurement illuminanceMeasurement;
	private Groups groups;

	public LightSensorDevice(BundleContext ctx, ZigBeeDevice zbDevice) throws ZigBeeHAException {

		super(ctx, zbDevice);

		illuminanceMeasurement = (IlluminanceMeasurement) addCluster(HAProfile.ILLUMINANCE_MEASUREMENT);
		groups = (Groups) addCluster(HAProfile.GROUPS);
	}

	public IlluminanceMeasurement getIlluminanceMeasurement() {

		return illuminanceMeasurement;
	}

	@Override
	public String getName() {

		return LightSensor.NAME;
	}

	@Override
	public DeviceDescription getDescription() {

		return DEVICE_DESCRIPTOR;
	}

	final static DeviceDescription DEVICE_DESCRIPTOR =  new AbstractDeviceDescription(){

		public int[] getCustomClusters() {
			return LightSensor.CUSTOM;
		}

		public int[] getMandatoryCluster() {
			return LightSensor.MANDATORY;
		}

		public int[] getOptionalCluster() {
			return LightSensor.OPTIONAL;
		}

		public int[] getStandardClusters() {
			return LightSensor.STANDARD;
		}

	};

	public Groups getGroups() {
		return groups;
	}
}