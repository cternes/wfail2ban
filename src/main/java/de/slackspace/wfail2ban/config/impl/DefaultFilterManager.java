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
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.slackspace.wfail2ban.config.FilterManager;
import de.slackspace.wfail2ban.filter.Filter;
import de.slackspace.wfail2ban.filter.impl.RegexFilter;
import de.slackspace.wfail2ban.util.NumberUtil;

public class DefaultFilterManager implements FilterManager {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public Filter readFilterFile(File filterFile) {
		return readFilterFile(filterFile, null);
	}
	
	@Override
	public Filter readFilterFile(File filterFile, DefaultConfiguration config) {
		if(filterFile == null) {
			throw new IllegalArgumentException("FilterFile must not be null");
		}
		if(!filterFile.exists() || !filterFile.canRead()) {
			throw new IllegalArgumentException("FilterFile "+filterFile+" does not exists or cannot be read!");
		}
		
		Filter filter = new RegexFilter();
		Scanner scanner = null;
		try {
			scanner = new Scanner(filterFile);
			while(scanner.hasNext()) {
				String line = scanner.nextLine();
				extractRegEx(line, filter);
				extractFindtime(line, filter, config);
				extractMaxRetry(line, filter, config);
			}
		} catch (FileNotFoundException e) {
			logger.error("Filter file "+filterFile+" could not be read. Error was: ", e);
		}
		finally {
			scanner.close();
		}
		
		return filter;
	}

	private void extractMaxRetry(String line, Filter filter, DefaultConfiguration config) {
		Pattern pattern = Pattern.compile("maxretry.*=(.*)");
		Matcher matcher = pattern.matcher(line);
		boolean matches = matcher.matches();
		if(matches) {
			String value = matcher.group(1).trim();
			if(NumberUtil.isInteger(value)) {
				filter.setMaxRetry(Integer.parseInt(value));
			}
		}
		else {
			if(config != null) {
				filter.setMaxRetry(config.getMaxRetry());
			}
		}
	}

	private void extractFindtime(String line, Filter filter, DefaultConfiguration config) {
		Pattern pattern = Pattern.compile("findtime.*=(.*)");
		Matcher matcher = pattern.matcher(line);
		boolean matches = matcher.matches();
		if(matches) {
			String value = matcher.group(1).trim();
			if(NumberUtil.isLong(value)) {
				filter.setFindTime(Long.parseLong(value));
			}
		}
		else {
			if(config != null) {
				filter.setFindTime(config.getFindtime());
			}
		}
	}

	private void extractRegEx(String line, Filter filter) {
		Pattern pattern = Pattern.compile("failregex.*=(.*)");
		Matcher matcher = pattern.matcher(line);
		boolean matches = matcher.matches();
		if(matches) {
			String value = matcher.group(1).trim();
			filter.setFailPattern(value);
		}
	}

}
