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
package it.cnr.isti.zigbee.dongle.CC2530.impl;

import gnu.io.CommPortIdentifier;
import it.cnr.isti.cc2480.high.AsynchrounsCommandListener;
import it.cnr.isti.cc2480.high.HWHighLevelDriver;
import it.cnr.isti.cc2480.high.SynchrounsCommandListner;
import it.cnr.isti.cc2480.low.HWLowLevelDriver;
import it.cnr.isti.cc2480.low.PacketListener;
import it.cnr.isti.primitvetypes.util.Integers;
import it.cnr.isti.zigbee.dongle.api.AFMessageListner;
import it.cnr.isti.zigbee.dongle.api.AnnunceListner;
import it.cnr.isti.zigbee.dongle.api.ConfigurationProperties;
import it.cnr.isti.zigbee.dongle.api.DriverStatus;
import it.cnr.isti.zigbee.dongle.api.NetworkMode;
import it.cnr.isti.zigbee.dongle.api.SimpleDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itaca.ztool.api.ZToolAddress16;
import com.itaca.ztool.api.ZToolAddress64;
import com.itaca.ztool.api.ZToolCMD;
import com.itaca.ztool.api.ZToolException;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.api.af.AF_DATA_CONFIRM;
import com.itaca.ztool.api.af.AF_DATA_REQUEST;
import com.itaca.ztool.api.af.AF_DATA_SRSP;
import com.itaca.ztool.api.af.AF_INCOMING_MSG;
import com.itaca.ztool.api.af.AF_REGISTER;
import com.itaca.ztool.api.af.AF_REGISTER_SRSP;
import com.itaca.ztool.api.simple.ZB_GET_DEVICE_INFO;
import com.itaca.ztool.api.simple.ZB_GET_DEVICE_INFO_RSP;
import com.itaca.ztool.api.simple.ZB_READ_CONFIGURATION;
import com.itaca.ztool.api.simple.ZB_READ_CONFIGURATION_RSP;
import com.itaca.ztool.api.simple.ZB_WRITE_CONFIGURATION;
import com.itaca.ztool.api.simple.ZB_WRITE_CONFIGURATION_RSP;
import com.itaca.ztool.api.system.SYS_RESET;
import com.itaca.ztool.api.system.SYS_RESET_RESPONSE;
import com.itaca.ztool.api.system.SYS_VERSION;
import com.itaca.ztool.api.util.UTIL_GET_DEVICE_INFO;
import com.itaca.ztool.api.util.UTIL_GET_DEVICE_INFO_RESPONSE;
import com.itaca.ztool.api.zdo.ZDO_ACTIVE_EP_REQ;
import com.itaca.ztool.api.zdo.ZDO_ACTIVE_EP_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_ACTIVE_EP_RSP;
import com.itaca.ztool.api.zdo.ZDO_BIND_REQ;
import com.itaca.ztool.api.zdo.ZDO_BIND_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_BIND_RSP;
import com.itaca.ztool.api.zdo.ZDO_END_DEVICE_ANNCE_IND;
import com.itaca.ztool.api.zdo.ZDO_IEEE_ADDR_REQ;
import com.itaca.ztool.api.zdo.ZDO_IEEE_ADDR_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_IEEE_ADDR_RSP;
import com.itaca.ztool.api.zdo.ZDO_MGMT_LEAVE_REQ;
import com.itaca.ztool.api.zdo.ZDO_MGMT_LEAVE_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_MGMT_LEAVE_RSP;
import com.itaca.ztool.api.zdo.ZDO_NODE_DESC_REQ;
import com.itaca.ztool.api.zdo.ZDO_NODE_DESC_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_NODE_DESC_RSP;
import com.itaca.ztool.api.zdo.ZDO_SIMPLE_DESC_REQ;
import com.itaca.ztool.api.zdo.ZDO_SIMPLE_DESC_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_SIMPLE_DESC_RSP;
import com.itaca.ztool.api.zdo.ZDO_STARTUP_FROM_APP;
import com.itaca.ztool.api.zdo.ZDO_STARTUP_FROM_APP_SRSP;
import com.itaca.ztool.api.zdo.ZDO_UNBIND_REQ;
import com.itaca.ztool.api.zdo.ZDO_UNBIND_REQ_SRSP;
import com.itaca.ztool.api.zdo.ZDO_UNBIND_RSP;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 229 $ ($LastChangedDate: 2011-05-20 11:42:58 +0200 (ven, 20 mag 2011) $)
 * @since 0.1.0
 *
 */
public class DriverCC2530 implements Runnable, SimpleDriver{
	private MasterResetGUI resetGUI;
	private final static Logger logger = LoggerFactory.getLogger(DriverCC2530.class);
	private final static Logger logger4Waiter = LoggerFactory.getLogger(WaitForCommand.class);
	
	public static final int RESEND_TIMEOUT_DEFAULT = 1000;
	public static final String RESEND_TIMEOUT_KEY = "zigbee.driver.tsb.resend.timout";
	
	public  static final int RESEND_MAX_RESEND_DEFAULT = 3;
	public  static final String RESEND_MAX_RESEND_KEY = "zigbee.driver.tsb.resend.max";
	
	public  static final boolean RESEND_ONLY_EXCEPTION_DEFAULT = true;
	public  static final String RESEND_ONLY_EXCEPTION_KEY = "zigbee.driver.tsb.resend.exceptiononly";
	
	
	private Thread driver;
	
	private HWHighLevelDriver high;
	private HWLowLevelDriver low;
	private String port;
	private int rate;
	private DriverStatus state;
	private NetworkMode mode;
	private short pan;
	private byte channel;
	private boolean cleanStatus;

	private long TIMEOUT = 5000;//Activator.getCurrentConfiguration().getZigBeeTimeout();
	private final int RESEND_TIMEOUT; 
	private final int RESEND_MAX_RETRY;
	private final boolean RESEND_ONLY_EXCEPTION;
	
	private final HashSet<AnnunceListner> annunceListners = new HashSet<AnnunceListner>();
	private final AnnunceListerFilter annunceListner = new AnnunceListerFilter(annunceListners);
	
	private final ArrayList<AFMessageListner> afMessageListners = new ArrayList<AFMessageListner>();
	private final AFMessageListnerFilter afListner = new AFMessageListnerFilter(afMessageListners);
	
	private long ieeeAddress = -1;
	private final HashMap<Class<?>, Thread> conversation3Way = new HashMap<Class<?>, Thread>();
	
	private class AnnunceListerFilter implements AsynchrounsCommandListener{
		
		private final Collection<AnnunceListner> listners;
		
		private AnnunceListerFilter(Collection<AnnunceListner> list){
			listners = list;
		}
		
		public void receivedAsynchrounsCommand(ZToolPacket packet) {
			if(packet.isError()) return;
			if(packet.getCMD().get16BitValue() == ZToolCMD.ZDO_END_DEVICE_ANNCE_IND){
				ZDO_END_DEVICE_ANNCE_IND annunce = (ZDO_END_DEVICE_ANNCE_IND) packet;
				for (AnnunceListner l : listners) {
					l.notify(annunce.SrcAddr, annunce.IEEEAddr, annunce.NwkAddr, annunce.Capabilities);
				}
			}
		}
		
	}
	
	private class AFMessageListnerFilter implements AsynchrounsCommandListener{

		private final Collection<AFMessageListner> listners;
		
		private AFMessageListnerFilter(Collection<AFMessageListner> list){
			listners = list;
		}
		
		public void receivedAsynchrounsCommand(ZToolPacket packet) {
			if(packet.isError()) return;
			if(packet.getCMD().get16BitValue() == ZToolCMD.AF_INCOMING_MSG){
				AF_INCOMING_MSG msg = (AF_INCOMING_MSG) packet;
				if( listners.isEmpty() ){
					logger.debug("Recieved AF_INCOMING_MSG but no listeners. Message was: {} ", msg);
				} else {
					logger.debug("Recieved AF_INCOMING_MSG notifying {} listeners of {}", listners.size(), msg);
				}
				ArrayList<AFMessageListner> localCopy = null; 
				synchronized (listners) {	
					localCopy = new ArrayList<AFMessageListner>(listners);
				}
				for ( AFMessageListner l : localCopy){
//					if( l.match(
//							msg.getClusterId(), msg.getSrcAddr(),
//							msg.getSrcEndpoint(), msg.getDstEndpoint(),
//							msg.getTransId()
//					) ) {
						l.notify(msg);
//					}					
				}
			}
		}
		
	}
	
	public DriverCC2530(String serialPort, int bitrate) throws ZToolException {		
		this(serialPort,bitrate,NetworkMode.Coordinator,0,19, false);
	}

	public DriverCC2530(
			String serialPort, int bitrate, NetworkMode mode, int pan, int channel
	) throws ZToolException {
		
		this(serialPort, bitrate, mode, pan, channel, false);
	}	
	
	public DriverCC2530(
			String serialPort, int bitRate, NetworkMode mode, int pan, int channel, boolean cleanNetworkStatus
	) throws ZToolException {
		int aux = RESEND_TIMEOUT_DEFAULT;
		try{
			aux = Integer.parseInt(System.getProperty(RESEND_TIMEOUT_KEY)); 
			logger.debug("Using RESEND_TIMEOUT set from enviroment {}", aux);
		}catch(NumberFormatException ex){
			logger.debug("Using RESEND_TIMEOUT set as DEFAULT {}", aux);
		}		
		RESEND_TIMEOUT = aux;
		
		aux = RESEND_MAX_RESEND_DEFAULT;
		try{
			aux = Integer.parseInt(System.getProperty(RESEND_MAX_RESEND_KEY)); 
			logger.debug("Using RESEND_MAX_RETRY set from enviroment {}", aux);
		}catch(NumberFormatException ex){
			logger.debug("Using RESEND_MAX_RETRY set as DEFAULT {}", aux);
		}
		RESEND_MAX_RETRY = aux; 			
		
		boolean b = RESEND_ONLY_EXCEPTION_DEFAULT;
		try{
			aux = Integer.parseInt(System.getProperty(RESEND_ONLY_EXCEPTION_KEY)); 
			logger.debug("Using RESEND_MAX_RETRY set from enviroment {}", aux);
		}catch(NumberFormatException ex){
			logger.debug("Using RESEND_MAX_RETRY set as DEFAULT {}", aux);
		}
		RESEND_ONLY_EXCEPTION = b; 
		state = DriverStatus.CLOSED;
		this.cleanStatus = cleanNetworkStatus;
		setSerialPort(serialPort, bitRate);
		setZigBeeNetwork((byte)channel, (short)pan);
		setZigBeeNodeMode(mode);
		resetGUI=new MasterResetGUI(this);
		resetGUI.setVisible(true);
	}
		
	private String buildDriverThreadName(String serialPort, int bitrate) {
		return "SimpleDriver["+serialPort+","+bitrate+"]";
	}
	
	public void setZigBeeNodeMode(NetworkMode m) {
		if ( state != DriverStatus.CLOSED ) {
			throw new IllegalStateException("Network mode can be changed only " +
					"if driver is CLOSED while it is:"+state);
		}
		cleanStatus = mode != m;
		mode = m;
	}
	
	public void setZigBeeNetwork(byte ch, short panId) {
		if ( state != DriverStatus.CLOSED ) {
			throw new IllegalStateException("Network mode can be changed only " +
					"if driver is CLOSED while it is:"+state);
		}
		cleanStatus = ch != channel || panId != pan;
		channel = ch;
		pan = panId;
	}	

	public void setSerialPort(String serialName,int bitRate) {
		if ( state != DriverStatus.CLOSED ) {
			throw new IllegalStateException("Serial port can be changed only " +
					"if driver is CLOSED while it is:"+state);
		}		
		port = serialName;
		rate = bitRate;
	}
	
	public void open(boolean cleanCache){
		cleanStatus = cleanCache;
		open();
	}
	
	public void open(){
		if ( state == DriverStatus.CLOSED ) {
			state = DriverStatus.CREATED;
			driver = new Thread(this);
			driver.setName(buildDriverThreadName(port,rate));
			driver.start();		
		}else{
			throw new IllegalStateException("Driver already opened, current status is:"+state);
		}
	}
	
	public void close(){
		if(state == DriverStatus.CLOSED){
			logger.debug("Already CLOSED");
			return;
		}
		logger.info("Closing");
		if( Thread.currentThread() != driver ){
			logger.debug("Waiting for intialization operation to complete before closing");
			try {
				driver.join();
			} catch (InterruptedException ignored) {
			}			
		}else{
			logger.debug("Self closing");
		}
		if(state == DriverStatus.NETWORK_READY){
			logger.debug("Closing NETWORK");
			setState(DriverStatus.HARDWARE_READY);
		}
		if(state == DriverStatus.HARDWARE_READY){
			logger.debug("Closing HARDWARE");
			high.close();
			logger.debug("Closing LOW HARDWARE");
			low.close();
			setState(DriverStatus.CREATED);
		}
		if(state == DriverStatus.CREATED){
			setState(DriverStatus.CLOSED);
		}
		logger.info("Closed");
	}
	
	public ZDO_IEEE_ADDR_RSP sendZDOIEEEAddressRequest(ZDO_IEEE_ADDR_REQ request){
		if( waitForNetwork() == false ) return null;
		ZDO_IEEE_ADDR_RSP result = null;
		
		waitAndLock3WayConversation(request);
		final WaitForCommand waiter = new WaitForCommand(ZToolCMD.ZDO_IEEE_ADDR_RSP, high);
		
		logger.debug("Sending ZDO_IEEE_ADDR_REQ {}", request);
		ZDO_IEEE_ADDR_REQ_SRSP response = (ZDO_IEEE_ADDR_REQ_SRSP) sendSynchrouns(high, request);
		if ( response == null || response.Status != 0 ) {
			logger.debug("ZDO_IEEE_ADDR_REQ failed, recieved {}", response);
			waiter.cleanup();
		} else {
			result = (ZDO_IEEE_ADDR_RSP) waiter.getCommand(TIMEOUT);
		}
		unLock3WayConversation(request);
		return result;
	}

	public ZDO_NODE_DESC_RSP sendZDONodeDescriptionRequest(ZDO_NODE_DESC_REQ request) {
		if( waitForNetwork() == false ) return null;
		ZDO_NODE_DESC_RSP result = null;
		
		waitAndLock3WayConversation(request);
		final WaitForCommand waiter = new WaitForCommand(ZToolCMD.ZDO_NODE_DESC_RSP, high);
		
		ZDO_NODE_DESC_REQ_SRSP response = (ZDO_NODE_DESC_REQ_SRSP) sendSynchrouns(high, request);
		if ( response == null || response.Status != 0 ) {
			waiter.cleanup();
		} else {
			result = (ZDO_NODE_DESC_RSP) waiter.getCommand(TIMEOUT);
		}
		
		unLock3WayConversation(request);
		return result;
	}
	
	public ZDO_ACTIVE_EP_RSP sendZDOActiveEndPointRequest(ZDO_ACTIVE_EP_REQ request) {
		if( waitForNetwork() == false ) return null;
		ZDO_ACTIVE_EP_RSP result = null;
		
		waitAndLock3WayConversation(request);
		final WaitForCommand waiter = new WaitForCommand(ZToolCMD.ZDO_ACTIVE_EP_RSP, high);
		
		logger.debug("Sending ZDO_ACTIVE_EP_REQ {}", request);
		ZDO_ACTIVE_EP_REQ_SRSP response = (ZDO_ACTIVE_EP_REQ_SRSP) sendSynchrouns(high, request);
		if ( response == null || response.Status != 0 ) {
			logger.debug("ZDO_ACTIVE_EP_REQ failed, recieved {}", response);
			waiter.cleanup();
		} else {
			result = (ZDO_ACTIVE_EP_RSP) waiter.getCommand(TIMEOUT);
		}
		unLock3WayConversation(request);
		return result;
	}
	
	/**
	 * @param request
	 */
	private void waitAndLock3WayConversation(ZToolPacket request) {
		synchronized (conversation3Way) {
			Class<?> clz = request.getClass();
			Thread requestor = null;
			while(	(requestor = conversation3Way.get(clz) ) != null ){
				logger.debug("Waiting for {} issued by {} to complete", clz, requestor);
				try{
					conversation3Way.wait();
				}catch(InterruptedException ex){					
				}
			}
			conversation3Way.put(clz, Thread.currentThread());
		}
	}

	/**
	 * Release the lock held for the 3-way communication
	 * 
	 * @param request
	 */
	private void unLock3WayConversation(ZToolPacket request) {
		Class<?> clz = request.getClass();
		Thread requestor = null;
		synchronized ( conversation3Way ) {
			requestor = conversation3Way.get( clz );
			conversation3Way.put( clz, null ); 
		}
		if( requestor == null ){
			logger.error("LOCKING BROKEN SOMEONE RELEASE LOCK WITHOU LOCKING IN ADVANCE for {}", clz);
		} else if( requestor != Thread.currentThread() ) {
			logger.error(
					"Thread {} stolen the answer of {} waited by {}",
					new Object[]{ Thread.currentThread(), clz, requestor } 
			);
		}
	}
	
	public ZDO_SIMPLE_DESC_RSP sendZDOSimpleDescriptionRequest(ZDO_SIMPLE_DESC_REQ request) {
		if( waitForNetwork() == false ) return null;
		ZDO_SIMPLE_DESC_RSP result = null;
		waitAndLock3WayConversation(request);
		final WaitForCommand waiter = new WaitForCommand(ZToolCMD.ZDO_SIMPLE_DESC_RSP, high);
		
		ZDO_SIMPLE_DESC_REQ_SRSP response = (ZDO_SIMPLE_DESC_REQ_SRSP) sendSynchrouns(high, request);
		if ( response == null || response.Status != 0 ) {
			waiter.cleanup();
		} else {
			result = (ZDO_SIMPLE_DESC_RSP) waiter.getCommand(TIMEOUT);
		}
		
		unLock3WayConversation(request);
		return result;
	}

	public void run() {
		logger.info("Intializing");
		setState(DriverStatus.HARDWARE_INITIALIZING);		
		if(initializeHardware() == true){
			setState(DriverStatus.HARDWARE_READY);			
		}else{
			close();
			return;
		}
		
		setState(DriverStatus.NETWORK_INITIALIZING);
		if(initializeZigBeeNetwork() == true){
			setState(DriverStatus.NETWORK_READY);
		}else{
			close();
			return;
		}
	}

	
	@SuppressWarnings("unchecked")
	private boolean initializeHardware() {
		String portToOpen = null; 
		if("auto".equalsIgnoreCase(port)){
			logger.info("Automatic discovery the dongle port by inspecting all the serail ports");
			Enumeration<CommPortIdentifier> ports = CommPortIdentifier.getPortIdentifiers();
			while(ports.hasMoreElements()){
				CommPortIdentifier com = ports.nextElement();
				if(DriverCC2530.initializeHardware(com.getName(), rate)){
					portToOpen=com.getName();
					Thread.currentThread().setName(buildDriverThreadName(portToOpen, rate));					
					break;
				}
			}
			if ( portToOpen == null ) {
				logger.error("Automatic discovery FAILED the dongle couldn't be find on any port: it may be frozen");
				return false;
			}
		}else{
			if( DriverCC2530.initializeHardware(port, rate) == true ){
				portToOpen = port;
			} else {
				logger.error("Failed to intialize the dongle on port {} at rate {}", port, rate);
				return false;
			}
		}
		
		low = new HWLowLevelDriver();
		try {
			low.open(portToOpen, rate);
		} catch (ZToolException e) {
			logger.error("The port was already open in advance but we can't open it now",e);
			return false;
		}
		high = new HWHighLevelDriver(low);
		return true;
	}

	private void setState(DriverStatus value) {
		logger.info("State changed from {} to {}", this.state, value);
		synchronized (this) {
			state = value;
			notifyAll();
		}
		if(state == DriverStatus.HARDWARE_READY){
			postHardwareEnabled();
		}
	}

	private void postHardwareEnabled() {
		if( ! afMessageListners.isEmpty() ){
			high.addAsynchrounsCommandListener(afListner);			
		}
		if( ! annunceListners.isEmpty() ){
			high.addAsynchrounsCommandListener(annunceListner);
		}
	}
	
	static boolean initializeHardware(String portName, int boudRate) {
		boolean result = false;
		final int recieved[] = new int[1];
		final HWLowLevelDriver probingDriver = new HWLowLevelDriver();
		final PacketListener monitor = new PacketListener(){			
			public void packetRecieved(ZToolPacket packet) {
				logger.debug("Received initializing SYS VERSION candidate");
				if(packet.getCommandId() == ZToolCMD.SYS_VERSION_RESPONSE){
					logger.debug("Initializing Hardware: Received correctly SYS_VERSION_RESPONSE");
					synchronized (recieved) {
						recieved[0] = 3;						
					}
				} else if(packet.isError()){
					logger.debug("Initializing Hardware: Received erroneous packet: {}",packet.getErrorMsg());
					synchronized (recieved) {
						recieved[0] += 1;						
					}
				} else {
					logger.debug("Initializing Hardware: Received {}",packet.getClass().getName());						
					synchronized (recieved) {
						recieved[0] += 1;						
					}
				}
			}
			
		};
		probingDriver.addPacketListener(monitor);
		try {
			probingDriver.open(portName,boudRate);
			probingDriver.sendPacket(new SYS_VERSION());
			final long ready = System.currentTimeMillis() + 5000;
			while(ready > System.currentTimeMillis()){
				synchronized (recieved) {
					if( recieved[0] == 3 ){
						logger.debug("Received initializing SYS VERSION");
						break;						
					}
				}
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException ignored) {
					logger.debug("Exception SYS VERSION");
				}
			}
			logger.debug("End of waiting for SYS VERSION");
			synchronized (recieved) {
				if( recieved[0] == 3 ){
					logger.debug("Succeeded initializing SYS VERSION");
					result = true;
				}
			}
		} catch (ZToolException e) {
			logger.info("Unable to open serial port: {}", portName);
			logger.error("Unable to open serial port, due to:", e);
		}catch (IOException e) {
			logger.error("Hardware initialization failed", e);
		}
		probingDriver.close();
		probingDriver.removePacketListener(monitor);
		return result;		
	}

	private boolean waitForHardware() {
		synchronized (this) {
			while (state == DriverStatus.CREATED || state == DriverStatus.CLOSED ){
				logger.debug("Waiting for HARDWARE to become ready");
				try {
					wait();
				} catch (InterruptedException ignored) {
				}
			}
			return isHardwareReady();
		}		 
	}			
	
	private boolean waitForNetwork() {
		synchronized (this) {
			while (state != DriverStatus.NETWORK_READY && state != DriverStatus.CLOSED ){
				logger.debug("Waiting for NETWORK to become ready");
				try {
					wait();
				} catch (InterruptedException ignored) {
				}
			}
			return isNetworkReady();
		}		 
	}			
	
	private class WaitForCommand implements AsynchrounsCommandListener{
				
		final ZToolPacket[] result = new ZToolPacket[]{null};
		final int waitFor;
		final HWHighLevelDriver driver;

		public WaitForCommand(int waitFor, HWHighLevelDriver driver) {
			this.waitFor = waitFor;
			this.driver = driver;
			driver.addAsynchrounsCommandListener(this);
		}



		public void receivedAsynchrounsCommand(ZToolPacket packet) {
			logger4Waiter.info("Recieved a packet {} and waiting for {}", packet.getCMD(), waitFor);
			logger4Waiter.debug("Recieved {} {}", packet.getClass(), packet.toString());
			if( packet.isError() ) return;
			if( packet.getCMD().get16BitValue() != waitFor) return;
			synchronized (result) {
				result[0]=packet;
				logger4Waiter.info("Recieved packet that we were waiting for");
				cleanup();
			}
		}
		
		public ZToolPacket getCommand(final long timeout){
			synchronized (result) {
				final long wakeUpTime = System.currentTimeMillis() + timeout;
				while(result[0] == null && wakeUpTime > System.currentTimeMillis()){
					try {
						result.wait(wakeUpTime-System.currentTimeMillis());
					} catch (InterruptedException ignored) {
					}

				}
			}
			if( result[0] == null ){
				logger4Waiter.warn("Timeout {} expired and not packet with {} recieved", timeout, waitFor);
			} 
			cleanup();
			return result[0];
		}
		
		public void cleanup(){
			synchronized (result) {
				driver.removeAsynchrounsCommandListener(this);
				result.notify();											
			}
		}
	}	
	
	private class WaitForAList implements PacketListener{
		
			final int[] waitingList;
			final ZToolPacket[] packetHistory;
			final HWLowLevelDriver hwDriver;
			int idx = 0;
			
			/**
			 * This constructor uses the {@link WaitForAList#WaitForAList(int[], ZToolPacket[], HWLowLevelDriver) constructor<br>
			 * with the following parameters <code>WaitForAList(list,null,null)</code>
			 * 
			 * @see WaitForAList#WaitForAList(int[], ZToolPacket[], HWLowLevelDriver)
			 * @param list
			 */
			public WaitForAList(final int[] list){
				this(list, null, null);
			}

			/**
			 * Create a {@link WaitForAList} that wait for a specified list<br>
			 * of packet to be received. Furthermore, the {@link WaitForAList}<br>
			 * while record all the matching packet received, and it will handle<br>
			 * by itself the registration and unregistration as {@link PacketListener}<br>
			 * to the specified {@link HWLowLevelDriver}.<br>
			 * <br>
			 * <b>NOTE:</b>If the {@link ZToolPacket} array is null no packet will be recorded<br>
			 * <b>NOTE:</b>If the {@link HWLowLevelDriver} array is null registration and unregistration as<br>
			 * {@link PacketListener} must be handled by the invoker<br>
			 * 
			 * @param list the array of <code>int</code> to listen to
			 * @param packets {@link ZToolPacket} the array of the same size as <code>list</code> to record the received packet
			 * @param lowDriver {@link HWLowLevelDriver} to register to, if <code>null</code> to handle registration/unregistration manually
			 */
			public WaitForAList(
					final int[] list, 
					final ZToolPacket[] packets,
					final HWLowLevelDriver lowDriver){
				this.waitingList = list;
				this.packetHistory = packets;
				this.hwDriver = lowDriver;
				if (  hwDriver != null) {
					hwDriver.addPacketListener(this);				
				}
			}			
			
			public void waitForAll(long timeout){
				long wakeUpTime = System.currentTimeMillis() + timeout;
				synchronized (waitingList) {
					while( idx < waitingList.length && wakeUpTime > System.currentTimeMillis()){
						try {
							waitingList.wait(wakeUpTime - System.currentTimeMillis());
						} catch (InterruptedException ignored) {
						}
					}
				}
			}
			
			public void packetRecieved(ZToolPacket packet) {
				if(packet.isError()) return;
				if((packet.getCommandId() & 0xFFFF) == waitingList[idx]){
					logger.info("Recieved packet that was waiting for incresing waitingList");
					synchronized (waitingList) {
						if ( packetHistory != null ) {
							packetHistory[idx] = packet;
						}
						idx = idx + 1;
						if ( idx == waitingList.length ){
							if ( low != null) {
								low.removePacketListener(this);
							}
							waitingList.notifyAll();
							return;
						}
					}
				}
			}
			
		}
	
		
	private boolean dongleReset(){
		if( waitForHardware() == false ) return false;

		final WaitForCommand waiter = new WaitForCommand(
				ZToolCMD.SYS_RESET_RESPONSE,
				high
		);
		
//		WaitForAList waiting = new WaitForAList(new int[]{
//				ZToolCMD.SYS_RESET_RESPONSE,
//				ZToolCMD.ZB_APP_REGISTER_RSP,
//				ZToolCMD.ZB_WRITE_CONFIGURATION_RSP,
//				ZToolCMD.ZB_WRITE_CONFIGURATION_RSP					
//		});
		
//		low.addPacketListener(waiting);
		
		try {
			high.sendAsynchrounsCommand(new SYS_RESET(SYS_RESET.RESET_TYPE.SERIAL_BOOTLOADER));
		} catch (IOException e) {
			logger.error("DongleReset failed", e);
			return false;
		}
		
		SYS_RESET_RESPONSE response = 
			(SYS_RESET_RESPONSE) waiter.getCommand(TIMEOUT);
		
//		waiting.waitForAll(TIMEOUT*2);		
//		low.removePacketListener(waiting);
		return response != null;
	}
	
	private boolean dongleClearState(){
		dongleSetCleanState(true);
		
		boolean result = dongleReset();
		
		dongleSetCleanState(false);
		
		return result;
	}
	
	private boolean dongleSetCleanState(boolean clean){
		ZB_WRITE_CONFIGURATION_RSP response;
		if(clean){
			response = (ZB_WRITE_CONFIGURATION_RSP) sendSynchrouns(
					high, 
					new ZB_WRITE_CONFIGURATION( 
							ZB_WRITE_CONFIGURATION.CONFIG_ID.ZCD_NV_STARTUP_OPTION,
							new int[]{0x00000002}
					)
			);
			
			if( response == null || response.Status != 0) {
				logger.info("Couldn't set ZCD_NV_STARTUP_OPTION to CLEAN_STATE");
				return false;
			}else{
				logger.info("Set ZCD_NV_STARTUP_OPTION to CLEAN_STATE");
			}
		}else{
			response = (ZB_WRITE_CONFIGURATION_RSP) sendSynchrouns(
					high, 
					new ZB_WRITE_CONFIGURATION( 
							ZB_WRITE_CONFIGURATION.CONFIG_ID.ZCD_NV_STARTUP_OPTION,
							new int[]{0x00000000}
					)
			);
			
			if( response == null || response.Status != 0) {
				logger.info("Couldn't set ZCD_NV_STARTUP_OPTION back to DO_NOTHING");
				return false;
			}
		}
		return true;
	}
	
	static final int[] buildChannelMask(int channel){
		int channelMask = 1 << channel;
		int[] mask = new int[4];
		for (int i = 0; i < mask.length; i++) {
			mask[i] = Integers.getByteAsInteger(channelMask, i);
		}
		return mask;
	}
	
	private boolean dongleSetChannel(int[] channelMask) {
		
		logger.info(
				"Setting the channel to {}{}{}{}", new Object[]{ 
					Integer.toHexString(channelMask[0]), 
					Integer.toHexString(channelMask[1]),
					Integer.toHexString(channelMask[2]),
					Integer.toHexString(channelMask[3])
				}				
		);
		
		ZB_WRITE_CONFIGURATION_RSP response = 
			(ZB_WRITE_CONFIGURATION_RSP) sendSynchrouns(
				high, 
				new ZB_WRITE_CONFIGURATION( 
						ZB_WRITE_CONFIGURATION.CONFIG_ID.ZCD_NV_CHANLIST,
						channelMask
				)
		);
		
		return response != null && response.Status == 0;
	}

	private boolean dongleSetChannel(int ch){		
		int[] channelMask = buildChannelMask(ch);
		
		return dongleSetChannel(channelMask);
	}	
	
	private boolean dongleSetChannel(){		
		int[] channelMask = buildChannelMask(channel);
		
		return dongleSetChannel(channelMask);
	}
	
	private boolean dongleSetNetworkMode(){
		logger.info("Changing the Network Mode to {}", mode);
		
		ZB_WRITE_CONFIGURATION_RSP response = 
			(ZB_WRITE_CONFIGURATION_RSP) sendSynchrouns(
				high, 
				new ZB_WRITE_CONFIGURATION( 
						ZB_WRITE_CONFIGURATION.CONFIG_ID.ZCD_NV_LOGICAL_TYPE,
						new int[]{ mode.ordinal() }
				)
		);
		
		return response != null && response.Status == 0;
	}
	
	private boolean dongleSetPanId(){				
		ZB_WRITE_CONFIGURATION_RSP response = 
			(ZB_WRITE_CONFIGURATION_RSP) sendSynchrouns(
				high, 
				new ZB_WRITE_CONFIGURATION( 
						ZB_WRITE_CONFIGURATION.CONFIG_ID.ZCD_NV_PANID,
						new int[]{
								Integers.getByteAsInteger(pan, 0),
								Integers.getByteAsInteger(pan, 1)
						}
				)
		);
		
		return response != null && response.Status == 0;
	}
	
	
	private boolean createZigBeeNetwork(){
		/*
		 * NOTE:The dongle has to be reset all the time to avoid that the endpoints
		 * register on the dongle itself break the code at upper level
		 * 
		 */
//		if( reset && dongleReset() ) {
//			logger.debug("Reset the dongle before Creating ZigBee Network");
//		} else if (reset) {
//			logger.debug("FAILED to reset the dongle before Creating ZigBee Network");
//		} else {
//			logger.debug("Dongle has already been reset so no endpoint registered");
//		}
		switch (mode) {
			case Coordinator:
				return doCoordinatorCreateNetwork();			
			case Router:
				//TODO Implements start up as Router
				return false;
			case EndDevice:
				return doEndDeviceCreateNetwok();			
		}
		
		/*
		 * This code is unreachable but compiler can't find this out yet.
		 * It may introduce a compilation issue of future compilers
		 */
		return false;
	}
	
	private boolean doEndDeviceCreateNetwok() {
		logger.debug("Creating network as EndDevice");
				
		ZDO_STARTUP_FROM_APP_SRSP response = (ZDO_STARTUP_FROM_APP_SRSP) sendSynchrouns(
				high, new ZDO_STARTUP_FROM_APP(ZDO_STARTUP_FROM_APP.RESET_TYPE.TARGET_DEVICE)
		);
		if ( response == null ) {			
			return false;
		} else {
			return true;//TODO: response.Status == ZDO_STARTUP_FROM_APP_SRSP.AF_STATUS.SUCCESS;
		}
	}

	private boolean doCoordinatorCreateNetwork() {
		logger.info("Creating network as Coordintator");		
		
		ZB_READ_CONFIGURATION_RSP response = (ZB_READ_CONFIGURATION_RSP) sendSynchrouns(
				high, new ZB_READ_CONFIGURATION(3));
		
		if(response!=null && response.Status==0){
			if(response.Len==1 && response.Value[0]==0){
				dongleSetCleanState(false);
			}
		}else{
			logger.error("Couldn´t get dongle status for startup");
			return false;
		}
		
		ZDO_STARTUP_FROM_APP_SRSP response2 = (ZDO_STARTUP_FROM_APP_SRSP) sendSynchrouns(
				high, new ZDO_STARTUP_FROM_APP(ZDO_STARTUP_FROM_APP.RESET_TYPE.TARGET_DEVICE)
		);
		if ( response2 == null ) {			
			return false;
		} else {
			return true;//TODO response.Status == ZDO_STARTUP_FROM_APP_SRSP.AF_STATUS.SUCCESS;
		}
	}

	/*
	//TODO We should check if the current setting differs from the setting stored on the dongle 
	 * and later define whetever to clean or not the cache
	private boolean mustCleanStatus(){
		if( cleanStatus ) {
			return true;
		}
		
		createZigBeeNetwork();
		
		int[] value;
		
		value = getDeviceInfo(ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.CHANNEL);
		if ( value == null){
			logger.error("Method getDeviceInfo(ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.CHANNEL) returned null");
			return true;
		} else if ( value[0] != this.channel ) {
			logger.debug("Dongle network state must be reset because channel is changed. " +
					"Old was = {} while new is {}", value[0], this.channel);
			return true;
		}
		
		value = getDeviceInfo(ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.PAN_ID);
		if ( value == null){
			logger.error("Method getDeviceInfo(ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.PAN_ID) returned null");
			return true;
		} else if ( Integers.shortFromInts(value, 1, 0) != this.pan) {
			logger.debug("Dongle network state must be reset because PanId is changed. " +
					"Old was = {} while new is {}", Integers.shortFromInts(value, 1, 0), this.pan);
			return true;
		}
		
		dongleReset();
		
		return false;
	}
	*/
	
	private boolean initializeZigBeeNetwork() {
		
		boolean cleanNetworkState = cleanStatus;

		if(cleanNetworkState){
			if ( doCleanAndSetConfiguration() == false ) {
				return false;
			}
		}
		boolean creation = createZigBeeNetwork();
		if ( creation == false ){
			return false;
		}
		state = DriverStatus.NETWORK_READY;
		if ( doesCurrentConfigurationMatchZStackConfiguration() ) {
			logger.error("Dongle configuration does not match specified configuration");
		}
		return creation;
		
	}

	private boolean doesCurrentConfigurationMatchZStackConfiguration() {
		int value = -1;
		boolean mismatch = false;
		if ( ( value = getCurrentChannel() ) != channel ) {
			logger.warn(
					"The channel configuration differ from the channel configuration in use: " +
					"in use {}, while the configured is {}.\n" +
					"The ZigBee Stack shuold be flushed, try to set "+ConfigurationProperties.NETWORK_FLUSH+" to TRUE",
					value, channel
			);
			mismatch = true;
		}
		if ( ( value = getCurrentPanId() ) != pan ) {
			logger.warn(
					"The PanId configuration differ from the channel configuration in use: " +
					"in use {}, while the configured is {}.\n" +
					"The ZigBee Stack shuold be flushed, try to set "+ConfigurationProperties.NETWORK_FLUSH+" to TRUE",
					value, pan
			);
			mismatch = true;
		}
		if ( ( value = getZigBeeNodeMode() ) != mode.ordinal() ) {
			logger.warn(
					"The NetworkMode configuration differ from the channel configuration in use: " +
					"in use {}, while the configured is {}.\n" +
					"The ZigBee Stack should be flushed, try to set "+ConfigurationProperties.NETWORK_FLUSH+" to TRUE",
					value, mode.ordinal()
			);
			mismatch = true;
		}
		
		return mismatch;
	}

	private boolean doCleanAndSetConfiguration() {
		logger.debug("Cleaning dongle networks stack status");
//		if( ! dongleClearState() ){
//			logger.error("Unable to CLEAN the ZigBee network stack staus");
//			return false;
//		} else {
//			logger.info("ZigBee network stack status CLEANED");
//		}
		if( ! dongleSetCleanState(true) ){
			logger.error("Unable to set clean state for dongle");
			return false;
		}
		if( ! dongleSetChannel() ){
			logger.error("Unable to set CHANNEL for ZigBee Network");
			return false;
		} else {
			logger.info("CHANNEL set");
		}
		if( ! dongleSetPanId() ){
			logger.error("Unable to set PANID for ZigBee Network");
			return false;
		} else {
			logger.info("PANID set");				
		}
		if ( dongleSetNetworkMode() == false ) {
			logger.error("Unable to set NETWORK_MODE for ZigBee Network");
			return false;
		} else {
			logger.info("NETWORK_MODE set");				
		}
		if( ! dongleReset() ){
			logger.error("Unable to reset dongle");
			return false;
		}
		if( ! dongleSetCleanState(false) ){
			logger.error("Unable to unset clean state for dongle");
			return false;
		}
//		if ( dongleMasterReset() == false ) {
//			logger.error("Unable to send the master reset for ZigBee Network");
//			return false;
//		} else {
//			logger.info("master reset sent");				
//		}
		return true;
	}

	protected boolean dongleMasterReset() {
		//---------START FROM APP
		logger.debug("Reset seq: Trying STARTFROMAPP");
		ZDO_STARTUP_FROM_APP_SRSP responseA1 = (ZDO_STARTUP_FROM_APP_SRSP) sendSynchrouns(
				high, new ZDO_STARTUP_FROM_APP(ZDO_STARTUP_FROM_APP.RESET_TYPE.TARGET_DEVICE)
		);
		if (responseA1==null){logger.error("Reset seq: Failed STARTFROMAPP");return false;}
		//---------ZB WRITE CONF
		logger.debug("Reset seq: Trying WRITECONF");
		ZB_WRITE_CONFIGURATION_RSP responseA2 = (ZB_WRITE_CONFIGURATION_RSP) sendSynchrouns(
				high, new ZB_WRITE_CONFIGURATION(3,new int[]{2})
		);
		if (responseA2==null){logger.error("Reset seq: Failed WRITECONF");return false;}
		//---------GET DEVICE INFO
		logger.debug("Reset seq: Trying GETDEVICEINFO");
//		final WaitForCommand waiter1 = new WaitForCommand(
//				ZToolCMD.UTIL_GET_DEVICE_INFO_RESPONSE,
//				high
//		);
//		try{
//			high.sendAsynchrounsCommand(new UTIL_GET_DEVICE_INFO());
//		} catch (IOException e) {
//			logger.error("GetDeviceInfo of Master Reset failed", e);
//			return false;
//		}
//		UTIL_GET_DEVICE_INFO_RESPONSE responseA3 = (UTIL_GET_DEVICE_INFO_RESPONSE) waiter1.getCommand(TIMEOUT);
		UTIL_GET_DEVICE_INFO_RESPONSE responseA3 = (UTIL_GET_DEVICE_INFO_RESPONSE) sendSynchrouns(high,new UTIL_GET_DEVICE_INFO());
		if (responseA3==null){logger.error("Reset seq: Failed GETDEVICEINFO");return false;}
		ZToolAddress16[] addresses = new ZToolAddress16[responseA3.AssocDevicesList.length];
		for(int k=0;k<responseA3.AssocDevicesList.length;k++){
			addresses[k]=new ZToolAddress16(responseA3.AssocDevicesList[k].getMsb(),responseA3.AssocDevicesList[k].getLsb());
		}
		//---------ZDO GET IEEE ADDR
		logger.debug("Reset seq: Trying GETIEEEADDR");
		ZToolAddress64[] longAddresses = new ZToolAddress64[addresses.length];
		for(int k=0;k<addresses.length;k++){
//			ZDO_IEEE_ADDR_RSP responseA4 = sendZDOIEEEAddressRequest(new ZDO_IEEE_ADDR_REQ(addresses[k],ZDO_IEEE_ADDR_REQ.REQ_TYPE.EXTENDED.getValue(),0));
			
			ZDO_IEEE_ADDR_RSP responseA4 = null;
			WaitForCommand waiter = new WaitForCommand(ZToolCMD.ZDO_IEEE_ADDR_RSP, high);
			logger.debug("Sending ZDO_IEEE_ADDR_REQ");
			ZDO_IEEE_ADDR_REQ_SRSP response = (ZDO_IEEE_ADDR_REQ_SRSP) sendSynchrouns(high, new ZDO_IEEE_ADDR_REQ(addresses[k],ZDO_IEEE_ADDR_REQ.REQ_TYPE.EXTENDED.getValue(),0));
			if ( response == null || response.Status != 0 ) {
				logger.debug("ZDO_IEEE_ADDR_REQ failed, recieved {}", response);
				waiter.cleanup();
			} else {
				responseA4 = (ZDO_IEEE_ADDR_RSP) waiter.getCommand(TIMEOUT);
			}
			
			if (responseA4!=null){
				longAddresses[k]=responseA4.getIEEEAddress();
			}else{
				longAddresses[k]=null;
			}
		}
		//---------ZDO MGMT LEAVE
		logger.debug("Reset seq: Trying LEAVE");
		long start=System.currentTimeMillis();
		for (int k = 0; k < longAddresses.length; k++) {
			if (longAddresses[k] != null) {
				WaitForCommand waiter3 = new WaitForCommand(ZToolCMD.ZDO_MGMT_LEAVE_RSP, high);
				
				ZDO_MGMT_LEAVE_REQ_SRSP response = (ZDO_MGMT_LEAVE_REQ_SRSP) sendSynchrouns(high, new ZDO_MGMT_LEAVE_REQ(addresses[k], longAddresses[k], 3));
				if ((System.currentTimeMillis() - start) > 5000){logger.error("Reset seq: Failed LEAVE");return false;}
				if ( response == null || response.Status != 0 ) {
					waiter3.cleanup();
					logger.error("Reset seq: Failed LEAVE");
					return false;
				} else {
					if ((System.currentTimeMillis() - start) > 5000){logger.error("Reset seq: Failed LEAVE");return false;}
					ZDO_MGMT_LEAVE_RSP responseA5 = (ZDO_MGMT_LEAVE_RSP) waiter3.getCommand(TIMEOUT);
					if ((System.currentTimeMillis() - start) > 5000){logger.error("Reset seq: Failed LEAVE");return false;}
					if(responseA5==null || responseA5.Status!=ZDO_MGMT_LEAVE_RSP.ZDO_STATUS.ZDP_SUCCESS){logger.error("Reset seq: Failed LEAVE");return false;}
				}
			}
			if ((System.currentTimeMillis() - start) > 5000){logger.error("Reset seq: Failed LEAVE");return false;}
		}
		
		//---------SYS RESET
		logger.debug("Reset seq: Trying SYSRESET");
		if (!dongleReset()){logger.error("Reset seq: Failed SYSRESET");return false;}
		//---------START FROM APP
//		//already in initializeZigBeeNetwork
//		ZDO_STARTUP_FROM_APP_SRSP responseA7 = (ZDO_STARTUP_FROM_APP_SRSP) sendSynchrouns(
//				high, new ZDO_STARTUP_FROM_APP(ZDO_STARTUP_FROM_APP.RESET_TYPE.TARGET_DEVICE)
//		);
//		if (responseA7==null)return false;
		return true;
	}

	private ZToolPacket sendSynchrouns(final HWHighLevelDriver hwDriver, final ZToolPacket request) {
		final ZToolPacket[] response = new ZToolPacket[]{null};
//		final int TIMEOUT = 1000, MAX_SEND = 3;					
		int sending = 1;		
		
		logger.info("Sending Synchrouns {}", request.getClass().getName());
		
		SynchrounsCommandListner listener = new SynchrounsCommandListner() {
		
			public void receivedCommandResponse(ZToolPacket packet) {
				logger.info("Recieved Synchrouns Response {}", packet.getClass().getName());
				synchronized (response) {
					response[0] = packet;
					response.notify();
				}
			}
		
		};
		
		while( sending <= RESEND_MAX_RETRY ){
			try {
				hwDriver.sendSynchrounsCommand(request, listener, RESEND_TIMEOUT);
				logger.info("Sent {} during the {}-th tenative", request.getClass().getName(),sending);
				synchronized (response) {
					long wakeUpTime = System.currentTimeMillis() + RESEND_TIMEOUT;
					while(response[0] == null && wakeUpTime > System.currentTimeMillis()){
						final long sleeping = wakeUpTime - System.currentTimeMillis();
						logger.debug("Waiting Synchrouns for up to {}ms till {} Unixtime", sleeping, wakeUpTime);
						if ( sleeping <= 0 ) {
							break;
						}
						try {
							response.wait(sleeping);
						} catch (InterruptedException ignored) {
						}
					}
				}
				if ( response[0] != null ) {
					logger.debug("Recieved synchrouns command {} before timout", response[0]);
				} else {
					logger.debug("Timeout fired and not synchrouns command recieved", response[0]);
				}
				if ( RESEND_ONLY_EXCEPTION ) {
					break;
				}else{
					logger.info("Failed to send {} during the {}-th tenative", request.getClass().getName(),sending);
					sending++;
				}
			}catch(IOException ignored){
				logger.info("Failed to send {} during the {}-th tenative", request.getClass().getName(),sending);
				logger.debug("Sending operation failed due to ", ignored);
				sending++;
			}
		}
		
		return response[0];
	}
		
	public boolean addAnnunceListener(AnnunceListner listner){		
		if(annunceListners.isEmpty() && isHardwareReady() ){
			high.addAsynchrounsCommandListener(annunceListner);
		}		
		return annunceListners.add(listner);
	}
	
	public boolean removeAnnunceListener(AnnunceListner listner){
		boolean result = annunceListners.remove(listner);
		if(annunceListners.isEmpty() && isHardwareReady() ){
			high.removeAsynchrounsCommandListener(annunceListner);
		}
		return result;
	}

	public AF_REGISTER_SRSP sendAFRegister(AF_REGISTER request) {
		if( waitForNetwork() == false ) return null;
		
		AF_REGISTER_SRSP response = (AF_REGISTER_SRSP) sendSynchrouns(high, request);
		return response;
	}

	public AF_DATA_CONFIRM sendAFDataRequest(AF_DATA_REQUEST request) {
		if( waitForNetwork() == false ) return null;
		AF_DATA_CONFIRM result = null;

		waitAndLock3WayConversation(request);		
		final WaitForCommand waiter = new WaitForCommand(ZToolCMD.AF_DATA_CONFIRM, high);
		
		AF_DATA_SRSP  response = (AF_DATA_SRSP) sendSynchrouns(high, request);
		if ( response == null || response.Status != 0 ) {
			waiter.cleanup();
		} else {
			result = (AF_DATA_CONFIRM) waiter.getCommand(TIMEOUT);
		}
		unLock3WayConversation(request);
		return result;
	}

	public ZDO_BIND_RSP sendZDOBind(ZDO_BIND_REQ request) {
		if( waitForNetwork() == false ) return null;
		ZDO_BIND_RSP result = null;
		
		waitAndLock3WayConversation(request);
		final WaitForCommand waiter = new WaitForCommand(ZToolCMD.ZDO_BIND_RSP, high);
		
		ZDO_BIND_REQ_SRSP  response = (ZDO_BIND_REQ_SRSP) sendSynchrouns(high, request);
		if ( response == null || response.Status != 0 ) {
			waiter.cleanup();
		} else {
			result = (ZDO_BIND_RSP) waiter.getCommand(TIMEOUT);
		}
		unLock3WayConversation(request);
		return result;
	}
	
	public ZDO_UNBIND_RSP sendZDOUnbind(ZDO_UNBIND_REQ request) {
		if( waitForNetwork() == false ) return null;
		ZDO_UNBIND_RSP result = null;
		
		waitAndLock3WayConversation(request);
		final WaitForCommand waiter = new WaitForCommand(ZToolCMD.ZDO_UNBIND_RSP, high);
		
		ZDO_UNBIND_REQ_SRSP  response = (ZDO_UNBIND_REQ_SRSP) sendSynchrouns(high, request);
		if ( response == null || response.Status != 0 ) {
			waiter.cleanup();
		} else {
			result = (ZDO_UNBIND_RSP) waiter.getCommand(TIMEOUT);
		}
		
		unLock3WayConversation(request);
		return result;
	}
	
	public boolean removeAFMessageListener(AFMessageListner listner){
		boolean result = false;
		synchronized (afMessageListners) {
			result = afMessageListners.remove(listner);
		}
		
		if(afMessageListners.isEmpty() && isHardwareReady() ){
			if ( high.removeAsynchrounsCommandListener(afListner) ) {
				logger.debug("Removed AsynchrounsCommandListener {} to HWHighLevelDriver", afListner.getClass().getName()); 
			} else {
				logger.debug("Could not remove AsynchrounsCommandListener {} to HWHighLevelDriver", afListner.getClass().getName()); 
			}
		}
		if ( result ) {
			logger.debug("Removed AFMessageListner {}:{}", listner, listner.getClass().getName());
			return true;
		} else {
			logger.debug("Could not remove AFMessageListner {}:{}", listner, listner.getClass().getName());
			return false;
		}
	}

	public boolean addAFMessageListner(AFMessageListner listner) {
		if(afMessageListners.isEmpty() && isHardwareReady() ){
			if ( high.addAsynchrounsCommandListener(afListner) ) {
				logger.debug("Added AsynchrounsCommandListener {} to HWHighLevelDriver", afListner.getClass().getName()); 
			} else {
				logger.debug("Could not add AsynchrounsCommandListener {} to HWHighLevelDriver", afListner.getClass().getName()); 
			}
		}
		boolean result = false;
		synchronized (afMessageListners) {
			result = afMessageListners.add(listner); 
		}
		
		if ( result ) {
			logger.debug("Added AFMessageListner {}:{}", listner, listner.getClass().getName());
			return true;
		} else {
			logger.debug("Could not add AFMessageListner {}:{}", listner, listner.getClass().getName());
			return false;
		}
	}
	
	private boolean isNetworkReady() {
		synchronized (this) {
			return state.ordinal() >= DriverStatus.NETWORK_READY.ordinal() 
			&& state.ordinal() < DriverStatus.CLOSED.ordinal();
		}
	}
	
	private boolean isHardwareReady() {
		synchronized (this) {
			return state.ordinal() >= DriverStatus.HARDWARE_READY.ordinal() 
			&& state.ordinal() < DriverStatus.CLOSED.ordinal();
		}
	}
	
	/**
	 * 
	 * @return
	 * @since 0.2.0
	 */
	public long getExtendedPanId() {
		if( waitForNetwork() == false ) {
			logger.info("Failed to reach the {} level: getExtendedPanId() failed", DriverStatus.NETWORK_READY);
			return -1;
		}
		
		int[] result = getDeviceInfo(ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.EXT_PAN_ID);
		
		if( result == null ){
			return -1;
		} else {
			return Integers.shortFromInts(result, 7, 0);
		}	}
	
	/**
	 * 
	 * @return
	 * @since 0.2.0
	 */
	public long getIEEEAddress() {
		
		if( ieeeAddress != -1 ){
			return ieeeAddress;
		}
		
		if( waitForNetwork() == false ) {
			logger.info("Failed to reach the {} level: getIEEEAddress() failed", DriverStatus.NETWORK_READY);
			return -1;
		}
		
		int[] result = getDeviceInfo(ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.IEEE_ADDR);
		
		if( result == null ){
			return -1;
		} else {
			ieeeAddress = Integers.longFromInts(result, 7, 0);
			return ieeeAddress;
		}
	}

	/**
	 * 
	 * @return
	 * @since 0.2.0
	 */
	public int getCurrentPanId(){		
		if( waitForNetwork() == false ) {
			logger.info("Failed to reach the {} level: getCurrentPanId() failed", DriverStatus.NETWORK_READY);
			return -1;
		}
		
		int[] result = getDeviceInfo(ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.PAN_ID);
		
		if( result == null ){
			return -1;
		} else {
			return Integers.shortFromInts(result, 1, 0);
		}
	}
	
	/**
	 * 
	 * @return
	 * @since 0.2.0
	 */
	public int getCurrentChannel(){
		if( waitForNetwork() == false ) {
			logger.info("Failed to reach the {} level: getCurrentChannel() failed", DriverStatus.NETWORK_READY);
			return -1;
		}
		
		int[] result = getDeviceInfo(ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.CHANNEL);
		
		if( result == null ){
			return -1;
		} else {
			return result[0];
		}
	}	

	/**
	 * 
	 * @return
	 * @since 0.2.0
	 */
	public int getCurrentState(){
		if( waitForNetwork() == false ) {
			logger.info("Failed to reach the {} level: getCurrentChannel() failed", DriverStatus.NETWORK_READY);
			return -1;
		}
		
		int[] result = getDeviceInfo(ZB_GET_DEVICE_INFO.DEV_INFO_TYPE.STATE);
		
		if( result == null ){
			return -1;
		} else {
			return result[0];
		}
	}	
	
	
	private int[] getDeviceInfo(int type){
		ZB_GET_DEVICE_INFO_RSP response = (ZB_GET_DEVICE_INFO_RSP) sendSynchrouns(
				high, new ZB_GET_DEVICE_INFO(type)
		);
		
		if ( response == null ){
			logger.debug("Failed getDeviceInfo for {} due to null value", type);
			return null;
		} else if ( response.Param != type ) {
			logger.debug("Failed getDeviceInfo for {} non matching response returned {}", type, response.Param);
			return null;
		} else {
			logger.debug("Successed getDeviceInfo for {}", type); 
			return response.Value;
		}
	}

	public int getZigBeeNodeMode(){
		ZB_READ_CONFIGURATION_RSP response = 
			(ZB_READ_CONFIGURATION_RSP) sendSynchrouns(
				high, 
				new ZB_READ_CONFIGURATION( ZB_WRITE_CONFIGURATION.CONFIG_ID.ZCD_NV_LOGICAL_TYPE )
		);
		if ( response != null && response.Status == 0 ) {
			return response.Value[0];
		} else {
			return -1;
		}
	}	
	
	public DriverStatus getDriverStatus() {
		return state;
	}
	
}

