package it.cnr.isti.zigbee.zcl.library.api.security_safety;

import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.ZCLCluster;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_ace.BypassPayload;

public interface IASACE extends ZCLCluster {

	public static final short ID = 0x0501;
	static final String NAME = "IAS ACE";
	static final String DESCRIPTION = "Attributes and commands for any Ancillary Control Equipment device.";

	static final byte ARM = 0x00;
	static final byte BYPASS = 0x01;
	static final byte EMERGENCY = 0x02;
	static final byte FIRE = 0x03;
	static final byte PANIC = 0x04;
	static final byte GET_ZONE_ID_MAP = 0x05;
	static final byte GET_ZONE_INFORMATION = 0x06;

	public Response arm(byte armMode) throws ZigBeeClusterException;
	public void bypass(BypassPayload payload) throws ZigBeeClusterException;
	public void emergency() throws ZigBeeClusterException;
	public void fire() throws ZigBeeClusterException;
	public void panic() throws ZigBeeClusterException;
	public Response getZoneIdMap() throws ZigBeeClusterException;
	public Response getZoneInformation(int zoneID) throws ZigBeeClusterException;
}