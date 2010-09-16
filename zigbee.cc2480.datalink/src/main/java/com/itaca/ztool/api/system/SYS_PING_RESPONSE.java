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

package com.itaca.ztool.api.system;

import com.itaca.ztool.api.ZToolCMD;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.util.DoubleByte;

/**
 *
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 665 $ ($LastChangedDate: 2009-11-19 18:09:24 +0100 (Thu, 19 Nov 2009) $)
 */
public class SYS_PING_RESPONSE extends ZToolPacket /*implements IRESPONSE,ISYSTEM*/ {
    /// <name>TI.ZPI1.SYS_PING_RESPONSE.Capabilities</name>
    /// <summary>This field represents the interfaces that this device can handle (compiled into the device).</summary>
    public int Capabilities;

    /// <name>TI.ZPI1.SYS_PING_RESPONSE</name>
    /// <summary>Constructor</summary>
    public SYS_PING_RESPONSE() {
    }

    public SYS_PING_RESPONSE(DoubleByte capabilities1) {
        this.Capabilities = capabilities1.get16BitValue();

        int[] framedata = new int[2];
        framedata[0] = capabilities1.getLsb();
        framedata[1] = capabilities1.getMsb();

        super.buildPacket(new DoubleByte(ZToolCMD.SYS_PING_RESPONSE), framedata);
    }
    
    public SYS_PING_RESPONSE(int[] framedata) {
         this.Capabilities= new DoubleByte(framedata[1],framedata[0]).get16BitValue();

        super.buildPacket(new DoubleByte(ZToolCMD.SYS_PING_RESPONSE), framedata);
    }

    /// <name>TI.ZPI1.SYS_PING_RESPONSE.CAPABILITIES</name>
    /// <summary>Capabilities bitfield</summary>
    public class CAPABILITIES {
            /// <name>TI.ZPI2.SYS_PING_RESPONSE.CAPABILITIES.MT_CAP_AF</name>
            /// <summary>Capabilities bitfield</summary>
            public static final int MT_CAP_AF = 8;
            /// <name>TI.ZPI2.SYS_PING_RESPONSE.CAPABILITIES.MT_CAP_APP</name>
            /// <summary>Capabilities bitfield</summary>
            public static final int MT_CAP_APP = 0x100;
            /// <name>TI.ZPI2.SYS_PING_RESPONSE.CAPABILITIES.MT_CAP_DEBUG</name>
            /// <summary>Capabilities bitfield</summary>
            public static final int MT_CAP_DEBUG = 0x80;
            /// <name>TI.ZPI2.SYS_PING_RESPONSE.CAPABILITIES.MT_CAP_MAC</name>
            /// <summary>Capabilities bitfield</summary>
            public static final int MT_CAP_MAC = 2;
            /// <name>TI.ZPI2.SYS_PING_RESPONSE.CAPABILITIES.MT_CAP_NWK</name>
            /// <summary>Capabilities bitfield</summary>
            public static final int MT_CAP_NWK = 4;
            /// <name>TI.ZPI2.SYS_PING_RESPONSE.CAPABILITIES.MT_CAP_SAPI</name>
            /// <summary>Capabilities bitfield</summary>
            public static final int MT_CAP_SAPI = 0x20;
            /// <name>TI.ZPI2.SYS_PING_RESPONSE.CAPABILITIES.MT_CAP_SYS</name>
            /// <summary>Capabilities bitfield</summary>
            public static final int MT_CAP_SYS = 1;
            /// <name>TI.ZPI2.SYS_PING_RESPONSE.CAPABILITIES.MT_CAP_UTIL</name>
            /// <summary>Capabilities bitfield</summary>
            public static final int MT_CAP_UTIL = 0x40;
            /// <name>TI.ZPI2.SYS_PING_RESPONSE.CAPABILITIES.MT_CAP_ZDO</name>
            /// <summary>Capabilities bitfield</summary>
            public static final int MT_CAP_ZDO = 0x10;
            /// <name>TI.ZPI2.SYS_PING_RESPONSE.CAPABILITIES.NONE</name>
            /// <summary>Capabilities bitfield</summary>
            public static final int NONE = 0;
        }
}
