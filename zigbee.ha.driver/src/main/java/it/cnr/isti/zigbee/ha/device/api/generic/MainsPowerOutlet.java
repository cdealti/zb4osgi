package it.cnr.isti.zigbee.ha.device.api.generic;

import it.cnr.isti.zigbee.ha.cluster.glue.general.Groups;
import it.cnr.isti.zigbee.ha.cluster.glue.general.OnOff;
import it.cnr.isti.zigbee.ha.cluster.glue.general.OnOffSwitchConfiguration;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Scenes;
import it.cnr.isti.zigbee.ha.driver.ArraysUtil;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HAProfile;

public interface MainsPowerOutlet extends HADevice {

	public static final int DEVICE_ID = 0x0009;
	public static final String NAME = "Mains Power Outlet";
	public static final int[] MANDATORY = ArraysUtil.append(HADevice.MANDATORY, new int[]{
			HAProfile.ON_OFF, HAProfile.SCENES, HAProfile.GROUPS
	});
	public static final int[] OPTIONAL = HADevice.OPTIONAL;
	public static final int[] STANDARD = ArraysUtil.append(MANDATORY, OPTIONAL);
	public static final int[] CUSTOM = {};


	/**
	 * Access method for the <b>Mandatory</b> cluster: {@link OnOffSwitchConfiguration} 
	 *  
	 * @return the {@link OnOffSwitchConfiguration} cluster object
	 */
	public OnOff getOnOff();
	
	public Scenes getScenes();
	
	public Groups getGroups();
}