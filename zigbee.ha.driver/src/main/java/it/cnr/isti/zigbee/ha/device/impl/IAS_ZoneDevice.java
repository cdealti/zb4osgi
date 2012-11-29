package it.cnr.isti.zigbee.ha.device.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.cluster.glue.security_safety.IASZone;
import it.cnr.isti.zigbee.ha.device.api.security_safety.IAS_Zone;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HAProfile;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.ha.driver.core.reflection.AbstractDeviceDescription;
import it.cnr.isti.zigbee.ha.driver.core.reflection.DeviceDescription;

import org.osgi.framework.BundleContext;

public class IAS_ZoneDevice extends HADeviceBase implements IAS_Zone {

	private IASZone iasZoneCluster;

	public IAS_ZoneDevice(BundleContext ctx, ZigBeeDevice zbDevice) throws ZigBeeHAException {

		super(ctx, zbDevice);
		
		iasZoneCluster = (IASZone) addCluster(HAProfile.IAS_ZONE);
	}

	public IASZone getIASZone() {

		return iasZoneCluster;
	}

	@Override
	public String getName() {

		return IAS_Zone.NAME;
	}

	@Override
	public DeviceDescription getDescription() {

		return DEVICE_DESCRIPTOR;
	}

	final static DeviceDescription DEVICE_DESCRIPTOR =  new AbstractDeviceDescription(){

		public int[] getCustomClusters() {
			return IAS_Zone.CUSTOM;
		}

		public int[] getMandatoryCluster() {
			return IAS_Zone.MANDATORY;
		}

		public int[] getOptionalCluster() {
			return IAS_Zone.OPTIONAL;
		}

		public int[] getStandardClusters() {
			return IAS_Zone.STANDARD;
		}
	};
}