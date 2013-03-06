package it.cnr.isti.zigbee.hc.core;
/*
   Copyright 2008-2010 CNR-ISTI, http://isti.cnr.it
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



import it.cnr.isti.zigbee.api.ZigBeeDevice;

import org.osgi.framework.ServiceReference;

/**
 *
 * This class represent the main interface for the extendable architecture of the<br>
 * refinement driver. Each refinement driver from {@link ZigBeeDevice} to {@link HCDevice}<br>
 * has to implement this interface as OSGi Service and register it on OSGi platform.
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 *
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.1.0
 *
 */
public interface HCDeviceFactory {

    //TODO Allow the creation of multi-device factory

    /**
     *
     * @return return the {@link String} representing the Device Id that this driver can refine
     */
    public String getDeviceId();

    /**
     *
     * @return return an array of {@link String} containing all the cluster interfaces<br>
     * 		 	implemented by the refined device generated by this driver
     */
    public int[] getDeviceClusters();

    /**
     *
     * @return return an array of {@link String} containing all the interfaces<br>
     * 			implemented by the refined device generated by this driver
     */
    public String[] getRefinedInterfaces();


    /**
     *
     * @param zbd the {@link ZigBeeDevice} to refine
     * @return the {@link HCDeviceBase} refined from {@link ZigBeeDevice} from this<br>
     * 			refinement driver
     *
     * @throws ZigBeeHCException
     */
    public HCDeviceBase getInstance(ZigBeeDevice zbd) throws ZigBeeHCException;

    /**
     * <b>NOT IN USE</b>
     *
     * @param sr the {@link ServiceReference} referencing at the {@link ZigBeeDevice}
     * @return a matching value between the {@link ZigBeeDevice} referenced by the<br>
     * 			{@link ServiceReference} for this refinement driver
     */
    public int hasMatch(ServiceReference sr);

}
