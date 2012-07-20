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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.slackspace.wfail2ban.firewall.FirewallManager;
import de.slackspace.wfail2ban.util.ConsolePrinter;

public class DefaultFirewallManager implements FirewallManager {

	private static final String RULE_NAME = "wfail2ban-";
	private static final String RULE_SUFFIX = "#";

	private Logger logger = LoggerFactory.getLogger(getClass());
	private FirewallPreprocessor preprocessor = new FirewallPreprocessor();
	
	public void addFirewallRules(String filterName, Set<String> ipSet) {
		if(ipSet == null || ipSet.size() == 0){
			logger.debug("No IP's found to block. Continuing...");
			return; //aborting operation
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("Updating firewall rules...");
		}
		
		deleteExistingRules(filterName);
		
		List<String> preparedIpSets = preprocessor.prepareIpSet(ipSet);
		int counter = 0;
		for (String preparedIpSet : preparedIpSets) {
			addDefaultWindowsFirewallRule(counter, filterName, preparedIpSet);
			counter++;
		}
		
		if(logger.isDebugEnabled()) {
			logger.debug("Firewall update finished.");
		}
	}
	
	private void deleteExistingRules(String filterName) {
		int counter = 0;
		
		while(checkFirewallRuleExists(counter, filterName)) {
			deleteFirewallRule(counter, filterName);
			counter++;
		}
	}

	private void deleteFirewallRule(int ruleNumber, String filterName) {
		CommandLine cmdLine = new CommandLine("cmd.exe");
		cmdLine.addArgument("/C");
		cmdLine.addArgument(System.getenv("WINDIR") + "\\system32\\netsh.exe");
		cmdLine.addArgument("advfirewall");
		cmdLine.addArgument("firewall");
		cmdLine.addArgument("delete");
		cmdLine.addArgument("rule");
		cmdLine.addArgument(createFinalRuleName(ruleNumber, filterName));
		DefaultExecutor executor = new DefaultExecutor();
		ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
		executor.setWatchdog(watchdog);
		try {
			executor.execute(cmdLine);
			if(logger.isDebugEnabled()) {
				logger.debug("Deleted firewall rule "+ createFinalRuleName(ruleNumber, filterName));
			}
		} catch (ExecuteException e) {
			logger.error("Could not delete firewall rule. Error was: ", e);
		} catch (IOException e) {
			logger.error("Could not delete firewall rule. Error was: ", e);
		}
	}

	private String createFinalRuleName(int ruleNumber, String filterName) {
		return RULE_NAME + filterName + RULE_SUFFIX + ruleNumber;
	}

	private boolean checkFirewallRuleExists(int ruleNumber, String filterName) {
		CommandLine cmdLine = new CommandLine("cmd.exe");
		cmdLine.addArgument("/C");
		cmdLine.addArgument(System.getenv("WINDIR") + "\\system32\\netsh.exe");
		cmdLine.addArgument("advfirewall");
		cmdLine.addArgument("firewall");
		cmdLine.addArgument("show");
		cmdLine.addArgument("rule");
		cmdLine.addArgument(createFinalRuleName(ruleNumber, filterName));
		
		DefaultExecutor executor = new DefaultExecutor();
		ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
		executor.setWatchdog(watchdog);
		try {
			executor.execute(cmdLine);
			return true;
		} catch (ExecuteException e) {
			//rule does not exist
			return false;
		} catch (IOException e) {
			logger.error("Could not list firewall rule. Error was: ", e);
		}
		return false;
	}

	private void addDefaultWindowsFirewallRule(int ruleNumber, String filterName, String ipList) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("name", createFinalRuleName(ruleNumber, filterName));
		map.put("direction", "in");
		map.put("ipList", ipList);
		CommandLine cmdLine = new CommandLine("cmd.exe");
		cmdLine.addArgument("/C");
		cmdLine.addArgument(System.getenv("WINDIR") + "\\system32\\netsh.exe");
		cmdLine.addArgument("advfirewall");
		cmdLine.addArgument("firewall");
		cmdLine.addArgument("add");
		cmdLine.addArgument("rule");
		cmdLine.addArgument("name=${name}");
		cmdLine.addArgument("dir=${direction}");
		cmdLine.addArgument("action=block");
		cmdLine.addArgument("localip=any");
		cmdLine.addArgument("remoteip=${ipList}");
		cmdLine.addArgument("description=This is a generated rule from wfail2ban. Do not edit!");
		cmdLine.addArgument("profile=any");
		cmdLine.addArgument("interfacetype=any");
		cmdLine.setSubstitutionMap(map);
		DefaultExecutor executor = new DefaultExecutor();
		ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
		executor.setWatchdog(watchdog);
		try {
			executor.execute(cmdLine);
			if(logger.isDebugEnabled()) {
				logger.debug("Added firewall rule " + createFinalRuleName(ruleNumber, filterName));
			}
		} catch (ExecuteException e) {
			ConsolePrinter.printError("Could not create firewall rule. Continuing with next one...");
			logger.error("", e);
		} catch (IOException e) {
			logger.error("Could not create firewall rule. Error was: ", e);
		}
	}
}
