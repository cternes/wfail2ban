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
package de.slackspace.wfail2ban.ticket.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.slackspace.wfail2ban.filter.Filter;
import de.slackspace.wfail2ban.filter.FilterResult;
import de.slackspace.wfail2ban.logfile.impl.DateTimeMatcher;
import de.slackspace.wfail2ban.ticket.Ticket;
import de.slackspace.wfail2ban.ticket.TicketManager;
import de.slackspace.wfail2ban.util.ConsolePrinter;
import de.slackspace.wfail2ban.util.DateTimeCalculator;

public class FailTicketManager implements TicketManager {

	protected static final String HOST_GROUPNAME = "host";
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private DateTimeMatcher datetimeMatcher = new DateTimeMatcher();
	private Map<String, Ticket> ticketMap = new HashMap<String, Ticket>();
	
	@Override
	public void createTicket(String line, Matcher matcher, Filter filter, FilterResult filterResult) {
		String ip = extractIpFromLine(matcher);
		Calendar accessTime = extractTimeFromLine(line);
		
		if(accessTime != null) {
			retrieveOrCreateTicket(ip, accessTime, filter, filterResult);
		}
	}
	
	private void retrieveOrCreateTicket(String ip, Calendar accessTime, Filter filter, FilterResult filterResult) {
		if(logger.isDebugEnabled()) {
			logger.debug("Found match. IP "+ip+" at time "+accessTime.getTime().toString());
		}
		
		Ticket ticket = ticketMap.get(ip);
		if(ticket == null) { //create new ticket
			ticket = new FailTicket(ip, accessTime);
			ticketMap.put(ip, ticket);
			
			if(logger.isDebugEnabled()) {
				logger.debug("Creating ticket for ip "+ip);
			}
		}
		else { //use existing ticket
			increaseRetryCount(ticket, accessTime, filter.getFindTime());
		}
		
		checkRetryCount(filter, filterResult, ticket);
	}

	private void checkRetryCount(Filter filter, FilterResult filterResult, Ticket ticket) {
		//ban if necessary
		if(ticket.getRetryCount() >= filter.getMaxRetry()) {
			ticket.ban();
			filterResult.addIpToBlock(ticket.getIp());
			
			ConsolePrinter.printMessage("IP "+ticket.getIp()+" exceeded maxretry["+filter.getMaxRetry()+"]. Added to ban list.");
		}
	}
	
	private void increaseRetryCount(Ticket ticket, Calendar accessTime, long findTime) {
		long diffInSeconds = DateTimeCalculator.getDiffInSeconds(accessTime, ticket.getLastAccessTime());
		if(diffInSeconds <= findTime) { //access is in time frame
			ticket.incrementRetryCount();
			
			if(logger.isDebugEnabled()) {
				logger.debug("Last access from "+ticket.getIp()+" is within find time ["+findTime+"s]. Increasing retry count to "+ticket.getRetryCount());
			}
		}
		else { //access is not in time frame
			ticket.resetRetryCount();
			
			if(logger.isDebugEnabled()) {
				logger.debug("Last access from "+ticket.getIp()+" is NOT within find time ["+findTime+"s]. Resetting retry count to 1");
			}
		}
		
		ticket.setLastAccessTime(accessTime);
	}

	private Calendar extractTimeFromLine(String line) {
		Calendar calendar = datetimeMatcher.matchDateTime(line);
		if(calendar == null) {
			logger.warn("Unable to create ticket, because datetime could not be found in line ["+line+"].");
		}
		return calendar;
	}

	private String extractIpFromLine(Matcher matcher) {
		return matcher.group(HOST_GROUPNAME);
	}
	
}
