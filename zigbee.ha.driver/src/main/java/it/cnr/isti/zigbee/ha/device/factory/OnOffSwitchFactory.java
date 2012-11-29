package it.cnr.isti.zigbee.ha.device.factory;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.device.api.generic.OnOffSwitch;
import it.cnr.isti.zigbee.ha.device.impl.OnOffSwitchDevice;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceFactoryBase;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;

import org.osgi.framework.BundleContext;

public class OnOffSwitchFactory extends HADeviceFactoryBase {

	public OnOffSwitchFactory(BundleContext ctx) {
		super(ctx, OnOffSwitch.class);
	}

	private String[] clusters;
	private String[] implementedInterfaces = { 
			HADevice.class.getName(), 
			HADeviceBase.class.getName(),
			OnOffSwitch.class.getName()
	};
	@Override
	public String getDeviceId() {
		return String.valueOf(OnOffSwitch.DEVICE_ID);
	}
	@Override
	public String[] getRefinedInterfaces() {
		return implementedInterfaces;
	}
	@Override
	public HADeviceBase getInstance(ZigBeeDevice zbDevice) throws ZigBeeHAException {
		return new OnOffSwitchDevice(ctx, zbDevice);
	}
}