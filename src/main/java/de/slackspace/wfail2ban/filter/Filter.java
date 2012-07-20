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
package de.slackspace.wfail2ban.filter;

import java.util.regex.Pattern;

/**
 * A filter defines several parameters how a logfile will be parsed. 
 * 
 * @author Christian Ternes
 *
 */
public interface Filter {

	public static final String ENABLED = "enabled";
	public static final String FILTER = "filter";
	public static final String LOGPATH = "logpath";
	public static final String MAXRETRY = "maxretry";
	public static final String FINDTIME = "findtime"; 
	
	/**
	 * Retrieves the path to a log file.
	 * 
	 * @return
	 */
	public String getLogfilePath();
	
	/**
	 * Sets the path to a log file.
	 * 
	 * @param logfilePath
	 */
	public void setLogfilePath(String logfilePath);
	
	/**
	 * Retrieves the fail regex pattern. 
	 * 
	 * @return
	 */
	public Pattern getFailPattern();
	
	/**
	 * Sets the fail regex pattern.
	 * 
	 * @param pattern
	 */
	public void setFailPattern(String pattern);
	
	/**
	 * Retrieves the filter name.
	 * 
	 * @return
	 */
	public String getName();
	
	/**
	 * Sets the filter name.
	 * 
	 * @param name
	 */
	public void setName(String name);
	
	/**
	 * Retrieves the findtime.
	 * A host is banned if it has generated "maxretry" during the last "findtime" seconds. 
	 * 
	 * @return
	 */
	public long getFindtime();
	
	/**
	 * Sets the findtime.
	 * A host is banned if it has generated "maxretry" during the last "findtime" seconds. 
	 * 
	 * @param findtime
	 */
	public void setFindtime(long findtime);
	
	/**
	 * Retrieves the maxretry. 
	 * "maxretry" is the number of failures before a host get banned.
	 * 
	 * @param maxRetry
	 */
	public void setMaxRetry(int maxRetry);
	
	/**
	 * Sets the maxretry.
	 * "maxretry" is the number of failures before a host get banned.
	 * 
	 * @return
	 */
	public int getMaxRetry();
	
	/**
	 * Parses a given line from a log file using several parameters like maxretry and findtime.
	 * 
	 * @param line a line from a log file
	 * @param callback a {@link FilterResultHandler} which will be called after parsing has finished
	 */
	public void parseLine(String line, FilterResultHandler callback);
}
