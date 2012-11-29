package it.cnr.isti.zigbee.ha.device.factory;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.device.api.security_safety.IAS_Zone;
import it.cnr.isti.zigbee.ha.device.impl.IAS_ZoneDevice;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceFactoryBase;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;

import org.osgi.framework.BundleContext;

public class IAS_ZoneFactory extends HADeviceFactoryBase {

	public IAS_ZoneFactory(BundleContext ctx) {
		super(ctx, IAS_Zone.class);
	}

	private String[] clusters;
	private String[] implementedInterfaces = { 
			HADevice.class.getName(), 
			HADeviceBase.class.getName(),
			IAS_Zone.class.getName()
	};
	
	@Override
	public String getDeviceId() {
		return String.valueOf(IAS_Zone.DEVICE_ID);
	}
	@Override
	public String[] getRefinedInterfaces() {
		return implementedInterfaces;
	}
	@Override
	public HADeviceBase getInstance(ZigBeeDevice zbDevice) throws ZigBeeHAException {
		return new IAS_ZoneDevice(ctx, zbDevice);
	}
}