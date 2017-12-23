package net.mithra.familly.db.dao.execution.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.mithra.familly.db.dao.CoParameterExeDao;
import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.execution.CoParameterExe;
import net.mithra.familly.db.vo.execution.CoParameterExe;
import net.mithra.familly.db.vo.user.FaUser;
import net.mithra.familly.db.vo.execution.CoParameterExe;
import net.mithra.familly.utils.TestDBHelper;

public class CoParameterExeDaoTest extends TestDBHelper {


	@AfterMethod
	public void setAfterMethod(Method method) throws Exception {
		deleteAll();
	}

	@BeforeMethod
	public void setUpMethod(Method method) throws Exception {
		deleteAll();
	}

	@Test
	public void test_save() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST coParameterExeDao.save ***");
		CoParameterExe coParameterExe = getCoParameterExeWithoutSave();
		coParameterExeDao.save(coParameterExe);
		assertNotNull(coParameterExe.getId());
		CoParameterExe result = coParameterExeDao.find(coParameterExe);
        assertNotNull(result);
        coParameterExeDao.delete(coParameterExe);
	}
	
	
	@Test
	public void test_add() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST coParameterExeDao.add ***");
		CoParameterExe coParameterExe = getCoParameterExeWithoutSave();
		assertNotNull(coParameterExe);
		coParameterExeDao.add(coParameterExe);
		assertNotNull(coParameterExe.getId());
		coParameterExeDao.delete(coParameterExe);
	}
	
	@Test
    public void testfindByTaskId() throws DBNONullException, DBNOUniqueException {
        System.out.println("*** TEST coParameterExeDao.findByTaskId ***");
        CoParameterExe coParameterExe = getCoParameterExeWithoutSave();
        coParameterExe.setTaskId("myTaskId");
        coParameterExeDao.getOrSave(coParameterExe);
        assertNotNull(coParameterExe);
        assertNotNull(coParameterExe.getId());
        CoParameterExe coParameterExeTest = coParameterExeDao.findByTaskId("myTaskId");
        assertNotNull(coParameterExeTest);
        assertEquals(coParameterExeTest.getId(),coParameterExe.getId());
        deleteAll();
    }

	/**
	 * test coParameterExeDao.deleteAll.
	 * 
	 * @throws DBNOUniqueException
	 * @throws DBNONullException
	 *
	 * @throws DuplicateObjectException
	 * @throws UnexpectedDatabaseException
	 */
	@Test(dependsOnMethods = { "test_save" })
	public void test_deleteAll() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST coParameterExeDao.deleteAll ***");

		getCoParameterExeListWithSave(3);
		coParameterExeDao.deleteAll();

		List<CoParameterExe> coParameterExeTest = (List<CoParameterExe>) coParameterExeDao.findAll(CoParameterExe.class);
		assertEquals(coParameterExeTest.size(), 0);
	}

	/**
	 * test coParameterExeDao.delete
	 * 
	 * @throws DBNOUniqueException
	 * @throws DBNONullException
	 *
	 * @throws DuplicateObjectException
	 * @throws UnexpectedDatabaseException
	 */
	@Test(dependsOnMethods = { "test_save", "test_deleteAll" })
	public void test_delete() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST coParameterExeDao.delete ***");
		String coParameterExeId;

		CoParameterExe coParameterExe = getCoParameterExeWithSave();
		coParameterExeId = coParameterExe.getId();
		assertNotNull(coParameterExeId);

		coParameterExeDao.delete(coParameterExe);

		coParameterExe = coParameterExeDao.find(coParameterExe);
		assertNull(coParameterExe);
	}

	/**
	 * test coParameterExeDao.update
	 * 
	 * @throws DBNOUniqueException
	 * @throws DBNONullException
	 *
	 * @throws DuplicateObjectException
	 * @throws UnexpectedDatabaseException
	 */
	@Test(dependsOnMethods = { "test_save", "test_delete", "test_deleteAll" })
	public void test_Update() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST coParameterExeDao.update ***");
		String coParameterExeId;

		CoParameterExe coParameterExe = getCoParameterExeWithSave();
		coParameterExeId = coParameterExe.getId();
		assertNotNull(coParameterExeId);

		coParameterExe.setParamName("myParamValue");
		coParameterExeDao.save(coParameterExe);

		CoParameterExe coParameterExeTest = new CoParameterExe();
		coParameterExeTest = coParameterExeDao.find(coParameterExe);
		assertEquals(coParameterExeTest.getParamName(), "myParamValue");

	}



	/**
	 * test opUserDao.findAll
	 * 
	 * @throws DBNOUniqueException
	 * @throws DBNONullException
	 *
	 * @throws DuplicateObjectException
	 * @throws UnexpectedDatabaseException
	 */
	@Test(dependsOnMethods = { "test_add"})
	public void test_find() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST coParameterExeDao.find ***");
		String coParameterExeId;
		
		CoParameterExe coParameterExe = getCoParameterExeWithSave();
		coParameterExeId = coParameterExe.getId();
		assertNotNull(coParameterExeId);
		
		CoParameterExe result = coParameterExeDao.find(coParameterExe);
        assertNotNull(result);    
        assertEquals(result.getParamName(), coParameterExe.getParamName());
        assertEquals(result.getParamValue(), coParameterExe.getParamValue());
        assertEquals(result.getTaskId(), coParameterExe.getTaskId());

	}
	
	
	/**
	 * test coParameterExeDao.findByAll
	 * 
	 * @throws DBNOUniqueException
	 * @throws DBNONullException
	 *
	 * @throws DuplicateObjectException
	 * @throws UnexpectedDatabaseException
	 */
	@Test(dependsOnMethods = { "test_save", "test_delete", "test_deleteAll" })
	public void test_findByAll() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST coParameterExeDao.findByAll ***");

		coParameterExeDao.deleteAll();

		CoParameterExe coParameterExe = getCoParameterExeWithoutSave();
		coParameterExeDao.save(coParameterExe);
		assertNotNull(coParameterExe.getId());

		List<CoParameterExe> coParameterExeList = (List<CoParameterExe>) coParameterExeDao.findAll(CoParameterExe.class);
		assertEquals(coParameterExeList.size(), 1);

		CoParameterExe coParameterExeTest = coParameterExeDao.findByAll(coParameterExe.getId(),coParameterExe.getParamName(),coParameterExe.getParamValue(),coParameterExe.getTaskId());
		assertNotNull(coParameterExeTest.getId());

		coParameterExeList = (List<CoParameterExe>) coParameterExeDao.findAll(CoParameterExe.class);
		assertEquals(coParameterExeList.size(), 1);

	}
	

	
	

}
