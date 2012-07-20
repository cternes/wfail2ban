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
package de.slackspace.wfail2ban.firewall.impl;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.slackspace.wfail2ban.firewall.FirewallManager;
import de.slackspace.wfail2ban.firewall.FirewallMonitor;
import de.slackspace.wfail2ban.util.ConsolePrinter;

public class FirewallMonitorImpl implements FirewallMonitor {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private FirewallManager firewallManager = new DefaultFirewallManager();
	
	public void invokeFirewall(boolean isTestMode, Map<String, Set<String>> blockedIpsPerFilter) {
		logger.info("Invoking firewall...");
		
		for (String filterName : blockedIpsPerFilter.keySet()) {
			Set<String> ipSet = blockedIpsPerFilter.get(filterName);
			if(isTestMode) {
				listIps(filterName, ipSet);
			}
			else { //block ips if not in test mode
				blockIps(filterName, ipSet);
			}
		}
		
		if(isTestMode) {
			System.exit(0); //exiting after first method call in test mode
		}
	}
	
	private void blockIps(String filterName, Set<String> blockedIpSet) {
		firewallManager.addFirewallRules(filterName, blockedIpSet);
	}
	
	private void listIps(String filterName, Set<String> blockedIpSet) {
		ConsolePrinter.printMessage("");
		ConsolePrinter.printMessage("---------FILTER "+filterName+"----------------");
		ConsolePrinter.printMessage("Number of IP's found: "+blockedIpSet.size());
		ConsolePrinter.printMessage("List of IP's: ");
		for (String ip : blockedIpSet) {
			ConsolePrinter.printMessage(ip);
		}
	}

}
