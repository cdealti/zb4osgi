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

package it.cnr.isti.zigbee.zcl.library.impl.core;

import it.cnr.isti.zigbee.api.Cluster;
import it.cnr.isti.zigbee.zcl.library.api.core.Command;

import com.itaca.ztool.util.ByteUtils;
 /**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 *
 */
public class ZCLFrame {

	private ZCLHeaderImpl header;
	private byte[] payload;
	private byte[] frame;
	
	public ZCLFrame(Cluster cluster) {
		byte[] frame = cluster.getClusterMsg();
		header = new ZCLHeaderImpl(frame);
		
		int srcPos = header.size(); 
		int lenght = frame.length - header.size();
		payload = new byte[lenght];
		System.arraycopy(frame, srcPos, payload, 0, lenght);
	}

	public ZCLFrame(Command cmd, boolean isEnableddefaultResponse) {
		header = new ZCLHeaderImpl(cmd, isEnableddefaultResponse);
		payload = cmd.getPayload();
		frame = createFrame();
	}
	
	public ZCLHeaderImpl getHeader(){
		return header;
	}
	
	public byte[] getPayload(){
		return payload;
	}
	
	private byte[] createFrame(){
		byte[] frame = new byte[header.size() + payload.length];
		System.arraycopy(header.toByte(), 0, frame, 0, header.size());
		System.arraycopy(payload, 0, frame, header.size(), payload.length);
		return frame;
	}
	
   public byte[] toByte(){
	   return frame;
   }
	
	public int size(){
		return toByte().length;
	}


	
	public String toString() {
		return 
		"[ ZCL Header: " + ByteUtils.toBase16( getHeader().toByte() ) 
				+ ", ZCL Payload: " + ByteUtils.toBase16(getPayload()) 
		+ "]";
	}
}
