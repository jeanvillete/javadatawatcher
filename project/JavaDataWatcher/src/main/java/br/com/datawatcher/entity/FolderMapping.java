/**
 * 
 */
package br.com.datawatcher.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.com.tatu.helper.FileHelper;
import org.com.tatu.helper.GeneralsHelper;

import br.com.datawatcher.exception.DataWatcherException;
import br.com.datawatcher.service.CompareSimpleRegister;

/**
 * @author Jean Villete
 *
 */
public class FolderMapping extends DataMapping {

	private String				canonicalPath;
	private String				filter;
	private java.io.File		folder;
	private Set<FileWrapper>	folderState;
	private Boolean				recursively;
	
	public FolderMapping() { }
	
	public FolderMapping(String canonicalPath, String filter) {
		this.canonicalPath = canonicalPath;
		this.filter = filter;
	}
	
	private Set<FileWrapper> getFiles() {
		Set<FileWrapper> folderState = new HashSet<FileWrapper>();
		java.io.File[] files = null;
		if (GeneralsHelper.isStringOk(this.filter)) {
			List<String> filters = new ArrayList<String>();
			for (String filter : this.filter.split(":")) {
				if (GeneralsHelper.isStringOk(filter)) {
					filters.add(filter.trim());
				}
			}
			
			FileHelper fileHelper = new FileHelper(this.canonicalPath);
			files = fileHelper.find(filters.toArray(new String[]{})).toArray(new java.io.File[]{});
		} else {
			files = this.getFolder().listFiles();
		}
		
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
	public String getFilter() {
		return filter;
	}
	public Boolean getRecursively() {
		return recursively;
	}
	public void setRecursively(Boolean recursively) {
		this.recursively = recursively;
	}
	public void setFilter(String filter) {
		this.filter = filter;
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

}
