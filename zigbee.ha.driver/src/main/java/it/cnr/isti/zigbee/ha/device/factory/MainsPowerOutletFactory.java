package it.cnr.isti.zigbee.ha.device.factory;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.device.api.generic.MainsPowerOutlet;
import it.cnr.isti.zigbee.ha.device.impl.MainsPowerOutletDevice;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceFactoryBase;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;

import org.osgi.framework.BundleContext;

public class MainsPowerOutletFactory extends HADeviceFactoryBase {

	public MainsPowerOutletFactory(BundleContext ctx) {
		super(ctx, MainsPowerOutlet.class);
	}

	private String[] clusters;
	private String[] implementedInterfaces = { 
			HADevice.class.getName(), 
			HADeviceBase.class.getName(),
			MainsPowerOutlet.class.getName()
	};

	@Override
	public String getDeviceId() {
		return String.valueOf(MainsPowerOutlet.DEVICE_ID);
	}

	@Override
	public String[] getRefinedInterfaces() {
		return implementedInterfaces;
	}

	@Override
	public HADeviceBase getInstance(ZigBeeDevice zbDevice) throws ZigBeeHAException {
		return new MainsPowerOutletDevice(ctx, zbDevice);
	}
}