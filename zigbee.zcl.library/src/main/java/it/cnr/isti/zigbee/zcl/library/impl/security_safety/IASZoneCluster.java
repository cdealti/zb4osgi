package it.cnr.isti.zigbee.zcl.library.impl.security_safety;

import it.cnr.isti.zigbee.api.Cluster;
import it.cnr.isti.zigbee.api.ClusterFilter;
import it.cnr.isti.zigbee.api.ClusterListner;
import it.cnr.isti.zigbee.api.ZigBeeBasedriverException;
import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.zcl.library.api.core.Attribute;
import it.cnr.isti.zigbee.zcl.library.api.core.Response;
import it.cnr.isti.zigbee.zcl.library.api.core.ZigBeeClusterException;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.IASZone;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_zone.ZoneEnrollRequestPayload;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_zone.ZoneEnrollResponse;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_zone.ZoneStatusChangeNotificationListener;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_zone.ZoneStatusChangeNotificationPayload;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_zone.ZoneStatusChangeNotificationResponse;
import it.cnr.isti.zigbee.zcl.library.impl.attribute.Attributes;
import it.cnr.isti.zigbee.zcl.library.impl.core.AttributeImpl;
import it.cnr.isti.zigbee.zcl.library.impl.core.ResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.core.ZCLClusterBase;
import it.cnr.isti.zigbee.zcl.library.impl.global.DefaultResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_zone.IAS_ZoneZoneStatusChangeNotificationFilter;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_zone.ZoneEnrollRequestCommand;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_zone.ZoneEnrollResponseImpl;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_zone.ZoneStatusChangeNotificationCommand;
import it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_zone.ZoneStatusChangeNotificationResponseImpl;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IASZoneCluster extends ZCLClusterBase implements IASZone {

	private final AttributeImpl attributeZoneState;
	private final AttributeImpl attributeZoneType;
	private final AttributeImpl attributeZoneStatus;
	private final AttributeImpl attributeIASCIEAddress;

	private final ArrayList<ZoneStatusChangeNotificationListener> listeners = new ArrayList<ZoneStatusChangeNotificationListener>();
	private ZoneStatusChangeNotificationListenerNotifier bridge;

	private final Attribute[] attributes;

	private final Logger log = LoggerFactory.getLogger(IASZoneCluster.class);

	public IASZoneCluster(ZigBeeDevice zbDevice) {

		super(zbDevice);

		attributeZoneState = new AttributeImpl(zbDevice,this,Attributes.ZONE_STATE);
		attributeZoneType = new AttributeImpl(zbDevice,this,Attributes.ZONE_TYPE);
		attributeZoneStatus = new AttributeImpl(zbDevice,this,Attributes.ZONE_STATUS);
		attributeIASCIEAddress = new AttributeImpl(zbDevice,this,Attributes.IAS_CIE_ADDRESS);

		attributes = new AttributeImpl[]{attributeZoneState, attributeZoneType, attributeZoneStatus, attributeIASCIEAddress};

		bridge = new ZoneStatusChangeNotificationListenerNotifier();
	}	

	public Attribute getAttributeZoneState() {

		return attributeZoneState;
	}

	public Attribute getAttributeZoneType() {

		return attributeZoneType;
	}

	public Attribute getAttributeZoneStatus() {

		return attributeZoneStatus;
	}

	public Attribute getAttributeIASCIEAddress() {

		return attributeIASCIEAddress;
	}

	@Override
	public short getId() {

		return IASZone.ID;
	}

	@Override
	public String getName() {

		return IASZone.NAME;
	}

	@Override
	public Attribute[] getStandardAttributes() {

		return attributes;
	}

	public Response zoneStatusChangeNotification(ZoneStatusChangeNotificationPayload payload) throws ZigBeeClusterException {

		enableDefaultResponse();
		ZoneStatusChangeNotificationCommand zscnc = new ZoneStatusChangeNotificationCommand(payload);
		Response response = invoke(zscnc);
		return new DefaultResponseImpl(response);
	}

	public ZoneEnrollResponse zoneEnrollRequest(ZoneEnrollRequestPayload payload) throws ZigBeeClusterException {

		ZoneEnrollRequestCommand zoneEnrCmd = new ZoneEnrollRequestCommand(payload);
		Response response = invoke(zoneEnrCmd, false);
		return new ZoneEnrollResponseImpl(response);
	}

	public boolean addZoneStatusChangeNotificationListener(ZoneStatusChangeNotificationListener listener) {
		synchronized (listeners) {
			if ( listeners.size() == 0 ){
				try {
					getZigBeeDevice().bind(ID);
				} catch (ZigBeeBasedriverException e) {
					log.error("Unable to bind to device for IASZone reporting", e);
					return false;
				}
				if ( getZigBeeDevice().addClusterListener(bridge) == false ) {
					log.error("Unable to register the cluster listener for IASZone reporting");
					return false;
				}
			}
			listeners.add(listener);
			return true;		
		}
	}

	public boolean removeZoneStatusChangeNotificationListener(ZoneStatusChangeNotificationListener listener) {
		synchronized (listeners) {
			boolean removed = listeners.remove(listener); 
			if ( listeners.size() == 0 && removed ){
				try {
					getZigBeeDevice().unbind(ID);
				} catch (ZigBeeBasedriverException e) {
					log.error("Unable to unbind to device for IASZone reporting", e);
					return false;
				}
				if ( getZigBeeDevice().removeClusterListener(bridge) == false ) {
					log.error("Unable to unregister the cluster listener for IASZone reporting");
					return false;
				}
			}
			return removed;		
		}
	}

	private class ZoneStatusChangeNotificationListenerNotifier implements ClusterListner{

		public void setClusterFilter(ClusterFilter filter) {
		}

		public ClusterFilter getClusterFilter() {
			return IAS_ZoneZoneStatusChangeNotificationFilter.FILTER;
		}

		public void handleCluster(ZigBeeDevice device, Cluster c) {
			try {
				ResponseImpl response = new ResponseImpl(c, ID);
				ZoneStatusChangeNotificationResponse zscnr = new ZoneStatusChangeNotificationResponseImpl(response);
				ArrayList<ZoneStatusChangeNotificationListener> localCopy;
				synchronized (listeners) {
					localCopy = new ArrayList<ZoneStatusChangeNotificationListener>(listeners);					
				}
				log.debug("Notifying {} ZoneStatusChangeNotificationListener", localCopy.size());
				for (ZoneStatusChangeNotificationListener alarmListner : localCopy) {
					try{
						log.debug("Notifying {}:{}", alarmListner.getClass().getName(), alarmListner);
						alarmListner.zoneStatusChangeNotification(zscnr.getZoneStatus());
					}catch(Exception e){
						log.error("Error while notifying {}:{} caused by {}",new Object[]{
								alarmListner.getClass().getName(), alarmListner, e.getStackTrace() 
						});
					}
				}

			} catch (ZigBeeClusterException e) {
				e.printStackTrace();
			}
		}
	}
}