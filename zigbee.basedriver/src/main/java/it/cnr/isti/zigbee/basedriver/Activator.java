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

//import it.cnr.isti.zb4osgi.api.Eventing;
import it.cnr.isti.zigbee.api.ZigBeeDevice;
import it.cnr.isti.zigbee.basedriver.api.impl.ZigBeeDeviceImpl;
import it.cnr.isti.zigbee.basedriver.communication.SimpleDriverServiceTracker;
import it.cnr.isti.zigbee.basedriver.configuration.ConfigurationService;
import it.cnr.isti.zigbee.dongle.api.SimpleDriver;

import java.util.ArrayList;
import java.util.HashMap;
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
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public class Activator implements BundleActivator {

	public static final String BASEDRIVER_CONFIG_PID = ConfigurationService.PID;

	private static final String FILTER_SIMPLE_DRIVER_SERVICE = "(" + Constants.OBJECTCLASS + "=" + SimpleDriver.class.getName() + ")";

	private static BundleContext context;
	private static ConfigurationService configuration;

	private static Object singelton = new Object();

	/**
	 * This variable contains the list of {@link ServiceRegistration} referring to the<br>
	 * {@link ZigBeeDeviceImpl} service registered by the Base Driver as {@link ZigBeeDevice}.<br>
	 * The {@link ServiceRegistration} are indexed by IEEE represented as {@link String} with format 01:23:45:67:89:AB:CD:EF<br>
	 * If you want to obtain {@link ZigBeeDeviceImpl} you can use the code<br>
	 * <code><pre>
        ZigBeeNodeImpl node = null;

        ArrayList<ServiceRegistration> devicesOnNode = devices.get(node.getIEEEAddress());
        ServiceRegistration registration = devicesOnNode.get( 0 );
        ZigBeeDeviceImpl dev = (ZigBeeDeviceImpl) 
        Activator.getBundleContext().getService( registration.getReference() );
	 * </pre></code> 
	 */
	public static final HashMap<String, ArrayList<ServiceRegistration> > devices = new HashMap<String, ArrayList<ServiceRegistration>>();

	private SimpleDriverServiceTracker tracker;
	//public static Eventing ev;

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
			Activator.context = bc;
		}
		registerConfigurableService();
		registerSimpleDriverTracker();
		//registerEventingService();
	}

	/*private void registerEventingService(){

		ev = null;
		try{
			ServiceReference g = Activator.getBundleContext().getServiceReference(Eventing.class.getName());
			if(g != null)
				ev = (Eventing) Activator.getBundleContext().getService(g);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}*/

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

		synchronized ( devices ) {
			for (ArrayList<ServiceRegistration> registrations : devices.values()) {
				for ( ServiceRegistration registration : registrations ) {
					registration.unregister();
				}
			}
			devices.clear();
		}
	}

	public void stop(BundleContext bc) throws Exception {
		unregisterAllDeviceService();
		unregisterSimpleDriverTracker();
		synchronized (singelton) {
			Activator.context = null;
		}
	}

	/*public static Eventing getEventingService(){
		return ev;
	}*/
	/**
	 * <b>DO NOT USE!! IT IS NEEDED ONLY FOR TEST UNIT PURPOSE</b><br>
	 * 
	 * @param cs
	 * @param bc
	 * @since 0.6.0 -  Revision 60
	 */
	public static final void setStubObjectes(ConfigurationService cs, BundleContext bc) {
		synchronized (singelton) {
			configuration = cs;
			context = bc;
		}
	}

	public static final ConfigurationService getCurrentConfiguration() {
		synchronized (singelton) {
			return configuration;
		}
	}

	public static final BundleContext getBundleContext() {
		synchronized ( singelton ) {
			return context;
		}		
	}	
}