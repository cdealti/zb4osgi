package it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing;

import it.cnr.isti.zigbee.ha.cluster.glue.Cluster;
import it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.event.MeasuredValueListener;
import it.cnr.isti.zigbee.ha.cluster.glue.measureament_sensing.event.ToleranceListener;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;

public interface IlluminanceMeasurement extends Cluster {

	public Attribute getMeasuredValue();
	public Attribute getMinMeasuredValue();
	public Attribute getMaxMeasuredValue();
	public Attribute getTolerance();
	public Attribute getLightSensorType();

	public boolean subscribe(MeasuredValueListener tl);

	public boolean unsubscribe(MeasuredValueListener tl);

	public boolean subscribe(ToleranceListener tl);

	public boolean unsubscribe(ToleranceListener tl);

	//public boolean subscribe(LightSensorTypeListener tl);

	//public boolean unsubscribe(LightSensorTypeListener tl);
}
