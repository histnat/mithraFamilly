package net.mithra.familly.db.dao.execution.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.mithra.familly.db.dao.OpRoleDao;
import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.Conversion;
import net.mithra.familly.db.vo.execution.CoHistoryExe;
import net.mithra.familly.db.vo.user.FaRole;
import net.mithra.familly.db.vo.user.FaUser;
import net.mithra.familly.utils.TestDBHelper;

public class coHistoryExeDaoTest extends TestDBHelper {



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
		System.out.println("*** TEST coHistoryExeDao.save ***");
		CoHistoryExe coHistoryExe = getCoHistoryExeWithoutSave();
		coHistoryExeDao.save(coHistoryExe);
		assertNotNull(coHistoryExe.getId());
		CoHistoryExe result = coHistoryExeDao.find(coHistoryExe);
        assertNotNull(result);
        coHistoryExeDao.delete(coHistoryExe);
	}
	
	@Test
	public void test_add() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST coHistoryExeDao.add ***");
		CoHistoryExe coHistoryExe = getCoHistoryExeWithoutSave();
		assertNotNull(coHistoryExe);
		coHistoryExeDao.add(coHistoryExe);
		assertNotNull(coHistoryExe.getId());
		coHistoryExeDao.delete(coHistoryExe);
	}
	
	@Test
    public void testfindByUser() throws DBNONullException, DBNOUniqueException {
        System.out.println("*** TEST coHistoryExeDao.findByUser ***");
        CoHistoryExe coHistoryExe = getCoHistoryExeWithoutSave();
        FaUser opUser=getOpUserWithSave();
//        coHistoryExe.setOpUser(opUser);
        coHistoryExe.setUserId(opUser.getId());
        coHistoryExeDao.getOrSave(coHistoryExe);
        assertNotNull(coHistoryExe);
        assertNotNull(coHistoryExe.getId());
        CoHistoryExe coHistoryExeTest = coHistoryExeDao.findByUser(opUser);
        assertNotNull(coHistoryExeTest);
        assertEquals(coHistoryExeTest.getId(),coHistoryExe.getId());
        deleteAll();
    }

	/**
	 * test opUserDao.deleteAll.
	 * 
	 * @throws DBNOUniqueException
	 * @throws DBNONullException
	 *
	 * @throws DuplicateObjectException
	 * @throws UnexpectedDatabaseException
	 */
	@Test(dependsOnMethods = { "test_save" })
	public void test_deleteAll() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST coHistoryExeDao.deleteAll ***");

		getCoHistoryExeListWithSave(3);
		coHistoryExeDao.deleteAll(CoHistoryExe.class);

		List<CoHistoryExe> coHistoryExeTest = (List<CoHistoryExe>) coHistoryExeDao.findAll(CoHistoryExe.class);
		assertEquals(coHistoryExeTest.size(), 0);
	}

	/**
	 * test opUserDao.delete
	 * 
	 * @throws DBNOUniqueException
	 * @throws DBNONullException
	 *
	 * @throws DuplicateObjectException
	 * @throws UnexpectedDatabaseException
	 */
	@Test(dependsOnMethods = { "test_save", "test_deleteAll" })
	public void test_delete() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST coHistoryExeDao.delete ***");
		String coHistoryExeId;

		CoHistoryExe coHistoryExe = getCoHistoryExeWithSave();
		coHistoryExeId = coHistoryExe.getId();
		assertNotNull(coHistoryExeId);

		coHistoryExeDao.delete(coHistoryExe);

		CoHistoryExe result = coHistoryExeDao.find(coHistoryExe);
		assertNull(result);
	}

	/**
	 * test opUserDao.update
	 * 
	 * @throws DBNOUniqueException
	 * @throws DBNONullException
	 *
	 * @throws DuplicateObjectException
	 * @throws UnexpectedDatabaseException
	 */
	@Test(dependsOnMethods = { "test_save", "test_delete", "test_deleteAll" })
	public void test_Update() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST coHistoryExeDao.update ***");
		String coHistoryExeId;

		CoHistoryExe coHistoryExe = getCoHistoryExeWithSave();
		coHistoryExeId = coHistoryExe.getId();
		assertNotNull(coHistoryExeId);

		coHistoryExe.setNbrFiles(16);
		coHistoryExeDao.update(coHistoryExe);


		CoHistoryExe result = coHistoryExeDao.find(coHistoryExe);
        assertNotNull(result);
        assertEquals(result.getNbrFiles(),16);

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
		System.out.println("*** TEST coHistoryExeDao.find ***");
		String coHistoryExeId;
		
		CoHistoryExe coHistoryExe = getCoHistoryExeWithSave();
		coHistoryExeId = coHistoryExe.getId();
		assertNotNull(coHistoryExeId);
		
		CoHistoryExe result = coHistoryExeDao.find(coHistoryExe);
        assertNotNull(result);    
        assertEquals(result.getNbrFiles(), coHistoryExe.getNbrFiles());
        assertEquals(result.getNbrNBloquant(), coHistoryExe.getNbrNBloquant());
        assertEquals(result.getNbrOK(), coHistoryExe.getNbrOK());
        assertEquals(result.getNbrNOK(), coHistoryExe.getNbrNOK());
        assertEquals(result.getStartTime(), coHistoryExe.getStartTime());
        assertEquals(result.getEndTime(), coHistoryExe.getEndTime());
	}
	
	


	/**
	 * test opUserDao.findById
	 * 
	 * @throws DBNOUniqueException
	 * @throws DBNONullException
	 *
	 * @throws DuplicateObjectException
	 * @throws UnexpectedDatabaseException
	 */
	@Test(dependsOnMethods = { "test_save", "test_delete", "test_deleteAll" })
	public void test_findByAll() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST coHistoryExeDao.findByAll ***");

		coHistoryExeDao.deleteAll();

		CoHistoryExe coHistoryExe = getCoHistoryExeWithoutSave();
		coHistoryExeDao.save(coHistoryExe);
		assertNotNull(coHistoryExe.getId()); 
		
        List<CoHistoryExe> coHistoryExeList = (List<CoHistoryExe>) coHistoryExeDao.findAll(CoHistoryExe.class);
        assertEquals(coHistoryExeList.size(), 1);
		

		CoHistoryExe coHistoryExeListResult =coHistoryExeDao.findByAll(coHistoryExe.getNbrFiles(), coHistoryExe.getNbrOK(), coHistoryExe.getNbrNOK(), coHistoryExe.getNbrNBloquant(), coHistoryExe.getStartTime(), coHistoryExe.getEndTime());
		assertNotNull(coHistoryExeListResult.getId());

		coHistoryExeList = (List<CoHistoryExe>) coHistoryExeDao.findAll(CoHistoryExe.class);
        assertEquals(coHistoryExeList.size(), 1);

	}



}
