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

import gnu.trove.TShortArrayList;
import gnu.trove.TShortHashSet;
import it.cnr.isti.primitvetypes.util.Integers;
import it.cnr.isti.thread.Stoppable;
import it.cnr.isti.zigbee.basedriver.Activator;
import it.cnr.isti.zigbee.dongle.api.SimpleDriver;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itaca.ztool.api.ZToolAddress16;
import com.itaca.ztool.api.ZToolException;
import com.itaca.ztool.api.zdo.ZDO_IEEE_ADDR_REQ;
import com.itaca.ztool.api.zdo.ZDO_IEEE_ADDR_RSP;


/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 668 $ ($LastChangedDate: 2009-11-19 18:48:39 +0100 (Thu, 19 Nov 2009) $)
 * @since 0.1.0
 *
 */
public class NetworkBrowserThread implements Stoppable {

	private static final Logger logger = LoggerFactory.getLogger(NetworkBrowserThread.class);
	
	private static final short COORDINATOR_NWK_ADDRESS = 0;
	private static final long DEFAULT_DUTY_PERIOD = 15*60*1000; //15 minutes

	private final ImportingQueue queue;
	final SimpleDriver driver;
	private boolean end = false;
	
	
	
	public NetworkBrowserThread(ImportingQueue queue, SimpleDriver driver) {
		this.queue = queue;
		this.driver = driver;
	}
	
	public void run(){
		long period = DEFAULT_DUTY_PERIOD;
		final String threadName = Thread.currentThread().getName();
		
		logger.info("{} STARTED Succesfully", threadName);		
		
		while(!isEnd()){			
			long wakeUpTime = System.currentTimeMillis() + period;
			try{
				logger.info("Inspecting ZigBee network for new nodes");
				TShortArrayList toInspect = new TShortArrayList(); 
				TShortHashSet alreadyInspected = new TShortHashSet();
				toInspect.add(COORDINATOR_NWK_ADDRESS);
				while(toInspect.size() != 0){
					short nwkAddress = toInspect.remove( toInspect.size()-1 );
					alreadyInspected.add(nwkAddress);
					logger.info("Inspecting node #{} ({})", nwkAddress, ((int) nwkAddress & 0xFFFF));
					ZDO_IEEE_ADDR_RSP result = driver.sendZDOIEEEAddressRequest(
							new ZDO_IEEE_ADDR_REQ(nwkAddress,ZDO_IEEE_ADDR_REQ.REQ_TYPE.EXTENDED,(byte) 0)						
					);
					
					if( result == null) {
						logger.debug("No answer from #{} ({})", nwkAddress, ((int) nwkAddress & 0xFFFF));
					} else {
						logger.debug(
								"Answer from {} with {} associated", 
								result.getIEEEAddress(), result.getAssociatedDeviceCount()
						);
					}
					
					while(result != null){
						
						ZToolAddress16 nwk = new ZToolAddress16(
								Integers.getByteAsInteger(nwkAddress, 1),
								Integers.getByteAsInteger(nwkAddress, 0)
						);
						queue.push(nwk, result.getIEEEAddress());						
						
						short[] toAdd = result.getAssociatedDeviceList();
						for (int i = 0; i < toAdd.length; i++) {
							logger.info("Found device #{} associated to #{}",toAdd[i],nwkAddress);
							if(!alreadyInspected.contains(toAdd[i])){
								toInspect.add(toAdd[i]);
							}
						}
						if( toAdd.length + result.getStartIndex() < result.getAssociatedDeviceCount() ) {
							result = driver.sendZDOIEEEAddressRequest(
									new ZDO_IEEE_ADDR_REQ(nwkAddress,ZDO_IEEE_ADDR_REQ.REQ_TYPE.EXTENDED,(byte) toAdd.length)						
							);						
						}else{
							result = null;
						}
					}
				}
				
				try {
					final long sleeping = wakeUpTime-System.currentTimeMillis();
					logger.info("{} sleeping for: {}ms", threadName, sleeping);
					Thread.sleep(sleeping);
				} catch (InterruptedException ignored) {
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		logger.info("{} TERMINATED Succesfully", threadName);
	}
	
	
	private synchronized boolean isEnd() {
		return end;
	}

	public synchronized void end() {
		end = true;
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException, ZToolException{
		//new ZigBeeAccessDriver(args[0],Integer.parseInt(args[1])).run();
	}
}
