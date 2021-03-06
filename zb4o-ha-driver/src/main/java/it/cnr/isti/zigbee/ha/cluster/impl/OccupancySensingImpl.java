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

package it.cnr.isti.zigbee.ha.cluster.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.Activator;
import it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.OccupancySensing;
import it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.event.OccupancyListener;
import it.cnr.isti.zigbee.ha.cluster.impl.event.OccupancyBridgeListeners;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Subscription;
import it.cnr.isti.zigbee.zcl.library.impl.measureament_sensing.OccupancySensingCluster;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:manlio.bacco@isti.cnr.it">Manlio Bacco</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @author <a href="mailto:alessandro.giari@isti.cnr.it">Alessandro Giari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public class OccupancySensingImpl implements OccupancySensing {
			
	private OccupancySensingCluster occupancySensingCluster;
	private Attribute occupancy;
	private Attribute occupancySensorType;
	private Attribute pirOccupiedToUnoccupiedDelay;
	private Attribute pirUnoccupiedToOccupiedDelay;
	private Attribute pirUnoccupiedToOccupiedThreshold;
	private Attribute ultraSonicOccupiedToUnoccupiedDelay;
	private Attribute ultraSonicUnoccupiedToOccupiedDelay;
	private Attribute ultrasonicUnoccupiedToOccupiedThreshold;

	private OccupancyBridgeListeners eventBridge;
	
	public OccupancySensingImpl(ZigBeeDevice zbDevice){
		
		occupancySensingCluster = new OccupancySensingCluster(zbDevice);
		occupancy = occupancySensingCluster.getAttributeOccupancy();
		occupancySensorType = occupancySensingCluster.getAttributeOccupancySensorType();
		pirOccupiedToUnoccupiedDelay = occupancySensingCluster.getAttributePIROccupiedToUnoccupiedDelay();
		pirUnoccupiedToOccupiedDelay = occupancySensingCluster.getAttributePIRUnoccupiedToOccupiedDelay();
		ultraSonicOccupiedToUnoccupiedDelay = occupancySensingCluster.getAttributeUltraSonicOccupiedToUnoccupiedDelay();
		ultraSonicUnoccupiedToOccupiedDelay = occupancySensingCluster.getAttributeUltraSonicUnoccupiedToOccupiedDelay();
		pirUnoccupiedToOccupiedThreshold = occupancySensingCluster.getAttributePIRUnoccupiedToOccupiedThreshold();
		ultrasonicUnoccupiedToOccupiedThreshold = occupancySensingCluster.getAttributeUltrasonicUnoccupiedToOccupiedThreshold();

		eventBridge = new OccupancyBridgeListeners(Activator.getConfiguration(), occupancy, this);		
	}

	public Attribute getOccupancy() {
		return occupancy;
	}

	public Attribute getOccupancySensorType() {
		return occupancySensorType;
	}

	public Attribute getPIROccupiedToUnoccupiedDelay() {
		return pirUnoccupiedToOccupiedDelay;
	}

	public Attribute getPIRUnoccupiedToOccupiedDelay() {
		return pirOccupiedToUnoccupiedDelay;
	}

	public Attribute getUltraSonicOccupiedToUnoccupiedDelay() {
		return ultraSonicOccupiedToUnoccupiedDelay;
	}

	public Attribute getUltraSonicUnoccupiedToOccupiedDelay() {
		return ultraSonicUnoccupiedToOccupiedDelay;
	}

	public Subscription[] getActiveSubscriptions() {
		return occupancySensingCluster.getActiveSubscriptions();
	}

	public int getId() {
		return occupancySensingCluster.getId();
	}

	public String getName() {
		return occupancySensingCluster.getName();
	}

	public void subscribe(OccupancyListener listener) {
		eventBridge.subscribe(listener);
	}

	public void unsubscribe(OccupancyListener listener) {
		eventBridge.unsubscribe(listener);
	}
	
	public Attribute getAttribute(int id) {		
		Attribute[] attributes = occupancySensingCluster.getAvailableAttributes();
		for (int i = 0; i < attributes.length; i++) {
			if( attributes[i].getId() == id ) 
				return attributes[i];
		}
		return null;
	}

	public Attribute[] getAttributes() {
		return occupancySensingCluster.getAvailableAttributes();
	}
	
	public Attribute getPIRUnoccupiedToOccupiedThreshold() {
		return pirUnoccupiedToOccupiedThreshold;
	}

	public Attribute getUltraSonicUnoccupiedToOccupiedThreshold() {
		return ultrasonicUnoccupiedToOccupiedThreshold;
	}	
}
