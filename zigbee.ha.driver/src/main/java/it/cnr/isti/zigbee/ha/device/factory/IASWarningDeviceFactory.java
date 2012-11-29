package it.cnr.isti.zigbee.ha.device.factory;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.device.api.security_safety.IAS_Warning;
import it.cnr.isti.zigbee.ha.device.impl.IAS_Warning_Device;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceFactoryBase;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;

import org.osgi.framework.BundleContext;

public class IASWarningDeviceFactory extends HADeviceFactoryBase {

	public IASWarningDeviceFactory(BundleContext ctx) {
		super(ctx, IAS_Warning.class);
	}

	private String[] clusters;
	private String[] implementedInterfaces = { 
			HADevice.class.getName(), 
			HADeviceBase.class.getName(),
			IAS_Warning.class.getName()
	};

	@Override
	public String getDeviceId() {
		return String.valueOf(IAS_Warning.DEVICE_ID);
	}

	@Override
	public String[] getRefinedInterfaces() {
		return implementedInterfaces;
	}

	@Override
	public HADeviceBase getInstance(ZigBeeDevice zbDevice) throws ZigBeeHAException {
		return new IAS_Warning_Device(ctx, zbDevice);
	}
}
