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
package de.slackspace.wfail2ban.logfile;

import de.slackspace.wfail2ban.filter.Filter;
import de.slackspace.wfail2ban.filter.FilterResultHandler;

/**
 * The LogfileReader reads a given logfile. The content is matched against a pattern from a given {@link Filter}.
 * How the logfile will be parsed, depends on the concrete implementation of the LogfileReader. 
 * 
 * @author Christian Ternes
 *
 */
public interface LogfileReader {

	/**
	 * Parses a logfile and matches its content against a pattern from the given {@link Filter}. 
	 * The result of the parsing, will be transmitted to the given {@link FilterResultHandler}.
	 * 
	 * @param filter a {@link Filter} which contains several parameters for logfile parsing
	 * @param callback a {@link FilterResultHandler} which will be called after parsing has finished
	 */
	public void parseLogfile(Filter filter, FilterResultHandler callback);
}
