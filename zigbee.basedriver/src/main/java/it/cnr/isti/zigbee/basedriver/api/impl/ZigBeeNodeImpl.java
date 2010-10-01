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

package it.cnr.isti.zigbee.basedriver.api.impl;

import it.cnr.isti.zigbee.api.ZigBeeNode;
import it.cnr.isti.zigbee.basedriver.Activator;
import it.cnr.isti.zigbee.util.IEEEAddress;

import java.util.Dictionary;
import java.util.Properties;

import com.itaca.ztool.api.ZToolAddress64;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public class ZigBeeNodeImpl implements ZigBeeNode {

	final private int nwkAddress;
	final private Properties description;
	
	/**
	 * 
	 * @param nwkAddress
	 * @param ieee
	 * @param pan
	 * @since 0.6.0 - Revision 67
	 */
    public ZigBeeNodeImpl(int nwkAddress, String ieee, short pan){     
        this.nwkAddress = nwkAddress;
        IEEEAddress.fromColonNotation( ieee ); //Only for checking the IEEE format

        description  = new Properties();        
        description.put(ZigBeeNode.IEEE_ADDRESS, ieee );
        description.put(ZigBeeNode.NWK_ADDRESS, nwkAddress);
        description.put(ZigBeeNode.PAN_ID, pan);
    }
    
    /**
     * 
     * @param nwkAddress
     * @param ieee
     * @param pan
     * @since 0.6.0 - Revision 67
     */
	public ZigBeeNodeImpl(int nwkAddress, String ieee){		
		this.nwkAddress = nwkAddress;
		IEEEAddress.fromColonNotation( ieee ); //Only for checking the IEEE format

		description  = new Properties();		
        description.put(ZigBeeNode.IEEE_ADDRESS, ieee );
        description.put(ZigBeeNode.NWK_ADDRESS, nwkAddress);
        description.put(ZigBeeNode.PAN_ID, Activator.getCurrentConfiguration().getPanId());
	}

	
    public ZigBeeNodeImpl(int nwkAddress, ZToolAddress64 ieeeAddress){      
        this.nwkAddress = nwkAddress;
        
        description  = new Properties();
        description.put(ZigBeeNode.IEEE_ADDRESS, IEEEAddress.toString(ieeeAddress.getLong()));
        description.put(ZigBeeNode.NWK_ADDRESS, nwkAddress);
        description.put(ZigBeeNode.PAN_ID, Activator.getCurrentConfiguration().getPanId());
    }
	

	@SuppressWarnings("unchecked")
	public Dictionary getDescription() {
		return description;
	}

	public String getIEEEAddress() {
		return description.getProperty(ZigBeeNode.IEEE_ADDRESS);
	}

	public int getNetworkAddress() {
		return nwkAddress;
	}	
	
	public String toString() {
	    return getNetworkAddress() + "(" + getIEEEAddress() + ") ";
	}
	
	public boolean equals(Object obj) {
	    if ( obj == this ) {
	        return true;
	    }else if ( obj instanceof ZigBeeNode ){
	        ZigBeeNode node = (ZigBeeNode) obj;
	        return nwkAddress == node.getNetworkAddress() && getIEEEAddress().equals( node.getIEEEAddress() );
	    }else{
	        return false;
	    }
	}
	
	public int hashCode() {
	    return getIEEEAddress().hashCode();
	}

}
