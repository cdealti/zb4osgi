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

package it.cnr.isti.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @version $LastChangedRevision: 662 $ ($LastChangedDate: 2009-11-10 01:45:32 +0100 (Tue, 10 Nov 2009) $)
 * @since 0.3.0
 *
 */
public class ThreadUtils {
	
    private final static Logger logger = LoggerFactory.getLogger(ThreadUtils.class);
    
	public static final void waitNonPreemptive(long time){
		final long end = System.currentTimeMillis() + time;
		do{
			try {
			    final long delta = Math.max( end - System.currentTimeMillis(), 0 ); 
			    logger.info( "{} waiting for {}ms", Thread.currentThread(), delta );
				Thread.sleep( delta );
			} catch (InterruptedException ignored) {
			}
		}while(end > System.currentTimeMillis());
	}
	
	/**
	 * 
	 * @since 0.4.0
	 */
    public static final void waitingUntil(long time){
        do{
            try {
                final long delta = Math.max( time - System.currentTimeMillis(), 0 ); 
                logger.info( "{} waiting for {}ms", Thread.currentThread(), delta );
                Thread.sleep( delta );
            } catch (InterruptedException ignored) {
            }
        }while(time > System.currentTimeMillis());
    }
}
