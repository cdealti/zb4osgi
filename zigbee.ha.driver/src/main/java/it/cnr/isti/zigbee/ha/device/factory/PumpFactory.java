package it.cnr.isti.zigbee.ha.device.factory;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.device.api.hvac.Pump;
import it.cnr.isti.zigbee.ha.device.impl.PumpDevice;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceFactoryBase;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;

import org.osgi.framework.BundleContext;

public class PumpFactory extends HADeviceFactoryBase {

	public PumpFactory(BundleContext ctx) {
		super(ctx, Pump.class);
	}

	private String[] clusters;
	private String[] implementedInterfaces = { 
			HADevice.class.getName(), 
			HADeviceBase.class.getName(),
			Pump.class.getName()
	};

	@Override
	public String getDeviceId() {
		return String.valueOf(Pump.DEVICE_ID);
	}

	@Override
	public String[] getRefinedInterfaces() {
		return implementedInterfaces;
	}

	@Override
	public HADeviceBase getInstance(ZigBeeDevice zbDevice) throws ZigBeeHAException {
		return new PumpDevice(ctx, zbDevice);
	}
}
