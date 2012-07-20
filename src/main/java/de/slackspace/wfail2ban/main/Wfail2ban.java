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
package de.slackspace.wfail2ban.main;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import de.slackspace.wfail2ban.config.JailManager;
import de.slackspace.wfail2ban.config.impl.DefaultConfiguration;
import de.slackspace.wfail2ban.config.impl.DefaultJailManager;
import de.slackspace.wfail2ban.filter.Filter;
import de.slackspace.wfail2ban.filter.FilterResult;
import de.slackspace.wfail2ban.filter.FilterResultHandler;
import de.slackspace.wfail2ban.firewall.FirewallMonitor;
import de.slackspace.wfail2ban.firewall.impl.FirewallMonitorImpl;
import de.slackspace.wfail2ban.logfile.LogfileReader;
import de.slackspace.wfail2ban.logfile.impl.ContinuousLogfileReader;
import de.slackspace.wfail2ban.logfile.impl.SimpleLogfileReader;
import de.slackspace.wfail2ban.util.ConsolePrinter;

public class Wfail2ban implements FilterResultHandler {

	private static final long FIREWALL_REFRESH_PERIOD_TEST = 5000L; //5 sec
	
	private DefaultConfiguration defaultConfig;
	private LogfileReader logfileReader;
	private List<Filter> activeFilterList = new ArrayList<Filter>();
	private boolean isTestMode = false;
	private FirewallMonitor firewallMonitor = new FirewallMonitorImpl();
	private Map<String, Set<String>> blockedIpsPerFilter = new HashMap<String, Set<String>>();
	
	public Wfail2ban(boolean isTestModeEnabled) {
		isTestMode = isTestModeEnabled;
		
		if(isTestModeEnabled) {
			ConsolePrinter.printMessage("---Test mode enabled---");
			 logfileReader = new SimpleLogfileReader();
		}
		else {
			 logfileReader = new ContinuousLogfileReader();
		}
	}

	public void readConfiguration() {
		readConfiguration(JailManager.JAIL_CONFIG_FILE);
	}
	
	public void readConfiguration(String configFile) {
		try {
			JailManager jailManager = new DefaultJailManager(new File(configFile));
			defaultConfig = jailManager.readDefaultConfig();
			activeFilterList = jailManager.readJails(defaultConfig);
		}
		catch(IllegalArgumentException e) {
			ConsolePrinter.printError("An error occurred while reading configuration file: " + JailManager.JAIL_CONFIG_FILE, e);
			ConsolePrinter.printError("Please provide a valid configuration file!");
			System.exit(-1);
		}
	}
	
	public void startLogfileMonitoring() {
		ConsolePrinter.printMessage("Initialization finished.");
		ConsolePrinter.printMessage("Start monitoring...");
		
		for (Filter filter: activeFilterList) {
			logfileReader.parseLogfile(filter, this);
		}
	}
	
	public void startFirewallMonitoring() {
		ConsolePrinter.printMessage("Start firewall monitoring...");
		
		long firewallRefreshPeriod = 60000L;
		long firewallDelayPeriod = 60000L;
		if(defaultConfig != null) {
			firewallRefreshPeriod = defaultConfig.getRefreshPeriod();
			firewallDelayPeriod = defaultConfig.getDelayPeriod();
		}
		
		if(isTestMode) {
			firewallRefreshPeriod = FIREWALL_REFRESH_PERIOD_TEST;
			firewallDelayPeriod = FIREWALL_REFRESH_PERIOD_TEST;
		}
		ConsolePrinter.printMessage("Firewall delay period="+(firewallDelayPeriod/1000)+"s");
		ConsolePrinter.printMessage("Firewall refresh period="+(firewallRefreshPeriod/1000)+"s");
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				//TODO: only invoke firewall if blockedIpsPerFilter has changes
				firewallMonitor.invokeFirewall(isTestMode, blockedIpsPerFilter);
			}
		}, firewallDelayPeriod, firewallRefreshPeriod);
	}

	public void handleFilterResult(FilterResult filterResult) {
		if(!filterResult.isEmpty()) {
			mergeFilterResults(filterResult);
		}
	}
	
	private void mergeFilterResults(FilterResult filterResult) {
		Set<String> foundSet = blockedIpsPerFilter.get(filterResult.getFilterName());
		if(foundSet == null) {
			blockedIpsPerFilter.put(filterResult.getFilterName(), filterResult.getIpsToBlock());	
		}
		else {
			foundSet.addAll(filterResult.getIpsToBlock());
		}
	}
}
