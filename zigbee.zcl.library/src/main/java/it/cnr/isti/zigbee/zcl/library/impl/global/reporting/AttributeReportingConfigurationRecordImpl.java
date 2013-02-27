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

import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.global.AttributeReportingConfigurationRecord;
import it.cnr.isti.zigbee.zcl.library.impl.core.ZigBeeType;


/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public class AttributeReportingConfigurationRecordImpl implements
		AttributeReportingConfigurationRecord {
	
	private Attribute attribute;
	private int direction;
	private int maximumReportinInterval;
	private int minimumReportingInterval;
	private Object reportableChange;
	private int timeoutPeriod;
	

	public AttributeReportingConfigurationRecordImpl(Attribute attribute, int direction){
		this.attribute = attribute;
		this.direction = direction;
	}
	
	public AttributeReportingConfigurationRecordImpl(Attribute attribute, int direction,
			int maximumReportinInterval, int minimumReportingInterval, Object reportableChange, 
			int timeoutPeriod){
		
		this.attribute = attribute;
		this.direction = direction;
		this.maximumReportinInterval = maximumReportinInterval;
		this.minimumReportingInterval = minimumReportingInterval;
		this.reportableChange = reportableChange;
		this.timeoutPeriod = timeoutPeriod;
	}
	
	public ZigBeeType getAttributeDataType() {
		return attribute.getZigBeeType();
	}

	public int getAttributeId() {
		return attribute.getId();
	}

	public int getDiretion() {
		return direction;
	}

	public int getMaximumReportinInterval() {
		return maximumReportinInterval;
	}

	public int getMinimumReportingInterval() {
		return minimumReportingInterval;
	}

	public Object getReportableChange() {
		return reportableChange;
	}

	public int getTimeoutPeriod() {
		return timeoutPeriod;
	}

}
