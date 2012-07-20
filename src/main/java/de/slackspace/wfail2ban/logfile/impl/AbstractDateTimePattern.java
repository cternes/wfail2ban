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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.slackspace.wfail2ban.logfile.DateTimePattern;

public abstract class AbstractDateTimePattern implements DateTimePattern {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private Pattern regexPattern;
	
	@Override
	public Calendar matchDateTime(String line) {
		Matcher matcher = getRegexPattern().matcher(line);
		if(matcher.find()) {
			String dateTime = matcher.group();
			if(logger.isDebugEnabled()) {
				logger.debug("Found datetime ["+dateTime+"] in line: "+line);
			}
			
			Calendar cal = convertToDate(dateTime);
			return cal;
		}
			
		return null;
	}
	
	private Calendar convertToDate(String dateTime) {
		try {
			Date date = new SimpleDateFormat(getPattern(), Locale.ENGLISH).parse(dateTime);
			
			if(logger.isDebugEnabled()) {
				logger.debug("Parsed datetime into date ["+date.toString()+"] with pattern ["+getPattern()+"]");
			}
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			replaceMissingYearWithCurrent(cal);
			
			return cal;
		}
		catch(IllegalArgumentException e) {
			logger.warn("Could not parse date. The given datetime pattern ["+getPattern()+"] is invalid.");
		}
		catch (ParseException e) {
			logger.warn("Found datetime ["+dateTime+"] in line with regex ["+getRegex()+"] but could not be parsed with pattern ["+getPattern()+"].");
		}
		return null;
	}
	
	private void replaceMissingYearWithCurrent(Calendar cal) {
		if(cal.get(Calendar.YEAR) == 1970) {
			cal.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		}
	}

	private Pattern getRegexPattern() {
		if(regexPattern == null) {
			regexPattern = Pattern.compile(getRegex());
		}
		return regexPattern;
	}
	
	protected abstract String getRegex();
	
	protected abstract String getPattern();
	
}
