/*
   Copyright 2008-2010 Andrew Rapp, http://code.google.com/p/xbee-api/

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

package com.itaca.ztool.util;

import java.io.IOException;

/**
 * 
 * @author <a href="mailto:andrew.rapp@gmail.com">Andrew Rapp</a>
 * @author <a href="mailto:alfiva@aaa.upv.es">Alvaro Fides Valero</a>
 * @version $LastChangedRevision: 765 $ ($LastChangedDate: 2010-08-04 18:56:02 +0200 (Wed, 04 Aug 2010) $)
 *
 */
public class ByteUtils {

	/**
	 * There is a slight problem with this method that you might have noticed;  a Java int is signed, so we can't make
	 * use of the 32nd bit.  This means we this method does not support a four byte value with msb greater than 01111111 ((2^7-1) or 127).
	 * 
	 * TODO use long instead of int to support 4 bytes values.  note that long assignments are not atomic.
	 */
	public static int convertMultiByteToInt(int[] bytes) {
		
		if (bytes.length > 4) {
			throw new RuntimeException("too big");
		} else if (bytes.length == 4 && ((bytes[0] & 0x80) == 0x80)) {
			// 0x80 == 10000000, 0x7e == 01111111
			throw new RuntimeException("Java int can't support a four byte value, with msb byte greater than 7e");
		}
		
		int val = 0;
		
		for (int i = 0; i < bytes.length; i++) {
			
			if (bytes[i] > 0xFF) {
				throw new RuntimeException("Values exceeds byte range: " + bytes[i]);
			}
			
			if (i == (bytes.length - 1)) {
				val+= bytes[i];
			} else {
				val+= bytes[i] << ((bytes.length - i - 1) * 8);	
			}
		}
		
		return val;
	}
        
        public static long convertMultiByteToLong(byte[] bytes) {
		
		if (bytes.length > 8) {
			throw new IllegalArgumentException("too many bytes can't be converted to long");
		} else if (bytes.length == 8 && ((bytes[0] & 0x80) == 0x80)) {
			// 0x80 == 10000000, 0x7e == 01111111
			throw new RuntimeException("Java long can't support a 8 bytes value, where msb byte greater than 7e");
		}
		
		long val = 0;
		
		for (int i = 0; i < bytes.length; i++) {
			val += 0x000000FF & bytes[i];
			val = val << 8;	
		}
		
		return val;
	}
	
	public static int[] convertInttoMultiByte(int val) {
		
		// must decompose into a max of 4 bytes
		// b1		b2		 b3		  b4
		// 01111111 11111111 11111111 11111111
		// 127      255      255      255
		
		int size = 0;
		
		if ((val >> 24) > 0) {
			size = 4;
		} else if ((val >> 16) > 0) {
			size = 3;
		} else if ((val >> 8) > 0) {
			size = 2;
		} else {
			size = 1;
		}
		
		int[] data = new int[size];
		
		for (int i = 0; i < size; i++) {
			data[i] = (val >> (size - i - 1) * 8) & 0xFF;
		}
		
		return data;	
	}
        
        public static int[] convertLongtoMultiByte(long val) {
		
		int size = 0;
		
		if ((val >> 56) > 0) {
			size = 8;
		} else if ((val >> 48) > 0) {
			size = 7;
		} else if ((val >> 40) > 0) {
			size = 6;
		} else if ((val >> 32) > 0) {
			size = 5;
		} else if ((val >> 24) > 0) {
			size = 4;
		} else if ((val >> 16) > 0) {
			size = 3;
		} else if ((val >> 8) > 0) {
			size = 2;
		} else {
			size = 1;
		}
		
		int[] data = new int[size];
		
		for (int i = 0; i < size; i++) {
			data[i] = (int) ((val >> (size - i - 1) * 8) & 0xFF);
		}
		
		return data;	
	}
	public final static String toBase16(final int[] arr) {
		return toBase16(arr, 0, arr.length);
	}
	
	/**
	 * 
	 *  @since 0.6.0
	 */
	public final static String toBase16(final int[] arr, final int start) {
		return toBase16(arr, start, arr.length);
	}
	
	/**
	 * 
	 *  @since 0.6.0
	 */
	public final static String toBase16(final int[] arr, final int start, final int end) {
		
		StringBuffer sb = new StringBuffer();
		
		for (int i = start; i < end; i++) {
			sb.append(toBase16(arr[i]));
			
			if (i < arr.length - 1) {
				sb.append(" ");
			}
		}
		
		return sb.toString();
	}
	
	public static String toBase16(byte[] arr) {
		
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < arr.length; i++) {
			sb.append(toBase16(arr[i]));
			
			if (i < arr.length - 1) {
				sb.append(" ");
			}
		}
		
		return sb.toString();
	}
	

	public static String toBase2(int[] arr) {
		
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < arr.length; i++) {
			sb.append(toBase2(arr[i]));
			
			if (i < arr.length - 1) {
				sb.append(" ");
			}
		}
		
		return sb.toString();
	}

	public static String toBase10(int[] arr) {
		
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < arr.length; i++) {
			sb.append((arr[i]));
			
			if (i < arr.length - 1) {
				sb.append(" ");
			}
		}
		
		return sb.toString();
	}
	
	public static String toChar(int[] arr) {
		
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < arr.length; i++) {
			sb.append((char)arr[i]);
		}
		
		return sb.toString();
	}	
	
	private static String padBase2(String s) {
		
		for (int i = s.length(); i < 8; i++) {
			s = "0" + s;
		}
		
		return s;
	}
	
//	/**
//	 * Determines the bit value of a single byte
//	 * 
//	 * @param b
//	 * @return
//	 */
//	public static boolean[] parseBits(int b) {
//		
//		boolean[] results = new boolean[8];
//		
//		for (int i = 0; i < 8; i++) {
//			if (((b >> i) & 0x1) == 0x1) {
//				results[i] = true;
//			} else {
//				results[i] = false;
//			}
//		}
//	
//		return results;
//	}
	
	/**
	 * Returns true if the bit is on (1) at the specified position
	 * Position range: 1-8
	 */
	public static boolean getBit(int b, int position) {
		
		if (position < 1 || position > 8) {
			throw new IllegalArgumentException("Position is out of range");
		}
		
		if (b > 0xff) {
			throw new IllegalArgumentException("input value is larger than a byte");
		}
		
		if (((b >> (--position)) & 0x1) == 0x1) {
			return true;
		} 
		
		return false;		
	}
	
	public static String toBase16(int b) {
		
		if (b > 0xff || b < -128) {
			throw new IllegalArgumentException("Input value is larger than a byte");
		}
		if ( b < 0) {
			return "0x" + Integer.toHexString(b).substring(6);
		} else if (b < 0x10) {
			return "0x0" + Integer.toHexString(b);
		} else if (b >= 0x10){
			return "0x" + Integer.toHexString(b);			
		} else {
			throw new IllegalArgumentException("Unable to recognize the value "+b);			
		}
	}
	
	public static String toBase2(int b) {
		
		if (b > 0xff) {
			throw new IllegalArgumentException("input value is larger than a byte");
		}
		
		return padBase2(Integer.toBinaryString(b));
	}
	
	public static String formatByte(int b) {
		return "base10=" + Integer.toString(b) + ",base16=" + toBase16(b) + ",base2=" + toBase2(b);
	}
	
	public static int[] stringToIntArray(String s) {
		int[] intArr = new int[s.length()];
		
		for (int i = 0; i < s.length(); i++) {
			intArr[i] = (int)s.charAt(i);
		}
		
		return intArr;
	}
	
	/**
	 * Parses a 10-bit analog value from the input stream
	 * 
	 * @param pos relative position in packet (for logging only)
	 * 
	 * @return
	 * @throws IOException
	 */
	public static int parse10BitAnalog(int msb, int lsb) throws IOException {	
		msb = msb & 0xff;
		
		// shift up bits 9 and 10 of the msb
		msb = (msb & 0x3) << 8;
		
//		log.debug("shifted msb is " + msb);
		
		lsb = lsb & 0xff;
		
		return msb + lsb;
	}
	
	public static int parse10BitAnalog(IIntArrayInputStream in, int pos) throws IOException {
		int adcMsb = in.read("Analog " + pos + " MSB");
		int adcLsb = in.read("Analog " + pos + " LSB");
		
		return ByteUtils.parse10BitAnalog(adcMsb, adcLsb);
	}

}
