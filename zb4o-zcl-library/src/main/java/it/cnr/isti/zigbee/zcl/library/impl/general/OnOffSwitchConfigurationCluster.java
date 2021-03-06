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

package it.cnr.isti.zigbee.zcl.library.impl.general;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.general.OnOffSwitchConfiguration;
import it.cnr.isti.zigbee.zcl.library.impl.attribute.Attributes;
import it.cnr.isti.zigbee.zcl.library.impl.core.AttributeImpl;
import it.cnr.isti.zigbee.zcl.library.impl.core.ZCLClusterBase;
/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public class OnOffSwitchConfigurationCluster extends ZCLClusterBase implements OnOffSwitchConfiguration {
	
	private final AttributeImpl switchType;
	private final AttributeImpl switchAction;
	
	private final Attribute[] attributes;

	public OnOffSwitchConfigurationCluster(ZigBeeDevice zbDevice){
		super(zbDevice);
		switchType = new AttributeImpl(zbDevice,this,Attributes.SWITCH_TYPE);
		switchAction = new AttributeImpl(zbDevice,this,Attributes.SWITCH_ACTIONS); 
		attributes = new Attribute[]{switchType, switchAction};
	}
	
	@Override
	public short getId() {
		return OnOffSwitchConfiguration.ID;
	}

	@Override
	public String getName() {
		return OnOffSwitchConfiguration.NAME;
	}

	@Override
	public Attribute[] getStandardAttributes() {
		return attributes;
	}

	public Attribute getAttributeSwitchActions() {
		return switchAction;
	}

	public Attribute getAttributeSwitchType() {
		return switchType;
	}

}
