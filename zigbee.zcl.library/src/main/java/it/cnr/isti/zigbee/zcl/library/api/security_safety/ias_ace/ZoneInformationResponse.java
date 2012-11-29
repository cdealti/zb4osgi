package it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_ace;

import it.cnr.isti.zigbee.zcl.library.api.core.Response;

public interface ZoneInformationResponse extends Response {

	public static final byte ID = 0x02;

	public int getZoneID();
	public short getZoneType();
	public String getIEEEAddress();
}