package it.cnr.isti.zigbee.ha.device.api.lighting;

import it.cnr.isti.zigbee.ha.cluster.glue.general.Groups;
import it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.IlluminanceMeasurement;
import it.cnr.isti.zigbee.ha.driver.ArraysUtil;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HAProfile;

public interface LightSensor extends HADevice {

	public static final int DEVICE_ID = 0x0106;
	public static final String NAME = "Light Sensor";
	public static final int[] MANDATORY = ArraysUtil.append(HADevice.MANDATORY, new int[]{
			HAProfile.ILLUMINANCE_MEASUREMENT
	});
	public static final int[] OPTIONAL = ArraysUtil.append(HADevice.OPTIONAL, new int[]{
			HAProfile.GROUPS
	});
	public static final int[] STANDARD = ArraysUtil.append(MANDATORY, OPTIONAL);
	public static final int[] CUSTOM = {};

	public IlluminanceMeasurement getIlluminanceMeasurement();
	
	public Groups getGroups();
}
