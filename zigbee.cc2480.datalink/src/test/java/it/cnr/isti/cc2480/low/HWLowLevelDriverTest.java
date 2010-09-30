/**
 * 
 */
package it.cnr.isti.cc2480.low;
import static org.junit.Assert.*;

import java.io.IOException;

import it.cnr.isti.cc2480.virutal.Emulator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itaca.ztool.api.ZToolException;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.api.system.SYS_VERSION;
import com.itaca.ztool.api.system.SYS_VERSION_RESPONSE;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.6.0
 * 
 */
public class HWLowLevelDriverTest {

	/**
	 * Test method for {@link it.cnr.isti.cc2480.low.HWLowLevelDriver#sendPacket(com.itaca.ztool.api.ZToolPacket)}.
	 */
	@Test
	public void testSendPacket() {
		final HWLowLevelDriver driver = new HWLowLevelDriver();
		final ZToolPacket[] packets = new ZToolPacket[1];
        Emulator serial;
		try {
			serial = new Emulator( Emulator.class.getResourceAsStream( "session.fsm" ) );
		} catch (IOException e) {
			serial = null;
		}
		driver.addPacketListener(new PacketListener(){
			public void packetRecieved(ZToolPacket packet) {
				synchronized (packets) {
					packets[0] = packet;
					packets.notify();
				}
			}			
		});
		driver.setSerialHandler(serial);
		try {
			driver.open("VIRTUAL", 19200);
		} catch (ZToolException ex) {
			ex.printStackTrace();
		}
		try {
			driver.sendPacket(new SYS_VERSION());
		} catch (IOException e) {
		}
		long future = System.currentTimeMillis()+5000;
		synchronized (packets) {
			while (packets[0] == null && future > System.currentTimeMillis() ) {
				try {
					packets.wait(250);
				} catch (InterruptedException e) {
				}
			}			
		}
		if( packets[0] == null ){
			fail("Recieved no answer from the emulated transmission");
		}else{
			assertEquals(
					"Recieved the wrong packet from the emulated transmission",
					SYS_VERSION_RESPONSE.class.getName(), 
					packets[0].getClass().getName()
			);
		}
	}

}
