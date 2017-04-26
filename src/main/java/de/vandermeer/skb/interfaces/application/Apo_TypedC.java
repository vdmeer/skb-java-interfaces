package de.vandermeer.skb.interfaces.application;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.stringtemplate.v4.ST;

public interface Apo_TypedC<T> extends ApoBaseTyped<T>, ApoBaseC {

	/**
	 * Returns the value of the option.
	 * First the CLI value is tested and if not null it is returned.
	 * Last the default value is returned.
	 * @return application value, null if none found
	 */
	default T getValue(){
		return (this.getCliValue()!=null)?this.getCliValue():this.getDefaultValue();
	}

	@Override
	default void validate() throws IllegalStateException {
		ApoBaseC.super.validate();
		Validate.validState(!StringUtils.isBlank(this.getCliArgumentName()), "Apo: CLI argName cannot be blank");
		Validate.validState(!StringUtils.isBlank(this.getCliArgumentDescription()), "Apo: CLI argDescr cannot be blank");
	}

	/**
	 * Returns the CLI value of the option if any set.
	 * @return CLI value, null if none set
	 */
	T getCliValue();

	/**
	 * Tests if the option is set.
	 * A typed option is set if it has a value that is not `null`.
	 * @return true if set, false otherwise
	 */
	@Override
	default boolean isSet(){
		return this.getValue()!=null;
	}

	/**
	 * Returns the name of the CLI argument.
	 * For instance, if the option is `-h COMMAD` there is a argument `COMMAND` expected from the command line.
	 * The name returned in this example should be `COMMAND`.
	 * @return CLI argument name, must not be blank
	 */
	String getCliArgumentName();

	/**
	 * Returns the flag for an argument being optional or not.
	 * The default implementation is `false`.
	 * @return true if argument is optional, false if not.
	 */
	default boolean cliArgIsOptional(){
		return false;
	}

	/**
	 * Returns a description of the CLI argument.
	 * The CLI argument is the value that is expected with in the command line.
	 * For instance, if the option is `-h COMMAD` there is a argument `COMMAND` expected from the command line.
	 * This description then should explain what `COMMAD` is or what values can be used.
	 * @return CLI argument description, must not be blank
	 */
	String getCliArgumentDescription();

	/**
	 * Sets the CLI value of the option.
	 * @param value the value read from the command line, must not be null (or blank in case of a string)
	 * @throws IllegalStateException if the argument was blank (string) or otherwise problematic
	 */
	void setCliValue(Object value) throws IllegalStateException;

	@Override
	default ST getHelpCli(){
		ST st = ApoBaseC.super.getHelpCli();

		st.add("cliArgName", this.getCliArgumentName());
		st.add("cliArgOptional", this.cliArgIsOptional());
		st.add("cliArgDescr", this.getCliArgumentDescription());
		st.add("defaultValue", this.getDefaultValue());

		return st;
	}
}
