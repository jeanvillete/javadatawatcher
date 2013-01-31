package br.com.listener.jdbc;
import org.apache.log4j.Logger;

import br.com.datawatcher.entity.File;
import br.com.datawatcher.listener.adapter.FolderMappingListenerAdapter;

/**
 * @author Jean Villete
 *
 */
public class FolderListenerImplTest extends FolderMappingListenerAdapter {

	private static Logger log = Logger.getLogger(FolderListenerImplTest.class);
	
	@Override
	public void insert(File newFile) {
		this.printFile("insert", newFile);
	}
	
	@Override
	public void delete(File oldFile) {
		this.printFile("delete", oldFile);
	}

	private void printFile(String operation, File file) {
		log.info(operation + ", file name : " + file.getName());
	}

}
