package br.com.listener.jdbc;
import java.io.IOException;

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
	
	@Override
	public void update(FileWrapper file) {
		this.printFile("update", file);
	}

	private void printFile(String operation, FileWrapper file) {
		try {
			log.info(operation + ", file name : " + file.getFile().getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
