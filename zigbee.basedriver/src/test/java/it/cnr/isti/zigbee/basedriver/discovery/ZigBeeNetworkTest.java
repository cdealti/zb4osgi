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

package it.cnr.isti.zigbee.basedriver.discovery;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.api.ZigBeeNode;

import org.junit.Test;
/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 668 $ ($LastChangedDate: 2009-11-19 18:48:39 +0100 (Thu, 19 Nov 2009) $)
 *
 */
public class ZigBeeNetworkTest {

	@Test
	public void testAddNode() {
		ZigBeeNetwork network = new ZigBeeNetwork();
		ZigBeeNode nodeAlpha = createMock(ZigBeeNode.class);
		expect(nodeAlpha.getIEEEAddress()).andReturn("01:02:03:04:05:06:07:08");
		replay(nodeAlpha);
		assertTrue(network.addNode(nodeAlpha));
		
		ZigBeeNode nodeBeta = createMock(ZigBeeNode.class);
		expect(nodeBeta.getIEEEAddress()).andReturn("01:02:03:04:05:06:07:08");
		replay(nodeBeta);
		assertFalse(network.addNode(nodeBeta));
	}

	@Test
	public void testAddDevice() {
		ZigBeeNetwork network = new ZigBeeNetwork();
		ZigBeeNode nodeAlpha = createMock(ZigBeeNode.class);
		expect(nodeAlpha.getIEEEAddress()).andReturn("01:02:03:04:05:06:07:08");

		ZigBeeDevice deviceAlpha = createMock(ZigBeeDevice.class);
		expect(deviceAlpha.getPhysicalNode()).andReturn(nodeAlpha);
		expect(nodeAlpha.getIEEEAddress()).andReturn("01:02:03:04:05:06:07:08");
		expect(deviceAlpha.getId()).andReturn((short)0x20);
		expect(deviceAlpha.getProfileId()).andReturn(0x104);

		ZigBeeDevice deviceBeta = createMock(ZigBeeDevice.class);
		expect(deviceBeta.getPhysicalNode()).andReturn(nodeAlpha);
		expect(nodeAlpha.getIEEEAddress()).andReturn("01:02:03:04:05:06:07:08");
		expect(deviceBeta.getId()).andReturn((short)0x20);
		expect(deviceBeta.getProfileId()).andReturn(0x104);
		
		
		replay(nodeAlpha);
		replay(deviceAlpha);
		replay(deviceBeta);
		assertTrue(network.addNode(nodeAlpha));
		assertTrue(network.addDevice(deviceAlpha));
		assertFalse(network.addDevice(deviceBeta));
	}

	@Test
	public void testContainsString() {
		ZigBeeNetwork network = new ZigBeeNetwork();
		ZigBeeNode nodeAlpha = createMock(ZigBeeNode.class);
		expect(nodeAlpha.getIEEEAddress()).andReturn("01:02:03:04:05:06:07:08");
		replay(nodeAlpha);
		assertTrue(network.addNode(nodeAlpha));
		assertEquals(nodeAlpha, network.contains("01:02:03:04:05:06:07:08"));

		final String beta = "00:01:02:03:04:05:06:07";
		ZigBeeNode nodeBeta = createMock(ZigBeeNode.class);
		expect(nodeBeta.getIEEEAddress()).andReturn(beta);
		replay(nodeBeta);
		assertTrue(network.addNode(nodeBeta));
		assertEquals(nodeBeta, network.contains(beta));	
	}

}
