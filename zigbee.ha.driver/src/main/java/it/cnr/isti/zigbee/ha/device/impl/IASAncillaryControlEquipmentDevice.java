package it.cnr.isti.zigbee.ha.device.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Identify;
import it.cnr.isti.zigbee.ha.cluster.glue.security_safety.IASACE;
import it.cnr.isti.zigbee.ha.cluster.glue.security_safety.IASZone;
import it.cnr.isti.zigbee.ha.device.api.security_safety.IASAncillaryControlEquipment;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HAProfile;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.ha.driver.core.reflection.AbstractDeviceDescription;
import it.cnr.isti.zigbee.ha.driver.core.reflection.DeviceDescription;

import org.osgi.framework.BundleContext;

public class IASAncillaryControlEquipmentDevice extends HADeviceBase implements IASAncillaryControlEquipment{

	private Identify identify;
	private IASZone iasZone;
	private IASACE iasAce;

	public IASAncillaryControlEquipmentDevice(BundleContext ctx, ZigBeeDevice zbDevice) throws ZigBeeHAException {

		super(ctx, zbDevice);
		iasAce = (IASACE) addCluster(HAProfile.IAS_ACE);	
		iasZone = (IASZone) addCluster(HAProfile.IAS_ZONE);	
		identify = (Identify) addCluster(HAProfile.IDENTIFY);	
	}

	public IASACE getIASACE() {
		return iasAce;
	}

	public IASZone getIASZone() {
		return iasZone;
	}

	/*public Identify getIdentify(){
		return identify;
	}*/

	@Override
	public String getName() {
		return IASAncillaryControlEquipment.NAME;
	}

	@Override
	public DeviceDescription getDescription() {

		return DEVICE_DESCRIPTOR;
	}

	final static DeviceDescription DEVICE_DESCRIPTOR =  new AbstractDeviceDescription(){

		public int[] getCustomClusters() {
			return IASAncillaryControlEquipment.CUSTOM;
		}

		public int[] getMandatoryCluster() {
			return IASAncillaryControlEquipment.MANDATORY;
		}

		public int[] getOptionalCluster() {
			return IASAncillaryControlEquipment.OPTIONAL;
		}

		public int[] getStandardClusters() {
			return IASAncillaryControlEquipment.STANDARD;
		}
	};
}