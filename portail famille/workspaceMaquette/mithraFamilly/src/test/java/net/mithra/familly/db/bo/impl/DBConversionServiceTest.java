package net.mithra.familly.db.bo.impl;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.mithra.familly.db.bo.DBConversionService;
import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.Conversion;
import net.mithra.familly.db.vo.LogLine;
import net.mithra.familly.db.vo.Task;
import net.mithra.familly.utils.TestDBHelper;


public class DBConversionServiceTest extends TestDBHelper{
	
	@Autowired
	DBConversionService dbConversionService;

	@AfterMethod
    public void setAfterMethod(Method method) throws Exception {
    	deleteAll();
    }

    @BeforeMethod
    public void setUpMethod(Method method) throws Exception {
    	deleteAll();
    }
    
    @Test
    public void test_saveLogLine() throws DBNONullException, DBNOUniqueException {
        System.out.println("*** TEST conversionDao.save(LogLine) ***");
        LogLine logLine = getLogLineWithoutSave();
        logLine.setLevel(dbConversionService.getLogLevelWriter()+1);
        logLine = dbConversionService.save(logLine);
        assertNull(logLine.getId());
        logLine.setLevel(dbConversionService.getLogLevelWriter());
        logLine = dbConversionService.save(logLine);
        assertNotNull(logLine.getId());
    }
    
    @Test
    public void test_saveTask() throws DBNONullException, DBNOUniqueException {
        System.out.println("*** TEST conversionDao.save(Task) ***");
        Task task = getTaskWithoutSave();
        taskDao.save(task);
        assertNotNull(task.getId());
    }
    
    @Test
    public void test_getConversion1() throws DBNONullException, DBNOUniqueException  {
        System.out.println("*** TEST DBConversionServic.getConversion(String folderIn, String folderOut, String workFlowName, String workFlowSequence) ***");
        Conversion conversion = getConversionWithoutSave();
        
        Conversion conversionTest = dbConversionService.getConversion(conversion.getFolderIn(),conversion.getFolderOut(), conversion.getWorkFlowName(), conversion.getWorkFlowSequence());
        assertNotNull(conversionTest);
        assertNotNull(conversionTest.getStartTime());
        assertEquals(conversionTest.getFolderIn(),conversion.getFolderIn());
        assertEquals(conversionTest.getFolderOut(),conversion.getFolderOut());
        assertEquals(conversionTest.getWorkFlowName(),conversion.getWorkFlowName());
        assertEquals(conversionTest.getWorkFlowSequence(),conversion.getWorkFlowSequence());
    }
    
    @Test(enabled=false)
    public void test_getConversion2()  {
        System.out.println("*** TEST DBConversionServic.getConversion(String workFlowName,String workFlowSequence) ***");
        fail("not implemented");
    }
    
    @Test
    public void test_getConversion3() throws DBNONullException, DBNOUniqueException  {
        System.out.println("*** TEST DBConversionServic.getConversion(Integer id) ***");
        Conversion conversion = getConversionWithSave();
        assertNotNull(conversion.getId());
        Conversion conversionR = dbConversionService.getConversion(conversion.getId());
        
        assertNotNull(conversionR);
        assertEquals(conversion.getId(), conversionR.getId());
        assertEquals(conversion.getFolderIn(),conversionR.getFolderIn());
        assertEquals(conversion.getFolderOut(),conversionR.getFolderOut());
        assertEquals(conversion.getWorkFlowName(),conversionR.getWorkFlowName());
        assertEquals(conversion.getWorkFlowSequence(),conversionR.getWorkFlowSequence());
        
        
    }
   
    
    @Test
    public void test_isAlsoRunning() throws DBNONullException, DBNOUniqueException{
        System.out.println("*** TEST DBConversionServic.isAlsoRunning(String folderIn, String folderOut, String workFlowName, String workFlowSequence) ***");
        Conversion conversion = getConversionWithoutSave();
        if(conversion.getStopTime()!=null)
        	conversion.setStopTime(null);
        conversionDao.save(conversion);
        assertNotNull(conversion.getId());
        
        assertTrue(dbConversionService.isAlsoRunning(conversion.getFolderIn(), conversion.getFolderOut(), conversion.getWorkFlowName(),conversion.getWorkFlowSequence()));
        
        dbConversionService.stopConversion(conversion);
        assertFalse(dbConversionService.isAlsoRunning(conversion.getFolderIn(), conversion.getFolderOut(), conversion.getWorkFlowName(),conversion.getWorkFlowSequence()));
    }
    
    @Test
    public void test_stopConversion() throws DBNONullException, DBNOUniqueException{
        System.out.println("*** TEST DBConversionServic.stopConversion(Conversion conversion) ***");
        Conversion conversion = getConversionWithoutSave();
        if(conversion.getStopTime()!=null)
        	conversion.setStopTime(null);
        conversionDao.save(conversion);
        assertNotNull(conversion.getId());
        
        conversion = dbConversionService.stopConversion(conversion);
        assertNotNull(conversion.getStopTime());
        
        conversion = dbConversionService.getConversion(conversion.getId());
        assertNotNull(conversion.getStopTime());
        
    }

	/**
	 * @return the dbConversionService
	 */
	public DBConversionService getDbConversionService() {
		return dbConversionService;
	}

	/**
	 * @param dbConversionService the dbConversionService to set
	 */
	public void setDbConversionService(DBConversionService dbConversionService) {
		this.dbConversionService = dbConversionService;
	}
   

    

    
}
