/*
   Copyright 2008-2010 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies 
   of the Italian National Research Council 


   See the NOTICE file distributed with this work for additional 
   information regarding copyright ownership

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package it.cnr.isti.zigbee.basedriver.discovery;

import it.cnr.isti.thread.Stoppable;
import it.cnr.isti.thread.ThreadUtils;
import it.cnr.isti.zigbee.api.ZigBeeBasedriverException;
import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.api.ZigBeeNode;
import it.cnr.isti.zigbee.basedriver.Activator;
import it.cnr.isti.zigbee.basedriver.api.impl.ZigBeeDeviceImpl;
import it.cnr.isti.zigbee.basedriver.api.impl.ZigBeeNodeImpl;
import it.cnr.isti.zigbee.basedriver.communication.AFLayer;
import it.cnr.isti.zigbee.basedriver.discovery.ImportingQueue.ZigBeeNodeAddress;
import it.cnr.isti.zigbee.dongle.api.SimpleDriver;
import it.cnr.isti.zigbee.util.IEEEAddress;

import java.util.ArrayList;

import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itaca.ztool.api.ZToolAddress16;
import com.itaca.ztool.api.ZToolAddress64;
import com.itaca.ztool.api.zdo.ZDO_ACTIVE_EP_REQ;
import com.itaca.ztool.api.zdo.ZDO_ACTIVE_EP_RSP;

/**
 * 
 * This class implements the {@link Thread} that completes the discovery of the node<br>
 * found either by {@link NetworkBrowserThread} or {@link AnnunceListnerThread} by<br>
 * inspecting the <i>End Point</i> on the node.<br>
 * The inspection of each <i>End Point</i> lead to the creation {@link ZigBeeDevice}<br>
 * service, that is registered on the OSGi framework.
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public class DeviceBuilderThread implements Stoppable{

	private static final Logger logger = LoggerFactory.getLogger(DeviceBuilderThread.class);
	
	private final ImportingQueue queue;
	private final ArrayList<ZigBeeDeviceReference> failedDevice = new ArrayList<ZigBeeDeviceReference>();
	
	private final SimpleDriver driver;
	private boolean end;

    private long nextInspectionSlot = 0;
	
	private class ZigBeeDeviceReference{
		ZigBeeNode node;
		byte endPoint;
		
		ZigBeeDeviceReference(ZigBeeNode node, byte endPoint) {
			super();
			this.node = node;
			this.endPoint = endPoint;
		}
	}
	
	public DeviceBuilderThread(ImportingQueue queue, SimpleDriver driver) {
		this.queue = queue;
		this.driver = driver;
	}
	

	private ZDO_ACTIVE_EP_RSP doInspectDeviceOfNode(final int nwkAddress, final ZigBeeNode node){
		//TODO Move into SimpleDriver?!?!?
		logger.info("Inspecting device on node #{} by issuing ZDO_ACTIVE_EP_REQ", nwkAddress);
		
		int i = 0;
		ZDO_ACTIVE_EP_RSP result = null;
		
		while (i < Activator.getCurrentConfiguration().getMessageRetryCount()) {
			result = driver.sendZDOActiveEndPointRequest(
					new ZDO_ACTIVE_EP_REQ(nwkAddress)
			);
			if( result == null ){
				final long waiting = Activator.getCurrentConfiguration().getMessageRetryDelay();
				logger.debug(
						"Inspecting device on node {} failed during it {}-nth attempt. " +
						"Waiting for {}ms before retrying",
						new Object[]{node, i, waiting}
				);
				ThreadUtils.waitNonPreemptive(waiting);
				i++;
			} else {
				break;
			}
		}
				
		return result;
	}
	
	private void inspectDeviceOfNode(final int nwkAddress, final ZigBeeNode node) {
		
		final ZDO_ACTIVE_EP_RSP result  = doInspectDeviceOfNode(nwkAddress, node);
		if( result == null ){	
			logger.warn("ZDO_ACTIVE_EP_REQ FAILED on {}", node);
			return;
		}
		
		byte[] endPoints = result.getActiveEndPointList();
		logger.info("ZDO_ACTIVE_EP_REQ SUCCESS with {} from {}", endPoints.length, node);		
		for (int i = 0; i < endPoints.length; i++) {
			doCreateZigBeeDeviceService(node, endPoints[i]);
		}
	}

	private void doCreateZigBeeDeviceService(ZigBeeNode node, byte ep) {
        final ZigBeeNetwork network = AFLayer.getAFLayer(driver).getZigBeeNetwork();
        synchronized (network) {
            if( network.containsDevice(node.getIEEEAddress(), ep) ){
                logger.info(
                    "Skipping service creation for endpoint {} on node {} it is already registered as a Service", ep, node  
                );
                return ;
            }else{
                logger.info(
                        "Creating {} service for endpoint {} on node {}", 
                        new Object[]{ ZigBeeDevice.class, ep, node }
                    );
            }
    
        }
		try {
			ZigBeeDeviceImpl device = new ZigBeeDeviceImpl(driver, node, ep);
			if ( network.addDevice(device)  ) { 
    			ServiceRegistration registration = Activator.getBundleContext().registerService(
    					ZigBeeDevice.class.getName(),
    					device,
    					device.getDescription()
    			);
    			ArrayList<ServiceRegistration> list;
    			synchronized ( Activator.devices ) {
    			    final String ieee = node.getIEEEAddress();
    			    list = Activator.devices.get( ieee );
    			    if ( list == null ) {
                        list = new ArrayList<ServiceRegistration>();
                        Activator.devices.put( ieee, list );
                    }
                }
    			synchronized ( list ) {
    			    list.add( registration );
                }
			} else {
			    logger.error( "Failed to add endpoint {} to the network map for node {}", ep, node );
			}
		} catch (ZigBeeBasedriverException e) {
			logger.error("Error building the device:",e);
			failedDevice.add(new ZigBeeDeviceReference(node, ep)); 
		}
	}

	private void inspectNode(ZToolAddress16 nwkAddress, ZToolAddress64 ieeeAddress) {
		int nwk = nwkAddress.get16BitValue();
		final String ieee = IEEEAddress.toString(ieeeAddress.getLong());
		ZigBeeNodeImpl node = null;
		boolean isNew = false;
		final ZigBeeNetwork network = AFLayer.getAFLayer(driver).getZigBeeNetwork();
		synchronized (network) {
			node = network.containsNode(ieee);
			if( node == null ){
				node = new ZigBeeNodeImpl(nwk, ieeeAddress);
				isNew = true;
	            network.addNode(node);
                logger.debug( "Created node object for {} that was not available on the network", node );
			} 
		}
		if( isNew ){
		    inspectDeviceOfNode(nwk, node);
		} else if( node.getNetworkAddress() != nwk ) {
            logger.warn(
                "The device {} has been found again with a new network address {} ",
                node, nwkAddress.get16BitValue() 
            );
            if ( ! changedNetworkAddress( node, nwk ) ) {
                /*
                 * No previous device inspection completed successfully, so we should try to inspect
                 * the device agagin 
                 */
                inspectDeviceOfNode( nwk, new ZigBeeNodeImpl( nwk, node.getIEEEAddress() ) );
            } 
            node.setNetworkAddress( nwk );
        } 		
	}	

	/**
	 * This method updates the network address on all the device belonging the node<br>
	 * with the change network address<br>
	 * 
     * @param node {@link ZigBeeNodeImpl} the old node with the obsoleted network address
     * @param nwk the new network address of the node
     * @return if at least a device has been updated
     * @since 0.6.0 - Revision 74
     */
    private boolean changedNetworkAddress( ZigBeeNodeImpl node, int nwk ) {
        /*
         * This may happen either for two reason:
         *  A - Device has re-joined the network, it may happen either in end-user or
         *      ZigBee developer environment
         *  B - Device has been re-programmed and it joins as new device on the network,
         *      it could happen only on ZigBee developer environment 
         * The actual code handle only the case A 
         */
        final ArrayList<ServiceRegistration> registrations;
        synchronized ( Activator.devices ) {
            registrations = Activator.devices.get( node.getIEEEAddress() ); 
        }
        if ( registrations ==  null ) {
            logger.info( "No registered service to updated even if we identified a network address changing" );
            return false; 
        }
        boolean changed = false;
        /*
         * //TODO For covering case B: we should compare the content of "registrations"
         * with the actual list of End Point on the node    
         */
        for ( ServiceRegistration registration : registrations ) {
            final ZigBeeDeviceImpl device = 
                (ZigBeeDeviceImpl) Activator.getBundleContext().getService( registration.getReference() );
            if ( device.setPhysicalNode( new ZigBeeNodeImpl( nwk, node.getIEEEAddress() ) ) ) {
                changed = true;
                registration.setProperties( device.getDescription() );                
            }
        }
        
        return changed;
    }


    private void inspectNewlyDevice(){
        logger.info("Trying to register a node extracted from ImportingQueue");
        final ZigBeeNodeAddress dev = queue.pop();
        final ZToolAddress16 nwk = dev.getNetworkAddress();
        final ZToolAddress64 ieee = dev.getIEEEAddress();
        logger.info("Creating a new set of services for ZigBee Node {} ({})",nwk,ieee);
        nextInspectionSlot = Activator.getCurrentConfiguration().getDeviceInspectionPeriod() + System.currentTimeMillis();
        inspectNode(nwk, ieee);
	}
	
	private void inspecFailedDevice(){
	    //TODO We should add a statistical history for removing a device when we tried it too many times
        logger.info("Trying to register a node extracted from FailedQueue");
        final ZigBeeDeviceReference failed = failedDevice.remove(0); 
        nextInspectionSlot = Activator.getCurrentConfiguration().getDeviceInspectionPeriod() + System.currentTimeMillis();
        doCreateZigBeeDeviceService(failed.node, failed.endPoint);
	}
	
	/**
	 * @return the number of Node waiting for inspection
	 * @since 0.6.0 - Revision 71
	 */
	public int getPendingNodes() {
	    return queue.size();
	}
	
    /**
     * @return the number of Node waiting for inspection
     * @since 0.6.0 - Revision 71
     */
    public int getPendingDevices() {
        return failedDevice.size();
    }
    
	public void run() {
		logger.info("{} STARTED Successfully", Thread.currentThread().getName() );
		
		while(!isEnd()){
			try{
                ThreadUtils.waitingUntil( nextInspectionSlot );
			    
                if ( queue.size() > 0 && failedDevice.size() > 0 ){
                    double sel = Math.random();
                    if( sel > 0.6 ) {
                        inspecFailedDevice();
                    } else {
                        inspectNewlyDevice();
                    }
                } else if ( queue.size() == 0 && failedDevice.size() > 0 ){
                    inspecFailedDevice();
                } else if ( queue.size() > 0 && failedDevice.size() == 0 ){
                    inspectNewlyDevice();
                } else if ( queue.size() == 0  && failedDevice.size() == 0 ){
                    inspectNewlyDevice();
                }
                
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		logger.info("{} TERMINATED Successfully", Thread.currentThread().getName());
	}

	public synchronized boolean isEnd() {
		return end;
	}
	
	public synchronized void end() {
		end = true;
	}
	
}
