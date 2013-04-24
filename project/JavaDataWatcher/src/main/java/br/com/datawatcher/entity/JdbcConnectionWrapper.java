/**
 * 
 */
package br.com.datawatcher.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.datawatcher.common.JdbcConnection;

/**
 * @author Jean Villete
 *
 */
public class JdbcConnectionWrapper {
	private ResultSet			resultSet;
	private JdbcConnection		jdbcConnection;
	
	JdbcConnectionWrapper(ResultSet	resultSet, JdbcConnection jdbcConnection) {
		this.resultSet = resultSet;
		this.jdbcConnection = jdbcConnection;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public JdbcConnection getJdbcConnection() {
		return jdbcConnection;
	}

	public void setJdbcConnection(JdbcConnection jdbcConnection) {
		this.jdbcConnection = jdbcConnection;
	}
	
	public void close() throws SQLException {
		this.jdbcConnection.closeConnection();
		this.resultSet.close();
	}
}
