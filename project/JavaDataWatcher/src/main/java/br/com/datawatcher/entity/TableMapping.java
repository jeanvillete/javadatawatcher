/**
 * 
 */
package br.com.datawatcher.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.com.tatu.helper.GeneralsHelper;

import br.com.datawatcher.exception.DataWatcherException;
import br.com.datawatcher.exception.InterfaceNotImplemented;
import br.com.datawatcher.service.CompareSimpleRegister;

/**
 * @author Jean Villete
 *
 */
public class TableMapping extends DataMapping {

	private String				name;
	private Id					id;
	private List<Column>		columns = new ArrayList<Column>();
	private JdbcConnection		jdbcConnection;
	private DeclaredResult		declaredResult;
	// key = tuple id
	// value = tuple hash code
	private Set<Tuple> 			tableState;
	private boolean				started=false;
	
	public TableMapping addColumn(Column column) {
		if (column != null) {
			columns.add(column);
			return this;
		} else throw new IllegalArgumentException("parameter column can not be null");
	}
	
	private JdbcConnectionWrapper processSelect() throws InterfaceNotImplemented, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		br.com.datawatcher.common.JdbcConnection jdbcConnection = new br.com.datawatcher.common.JdbcConnection(this.jdbcConnection);

		ResultSet resultSet = null;
		if (this.declaredResult != null) {
			for (Procedure procedure : this.declaredResult.getProcedures()) {
				jdbcConnection.execute(procedure.getStatement());
			}
			resultSet = jdbcConnection.select(this.declaredResult.getQuery().getStatement());
		} else {
			StringBuffer sqlStatement = new StringBuffer();
			sqlStatement.append(this.id.getColumnName() + ", ");
			for (int i = 0; i < this.columns.size(); i++) {
				sqlStatement.append(this.columns.get(i).getColumnName());
				if ((i+1) < this.columns.size()) {
					sqlStatement.append(", ");
				}
			}
			resultSet = jdbcConnection.select("select " + sqlStatement.toString() + " from " + this.name);
		}
		if (resultSet != null) {
			return new JdbcConnectionWrapper(resultSet, jdbcConnection);
		} else throw new IllegalStateException("Problems with table mapping: " + this.name + ". result set is null.");
	}

	private Set<Tuple> buildResult(JdbcConnectionWrapper jdbcConnectionWrapper) throws SQLException, ClassNotFoundException {
		Set<Tuple> tableState = new HashSet<Tuple>();
		ResultSet resultSet = jdbcConnectionWrapper.getResultSet();
		while (resultSet.next()) {
			TupleId tupleId = new TupleId(this.id, this.getStringResultColumn(this.id, resultSet));
			Tuple tuple = new Tuple(tupleId);
			for (Column column : this.columns) {
				TupleField tupleField = new TupleField(column, this.getStringResultColumn(column, resultSet));
				tuple.addTupleField(tupleField);
			}
			tableState.add(tuple);
		}
		jdbcConnectionWrapper.close();
		return tableState;
	}
	
	private Object getStringResultColumn(Column column, ResultSet resultSet) throws ClassNotFoundException, SQLException {
		if (column.getColumnClass().equals(java.lang.String.class)) {
			return resultSet.getString(column.getColumnName());
		} else if (column.getColumnClass().equals(java.lang.Character.class)) {
			return resultSet.getString(column.getColumnName());
		} else if (column.getColumnClass().equals(java.lang.Long.class)) {
			return resultSet.getLong(column.getColumnName());
		} else if (column.getColumnClass().equals(java.lang.Integer.class)) {
			return resultSet.getInt(column.getColumnName());
		} else if (column.getColumnClass().equals(java.lang.Byte.class)) {
			return resultSet.getByte(column.getColumnName());
		} else if (column.getColumnClass().equals(java.lang.Double.class)) {
			return resultSet.getDouble(column.getColumnName());
		} else if (column.getColumnClass().equals(java.lang.Float.class)) {
			return resultSet.getFloat(column.getColumnName());
		} else if (column.getColumnClass().equals(java.util.Date.class)) {
			return resultSet.getDate(column.getColumnName());
		} else throw new IllegalStateException("Unknow type column: " + column.getColumnName());
	}
	
	@Override
	public void startup() throws DataWatcherException {
		if (this.id != null && GeneralsHelper.isCollectionOk(this.columns)) {
			try {
				JdbcConnectionWrapper jdbcConnectionWrapper = this.processSelect();
				this.tableState = this.buildResult(jdbcConnectionWrapper);
				this.started = true;
			} catch (Exception e) {
				throw new DataWatcherException(e);
			}
		} else throw new DataWatcherException("Problems while starting up the table mapping: " + this.name);
	}
	
	@Override
	public void checkChange() throws DataWatcherException {
		if (this.started) {
			try {
				JdbcConnectionWrapper jdbcConnectionWrapper = this.processSelect();
				Set<Tuple> newTuples = this.buildResult(jdbcConnectionWrapper);
				CompareSimpleRegister<Tuple> compareSimpleRegister = new CompareSimpleRegister<Tuple>(this.tableState, newTuples);
				compareSimpleRegister.processComparing(this.getListeners());
			} catch (Exception e) {
				throw new DataWatcherException(e);
			}
		} else throw new DataWatcherException("Table mapping was not started. (startup not invoked)");
	}
	
	// GETTERS AND SETTERS //
	public Id getId() {
		return id;
	}
	public void setId(Id id) {
		this.id = id;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public JdbcConnection getJdbcConnection() {
		return jdbcConnection;
	}
	public void setJdbcConnection(JdbcConnection jdbcConnection) {
		this.jdbcConnection = jdbcConnection;
	}
	public DeclaredResult getDeclaredResult() {
		return declaredResult;
	}
	public void setDeclaredResult(DeclaredResult declaredResult) {
		this.declaredResult = declaredResult;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
