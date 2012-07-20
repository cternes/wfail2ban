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

import java.io.File;

import de.slackspace.wfail2ban.config.impl.DefaultConfiguration;
import de.slackspace.wfail2ban.filter.Filter;

/**
 * The FilterManager reads the filter files from the filter directory.  
 * 
 * @author Christian Ternes
 *
 */
public interface FilterManager {

	/**
	 * Reads the specified filter file and creates a {@link Filter} from it.
	 * 
	 * @param filterFile
	 * @return a {@link Filter}
	 */
	public Filter readFilterFile(File filterFile);
	
	
	/**
	 * Reads the specified filter file and creates a {@link Filter} from it.
	 * The {@link DefaultConfiguration} is used to set global parameters if not given in filter file.
	 * 
	 * @param filterFile
	 * @param config
	 * @return a {@link Filter}
	 */
	public Filter readFilterFile(File filterFile, DefaultConfiguration config);
}
