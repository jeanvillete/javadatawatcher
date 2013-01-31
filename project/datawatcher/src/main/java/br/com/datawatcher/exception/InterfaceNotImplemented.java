/**
 * 
 */
package br.com.datawatcher.exception;

/**
 * @author Jean Villete
 *
 */
public class InterfaceNotImplemented extends DataWatcherException {

	private static final long serialVersionUID = -807473621855890941L;

	public InterfaceNotImplemented() {
	}

	public InterfaceNotImplemented(String message) {
		super(message);
	}

	public InterfaceNotImplemented(Exception e) {
		super(e);
	}

}
