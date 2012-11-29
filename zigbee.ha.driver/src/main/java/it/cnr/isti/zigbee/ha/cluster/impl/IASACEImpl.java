package it.cnr.isti.zigbee.ha.cluster.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.cluster.glue.security_safety.IASACE;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.Subscription;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_ace.BypassPayload;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_ace.ZoneIDMapResponse;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_ace.ZoneInformationResponse;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.IASACECluster;

public class IASACEImpl implements IASACE {

	private final IASACECluster cluster;

	public IASACEImpl(ZigBeeDevice zbDevice){
		cluster = new IASACECluster(zbDevice);
	}

	public int getId() {
		return cluster.getId();
	}

	public String getName() {
		return cluster.getName();
	}

	public Subscription[] getActiveSubscriptions() {
		return cluster.getActiveSubscriptions();
	}

	public Attribute[] getAttributes() {
		return cluster.getAvailableAttributes();
	}

	public Attribute getAttribute(int id) {
		Attribute[] attributes = cluster.getAvailableAttributes();
		for (int i = 0; i < attributes.length; i++) {
			if( attributes[i].getId() == id ) 
				return attributes[i];
		}
		return null;
	}

	public Response arm(byte armMode) throws ZigBeeHAException {
		try {
			Response response = (Response) cluster.arm(armMode);
			return response;
		} 
		catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public void bypass(BypassPayload payload)	throws ZigBeeHAException {
		try {
			cluster.bypass(payload);
		} 
		catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public void emergency() throws ZigBeeHAException {
		try {
			cluster.emergency();
		} 
		catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public void fire() throws ZigBeeHAException {
		try {
			cluster.fire();
		} 
		catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public void panic() throws ZigBeeHAException {
		try {
			cluster.panic();
		} 
		catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public ZoneIDMapResponse getZoneIdMap() throws ZigBeeHAException {
		try {
			ZoneIDMapResponse response = (ZoneIDMapResponse) cluster.getZoneIdMap();
			return response;
		} 
		catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}

	public ZoneInformationResponse getZoneInformation(int zoneID) throws ZigBeeHAException {
		try {
			ZoneInformationResponse response = (ZoneInformationResponse) cluster.getZoneInformation(zoneID);
			return response;
		} 
		catch (ZigBeeClusterException e) {
			throw new ZigBeeHAException(e);
		}
	}
}