/**
 * 
 */
package br.com.datawatcher.entity;

import java.io.File;

/**
 * @author Jean Villete
 *
 */
public class FileWrapper extends SimpleRegister {

	private File				file;
	
	public FileWrapper(File file) {
		if (file == null) {
			throw new IllegalArgumentException("param file cann't be null.");
		}
		this.file = file;
	}
	
	@Override
	public int hashCode() {
		return this.file.getName().hashCode();
	}

	@Override
	public int hashSimpleRegister() {
		return this.hashCode();
	}
	
	// GETTERS AND SETTERS //
	public File getFile() {
		return file;
	}

}
