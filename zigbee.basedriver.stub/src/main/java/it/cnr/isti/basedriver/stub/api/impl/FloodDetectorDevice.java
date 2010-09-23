package it.cnr.isti.basedriver.stub.api.impl;

import it.cnr.isti.zigbee.api.Cluster;
import it.cnr.isti.zigbee.api.ZigBeeBasedriverException;
import it.cnr.isti.zigbee.api.ZigBeeNode;

public class FloodDetectorDevice extends StubZigbeeDeviceBase {

	public FloodDetectorDevice(int endpoint, final ZigBeeNode node){
		super(
				0x0509, 0, endpoint, 0x0104, 
			new int[]{
					0x0000, //Basic
					0x0003, //Identify
					0x0004, //Scenes
					0x0005, //Group
					(short) 0xFC0A, //FloodMeasurement
					0x0402  //TemperatureMeasurement
			}, 
			new int[]{}, node
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

}
