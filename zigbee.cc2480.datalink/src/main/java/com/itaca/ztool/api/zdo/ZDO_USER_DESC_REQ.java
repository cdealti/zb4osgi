/*
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

import com.itaca.ztool.api.ZToolAddress16;
import com.itaca.ztool.api.ZToolCMD;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.util.DoubleByte;

/**
 *
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 665 $ ($LastChangedDate: 2009-11-19 18:09:24 +0100 (Thu, 19 Nov 2009) $)
 */
public class ZDO_USER_DESC_REQ extends ZToolPacket /*implements IREQUEST,IZDO*/ {
    /// <name>TI.ZPI1.ZDO_USER_DESC_REQ.DstAddr</name>
    /// <summary>destination address</summary>
    public ZToolAddress16 DstAddr;
    /// <name>TI.ZPI1.ZDO_USER_DESC_REQ.NWKAddrOfInterest</name>
    /// <summary>NWK address for the request</summary>
    public ZToolAddress16 nwkAddr;

    /// <name>TI.ZPI1.ZDO_USER_DESC_REQ</name>
    /// <summary>Constructor</summary>
    public ZDO_USER_DESC_REQ() {
    }

    public ZDO_USER_DESC_REQ(ZToolAddress16 num1, ZToolAddress16 num2) {
        this.DstAddr = num1;
        this.nwkAddr = num2;

        int[] framedata = new int[4];
        framedata[0] = this.DstAddr.getLsb();
        framedata[1] = this.DstAddr.getMsb();
        framedata[2] = this.nwkAddr.getLsb();
        framedata[3] = this.nwkAddr.getMsb();
        super.buildPacket(new DoubleByte(ZToolCMD.ZDO_USER_DESC_REQ), framedata);
    }

}
