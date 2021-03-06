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

import it.cnr.isti.zigbee.zcl.library.api.security_safety.ias_ace.ZoneTable;

public class ZoneTableImpl implements ZoneTable {

    private Zone[] zoneTable;

    public ZoneTableImpl(){
        zoneTable = new Zone[256];
    }

    public Zone[] getZoneTable() {
        return zoneTable;
    }

    public void addZone(Short zoneID, Short zoneType, String zoneAddress) {
        for(int i = 0; i < zoneTable.length; i++){
            if(zoneTable[i] == null)
                zoneTable[i] = new Zone(zoneID, zoneType, zoneAddress);
        }
    }

    public void removeZone(Short zoneID) {
        for(int i = 0; i < zoneTable.length; i++){
            Zone zone = zoneTable[i];
            if(zone.getZoneID().equals(zoneID)){
                zoneTable[i] = null;
                break;
            }
        }
    }
}