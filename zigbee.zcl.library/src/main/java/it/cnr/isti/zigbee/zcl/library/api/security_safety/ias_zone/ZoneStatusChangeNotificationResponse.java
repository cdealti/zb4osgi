package it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_zone;

import it.cnr.isti.zigbee.zcl.library.api.core.Response;

public interface ZoneStatusChangeNotificationResponse extends Response {

	public static final byte ID = 0x00;

	public short getZoneStatus();
	public byte getExtendedStatus();
}
