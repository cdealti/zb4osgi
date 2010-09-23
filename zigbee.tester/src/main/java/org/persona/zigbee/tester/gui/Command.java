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

package org.persona.zigbee.tester.gui;

import it.cnr.isti.zigbee.ha.cluster.glue.Cluster;

import java.lang.reflect.Method;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.3.0
 */
public class Command {
	
	private Method method;
	private Cluster cluster;
	
	public Command(Cluster c, Method m){
		cluster = c;
		method = m;
	}
	
	public String[] getInputParameters(){
		Class<?>[] params = method.getParameterTypes();
		String[] types = new String[params.length];
		for (int i = 0; i < types.length; i++) {
			types[i]=params[i].getName();
		}
		return types;
	}
	
	public String invoke(String[] values) throws Exception{
		Class<?>[] params = method.getParameterTypes();
		Object[] objs = new Object[params.length];
		for (int i = 0; i < objs.length; i++) {
			if ( params[i].isAssignableFrom( long.class ) ) objs[i] = Long.decode(values[i]).longValue();
			else if ( params[i].isAssignableFrom( int.class ) )objs[i] = Integer.decode(values[i]).intValue();
			else if ( params[i].isAssignableFrom( short.class ) ) objs[i] = Short.decode(values[i]).shortValue();
			else if ( params[i].isAssignableFrom( byte.class ) ) objs[i] = Byte.decode(values[i]).byteValue();
			else if ( params[i].isAssignableFrom( double.class ) ) objs[i] = Double.valueOf(values[i]).doubleValue();
			else if ( params[i].isAssignableFrom( float.class ) ) objs[i] = Float.valueOf(values[i]).floatValue();
			else objs[i]=values[i]; 
		}
		if( method.getReturnType() == void.class ) {
			method.invoke(cluster, objs);
			return null;
		}else{
			return method.invoke(cluster, objs).toString();
		}
	}
	
	public String getName() {
		return cluster.getName()+"."+method.getName();
	}
	
	public String toString() {
		return method.getName();
	}
	
}
