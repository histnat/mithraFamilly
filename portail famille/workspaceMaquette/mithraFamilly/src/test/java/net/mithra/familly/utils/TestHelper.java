package net.mithra.familly.utils;

import static org.testng.Assert.fail;

//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;

import com.sedoc.toolbox.SpringBeanProvider;
import net.mithra.familly.db.bo.DBConversionService;
import net.mithra.familly.db.bo.DBUserService;
import net.mithra.familly.db.dao.ConversionDao;
import net.mithra.familly.db.dao.TaskDao;
import net.mithra.familly.db.vo.Conversion;
import net.mithra.familly.db.vo.Task;
import net.mithra.familly.log.model.DBLogger;
import net.mithra.familly.ws.config.AppConfigTest;

@WebAppConfiguration
//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={AppConfigTest.class})
public abstract class TestHelper extends AbstractTestNGSpringContextTests {
	
	public static String IDLANGUE_FR="FR_fr";
	public static String IDLANGUE_EN="EN_us";
	
	@Autowired
	protected ConversionDao conversionDao;
	
	@Autowired
	protected TaskDao taskDao;
	
	@Autowired
	protected DBConversionService dbConversionService;
	
	@Autowired
	protected DBUserService dbUserService;

	@Autowired
	protected ApplicationContext appContext;
	
	protected String getFilePath(String path) {
		try {
			return this.getClass().getResource(path).toURI().getPath();
		} catch (Exception e) {
			fail("load impossible of file "+path+" :"+ e.getMessage());
		}
		return null;
	}
	
	protected DBLogger getDBLoggerTask(Conversion conversion, Task task)
	{
		SpringBeanProvider.setApplicationContext2(appContext);
		DBLogger dbLogger=new DBLogger();
		dbLogger.initClass();
		dbLogger.setConversion(conversion);
		dbLogger.setTask(task);
		return dbLogger;
	}
	
	protected DBLogger getDBLogger(Conversion conversion)
	{
		DBLogger dbLogger=new DBLogger();
		dbLogger.setConversion(conversion);
		return dbLogger;
	}
	
	

	public DBUserService getDbUserService() {
		return dbUserService;
	}

	public void setDbUserService(DBUserService dbUserService) {
		this.dbUserService = dbUserService;
	}

	/**
	 * @return the conversionDao
	 */
	public ConversionDao getConversionDao() {
		return conversionDao;
	}

	/**
	 * @param conversionDao the conversionDao to set
	 */
	public void setConversionDao(ConversionDao conversionDao) {
		this.conversionDao = conversionDao;
	}
	
	/**
	 * @return the taskDao
	 */
	public TaskDao getTaskDao() {
		return taskDao;
	}


	/**
	 * @param taskDao the taskDao to set
	 */
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}
	

}
