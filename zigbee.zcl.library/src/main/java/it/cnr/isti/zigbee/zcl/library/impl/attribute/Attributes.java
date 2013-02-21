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

package it.cnr.isti.zigbee.zcl.library.impl.attribute;

import it.cnr.isti.zigbee.zcl.library.impl.core.ZigBeeType;
/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public class Attributes  {

	final static public  AttributeDescriptor ON_OFF = new AbstractAttribute()
	.setId(0x0000)
	.setName("OnOff")
	.setReportable(true)
	.setType(Boolean.class)
	.setZigBeeType(ZigBeeType.Boolean)
	.setWritable(false);

	final static public    AttributeDescriptor ZCL_VERSION = new AbstractAttribute()
	.setId(0x0000)
	.setName("ZCLVersion")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor APPLICATION_VERSION = new AbstractAttribute()
	.setId(0x0001)
	.setName("ApplicationVersion")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor STACK_VERSION = new AbstractAttribute()
	.setId(0x0002)
	.setName("StackVersion")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor HW_VERSION = new AbstractAttribute()
	.setId(0x0003)
	.setName("HWVersion")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor MANUFACTURER_NAME = new AbstractAttribute()
	.setId(0x0004)
	.setName("ManufacturerName")
	.setReportable(false)
	.setType(String.class)
	.setZigBeeType(ZigBeeType.CharacterString)
	.setWritable(false);
	
	final static public   AttributeDescriptor MODEL_IDENTIFIER = new AbstractAttribute()
	.setId(0x0005)
	.setName("ModelIdentifier")
	.setReportable(false)
	.setType(String.class)
	.setZigBeeType(ZigBeeType.CharacterString)
	.setWritable(false);
	
	final static public   AttributeDescriptor DATE_CODE = new AbstractAttribute()
	.setId(0x0006)
	.setName("DateCode")
	.setReportable(false)
	.setType(String.class)
	.setZigBeeType(ZigBeeType.CharacterString)
	.setWritable(false);
	
	final static public   AttributeDescriptor POWER_SOURCE = new AbstractAttribute()
	.setId(0x0007)
	.setName("PowerSource")
	.setReportable(false)
	.setType(Byte.class)
	.setZigBeeType(ZigBeeType.Enumeration8bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor LOCATION_DESCRIPTION = new AbstractAttribute()
	.setId(0x0010)
	.setName("LocationDescription")
	.setReportable(false)
	.setType(String.class)
	.setZigBeeType(ZigBeeType.CharacterString)
	.setWritable(true);
	
	final static public   AttributeDescriptor PHYSICAL_ENVIRONMENT = new AbstractAttribute()
	.setId(0x0011)
	.setName("PhysicalEnvironment")
	.setReportable(false)
	.setType(Byte.class)
	.setZigBeeType(ZigBeeType.Enumeration8bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor DEVICE_ENABLED = new AbstractAttribute()
	.setId(0x0012)
	.setName("DeviceEnabled")
	.setReportable(false)
	.setType(Byte.class)
	.setZigBeeType(ZigBeeType.Boolean)
	.setWritable(true);
	
	final static public   AttributeDescriptor ALARM_MASK = new AbstractAttribute()
	.setId(0x0013)
	.setName("AlarmMask")
	.setReportable(false)
	.setType(ZigBeeType.Bitmap8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.Bitmap8bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor DISABLE_LOCAL_CONFIG = new AbstractAttribute()
	.setId(0x0014)
	.setName("DisableLocalConfig")
	.setReportable(false)
	.setType(ZigBeeType.Bitmap8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.Bitmap8bit)
	.setWritable(true);

	
	final static public   AttributeDescriptor IDENTIFY_TIME = new AbstractAttribute()
	.setId(0x0000)
	.setName("IdentifyTime")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor SCENE_COUNT = new AbstractAttribute()
	.setId(0x0000)
	.setName("SceneCount")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor CURRENT_SCENE = new AbstractAttribute()
	.setId(0x0001)
	.setName("CurrentScene")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(false);

	final static public   AttributeDescriptor CURRENT_GROUP = new AbstractAttribute()
	.setId(0x0002)
	.setName("CurrentGroup")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor SCENE_VALID = new AbstractAttribute()
	.setId(0x0003)
	.setName("SceneValid")
	.setReportable(false)
	.setType(ZigBeeType.Boolean.getJavaClass())
	.setZigBeeType(ZigBeeType.Boolean)
	.setWritable(false);
	
	final static public   AttributeDescriptor LAST_CONFIGURED_BY = new AbstractAttribute()
	.setId(0x0005)
	.setName("LastConfiguredBy")
	.setReportable(false)
	.setType(ZigBeeType.IEEEAddress.getJavaClass())
	.setZigBeeType(ZigBeeType.IEEEAddress)
	.setWritable(false);
	
	final static public   AttributeDescriptor NAME_SUPPORT_GROUPS = new AbstractAttribute()
	.setId(0x0000)
	.setName("NameSupport")
	.setReportable(false)
	.setType(ZigBeeType.Bitmap8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.Bitmap8bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor NAME_SUPPORT_SCENES = new AbstractAttribute()
	.setId(0x0004)
	.setName("NameSupport")
	.setReportable(false)
	.setType(ZigBeeType.Bitmap8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.Bitmap8bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor OCCUPANCY = new AbstractAttribute()
	.setId(0x0000)
	.setName("Occupancy")
	.setReportable(true)
	.setType(ZigBeeType.Bitmap8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.Bitmap8bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor OCCUPANCY_SENSOR_TYPE = new AbstractAttribute()
	.setId(0x0001)
	.setName("OccupancySensorType")
	.setReportable(false)
	.setType(ZigBeeType.Enumeration8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.Enumeration8bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor PIR_OCCUPIED_TO_UNOCCUPIED_DELAY = new AbstractAttribute()
	.setId(0x0010)
	.setName("PIROccupiedToUnoccupiedDelay")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor PIR_UNOCCUPIED_TO_OCCUPIED_DELAY = new AbstractAttribute()
	.setId(0x0011)
	.setName("PIRUnoccupiedToOccupiedDelay")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(true);

	final static public   AttributeDescriptor PIR_UNOCCUPIED_TO_OCCUPIED_THRESHOLD = new AbstractAttribute()
	.setId(0x0012)
	.setName("PIRUnoccupiedToOccupiedThreshold")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor ULTRA_SONIC_OCCUPIED_TO_UNOCCUPIED_DELAY = new AbstractAttribute()
	.setId(0x0020)
	.setName("UltraSonicOccupiedToUnoccupiedDelay")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor ULTRA_SONIC_UNOCCUPIED_TO_OCCUPIED_DELAY = new AbstractAttribute()
	.setId(0x0021)
	.setName("UltraSonicUnoccupiedToOccupiedDelay")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(true);

	final static public   AttributeDescriptor ULTRASONIC_UNOCCUPIED_TO_OCCUPIED_THRESHOLD = new AbstractAttribute()
	.setId(0x0022)
	.setName("UltrasonicUnoccupiedToOccupiedThreshold")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor MAINS_VOLTAGE = new AbstractAttribute()
	.setId(0x0000)
	.setName("MainsVoltage")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor MAINS_FREQUENCY = new AbstractAttribute()
	.setId(0x0001)
	.setName("MainsFrequency")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor MAINS_ALARM_MASK = new AbstractAttribute()
	.setId(0x0010)
	.setName("MainsAlarmMask")
	.setReportable(false)
	.setType(ZigBeeType.Bitmap8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.Bitmap8bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor MAINS_VOLTAGE_MIN_THRESHOLD = new AbstractAttribute()
	.setId(0x0011)
	.setName("MainsVoltageMinThreshold")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor MAINS_VOLTAGE_MAX_THRESHOLD = new AbstractAttribute()
	.setId(0x0012)
	.setName("MainsVoltageMaxThreshold")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor MAINS_VOLTAGE_DWELL_TRIP_POINT = new AbstractAttribute()
	.setId(0x0013)
	.setName("MainsVoltageDwellTripPoint")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor BATTERY_VOLTAGE = new AbstractAttribute()
	.setId(0x0020)
	.setName("BatteryVoltage")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor BATTERY_MANUFACTURER = new AbstractAttribute()
	.setId(0x0030)
	.setName("BatteryManufacturer")
	.setReportable(false)
	.setType(ZigBeeType.CharacterString.getJavaClass())
	.setZigBeeType(ZigBeeType.CharacterString)
	.setWritable(true);

	final static public   AttributeDescriptor BATTERY_SIZE = new AbstractAttribute()
	.setId(0x0031)
	.setName("BatterySize")
	.setReportable(false)
	.setType(ZigBeeType.Enumeration8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.Enumeration8bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor BATTERY_AHr_RATING = new AbstractAttribute()
	.setId(0x0032)
	.setName("BatteryAHrRating")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor BATTERY_QUANTITY = new AbstractAttribute()
	.setId(0x0033)
	.setName("BatteryQuantity")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor BATTERY_RATED_VOLTAGE = new AbstractAttribute()
	.setId(0x0034)
	.setName("BatteryRatedVoltage")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor BATTERY_ALARM_MASK = new AbstractAttribute()
	.setId(0x0035)
	.setName("BatteryAlarmMask")
	.setReportable(false)
	.setType(ZigBeeType.Bitmap8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.Bitmap8bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor BATTERY_VOLTAGE_MIN_THRESHOLD = new AbstractAttribute()
	.setId(0x0036)
	.setName("BatteryVoltageMinThreshold")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor CURRENT_TEMPERATURE = new AbstractAttribute()
	.setId(0x0000)
	.setName("CurrentTemperature")
	.setReportable(false)
	.setType(ZigBeeType.SignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.SignedInteger16bit)
	.setWritable(false);

	final static public   AttributeDescriptor MIN_TEMP_EXPERIENCED = new AbstractAttribute()
	.setId(0x0001)
	.setName("MinTempExperienced")
	.setReportable(false)
	.setType(ZigBeeType.SignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.SignedInteger16bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor MAX_TEMP_EXPERIENCED = new AbstractAttribute()
	.setId(0x0002)
	.setName("MaxTempExperienced")
	.setReportable(false)
	.setType(ZigBeeType.SignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.SignedInteger16bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor OVER_TEMP_TOTAL_DWELL = new AbstractAttribute()
	.setId(0x0003)
	.setName("OverTempTotalDwell")
	.setReportable(false)
	.setType(ZigBeeType.SignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.SignedInteger16bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor DEVICE_TEMP_ALARM_MASK = new AbstractAttribute()
	.setId(0x0010)
	.setName("DeviceTempAlarmMask")
	.setReportable(false)
	.setType(ZigBeeType.Bitmap8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.Bitmap8bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor LOW_TEMP_THRESHOLD = new AbstractAttribute()
	.setId(0x0011)
	.setName("LowTempThreshold")
	.setReportable(false)
	.setType(ZigBeeType.SignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.SignedInteger16bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor HIGH_TEMP_THRESHOLD = new AbstractAttribute()
	.setId(0x0012)
	.setName("HighTempThreshold")
	.setReportable(false)
	.setType(ZigBeeType.SignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.SignedInteger16bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor LOW_TEMP_DWELL_TRIP_POINT = new AbstractAttribute()
	.setId(0x0013)
	.setName("LowTempDwellTripPoint")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger24bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger24bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor HIGH_TEMP_DWELL_TRIP_POINT = new AbstractAttribute()
	.setId(0x0014)
	.setName("HighTempDwellTripPoint")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger24bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger24bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor SWITCH_TYPE = new AbstractAttribute()
	.setId(0x0000)
	.setName("SwitchType")
	.setReportable(false)
	.setType(ZigBeeType.Enumeration8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.Enumeration8bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor SWITCH_ACTIONS = new AbstractAttribute()
	.setId(0x0000)
	.setName("SwitchActions")
	.setReportable(false)
	.setType(ZigBeeType.Enumeration8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.Enumeration8bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor CURRENT_LEVEL = new AbstractAttribute()
	.setId(0x0000)
	.setName("CurrentLevel")
	.setReportable(true)
	.setType(ZigBeeType.UnsignedInteger8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor REMAINING_TIME = new AbstractAttribute()
	.setId(0x0001)
	.setName("RemainingTime")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);

	final static public   AttributeDescriptor ON_OFF_TRANSATION_TIME = new AbstractAttribute()
	.setId(0x0010)
	.setName("OnOffTransationTime")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor ON_LEVEL = new AbstractAttribute()
	.setId(0x0011)
	.setName("OnLevel")
	.setReportable(true)
	.setType(ZigBeeType.UnsignedInteger8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor TIME = new AbstractAttribute()
	.setId(0x0000)
	.setName("Time")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger32bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger32bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor TIME_STATUS = new AbstractAttribute()
	.setId(0x0001)
	.setName("TimeStatus")
	.setReportable(false)
	.setType(ZigBeeType.Bitmap8bit.getJavaClass())
	.setZigBeeType(ZigBeeType.Bitmap8bit)
	.setWritable(true);
	
	final static public   AttributeDescriptor MEASURED_VALUE_SIGNED_16_BIT = new AbstractAttribute()
	.setId(0x0000)
	.setName("MeasuredValue")
	.setReportable(true)
	.setType(ZigBeeType.SignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.SignedInteger16bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor MIN_MEASURED_VALUE_SIGNED_16_BIT = new AbstractAttribute()
	.setId(0x0001)
	.setName("MinMeasuredValue")
	.setReportable(false)
	.setType(ZigBeeType.SignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.SignedInteger16bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor MAX_MEASURED_VALUE_SIGNED_16_BIT = new AbstractAttribute()
	.setId(0x0002)
	.setName("MaxMeasuredValue")
	.setReportable(false)
	.setType(ZigBeeType.SignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.SignedInteger16bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor MEASURED_VALUE_UNSIGNED_16_BIT = new AbstractAttribute()
	.setId(0x0000)
	.setName("MeasuredValue")
	.setReportable(true)
	.setType(ZigBeeType.UnsignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor MIN_MEASURED_VALUE_UNSIGNED_16_BIT = new AbstractAttribute()
	.setId(0x0001)
	.setName("MinMeasuredValue")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor MAX_MEASURED_VALUE_UNSIGNED_16_BIT = new AbstractAttribute()
	.setId(0x0002)
	.setName("MaxMeasuredValue")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor TOLERANCE = new AbstractAttribute()
	.setId(0x0003)
	.setName("Tolerance")
	.setReportable(true)
	.setType(ZigBeeType.UnsignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);
	
	final static public   AttributeDescriptor ALLARM_COUNT = new AbstractAttribute()
	.setId(0x0000)
	.setName("AlarmCount")
	.setReportable(false)
	.setType(ZigBeeType.UnsignedInteger16bit.getJavaClass())
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);

	final static public   AttributeDescriptor LIGHT_SENSOR_TYPE = new AbstractAttribute()
	.setId(0x0004)
	.setName("LightSensorType")
	.setReportable(false)
	.setType(Byte.class)
	.setZigBeeType(ZigBeeType.Enumeration8bit)
	.setWritable(false);

	final static public   AttributeDescriptor ZONE_STATE = new AbstractAttribute()
	.setId(0x0000)
	.setName("ZoneState")
	.setReportable(false)
	.setType(Byte.class)
	.setZigBeeType(ZigBeeType.Enumeration8bit)
	.setWritable(false);

	final static public   AttributeDescriptor ZONE_TYPE = new AbstractAttribute()
	.setId(0x0001)
	.setName("ZoneType")
	.setReportable(false)
	.setType(Byte.class)
	.setZigBeeType(ZigBeeType.Enumeration16bit)
	.setWritable(false);

	final static public   AttributeDescriptor ZONE_STATUS = new AbstractAttribute()
	.setId(0x0002)
	.setName("ZoneStatus")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.Bitmap16bit)
	.setWritable(false);

	final static public   AttributeDescriptor IAS_CIE_ADDRESS = new AbstractAttribute()
	.setId(0x00010)
	.setName("IASCieAddress")
	.setReportable(false)
	.setType(String.class)
	.setZigBeeType(ZigBeeType.IEEEAddress)
	.setWritable(true);

	final static public   AttributeDescriptor CURRENT_HUE = new AbstractAttribute()
	.setId(0x0000)
	.setName("CurrentHue")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(false);

	final static public   AttributeDescriptor CURRENT_SATURATION = new AbstractAttribute()
	.setId(0x0001)
	.setName("CurrentSaturation")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(false);

	final static public   AttributeDescriptor REMAINING_TIME_COLOR_CONTROL = new AbstractAttribute()
	.setId(0x0002)
	.setName("RemainingTime")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);

	final static public   AttributeDescriptor CURRENT_X = new AbstractAttribute()
	.setId(0x0003)
	.setName("CurrentX")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);

	final static public   AttributeDescriptor CURRENT_Y = new AbstractAttribute()
	.setId(0x0004)
	.setName("CurrentY")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);

	final static public   AttributeDescriptor DRIFT_COMPENSATION = new AbstractAttribute()
	.setId(0x0005)
	.setName("DriftCompensation")
	.setReportable(false)
	.setType(Byte.class)
	.setZigBeeType(ZigBeeType.Enumeration8bit)
	.setWritable(false);

	final static public   AttributeDescriptor COMPENSATION_TEXT = new AbstractAttribute()
	.setId(0x0006)
	.setName("CompensationText")
	.setReportable(false)
	.setType(String.class)
	.setZigBeeType(ZigBeeType.CharacterString)
	.setWritable(false);

	final static public   AttributeDescriptor COLOR_TEMPERATURE = new AbstractAttribute()
	.setId(0x0007)
	.setName("ColorTemperature")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);

	final static public   AttributeDescriptor COLOR_MODE = new AbstractAttribute()
	.setId(0x0008)
	.setName("ColorMode")
	.setReportable(false)
	.setType(Byte.class)
	.setZigBeeType(ZigBeeType.Enumeration8bit)
	.setWritable(false);

	final static public   AttributeDescriptor NUMBER_OF_PRIMARIES = new AbstractAttribute()
	.setId(0x0010)
	.setName("NumberOfPrimaries")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(false);

	final static public   AttributeDescriptor PRIMARY_1_X = new AbstractAttribute()
	.setId(0x0011)
	.setName("Primary1X")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);

	final static public   AttributeDescriptor PRIMARY_1_Y = new AbstractAttribute()
	.setId(0x0012)
	.setName("Primary1Y")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);

	final static public   AttributeDescriptor PRIMARY_1_INTENSITY = new AbstractAttribute()
	.setId(0x0013)
	.setName("Primary1Intensity")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(false);

	final static public   AttributeDescriptor PRIMARY_2_X = new AbstractAttribute()
	.setId(0x0015)
	.setName("Primary2X")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);

	final static public   AttributeDescriptor PRIMARY_2_Y = new AbstractAttribute()
	.setId(0x0016)
	.setName("Primary2Y")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);

	final static public   AttributeDescriptor PRIMARY_2_INTENSITY = new AbstractAttribute()
	.setId(0x0017)
	.setName("Primary2Intensity")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(false);

	final static public   AttributeDescriptor PRIMARY_3_X = new AbstractAttribute()
	.setId(0x0019)
	.setName("Primary3X")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);

	final static public   AttributeDescriptor PRIMARY_3_Y = new AbstractAttribute()
	.setId(0x001a)
	.setName("Primary3Y")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);

	final static public   AttributeDescriptor PRIMARY_3_INTENSITY = new AbstractAttribute()
	.setId(0x001b)
	.setName("Primary3Intensity")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(false);

	final static public   AttributeDescriptor PRIMARY_4_X = new AbstractAttribute()
	.setId(0x0020)
	.setName("Primary4X")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);

	final static public   AttributeDescriptor PRIMARY_4_Y = new AbstractAttribute()
	.setId(0x0021)
	.setName("Primary4Y")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);

	final static public   AttributeDescriptor PRIMARY_4_INTENSITY = new AbstractAttribute()
	.setId(0x0022)
	.setName("Primary1Intensity")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(false);

	final static public   AttributeDescriptor PRIMARY_5_X = new AbstractAttribute()
	.setId(0x0024)
	.setName("Primary5X")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);

	final static public   AttributeDescriptor PRIMARY_5_Y = new AbstractAttribute()
	.setId(0x0025)
	.setName("Primary5Y")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);

	final static public   AttributeDescriptor PRIMARY_5_INTENSITY = new AbstractAttribute()
	.setId(0x0026)
	.setName("Primary5Intensity")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(false);

	final static public   AttributeDescriptor PRIMARY_6_X = new AbstractAttribute()
	.setId(0x0028)
	.setName("Primary6X")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);

	final static public   AttributeDescriptor PRIMARY_6_Y = new AbstractAttribute()
	.setId(0x0029)
	.setName("Primary6Y")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(false);

	final static public   AttributeDescriptor PRIMARY_6_INTENSITY = new AbstractAttribute()
	.setId(0x002a)
	.setName("Primary6Intensity")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(false);

	final static public   AttributeDescriptor WHITE_POINT_X = new AbstractAttribute()
	.setId(0x0030)
	.setName("WhitePointX")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(true);

	final static public   AttributeDescriptor WHITE_POINT_Y = new AbstractAttribute()
	.setId(0x0031)
	.setName("WhitePointY")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(true);

	final static public   AttributeDescriptor COLOR_POINT_RX = new AbstractAttribute()
	.setId(0x0032)
	.setName("ColorPointRX")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(true);

	final static public   AttributeDescriptor COLOR_POINT_RY = new AbstractAttribute()
	.setId(0x0033)
	.setName("ColorPointRY")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(true);

	final static public   AttributeDescriptor COLOR_POINT_R_INTENSITY = new AbstractAttribute()
	.setId(0x0034)
	.setName("ColorPointRIntensity")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(true);

	final static public   AttributeDescriptor COLOR_POINT_GX = new AbstractAttribute()
	.setId(0x0036)
	.setName("ColorPointGX")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(true);

	final static public   AttributeDescriptor COLOR_POINT_GY = new AbstractAttribute()
	.setId(0x0037)
	.setName("ColorPointGY")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(true);

	final static public   AttributeDescriptor COLOR_POINT_G_INTENSITY = new AbstractAttribute()
	.setId(0x0038)
	.setName("ColorPointGIntensity")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(true);

	final static public   AttributeDescriptor COLOR_POINT_BX = new AbstractAttribute()
	.setId(0x003a)
	.setName("ColorPointBX")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(true);

	final static public   AttributeDescriptor COLOR_POINT_BY = new AbstractAttribute()
	.setId(0x003b)
	.setName("ColorPointBY")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(true);

	final static public   AttributeDescriptor COLOR_POINT_B_INTENSITY = new AbstractAttribute()
	.setId(0x003d)
	.setName("ColorPointBIntensity")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger8bit)
	.setWritable(true);

	final static public   AttributeDescriptor MAX_DURATION = new AbstractAttribute()
	.setId(0x0000)
	.setName("MaxDuration")
	.setReportable(false)
	.setType(Integer.class)
	.setZigBeeType(ZigBeeType.UnsignedInteger16bit)
	.setWritable(true);
}
