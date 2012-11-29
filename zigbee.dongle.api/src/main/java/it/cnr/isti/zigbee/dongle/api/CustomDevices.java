package it.cnr.isti.zigbee.dongle.api;


public interface CustomDevices {

	public final static String ENDPOINT = "-1";
	public final static String ENDPOINT_KEY = "it.cnr.isti.zigbee.custom.device.endpoint";

	public final static String PROFILE_ID = "-1";
	public final static String PROFILE_ID_KEY = "it.cnr.isti.zigbee.custom.device.profileID";

	public final static String DEVICE_ID = "-1";
	public final static String DEVICE_ID_KEY = "it.cnr.isti.zigbee.custom.device.deviceID";

	public final static String VERSION = "-1";
	public final static String VERSION_KEY = "it.cnr.isti.zigbee.custom.device.version";

	public final static String INPUT_CLUSTERS = "";
	public final static String INPUT_CLUSTERS_KEY = "it.cnr.isti.zigbee.custom.device.inputClusters";

	public final static String OUTPUT_CLUSTERS = "";
	public final static String OUTPUT_CLUSTERS_KEY = "it.cnr.isti.zigbee.custom.device.outputClusters";
}
