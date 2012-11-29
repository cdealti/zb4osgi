package it.cnr.isti.zigbee.ha.device.factory;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.device.api.lighting.OnOffLightSwitch;
import it.cnr.isti.zigbee.ha.device.impl.OnOffLightSwitchDevice;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceFactoryBase;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;

import org.osgi.framework.BundleContext;

public class OnOffLightSwitchFactory extends HADeviceFactoryBase {

	private String[] clusters;
	private String[] implementedInterfaces = { 
			HADevice.class.getName(), 
			HADeviceBase.class.getName(),
			OnOffLightSwitch.class.getName()
	};

	public OnOffLightSwitchFactory(BundleContext ctx) {
		super(ctx, OnOffLightSwitch.class);
	}

	@Override
	public String getDeviceId() {
		return String.valueOf(OnOffLightSwitch.DEVICE_ID);
	}

	@Override
	public String[] getRefinedInterfaces() {
		return implementedInterfaces;
	}

	@Override
	public HADeviceBase getInstance(ZigBeeDevice zbDevice) throws ZigBeeHAException {
		return new OnOffLightSwitchDevice(ctx, zbDevice);
	}
}