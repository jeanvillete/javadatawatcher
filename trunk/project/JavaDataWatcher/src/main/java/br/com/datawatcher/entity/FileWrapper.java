/**
 * 
 */
package br.com.datawatcher.entity;

import java.io.File;
import java.io.IOException;

import br.com.datawatcher.exception.DataWatcherRuntimeException;

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
		try {
			return this.file.getCanonicalPath().hashCode();
		} catch (IOException e) {
			throw new DataWatcherRuntimeException(e);
		}
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
