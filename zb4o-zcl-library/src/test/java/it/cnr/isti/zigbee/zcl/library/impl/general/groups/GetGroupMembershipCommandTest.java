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
package it.cnr.isti.zigbee.zcl.library.impl.general.groups;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

/**
 *
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.8.0
 *
 */
public class GetGroupMembershipCommandTest {

    @Test
    public void testGetPayload() {
        GetGroupMembershipCommand req = new GetGroupMembershipCommand(new int[]{512, 32768, -512});
        assertArrayEquals( new byte[]{
                0x03,
                0x00, 0x02, 		//   512
                0x00, (byte) 0x80,	// 32768
                0x00, (byte) 0xFE 	// - 512
        },req.getPayload());
    }

}
