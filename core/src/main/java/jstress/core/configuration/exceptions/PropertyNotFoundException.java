package jstress.core.configuration.exceptions;

public class PropertyNotFoundException extends ConfigurationException {

	private static final long serialVersionUID = 1L;

	public PropertyNotFoundException() {
	}

	public PropertyNotFoundException(String arg0) {
		super(arg0);
	}

	public PropertyNotFoundException(Throwable arg0) {
		super(arg0);
	}

	public PropertyNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public PropertyNotFoundException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}
}
