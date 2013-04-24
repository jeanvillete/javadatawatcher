/**
 * 
 */
package br.com.datawatcher.entity;

import java.util.ArrayList;
import java.util.List;

import br.com.datawatcher.common.Util;

/**
 * @author Jean Villete
 *
 */
public class Tuple extends SimpleRegister {

	private Integer						hashId;
	private Integer						hashRegister;
	
	private TupleId 					tupleId;
	private List<TupleField> 			tupleFields = new ArrayList<TupleField>();

	public Tuple(TupleId tupleId) {
		if (tupleId == null) {
			throw new IllegalArgumentException("tupleId parameter can't be null");
		}
		this.tupleId = tupleId;
	}

	public void addTupleField(TupleField tupleField) {
		if (tupleField == null) {
			throw new IllegalArgumentException("tupleField parameter can't be null");
		}
		this.tupleFields.add(tupleField);
	}
	
	private void calculatesHashes() {
		this.hashId = this.tupleId.hashCode();
		if (Util.isBooleanOk(this.tupleId.isTransientValue())) {
			this.tupleId = null;
		}
		
		StringBuffer str = new StringBuffer(this.tupleId.getStringValue());
		for (int index = 0; index < this.tupleFields.size(); index++) {
			TupleField tupleField = this.tupleFields.get(index);
			str.append(tupleField.getStringValue());
			if (Util.isBooleanOk(tupleField.isTransientValue())) {
				this.tupleFields.remove(index);
			}
		}
		this.hashRegister = str.toString().hashCode();
	}
	
	@Override
	public int hashCode() {
		if (this.hashId == null) {
			this.calculatesHashes();
		}
		return this.hashId;
	}
	
	@Override
	public long hashSimpleRegister() {
		if (this.hashRegister == null) {
			this.calculatesHashes();
		}
		return this.hashRegister;
	}
	
	public TupleId getTupleId() {
		return tupleId;
	}

	public List<TupleField> getTupleFields() {
		return tupleFields;
	}

}
