package it.cnr.isti.zigbee.ha.device.api.security_safety;

import it.cnr.isti.zigbee.ha.cluster.glue.general.Groups;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Identify;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Scenes;
import it.cnr.isti.zigbee.ha.cluster.glue.security_safety.IASACE;
import it.cnr.isti.zigbee.ha.cluster.glue.security_safety.IASWD;
import it.cnr.isti.zigbee.ha.cluster.glue.security_safety.IASZone;
import it.cnr.isti.zigbee.ha.driver.ArraysUtil;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HAProfile;

public interface IASControlAndIndicatingEquipment extends HADevice {

	public static final int DEVICE_ID = 0x0400;
	public static final String NAME = "IAS Control and Indicating Equipment";
	public static final int[] MANDATORY = ArraysUtil.append(HADevice.MANDATORY, new int[]{
			HAProfile.IAS_ZONE, HAProfile.IDENTIFY, HAProfile.IAS_ACE, HAProfile.IAS_WD
	});
	public static final int[] OPTIONAL =  ArraysUtil.append(HADevice.OPTIONAL, new int[]{
			HAProfile.SCENES, HAProfile.GROUPS
	});
	public static final int[] STANDARD = ArraysUtil.append(MANDATORY, OPTIONAL);
	public static final int[] CUSTOM = {};

	public IASACE getIASACE();
	public Identify getIdentify();
	public IASZone getIASZone();
	public IASWD getIASwd();
	public Scenes getScenes();
	public Groups getGroups();
}