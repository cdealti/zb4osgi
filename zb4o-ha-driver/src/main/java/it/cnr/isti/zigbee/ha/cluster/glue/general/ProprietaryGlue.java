/*
   Copyright 2008-2014 CNR-ISTI, http://isti.cnr.it
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

package it.cnr.isti.zigbee.ha.cluster.glue.general;

import it.cnr.isti.zigbee.ha.cluster.glue.Cluster;
import it.cnr.isti.zigbee.ha.driver.core.ZigBeeHAException;

/**
 *
 * @author <a href="mailto:giancarlo.riolo@isti.cnr.it">Giancarlo Riolo</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.9.0
 */
public interface ProprietaryGlue extends Cluster {

	public boolean getAttributeEP1Enable() throws ZigBeeHAException;

	public float getAttributeVoltageCostant() throws ZigBeeHAException;

	public float getAttributeCurrentCostant() throws ZigBeeHAException;

	public float getAttributeVoltageRMS() throws ZigBeeHAException;

	public float getAttributeCurrentRMS() throws ZigBeeHAException;

	public boolean getAttributeEP2Enable() throws ZigBeeHAException;

	public boolean getAttributeEP3Enable() throws ZigBeeHAException;

	public boolean getAttributeEP4EP5Enable() throws ZigBeeHAException;

	public boolean getAttributeEP9Enable() throws ZigBeeHAException;
}
