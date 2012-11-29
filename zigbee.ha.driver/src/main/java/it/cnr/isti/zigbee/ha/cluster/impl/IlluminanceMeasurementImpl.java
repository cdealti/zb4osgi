package it.cnr.isti.zigbee.ha.cluster.impl;

import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.ha.Activator;
import it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.IlluminanceMeasurement;
import it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.event.MeasuredValueListener;
import it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.event.ToleranceListener;
import it.cnr.isti.zigbee.ha.cluster.impl.event.MeasuredValueBridgeListeners;
import it.cnr.isti.zigbee.ha.cluster.impl.event.ToleranceBridgeListeners;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Subscription;
import it.cnr.isti.zigbee.zcl.library.impl.measureament_sensing.IlluminanceMeasurementCluster;

public class IlluminanceMeasurementImpl implements IlluminanceMeasurement {

	private final IlluminanceMeasurementCluster cluster;
	
	private final Attribute measuredValue;
	private final Attribute minMeasuredValue;
	private final Attribute maxMeasuredValue;
	private final Attribute tolerance;
	private final Attribute lightSensorType;

	private final MeasuredValueBridgeListeners measureBridge;
	private final ToleranceBridgeListeners toleranceBridge;
	//private final ToleranceBridgeListeners toleranceBridge;

	public IlluminanceMeasurementImpl(ZigBeeDevice zbDevice){

		cluster = new IlluminanceMeasurementCluster(zbDevice);
		measuredValue = cluster.getMeasuredValue();
		minMeasuredValue = cluster.getMinMeasuredValue();
		maxMeasuredValue = cluster.getMaxMeasuredValue();
		tolerance = cluster.getTolerance();
		lightSensorType = cluster.getLightSensorType();

		toleranceBridge = new ToleranceBridgeListeners(Activator.getConfiguration(), tolerance, this);
		measureBridge = new MeasuredValueBridgeListeners(Activator.getConfiguration(), measuredValue, this);
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

	public Attribute getMeasuredValue() {

		return measuredValue;
	}

	public Attribute getMinMeasuredValue() {

		return minMeasuredValue;
	}

	public Attribute getMaxMeasuredValue() {

		return maxMeasuredValue;
	}

	public Attribute getTolerance() {

		return tolerance;
	}

	public Attribute getLightSensorType() {

		return lightSensorType;
	}

	public boolean subscribe(MeasuredValueListener tl) {

		return measureBridge.subscribe(tl);
	}

	public boolean unsubscribe(MeasuredValueListener tl) {

		return measureBridge.unsubscribe(tl);
	}

	public boolean subscribe(ToleranceListener tl) {

		return toleranceBridge.subscribe(tl);
	}

	public boolean unsubscribe(ToleranceListener tl) {

		return toleranceBridge.unsubscribe(tl);
	}
}