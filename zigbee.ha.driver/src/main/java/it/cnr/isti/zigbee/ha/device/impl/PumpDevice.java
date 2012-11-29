package it.cnr.isti.zigbee.ha.device.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Groups;
import it.cnr.isti.zigbee.ha.cluster.glue.general.LevelControl;
import it.cnr.isti.zigbee.ha.cluster.glue.general.OnOff;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Scenes;
import it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.TemperatureMeasurement;
import it.cnr.isti.zigbee.ha.device.api.hvac.Pump;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HAProfile;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.ha.driver.core.reflection.AbstractDeviceDescription;
import it.cnr.isti.zigbee.ha.driver.core.reflection.DeviceDescription;

import org.osgi.framework.BundleContext;

public class PumpDevice extends HADeviceBase implements Pump {

	private OnOff onOffCluster;
	private Scenes scenesCluster;
	private Groups groupsCluster;
	private LevelControl levelControlCluster;
	private TemperatureMeasurement temperatureMeasurementCluster;

	public PumpDevice(BundleContext ctx, ZigBeeDevice zbDevice) throws ZigBeeHAException{
		super(ctx, zbDevice);

		levelControlCluster = (LevelControl) addCluster(HAProfile.LEVEL_CONTROL);
		onOffCluster = (OnOff) addCluster(HAProfile.ON_OFF);
		scenesCluster = (Scenes) addCluster(HAProfile.SCENES);
		groupsCluster = (Groups) addCluster(HAProfile.GROUPS);
		temperatureMeasurementCluster = (TemperatureMeasurement) addCluster(HAProfile.TEMPERATURE_MEASUREMENT);
	}

	final static DeviceDescription DEVICE_DESCRIPTOR =  new AbstractDeviceDescription(){

		public int[] getCustomClusters() {
			return Pump.CUSTOM;
		}

		public int[] getMandatoryCluster() {
			return Pump.MANDATORY;
		}

		public int[] getOptionalCluster() {
			return Pump.OPTIONAL;
		}

		public int[] getStandardClusters() {
			return Pump.STANDARD;
		}
	};

	@Override
	public DeviceDescription getDescription() {
		return DEVICE_DESCRIPTOR;
	}

	@Override
	public String getName() {
		return Pump.NAME;
	}

	public OnOff getOnOff() {
		return onOffCluster;
	}

	public Scenes getScenes() {
		return scenesCluster;
	}

	public Groups getGroups() {
		return groupsCluster;
	}

	public LevelControl getLevelControl() {
		return levelControlCluster;
	}

	public TemperatureMeasurement getTemperatureMeasurement() {
		return temperatureMeasurementCluster;
	}
}