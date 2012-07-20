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
package de.slackspace.wfail2ban.config;

import java.util.List;

import de.slackspace.wfail2ban.config.impl.DefaultConfiguration;
import de.slackspace.wfail2ban.filter.Filter;

/**
 * The JailManager reads the global configuration file.
 * 
 * @author Christian Ternes
 *
 */
public interface JailManager {

	public static final String JAIL_CONFIG_FILE = "jail.conf";
	
	/**
	 * Reads the global configuration.
	 * 
	 * @return a {@link DefaultConfiguration}
	 */
	public DefaultConfiguration readDefaultConfig();
	
	/**
	 * Reads all enabled {@link Filter}s from the filter directory.
	 * 
	 * @param config the {@link DefaultConfiguration}
	 * @return a list of {@link Filter}s
	 */
	public List<Filter> readJails(DefaultConfiguration config);
	
}
