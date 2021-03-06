/*
   Copyright 2008-2013 CNR-ISTI, http://isti.cnr.it
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

package it.cnr.isti.zigbee.ha.device.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.driver.core.HADeviceBase;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.ha.driver.core.reflection.AbstractDeviceDescription;
import it.cnr.isti.zigbee.ha.driver.core.reflection.DeviceDescription;

import java.util.ArrayList;

import org.osgi.framework.BundleContext;

/**
 * 
 * This class should be used by the refinment driver when there is no device matching the DeviceId<br>
 * of the service
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public class GenericHADevice extends HADeviceBase {
	
	private int[] mandatory;
	private int[] optional;
	private int[] standard;
	private int[] custom;
	public GenericHADevice(BundleContext ctx,ZigBeeDevice zbDevice) throws ZigBeeHAException{
		super(ctx,zbDevice);
		
		mandatory = new int[0]; // we don't know the device so we cannot distinuish
		optional = new int[0];
		
		int[] clusterIDs = zbDevice.getInputClusters();
		ArrayList<Integer> standardList = new ArrayList<Integer>();
		ArrayList<Integer> customList = new ArrayList<Integer>();
		for (int i = 0; i < clusterIDs.length; i++) {
//			if (HAProfile.clusters.containsKey(clusterIDs[i])){
//				standardList.add(clusterIDs[i]);
//			} else {
//				customList.add(clusterIDs[i]);
//			}
		}
		standard = new int[standardList.size()];
		for (int i = 0; i < standard.length; i++) {
			standard[i] = standardList.get(i).intValue();
		}
		custom = new int[customList.size()];
		for (int i = 0; i < custom.length; i++) {
			custom[i] = customList.get(i).intValue();
		}
		
		for (int i = 0; i < standard.length; i++) {
			addCluster(standard[i]);
		}
	}
	
	
	final DeviceDescription descriptor =  new AbstractDeviceDescription(){

		public int[] getCustomClusters() {
			return custom;
		}

		public int[] getMandatoryCluster() {
			return mandatory;
		}

		public int[] getOptionalCluster() {
			return optional;
		}

		public int[] getStandardClusters() {
			return standard;
		}
		
	};
	
	@Override
	public DeviceDescription getDescription() {
		return descriptor;
	}

	@Override
	public String getName() {
		return "Generic HA Device";
	}

}
