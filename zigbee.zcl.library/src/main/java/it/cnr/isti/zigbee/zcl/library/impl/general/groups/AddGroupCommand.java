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

package it.cnr.isti.zigbee.zcl.library.impl.general.groups;

import it.cnr.isti.zigbee.zcl.library.api.core.ZBSerializer;
import it.cnr.isti.zigbee.zcl.library.api.general.Groups;
import it.cnr.isti.zigbee.zcl.library.impl.core.AbstractCommand;
import it.cnr.isti.zigbee.zcl.library.impl.core.ByteArrayOutputStreamSerializer;
import it.cnr.isti.zigbee.zcl.library.impl.core.DefaultSerializer;
import it.cnr.isti.zigbee.zcl.library.impl.core.ZigBeeType;


/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public class AddGroupCommand extends AbstractCommand {

	private int groupId;
	private String name;
	
	public AddGroupCommand(int groupId, String name){
		super(Groups.ADD_GROUP_ID);
		this.groupId = groupId;
		this.name = name;
	}
	
	public byte[] getPayload(){	
		if( payload == null){			
			ZBSerializer serializer = new ByteArrayOutputStreamSerializer();
			serializer.append_short((short)groupId);		
			serializer.appendZigBeeType(name, ZigBeeType.CharacterString);
			payload = serializer.getPayload();			
		}
		return payload;
	}
	
}
