package de.vandermeer.skb.interfaces.application;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

public interface ApoBaseP extends ApoBase {

	/**
	 * Tests if the option was present in properties.
	 * @return true if it was present, false otherwise
	 */
	boolean inProperties();

	/**
	 * Returns the property key of the option.
	 * @return property key, must not be blank
	 */
	String getPropertyKey();

	@Override
	default void validate() throws IllegalStateException {
		ApoBase.super.validate();
		Validate.validState(!StringUtils.isBlank(this.getPropertyKey()), "Apo: propertyKey must have a value");
	}

	@Override
	default ST getHelp(){
		return this.getHelpProperty();
	}

	/**
	 * Returns an ST with a help information for the option.
	 * @return ST with help information, must not be null
	 */
	default ST getHelpProperty(){
		STGroupFile stg = new STGroupFile("de/vandermeer/skb/interfaces/application/option-help.stg");
		ST st = stg.getInstanceOf("optionHelp");

		st.add("propertyKey", this.getPropertyKey());
		st.add("shortDescr", this.getDescription());
		st.add("longDescr", this.getLongDescription());

		return st;
	}
}
