/*
   Copyright 2008-2010 CNR-ISTI, http://isti.cnr.it
   Institute of Information Science and Technologies 
   of the Italian National Research Council 

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

package com.itaca.ztool.api.zdo;

import it.cnr.isti.primitvetypes.util.Integers;

import com.itaca.ztool.api.ZToolAddress16;
import com.itaca.ztool.api.ZToolCMD;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.util.DoubleByte;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 665 $ ($LastChangedDate: 2009-11-19 18:09:24 +0100 (Thu, 19 Nov 2009) $)
 * @since 0.1.0
 */
public class ZDO_SIMPLE_DESC_REQ extends ZToolPacket /*implements IREQUEST,IZDO*/ {
    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_REQ.DstAddr</name>
    /// <summary>destination address</summary>
    public ZToolAddress16 DstAddr;
    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_REQ.Endpoint</name>
    /// <summary>End point</summary>
    public int Endpoint;
    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_REQ.NWKAddrOfInterest</name>
    /// <summary>NWK address for the request</summary>
    public ZToolAddress16 NWKAddrOfInterest;

    /// <name>TI.ZPI1.ZDO_SIMPLE_DESC_REQ</name>
    /// <summary>Constructor</summary>
    public ZDO_SIMPLE_DESC_REQ() {
    }

    public ZDO_SIMPLE_DESC_REQ(ZToolAddress16 dstAddr, ZToolAddress16 nwkAddressOfInterest, int endPoint) {
        int[] framedata = new int[5];
        framedata[0] = dstAddr.getLsb();
        framedata[1] = dstAddr.getMsb();
        framedata[2] = nwkAddressOfInterest.getLsb();
        framedata[3] = nwkAddressOfInterest.getMsb();
        framedata[4] = endPoint;
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_SIMPLE_DESC_REQ), framedata);
    }

	public ZDO_SIMPLE_DESC_REQ(short nwkAddress, byte endPoint) {
		int[] framedata = new int[5];
		
        framedata[0] = Integers.getByteAsInteger(nwkAddress, 0);
        framedata[1] = Integers.getByteAsInteger(nwkAddress, 1);
        framedata[2] = framedata[0];
        framedata[3] = framedata[1];
        framedata[4] = endPoint & 0xFF;
        
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_SIMPLE_DESC_REQ), framedata);
	}

}
