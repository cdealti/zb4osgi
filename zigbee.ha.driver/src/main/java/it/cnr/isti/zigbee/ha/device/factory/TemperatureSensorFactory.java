package it.cnr.isti.zigbee.ha.device.factory;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.device.api.hvac.TemperatureSensor;
import it.cnr.isti.zigbee.ha.device.impl.TemperatureSensorDevice;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceFactoryBase;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;

import org.osgi.framework.BundleContext;

public class TemperatureSensorFactory extends HADeviceFactoryBase {

	private String[] clusters;
	private String[] implementedInterfaces = { 
			HADevice.class.getName(), 
			HADeviceBase.class.getName(),
			TemperatureSensor.class.getName()
	};

	public TemperatureSensorFactory(BundleContext ctx) {
		super(ctx, TemperatureSensor.class);
	}

	@Override
	public String getDeviceId() {
		return String.valueOf(TemperatureSensor.DEVICE_ID);
	}

	@Override
	public String[] getRefinedInterfaces() {
		return implementedInterfaces;
	}

	@Override
	public HADeviceBase getInstance(ZigBeeDevice zbDevice) throws ZigBeeHAException {
		return new TemperatureSensorDevice(ctx, zbDevice);
	}
}