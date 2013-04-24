/**
 * 
 */
package br.com.datawatcher.listener.adapter;

import br.com.datawatcher.entity.FileWrapper;
import br.com.datawatcher.entity.SimpleRegister;
import br.com.datawatcher.interfaces.DataChangeable;

/**
 * @author Jean Villete
 *
 */
public abstract class FolderMappingListenerAdapter implements DataChangeable {

	@Override
	public void insert(SimpleRegister newSimpleRegister) {
		this.insert((FileWrapper) newSimpleRegister);
	}

	@Override
	public void update(SimpleRegister oldSimpleRegister, SimpleRegister newSimpleRegister) {
		this.update((FileWrapper)newSimpleRegister);
	}

	@Override
	public void delete(SimpleRegister oldSimpleRegister) {
		this.delete((FileWrapper) oldSimpleRegister);
	}

	public abstract void insert(FileWrapper newFile);
	public abstract void delete(FileWrapper oldFile);
	public abstract void update(FileWrapper file);
}
