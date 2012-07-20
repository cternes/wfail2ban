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

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.apache.commons.io.input.TailerListenerAdapter;

import de.slackspace.wfail2ban.filter.Filter;
import de.slackspace.wfail2ban.filter.FilterResultHandler;

public class ContinuousLogfileReader extends AbstractLogfileReader {

	@Override
	protected void doParsing(Filter filter, FilterResultHandler callback) {
		TailerListener listener = new ContinuousTailerListener(filter, callback);
		Tailer tailer = new Tailer(new File(filter.getLogfilePath()), listener);

		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.submit(tailer);
	}
	
	class ContinuousTailerListener extends TailerListenerAdapter {
		
		private Filter filter;
		private FilterResultHandler callback;
		
		public ContinuousTailerListener(Filter filter, FilterResultHandler callback) {
			this.callback = callback;
			this.filter = filter;
		}

		public void handle(String line) {
			filter.parseLine(line, callback);
		}
	}

	
}
