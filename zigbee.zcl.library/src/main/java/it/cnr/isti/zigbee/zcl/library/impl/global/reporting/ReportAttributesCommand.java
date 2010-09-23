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

import java.util.ArrayList;

import it.cnr.isti.zigbee.zcl.library.api.core.ZBDeserializer;
import it.cnr.isti.zigbee.zcl.library.api.core.ZBSerializer;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.global.AttributeReport;
import it.cnr.isti.zigbee.zcl.library.api.global.ReadAttributesResponse;
import it.cnr.isti.zigbee.zcl.library.api.global.ReadAttributesStatus;
import it.cnr.isti.zigbee.zcl.library.impl.core.AbstractCommand;
import it.cnr.isti.zigbee.zcl.library.impl.core.DefaultDeserializer;
import it.cnr.isti.zigbee.zcl.library.impl.core.DefaultSerializer;
import it.cnr.isti.zigbee.zcl.library.impl.core.ResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.global.read.ReadAttributeStatusImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 *         
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.2.0
 *
 */
public class ReportAttributesCommand extends ResponseImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(ReportAttributesCommand.class);
	
	public static final byte ID = 0x0A;
	
	private AttributeReport[] attributesReport;
	
	public ReportAttributesCommand(ResponseImpl response) throws ZigBeeClusterException{
		super(response);
		ResponseImpl.checkGeneralCommandFrame(response, ReportAttributesCommand.ID);
		
		byte[] msg = getPayload();
		ZBDeserializer deserializer = new DefaultDeserializer(msg,0);
		ArrayList<AttributeReportImpl> attributes = new ArrayList<AttributeReportImpl>();
		for (int i = 0; deserializer.getPosition() < msg.length; i++) {
			attributes.add(new AttributeReportImpl(deserializer));
		}
		
		attributesReport = attributes.toArray(new AttributeReport[]{});
	}		
	
	public AttributeReport[] getAttributeReports() {
		return attributesReport;
	}
}
