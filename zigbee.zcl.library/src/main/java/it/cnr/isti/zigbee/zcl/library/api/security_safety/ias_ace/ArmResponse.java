package it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_ace;

import it.cnr.isti.zigbee.zcl.library.api.core.Response;

public interface ArmResponse extends Response {
	
	public static final byte ID = 0x00;
	
	public byte getArmNotification();
}