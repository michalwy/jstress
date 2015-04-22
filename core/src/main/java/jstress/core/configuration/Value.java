package jstress.core.configuration;

import jstress.core.configuration.exceptions.InvalidTypeException;
import jstress.core.configuration.exceptions.PropertyNotFoundException;

public abstract class Value {
	
	public enum Type {
		UNKNOWN, OBJECT, STRING, INTEGER
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
	
	public boolean isObject() {
		return this.getType() == Type.OBJECT;
	}
	
	public Value get() {
		return this.getValue();
	}
	
	public Value get(String property) throws PropertyNotFoundException, InvalidTypeException {
		if (!this.get().isObject()) {
			throw new InvalidTypeException();
		}
		return this.getValue().getProperty(property);
	}
	
	public Type getType(String property) throws PropertyNotFoundException, InvalidTypeException {
		return this.get("property").getType();
	}
	
	public String getString() throws InvalidTypeException {
		return this.get().asString();
	}

	public String getString(String property) throws InvalidTypeException, PropertyNotFoundException {
		return this.get(property).getString();
	}
	
	public boolean isString() {
		return this.get().getType() == Type.STRING;
	}
	
	public boolean isString(String property) throws PropertyNotFoundException, InvalidTypeException {
		return this.get(property).isString();
	}
	
	public int getInt() throws InvalidTypeException {
		return this.get().asInt();
	}

	public int getInt(String property) throws InvalidTypeException, PropertyNotFoundException {
		return this.get(property).getInt();
	}
	
	public boolean isInt() {
		return this.get().getType() == Type.INTEGER;
	}
	
	public boolean isInt(String property) throws PropertyNotFoundException, InvalidTypeException {
		return this.get(property).isInt();
	}
	
	protected Value getValue() {
		return this.isLink() ? this.getLink() : this;
	}
	
	public abstract Type getType();
	public abstract Value getProperty(String property) throws InvalidTypeException, PropertyNotFoundException;
	public abstract String asString() throws InvalidTypeException;
	public abstract int asInt() throws InvalidTypeException;
}
