/*
   Copyright 2012-2013 CNR-ISTI, http://isti.cnr.it
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
package it.cnr.isti.zigbee.zcl.library.impl.security_safety.ias_ace;

import it.cnr.isti.zigbee.zcl.library.api.core.ZBSerializer;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.IASACE;
import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_ace.BypassPayload;
import it.cnr.isti.zigbee.zcl.library.impl.core.AbstractCommand;
import it.cnr.isti.zigbee.zcl.library.impl.core.DefaultSerializer;

public class BypassCommand extends AbstractCommand {

    private BypassPayload bypassPayload;

    public BypassCommand(BypassPayload bypassPayload){
        super(IASACE.BYPASS);
        this.bypassPayload = bypassPayload;
    }

    public byte[] getPayload(){
        if( payload == null){
            int length = 4 + bypassPayload.getNumberOfZones()*4;

            payload = new byte[length];
            ZBSerializer serializer = new DefaultSerializer(payload,0);
            serializer.append_int(bypassPayload.getNumberOfZones());
            for(int i = 0; i < bypassPayload.getNumberOfZones(); i++){
                serializer.append_int(bypassPayload.getZonesID()[i]);
            }
        }
        return payload;
    }
}