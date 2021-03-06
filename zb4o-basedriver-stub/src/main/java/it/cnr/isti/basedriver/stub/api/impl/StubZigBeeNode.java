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
package it.cnr.isti.basedriver.stub.api.impl;

import it.cnr.isti.zigbee.api.ZigBeeNode;

import java.util.Dictionary;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public class StubZigBeeNode implements ZigBeeNode {
	
	private String ieee;
	private int nwk;

	public StubZigBeeNode(String ieee, int nwk) {
		this.ieee = ieee;
		this.nwk = nwk;
	}

	public Dictionary getDescription() {
		return null;
	}

	public String getIEEEAddress() {
		return ieee;
	}

	public int getNetworkAddress() {
		return nwk;
	}

	public void setnewNetworkAddress(int networkAddress) {
		// TODO Auto-generated method stub
		
	}

	

}
