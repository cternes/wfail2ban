/*******************************************************************************
 * Copyright 2012 Christian Ternes
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.slackspace.wfail2ban.config.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.slackspace.wfail2ban.config.FilterManager;
import de.slackspace.wfail2ban.config.JailManager;
import de.slackspace.wfail2ban.filter.Filter;
import de.slackspace.wfail2ban.util.ConsolePrinter;
import de.slackspace.wfail2ban.util.NumberUtil;

public class DefaultJailManager implements JailManager {

	private static final String FILTER_FILE_SUFFIX = ".conf";
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String filterDirectory = "filter/";
	private FilterManager filterManager = new DefaultFilterManager();
	private Ini ini;
	
	public DefaultJailManager(File configFile) {
		if(logger.isDebugEnabled()) {
			logger.debug("Reading configuration file " + configFile + "...");
		}
		if(configFile == null || !configFile.canRead()) {
			throw new IllegalArgumentException("Configuration file does not exist or cannot be read!");
		}
		
		try {
			ini = new Ini(configFile);
		} catch (InvalidFileFormatException e) {
			logger.error("Configuration file is not valid. Error was: ", e);
		} catch (IOException e) {
			logger.error("Configuration file could not be read. Error was: ", e);
		}
	}
	
	@Override
	public DefaultConfiguration readDefaultConfig() {
		DefaultConfiguration config = new DefaultConfiguration();
		Section defaultSection = ini.get(DefaultConfiguration.NAME);
		if(defaultSection != null) {
			String delayPeriod = defaultSection.get(DefaultConfiguration.DELAY_PERIOD);
			config.setDelayPeriod(delayPeriod);
			
			String refreshPeriod = defaultSection.get(DefaultConfiguration.REFRESH_PERIOD);
			config.setRefreshPeriod(refreshPeriod);
			
			String maxRetry = defaultSection.get(DefaultConfiguration.MAX_RETRY);
			config.setMaxRetry(maxRetry);
			
			String findTime = defaultSection.get(DefaultConfiguration.FIND_TIME);
			config.setFindtime(findTime);
		}
		
		ConsolePrinter.printMessage("--------------GLOBAL CONFIG---------------");
		ConsolePrinter.printMessage("delayPeriod="+(config.getDelayPeriod()/1000)+"s");
		ConsolePrinter.printMessage("refreshPeriod="+(config.getRefreshPeriod()/1000)+"s");
		ConsolePrinter.printMessage("findTime="+config.getFindtime()+"s");
		ConsolePrinter.printMessage("maxRetry="+config.getMaxRetry());
		ConsolePrinter.printMessage("------------------------------------------");
		ConsolePrinter.printMessage("");
		
		return config;
	}

	@Override
	public List<Filter> readJails(DefaultConfiguration config) {
		List<Filter> filterList = new ArrayList<Filter>();
		Iterator<String> sectionIter = ini.keySet().iterator();
		while(sectionIter.hasNext()) {
			String sectionKey = sectionIter.next();
			Section section = ini.get(sectionKey);
			Filter filter = readSection(section, sectionKey, config);
			if(filter != null) {
				filterList.add(filter);
				if(logger.isDebugEnabled()) {
					logger.debug("Activating filter " + filter + "...");
				}
			}
		}
		
		return filterList;
	}

	private Filter readSection(Section section, String sectionName, DefaultConfiguration config) {
		boolean isEnabled = false;
		String enabled = section.get(Filter.ENABLED);
		if(enabled != null) {
			isEnabled = Boolean.parseBoolean(enabled);
		}
		
		if(isEnabled) {
			String filter = section.get(Filter.FILTER);
			if(filter != null) {
				Filter parsedFilter = readFilter(filter);
				parsedFilter.setName(sectionName);
				
				String logpath = section.get(Filter.LOGPATH);
				if(logpath != null) {
					parsedFilter.setLogfilePath(logpath);
				}
				
				String maxretry = section.get(Filter.MAXRETRY);
				if(NumberUtil.isInteger(maxretry)) {
					parsedFilter.setMaxRetry(NumberUtil.toInteger(maxretry));
				}
				else {
					parsedFilter.setMaxRetry(config.getMaxRetry());
				}
				
				String findtime = section.get(Filter.FINDTIME);
				if(NumberUtil.isLong(findtime)) {
					parsedFilter.setFindtime(NumberUtil.toLong(findtime));
				}
				else {
					parsedFilter.setFindtime(config.getFindtime());
				}
				
				ConsolePrinter.printMessage("--------------ACTIVE FILTER---------------");
				ConsolePrinter.printMessage("filterName="+parsedFilter.getName());
				ConsolePrinter.printMessage("failRegex="+parsedFilter.getFailPattern());
				ConsolePrinter.printMessage("logfilePath="+parsedFilter.getLogfilePath());
				ConsolePrinter.printMessage("findTime="+parsedFilter.getFindtime()+"s");
				ConsolePrinter.printMessage("maxRetry="+parsedFilter.getMaxRetry());
				ConsolePrinter.printMessage("------------------------------------------");
				ConsolePrinter.printMessage("");
				
				return parsedFilter;
			}
		}
		return null;
	}

	private Filter readFilter(String filter) {
		return filterManager.readFilterFile(new File(filterDirectory + filter + FILTER_FILE_SUFFIX));
	}
	
	public void setFilterDirectory(String filterDirectory) {
		this.filterDirectory = filterDirectory;
	}

}
