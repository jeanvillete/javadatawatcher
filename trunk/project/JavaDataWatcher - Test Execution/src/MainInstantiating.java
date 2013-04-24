import br.com.datawatcher.entity.CheckChange;
import br.com.datawatcher.entity.DataWatcher;
import br.com.datawatcher.entity.FolderMapping;
import br.com.datawatcher.entity.Listener;
import br.com.datawatcher.exception.DataWatcherException;
import br.com.listener.jdbc.FolderListenerImplTest;

public class MainInstantiating {

	public static void main(String[] args) {
		try {
			FolderMapping folder = new FolderMapping();
			folder.setIdentifier("MyFolderWatcherCron");
			folder.setCanonicalPath("C:\\TEMP\\datawatcher\\listened");
			folder.setRegexFilter("^[\\w|\\W]+.txt$");
			folder.setCheckChange(new CheckChange("0/5 * * ? * *"));
			Listener listener = new Listener(FolderListenerImplTest.class.getName());
			listener.setAsynchronous("true");
			folder.addListeners(listener);
			
			DataWatcher watcher = new DataWatcher();
			watcher.addMapping(folder);
			watcher.start();
		} catch (DataWatcherException e) {
			e.printStackTrace();
		}
	}
}
