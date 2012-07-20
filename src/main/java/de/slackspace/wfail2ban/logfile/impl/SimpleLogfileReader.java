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
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.slackspace.wfail2ban.filter.Filter;
import de.slackspace.wfail2ban.filter.FilterResultHandler;

/**
 * The SimpleLogfileReader parses a log file only once.
 * 
 * @author Christian Ternes
 *
 */
public class SimpleLogfileReader extends AbstractLogfileReader {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	protected void doParsing(Filter filter, FilterResultHandler callback) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(filter.getLogfilePath()));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				filter.parseLine(line, callback);
			}
		} catch (FileNotFoundException e) {
			logger.error("Log file "+filter.getLogfilePath()+" could not be found. Continuing with next filter...");
			return;
		}
		finally {
			if(scanner != null) {
				scanner.close();
			}
		}
		
	}

}
