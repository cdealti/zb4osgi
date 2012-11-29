package it.cnr.isti.zigbee.ha.device.factory;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.device.api.generic.LevelControlSwitch;
import it.cnr.isti.zigbee.ha.device.impl.LevelControlSwitchDevice;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceFactoryBase;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;

import org.osgi.framework.BundleContext;

public class LevelControlSwitchFactory extends HADeviceFactoryBase {

	public LevelControlSwitchFactory(BundleContext ctx) {
		super(ctx, LevelControlSwitch.class);
	}

	private String[] clusters;
	private String[] implementedInterfaces = { 
			HADevice.class.getName(), 
			HADeviceBase.class.getName(),
			LevelControlSwitch.class.getName()
	};

	@Override
	public String getDeviceId() {
		return String.valueOf(LevelControlSwitch.DEVICE_ID);
	}

	@Override
	public String[] getRefinedInterfaces() {
		return implementedInterfaces;
	}

	@Override
	public HADeviceBase getInstance(ZigBeeDevice zbDevice) throws ZigBeeHAException {
		return new LevelControlSwitchDevice(ctx, zbDevice);
	}
}