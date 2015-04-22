package jstress.core.configuration.exceptions;

public class ConfigurationExistsException extends ConfigurationException {

	private static final long serialVersionUID = 1L;

	public ConfigurationExistsException() {
	}

	public ConfigurationExistsException(String arg0) {
		super(arg0);
	}

	public ConfigurationExistsException(Throwable arg0) {
		super(arg0);
	}

	public ConfigurationExistsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ConfigurationExistsException(String arg0, Throwable arg1,
			boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
}
