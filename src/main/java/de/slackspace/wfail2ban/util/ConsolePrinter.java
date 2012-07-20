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
package de.slackspace.wfail2ban.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A utility class to print to the system console as well as to a logger.
 * 
 * @author Christian Ternes
 *
 */
public final class ConsolePrinter {

	private static final Logger logger = LoggerFactory.getLogger(ConsolePrinter.class);

	/**
	 * Prints a message to the system out stream as well as to a logger with info level. 
	 * 
	 * @param message
	 */
	public static void printMessage(final String message) {
		logger.info(message);
		System.out.println(message); 
	}

	/**
	 * Prints an error message to the system err stream as well as to a logger with error level.
	 * 
	 * @param error
	 */
	public static void printError(final String error) {
		printError(error, null);
	}

	/**
	 * Prints an error message and the given exception to the system err stream as well as to a logger with error level.
	 * The given full exception stack trace is written to the logger.
	 * The exception message will be written intended to the system err stream.  
	 * 
	 * @param error
	 * @param exception
	 */
	public static void printError(final String error, final Exception exception) {
		if(exception != null) {
			logger.error(error, exception);
	    	System.err.println(error);
	    	System.err.println("\t" + exception.getMessage());
		}
		else {
			logger.error(error);
	    	System.err.println(error);
		}
	}
}
