package br.com.listener.jdbc;
import org.apache.log4j.Logger;

import br.com.datawatcher.entity.Tuple;
import br.com.datawatcher.listener.adapter.TableMappingListenerAdapter;

/**
 * @author Jean Villete
 *
 */
public class JdbcListenerImplTest extends TableMappingListenerAdapter {

	private static Logger log = Logger.getLogger(JdbcListenerImplTest.class);
	
	@Override
	public void insert(Tuple newTuple) {
		this.printTuple("insert", newTuple);
	}
	
	@Override
	public void update(Tuple oldTuple, Tuple newTuple) {
		this.printTuple("update", newTuple);
	}
	
	@Override
	public void delete(Tuple oldTuple) {
		this.printTuple("delete", oldTuple);
	}
	
	private void printTuple(String operation, Tuple tuple) {
		log.info(operation + ", id : " + tuple.getTupleId().getValue());
	}

}
