/**
 * 
 */
package br.com.datawatcher.listener.adapter;

import br.com.datawatcher.entity.File;
import br.com.datawatcher.entity.SimpleRegister;
import br.com.datawatcher.interfaces.DataChangeable;

/**
 * @author Jean Villete
 *
 */
public abstract class FolderMappingListenerAdapter implements DataChangeable {

	@Override
	public void insert(SimpleRegister newSimpleRegister) {
		this.insert((File) newSimpleRegister);
	}

	@Override
	public void update(SimpleRegister oldSimpleRegister, SimpleRegister newSimpleRegister) {
	}

	@Override
	public void delete(SimpleRegister oldSimpleRegister) {
		this.delete((File) oldSimpleRegister);
	}

	public abstract void insert(File newFile);
	public abstract void delete(File oldFile);
}
