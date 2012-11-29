package it.cnr.isti.zigbee.ha.device.factory;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.device.api.generic.OnOffOutput;
import it.cnr.isti.zigbee.ha.device.impl.OnOffOutputDevice;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceFactoryBase;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;

import org.osgi.framework.BundleContext;

public class OnOffOutputFactory extends HADeviceFactoryBase {

	public OnOffOutputFactory(BundleContext ctx) {

		super(ctx, OnOffOutput.class);
	}

	private String[] clusters;
	private String[] implementedInterfaces = { 
			HADevice.class.getName(), 
			HADeviceBase.class.getName(),
			OnOffOutput.class.getName()
	};

	@Override
	public String getDeviceId() {
		return String.valueOf(OnOffOutput.DEVICE_ID);
	}

	@Override
	public String[] getRefinedInterfaces() {
		return implementedInterfaces;
	}

	@Override
	public HADeviceBase getInstance(ZigBeeDevice zbDevice) throws ZigBeeHAException {
		return new OnOffOutputDevice(ctx, zbDevice);
	}
}