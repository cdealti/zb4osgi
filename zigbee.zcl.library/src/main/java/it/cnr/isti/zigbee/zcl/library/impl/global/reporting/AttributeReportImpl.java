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

package it.cnr.isti.zigbee.zcl.library.impl.global.reporting;

import it.cnr.isti.zigbee.zcl.library.api.core.ZBDeserializer;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeType;
import it.cnr.isti.zigbee.zcl.library.api.global.AttributeReport;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.2.0
 *
 */
public class AttributeReportImpl implements AttributeReport {

	private short attributeId; 
	private Object value;
	private final ZigBeeType type;

	public AttributeReportImpl(ZBDeserializer deserializer) {
		attributeId = deserializer.read_short();			
		byte dataType = deserializer.read_byte();;
		type = ZigBeeType.getType(dataType);
		value = deserializer.readZigBeeType(type);		
	}

	public Object getAttributeData() {
		return value;
	}

	public ZigBeeType getAttributeDataType() {
		return type;
	}

	public int getAttributeId() {
		return attributeId;
	}
	
}
