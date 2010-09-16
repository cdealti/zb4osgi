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

package com.itaca.ztool.api.simple;

import com.itaca.ztool.api.ZToolCMD;
import com.itaca.ztool.api.ZToolPacket;
import com.itaca.ztool.util.DoubleByte;

/**
 *
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 665 $ ($LastChangedDate: 2009-11-19 18:09:24 +0100 (Thu, 19 Nov 2009) $)
 */
public class ZB_APP_REGISTER_RSP extends ZToolPacket/* implements IRESPONSE,ISIMPLEAPI*/{
    /// <name>TI.ZPI2.ZB_APP_REGISTER_RSP.Status</name>
        /// <summary>Status</summary>
        public int Status;

        /// <name>TI.ZPI2.ZB_APP_REGISTER_RSP</name>
        /// <summary>Constructor</summary>
        public ZB_APP_REGISTER_RSP()
        {
        }

        public ZB_APP_REGISTER_RSP(int[] framedata)
        {
            this.Status = framedata[0];
            super.buildPacket(new DoubleByte(ZToolCMD.ZB_APP_REGISTER_RSP), framedata);
        }

        /// <name>TI.ZPI2.ZB_APP_REGISTER_RSP.AF_STATUS</name>
        /// <summary>Status code for AF responses</summary>
        public class AF_STATUS 
        {
            /// <name>TI.ZPI2.ZB_APP_REGISTER_RSP.AF_STATUS.FAILED</name>
            /// <summary>Status code for AF responses</summary>
            public static final int FAILED = 1;
            /// <name>TI.ZPI2.ZB_APP_REGISTER_RSP.AF_STATUS.INVALID_PARAMETER</name>
            /// <summary>Status code for AF responses</summary>
            public static final int INVALID_PARAMETER = 3;
            /// <name>TI.ZPI2.ZB_APP_REGISTER_RSP.AF_STATUS.MEM_FAIL</name>
            /// <summary>Status code for AF responses</summary>
            public static final int MEM_FAIL = 2;
            /// <name>TI.ZPI2.ZB_APP_REGISTER_RSP.AF_STATUS.SUCCESS</name>
            /// <summary>Status code for AF responses</summary>
            public static final int SUCCESS = 0;
        }

}
