package it.cnr.isti.zigbee.ha.device.api.hvac;

import it.cnr.isti.zigbee.ha.cluster.glue.general.Alarms;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Groups;
import it.cnr.isti.zigbee.ha.cluster.glue.general.LevelControl;
import it.cnr.isti.zigbee.ha.cluster.glue.general.OnOff;
import it.cnr.isti.zigbee.ha.cluster.glue.general.Scenes;
import it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.TemperatureMeasurement;
import it.cnr.isti.zigbee.ha.driver.ArraysUtil;
import it.cnr.isti.zigbee.ha.driver.core.HADevice;
import it.cnr.isti.zigbee.ha.driver.core.HAProfile;

public interface Pump extends HADevice {

	public static final int DEVICE_ID = 0x0303;
	public static final String NAME = "Pump";

	public static final int[] MANDATORY = ArraysUtil.append(HADevice.MANDATORY, new int[]{
			/* TODO HAProfile.PUMP_CONFIGURATION_AND_CONTROL, */HAProfile.ON_OFF, HAProfile.SCENES, HAProfile.GROUPS
	});

	public static final int[] OPTIONAL = ArraysUtil.append(HADevice.OPTIONAL, new int[]{
			HAProfile.LEVEL_CONTROL, HAProfile.ALARMS, HAProfile.TEMPERATURE_MEASUREMENT, /* TODO HAProfile.PRESSURE_MEASUREMENT, HAProfile.FLOW_MEASUREMENT*/
	});

	public static final int[] STANDARD = ArraysUtil.append(MANDATORY, OPTIONAL);
	public static final int[] CUSTOM = {};

	/**
	 * Access method for the <b>Mandatory</b> cluster: {@link TemperatureMeasurement} 
	 *  
	 * @return the {@link TemperatureMeasurement} cluster object
	 */
	public OnOff getOnOff();

	public Scenes getScenes();

	public Groups getGroups();

	public LevelControl getLevelControl();

	public Alarms getAlarms();

	public TemperatureMeasurement getTemperatureMeasurement();

	//public PUMP_CONFIGURATION_AND_CONTROL getPUMP_CONFIGURATION_AND_CONTROL();

	//public PRESSURE_MEASUREMENT getPRESSURE_MEASUREMENT();

	//public FLOW_MEASUREMENT getFLOW_MEASUREMENT();
}