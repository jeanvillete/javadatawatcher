/**
 * 
 */
package br.com.datawatcher.entity;

import br.com.datawatcher.common.Util;

/**
 * @author Jean Villete
 *
 */
public class File extends SimpleRegister {

	private String				name;
	
	public File(String name) {
		if (!Util.isStringOk(name)) {
			throw new IllegalArgumentException("param name is not value.");
		}
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public int hashSimpleRegister() {
		return this.hashCode();
	}
	
	// GETTERS AND SETTERS //
	public String getName() {
		return name;
	}

}
