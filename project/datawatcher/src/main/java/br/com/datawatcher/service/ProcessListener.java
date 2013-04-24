/**
 * 
 */
package br.com.datawatcher.service;

import java.util.Set;

import br.com.datawatcher.common.Util;
import br.com.datawatcher.entity.CoupleAnalyze;
import br.com.datawatcher.entity.Listener;
import br.com.datawatcher.entity.SimpleRegister;
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
	private CoupleAnalyze 		coupleAnalyze;
	
	protected ProcessListener(Listener listener, CoupleAnalyze coupleAnalyze) {
		if (listener == null || coupleAnalyze == null) {
			throw new IllegalArgumentException("param can't be null");
		}
		this.listener = listener;
		this.coupleAnalyze = coupleAnalyze;
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
			if (this.coupleAnalyze.isUpdate()) {
				this.dataMappingState.remove(this.coupleAnalyze.getCurrentSimpleRegister());
				this.dataMappingState.add((T) this.coupleAnalyze.getNewSimpleRegister());
				((DataChangeable)Class.forName(this.listener.getClassname()).newInstance()).update(this.coupleAnalyze.getCurrentSimpleRegister(), (T)this.coupleAnalyze.getNewSimpleRegister());
			} else if (this.coupleAnalyze.isDelete()) {
				this.dataMappingState.remove(this.coupleAnalyze.getCurrentSimpleRegister());
				((DataChangeable)Class.forName(this.listener.getClassname()).newInstance()).delete(this.coupleAnalyze.getCurrentSimpleRegister());
			} else if (this.coupleAnalyze.isInsert()) {
				this.dataMappingState.add((T) this.coupleAnalyze.getNewSimpleRegister());
				((DataChangeable)Class.forName(this.listener.getClassname()).newInstance()).insert(this.coupleAnalyze.getNewSimpleRegister());
			}
		} catch (Exception e) {
			throw new DataWatcherRuntimeException(e);
		}
	}
}
