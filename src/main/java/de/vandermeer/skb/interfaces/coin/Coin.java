/* Copyright 2016 Sven van der Meer <vdmeer.sven@mykolab.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.vandermeer.skb.interfaces.coin;

/**
 * Base interface for a coin.
 *
 * @author     Sven van der Meer &lt;vdmeer.sven@mykolab.com&gt;
 * @version    v0.0.2 build 170502 (02-May-17) for Java 1.8
 * @since      v0.0.1
 */
public interface Coin<R> {

	/**
	 * Get the actual return object of the method using this coin.
	 * @return actual return object, null very likely means this is a tails coin
	 */
	R getReturn();

	/**
	 * Tests if the coin is heads (positive return).
	 * @return true if it is heads, false otherwise
	 */
	default boolean isHeads(){
		return false;
	}

	/**
	 * Tests if the coin is heads (positive return).
	 * @return true if it is heads, false otherwise
	 */
	default boolean isSuccess(){
		return this.isHeads();
	}

	/**
	 * Tests if the coin is tails (negative return).
	 * @return true if it is tails, false otherwise
	 */
	default boolean isTails(){
		return false;
	}

	/**
	 * Tests if the coin is tails (positive return).
	 * @return true if it is tails, false otherwise
	 */
	default boolean isError(){
		return this.isTails();
	}

	/**
	 * Tests if the coin reports errors.
	 * @return true if coin reports errors, false otherwise
	 */
	default boolean reportsErrors(){
		return false;
	}

	/**
	 * Tests if the coin has errors to report.
	 * @return true if it has errors to report, false otherwise
	 */
	default boolean hasErrorReports(){
		return false;
	}

	/**
	 * Tests if the coin reports information.
	 * @return true if coin reports information, false otherwise
	 */
	default boolean reportsInfo(){
		return false;
	}

	/**
	 * Tests if the coin has information to report.
	 * @return true if it has information to report, false otherwise
	 */
	default boolean hasInfoReports(){
		return false;
	}

	/**
	 * Tests if the coin reports warnings.
	 * @return true if coin reports warnings, false otherwise
	 */
	default boolean reportsWarnings(){
		return false;
	}

	/**
	 * Tests if the coin has warnings to report.
	 * @return true if it has warnings to report, false otherwise
	 */
	default boolean hasWarningReports(){
		return false;
	}
}
