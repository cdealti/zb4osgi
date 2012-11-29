package it.cnr.isti.zigbee.ha.device.api.security_safety;

import it.cnr.isti.zigbee.ha.cluster.glue.general.Identify;
import it.cnr.isti.zigbee.ha.cluster.glue.security_safety.IASACE;
import it.cnr.isti.zigbee.ha.cluster.glue.security_safety.IASZone;
import it.cnr.isti.zigbee.ha.driver.ArraysUtil;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HAProfile;

public interface IASAncillaryControlEquipment extends HADevice {

	public static final int DEVICE_ID = 0x0401;
	public static final String NAME = "IAS Ancillary Control Equipment";
	public static final int[] MANDATORY = ArraysUtil.append(HADevice.MANDATORY, new int[]{
			HAProfile.IAS_ZONE, HAProfile.IDENTIFY, HAProfile.IAS_ACE
	});
	public static final int[] OPTIONAL = HADevice.OPTIONAL;
	public static final int[] STANDARD = ArraysUtil.append(MANDATORY, OPTIONAL);
	public static final int[] CUSTOM = {};

	public IASACE getIASACE();
	public Identify getIdentify();
	public IASZone getIASZone();
}