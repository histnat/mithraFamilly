package net.mithra.familly.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import net.mithra.familly.service.FileService;
import net.mithra.familly.utils.FileAccess;

@PropertySource("classpath:openconv.properties")
@Service("FileService")
public class FileServiceImpl implements FileService{

	@Value("#{propertiesFile['folder.temp']}")
	protected String folderTemp;
	
	@Value("#{propertiesFile['folder.base']}")
	protected String folderBase;

	
	 /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;
	private final static String DEFAULT_LOGPDF_FILENAME = "outputPDF.pdf";
	private final static String FOLDER_LOG = "log";
	private final static String FOLDER_IN = "in";
	private final static String FOLDER_OUT = "out";
	private final static String FOLDER_RESULT = "result";
	private final static String FILE_RESULT = "result";
	private final static String FILE_TO_UNZIP = "toUnzip";
	
	
	/*
	 * 
	 * 
	 */
	
	/*
	 * arborescence des dossiers :
	 * 
	 * + folderBase	/	{idExe} / FOLDER_OUT 	/	FOLDER_RESULT	/ outXmlFiles
	 * 											/	FOLDER_LOG	/ DEFAULT_LOGPDF_FILENAME.pdf
	 * 							/ FOLDER_IN		/	inXmlfiles
	 * 
	 * + folderTemp / zipFiles
	 * 
	 */
	
	
	/*
	 * folderBase	/{idExe}  /
	 */
	private String getBaseDirPath(String idExe) {
		return folderBase+File.separator+idExe+File.separator;
	}

	
	/*
	 * folderBase	/{idExe}  / FOLDER_OUT 
	 */
	@Override
	public String getOutDirPath(String idExe) {
		return getBaseDirPath(idExe)+FOLDER_OUT;
	}
	
	/*
	 * folderBase	/{idExe}  / FOLDER_OUT /FOLDER_RESULT	
	 */
	@Override
	public String getOutResultDirPath(String idExe) {
		return pathAppendResult(getOutDirPath(idExe));
	}
	/*
	 * folderBase	/{idExe} / FOLDER_OUT /	FOLDER_LOG	
	 */
	@Override
	public String getOutLogDirPath(String idExe) {
		return pathAppendLog(getOutDirPath(idExe));
	}
	
	/*
	 * folderBase	/{idExe}  / FOLDER_IN 
	 */
	@Override
	public String getInDirPath(String idExe) {
		return getBaseDirPath(idExe)+FOLDER_IN;
	}

	/*
	 * folderBase	/{idExe} / FOLDER_OUT /	FOLDER_LOG	/ DEFAULT_LOGPDF_FILENAME.pdf
	 */	
	@Override
	public String getLogPdfFilePath(String idExe) {
		return getOutLogDirPath(idExe)+File.separator+DEFAULT_LOGPDF_FILENAME;
	}
	
	/*
	 * folderTemp /
	 */
	@Override
	public String getTempDirPath() {
		return folderTemp;
	}
	
	/*
	 *  folderTemp / {name}.zip
	 */
	@Override
	public String getTempZipFilePath(String name) {
		return getTempDirPath()+File.separator+name+".zip";
	}

	/*
	 * zip :
	 * folderBase	/{idExe}  / FOLDER_OUT /FOLDER_RESULT	
	 * to: 
	 *  folderTemp / FILE_RESULT.zip
	 */
	@Override
	public File getResultZip(String idExe) throws IOException{
		return zip(new File(getOutResultDirPath(idExe)),getTempZipFilePath(FILE_RESULT));
	}
	
	/*
	 *  folderTemp / FILE_TO_UNZIP.zip
	 */
	@Override
	public String getToUnzipFilePath(){
		return getTempZipFilePath(FILE_TO_UNZIP);
	}
	
	
	/*
	 * unzip :
	 * inZipFilePath
	 * to: 
	 * folderBase	/{idExe}  / FOLDER_IN 
	 */
	@Override
	public void unzipToIdExe(String inZipFilePath, String idExe) throws IOException{
		unzip(inZipFilePath,getInDirPath(idExe));
	}

//	@Override
//	public File getDirectory(String dirPath){
//		File dir=new File(dirPath);
//		FileAccess.createDirectory(dir);
//		return dir;
//	}

	@Override
	public String getFolderTemp() {
		return folderTemp+File.separator;
	}

	@Override
	public String getFolderBase() {
		return folderBase+File.separator;
	}

	
	
	@Override
	public String pathAppendResult(String baseDir){
		return baseDir+File.separator+FOLDER_RESULT;
	}
	@Override
	public String pathAppendLog(String baseDir){
		return baseDir+File.separator+FOLDER_LOG;
	}
	
	@Override
	public File zip(File inDirectory, String outZipFilePath) throws IOException{
		byte[] buffer;
		FileInputStream in = null;
		int bytes_read;
		File tempFile=new File(outZipFilePath);
		if(!tempFile.exists()){
			tempFile.createNewFile();
		}
		if(!tempFile.isFile())
			return null;

		ZipOutputStream z = new ZipOutputStream(new FileOutputStream(tempFile));

		for(File currFile:inDirectory.listFiles()){
			ZipEntry zipEntry=new ZipEntry(currFile.getName());
			z.putNextEntry(zipEntry);
			buffer = new byte[Math.toIntExact(currFile.length())];
			in = new FileInputStream(currFile);
			while ((bytes_read = in.read(buffer)) != -1) {
				z.write(buffer, 0, bytes_read);
			}
			z.closeEntry();
		}
		z.close();
		return tempFile;
	}
	
	@Override
	public void unzip(String inZipFilePath, String outDirectory) throws IOException {
		File destDir = new File(outDirectory);
		FileAccess.createDirectory(destDir);

		ZipInputStream zipIn = new ZipInputStream(new FileInputStream(inZipFilePath));
		ZipEntry entry = zipIn.getNextEntry();
		// iterates over entries in the zip file
		while (entry != null) {
			String filePath = outDirectory + File.separator + entry.getName();
			if (!entry.isDirectory()) {
				// if the entry is a file, extracts it
				extractFile(zipIn, filePath);
			} else {
				// if the entry is a directory, make the directory
				File dir = new File(filePath);
				dir.mkdir();
			}
			zipIn.closeEntry();
			entry = zipIn.getNextEntry();
		}
		zipIn.close();
	}
	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
		byte[] bytesIn = new byte[BUFFER_SIZE];
		int read = 0;
		while ((read = zipIn.read(bytesIn)) != -1) {
			bos.write(bytesIn, 0, read);
		}
		bos.close();
	}
	

}
