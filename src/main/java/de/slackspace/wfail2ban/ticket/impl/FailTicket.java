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

import de.slackspace.wfail2ban.ticket.Ticket;

public class FailTicket implements Ticket {

	private String ip;
	private Calendar lastAccessTime;
	private int retryCount = 1;
	private boolean isBanned = false;
	
	public FailTicket(String ip, Calendar lastAccessTime) {
		this.ip = ip;
		this.lastAccessTime = lastAccessTime;
	}

	public String getIp() {
		return ip;
	}
	
	public int getRetryCount() {
		return retryCount;
	}
	
	public void incrementRetryCount() {
		retryCount++;
	}
	
	public void resetRetryCount() {
		retryCount = 1;
	}
	
	public Calendar getLastAccessTime() {
		return lastAccessTime;
	}
	
	public void setLastAccessTime(Calendar accessTime) {
		this.lastAccessTime = accessTime;
	}

	public boolean isBanned() {
		return isBanned;
	}

	@Override
	public void ban() {
		isBanned = true;
	}

}
