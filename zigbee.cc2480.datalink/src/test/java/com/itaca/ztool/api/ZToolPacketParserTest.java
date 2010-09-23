package com.itaca.ztool.api;

import static org.junit.Assert.*;

import java.io.IOException;

import it.cnr.isti.cc2480.low.HWLowLevelDriver;
import it.cnr.isti.cc2480.low.PacketListener;
import it.cnr.isti.cc2480.virutal.Emulator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ZToolPacketParserTest {

    int[] nPackets = new int[1];

    private final static Logger logger = LoggerFactory.getLogger(ZToolPacketParserTest.class);    
    
    @Test
    public void testOverwrittenPacketHandling() {
        final HWLowLevelDriver driver = new HWLowLevelDriver();
        final ZToolPacket[] packets = new ZToolPacket[1];        
        Emulator serial;
        try {
            serial = new Emulator( ZToolPacketParserTest.class.getResourceAsStream( "overwritten.packet.fsm" ), false );
        } catch (IOException e) {
            serial = null;
        }
        driver.addPacketListener(new PacketListener(){
            public void packetRecieved(ZToolPacket packet) {
            	synchronized (nPackets) {
            		if ( nPackets[0] < 0 ) {
            			return;
            		}
                    packets[0]=packet;
                    if( packet.isError() ){
                    	nPackets[0]=-1;
                    } else {
                        nPackets[0]++;                    	
                    }
            		logger.debug("Received packet, total packet received is {}",nPackets[0]);
				}
            }           
        });
        driver.setSerialHandler(serial);
        try {
            driver.open("VIRTUAL", 19200);
        } catch (ZToolException ex) {
            ex.printStackTrace();
        }
        synchronized (nPackets) {
            while( nPackets[0] >= 0 && nPackets[0] < 10){
            	try {
					nPackets.wait(1000);
				} catch (InterruptedException e) {
				}
            }
		}
        driver.close();
        assertTrue("Notified of a bad packet", packets[0].isError() == false);
    }

}
