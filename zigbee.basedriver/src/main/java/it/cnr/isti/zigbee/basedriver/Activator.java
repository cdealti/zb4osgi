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

package it.cnr.isti.zigbee.basedriver;

import it.cnr.isti.zigbee.basedriver.communication.SimpleDriverServiceTracker;
import it.cnr.isti.zigbee.basedriver.configuration.ConfigurationService;
import it.cnr.isti.zigbee.dongle.api.SimpleDriver;

import java.util.ArrayList;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedService;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 728 $ ($LastChangedDate: 2010-03-12 22:50:38 +0100 (Fri, 12 Mar 2010) $)
 * @since 0.1.0
 *
 */
public class Activator implements BundleActivator {

	public static final String BASEDRIVER_CONFIG_PID = ConfigurationService.PID;

	private static final String FILTER_SIMPLE_DRIVER_SERVICE = 
		"(" + Constants.OBJECTCLASS + "=" + SimpleDriver.class.getName() + ")";
	
	private static BundleContext bc;
	private static ConfigurationService configuration;
	
	private static Object singelton = new Object();
	
	public static final ArrayList<ServiceRegistration> devices = new ArrayList<ServiceRegistration>();
	
	public static final BundleContext getBundleContext() {
		synchronized ( singelton ) {
			return bc;
		}		
	}

	private SimpleDriverServiceTracker tracker;	
	
	private void registerConfigurableService(){
		synchronized (singelton) {
			configuration = new ConfigurationService();
		}
		Properties properties = new Properties();
		
		properties.setProperty(Constants.SERVICE_PID, BASEDRIVER_CONFIG_PID);
		
		Activator.getBundleContext().registerService(
				ManagedService.class.getName(), 
				getCurrentConfiguration(),
				properties
		);
	}
	
	
	public void start(BundleContext bc) throws Exception {
		synchronized (singelton) {
			Activator.bc = bc;
		}
		registerConfigurableService();
		registerSimpleDriverTracker();
	}

	private void registerSimpleDriverTracker() {
		tracker = new SimpleDriverServiceTracker();
		try {
			Activator.getBundleContext().addServiceListener(tracker, FILTER_SIMPLE_DRIVER_SERVICE);
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
	}

	private void unregisterSimpleDriverTracker() {
		Activator.getBundleContext().removeServiceListener(tracker);
		tracker = null;
	}
	
	public static void unregisterAllDeviceService() {
		for (ServiceRegistration registration : devices) {
			registration.unregister();
		}
		devices.clear();
	}
	
	public void stop(BundleContext bc) throws Exception {
		unregisterSimpleDriverTracker();
		synchronized (singelton) {
			Activator.bc = null;
		}
	}

	public static final ConfigurationService getCurrentConfiguration() {
		synchronized (singelton) {
			return configuration;
		}
	}
}
