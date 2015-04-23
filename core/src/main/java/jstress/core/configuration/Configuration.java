package jstress.core.configuration;

import java.util.concurrent.ConcurrentHashMap;

import jstress.core.configuration.exceptions.ConfigurationExistsException;
import jstress.core.configuration.exceptions.ConfigurationNotFoundException;
import jstress.core.configuration.exceptions.IndexOutOfBoundsException;
import jstress.core.configuration.exceptions.InvalidTypeException;

public class Configuration extends Value {
	private static ConcurrentHashMap<String, Configuration> _configurations 
		= new ConcurrentHashMap<String, Configuration>();
	
	public static Configuration create(String key, IConfigurationProvider provider) throws ConfigurationExistsException {
		
		if (_configurations.containsKey(key)) {
			throw new ConfigurationExistsException();
		}

		Configuration configuration = new Configuration(provider);
		_configurations.put(key, configuration);
		return configuration;
	}

	public static Configuration getInstance(String key) throws ConfigurationNotFoundException {
		if (!_configurations.containsKey(key)) {
			throw new ConfigurationNotFoundException();
		}
		return _configurations.get(key);
	}

	private Configuration(IConfigurationProvider provider) {
		super(provider);
		this.setLink(this.getProvider().getRootObject());
	}

	@Override
	public Value getProperty(String property) {
		return null;
	}

	@Override
	public String asString() {
		return null;
	}

	@Override
	public Type getType() {
		return null;
	}

	@Override
	public int asInt() {
		return 0;
	}

	@Override
	public Value getArrayElement(int index) throws InvalidTypeException,
			IndexOutOfBoundsException {
		return null;
	}
	
}
