package de.vandermeer.skb.interfaces.application;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

public interface ApoBaseE extends ApoBase {

	/**
	 * Returns the environment key of the option.
	 * @return environment key, must not be blank
	 */
	String getEnvironmentKey();

	@Override
	default ST getHelp(){
		return this.getHelpEnv();
	}

	/**
	 * Returns an ST with a help information for the option.
	 * @return ST with help information, must not be null
	 */
	default ST getHelpEnv(){
		STGroupFile stg = new STGroupFile("de/vandermeer/skb/interfaces/application/option-help.stg");
		ST st = stg.getInstanceOf("optionHelp");

		st.add("envKey", this.getEnvironmentKey());
		st.add("shortDescr", this.getDescription());
		st.add("longDescr", this.getLongDescription());

		return st;
	}


	/**
	 * Tests if the option was present in the environment.
	 * @return true if it was present, false otherwise
	 */
	boolean inEnvironment();

	@Override
	default void validate() throws IllegalStateException {
		ApoBase.super.validate();
		Validate.validState(!StringUtils.isBlank(this.getEnvironmentKey()), "Apo: envKey must have a value");
	}
}
