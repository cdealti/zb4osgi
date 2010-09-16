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
public class SYS_RESET extends ZToolPacket /*implements IREQUEST,ISYSTEM*/ {
    /// <name>TI.ZPI1.SYS_RESET.Type</name>
    /// <summary>requests a target device reset (0) or serial bootloader reset (1). If the target device does not support serial bootloading, bootloader reset commands are ignored and no response is sent from the target.</summary>
    public int Type;

    /// <name>TI.ZPI1.SYS_RESET</name>
    /// <summary>Constructor</summary>
    @Deprecated
    public SYS_RESET() {
    }

    public SYS_RESET(int reset_type1) {
        this.Type = reset_type1;

        int[] framedata = new int[1];
        framedata[0] = this.Type;

        super.buildPacket(new DoubleByte(ZToolCMD.SYS_RESET), framedata);
    }
    
    public SYS_RESET(int[] framedata) {
        this.Type = framedata[0];

        super.buildPacket(new DoubleByte(ZToolCMD.SYS_RESET), framedata);
    }

    /// <name>TI.ZPI1.SYS_RESET.RESET_TYPE</name>
    /// <summary>Reset type</summary>
    public class RESET_TYPE {
        /// <name>TI.ZPI1.SYS_RESET.RESET_TYPE.SERIAL_BOOTLOADER</name>
        /// <summary>Reset type</summary>
        public static final int SERIAL_BOOTLOADER = 1;
        /// <name>TI.ZPI1.SYS_RESET.RESET_TYPE.TARGET_DEVICE</name>
        /// <summary>Reset type</summary>
        public static final int TARGET_DEVICE = 0;
        }
}
