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
package de.slackspace.wfail2ban.logfile.impl;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.slackspace.wfail2ban.filter.Filter;
import de.slackspace.wfail2ban.filter.FilterResultHandler;
import de.slackspace.wfail2ban.logfile.LogfileReader;

public abstract class AbstractLogfileReader implements LogfileReader {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public void parseLogfile(Filter filter, FilterResultHandler callback) {
		if(filter == null) {
			throw new IllegalArgumentException("Filter must not be null");
		}
		if(filter.getLogfilePath() == null || filter.getLogfilePath().isEmpty()) {
			throw new IllegalArgumentException("A logfile must be set");
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("Looking for potential matches in file " + filter.getLogfilePath() + "...");
		}

		File logFile = new File(filter.getLogfilePath());
		if(!logFile.canRead()) {
			logger.warn("The specified log file " + logFile + " does not exist or cannot be read! Continuing with next filter...");
			return;
		}
		
		doParsing(filter, callback);
	}
	
	protected abstract void doParsing(Filter filter, FilterResultHandler callback);
}
