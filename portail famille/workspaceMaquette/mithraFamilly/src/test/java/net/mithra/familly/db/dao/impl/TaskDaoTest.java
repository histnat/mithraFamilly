package net.mithra.familly.db.dao.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.lang.reflect.Method;
import java.util.List;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.Task;
import net.mithra.familly.utils.TestDBHelper;

public class TaskDaoTest extends TestDBHelper{

	@AfterMethod
    public void setAfterMethod(Method method) throws Exception {
    	deleteAll();
    }

    @BeforeMethod
    public void setUpMethod(Method method) throws Exception {
    	deleteAll();
    }
    
    @Test
    public void test_save() throws DBNONullException, DBNOUniqueException  {
        System.out.println("*** TEST taskDao.save ***");

        Task task = getTaskWithoutSave();
        taskDao.save(task);
        assertNotNull(task.getId());
        
        
    }

    /**
     * test taskDao.deleteAll. 
     * @throws DBNOUniqueException 
     * @throws DBNONullException 
     *
     * @throws DuplicateObjectException
     * @throws UnexpectedDatabaseException
     */
    @Test(dependsOnMethods = {"test_save"})
    public void test_deleteAll() throws DBNONullException, DBNOUniqueException  {
        System.out.println("*** TEST taskDao.deleteAll ***");

        getTaskWithSave(3);

        taskDao.deleteAll();

        List<Task> taskTest = (List<Task>) taskDao.findAll();
        assertEquals(taskTest.size(), 0);
    }

    /**
     * test taskDao.delete
     * @throws DBNOUniqueException 
     * @throws DBNONullException 
     *
     * @throws DuplicateObjectException
     * @throws UnexpectedDatabaseException
     */
    @Test(dependsOnMethods = {"test_save", "test_deleteAll"})
    public void test_delete() throws DBNONullException, DBNOUniqueException  {
        System.out.println("*** TEST taskDao.delete ***");
        String taskId;

        Task task = getTaskWithSave();
        taskId = task.getId();
        assertNotNull(taskId);

        taskDao.delete(task);

        task = taskDao.findOne(taskId);
        assertNull(task);
    }

    /**
     * test taskDao.update
     * @throws DBNOUniqueException 
     * @throws DBNONullException 
     *
     * @throws DuplicateObjectException
     * @throws UnexpectedDatabaseException
     */
    @Test(dependsOnMethods = {"test_save", "test_delete", "test_deleteAll"})
    public void test_Update() throws DBNONullException, DBNOUniqueException  {
        System.out.println("*** TEST taskDao.update ***");
        String taskId;

        Task task = getTaskWithSave();
        taskId = task.getId();
        assertNotNull(taskId);

        task.setLogResult("TEST UPDATE");
        taskDao.save(task);

        Task taskTest = new Task();
        taskTest = taskDao.findOne( taskId);
        assertEquals(taskTest.getLogResult(), "TEST UPDATE");

    }

    /**
     * test taskDao.get
     * @throws DBNOUniqueException 
     * @throws DBNONullException 
     *
     * @throws DuplicateObjectException
     * @throws UnexpectedDatabaseException
     */
    @Test(dependsOnMethods = {"test_save", "test_delete", "test_deleteAll"})
    public void test_get() throws DBNONullException, DBNOUniqueException  {
        System.out.println("*** TEST taskDao.get ***");
        String taskId;

        Task task = getTaskWithSave();
        taskId = task.getId();
        assertNotNull(taskId);
        String str = task.getLogResult();
        
        task = getTaskWithoutSave(2);
        taskDao.save(task);
        assertNotNull(task.getId());

        Task taskTest = new Task();
        taskTest = taskDao.findOne( taskId);
        assertNotNull(taskTest.getId());
        assertEquals(taskTest.getLogResult(), str);


    }

    /**
     * test taskDao.findAll
     * @throws DBNOUniqueException 
     * @throws DBNONullException 
     *
     * @throws DuplicateObjectException
     * @throws UnexpectedDatabaseException
     */
    @Test(dependsOnMethods = {"test_save", "test_delete", "test_deleteAll"})
    public void test_findAll() throws DBNONullException, DBNOUniqueException  {
        System.out.println("*** TEST taskDao.findAll ***");

        taskDao.deleteAll();

        Task task = getTaskWithSave();
        assertNotNull(task.getId());

        List<Task> result = (List<Task>) taskDao.findAll();
        assertEquals(result.size(), 1);

    

    }

    /**
     * test taskDao.findOne
     * @throws DBNOUniqueException 
     * @throws DBNONullException 
     *
     * @throws DuplicateObjectException
     * @throws UnexpectedDatabaseException
     */
    @Test(dependsOnMethods = {"test_save", "test_delete", "test_deleteAll"})
    public void test_findById() throws DBNONullException, DBNOUniqueException  {
        System.out.println("*** TEST taskDao.findOne ***");

        taskDao.deleteAll();

        List<Task> task = getTaskWithSave(2);


        List<Task> taskList = (List<Task>) taskDao.findAll();
        assertEquals(taskList.size(), task.size());

        Task taskTest = taskDao.findOne(task.get(0).getId());//langue2.getScDmCodeId());
        assertNotNull(taskTest.getId());
        assertEquals(taskTest.getId(), task.get(0).getId());

        
    }

    /**
	 * test dbConversionService.getOrSave
	 * 
	 * @throws DBNOUniqueException
	 * @throws DBNONullException
	 *
	 * @throws DuplicateObjectException
	 * @throws UnexpectedDatabaseException
	 */
    @Test(dependsOnMethods = {"test_save", "test_delete", "test_deleteAll"})
    public void test_getOrSave() throws DBNONullException, DBNOUniqueException  {
        System.out.println("*** TEST dbConversionService.getOrSave ***");

        taskDao.deleteAll();
        
        Task task = getTaskWithoutSave();
        task = dbConversionService.getOrSave(task);
        assertNotNull(task.getId());        

        List<Task> taskList = (List<Task>) taskDao.findAll();
        assertEquals(taskList.size(), 1);

        Task taskTest = dbConversionService.getOrSave(task);
        assertNotNull(taskTest.getId());

        taskList = (List<Task>) taskDao.findAll();
        assertEquals(taskList.size(), 1);
        
        
    }
    
    /**
     * test dbConversionService.findByAll
     * @throws DBNOUniqueException 
     * @throws DBNONullException 
     *
     * @throws DuplicateObjectException
     * @throws UnexpectedDatabaseException
     */
    @Test(dependsOnMethods = {"test_save", "test_delete", "test_deleteAll"})
    public void test_findByAll() throws DBNONullException, DBNOUniqueException  {
        System.out.println("*** TEST dbConversionService.findByAll ***");

        taskDao.deleteAll();
        
        Task task = getTaskWithoutSave();
        task = dbConversionService.getOrSave(task);
        assertNotNull(task.getId());        

        List<Task> taskList = (List<Task>) taskDao.findAll();
        assertEquals(taskList.size(), 1);

        Task taskTest = dbConversionService.findByAll(task);
        assertNotNull(taskTest.getId());

        taskList = (List<Task>) taskDao.findAll();
        assertEquals(taskList.size(), 1);
        
        
    }
    
    /**
     * test dbConversionService.findByConversionActionId
     * @throws DBNOUniqueException 
     * @throws DBNONullException 
     *
     */
    @Test(dependsOnMethods = {"test_save", "test_delete", "test_deleteAll"})
    public void test_findByConversionActionId() throws DBNONullException, DBNOUniqueException  {
        System.out.println("*** TEST dbConversionService.findByConversionActionId ***");

        taskDao.deleteAll();
        
        Task task = getTaskWithSave();
        assertNotNull(task.getId());        

        Task taskResult = taskDao.findByConversionActionId(conversionDao.findOne(task.getConversionId()), task.getActionId());
        assertNotNull(taskResult);
        assertEquals(taskResult.getId(),task.getId());
        
        
    }
    

    
}
