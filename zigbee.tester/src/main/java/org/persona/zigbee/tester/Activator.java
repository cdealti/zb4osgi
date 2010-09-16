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

package org.persona.zigbee.tester;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.persona.zigbee.tester.event.HAListenerFactoryServiceTracker;

/**
 * 
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 747 $ ($LastChangedDate: 2010-04-09 18:20:34 +0200 (Fri, 09 Apr 2010) $)
 * @since 0.1.0
 *
 */
public class Activator implements BundleActivator {

	public static BundleContext context;
    
	private ControlPoint cp;

	public static HAListenerFactoryServiceTracker tracker = null;
	
	/**
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
        tracker = new HAListenerFactoryServiceTracker(context);
		Activator.context = context;
        cp = new ControlPoint();
	}

	/**
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		cp.close();
		Activator.context=null;
		context.removeServiceListener(tracker);
		tracker = null;
	}
}
