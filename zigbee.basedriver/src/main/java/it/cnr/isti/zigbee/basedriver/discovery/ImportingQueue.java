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

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itaca.ztool.api.ZToolAddress16;
import com.itaca.ztool.api.ZToolAddress64;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 668 $ ($LastChangedDate: 2009-11-19 18:48:39 +0100 (Thu, 19 Nov 2009) $)
 * @since 0.1.0
 *
 */
public class ImportingQueue {
	
	private static final Logger logger = LoggerFactory.getLogger(ImportingQueue.class);
	
	public class ZigBeeNodeAddress {
		
		private final ZToolAddress16 networkAddress;
		private final ZToolAddress64 ieeeAddress;
		
		public ZigBeeNodeAddress(final ZToolAddress16 networkAddress, final ZToolAddress64 ieeeAddress) {			
			this.networkAddress = networkAddress;
			this.ieeeAddress = ieeeAddress;
		}
		
		public final ZToolAddress16 getNetworkAddress() {
			return networkAddress;
		}
		public final ZToolAddress64 getIEEEAddress() {
			return ieeeAddress;
		}
	}
	
	private final ArrayList<ZigBeeNodeAddress> addresses = new ArrayList<ZigBeeNodeAddress>();		
	
	public void clear() {
		synchronized (addresses) {
			addresses.clear();
		}
	}
	
	public boolean isEmpty() {
		synchronized (addresses) {
			return addresses.size() == 0;
		}
	}

	public int size() {
		synchronized (addresses) {
			return addresses.size();
		}
	}
	
	
	public void push(ZToolAddress16 nwkAddress, ZToolAddress64 ieeeAddress){
		ZigBeeNodeAddress inserting = new ZigBeeNodeAddress(nwkAddress, ieeeAddress);
		logger.debug("Adding {} {}",nwkAddress,ieeeAddress);
		synchronized (addresses) {
			addresses.add(inserting);
			addresses.notify();
		}		
		logger.debug("Added {} {}",nwkAddress,ieeeAddress);
	}

	public ZigBeeNodeAddress pop(){
		ZigBeeNodeAddress result = null;
		logger.debug("Removing element");
		synchronized (addresses) {
			while( addresses.isEmpty() ){
				try {
					addresses.wait();
				} catch (InterruptedException ignored) {
				}
			}
			result = addresses.remove(addresses.size() - 1);
		}
		logger.debug("Removed {} {}", result.networkAddress, result.ieeeAddress);		
		return result;
	}
	
	
}
