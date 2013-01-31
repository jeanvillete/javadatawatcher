/**
 * 
 */
package br.com.datawatcher.service;

import java.util.Set;

import br.com.datawatcher.common.Util;
import br.com.datawatcher.entity.CoupleAnalyze;
import br.com.datawatcher.entity.Listener;
import br.com.datawatcher.entity.SimpleRegister;
import br.com.datawatcher.entity.Tuple;
import br.com.datawatcher.exception.DataWatcherException;
import br.com.datawatcher.exception.DataWatcherRuntimeException;
import br.com.datawatcher.interfaces.DataChangeable;

/**
 * @author Jean Villete
 *
 */
public class ProcessListener <T extends SimpleRegister> implements Runnable {
	
	private Set<T> 				dataMappingState;
	private Listener 			listener;
	private CoupleAnalyze 		coupleTuple;
	
	protected ProcessListener(Listener listener, CoupleAnalyze coupleTuple) {
		if (listener == null || coupleTuple == null) {
			throw new IllegalArgumentException("param can't be null");
		}
		this.listener = listener;
		this.coupleTuple = coupleTuple;
	}
	
	protected void process(Set<T> dataMappingState) throws DataWatcherException {
		if (dataMappingState == null) {
			throw new IllegalArgumentException("param dataMappingState not value.");
		}
		this.dataMappingState = dataMappingState;
		if (Util.isBooleanOk(this.listener.getAsynchronous())) {
			new Thread(this).start();
		} else {
			this.run();
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			if (this.coupleTuple.isUpdate()) {
				this.dataMappingState.remove(this.coupleTuple.getCurrentSimpleRegister());
				this.dataMappingState.add((T) this.coupleTuple.getNewSimpleRegister());
				((DataChangeable)Class.forName(this.listener.getClassname()).newInstance()).update(this.coupleTuple.getCurrentSimpleRegister(), (Tuple)this.coupleTuple.getNewSimpleRegister());
			} else if (this.coupleTuple.isDelete()) {
				this.dataMappingState.remove(this.coupleTuple.getCurrentSimpleRegister());
				((DataChangeable)Class.forName(this.listener.getClassname()).newInstance()).delete(this.coupleTuple.getCurrentSimpleRegister());
			} else if (this.coupleTuple.isInsert()) {
				this.dataMappingState.add((T) this.coupleTuple.getNewSimpleRegister());
				((DataChangeable)Class.forName(this.listener.getClassname()).newInstance()).insert(this.coupleTuple.getNewSimpleRegister());
			}
		} catch (Exception e) {
			throw new DataWatcherRuntimeException(e);
		}
	}
}
