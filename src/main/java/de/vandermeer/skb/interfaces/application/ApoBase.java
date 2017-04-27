package de.vandermeer.skb.interfaces.application;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.stringtemplate.v4.ST;

import de.vandermeer.skb.interfaces.categories.CategoryIs;
import de.vandermeer.skb.interfaces.categories.has.HasDescription;

public interface ApoBase extends CategoryIs, HasDescription {

	/**
	 * Returns help information for the option.
	 * The information contains all possible settings.
	 * This might include CLI, property, environment, and other settings.
	 * @return help information, must not be null
	 */
	ST getHelp();

	/**
	 * Returns a long description of the option.
	 * For more complex options, the description should include use case and other information.
	 * @return long description for the option, blank if not set
	 */
	String getLongDescription();

	/**
	 * Tests if the option is set.
	 * @return true if set, false otherwise
	 */
	boolean isSet();

	/**
	 * Sets the long description.
	 * @param description new description, ignored if null or blank
	 */
	void setLongDescription(ST description);

	/**
	 * Sets the long description.
	 * @param description new description, ignored if null or blank
	 */
	void setLongDescription(String description);

	/**
	 * Validates the option.
	 * @throws IllegalStateException for any validation error
	 */
	default void validate() throws IllegalStateException {
		Validate.validState(!StringUtils.isBlank(this.getDescription()), "Apo: description cannot be blank");
	}
}
