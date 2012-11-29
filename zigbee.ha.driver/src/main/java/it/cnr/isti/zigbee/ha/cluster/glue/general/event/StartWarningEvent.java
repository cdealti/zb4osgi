package it.cnr.isti.zigbee.ha.cluster.glue.general.event;

import it.cnr.isti.zigbee.ha.cluster.glue.Cluster;

public interface StartWarningEvent {

	public boolean getEvent();
	public Cluster getSource();
}