package it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_zone;

public interface ZoneStatusChangeNotificationPayload {

	public short getZoneStatus();
	public byte getExtendedStatus();
}