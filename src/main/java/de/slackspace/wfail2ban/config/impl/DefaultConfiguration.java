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
package de.slackspace.wfail2ban.config.impl;

import de.slackspace.wfail2ban.util.NumberUtil;

/**
 * The DefaultConfiguration which holds all global configuration parameters. 
 * 
 * @author Christian Ternes
 *
 */
public class DefaultConfiguration {

	public static final String NAME = "DEFAULT";
	public static final String DELAY_PERIOD = "delayperiod";
	public static final String REFRESH_PERIOD = "refreshperiod";
	public static final String MAX_RETRY = "maxretry";
	public static final String FIND_TIME = "findtime";
	
	private long delayPeriod = 60000; //1 min
	private long refreshPeriod = 60000; //1 min
	private int maxRetry = 3;
	private long findtime = 600; //10 min
	
	public void setDelayPeriod(String delayPeriod) {
		if(NumberUtil.isLong(delayPeriod)) {
			this.delayPeriod = Long.parseLong(delayPeriod) * 1000;
		}
	}
	
	public void setDelayPeriod(long delayPeriod) {
		this.delayPeriod = delayPeriod;
	}
	
	public long getDelayPeriod() {
		return delayPeriod;
	}
	
	public void setRefreshPeriod(String refreshPeriod) {
		if(NumberUtil.isLong(refreshPeriod)) {
			this.refreshPeriod = Long.parseLong(refreshPeriod) * 1000;
		}
	}
	
	public void setRefreshPeriod(long refreshPeriod) {
		this.refreshPeriod = refreshPeriod;
	}
	
	public long getRefreshPeriod() {
		return refreshPeriod;
	}
	
	public void setMaxRetry(String maxRetry) {
		if(NumberUtil.isInteger(maxRetry)) {
			this.maxRetry = Integer.parseInt(maxRetry);
		}
	}

	public int getMaxRetry() {
		return maxRetry;
	}
	
	public void setFindtime(String findtime) {
		if(NumberUtil.isLong(findtime)) {
			this.findtime = Long.parseLong(findtime);
		}
	}

	public long getFindtime() {
		return findtime;
	}
	
}
