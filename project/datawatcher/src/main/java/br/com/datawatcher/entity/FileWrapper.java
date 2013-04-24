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
	private Long				lastModified;
	
	public FileWrapper(File file) {
		if (file == null) {
			throw new IllegalArgumentException("param file cann't be null.");
		}
		this.file = file;
		this.lastModified = this.file.lastModified();
	}
	
	@Override
	public int hashCode() {
		return this.file.getName().hashCode();
	}

	@Override
	public long hashSimpleRegister() {
		return this.lastModified;
	}
	
	// GETTERS AND SETTERS //
	public File getFile() {
		return file;
	}

}
