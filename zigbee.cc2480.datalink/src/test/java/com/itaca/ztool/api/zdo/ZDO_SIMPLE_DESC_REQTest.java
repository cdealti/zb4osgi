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

package com.itaca.ztool.api.zdo;

import org.junit.Test;

import com.itaca.ztool.api.ZToolAddress16;
import com.itaca.ztool.api.test.ZToolPacketUtil;

/**
 * Test class of {@link ZDO_SIMPLE_DESC_REQ}
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 658 $ ($LastChangedDate: 2009-11-10 01:30:04 +0100 (Tue, 10 Nov 2009) $)
 * @since 0.1.0
 */
public class ZDO_SIMPLE_DESC_REQTest {

	@Test
	public void testZDO_SIMPLE_DESC_REQIntByte() {
		ZDO_SIMPLE_DESC_REQ original = new ZDO_SIMPLE_DESC_REQ(
				new ZToolAddress16(0xF0,0xFA), new ZToolAddress16(0xF0, 0xFA), 0x80
		);
		ZDO_SIMPLE_DESC_REQ modified = new ZDO_SIMPLE_DESC_REQ(
				(short) 0xF0FA, (byte) 0x80
		);
		
		ZToolPacketUtil.isSamePacket(original, modified);
	}

}
