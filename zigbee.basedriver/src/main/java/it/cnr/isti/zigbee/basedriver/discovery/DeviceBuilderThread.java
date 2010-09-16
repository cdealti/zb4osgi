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
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 668 $ ($LastChangedDate: 2009-11-19 18:48:39 +0100 (Thu, 19 Nov 2009) $)
 * @since 0.1.0
 *
 */
public class DeviceBuilderThread implements Stoppable{

	private static final Logger logger = LoggerFactory.getLogger(DeviceBuilderThread.class);
	
	private final ImportingQueue queue;
	private final ArrayList<ZigBeeDeviceReference> failedDevice = new ArrayList<ZigBeeDeviceReference>();
	
	private final SimpleDriver driver;
	private boolean end;
	
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
						"Inspecting device on node #{} failed during it {}-nth attempt. " +
						"Waiting for {}ms before retrying",
						new Object[]{nwkAddress, i, waiting}
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
			logger.warn("ZDO_ACTIVE_EP_REQ FAILED on #{}", nwkAddress);
			return;
		}
		
		byte[] endPoints = result.getActiveEndPointList();
		logger.info("ZDO_ACTIVE_EP_REQ SUCCESS with {} from #{}", endPoints.length, nwkAddress);		
		for (int i = 0; i < endPoints.length; i++) {
			final ZigBeeNetwork network = AFLayer.getAFLayer(driver).getZigBeeNetwork();
			synchronized (network) {
				if( network.contains(node.getIEEEAddress(), endPoints[i]) ){
					logger.info("Skipping service creation for endpoint {} on node {} it is already registered as a Service",endPoints[i],nwkAddress);
					continue;
				}else{
					logger.info(
							"Creating {} service for {}:{}", 
							new Object[]{ZigBeeDevice.class, nwkAddress, endPoints[i]}
						);
				}
	
				doCreateZigBeeDeviceService(node, endPoints[i]);
			}
		}
	}

	private void doCreateZigBeeDeviceService(ZigBeeNode node, byte ep) {
		try {
			ZigBeeDeviceImpl device = new ZigBeeDeviceImpl(driver, node, ep);
			AFLayer.getAFLayer(driver).getZigBeeNetwork().addDevice(device);
			ServiceRegistration registration = Activator.getBundleContext().registerService(
					ZigBeeDevice.class.getName(),
					device,
					device.getDescription()
			);
			Activator.devices.add(registration);
		} catch (ZigBeeBasedriverException e) {
			logger.error("Error building the device:",e);
			failedDevice.add(new ZigBeeDeviceReference(node, ep)); 
		}
	}

	private void inspectNode(ZToolAddress16 nwkAddress, ZToolAddress64 ieeeAddress) {
		int nwk = nwkAddress.get16BitValue();
		logger.info("Creating {} object for {} ", ZigBeeNode.class, nwk);
		ZigBeeNode node = null;
		final ZigBeeNetwork network = AFLayer.getAFLayer(driver).getZigBeeNetwork();
		synchronized (network) {
			node = (ZigBeeNode) network.contains(ieeeAddress.toString());
			if( node == null ){
				node = new ZigBeeNodeImpl(nwk, ieeeAddress);
			}else if( node.getNetworkAddress() != nwkAddress.get16BitValue() ){
				//TODO Handle somehow: should we remove the all registered service?!?!?
			}
			network.addNode(node);
		}
		
		inspectDeviceOfNode(nwk, node);
	}	

	public void run() {
		final String threadName = Thread.currentThread().getName();
		logger.info("{} STARTED Successfully", threadName);
		
		while(!isEnd()){
			try{				
				if ( queue.size()*2 >=  failedDevice.size() ) {
					logger.info("Trying to register a node extracted from ImportingQueue");
					final ZigBeeNodeAddress dev = queue.pop();
					final ZToolAddress16 nwk = dev.getNetworkAddress();
					final ZToolAddress64 ieee = dev.getIEEEAddress();
					logger.info("Creating a new set of services for ZigBee Node {} ({})",nwk,ieee);
					inspectNode(nwk, ieee);
				} else {
					logger.info("Trying to register a node extracted from FailedQueue");
					final ZigBeeDeviceReference failed = failedDevice.remove(0); 
					doCreateZigBeeDeviceService(failed.node, failed.endPoint);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		logger.info("{} TERMINATED Successfully", threadName);
	}

	public synchronized boolean isEnd() {
		return end;
	}
	
	public synchronized void end() {
		end = true;
	}
	
}
