package net.mithra.familly.service.impl;

import static org.junit.Assert.assertNotEquals;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.service.FileService;
import net.mithra.familly.utils.FileAccess;
import net.mithra.familly.utils.TestDBHelper;


public class FileServiceImplTest extends TestDBHelper{

	
    @BeforeMethod
    public void init() {
    	FileAccess.createOrInitializeDirectory(fileService.getTempDirPath());
   }
	
//
//  @Test
//  public void getClearedTempDir() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//  @Test
//  public void getDirectory() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//  @Test
//  public void getFolderBase() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//  @Test
//  public void getFolderTemp() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//  @Test
//  public void getInDirPath() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//  @Test
//  public void getLogPdfFilePath() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//
//  @Test
//  public void getOutResultDirPath() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//  @Test
//  public void getResultZip() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//  @Test
//  public void getTempDirPath() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//  @Test
//  public void getTempZipFilePath() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//  @Test
//  public void getToUnzipFilePath() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//  @Test
//  public void pathAppendLog() {
//    throw new RuntimeException("Test not implemented");
//  }
//
//  @Test
//  public void pathAppendResult() {
//    throw new RuntimeException("Test not implemented");
//  }

  @Test
  public void unzip() throws IOException {
	  File initialZip=new File("src/test/resources/in/in.zip");
	  fileService.unzip(initialZip.getAbsolutePath(), fileService.getTempDirPath());
	  File dirTemp=new File(fileService.getTempDirPath());
	  File []listFile=dirTemp.listFiles();
	  System.out.println(listFile[0].getAbsolutePath());
	  assertEquals(listFile.length,4);
  }
  
  @Test
  public void zip() throws IOException {
	  File reZippedFile=fileService.zip(new File("src/test/resources/in/unzipped"), fileService.getTempZipFilePath("in"));
	  assertEquals(reZippedFile.length(), 8054);

  }


}
