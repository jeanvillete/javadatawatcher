/**
 * 
 */
package br.com.datawatcher.interfaces;

import br.com.datawatcher.entity.SimpleRegister;

/**
 * @author Jean Villete
 *
 */
public interface DataChangeable {

	void insert(SimpleRegister newSimpleRegister);
	void update(SimpleRegister oldSimpleRegister, SimpleRegister newSimpleRegister);
	void delete(SimpleRegister oldSimpleRegister);
	
}
