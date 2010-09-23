package com.itaca.ztool.api;

import static org.junit.Assert.*;

import java.io.IOException;
import java.security.GuardedObject;

import it.cnr.isti.cc2480.low.HWLowLevelDriver;
import it.cnr.isti.cc2480.low.PacketListener;
import it.cnr.isti.cc2480.virutal.Emulator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * Test unit for class {@link ZToolPacketParser}
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.6.0
 * 
 */
public class ZToolPacketParserTest {

    int[] nPackets = new int[1];

    private final static Logger logger = LoggerFactory.getLogger(ZToolPacketParserTest.class);    
    
    @Test
    public void testOverwrittenPacketHandling() {
        final ZToolPacket[] packets = new ZToolPacket[1];        
        final int TOTAL_GOD_PACKET = 26;
        Emulator serial;
        try {
            serial = new Emulator( ZToolPacketParserTest.class.getResourceAsStream( "overwritten.packet.fsm" ), false );
            serial.open("VIRTUAL", 19200, new ZToolPacketHandler(){

				public void error(Throwable th) {
				}

				public void handlePacket(ZToolPacket packet) {
	            	synchronized (nPackets) {
	            		if ( nPackets[0] < 0 ) {
	            			return;
	            		}
	                    packets[0] = packet;
	                    if( packet.isError() ){
	                    	nPackets[0]=-1;
	                    } else {
	                        nPackets[0]++;                    	
	                    }
	            		logger.debug("Received packet, total packet received is {}",nPackets[0]);
					}
				}
            	
            });
        } catch (IOException e) {
            serial = null;
        }
        
        final long delta = 250;
        long time = 0;
        final ZToolPacketParser parser = serial.getParser();
        synchronized (nPackets) {
            while( nPackets[0] >= 0 && nPackets[0] < TOTAL_GOD_PACKET
            	&& parser.getInternalThread() != null && parser.getInternalThread().isAlive() 
            	&& time < delta*10
            ){
            	logger.debug(
            			"Waiting for one of the following: " +
            			"BAD PACKET, enough packet received, parser dead, timout {}", delta*10 
            	);
            	try {
					nPackets.wait(delta);
				} catch (InterruptedException e) {
				}
				time+=delta;
            }
		}
        serial.close();
        assertTrue("Notified of a bad packet", packets[0].isError() == false);
        assertEquals("Parsed less input packet then available", TOTAL_GOD_PACKET, nPackets[0]);
    }

}
