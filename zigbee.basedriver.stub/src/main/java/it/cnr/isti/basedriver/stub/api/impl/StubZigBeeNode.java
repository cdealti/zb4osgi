package it.cnr.isti.basedriver.stub.api.impl;

import java.util.Dictionary;

import it.cnr.isti.zigbee.api.ZigBeeNode;

public class StubZigBeeNode implements ZigBeeNode {
	
	private String ieee;
	private int nwk;

	public StubZigBeeNode(String ieee, int nwk) {
		this.ieee = ieee;
		this.nwk = nwk;
	}

	@SuppressWarnings("unchecked")
	public Dictionary getDescription() {
		return null;
	}

	public String getIEEEAddress() {
		return ieee;
	}

	public int getNetworkAddress() {
		return nwk;
	}

}
