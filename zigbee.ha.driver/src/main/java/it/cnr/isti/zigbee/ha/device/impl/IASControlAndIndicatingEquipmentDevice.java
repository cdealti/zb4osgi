package it.cnr.isti.zigbee.ha.device.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Groups;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Identify;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Scenes;
import it.cnr.isti.zigbee.ha.cluster.glue.security_safety.IASACE;
import it.cnr.isti.zigbee.ha.cluster.glue.security_safety.IASWD;
import it.cnr.isti.zigbee.ha.cluster.glue.security_safety.IASZone;
import it.cnr.isti.zigbee.ha.device.api.security_safety.IASControlAndIndicatingEquipment;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HAProfile;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.ha.driver.core.reflection.AbstractDeviceDescription;
import it.cnr.isti.zigbee.ha.driver.core.reflection.DeviceDescription;

import org.osgi.framework.BundleContext;

public class IASControlAndIndicatingEquipmentDevice extends HADeviceBase implements IASControlAndIndicatingEquipment {

	private Identify identify;
	private IASZone iasZone;
	private IASACE iasAce;
	private IASWD iasWD;
	private Scenes scenes;
	private Groups groups;

	public IASControlAndIndicatingEquipmentDevice(BundleContext ctx, ZigBeeDevice zbDevice) throws ZigBeeHAException {

		super(ctx, zbDevice);

		iasAce = (IASACE) addCluster(HAProfile.IAS_ACE);	
		iasZone = (IASZone) addCluster(HAProfile.IAS_ZONE);	
		identify = (Identify) addCluster(HAProfile.IDENTIFY);	
		iasWD = (IASWD) addCluster(HAProfile.IAS_WD);
		scenes = (Scenes) addCluster(HAProfile.SCENES);
		groups = (Groups) addCluster(HAProfile.GROUPS);
	}

	public IASACE getIASACE() {
		return iasAce;
	}

	public IASZone getIASZone() {
		return iasZone;
	}

	public IASWD getIASwd() {
		return iasWD;
	}

	public Scenes getScenes() {
		return scenes;
	}

	public Groups getGroups() {
		return groups;
	}
	
	public Identify getIdentify() {
		return identify;
	}

	@Override
	public String getName() {
		return IASControlAndIndicatingEquipment.NAME;
	}

	@Override
	public DeviceDescription getDescription() {

		return DEVICE_DESCRIPTOR;
	}

	final static DeviceDescription DEVICE_DESCRIPTOR =  new AbstractDeviceDescription(){

		public int[] getCustomClusters() {
			return IASControlAndIndicatingEquipment.CUSTOM;
		}

		public int[] getMandatoryCluster() {
			return IASControlAndIndicatingEquipment.MANDATORY;
		}

		public int[] getOptionalCluster() {
			return IASControlAndIndicatingEquipment.OPTIONAL;
		}

		public int[] getStandardClusters() {
			return IASControlAndIndicatingEquipment.STANDARD;
		}
	};
}