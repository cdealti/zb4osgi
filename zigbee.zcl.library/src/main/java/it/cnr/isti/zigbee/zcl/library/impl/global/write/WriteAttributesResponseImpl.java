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

import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.Status;
import it.cnr.isti.zigbee.zcl.library.api.core.ZBDeserializer;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.global.WriteAttributesResponse;
import it.cnr.isti.zigbee.zcl.library.api.global.WriteAttributesStatus;
import it.cnr.isti.zigbee.zcl.library.impl.attribute.AttributeDescriptor;
import it.cnr.isti.zigbee.zcl.library.impl.core.DefaultDeserializer;
import it.cnr.isti.zigbee.zcl.library.impl.core.ResponseImpl;
/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public class WriteAttributesResponseImpl extends ResponseImpl implements
		WriteAttributesResponse {
	
	private WriteAttributesStatus[] attributes;
	
	public WriteAttributesResponseImpl(Response response, AttributeDescriptor[] descriptors) throws  ZigBeeClusterException{
		super(response);
		ResponseImpl.checkGeneralCommandFrame(response, WriteAttributesResponse.ID);
		attributes = new WriteAttributesStatus[descriptors.length];
		if( getPayload().length == 1 && getPayload()[0] == Status.SUCCESS.id ) {
			for (int i = 0; i < descriptors.length; i++) {
				attributes[i] = new WriteAttributeStatusImpl(descriptors[i]);
			}
		}else{
			ZBDeserializer deserializer = new DefaultDeserializer(getPayload(),0);
			for (int i = 0; i < descriptors.length; i++) {
				attributes[i] = new WriteAttributeStatusImpl(descriptors[i], deserializer);
			}
		}
	}

	public WriteAttributesStatus[] getWriteAttributesStatus() {
		return attributes;
	}

}
