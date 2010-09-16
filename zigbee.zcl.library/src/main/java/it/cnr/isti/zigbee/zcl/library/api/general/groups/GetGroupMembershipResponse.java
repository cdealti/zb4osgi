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

package it.cnr.isti.zigbee.zcl.library.api.general.groups;

import it.cnr.isti.zigbee.zcl.library.api.core.Response;

/**
 * 
 * This class represent the <i>Add Group Response</i> as defined by the document:<br>
 * <i>ZigBee Cluster Library</i> public release version 075123r01ZB
 *   
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 668 $ ($LastChangedDate: 2009-11-19 18:48:39 +0100 (Thu, 19 Nov 2009) $)
 * @since 0.1.0
 *
 */
public interface GetGroupMembershipResponse extends Response{
	
	public static final byte ID = 0x02;
	
	/**
	 * 
	 * @return a <code>short</code> representing the <i>Capacity</i> field
	 */
	public short getCapacity();
	
	/**
	 * 
	 * @return <code>int[]</code> representing the <i>Group list</i> and <i>Group count</i> field
	 */
	public int[] getGroupList();

}
