/**
 * 
 */
package br.com.datawatcher.entity;

import org.com.tatu.helper.GeneralsHelper;

import br.com.datawatcher.exception.InterfaceNotImplemented;
import br.com.datawatcher.interfaces.Decryptable;

/**
 * @author Jean Villete
 *
 */
public abstract class DataLogging {
	
	public DataLogging(String decryptClass) {
		this.decryptClass = decryptClass;
	}

	private String				decryptClass;

	protected String getDecryptedDataLogging(String dataLogging) throws InstantiationException, IllegalAccessException, ClassNotFoundException, InterfaceNotImplemented {
		if (GeneralsHelper.isStringOk(this.decryptClass)) {
			Object decryptable = Class.forName(this.decryptClass).newInstance();
			if (decryptable instanceof Decryptable) {
				return ((Decryptable)decryptable).decrypt(dataLogging);
			} else throw new InterfaceNotImplemented("The \"class-decrypt\" (tag) must refer a class that implement the \"br.com.datawatcher.common.Decryptable\" interface.");
		}
		return dataLogging;
	}

	public String getDecryptClass() {
		return decryptClass;
	}
}
