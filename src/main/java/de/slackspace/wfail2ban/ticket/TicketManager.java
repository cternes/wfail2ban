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
package de.slackspace.wfail2ban.ticket;

import java.util.regex.Matcher;

import de.slackspace.wfail2ban.filter.Filter;
import de.slackspace.wfail2ban.filter.FilterResult;

/**
 * The TicketManager takes care of storing all forbidden access request in the form of {@link Ticket}s.
 * Each {@link Filter} has it's own TicketManager. 
 * 
 * @author Christian Ternes
 *
 */
public interface TicketManager {

	/**
	 * Creates a new {@link Ticket} and stores it in the TicketManager.
	 * 
	 * @param line the line from a log file which has a forbidden access request in it
	 * @param matcher the {@link Matcher} with the host IP
	 * @param filter the {@link Filter} who has matched the bad request
	 * @param filterResult the {@link FilterResult} which contains masterdata about the bad request
	 */
	public void createTicket(String line, Matcher matcher, Filter filter, FilterResult filterResult);
}
