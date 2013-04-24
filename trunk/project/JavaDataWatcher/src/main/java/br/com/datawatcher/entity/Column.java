/**
 * 
 */
package br.com.datawatcher.entity;

/**
 * @author Jean Villete
 *
 */
public class Column {

	private String			columnType;
	private String			columnName;
	private String			transientValue;

	public Column() { }
	
	public Column(String columnType, String columnName) {
		this.columnType = columnType;
		this.columnName = columnName;
	}
	
	@SuppressWarnings("rawtypes")
	public Class getColumnClass() throws ClassNotFoundException {
		return Class.forName(this.columnType);
	}
	
	// GETTERS AND SETTERS //
	public String getColumnName() {
		return columnName;
	}

	public String getColumnType() {
		return columnType;
	}
	
	public String isTransientValue() {
		return transientValue;
	}
}
