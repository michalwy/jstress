package jstress.core.tests.configuration;

import jstress.core.configuration.Configuration;
import jstress.core.configuration.IConfigurationProvider;
import jstress.core.configuration.Value;
import jstress.core.configuration.Value.Type;
import jstress.core.configuration.exceptions.ConfigurationException;
import jstress.core.configuration.exceptions.ConfigurationExistsException;
import jstress.core.configuration.exceptions.ConfigurationNotFoundException;
import jstress.core.configuration.exceptions.IndexOutOfBoundsException;
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
			return 0;
		}

		@Override
		public Value getArrayElement(int index) throws InvalidTypeException,
				IndexOutOfBoundsException {
			return null;
		}
		
	}
	
	@BeforeClass
	public void setup() throws ConfigurationException {
		
		IConfigurationProvider provider = Mockito.mock(IConfigurationProvider.class);
		Value value = Mockito.spy(new TestValue(provider));
		Value stringValue = Mockito.spy(new TestValue(provider));
		Value intValue = Mockito.spy(new TestValue(provider));
		Value arrayValue = Mockito.spy(new TestValue(provider));

		Mockito.when(stringValue.getType()).thenReturn(Type.STRING);
		Mockito.when(stringValue.asString()).thenReturn("TestStringValue");
		Mockito.when(stringValue.asInt()).thenThrow(new InvalidTypeException());
		
		Mockito.when(intValue.getType()).thenReturn(Type.INTEGER);
		Mockito.when(intValue.asString()).thenThrow(new InvalidTypeException());
		Mockito.when(intValue.asInt()).thenReturn(2020);
		
		Mockito.when(arrayValue.getType()).thenReturn(Type.ARRAY);
		Mockito.when(arrayValue.getArrayElement(0)).thenReturn(stringValue);
		Mockito.when(arrayValue.getArrayElement(1)).thenReturn(intValue);
		Mockito.when(arrayValue.getArrayElement(2)).thenThrow(new IndexOutOfBoundsException());

		Mockito.when(value.getType()).thenReturn(Type.OBJECT);
		Mockito.when(value.getProperty("stringProperty")).thenReturn(stringValue);
		Mockito.when(value.getProperty("intProperty")).thenReturn(intValue);
		Mockito.when(value.getProperty("arrayProperty")).thenReturn(arrayValue);
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
		Assert.assertEquals(_configuration.isObject("stringProperty"), false);
		Assert.assertEquals(_configuration.isArray("stringProperty"), false);
		Assert.assertEquals(_configuration.isString("stringProperty"), true);
		Assert.assertEquals(_configuration.isInt("stringProperty"), false);

		Assert.assertEquals(_configuration.getString("stringProperty"), "TestStringValue");
	}

	@Test
	public void positiveIntProperty() throws PropertyNotFoundException, InvalidTypeException {
		Assert.assertEquals(_configuration.isObject("intProperty"), false);
		Assert.assertEquals(_configuration.isArray("intProperty"), false);
		Assert.assertEquals(_configuration.isString("intProperty"), false);
		Assert.assertEquals(_configuration.isInt("intProperty"), true);

		Assert.assertEquals(_configuration.getInt("intProperty"), 2020);
	}

	@Test
	public void positiveArrayProperty() throws PropertyNotFoundException, InvalidTypeException, IndexOutOfBoundsException {
		Assert.assertEquals(_configuration.isObject("arrayProperty"), false);
		Assert.assertEquals(_configuration.isArray("arrayProperty"), true);
		Assert.assertEquals(_configuration.isString("arrayProperty"), false);
		Assert.assertEquals(_configuration.isInt("arrayProperty"), false);
		
		Assert.assertEquals(_configuration.get("arrayProperty").getString(0), "TestStringValue");
		Assert.assertEquals(_configuration.get("arrayProperty").getInt(1), 2020);
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

	@Test(expectedExceptions = InvalidTypeException.class)
	public void negativeElementOfNonArray() throws ConfigurationException {
		_configuration.get("stringProperty").get(0);
	}

	@Test(expectedExceptions = IndexOutOfBoundsException.class)
	public void negativeArrayIndexOutOfBound() throws ConfigurationException {
		_configuration.get("arrayProperty").get(2);
	}

}
