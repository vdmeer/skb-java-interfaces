package de.vandermeer.skb.interfaces.application;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

public interface ApoBaseC extends ApoBase {

	/**
	 * Tests if the option was present in a parsed command line.
	 * @return true if it was present, false otherwise
	 */
	boolean inCli();

	/**
	 * Sets the option flag for being in a command line.
	 * @param inCli true if in a command line, false otherwise
	 */
	void setInCLi(boolean inCli);

	/**
	 * Returns the short CLI option.
	 * @return short CLI option, null if none set
	 */
	Character getCliShort();

	/**
	 * Returns the long CLI option.
	 * @return long CLI option, must not be blank
	 */
	String getCliLong();

	@Override
	default void validate() throws IllegalStateException {
		ApoBase.super.validate();
		Validate.validState(!StringUtils.isBlank(this.getCliShortLong()), "Apo: cliShor or cliLong must have a value");
	}

	/**
	 * Returns a CLI option either short or long.
	 * @return CLI option, short if short is not null, long otherwise
	 */
	default String getCliShortLong(){
		if(this.getCliShort()!=null){
			return this.getCliShort().toString();
		}
		return this.getCliLong();
	}

	/**
	 * Tests if the CLI option is required meaning it must be used in the command line.
	 * @return true if required, false otherwise
	 */
	boolean cliIsRequired();

	@Override
	default ST getHelp(){
		return this.getHelpCli();
	}

	/**
	 * Returns an ST with help information for the option.
	 * @return ST with help information, must not be null
	 */
	default ST getHelpCli(){
		STGroupFile stg = new STGroupFile("de/vandermeer/skb/interfaces/application/option-help.stg");
		ST st = stg.getInstanceOf("optionHelp");

		st.add("cliShort", this.getCliShort());
		st.add("cliLong", this.getCliLong());

		if(this.cliIsRequired()){
			st.add("cliRequired", "true");
		}
		st.add("shortDescr", this.getDescription());
		st.add("longDescr", this.getLongDescription());

		return st;
	}
}
