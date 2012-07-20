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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.slackspace.wfail2ban.logfile.DateTimePattern;

public class DateTimeMatcher {

	private List<DateTimePattern> patternList = new ArrayList<DateTimePattern>() {{
		//order does matter here, list most specific on top
		DefaultDateTimePattern pattern = new DefaultDateTimePattern().matches("\\S{3} \\S{3}\\s{1,2}\\d{1,2} \\d{2}:\\d{2}:\\d{2} \\d{4}").toDate("EEE MMM dd HH:mm:ss yyyy");
		add(pattern);
		
		pattern = new DefaultDateTimePattern().matches("\\S{3} \\S{3}\\s{1,2}\\d{1,2} \\d{2}:\\d{2}:\\d{2}").toDate("EEE MMM dd HH:mm:ss");
		add(pattern);
		
		pattern = new DefaultDateTimePattern().matches("\\S{3}\\s{1,2}\\d{1,2} \\d{2}:\\d{2}:\\d{2}").toDate("MMM dd HH:mm:ss");
		add(pattern);
		
		pattern = new DefaultDateTimePattern().matches("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}").toDate("yyyy/MM/dd HH:mm:ss");
		add(pattern);
		
		pattern = new DefaultDateTimePattern().matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}").toDate("dd/MM/yyyy HH:mm:ss");
		add(pattern);
		
		pattern = new DefaultDateTimePattern().matches("\\d{2}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}").toDate("dd/MM/yy HH:mm:ss");
		add(pattern);
		
		pattern = new DefaultDateTimePattern().matches("\\d{2}/\\S{3}/\\d{4}:\\d{2}:\\d{2}:\\d{2}").toDate("dd/MMM/yyyy:HH:mm:ss");
		add(pattern);
		
		pattern = new DefaultDateTimePattern().matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}").toDate("yyyy-MM-dd HH:mm:ss");
		add(pattern);
		
		pattern = new DefaultDateTimePattern().matches("\\d{4}.\\d{2}.\\d{2} \\d{2}:\\d{2}:\\d{2}").toDate("yyyy.MM.dd HH:mm:ss");
		add(pattern);
		
		pattern = new DefaultDateTimePattern().matches("\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}").toDate("dd-M-yyyy HH:mm:ss");
		add(pattern);
		
		pattern = new DefaultDateTimePattern().matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}").toDate("yyyy-MM-dd'T'HH:mm:ss");
		add(pattern);
	}};
	
	public Calendar matchDateTime(String line) {
		for (DateTimePattern dateTimePattern : patternList) {
			Calendar cal = dateTimePattern.matchDateTime(line);
			if(cal != null) {
				return cal;
			}
		}
		return null;
	}
}
