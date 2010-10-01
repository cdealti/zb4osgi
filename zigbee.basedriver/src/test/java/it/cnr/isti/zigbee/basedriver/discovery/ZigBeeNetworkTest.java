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
import gnu.trove.TIntArrayList;
import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.api.ZigBeeNode;
import it.cnr.isti.zigbee.basedriver.api.impl.ZigBeeNodeImpl;

import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public class ZigBeeNetworkTest {

    private class ZigBeeNetworkSample {
        private ZigBeeNode[] nodes;
        private ZigBeeDevice[] devices;
    }

    final private ZigBeeNetworkSample mock_d11_n10 = new ZigBeeNetworkSample();
    final private ZigBeeNetworkSample fake_d11_n10 = new ZigBeeNetworkSample();

    @Before
    public void setUp_MockNetwork11Devices_10Nodes() {
        String[] ieees = new String[]{
            "00:12:4B:00:00:03:0B:AC",
            "00:12:4B:00:00:03:0B:AE",
            "00:12:4B:00:00:03:0D:F8",
            "00:12:4B:00:00:03:0E:1B",
            "00:12:4B:00:00:03:0F:6E",
            "00:12:4B:00:00:03:0F:9D",
            "00:12:4B:00:00:03:15:AC",
            "00:12:4B:00:00:03:15:AD",
            "00:12:4B:00:00:03:16:69",
            "00:12:4B:00:01:25:6B:FA"           
        };
        
        ZigBeeNode[] nodes = new ZigBeeNode[ieees.length];
        boolean[] inserted = new boolean[ieees.length];
        for ( int i = 0; i < nodes.length; i++ ) {
            final ZigBeeNode node = createMock(ZigBeeNode.class);
            expect(node.getIEEEAddress()).andReturn(ieees[i]).anyTimes();
            replay(node);
            nodes[i] = node;
            inserted[i] = false;
        }
        
        mock_d11_n10.nodes = nodes;        
    }

    @Before
    public void setUp_FakeNetwork11Devices_10Nodes() {
        String[] ieees = new String[]{
            "00:12:4B:00:00:03:0B:AC",
            "00:12:4B:00:00:03:0B:AE",
            "00:12:4B:00:00:03:0D:F8",
            "00:12:4B:00:00:03:0E:1B",
            "00:12:4B:00:00:03:0F:6E",
            "00:12:4B:00:00:03:0F:9D",
            "00:12:4B:00:00:03:15:AC",
            "00:12:4B:00:00:03:15:AD",
            "00:12:4B:00:00:03:16:69",
            "00:12:4B:00:01:25:6B:FA"           
        };
        
        ZigBeeNode[] nodes = new ZigBeeNode[ieees.length];
        boolean[] inserted = new boolean[ieees.length];
        for ( int i = 0; i < nodes.length; i++ ) {
            final ZigBeeNode node = new ZigBeeNodeImpl( 0, ieees[i], (short) 0 );
            nodes[i] = node;
            inserted[i] = false;
        }
        
        fake_d11_n10.nodes = nodes;        
    }
    
    
    /**
     * This method aims to test replicate and identifies the
     * <a href="http://zb4osgi.aaloa.org/redmine/issues/50" title="Duplicated devices registered">#50</a>
     */
    //@Test
    public void testDuplicatedDevices() {
        String[][] devices = new String[][]{
            new String[]{"0x00 0x00:","0x00:0x12:0x4B:0x00:0x01:0x25:0x6B:0xFA"},
            new String[]{"0x00 0x01:","0x00:0x12:0x4B:0x00:0x00:0x03:0x0F:0x6E"},
            new String[]{"0x00 0x01:","0x00:0x12:0x4B:0x00:0x00:0x03:0x15:0xAD"},
            new String[]{"0x00 0x02:","0x00:0x12:0x4B:0x00:0x00:0x03:0x15:0xAC"},
            new String[]{"0x00 0x02:","0x00:0x12:0x4B:0x00:0x00:0x03:0x16:0x69"},
            new String[]{"0x14 0x3E:","0x00:0x12:0x4B:0x00:0x00:0x03:0x0D:0xF8"},
            new String[]{"0x14 0x3E:","0x00:0x12:0x4B:0x00:0x00:0x03:0x15:0xAD"},
            new String[]{"0x14 0x3F:","0x00:0x12:0x4B:0x00:0x00:0x03:0x0E:0x1B"},
            new String[]{"0x14 0x3F:","0x00:0x12:0x4B:0x00:0x00:0x03:0x0F:0x9D"},
            new String[]{"0x17 0x9C:","0x00:0x12:0x4B:0x00:0x00:0x03:0x0F:0x9D"},
            new String[]{"0x17 0x9C:","0x00:0x12:0x4B:0x00:0x00:0x03:0x15:0xAC"},
            new String[]{"0x1A 0xF9:","0x00:0x12:0x4B:0x00:0x00:0x03:0x0F:0x6E"},
            new String[]{"0x1A 0xF9:","0x00:0x12:0x4B:0x00:0x00:0x03:0x16:0x69"},
            new String[]{"0x28 0x7B:","0x00:0x12:0x4B:0x00:0x00:0x03:0x0B:0xAC"},
            new String[]{"0x28 0x7B:","0x00:0x12:0x4B:0x00:0x00:0x03:0x0E:0x1B"},
            new String[]{"0x3C 0xB8:","0x00:0x12:0x4B:0x00:0x00:0x03:0x0B:0xAC"},
            new String[]{"0x50 0xF5:","0x00:0x12:0x4B:0x00:0x00:0x03:0x0B:0xAE"},
            new String[]{"0x50 0xF6:","0x00:0x12:0x4B:0x00:0x00:0x03:0x0B:0xAE"},
            new String[]{"0x50 0xF6:","0x00:0x12:0x4B:0x00:0x00:0x03:0x0D:0xF8"}            
        };
        /*
        ZigBeeNode[] nodes = new ZigBeeNode[ieees.length];
        boolean[] inserted = new boolean[ieees.length];
        for ( int i = 0; i < nodes.length; i++ ) {
            final ZigBeeNode node = createMock(ZigBeeNode.class);
            expect(node.getIEEEAddress()).andReturn(ieees[i]).anyTimes();
            replay(node);
            nodes[i] = node;
            inserted[i] = false;
        }
        for ( int i = 0; i < inserted.length; i++ ) {
            ZigBeeDevice deviceAlpha = createMock(ZigBeeDevice.class);
            expect(deviceAlpha.getPhysicalNode()).andReturn(nodes[i]).anyTimes();            
            expect(deviceAlpha.getId()).andReturn((short)0x20);
            expect(deviceAlpha.getProfileId()).andReturn(0x104).anyTimes();
        }
        
        ZigBeeNetwork network = new ZigBeeNetwork();
        network.addDevice( null );
        ZigBeeNode nodeAlpha = createMock(ZigBeeNode.class);
        expect(nodeAlpha.getIEEEAddress()).andReturn("01:02:03:04:05:06:07:08");
        replay(nodeAlpha);
        assertTrue(network.addNode(nodeAlpha));
        
        ZigBeeNode nodeBeta = createMock(ZigBeeNode.class);
        expect(nodeBeta.getIEEEAddress()).andReturn("01:02:03:04:05:06:07:08");
        replay(nodeBeta);
        assertFalse(network.addNode(nodeBeta));
        */
    }    
    
    /**
     * This test verify that is impossible to add twice a node with the same IEEEAddress.
     * It partially cover issue:
     * <a href="http://zb4osgi.aaloa.org/redmine/issues/50" title="Duplicated devices registered">#50</a>
     */
	@Test
	public void testAvoidDuplicatedNodeMock() {
	    
        ZigBeeNetwork network = new ZigBeeNetwork();
        ZigBeeNode[] nodes = mock_d11_n10.nodes;
        
        boolean[] inserted = new boolean[nodes.length];

        for ( int i = 0; i < nodes.length; i++ ) {
            inserted[i] = false;
        }
        
        int dup;
	    for ( int i = 0; i < nodes.length; i++ ) {
	        assertEquals( 
	            "Inserimento di dato sequenziale atteso true se non presente false altrimenti", 
	            ! inserted[i], network.addNode( nodes[i] ) 
	        );
	        inserted[i] = true;
	        
            dup = (int) ( ( Math.random() * 1000.0d ) % ( nodes.length ) ); 
            assertEquals( "Inserimento di dato casuale atteso true se non presente false altrimenti", 
                ! inserted[dup], network.addNode( nodes[dup] ) 
            );
            inserted[dup] = true;
            
            dup = (int) ( ( Math.random() * 1000.0d ) % ( nodes.length ) ); 
            assertEquals( "Inserimento di dato casuale atteso true se non presente false altrimenti", 
                ! inserted[dup], network.addNode( nodes[dup] ) 
            );
            inserted[dup] = true;
        }
	}

    /**
     * This test verify that is impossible to add twice a node with the same IEEEAddress.
     * It partially cover issue:
     * <a href="http://zb4osgi.aaloa.org/redmine/issues/50" title="Duplicated devices registered">#50</a>
     */
    @Test
    public void testAvoidDuplicatedNodeFake() {
        
        ZigBeeNetwork network = new ZigBeeNetwork();
        ZigBeeNode[] nodes = fake_d11_n10.nodes;
        
        boolean[] inserted = new boolean[nodes.length];

        for ( int i = 0; i < nodes.length; i++ ) {
            inserted[i] = false;
        }
        
        int dup;
        for ( int i = 0; i < nodes.length; i++ ) {
            assertEquals( 
                "Inserimento di dato sequenziale atteso true se non presente false altrimenti", 
                ! inserted[i], network.addNode( nodes[i] ) 
            );
            inserted[i] = true;
            
            dup = (int) ( ( Math.random() * 1000.0d ) % ( nodes.length ) ); 
            assertEquals( "Inserimento di dato casuale atteso true se non presente false altrimenti", 
                ! inserted[dup], network.addNode( nodes[dup] ) 
            );
            inserted[dup] = true;
            
            dup = (int) ( ( Math.random() * 1000.0d ) % ( nodes.length ) ); 
            assertEquals( "Inserimento di dato casuale atteso true se non presente false altrimenti", 
                ! inserted[dup], network.addNode( nodes[dup] ) 
            );
            inserted[dup] = true;
        }
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
