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

import java.util.Map;
import java.util.Set;

/**
 * The FirewallMonitor will invoke the firewall from time to time to update the firewall rules.
 * Since windows does not provide iptables, we have to call the firewall in periods.
 * 
 * @author Christian Ternes
 *
 */
public interface FirewallMonitor {

	/**
	 * Invokes the firewall to update the firewall rules.
	 * 
	 * @param isTestMode true if the application is in test mode
	 * @param blockedIpsPerFilter a {@link Map} of IP's which should be blocked, grouped by the filter who created the IP list
	 */
	public void invokeFirewall(boolean isTestMode, Map<String, Set<String>> blockedIpsPerFilter);
}
