package it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_ace;

import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_ace.ZoneTable.Zone;

public interface ZoneIDMapResponse extends Response {

	public static final byte ID = 0x01;

	public Zone[] getZonesID();
}