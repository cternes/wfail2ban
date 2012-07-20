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
package de.slackspace.wfail2ban.filter.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.slackspace.wfail2ban.filter.Filter;
import de.slackspace.wfail2ban.filter.FilterResult;
import de.slackspace.wfail2ban.filter.FilterResultHandler;
import de.slackspace.wfail2ban.ticket.TicketManager;
import de.slackspace.wfail2ban.ticket.impl.FailTicketManager;

public class RegexFilter implements Filter {

	private String name;
	private String logfilePath;
	private Pattern failPattern;
	private long findtime = 600; //10 min
	private int maxRetry = 3;
	private TicketManager ticketManager = new FailTicketManager();
	
	public RegexFilter() {
	}
	
	public RegexFilter(String logfilePath, String failPattern) {
		setLogfilePath(logfilePath);
		setFailPattern(failPattern);
	}
	
	@Override
	public String getLogfilePath() {
		return logfilePath;
	}

	@Override
	public void setLogfilePath(String logfilePath) {
		this.logfilePath = logfilePath;
	}

	@Override
	public Pattern getFailPattern() {
		return failPattern;
	}

	@Override
	public void setFailPattern(String pattern) {
		String replacedPattern = pattern.replace("<HOST>", "(?<host>(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3}))");
		failPattern = Pattern.compile(replacedPattern);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void parseLine(String line, FilterResultHandler callback) {
		FilterResult filterResult = new DefaultFilterResult(name);
		
		Matcher matcher = failPattern.matcher(line);
		boolean matches = matcher.matches();
		if(matches) {
			ticketManager.createTicket(line, matcher, this, filterResult);
		}
		
		callback.handleFilterResult(filterResult);
	}

	public void setFindtime(long findtime) {
		this.findtime = findtime;
	}

	public long getFindtime() {
		return findtime;
	}

	public void setMaxRetry(int maxRetry) {
		this.maxRetry = maxRetry;
	}

	public int getMaxRetry() {
		return maxRetry;
	}

	@Override
	public String toString() {
		return "RegexFilter [name=" + name + ", logfilePath=" + logfilePath
				+ ", failPattern=" + failPattern + ", findTime=" + findtime
				+ ", maxRetry=" + maxRetry + "]";
	}
	
}
