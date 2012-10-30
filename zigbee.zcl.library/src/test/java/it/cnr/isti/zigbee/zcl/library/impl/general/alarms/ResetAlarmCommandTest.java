package it.cnr.isti.zigbee.zcl.library.impl.general.alarms;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

public class ResetAlarmCommandTest {

	@Test
	public void testGetPayload() {
		ResetAlarmCommand command = new ResetAlarmCommand( (byte)0x10, (short) 0x2030);
		assertArrayEquals(new byte[]{
				0x10, 0x30, 0x20
		}, command.getPayload()
		);
	}

}
