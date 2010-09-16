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

package it.cnr.isti.zigbee.zcl.library.impl.measureament_sensing;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.measureament_sensing.OccupacySensing;
import it.cnr.isti.zigbee.zcl.library.impl.attribute.Attributes;
import it.cnr.isti.zigbee.zcl.library.impl.core.AttributeImpl;
import it.cnr.isti.zigbee.zcl.library.impl.core.ZCLClusterBase;

/**
 * Implementation of the {@link OccupacySensing} interface
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 668 $ ($LastChangedDate: 2009-11-19 18:48:39 +0100 (Thu, 19 Nov 2009) $)
 * @since 0.4.0
 *
 */
public class OccupacySensingCluster extends ZCLClusterBase implements OccupacySensing {
	
	private final AttributeImpl occupancy;
	private final AttributeImpl occupancySensorType;
	private final AttributeImpl pirOccupiedToUnoccupiedDelay;
	private final AttributeImpl pirUnoccupiedToOccupiedDelay;
	private final AttributeImpl ultraSonicOccupiedToUnoccupiedDelay;
	private final AttributeImpl ultraSonicUnoccupiedToOccupiedDelay;
	
	
	private final Attribute[] attributes;

	public OccupacySensingCluster(ZigBeeDevice zbDevice){
		super(zbDevice);
		occupancy = new AttributeImpl(zbDevice,this,Attributes.OCCUPANCY);
		occupancySensorType = new AttributeImpl(zbDevice,this,Attributes.OCCUPANCY_SENSOR_TYPE);
		pirOccupiedToUnoccupiedDelay = new AttributeImpl(zbDevice,this,Attributes.PIR_OCCUPIED_TO_UNOCCUPIED_DELAY);
		pirUnoccupiedToOccupiedDelay = new AttributeImpl(zbDevice,this,Attributes.PIR_UNOCCUPIED_TO_OCCUPIED_DELAY);
		ultraSonicOccupiedToUnoccupiedDelay = new AttributeImpl(zbDevice,this,Attributes.ULTRA_SONIC_OCCUPIED_TO_UNOCCUPIED_DELAY);
		ultraSonicUnoccupiedToOccupiedDelay = new AttributeImpl(zbDevice,this,Attributes.ULTRA_SONIC_UNOCCUPIED_TO_OCCUPIED_DELAY);
		attributes = new AttributeImpl[]{occupancy,occupancySensorType,pirOccupiedToUnoccupiedDelay,
				pirUnoccupiedToOccupiedDelay,ultraSonicOccupiedToUnoccupiedDelay,ultraSonicUnoccupiedToOccupiedDelay};
	}
	
	@Override
	public short getId() {
		return OccupacySensing.ID;
	}

	@Override
	public String getName() {
		return OccupacySensing.NAME;
	}

	@Override
	public Attribute[] getStandardAttributes() {
		return attributes;
	}

	public Attribute getAttributeOccupancy() {
		return occupancy;
	}

	public Attribute getAttributeOccupancySensorType() {
		return occupancySensorType;
	}

	public Attribute getAttributePIROccupiedToUnoccupiedDelay() {
		return pirOccupiedToUnoccupiedDelay;
	}

	public Attribute getAttributePIRUnoccupiedToOccupiedDelay() {
		return pirUnoccupiedToOccupiedDelay;
	}

	public Attribute getAttributeUltraSonicOccupiedToUnoccupiedDelay() {
		return ultraSonicOccupiedToUnoccupiedDelay;
	}

	public Attribute getAttributeUltraSonicUnoccupiedToOccupiedDelay() {
		return ultraSonicUnoccupiedToOccupiedDelay;
	}

}
