package net.mithra.familly.service;

import java.io.File;
import java.io.IOException;

public interface FileService {

	
	String getOutLogDirPath(String idExe);
	String getOutResultDirPath(String idExe);
	String getLogPdfFilePath(String idExe);
	String getTempDirPath();
	String getTempZipFilePath(String name);
	File zip(File inDir, String outZipFilePath) throws IOException;
	File getResultZip(String idExe) throws IOException;
	void unzip(String zipFilePath, String destDirectory) throws IOException;
	String getInDirPath(String idExe);
	void unzipToIdExe(String inZipFilePath, String idExe) throws IOException;
	String getToUnzipFilePath();
//	File getDirectory(String dirPath);
	String getOutDirPath(String idExe);
	String pathAppendResult(String baseDir);
	String pathAppendLog(String baseDir);
	String getFolderTemp();
	String getFolderBase();


	
}
