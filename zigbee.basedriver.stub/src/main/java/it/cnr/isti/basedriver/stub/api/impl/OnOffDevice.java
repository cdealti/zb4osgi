package it.cnr.isti.basedriver.stub.api.impl;

import it.cnr.isti.zigbee.api.Cluster;
import it.cnr.isti.zigbee.api.ZigBeeBasedriverException;
import it.cnr.isti.zigbee.api.ZigBeeNode;

public class OnOffDevice extends StubZigbeeDeviceBase {

	public OnOffDevice(int endpoint, final ZigBeeNode node){
		super(
			0x0002, 0, endpoint, 0x0104,  
			new int[]{
					0x0000, //Basic
					0x0001, //Power Configuration
					0x0002, //Device Temperature Configuration						
					0x0003, //Identify
					0x0004, //Scenes
					0x0005, //Group
					0x0006, //OnOff
					0x0009, //Alarms
			}, new int[]{
			}, node
		);
	}
	
	@Override
	public Cluster stubInvoke(Cluster cluster) throws ZigBeeBasedriverException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stubSend(Cluster cluster) throws ZigBeeBasedriverException {
		// TODO Auto-generated method stub
		
	}

	public ZigBeeNode getPhysicalNode() {
		// TODO Auto-generated method stub
		return null;
	}
}
