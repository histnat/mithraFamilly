package net.mithra.familly.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.sedoc.toolbox.LogMes;

public class FileAccess {

	/**
	 * 
	 * @param path
	 * @return
	 */
	public static String getResourceFilePath(String path) {
		try {
			return getResourceFilePath(path, false).getAbsolutePath();
		} catch (Exception e) {
			LogMes.logError(FileAccess.class, "imposible to acces at:" + path, e);
		}
		return null;
	}

	/**
	 * 
	 * @param path
	 * @param tryFileSystem
	 *            - if true try to find on file system befor search in resource
	 * @return
	 */
	public static File getResourceFilePath(String path, boolean tryFileSystem) {
		if (path == null) {
			LogMes.log(FileAccess.class, LogMes.ERROR, "path is null, it's impossible to find it");
			return null;
		}
		if (tryFileSystem) {
			File f = new File(path);

			if (f.exists()) {
				return f;
			}
		}
		try {
			System.out.println((new FileAccess())
					.getClass()
					.getClassLoader()
					.getResource(path)
					.toURI()
					.getPath());
			return new File((new FileAccess()).getClass().getClassLoader().getResource(path).toURI().getPath());
		} catch (Exception e) {
			LogMes.logError(FileAccess.class, "imposible to access at:" + path, e);
		}
		return null;

		// try {
		// return (new
		// FileAccess()).getClass().getResource(path).toURI().getPath();
		// } catch (Exception e) {
		// LogMes.logError(FileAccess.class, "imposible to acces at:"+path, e);
		// }
		// return null;
	}

	/**
	 * 
	 * @param path
	 * @return
	 */
	public static InputStream getResourceFileStream(String path) {
		// the priority is for the fileSystem
		if (path == null) {
			LogMes.log(FileAccess.class, LogMes.ERROR, "path is null, it's impossible to find it");
			return null;
		}
		File f = new File(path);

		if (f.exists()) {
			try {
				return new FileInputStream(f);
			} catch (FileNotFoundException e) {
				LogMes.logError(FileAccess.class, "problem during access at:" + path, e);
			}
		}

		// ellse we look in jar
		try {
			System.out.println((new FileAccess())
					.getClass()
					.getClassLoader()
					.getResource(path)
					.toURI()
					.getPath());
			return (new FileAccess()).getClass().getClassLoader().getResourceAsStream(path);
		} catch (Exception e) {
			LogMes.logError(FileAccess.class, "problem during access at:" + path, e);
		}
		return null;
	}
	
	public static void createOrInitializeDirectory(File dir){
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		else 
		{
			emptyDirectory(dir);
		}
	}
	
	public static void createOrInitializeDirectory(String dirPath){
		File dir=new File(dirPath);
		if(!dir.exists())
		{
			dir.mkdirs();
		}
		else 
		{
			emptyDirectory(dir);
		}
	}
	
	
	public static void createDirectory(File dir){
		if(!dir.exists())
		{
			dir.mkdirs();
		}

	}
	
	public static void emptyDirectory(File folder){
		folder.getAbsolutePath();
		   for(File file : folder.listFiles()){
		      if(file.isDirectory()){
		          emptyDirectory(file);
		      }
		       file.delete();
		   }
		}
	
	public static void cleanDirectory(File dir) {
		if(dir!=null && dir.exists()){
			for(File file:dir.listFiles()){
				if(file.isDirectory()){
					cleanDirectory(file);
					file.delete();
				}
				else
					file.delete();
			}
		}
		
	}
}
