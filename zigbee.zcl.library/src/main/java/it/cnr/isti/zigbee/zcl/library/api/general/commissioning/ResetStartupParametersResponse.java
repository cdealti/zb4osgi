package it.cnr.isti.zigbee.zcl.library.api.general.commissioning;

import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.Status;

public interface ResetStartupParametersResponse extends Response {

	public static final byte ID = 0x03;

	public Status getStatus();
}
