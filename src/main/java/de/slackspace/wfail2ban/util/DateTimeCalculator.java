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
package de.slackspace.wfail2ban.util;

import java.util.Calendar;

/**
 * A utility class to do calculations with calendar and date objects. 
 * 
 * @author Christian Ternes
 *
 */
public final class DateTimeCalculator {

	/**
	 * Retrieves the difference in seconds between two given calendar objects.
	 * 
	 * @param datetime1 the first calendar object 
	 * @param datetime2 the second calendar object
	 * @return the difference in seconds, or -1 if the given arguments are null
	 */
	public static long getDiffInSeconds(Calendar datetime1, Calendar datetime2) {
		if(datetime1 == null || datetime2 == null) {
			return -1;
		}
		
		long diff = datetime2.getTimeInMillis() - datetime1.getTimeInMillis();
		
		//convert to seconds
		long diffSeconds = diff / 1000;
		long diffSecondsAbs = Math.abs(diffSeconds);
		
		return diffSecondsAbs;
	}
}
