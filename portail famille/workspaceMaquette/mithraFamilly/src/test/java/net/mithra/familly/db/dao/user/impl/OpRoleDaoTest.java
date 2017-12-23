package net.mithra.familly.db.dao.user.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.mithra.familly.db.dao.OpRoleDao;
import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.user.FaRole;
import net.mithra.familly.utils.TestDBHelper;

public class OpRoleDaoTest extends TestDBHelper {

	@Autowired
	OpRoleDao opRoleDao;

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
		System.out.println("*** TEST opRoleDao.save ***");
		FaRole opRole = getOpRoleWithoutSave();
		opRoleDao.save(opRole);
		assertNotNull(opRole.getId());
		FaRole result = opRoleDao.find(opRole);
        assertNotNull(result);
        opRoleDao.delete(opRole);
	}
	
	@Test
	public void test_add() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST opRoleDao.add ***");
		FaRole opRole = getOpRoleWithoutSave();
		assertNotNull(opRole);
		opRoleDao.add(opRole);
		assertNotNull(opRole.getId());
	}
	
	@Test
    public void testfindByCode() throws DBNONullException, DBNOUniqueException {
        System.out.println("*** TEST opRoleDao.findByCode ***");
        FaRole opRole = getOpRoleWithSave();
        assertNotNull(opRole);
        assertNotNull(opRole.getId());
        FaRole opRoleTest = opRoleDao.findByCode(opRole.getCode());
        assertNotNull(opRoleTest);
        assertEquals(opRoleTest.getCode(),opRole.getCode());
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
		System.out.println("*** TEST opRoleDao.deleteAll ***");

		getOpRoleWithSave(3);
		opRoleDao.deleteAll(FaRole.class);

		List<FaRole> opRoleTest = (List<FaRole>) opRoleDao.findAll(FaRole.class);
		assertEquals(opRoleTest.size(), 0);
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
		String opRoleId;

		FaRole opRole = getOpRoleWithSave();
		opRoleId = opRole.getId();
		assertNotNull(opRoleId);

		opRoleDao.delete(opRole);

		FaRole result = opRoleDao.find(opRole);
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
		System.out.println("*** TEST opRoleDao.update ***");
		String opRoleId;

		FaRole opRole = getOpRoleWithSave();
		opRoleId = opRole.getId();
		assertNotNull(opRoleId);

		opRole.setCode("test");
		opRoleDao.update(opRole);


		FaRole result = opRoleDao.find(opRole);
        assertNotNull(result);
        assertEquals(result.getCode(),"test");

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
		System.out.println("*** TEST opRoleDao.find ***");
		String opRoleId;
		
		FaRole opRole = getOpRoleWithSave();
		opRoleId = opRole.getId();
		assertNotNull(opRoleId);
		
		FaRole result = opRoleDao.find(opRole);
        assertNotNull(result);    
        assertEquals(result.getCode(), opRole.getCode());
        assertEquals(result.getDescrs().size(), opRole.getDescrs().size());
        assertEquals(2, opRole.getDescrs().size());
        assertEquals(result.getName().size(), opRole.getName().size());
        assertEquals(2, opRole.getName().size());

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
		System.out.println("*** TEST opRoleDao.findById ***");

		opRoleDao.deleteAll(FaRole.class);

		List<FaRole> opRole = getOpRoleWithSave(2);

		List<FaRole> opRoleList = (List<FaRole>) opRoleDao.findAll(FaRole.class);
		assertEquals(opRoleList.size(), opRole.size());

		FaRole opRoleTest = opRoleDao.findOne(opRole.get(0).getId(),FaRole.class);// langue2.getScDmCodeId());
		assertNotNull(opRoleTest.getId());
		assertEquals(opRoleTest.getId(), opRole.get(0).getId());

	}



}
