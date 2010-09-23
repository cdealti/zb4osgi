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
 * @version $LastChangedRevision$ ($LastChangedDate$)
 */
public class SYS_OSAL_TIMER_EXPIRED_IND extends ZToolPacket /*implements IINDICATION,ISYSTEM*/{
    /// <name>TI.ZPI2.SYS_OSAL_TIMER_EXPIRED_IND.Id</name>
        /// <summary>Timer ID</summary>
        public int Id;

        /// <name>TI.ZPI2.SYS_OSAL_TIMER_EXPIRED_IND</name>
        /// <summary>Constructor</summary>
        public SYS_OSAL_TIMER_EXPIRED_IND()
        {
        }

        /// <name>TI.ZPI2.SYS_OSAL_TIMER_EXPIRED_IND</name>
        /// <summary>Constructor</summary>
        public SYS_OSAL_TIMER_EXPIRED_IND(int[] framedata)
        {
            this.Id = framedata[0];
            super.buildPacket(new DoubleByte(ZToolCMD.SYS_OSAL_TIMER_EXPIRED_IND), framedata);
        }

        public SYS_OSAL_TIMER_EXPIRED_IND(int num1)
        {
            this.Id = num1;
            int[] framedata={num1};
            super.buildPacket(new DoubleByte(ZToolCMD.SYS_OSAL_TIMER_EXPIRED_IND), framedata);
        }

}
