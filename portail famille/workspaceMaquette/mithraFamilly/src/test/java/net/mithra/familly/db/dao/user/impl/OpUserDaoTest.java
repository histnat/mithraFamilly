package net.mithra.familly.db.dao.user.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.mithra.familly.db.dao.OpUserDao;
import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.user.FaUser;
import net.mithra.familly.utils.TestDBHelper;

public class OpUserDaoTest extends TestDBHelper {

	@Autowired
	OpUserDao opUserDao;

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
		System.out.println("*** TEST opUserDao.save ***");
		FaUser opUser = getOpUserWithoutSave();
		opUserDao.save(opUser);
		assertNotNull(opUser.getId());
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
		System.out.println("*** TEST opUserDao.deleteAll ***");

		getOpUserWithSave(3);
		opUserDao.deleteAll();

		List<FaUser> opUserTest = (List<FaUser>) opUserDao.findAll();
		assertEquals(opUserTest.size(), 0);
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
		System.out.println("*** TEST opUserDao.delete ***");
		String opUserId;

		FaUser opUser = getOpUserWithSave();
		opUserId = opUser.getId();
		assertNotNull(opUserId);

		opUserDao.delete(opUser);

		opUser = opUserDao.findOne(opUserId);
		assertNull(opUser);
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
		System.out.println("*** TEST opUserDao.update ***");
		String opUserId;

		FaUser opUser = getOpUserWithSave();
		opUserId = opUser.getId();
		assertNotNull(opUserId);

		opUser.setEmail("dddd");
		opUserDao.save(opUser);

		FaUser opUserTest = new FaUser();
		opUserTest = opUserDao.findOne(opUserId);
		assertEquals(opUserTest.getEmail(), "dddd");

	}

	/**
	 * test opUserDao.get
	 * 
	 * @throws DBNOUniqueException
	 * @throws DBNONullException
	 *
	 * @throws DuplicateObjectException
	 * @throws UnexpectedDatabaseException
	 */
	@Test(dependsOnMethods = { "test_save", "test_delete", "test_deleteAll" })
	public void test_get() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST opUserDao.get ***");
		String opUserId;

		FaUser opUser = getOpUserWithSave();
		opUserId = opUser.getId();
		assertNotNull(opUserId);
		String str = opUser.getEmail();

		opUser = getOpUserWithoutSave(2);
		opUserDao.save(opUser);
		assertNotNull(opUser.getId());

		FaUser opUserTest = new FaUser();
		opUserTest = opUserDao.findOne(opUserId);
		assertNotNull(opUserTest.getId());
		assertEquals(opUserTest.getEmail(), str);

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
	@Test(dependsOnMethods = { "test_save", "test_delete", "test_deleteAll" })
	public void test_findAll() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST opUserDao.findAll ***");

		opUserDao.deleteAll();

		FaUser opUser = getOpUserWithSave();
		assertNotNull(opUser.getId());

		List<FaUser> result = (List<FaUser>) opUserDao.findAll();
		assertEquals(result.size(), 1);

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
	public void test_findById() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST opUserDao.findById ***");

		opUserDao.deleteAll();

		List<FaUser> opUser = getOpUserWithSave(2);

		List<FaUser> opUserList = (List<FaUser>) opUserDao.findAll();
		assertEquals(opUserList.size(), opUser.size());

		FaUser opUserTest = opUserDao.findOne(opUser.get(0).getId());// langue2.getScDmCodeId());
		assertNotNull(opUserTest.getId());
		assertEquals(opUserTest.getId(), opUser.get(0).getId());

	}

	/**
	 * test opUserDao.findByAll
	 * 
	 * @throws DBNOUniqueException
	 * @throws DBNONullException
	 *
	 * @throws DuplicateObjectException
	 * @throws UnexpectedDatabaseException
	 */
	@Test(dependsOnMethods = { "test_save", "test_delete", "test_deleteAll" })
	public void test_findByAll() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST opUserDao.findByAll ***");

		opUserDao.deleteAll();

		FaUser opUser = getOpUserWithoutSave();
		opUserDao.save(opUser);
		assertNotNull(opUser.getId());

		List<FaUser> opUserList = (List<FaUser>) opUserDao.findAll();
		assertEquals(opUserList.size(), 1);

		FaUser opUserTest = dbUserService.findByAll(opUser);
		assertNotNull(opUserTest.getId());

		opUserList = (List<FaUser>) opUserDao.findAll();
		assertEquals(opUserList.size(), 1);

	}

}
