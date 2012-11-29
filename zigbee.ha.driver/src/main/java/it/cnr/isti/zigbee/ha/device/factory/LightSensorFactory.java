package it.cnr.isti.zigbee.ha.device.factory;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.device.api.lighting.LightSensor;
import it.cnr.isti.zigbee.ha.device.impl.LightSensorDevice;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceFactoryBase;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;

import org.osgi.framework.BundleContext;

public class LightSensorFactory extends HADeviceFactoryBase {

	public LightSensorFactory(BundleContext ctx) {
		super(ctx, LightSensor.class);	
	}

	private String[] clusters;
	private String[] implementedInterfaces = { 
			HADevice.class.getName(), 
			HADeviceBase.class.getName(),
			LightSensor.class.getName()
	};

	@Override
	public String getDeviceId() {
		return String.valueOf(LightSensor.DEVICE_ID);
	}

	@Override
	public String[] getRefinedInterfaces() {
		return implementedInterfaces;
	}

	@Override
	public HADeviceBase getInstance(ZigBeeDevice zbDevice) throws ZigBeeHAException {
		return new LightSensorDevice(ctx, zbDevice);
	}
}