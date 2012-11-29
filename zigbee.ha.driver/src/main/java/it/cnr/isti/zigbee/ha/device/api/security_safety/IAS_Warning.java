package it.cnr.isti.zigbee.ha.device.api.security_safety;

import it.cnr.isti.zigbee.ha.cluster.glue.security_safety.IASWD;
import it.cnr.isti.zigbee.ha.cluster.glue.security_safety.IASZone;
import it.cnr.isti.zigbee.ha.driver.ArraysUtil;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HAProfile;

public interface IAS_Warning {

	public static final int DEVICE_ID = 0x0403;
	public static final String NAME = "IAS Warning Device";
	public static final int[] MANDATORY = ArraysUtil.append(HADevice.MANDATORY, new int[]{
			HAProfile.IAS_WD, HAProfile.IAS_ZONE
	});
	public static final int[] OPTIONAL = ArraysUtil.append(HADevice.OPTIONAL, new int[]{
			HAProfile.SCENES, HAProfile.GROUPS
	});
	public static final int[] STANDARD = ArraysUtil.append(MANDATORY, OPTIONAL);
	public static final int[] CUSTOM = {};

	public IASWD getIASWD();
	public IASZone getIASZone();
}