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

package it.cnr.isti.zigbee.zcl.library.impl.global.read;

import it.cnr.isti.zigbee.zcl.library.api.core.ZBSerializer;
import it.cnr.isti.zigbee.zcl.library.impl.core.AbstractCommand;
import it.cnr.isti.zigbee.zcl.library.impl.core.ByteArrayOutputStreamSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public class ReadAttributeCommand extends AbstractCommand {

	private static final Logger logger = LoggerFactory.getLogger(ReadAttributeCommand.class);
	
	private static byte ID = 0x00;
	
	private int[] attributeIds;	
	
	public ReadAttributeCommand(int[] attributeIds){
		super(ID,false);
		this.attributeIds = attributeIds;
	}
	
	public byte[] getPayload(){	
		if( payload == null){
			logger.debug("Creating Payload for command");
			ZBSerializer serializer = new ByteArrayOutputStreamSerializer();
			for (int i = 0; i < attributeIds.length; i++) {
				serializer.append_short((short) attributeIds[i]);
			}
			payload = serializer.getPayload();
		}
		return payload;
	}
	
}
