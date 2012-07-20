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

import java.util.Calendar;

/**
 * A ticket records forbidden access attempts. It contains the masterdata information of the access request.
 * 
 * @author Christian Ternes
 *
 */
public interface Ticket {

	/**
	 * Retrieves the IP of the ticket.
	 * 
	 * @return
	 */
	public String getIp();
	
	/**
	 * Retrieves the current retry count.
	 * 
	 * @return
	 */
	public int getRetryCount();
	
	/**
	 * Sets the retry count to 1. 
	 */
	public void resetRetryCount();
	
	/**
	 * Increases the retry count by 1. 
	 */
	public void incrementRetryCount();

	/**
	 * Retrieves the datetime of the previous access request from the same IP. 
	 * 
	 * @return
	 */
	public Calendar getLastAccessTime();
	
	/**
	 * Sets the datetime of the previous access request from the same IP.
	 * 
	 * @param accessTime
	 */
	public void setLastAccessTime(Calendar accessTime);
	
	/**
	 * Adds the IP of the ticket to the ban list.
	 */
	public void ban();
	
	/**
	 * Determines if the IP of the ticket is on the ban list.
	 * 
	 * @return
	 */
	public boolean isBanned();
}
