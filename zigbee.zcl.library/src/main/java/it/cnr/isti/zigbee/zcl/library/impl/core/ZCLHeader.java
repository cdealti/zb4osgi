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

package it.cnr.isti.zigbee.zcl.library.impl.core;

import it.cnr.isti.zigbee.zcl.library.impl.core.ZCLLayer;
import it.cnr.isti.zigbee.zcl.library.api.core.Command;
/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 668 $ ($LastChangedDate: 2009-11-19 18:48:39 +0100 (Thu, 19 Nov 2009) $)
 *
 */
public class ZCLHeader {
	
	private ZCLFrameControl frameControl;
	private byte[] manufacturerId;
	private byte transcationId;
	private byte commandId;
	
	
	private Command cmd;	
	private byte[] header;
	private byte[] frame;

	public ZCLHeader(Command cmd, boolean isDefaultResponseEnabled) {
		this.cmd = cmd;		
		
		frameControl = new ZCLFrameControl(cmd,isDefaultResponseEnabled);
		manufacturerId = cmd.getManufacturerId();
		transcationId = ZCLLayer.getTranscactionId();
		commandId = cmd.getHeaderCommandId();
		
		header = createHeader();
	}
	
	private byte[] createHeader(){
		byte[] newHeader;
		if (cmd.isManufacturerExtension()){
			newHeader = new byte[5];
			newHeader[0] = frameControl.toByte();
			newHeader[1] = manufacturerId[0];
			newHeader[2] = manufacturerId[1];
			newHeader[3] = transcationId;
			newHeader[4] = commandId;
		}
		else {
			newHeader = new byte[3];
			newHeader[0] = frameControl.toByte();
			newHeader[1] = transcationId;
			newHeader[2] = commandId;			
		}
		return newHeader;
	}
	
	public ZCLHeader(byte[] frame) {
		this.frame = frame;
		frameControl = new ZCLFrameControl(frame[0]);
		header = copyHeader(frameControl.isManufacturerExtension());			
	}
	
  
	private byte[] copyHeader(boolean extendedHeader) {
		byte[] resultHeader;
		if(extendedHeader){
			resultHeader = new byte[5];
			System.arraycopy(frame, 0, resultHeader, 0, 5);
			manufacturerId = new byte[2];
			manufacturerId[0] = resultHeader[1];
			manufacturerId[1] = resultHeader[2];
			transcationId = resultHeader[3];
			commandId = resultHeader[4];	
		} else{
			resultHeader = new byte[3];
			System.arraycopy(frame, 0, resultHeader, 0, 3);
			transcationId = resultHeader[1];
			commandId = resultHeader[2];				
		}
		return resultHeader;
	}



	public ZCLFrameControl getFramecontrol(){
		return frameControl;
	}
	
	public byte[] getManufacturerId() {
		return manufacturerId;
	}
	
	public byte getTransactionId() {
		return transcationId;
	}
	
	public byte	 getCommandId() {
		return commandId;
	}
		
	
	public byte[] toByte() {
		return header;
	}
	
	public int size(){
		return toByte().length;
	}
	
}
