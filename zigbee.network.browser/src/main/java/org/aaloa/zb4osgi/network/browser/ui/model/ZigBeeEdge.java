/*
   Copyright 2010-2013 CNR-ISTI, http://isti.cnr.it
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
package org.aaloa.zb4osgi.network.browser.ui.model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi - ISTI-CNR
 *         </a> - Copyright (c) 29/set/2010
 * @version $LastChangedRevision$ ($LastChangedDate$)
 */
public class ZigBeeEdge {

    private static final Logger logger = LoggerFactory.getLogger(ZigBeeEdge.class);

    private ZigBeeVertex from;
    private ZigBeeVertex to;


    public ZigBeeEdge(ZigBeeVertex from, ZigBeeVertex to) {
        this.from = from;
        this.to = to;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String toString() {
        return from + "->" + to;
    }

    public boolean equals(Object obj) {
        if ( obj == null )
            return false;
        return this.toString().equals(obj.toString());
    }
}
