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

package it.cnr.isti.zigbee.ha.driver;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * 
 * @version $LastChangedRevision: 669 $ ($LastChangedDate: 2009-11-19 18:49:43 +0100 (Thu, 19 Nov 2009) $)
 * @since 0.4.0
 * 
 */
public class ArraysUtil {

    /**
     * Create a new <code>int[]</code> by concatenating prefix and postfix
     *   
     * @param prefix the first the <code>int[]</code> to join
     * @param postfix the second the <code>int[]</code> to join
     * @return the new <code>int[]</code> obtained by concatenating prefix and postfix
     */
    public static final int[] append(int[] prefix, int[] postfix){
	int[] result = new int[prefix.length+postfix.length];
	System.arraycopy(prefix, 0, result, 0, prefix.length);
	System.arraycopy(postfix, 0, result, prefix.length, postfix.length);
	return result;
    }
    
}
