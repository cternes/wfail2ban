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

import de.slackspace.wfail2ban.util.ConsolePrinter;

public class Main {

	private static final String VERSION = "0.1.1";
	
	public static void main(String[] args) {
		ConsolePrinter.printMessage("--------------------------------------------");
		ConsolePrinter.printMessage("    Starting wfail2ban " + VERSION + "    ");
		ConsolePrinter.printMessage("--------------------------------------------");
		ConsolePrinter.printMessage("");
		
		new Main(args);
	}
	
	public Main(String[] args) {
		Wfail2ban wfail2ban = new Wfail2ban(isTestModeEnabled(args));
		wfail2ban.readConfiguration();
		wfail2ban.startLogfileMonitoring();
		wfail2ban.startFirewallMonitoring();
	}
	
	public boolean isTestModeEnabled(String[] args) {
		for (String arg : args) {
			if(arg.equals("-test")) {
				return true;
			}
		}
		return false;
	}
	
}
