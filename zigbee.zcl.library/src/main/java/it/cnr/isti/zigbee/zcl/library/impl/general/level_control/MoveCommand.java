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

package it.cnr.isti.zigbee.zcl.library.impl.general.level_control;

import it.cnr.isti.zigbee.zcl.library.api.core.ZBSerializer;
import it.cnr.isti.zigbee.zcl.library.api.general.Alarms;
import it.cnr.isti.zigbee.zcl.library.api.general.LevelControl;
import it.cnr.isti.zigbee.zcl.library.impl.core.AbstractCommand;
import it.cnr.isti.zigbee.zcl.library.impl.core.DefaultSerializer;
/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision: 718 $ ($LastChangedDate: 2010-03-10 17:22:58 +0100 (Wed, 10 Mar 2010) $)
 *
 */
public class MoveCommand extends AbstractCommand {
	
	private byte mode; 
	private short rate;

	
	public MoveCommand(byte mode, short rate){
		this( mode, rate, false);
	}
	
	public MoveCommand(byte mode, short rate, boolean hasOnOff){
		super(LevelControl.MOVE_ID);			
		if( hasOnOff ) {
			setId(LevelControl.MOVE_TO_LEVEL_WITH_ONOFF_ID);
		}
		this.mode = mode;
		this.rate = rate;
	}
	
	public byte[] getPayload(){	
		if( payload == null){
			payload = new byte[3];
			ZBSerializer serializer = new DefaultSerializer(payload,0);
			serializer.append_byte((byte)mode);
			serializer.append_short((short)rate);
		}
		return payload;
	}
}
