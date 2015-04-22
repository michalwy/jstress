package jstress.core.configuration.exceptions;

public class ConfigurationException extends Exception {

	private static final long serialVersionUID = 1L;

	public ConfigurationException() {
	}

	public ConfigurationException(String arg0) {
		super(arg0);
	}

	public ConfigurationException(Throwable arg0) {
		super(arg0);
	}

	public ConfigurationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ConfigurationException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
}
