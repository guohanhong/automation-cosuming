package com.asking.cosuming.routing;

public abstract class LastModified {
	
	protected long modified;
	
	public abstract void modified();
	
	public abstract void close();
}
