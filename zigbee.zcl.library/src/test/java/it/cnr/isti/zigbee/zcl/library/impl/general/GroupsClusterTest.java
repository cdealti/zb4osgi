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
package it.cnr.isti.zigbee.zcl.library.impl.general;

import static org.easymock.EasyMock.*;

import static org.junit.Assert.*;
import it.cnr.isti.zigbee.api.Cluster;
import it.cnr.isti.zigbee.api.ZigBeeBasedriverException;
import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.zcl.library.api.core.Status;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.general.Groups;
import it.cnr.isti.zigbee.zcl.library.api.general.groups.AddGroupResponse;
import it.cnr.isti.zigbee.zcl.library.impl.RawClusterImpl;

import org.junit.Test;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.8.0
 *
 */
public class GroupsClusterTest {

	private ZigBeeDevice createMockDevice() throws ZigBeeBasedriverException {
		ZigBeeDevice mock = createMock(ZigBeeDevice.class);
		
		expect(mock.invoke( (Cluster) anyObject()))
			.andReturn( new RawClusterImpl(
							Groups.ID, 
							new byte[]{0x09, 0x18, 0x00, 0x00, 0x00, (byte) 0xf0 }
			) );
		replay( mock );
		return mock;
	}
	
	@Test
	public void testAddGroup() {
		GroupsCluster cluster = null;
		ZigBeeDevice device = null;
		try {
			device = createMockDevice();
			cluster = new GroupsCluster(device);
		} catch (ZigBeeBasedriverException ignored) {
		}
		try {
			AddGroupResponse response = (AddGroupResponse) cluster.addGroup(0xFF00,"hello world!");
			assertEquals( Status.SUCCESS, response.getStatus() );
			assertEquals( 0xf000, response.getGroupId() );
		} catch (ZigBeeClusterException ex) {
			fail("Unexpected exception "+ex);
			ex.printStackTrace();
		}
	}

}