/**
 * 
 */
package br.com.datawatcher.listener.adapter;

import br.com.datawatcher.entity.SimpleRegister;
import br.com.datawatcher.entity.Tuple;
import br.com.datawatcher.interfaces.DataChangeable;

/**
 * @author Jean Villete
 *
 */
public abstract class TableMappingListenerAdapter implements DataChangeable {

	@Override
	public void insert(SimpleRegister newSimpleRegister) {
		this.insert((Tuple) newSimpleRegister);
	}

	@Override
	public void update(SimpleRegister oldSimpleRegister, SimpleRegister newSimpleRegister) {
		this.update((Tuple) oldSimpleRegister, (Tuple) newSimpleRegister);
	}

	@Override
	public void delete(SimpleRegister oldSimpleRegister) {
		this.delete((Tuple) oldSimpleRegister);
	}

	public abstract void insert(Tuple newTuple);
	public abstract void update(Tuple oldTuple, Tuple newTuple);
	public abstract void delete(Tuple oldTuple);
}
