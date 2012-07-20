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
package de.slackspace.wfail2ban.filter.impl;

import java.util.HashSet;
import java.util.Set;

import de.slackspace.wfail2ban.filter.FilterResult;

public class DefaultFilterResult implements FilterResult {

	private String filterName;
	private Set<String> ipSet = new HashSet<String>();
	
	public DefaultFilterResult(String name) {
		this.filterName = name;
	}
	
	@Override
	public Set<String> getIpsToBlock() {
		return ipSet;
	}

	@Override
	public void addIpToBlock(String ip) {
		ipSet.add(ip);
	}

	@Override
	public boolean isEmpty() {
		if(ipSet.size() > 0) {
			return false;
		}
		
		return true;
	}

	@Override
	public String toString() {
		return "DefaultFilterResult [ipSet=" + ipSet + "]";
	}

	@Override
	public String getFilterName() {
		return filterName;
	}

	@Override
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	
}
