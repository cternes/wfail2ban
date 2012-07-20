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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FirewallPreprocessor {
	
	private int firewallMaxIps = 200; //default firewall capacity

	public FirewallPreprocessor() {
	}
	
	public FirewallPreprocessor(int firewallMaxIps) {
		this.firewallMaxIps = firewallMaxIps;
	}
	
	public List<String> prepareIpSet(Set<String> ipSet) {
		if(ipSet == null) {
			throw new IllegalArgumentException("IpSet must not be null");
		}
		
		List<String> firewallPackages = new ArrayList<String>();  
		int size = ipSet.size();
		if(size > firewallMaxIps) {
			
			int cycles = size / firewallMaxIps;
			//handle odd size
			if((size % firewallMaxIps) != 0) {
				cycles++;
			}
			
			int counter = 0;
			for (int i = 0; i < cycles; i++) {
				Set<String> set = new HashSet<String>();
				Iterator<String> iter = ipSet.iterator();
				int j = counter;
				while(j < counter + firewallMaxIps) {
					if(!iter.hasNext()) {
						break;
					}
					String ip = iter.next();
					set.add(ip);
					iter.remove();
					j++;
				}
				
				String convertedSet = convertSetToString(set);
				firewallPackages.add(convertedSet);
				counter++;
			}
		}
		else {
			String convertedSet = convertSetToString(ipSet);
			firewallPackages.add(convertedSet);
		}
		return firewallPackages;
	}

	private String convertSetToString(Set<String> set) {
		StringBuilder sb = new StringBuilder(set.size());
		boolean isFirst = true;
		for (String ip : set) {
			if(!isFirst) {
				sb.append(",");
			}
			sb.append(ip);
			isFirst = false;
		}
		return sb.toString();
	}
	
}