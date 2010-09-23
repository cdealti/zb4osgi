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

package it.cnr.isti.zigbee.ha.driver.core;

import it.cnr.isti.zigbee.ha.device.api.generic.OnOffOutput;
import it.cnr.isti.zigbee.ha.device.api.hvac.TemperatureSensor;
import it.cnr.isti.zigbee.ha.device.api.lighting.DimmableLight;
import it.cnr.isti.zigbee.ha.device.api.lighting.OccupancySensor;
import it.cnr.isti.zigbee.ha.device.api.lighting.OnOffLight;
import it.cnr.isti.zigbee.ha.device.api.lighting.OnOffLightSwitch;
import it.cnr.isti.zigbee.zcl.library.api.general.Alarms;
import it.cnr.isti.zigbee.zcl.library.api.general.Basic;
import it.cnr.isti.zigbee.zcl.library.api.general.DeviceTemperatureConfiguration;
import it.cnr.isti.zigbee.zcl.library.api.general.Groups;
import it.cnr.isti.zigbee.zcl.library.api.general.Identify;
import it.cnr.isti.zigbee.zcl.library.api.general.LevelControl;
import it.cnr.isti.zigbee.zcl.library.api.general.OnOff;
import it.cnr.isti.zigbee.zcl.library.api.general.OnOffSwitchConfiguration;
import it.cnr.isti.zigbee.zcl.library.api.general.PowerConfiguration;
import it.cnr.isti.zigbee.zcl.library.api.general.Scenes;
import it.cnr.isti.zigbee.zcl.library.api.general.Time;
import it.cnr.isti.zigbee.zcl.library.api.measureament_sensing.OccupacySensing;
import it.cnr.isti.zigbee.zcl.library.api.measureament_sensing.RelativeHumidityMeasurement;
import it.cnr.isti.zigbee.zcl.library.api.measureament_sensing.TemperatureMeasurement;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.IASWD;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.IASZone;


/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 *         
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.4.0
 *
 */
public class  HAProfile {

	public static final int ID = 260;

	//Generic
	public static final int BASIC = Basic.ID;
	public static final int IDENTIFY = Identify.ID;
	public static final int GROUPS = Groups.ID;
	public static final int SCENES = Scenes.ID;
	public static final int ON_OFF = OnOff.ID;
	public static final int TIME = Time.ID;
	public static final int LEVEL_CONTROL = LevelControl.ID;
	public static final int ONOFF_OUTPUT = OnOffOutput.DEVICE_ID;
	public static final int ON_OFF_SWITCH_CONFIGURATION = OnOffSwitchConfiguration.ID;
	public static final int ALARMS = Alarms.ID;
	public static final int POWER_CONFIGURATION = PowerConfiguration.ID;
	public static final int DEVICE_TEMPERATURE_CONFIGURATION = DeviceTemperatureConfiguration.ID;
	
	//Measureament & Sensing
	public static final int TEMPERATURE_MEASUREMENT = TemperatureMeasurement.ID;
	public static final int OCCUPANCY_SENSING = OccupacySensing.ID;
	public static final int RELATIVE_HUMIDITY_MEASUREMENT = RelativeHumidityMeasurement.ID;
	
	//Lighting
	public static final int ONOFF_LIGHT = OnOffLight.DEVICE_ID; 
	public static final int DIMMABLE_LIGHT = DimmableLight.DEVICE_ID;
	public static final int ONOFF_LIGHT_SWITCH = OnOffLightSwitch.DEVICE_ID;
	public static final int OCCUPANCY_SENSOR = OccupancySensor.DEVICE_ID;

	//HVAC
	public static final int TEMPERATURE_SENSOR = TemperatureSensor.DEVICE_ID;
	
	//Security & Safety
	public static final int IAS_ZONE = IASZone.ID;
	public static final int IAS_WD = IASWD.ID;

}
