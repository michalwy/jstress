package jstress.core.configuration;

import jstress.core.configuration.exceptions.IndexOutOfBoundsException;
import jstress.core.configuration.exceptions.InvalidTypeException;
import jstress.core.configuration.exceptions.PropertyNotFoundException;

public abstract class Value {
	
	public enum Type {
		UNKNOWN, OBJECT, STRING, INTEGER, ARRAY
	}
	
	private IConfigurationProvider _provider;
	private Value _link;

	public Value(IConfigurationProvider provider) {
		this._provider = provider;
	}
	
	public IConfigurationProvider getProvider() {
		return this._provider;
	}
	
	public void setLink(Value link) {
		this._link = link;
	}
	
	public Value getLink() {
		return this._link;
	}
	
	public boolean isLink() {
		return this._link != null;
	}
	
	public Value get() {
		return this.isLink() ? this.getLink() : this;
	}
	
	public Value get(String property) throws PropertyNotFoundException, InvalidTypeException {
		if (!this.get().isObject()) {
			throw new InvalidTypeException();
		}
		return this.get().getProperty(property);
	}
	
	public Value get(int index) throws IndexOutOfBoundsException, InvalidTypeException {
		if (!this.get().isArray()) {
			throw new InvalidTypeException();
		}
		return this.get().getArrayElement(index);
	}

	public Type getType(String property) throws PropertyNotFoundException, InvalidTypeException {
		return this.get(property).getType();
	}

	public Type getType(int index) throws IndexOutOfBoundsException, InvalidTypeException {
		return this.get(index).getType();
	}

	public boolean isObject() {
		return this.getType() == Type.OBJECT;
	}
	
	public boolean isObject(String property) throws PropertyNotFoundException, InvalidTypeException {
		return this.get(property).isObject();
	}
	
	public boolean isObject(int index) throws IndexOutOfBoundsException, InvalidTypeException {
		return this.get(index).isObject();
	}
	
	public boolean isArray() {
		return this.getType() == Type.ARRAY;
	}
	
	public boolean isArray(String property) throws PropertyNotFoundException, InvalidTypeException {
		return this.get(property).isArray();
	}
	
	public boolean isArray(int index) throws IndexOutOfBoundsException, InvalidTypeException {
		return this.get(index).isArray();
	}
	
	public boolean isString() {
		return this.get().getType() == Type.STRING;
	}
	
	public boolean isString(String property) throws PropertyNotFoundException, InvalidTypeException {
		return this.get(property).isString();
	}
	
	public boolean isString(int index) throws InvalidTypeException, IndexOutOfBoundsException {
		return this.get(index).isString();
	}

	public boolean isInt() {
		return this.get().getType() == Type.INTEGER;
	}
	
	public boolean isInt(String property) throws PropertyNotFoundException, InvalidTypeException {
		return this.get(property).isInt();
	}
	
	public boolean isInt(int index) throws IndexOutOfBoundsException, InvalidTypeException {
		return this.get(index).isInt();
	}
	
	public String getString() throws InvalidTypeException {
		return this.get().asString();
	}

	public String getString(String property) throws InvalidTypeException, PropertyNotFoundException {
		return this.get(property).getString();
	}
	
	public String getString(int index) throws InvalidTypeException, IndexOutOfBoundsException {
		return this.get(index).getString();
	}
	
	public int getInt() throws InvalidTypeException {
		return this.get().asInt();
	}

	public int getInt(String property) throws InvalidTypeException, PropertyNotFoundException {
		return this.get(property).getInt();
	}
	
	public int getInt(int index) throws InvalidTypeException, IndexOutOfBoundsException {
		return this.get(index).getInt();
	}
	
	public abstract Type getType();
	public abstract Value getProperty(String property) throws InvalidTypeException, PropertyNotFoundException;
	public abstract Value getArrayElement(int index) throws InvalidTypeException, IndexOutOfBoundsException;
	public abstract String asString() throws InvalidTypeException;
	public abstract int asInt() throws InvalidTypeException;
}
