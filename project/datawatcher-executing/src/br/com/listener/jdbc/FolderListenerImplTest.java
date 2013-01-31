package br.com.listener.jdbc;
import org.apache.log4j.Logger;

import br.com.datawatcher.entity.FileWrapper;
import br.com.datawatcher.listener.adapter.FolderMappingListenerAdapter;

/**
 * @author Jean Villete
 *
 */
public class FolderListenerImplTest extends FolderMappingListenerAdapter {

	private static Logger log = Logger.getLogger(FolderListenerImplTest.class);
	
	@Override
	public void insert(FileWrapper newFile) {
		this.printFile("insert", newFile);
	}
	
	@Override
	public void delete(FileWrapper oldFile) {
		this.printFile("delete", oldFile);
	}

	private void printFile(String operation, FileWrapper file) {
		log.info(operation + ", file name : " + file.getFile().getName());
	}

}
