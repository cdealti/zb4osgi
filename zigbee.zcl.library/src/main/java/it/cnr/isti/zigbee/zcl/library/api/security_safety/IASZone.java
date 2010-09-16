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

package it.cnr.isti.zigbee.zcl.library.api.security_safety;

import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.ZCLCluster;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_zone.ZoneEnrollResponse;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 *         
 * @version $LastChangedRevision: 723 $ ($LastChangedDate: 2010-03-12 17:19:17 +0100 (Fri, 12 Mar 2010) $)
 * @since 0.1.0
 *
 */
public interface IASZone extends ZCLCluster{

	public static final short ID = 0x0500;
	
	public Attribute getAttributeZoneState();
	public Attribute getAttributeZoneType();
	public Attribute getAttributeZoneStatus();
	public Attribute getAttributeIASCIEAddress();

	public Response ZoneStatusChangeNotification(byte zoneStatus, byte extendedStatus);
	public ZoneEnrollResponse ZoneEnrollRequest(byte zoneType, byte manufacturerCode);
	

}
