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

package it.cnr.isti.zigbee.zcl.library.impl.global.write;

import it.cnr.isti.zigbee.zcl.library.api.core.ZBSerializer;
import it.cnr.isti.zigbee.zcl.library.api.global.WriteAttributeRecord;
import it.cnr.isti.zigbee.zcl.library.impl.core.AbstractCommand;
import it.cnr.isti.zigbee.zcl.library.impl.core.DefaultSerializer;
import it.cnr.isti.zigbee.zcl.library.impl.core.ZigBeeType;
/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public class WriteAttributeCommand extends AbstractCommand {
	private static byte ID = 0x02;
	
	private WriteAttributeRecord[] attributeRecord;
	
	public WriteAttributeCommand(WriteAttributeRecord[] attributerecord){
		super(ID,false);
		this.attributeRecord = attributerecord;
	}
	
	public byte[] getPayload(){	
		if( payload == null){			
			int length = 0;
			for (int i = 0; i < attributeRecord.length; i++){

				int len = attributeRecord[i].getAttributeDataType().getLength();
				if(len == -1){
					//TODO Use a general method instead of assuming that variable length is applied only for String 
					length = length + ((String) attributeRecord[i].getAttributeData()).length();
				} else {
					length = length + len;
				}
				length = length + 2 + 1 + 1; // manlio +1 (a bit for string length field) //space for attribute id and attribute data type
			}
			payload = new byte[length];
			ZBSerializer serializer = new DefaultSerializer(payload, 0);
		
			for (int i = 0; i < attributeRecord.length; i++) {
				serializer.append_short( (short) attributeRecord[i].getAttributeId());
				final ZigBeeType type = attributeRecord[i].getAttributeDataType();
				serializer.append_byte((byte) type.getId());
				serializer.appendZigBeeType(attributeRecord[i].getAttributeData(), type);
			}
		}
		return payload;
	}
}
