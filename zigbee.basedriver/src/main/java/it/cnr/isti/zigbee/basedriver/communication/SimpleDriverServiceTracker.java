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

package it.cnr.isti.zigbee.basedriver.communication;

import it.cnr.isti.zigbee.basedriver.Activator;
import it.cnr.isti.zigbee.basedriver.discovery.AnnunceListnerThread;
import it.cnr.isti.zigbee.basedriver.discovery.DeviceBuilderThread;
import it.cnr.isti.zigbee.basedriver.discovery.ImportingQueue;
import it.cnr.isti.zigbee.basedriver.discovery.NetworkBrowserThread;
import it.cnr.isti.zigbee.dongle.api.DriverStatus;
import it.cnr.isti.zigbee.dongle.api.SimpleDriver;

import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is tracks the {@link SimpleDriver} service avaialable on the OSGi framework<br>
 * and it creates all the resources required by this implementation of the <i>ZigBee Base Driver</i>
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.6.0
 *
 */
public class SimpleDriverServiceTracker implements ServiceListener{

	private final static Logger logger = LoggerFactory.getLogger(SimpleDriverServiceTracker.class);
	
	private final Object driverLock = new Object();
	
	private ServiceReference driverReference;
	private SimpleDriver driverService;
	private final AnnunceListnerThread annunceListener;
	private NetworkBrowserThread networkBrowser;
	private DeviceBuilderThread deviceBuilder;
	private final ImportingQueue importingQueue;
	
	public SimpleDriverServiceTracker(){
		importingQueue = new ImportingQueue();
		annunceListener = new AnnunceListnerThread(importingQueue);	
		
		ServiceReference ref = Activator.getBundleContext().getServiceReference(SimpleDriver.class.getName());
		if(ref != null) addingSimpleDriver(ref);
	}
	
	public void serviceChanged(ServiceEvent se) {
		switch (se.getType()) {
			case ServiceEvent.REGISTERED:{
				addingSimpleDriver(se.getServiceReference());
			}break;
			case ServiceEvent.UNREGISTERING:{
				removingSimpleDriver(se.getServiceReference());
			}break;
		}
	}

	private void setDownZigBeeImporter() {
		logger.info("Driver used left:clean up all the data and closing all the threads");
	
		Activator.getCurrentConfiguration().setDriver(null);
		networkBrowser.end();
		deviceBuilder.end();
		Activator.unregisterAllDeviceService();
	}
	
	private void removingSimpleDriver(ServiceReference sr) {
		logger.info("A service of {} is leaving", SimpleDriver.class);
		synchronized (driverLock) {
			if ( driverReference == sr ) {
				driverService.removeAnnunceListener(annunceListener);
				Activator.getBundleContext().ungetService(driverReference);
				driverReference = null;
			} else {
				return;
			}
		}
		driverService = null;
		setDownZigBeeImporter();
	}
	
	
	
	private void setUpZigBeeImporter() {		
		logger.info("Setting up all the importer data and threads");	
		Activator.getCurrentConfiguration().setDriver(driverService);
		importingQueue.clear();
		AFLayer.getAFLayer(driverService);
		driverService.addAnnunceListener(annunceListener);
		networkBrowser = new NetworkBrowserThread(importingQueue,  driverService );
		
		deviceBuilder = new DeviceBuilderThread( importingQueue, driverService); 
		Thread[] importing = new Thread[]{
				new Thread(networkBrowser, "NetworkBrowser["+driverService+"]"),
				new Thread(deviceBuilder, "DeviceBuilder["+driverService+"]")
		};
		for (int i = 0; i < importing.length; i++) {
			importing[i].start();
		}
	}
	
	private void addingSimpleDriver(ServiceReference sr) {
		logger.info("New {} service found", SimpleDriver.class);
		synchronized (driverLock) {
			if ( driverReference == null ) {
				driverReference = sr;
			}else{
				return;
			}
		}
		driverService = (SimpleDriver) Activator.getBundleContext().getService(driverReference);
		setUpZigBeeImporter();
	}
	
}
