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
package de.slackspace.wfail2ban.logfile.impl;


public class DefaultDateTimePattern extends AbstractDateTimePattern {

	private String regex;
	private String pattern;
	
	public DefaultDateTimePattern matches(String regex) {
		this.regex = regex;
		return this;
	}
	
	public DefaultDateTimePattern toDate(String pattern) {
		this.pattern = pattern;
		return this;
	}
	
	@Override
	protected String getRegex() {
		return regex;
	}

	@Override
	protected String getPattern() {
		return pattern;
	}

}
