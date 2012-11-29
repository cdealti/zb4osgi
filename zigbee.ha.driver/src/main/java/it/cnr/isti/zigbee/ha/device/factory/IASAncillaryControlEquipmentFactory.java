package it.cnr.isti.zigbee.ha.device.factory;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.device.api.security_safety.IASAncillaryControlEquipment;
import it.cnr.isti.zigbee.ha.device.impl.IASAncillaryControlEquipmentDevice;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceFactoryBase;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;

import org.osgi.framework.BundleContext;

public class IASAncillaryControlEquipmentFactory extends HADeviceFactoryBase {

	public IASAncillaryControlEquipmentFactory(BundleContext ctx) {

		super(ctx, IASAncillaryControlEquipment.class);
	}

	private String[] clusters;
	private String[] implementedInterfaces = { 
			HADevice.class.getName(), 
			HADeviceBase.class.getName(),
			IASAncillaryControlEquipment.class.getName()
	};

	@Override
	public String getDeviceId() {
		return String.valueOf(IASAncillaryControlEquipment.DEVICE_ID);
	}

	@Override
	public String[] getRefinedInterfaces() {
		return implementedInterfaces;
	}

	@Override
	public HADeviceBase getInstance(ZigBeeDevice zbDevice) throws ZigBeeHAException {
		return new IASAncillaryControlEquipmentDevice(ctx, zbDevice);
	}
}