/**
 * 
 */
package br.com.datawatcher.entity;

import java.io.FilenameFilter;
import java.util.HashSet;
import java.util.Set;

import org.com.tatu.helper.GeneralsHelper;

import br.com.datawatcher.exception.DataWatcherException;
import br.com.datawatcher.service.CompareSimpleRegister;

/**
 * @author Jean Villete
 *
 */
public class FolderMapping extends DataMapping {

	private String				canonicalPath;
	private String				regexFilter;
	private java.io.File		folder;
	private Set<FileWrapper>	folderState;
	
	public FolderMapping() { }
	
	public FolderMapping(String canonicalPath, String regexFilter) {
		this.canonicalPath = canonicalPath;
		this.regexFilter = regexFilter;
	}
	
	private Set<FileWrapper> getFiles() {
		Set<FileWrapper> folderState = new HashSet<FileWrapper>();
		java.io.File[] files = this.getFolder().listFiles(new FolderFilter());
		for (java.io.File file : files) {
			folderState.add(new FileWrapper(file));
		}
		return folderState;
	}
	
	@Override
	public void startup() {
		if (!this.getFolder().exists() || !this.getFolder().isDirectory()) {
			throw new IllegalStateException("the address should exist or be a valid directory.");
		}
		this.folderState = this.getFiles();
	}
	
	@Override
	public void checkChange() throws DataWatcherException {
		try {
			Set<FileWrapper> newFiles = this.getFiles();
			CompareSimpleRegister<FileWrapper> compareSimpleRegister = new CompareSimpleRegister<FileWrapper>(this.folderState, newFiles);
			compareSimpleRegister.processComparing(getListeners());
		} catch (Exception e) {
			throw new DataWatcherException(e);
		}
	}
	
	// GETTERS AND SETTERS //
	public String getCanonicalPath() {
		return canonicalPath;
	}
	public void setCanonicalPath(String canonicalPath) {
		this.canonicalPath = canonicalPath;
	}
	public String getRegexFilter() {
		return regexFilter;
	}
	public void setRegexFilter(String regexFilter) {
		this.regexFilter = regexFilter;
	}
	public java.io.File getFolder() {
		if (this.folder == null) {
			if (!GeneralsHelper.isStringOk(this.canonicalPath)) {
				throw new IllegalStateException("there's no valid value to attribute canonicalPath");
			}
			this.folder = new java.io.File(this.canonicalPath);
		}
		return this.folder;
	}
	
	private class FolderFilter implements FilenameFilter {
		@Override
		public boolean accept(java.io.File dir, String name) {
			if (GeneralsHelper.isStringOk(regexFilter)) {
				return name.matches(regexFilter);
			} return true;
		}
	}
}
