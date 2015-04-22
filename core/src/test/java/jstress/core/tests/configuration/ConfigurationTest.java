package jstress.core.tests.configuration;

import jstress.core.configuration.Configuration;
import jstress.core.configuration.IConfigurationProvider;
import jstress.core.configuration.Value;
import jstress.core.configuration.Value.Type;
import jstress.core.configuration.exceptions.ConfigurationException;
import jstress.core.configuration.exceptions.ConfigurationExistsException;
import jstress.core.configuration.exceptions.ConfigurationNotFoundException;
import jstress.core.configuration.exceptions.InvalidTypeException;
import jstress.core.configuration.exceptions.PropertyNotFoundException;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ConfigurationTest {
	
	private Configuration _configuration;
	private IConfigurationProvider _provider;
	
	private class TestValue extends Value {

		public TestValue(IConfigurationProvider provider) {
			super(provider);
		}

		@Override
		public Type getType() {
			return Type.UNKNOWN;
		}

		@Override
		public Value getProperty(String property) throws PropertyNotFoundException {
			return null;
		}

		@Override
		public String asString() throws InvalidTypeException {
			return null;
		}

		@Override
		public int asInt() throws InvalidTypeException {
			// TODO Auto-generated method stub
			return 0;
		}
		
	}
	
	@BeforeClass
	public void setup() throws ConfigurationException {
		
		IConfigurationProvider provider = Mockito.mock(IConfigurationProvider.class);
		Value value = Mockito.spy(new TestValue(provider));
		Value stringValue = Mockito.spy(new TestValue(provider));
		Value intValue = Mockito.spy(new TestValue(provider));

		Mockito.when(stringValue.getType()).thenReturn(Type.STRING);
		Mockito.when(stringValue.asString()).thenReturn("TestStringValue");
		Mockito.when(stringValue.asInt()).thenThrow(new InvalidTypeException());
		
		Mockito.when(intValue.getType()).thenReturn(Type.INTEGER);
		Mockito.when(intValue.asString()).thenThrow(new InvalidTypeException());
		Mockito.when(intValue.asInt()).thenReturn(2020);
		
		Mockito.when(value.getType()).thenReturn(Type.OBJECT);
		Mockito.when(value.getProperty("stringProperty")).thenReturn(stringValue);
		Mockito.when(value.getProperty("intProperty")).thenReturn(intValue);
		Mockito.when(value.getProperty("anotherProperty")).thenThrow(new PropertyNotFoundException());

		Mockito.when(provider.getRootObject()).thenReturn(value);
		
		_provider = provider;

		Configuration.create("testConfiguration", provider);
		_configuration = Configuration.getInstance("testConfiguration");
	}
	
	@Test
	public void positiveSingletoneFactory() {
		Assert.assertNotNull(_configuration);
		Assert.assertEquals(_configuration.getProvider(), _provider);
	}
	
	@Test
	public void positiveStringProperty() throws PropertyNotFoundException, InvalidTypeException {
		Assert.assertEquals(_configuration.isInt("stringProperty"), false);
		Assert.assertEquals(_configuration.isString("stringProperty"), true);
		Assert.assertEquals(_configuration.getString("stringProperty"), "TestStringValue");
	}

	@Test
	public void positiveIntProperty() throws PropertyNotFoundException, InvalidTypeException {
		Assert.assertEquals(_configuration.isInt("intProperty"), true);
		Assert.assertEquals(_configuration.isString("intProperty"), false);
		Assert.assertEquals(_configuration.getInt("intProperty"), 2020);
	}

	@Test(expectedExceptions = ConfigurationExistsException.class)
	public void negativeSingletoneFactoryConfigurationExists() throws ConfigurationException {
		Configuration.create("testConfiguration", _provider);
	}
	
	@Test(expectedExceptions = ConfigurationNotFoundException.class)
	public void negativeSingletoneFactoryConfigurationNotFound() throws ConfigurationException {
		Configuration.getInstance("anotherTestConfiguration");
	}
	
	@Test(expectedExceptions = PropertyNotFoundException.class)
	public void negativePropertyNotFound() throws ConfigurationException {
		_configuration.get("anotherProperty");
	}

	@Test(expectedExceptions = InvalidTypeException.class)
	public void negativeStringIsNotInt() throws ConfigurationException {
		_configuration.getInt("stringProperty");
	}

	@Test(expectedExceptions = InvalidTypeException.class)
	public void negativeIntIsNotString() throws ConfigurationException {
		_configuration.getString("intProperty");
	}

	@Test(expectedExceptions = InvalidTypeException.class)
	public void negativePropertyOfNonObject() throws ConfigurationException {
		_configuration.get("stringProperty").get("property");
	}


}
