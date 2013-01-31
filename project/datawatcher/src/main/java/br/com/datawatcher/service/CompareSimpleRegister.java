/**
 * 
 */
package br.com.datawatcher.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.datawatcher.entity.CoupleAnalyze;
import br.com.datawatcher.entity.Listener;
import br.com.datawatcher.entity.SimpleRegister;
import br.com.datawatcher.exception.DataWatcherException;

/**
 * @author Jean Villete
 *
 */
public class CompareSimpleRegister <T extends SimpleRegister> {
	
	private Map<Integer, CoupleAnalyze> newSimpleRegisters = new HashMap<Integer, CoupleAnalyze>();
	private Set<T> currentDataMappingState;
	
	public  CompareSimpleRegister(Set<T> currentSimpleRegister, Set<T> newSimpleRegister) {
		if (currentSimpleRegister == null || newSimpleRegister == null) {
			throw new IllegalArgumentException("param can't be null");
		}
		this.currentDataMappingState = currentSimpleRegister;
		this.turnUpCurrentState();
		this.turnUpNewState(newSimpleRegister);
	}
	
	private void turnUpCurrentState() {
		Iterator<T> iterator = this.currentDataMappingState.iterator();
		while (iterator.hasNext()) {
			SimpleRegister simpleRegister = iterator.next();
			
			CoupleAnalyze coupleTuple = this.newSimpleRegisters.get(simpleRegister.hashCode());
			if (coupleTuple == null) {
				coupleTuple = new CoupleAnalyze();
			}
			coupleTuple.addCurrentSimpleRegister(simpleRegister);
			this.newSimpleRegisters.put(simpleRegister.hashCode(), coupleTuple);
		}
	}
	
	private void turnUpNewState(Set<T> newSimpleRegister) {
		Iterator<T> iterator = newSimpleRegister.iterator();
		while (iterator.hasNext()) {
			SimpleRegister tuple = iterator.next();
			
			CoupleAnalyze coupleTuple = this.newSimpleRegisters.get(tuple.hashCode());
			if (coupleTuple == null) {
				coupleTuple = new CoupleAnalyze();
			}
			coupleTuple.addNewSimpleRegister(tuple);
			this.newSimpleRegisters.put(tuple.hashCode(), coupleTuple);
		}
	}
	
	public void processComparing(List<Listener> listeners) throws DataWatcherException {
		for (CoupleAnalyze coupleTuple : this.newSimpleRegisters.values()) {
			for (Listener listener : listeners) {
				new ProcessListener<T>(listener, coupleTuple).process(this.currentDataMappingState);
			}
		}
	}
}
