/*
   Copyright 2008-2010 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies 
   of the Italian National Research Council 


   See the NOTICE file distributed with this work for additional 
   information regarding copyright ownership

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package it.cnr.isti.zigbee.ha.device.api.lighting;

import it.cnr.isti.zigbee.ha.cluster.glue.general.Groups;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Identify;
import it.cnr.isti.zigbee.ha.cluster.glue.general.OnOff;
import it.cnr.isti.zigbee.ha.cluster.glue.general.OnOffSwitchConfiguration;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Scenes;
import it.cnr.isti.zigbee.ha.driver.ArraysUtil;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HAProfile;



/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public interface OnOffLightSwitch extends HADevice{

	public static final int DEVICE_ID = 0x0103;
	public static final String NAME = "OnOff Light Switch";
	public static final int[] MANDATORY = ArraysUtil.append(HADevice.MANDATORY, new int[]{
			HAProfile.ON_OFF_SWITCH_CONFIGURATION, HAProfile.ON_OFF, HAProfile.SCENES, HAProfile.GROUPS, HAProfile.IDENTIFY
	});
	public static final int[] OPTIONAL = HADevice.OPTIONAL;
	public static final int[] STANDARD = ArraysUtil.append(MANDATORY, OPTIONAL);
	public static final int[] CUSTOM = {};

	/**
	 * Access method for the <b>Mandatory</b> cluster: {@link OnOffSwitchConfiguration} 
	 *  
	 * @return the {@link OnOffSwitchConfiguration} cluster object
	 */
	public OnOffSwitchConfiguration getOnOffSwitchConfiguration();

	public OnOff getOnOff();

	public Scenes getScenes();

	public Groups getGroups();

	public Identify getIdentify();
}