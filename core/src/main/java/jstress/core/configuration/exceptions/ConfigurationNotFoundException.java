package jstress.core.configuration.exceptions;

public class ConfigurationNotFoundException extends ConfigurationException {

	private static final long serialVersionUID = 1L;

	public ConfigurationNotFoundException() {
	}

	public ConfigurationNotFoundException(String arg0) {
		super(arg0);
	}

	public ConfigurationNotFoundException(Throwable arg0) {
		super(arg0);
	}

	public ConfigurationNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ConfigurationNotFoundException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
}
