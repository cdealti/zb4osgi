package it.cnr.isti.basedriver.stub.api.impl;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashSet;
import java.util.Properties;

import it.cnr.isti.zigbee.api.Cluster;
import it.cnr.isti.zigbee.api.ClusterListner;
import it.cnr.isti.zigbee.api.ZigBeeBasedriverException;
import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.api.ZigBeeNode;

public abstract class StubZigbeeDeviceBase implements ZigBeeDevice {

	protected final HashSet<ClusterListner> listeners = new HashSet<ClusterListner>();
	private final int deviceId;
	private final short deviceVersion;
	private final short id;
	private final int[] inputs;
	private final int[] outputs;
	private final int profileId;	
	private final ZigBeeNode node;
	private final Properties properties = new Properties();
	private final String uuid;
	
	protected StubZigbeeDeviceBase(
			final int deviceId, final int deviceVersion, final int id, final int profileId,
			final int[] inputs, final int[] outputs, final ZigBeeNode node
	) {
		this.deviceId = deviceId;
		this.deviceVersion = (short) deviceVersion;
		this.id = (short) id;
		Arrays.sort(inputs);
		this.inputs = inputs;
		Arrays.sort(outputs);
		this.outputs = outputs;
		this.profileId = profileId;
		this.node = node;
		
		StringBuffer uuidBuilder = new StringBuffer()
			.append(profileId).append(":")
			.append(deviceId).append(":")
			.append(deviceVersion).append(":")
			.append(id).append("@")
			.append(node.getIEEEAddress());		
		
		uuid = uuidBuilder.toString();
		
		properties.put(ZigBeeDevice.PROFILE_ID, Integer.toString(profileId));
		properties.put(ZigBeeDevice.DEVICE_ID, Integer.toString(deviceId));
		properties.put(ZigBeeDevice.ENDPOINT, Integer.toString(id));
		properties.put(ZigBeeDevice.CLUSTERS_INPUT_ID, inputs);
		properties.put(ZigBeeDevice.CLUSTERS_OUTPUT_ID, outputs);
		properties.put(ZigBeeNode.IEEE_ADDRESS, node.getIEEEAddress());
		properties.put(ZigBeeDevice.UUID, uuidBuilder.toString());		
	}

	public boolean addClusterListener(ClusterListner listner) {
		return listeners.add(listner);
	}
	
	public String getUniqueIdenfier() {
		return uuid;
	}	
	
	@SuppressWarnings("unchecked")
	public Dictionary getDescription(){
		return properties;
	}
	public int getDeviceId() {
		return deviceId;
	}

	public short getDeviceVersion() {
		return deviceVersion;
	}

	public short getId() {
		return id;
	}

	public int[] getInputClusters() {
		return inputs;
	}

	public int[] getOutputClusters() {
		return outputs;
	}

	public int getProfileId() {
		return profileId;
	}

	public abstract Cluster stubInvoke(Cluster cluster) throws ZigBeeBasedriverException;
	
	public Cluster invoke(Cluster cluster) throws ZigBeeBasedriverException {
		if( providesInputCluster( cluster.getId() & 0xFFFF ) == false ){
			throw new ZigBeeBasedriverException("Cluster not registered as input so i can't be invoked");
		}
		Cluster c = stubInvoke(cluster);
		if ( providesInputCluster( c.getId() & 0xFFFF ) == false){
			throw new ZigBeeBasedriverException("Cluster not registered as input so i can't be invoked");
		}
		return c;
	}

	public abstract void stubSend(Cluster cluster) throws ZigBeeBasedriverException;

	public void send(Cluster cluster) throws ZigBeeBasedriverException {
		if( providesInputCluster( cluster.getId() & 0xFFFF ) == false ){
			throw new ZigBeeBasedriverException("Cluster not registered as input so i can't be invoked");
		}
		stubSend(cluster);
	}

	public boolean providesInputCluster(int id) {
		return Arrays.binarySearch(inputs, id) >= 0;
	}

	public boolean providesOutputCluster(int id) {
		return Arrays.binarySearch(outputs, id) >= 0;
	}

	public boolean removeClusterListener(ClusterListner listner) {
		return listeners.add(listner);
	}

	public ZigBeeNode getPhysicalNode(){
		return node;
	}
	
	public boolean bindTo(ZigBeeDevice device, int clusterId) throws ZigBeeBasedriverException{
		//TODO it should implemented somehow
		return false;
	}
	
	public boolean unbindFrom(ZigBeeDevice device, int clusterId) throws ZigBeeBasedriverException{
		//TODO it should implemented somehow
		return false;
	}
	
	public boolean bind(int clusterId) throws ZigBeeBasedriverException {
		//TODO it should implemented somehow
		return false;
	}
	
	public boolean unbind(int arg0) throws ZigBeeBasedriverException {
		//TODO it should implemented somehow
		return false;
	}	
}
