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
package de.slackspace.wfail2ban.firewall;

import java.util.Set;

/**
 * The FirewallManager takes care about the firewall rules.
 * The manager can create new rules if necessary, prepares the given IP's and manages the rule names.
 * 
 * @author Christian Ternes
 *
 */
public interface FirewallManager {

	/**
	 * Adds new firewall rules that will block the given {@link Set} of IP's.
	 * 
	 * @param filterName the filter who created the IP set
	 * @param ipSet the {@link Set} of IP's which should be blocked
	 */
	public void addFirewallRules(String filterName, Set<String> ipSet);
}
