package it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.event;

import it.cnr.isti.zigbee.ha.cluster.glue.Cluster;

public interface LightSensorTypeEvent {

	public int getEvent();

	public Cluster getSource();
}
