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

import it.cnr.isti.osgi.util.DictionaryHelper;
import it.cnr.isti.osgi.util.OSGiProperties;
import it.cnr.isti.zigbee.ha.driver.core.ReportingConfiguration;

import java.util.Dictionary;
import java.util.HashMap;

import org.osgi.framework.BundleContext;
import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author <a href="mailto:stefano.lenzi@isti.cnr.it">Stefano "Kismet" Lenzi</a>
 * @author <a href="mailto:francesco.furfari@isti.cnr.it">Francesco Furfari</a>
 * @version $LastChangedRevision$ ($LastChangedDate$)
 * @since 0.6.0
 *
 */
public class HADriverConfiguration implements ManagedService, ReportingConfiguration {
	
	private final static Logger logger = LoggerFactory.getLogger(HADriverConfiguration.class);
		
	private final HashMap<String, Object> configuration = new HashMap<String, Object>();
	private final BundleContext context;
	
	public HADriverConfiguration(BundleContext bc){
		context = bc;
		synchronized (configuration) {
			configuration.put(ReportingConfiguration.CONFIGURE_REPORTING_MIN_KEY, 
					OSGiProperties.getInt(context, ReportingConfiguration.CONFIGURE_REPORTING_MIN_KEY, ReportingConfiguration.DEFAULT_CONFIGURE_REPORTING_MIN) 
			);
			
			configuration.put(ReportingConfiguration.CONFIGURE_REPORTING_MAX_KEY, 
					OSGiProperties.getInt(context, ReportingConfiguration.CONFIGURE_REPORTING_MAX_KEY, ReportingConfiguration.DEFAULT_CONFIGURE_REPORTING_MAX) 
			);
			
			configuration.put(ReportingConfiguration.CONFIGURE_REPORTING_CHANGE_KEY, 
					OSGiProperties.getDouble(context, ReportingConfiguration.CONFIGURE_REPORTING_CHANGE_KEY, ReportingConfiguration.DEFAULT_CONFIGURE_REPORTING_CHANGE) 
			);
			
			configuration.put(ReportingConfiguration.CONFIGURE_REPORTING_OVERWRITE_KEY, 
					OSGiProperties.getBoolean(context, ReportingConfiguration.CONFIGURE_REPORTING_OVERWRITE_KEY, ReportingConfiguration.DEFAULT_CONFIGURE_REPORTING_OVERWRITE) 
			);
		}
		
		logger.debug("Initialized {} with {}", this, configuration);
	}
	
	@SuppressWarnings("unchecked")
	public void updated(Dictionary newConfig) throws ConfigurationException {
		
		logger.info("Updating configuration with {}", newConfig);
		
		if( newConfig == null ){
			logger.debug("New configuration is null, avoiding to update it");
			return;
		}
		
		DictionaryHelper helper = new DictionaryHelper(newConfig);
		boolean isChanged = false;
		synchronized (this) {
			isChanged = setInteger(ReportingConfiguration.CONFIGURE_REPORTING_MIN_KEY, 
					helper.getInt(ReportingConfiguration.CONFIGURE_REPORTING_MIN_KEY, getReportingMinimum()) 
			);		
			
			isChanged = setInteger(ReportingConfiguration.CONFIGURE_REPORTING_MAX_KEY, 
					helper.getInt(ReportingConfiguration.CONFIGURE_REPORTING_MAX_KEY, getReportingMaximum()) 
			);
			
			isChanged = setDouble(ReportingConfiguration.CONFIGURE_REPORTING_CHANGE_KEY, 
					helper.getDouble(ReportingConfiguration.CONFIGURE_REPORTING_CHANGE_KEY, getReportingChange()) 
			);			
			
			isChanged = setBoolean(ReportingConfiguration.CONFIGURE_REPORTING_OVERWRITE_KEY, 
					helper.getBoolean(ReportingConfiguration.CONFIGURE_REPORTING_OVERWRITE_KEY, getReportingOverwrite()) 
			);			
		}
		
		logger.debug("Current configuration after applying new configuration is {}", configuration);
		
	}

	private int getInt(String key){
		return ((Integer) configuration.get(key)).intValue();
	}
	
	private long getLong(String key){
		return ((Long) configuration.get(key)).longValue();
	}
	
	private double getDouble(String key){
		return ((Double) configuration.get(key)).doubleValue();
	}
	
	private boolean getBoolean(String key){
		return ((Boolean) configuration.get(key)).booleanValue();
	}
	
	private String getString(String key){
		return (String) configuration.get(key);
	}	
	
	private boolean setStringCaseInsensitve(String key, String value){
		if( getString(key).equalsIgnoreCase(value) ) return false;
		configuration.put(key, value);
		return true;
	}
	
	private boolean setStringCaseSensitve(String key, String value){
		if( getString(key).compareTo(value) == 0) return false;
		configuration.put(key, value);
		return true;
	}

	private boolean setLong(String key, long value){
		if( getLong(key) == value ) return false;
		configuration.put(key, value);
		return true;
	}

	private boolean setInteger(String key, int value){
		if( getInt(key) == value ) return false;
		configuration.put(key, value);
		return true;
	}

	private boolean setBoolean(String key, boolean value){
		if( getBoolean(key) == value ) return false;
		configuration.put(key, value);
		return true;
	}
	
	private boolean setDouble(String key, double value){
		if( getDouble(key) == value ) return false;
		configuration.put(key, value);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see it.cnr.isti.zigbee.ha.driver.ReportingConfiguration#getReportingMinimum()
	 */
	public synchronized int getReportingMinimum() {
		return getInt(ReportingConfiguration.CONFIGURE_REPORTING_MIN_KEY);
	}			

	/* (non-Javadoc)
	 * @see it.cnr.isti.zigbee.ha.driver.ReportingConfiguration#getReportingMaximum()
	 */
	public synchronized int getReportingMaximum() {
		return getInt(ReportingConfiguration.CONFIGURE_REPORTING_MAX_KEY);
	}			

	/* (non-Javadoc)
	 * @see it.cnr.isti.zigbee.ha.driver.ReportingConfiguration#getReportingChange()
	 */
	public synchronized double getReportingChange() {
		return getDouble(ReportingConfiguration.CONFIGURE_REPORTING_CHANGE_KEY);
	}			
	
	/* (non-Javadoc)
	 * @see it.cnr.isti.zigbee.ha.driver.ReportingConfiguration#getReportingOverwrite()
	 */
	public synchronized boolean getReportingOverwrite() {
		return getBoolean(ReportingConfiguration.CONFIGURE_REPORTING_OVERWRITE_KEY);
	}			
}
