package it.cnr.isti.zigbee.zcl.library.api.general.commissioning;

import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.Status;

public interface RestoreStartupParametersResponse extends Response {

	public static final byte ID = 0x02;

	public Status getStatus();
}
