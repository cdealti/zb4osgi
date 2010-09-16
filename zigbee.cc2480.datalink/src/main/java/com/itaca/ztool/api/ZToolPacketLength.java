/*
   Copyright 2008-2010 Andrew Rapp, http://code.google.com/p/xbee-api/

   Copyright 2008-2010 ITACA-TSB, http://www.tsb.upv.es/
   Instituto Tecnologico de Aplicaciones de Comunicacion 
   Avanzadas - Grupo Tecnologias para la Salud y el 
   Bienestar (TSB)


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

package com.itaca.ztool.api;

import com.itaca.ztool.util.ByteUtils;

/**
 * Represents a double byte XBee Address.
 * 
 * @author <a href="mailto:andrew.rapp@gmail.com">Andrew Rapp</a>
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 665 $ ($LastChangedDate: 2009-11-19 18:09:24 +0100 (Thu, 19 Nov 2009) $)
 */
public class ZToolPacketLength {
	
    private int Length;
	/**
	 * Manual says max packet length is 100 bytes so not sure why 2 bytes are needed
	 * 
	 * @param msb
	 * @param lsb
	 */
	public ZToolPacketLength(int msb, int lsb) {
            int[] doublelength=new int[2];
            doublelength[0]=msb;
            doublelength[1]=lsb;
		Length=ByteUtils.convertMultiByteToInt(doublelength);
	}

	public ZToolPacketLength(int[] length) {
		Length=ByteUtils.convertMultiByteToInt(length);
	}
        
        //Signed bytes!?
        public ZToolPacketLength(int length){
            Length=length;
        }
	
	public int getLength() {
		return this.Length;
	}
}
