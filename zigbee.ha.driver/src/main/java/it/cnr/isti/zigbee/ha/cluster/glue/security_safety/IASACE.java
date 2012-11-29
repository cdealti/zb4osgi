package it.cnr.isti.zigbee.ha.cluster.glue.security_safety;

import it.cnr.isti.zigbee.ha.cluster.glue.Cluster;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_ace.BypassPayload;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_ace.ZoneIDMapResponse;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_ace.ZoneInformationResponse;

public interface IASACE extends Cluster {

	public Response arm(byte armMode) throws ZigBeeHAException;
	public void bypass(BypassPayload payload) throws ZigBeeHAException;
	public void emergency() throws ZigBeeHAException;
	public void fire() throws ZigBeeHAException;
	public void panic() throws ZigBeeHAException;
	public ZoneIDMapResponse getZoneIdMap() throws ZigBeeHAException;
	public ZoneInformationResponse getZoneInformation(int zoneID) throws ZigBeeHAException;
}