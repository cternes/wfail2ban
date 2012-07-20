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

/**
 * A utility class to provide helper methods for string conversion.
 * 
 * @author Christian Ternes
 *
 */
public final class NumberUtil {

	/**
	 * Checks if a given string is of type long.
	 * 
	 * @param value
	 * @return true if string is of type long, false otherwise
	 */
	public static boolean isLong(String value) {
		try {
			Long.parseLong(value);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Checks if a given string is of type integer.
	 * 
	 * @param value
	 * @return true if string is of type integer, false otherwise
	 */
	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		}
		catch(NumberFormatException e) {
			return false;
		}
	}
	
	/**
	 * Converts a given string into type integer.
	 * If string is not an integer, -1 will be returned.
	 * 
	 * @param value
	 * @return the string value as integer or -1 if cannot be converted
	 */
	public static int toInteger(String value) {
		if(isInteger(value)) {
			return Integer.parseInt(value);
		}
		return -1;
	}
	
	/**
	 * Converts a given string into type long.
	 * If string is not a long, -1 will be returned.
	 * 
	 * @param value
	 * @return the string value as long or -1 if cannot be converted
	 */
	public static long toLong(String value) {
		if(isLong(value)) {
			return Long.parseLong(value);
		}
		return -1;
	}
}
