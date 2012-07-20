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
package de.slackspace.wfail2ban.logfile;

import java.util.Calendar;

public interface DateTimePattern {

	/**
	 * Extracts the datetime from the given log line. 
	 * 
	 * @param line a line from a log file
	 * @return the datetime as {@link Calendar} if found, null otherwise
	 */
	public Calendar matchDateTime(String line);
	
}
