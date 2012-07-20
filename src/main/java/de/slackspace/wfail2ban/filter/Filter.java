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

public interface Filter {

	public static final String ENABLED = "enabled";
	public static final String FILTER = "filter";
	public static final String LOGPATH = "logpath"; 

	public String getLogfilePath();
	public void setLogfilePath(String logfilePath);
	
	public Pattern getFailPattern();
	public void setFailPattern(String pattern);
	
	public String getName();
	public void setName(String name);
	
	public long getFindTime();
	public void setFindTime(long findTime);
	
	public void setMaxRetry(int maxRetry);
	public int getMaxRetry();
	
	public void parseLine(String line, FilterResultHandler callback);
}
