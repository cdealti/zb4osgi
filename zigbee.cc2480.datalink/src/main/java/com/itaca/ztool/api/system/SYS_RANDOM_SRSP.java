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
public class SYS_RANDOM_SRSP extends ZToolPacket /*implements IREPONSE,ISYSTEM*/{
    /// <name>TI.ZPI2.SYS_RANDOM_SRSP.Value</name>
        /// <summary>Random value</summary>
        public DoubleByte Value;

        /// <name>TI.ZPI2.SYS_RANDOM_SRSP</name>
        /// <summary>Constructor</summary>
        public SYS_RANDOM_SRSP()
        {
        }

        /// <name>TI.ZPI2.SYS_RANDOM_SRSP</name>
        /// <summary>Constructor</summary>
        public SYS_RANDOM_SRSP(int[] framedata)
        {
            this.Value = new DoubleByte(framedata[1],framedata[0]);
            super.buildPacket(new DoubleByte(ZToolCMD.SYS_RANDOM_SRSP), framedata);
        }

}
