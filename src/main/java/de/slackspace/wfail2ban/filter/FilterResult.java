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
package de.slackspace.wfail2ban.filter;

import java.util.Set;

/**
 * A FilterResult contains IP addresses which should be blocked. 
 * 
 * @author Christian Ternes
 *
 */
public interface FilterResult {

	/**
	 * Retrieves the name of the {@link Filter} which has generated the {@link FilterResult}.
	 * 
	 * @return the filter name
	 */
	public String getFilterName();
	
	/**
	 * Sets the name of the {@link Filter} which has generated the {@link FilterResult}.
	 * 
	 * @param filterName the filter name
	 */
	public void setFilterName(String filterName);
	
	/**
	 * Retrieves a {@link Set} of IP's which should be blocked by a firewall.
	 * 
	 * @return a {@link Set} of IP's which should be blocked
	 */
	public Set<String> getIpsToBlock();
	
	/**
	 * Sets a {@link Set} of IP's which should be blocked by a firewall.
	 * 
	 * @param ip a {@link Set} of IP's which should be blocked
	 */
	public void addIpToBlock(String ip);
	
	/**
	 * Determines if the IP set is empty or not. 
	 * 
	 * @return true if the IP set is empty, false otherwise
	 */
	public boolean isEmpty();
}
