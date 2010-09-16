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

package it.cnr.isti.zigbee.dongle.api;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi - ISTI-CNR
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.6.0
 *
 */
public interface ConfigurationProperties {

	/**
	 * 
	 */
	public final static String NETWORK_MODE = NetworkMode.Coordinator.toString();
	/**
	 * Key to use to change the <code>Network Mode</code>, accepted values are:<br>
	 *  - "<b>Coordinator</b>": the driver try to create a ZigBee network and will be the coordinator<br>
	 *  - "<b>Router</b>": the driver try to join any ZigBee as Router to an existing network<br>
	 *  - "<b>EndDevice</b>" : the driver to join to a ZigBee as End-Device to an existing network<br> 
	 */
	public final static String NETWORK_MODE_KEY = "it.cnr.isti.zigbee.driver.mode";
	
	public final static int PAN_ID = 0x1357;
	public final static String PAN_ID_KEY = "it.cnr.isti.zigbee.pan.id";
	
	public final static byte CHANNEL_ID = 23;
	public final static String CHANNEL_ID_KEY = "it.cnr.isti.zigbee.pan.channel";
	
	public final static String COM_NAME = "auto";
	public final static String COM_NAME_KEY = "it.cnr.isti.zigbee.driver.serial.portname";

	public final static int COM_BOUDRATE = 38400;
	public final static String COM_BOUDRATE_KEY = "it.cnr.isti.zigbee.driver.serial.boudrate";
	
	public final static boolean NETWORK_FLUSH = false;
	public final static String NETWORK_FLUSH_KEY = "it.cnr.isti.zigbee.driver.flush";

	public final static int APPLICATION_MSG_RETRY_COUNT = 3;
	public final static String APPLICATION_MSG_RETRY_COUNT_KEY = "it.cnr.isti.zigbee.driver.communication.retry.count";
	
	public final static int APPLICATION_MSG_RETRY_DELAY = 1000;
	public final static String APPLICATION_MSG_RETRY_DELAY_KEY = "it.cnr.isti.zigbee.driver.communication.retry.delay";

	public final static int APPLICATION_MSG_TIMEOUT = 2500;
	public final static String APPLICATION_MSG_TIMEOUT_KEY = "it.cnr.isti.zigbee.driver.communication.timout";	
	
	
}
