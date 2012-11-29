package it.cnr.isti.zigbee.ha.device.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Groups;
import it.cnr.isti.zigbee.ha.cluster.glue.general.LevelControl;
import it.cnr.isti.zigbee.ha.cluster.glue.general.OnOff;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Scenes;
import it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.OccupacySensing;
import it.cnr.isti.zigbee.ha.device.api.lighting.ColorDimmableLight;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HAProfile;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.ha.driver.core.reflection.AbstractDeviceDescription;
import it.cnr.isti.zigbee.ha.driver.core.reflection.DeviceDescription;
import it.cnr.isti.zigbee.zcl.library.api.lighting.ColorControl;

import org.osgi.framework.BundleContext;

public class ColorDimmableLightDevice extends HADeviceBase implements ColorDimmableLight {

	private OnOff onOff;
	private Scenes scenes;
	private Groups groups;
	private LevelControl levelControl;
	private OccupacySensing occupancySensing;
	private ColorControl colorControl;

	public ColorDimmableLightDevice(BundleContext ctx, ZigBeeDevice zbDevice) throws ZigBeeHAException {

		super(ctx, zbDevice);

		onOff = (OnOff) addCluster(HAProfile.ON_OFF);
		scenes = (Scenes) addCluster(HAProfile.SCENES);
		groups = (Groups) addCluster(HAProfile.GROUPS);
		levelControl = (LevelControl) addCluster(HAProfile.LEVELCONTROL);
		occupancySensing = (OccupacySensing) addCluster(HAProfile.OCCUPANCY_SENSING);
		colorControl = (ColorControl) addCluster(HAProfile.COLOR_CONTROL);
	}

	final static DeviceDescription DEVICE_DESCRIPTOR =  new AbstractDeviceDescription(){

		public int[] getCustomClusters() {
			return ColorDimmableLight.CUSTOM;
		}

		public int[] getMandatoryCluster() {
			return ColorDimmableLight.MANDATORY;
		}

		public int[] getOptionalCluster() {
			return ColorDimmableLight.OPTIONAL;
		}

		public int[] getStandardClusters() {
			return ColorDimmableLight.STANDARD;
		}		
	};

	public OnOff getOnOff() {
		return onOff;
	}

	public Scenes getScenes() {
		return scenes;
	}

	public Groups getGroups() {
		return groups;
	}

	public LevelControl getLevelControl() {
		return levelControl;
	}

	public OccupacySensing getOccupacySensing() {
		return occupancySensing;
	}

	public ColorControl getColorControl() {
		return colorControl;
	}

	@Override
	public String getName() {
		return ColorDimmableLight.NAME;
	}

	@Override
	public DeviceDescription getDescription() {
		return DEVICE_DESCRIPTOR;
	}
}