/**
 * 
 */
package br.com.datawatcher.entity;


/**
 * @author Jean Villete
 *
 */
public class CoupleAnalyze {

	private SimpleRegister				currentSimpleRegister;
	private SimpleRegister				newSimpleRegister;
	
	public CoupleAnalyze() {
	}
	
	public boolean isInsert() {
		return currentSimpleRegister == null && newSimpleRegister != null;
	}
	
	public boolean isDelete() {
		return currentSimpleRegister != null && newSimpleRegister == null;
	}
	
	public boolean isUpdate() {
		return (currentSimpleRegister != null && newSimpleRegister != null) && (currentSimpleRegister.hashSimpleRegister() != newSimpleRegister.hashSimpleRegister());
	}
	
	public void addCurrentSimpleRegister(SimpleRegister currentSimpleRegister) {
		if (currentSimpleRegister == null) {
			throw new IllegalArgumentException("param SimpleRegisterntSimpleRegister canSimpleRegister null");
		}
		this.currentSimpleRegister = currentSimpleRegister;
	}

	public void addNewSimpleRegister(SimpleRegister newSimpleRegister) {
		if (newSimpleRegister == null) {
			throw new IllegalArgumentException("param newSimpleRegister can't be null");
		}
		this.newSimpleRegister = newSimpleRegister;
	}

	public SimpleRegister getCurrentSimpleRegister() {
		return currentSimpleRegister;
	}

	public SimpleRegister getNewSimpleRegister() {
		return newSimpleRegister;
	}
	
}
