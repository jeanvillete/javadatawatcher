import java.io.File;

import br.com.datawatcher.entity.DataWatcher;
import br.com.datawatcher.exception.DataWatcherException;
import br.com.datawatcher.xstream.XStreamFactory;

public class MainFromXML {
	
	public static void main(String[] args) throws ClassNotFoundException {
		try {
	        DataWatcher dataWatcher = XStreamFactory.getInstance().fromXML(
					new File("xml-data"  + System.getProperty("file.separator") + "datawatcher.xml"));
			dataWatcher.start();
		} catch (DataWatcherException e) {
			e.printStackTrace();
		}
	}
}
