package br.com.datawatcher.entity;

import org.com.tatu.helper.GeneralsHelper;

public class Listener {
	
	private String			classname;
	private boolean			asynchronous;

	public Listener() { }
	
	public Listener(String classname) {
		this.classname = classname;
	}
	
	// GETTERS AND SETTERS //
	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public boolean getAsynchronous() {
		return asynchronous;
	}

	public void setAsynchronous(boolean asynchronous) {
		this.asynchronous = asynchronous;
	}
	
	public void setAsynchronous(String asynchronous) {
		this.asynchronous = GeneralsHelper.isBooleanTrue(asynchronous);
	}
}
