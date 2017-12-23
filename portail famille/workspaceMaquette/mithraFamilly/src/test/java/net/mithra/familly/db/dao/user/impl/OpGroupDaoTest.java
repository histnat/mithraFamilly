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

import net.mithra.familly.db.dao.OpGroupDao;

import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.user.FaGroup;
import net.mithra.familly.utils.TestDBHelper;

public class OpGroupDaoTest extends TestDBHelper {

	@Autowired
	OpGroupDao opGroupDao;

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
		System.out.println("*** TEST opGroupDao.save ***");
		FaGroup opGroup = getOpGroupWithoutSave();
		opGroupDao.save(opGroup);
		assertNotNull(opGroup.getId());
		FaGroup result = opGroupDao.find(opGroup);
        assertNotNull(result);
        opGroupDao.delete(opGroup);
	}
	
	@Test
	public void test_add() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST opGroupDao.add ***");
		FaGroup opGroup = getOpGroupWithoutSave();
		assertNotNull(opGroup);
		opGroupDao.add(opGroup);
		assertNotNull(opGroup.getId());
	}
	
	@Test
    public void testfindByCode() throws DBNONullException, DBNOUniqueException {
        System.out.println("*** TEST opGroupDao.findByCode ***");
        FaGroup opGroup = getOpGroupWithSave();
        assertNotNull(opGroup);
        assertNotNull(opGroup.getId());
        FaGroup opGroupTest = opGroupDao.findByCode(opGroup.getCode());
        assertNotNull(opGroupTest);
        assertEquals(opGroupTest.getCode(),opGroup.getCode());
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
		System.out.println("*** TEST opGroupDao.deleteAll ***");

		getOpGroupWithSave(3);
		opGroupDao.deleteAll(FaGroup.class);

		List<FaGroup> opGroupTest = (List<FaGroup>) opGroupDao.findAll(FaGroup.class);
		assertEquals(opGroupTest.size(), 0);
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
		String opGroupId;

		FaGroup opGroup = getOpGroupWithSave();
		opGroupId = opGroup.getId();
		assertNotNull(opGroupId);

		opGroupDao.delete(opGroup);

		FaGroup result = opGroupDao.find(opGroup);
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
		System.out.println("*** TEST opGroupDao.update ***");
		String opGroupId;

		FaGroup opGroup = getOpGroupWithSave();
		opGroupId = opGroup.getId();
		assertNotNull(opGroupId);

		opGroup.setCode("test");
		opGroupDao.update(opGroup);


		FaGroup result = opGroupDao.find(opGroup);
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
		System.out.println("*** TEST opGroupDao.find ***");
		String opGroupId;
		
		FaGroup opGroup = getOpGroupWithSave();
		opGroupId = opGroup.getId();
		assertNotNull(opGroupId);
		
		FaGroup result = opGroupDao.find(opGroup);
        assertNotNull(result);    
        assertEquals(result.getCode(), opGroup.getCode());
        assertEquals(result.getDescrs().size(), opGroup.getDescrs().size());
        assertEquals(2, opGroup.getDescrs().size());
        assertEquals(result.getName().size(), opGroup.getName().size());
        assertEquals(2, opGroup.getName().size());

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
		System.out.println("*** TEST opGroupDao.findById ***");

		opGroupDao.deleteAll(FaGroup.class);

		List<FaGroup> opGroup = getOpGroupWithSave(2);

		List<FaGroup> opGroupList = (List<FaGroup>) opGroupDao.findAll(FaGroup.class);
		assertEquals(opGroupList.size(), opGroup.size());

		FaGroup opGroupTest = opGroupDao.findOne(opGroup.get(0).getId(),FaGroup.class);// langue2.getScDmCodeId());
		assertNotNull(opGroupTest.getId());
		assertEquals(opGroupTest.getId(), opGroup.get(0).getId());

	}



}
