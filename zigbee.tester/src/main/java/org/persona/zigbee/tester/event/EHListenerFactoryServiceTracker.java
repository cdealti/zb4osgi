/*

   Copyright 2008-2013 CNR-ISTI, http://isti.cnr.it
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
package org.persona.zigbee.tester.event;

import java.util.ArrayList;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.persona.zigbee.tester.event.api.EHListenerFactoryService;


/**
 * 
 * This class track all the {@link EHListenerFactoryService} installed on the OSGi Framework
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 799 $ ($LastChangedDate: 2013-08-06 18:00:05 +0200(mar, 06 ago 2013) $)
 * @since 0.3.0
 */
public class EHListenerFactoryServiceTracker implements ServiceListener {

	private BundleContext bc;
	private ArrayList<EHListenerFactoryService> services = new ArrayList<EHListenerFactoryService>();

	public EHListenerFactoryServiceTracker(BundleContext context){
		bc = context;
		try {
			context.addServiceListener(
				this, "(" + Constants.OBJECTCLASS + "=" + EHListenerFactoryService.class.getName() + ")"
			);
		} catch (InvalidSyntaxException ignored) {
		}
		
		synchronized (this) {
			try {
				ServiceReference[] references = context.getServiceReferences(
						EHListenerFactoryService.class.getName(), null
				);
				if ( references == null ) {
					return;
				}
				for (int i = 0; i < references.length; i++) {
					addService(references[i]);
				}
			} catch (InvalidSyntaxException ignored) {
			}
		}
	}
	
	public void serviceChanged(ServiceEvent event) {
		synchronized (this) {
			switch (event.getType()) {
				case ServiceEvent.REGISTERED:
					addService(event.getServiceReference());
				break;
				case ServiceEvent.UNREGISTERING:
					removeService(event.getServiceReference());
				break;
				case ServiceEvent.MODIFIED:
					removeService(event.getServiceReference());
					addService(event.getServiceReference());
				break;
			}
		}
	}

	private void removeService(ServiceReference serviceReference) {
		services.remove((EHListenerFactoryService) bc.getService(serviceReference));
	}

	private void addService(ServiceReference serviceReference) {
		services.add((EHListenerFactoryService) bc.getService(serviceReference));
	}

	public EHListenerFactoryService[] getEHListenerFactoryService(){
		return services.toArray(new EHListenerFactoryService[]{});
	}
}
