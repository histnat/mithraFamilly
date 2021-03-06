package net.mithra.familly.db.bo.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

import java.lang.reflect.Method;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import net.mithra.familly.db.bo.DBExecutionService;
import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.execution.CoHistoryExe;
import net.mithra.familly.db.vo.execution.CoParameterExe;
import net.mithra.familly.db.vo.execution.CoScenario;
import net.mithra.familly.utils.TestDBHelper;

public class DBExecutionServiceTest extends TestDBHelper{


	@Autowired
	protected DBExecutionService dbExecutionService;

	@AfterMethod
    public void setAfterMethod(Method method) throws Exception {
    	deleteAll();
    }

    @BeforeMethod
    public void setUpMethod(Method method) throws Exception {
    	deleteAll();
    }

  @Test
  public void findByIdExe() {
      System.out.println("dbExecutionService.findByIdExe");
      CoHistoryExe coHistoryExe =null;
      try{
    	  coHistoryExe = getCoHistoryExeWithSave();
      }
      catch(Exception e){
          fail();
          }
      CoHistoryExe result = dbExecutionService.findByIdExe(coHistoryExe.getId());
      assertNotNull(result);
      assertEquals(result.getEndTime(), coHistoryExe.getEndTime());
      assertEquals(result.getStartTime(), coHistoryExe.getStartTime());
      assertEquals(result.getNbrFiles(), coHistoryExe.getNbrFiles());
      assertEquals(result.getNbrOK(), coHistoryExe.getNbrOK());  
  }

  @Test
  public void findUserByIdHist() throws DBNONullException, DBNOUniqueException {
      System.out.println("*** TEST historyService.findUserById ***");
      CoHistoryExe coHistoryExe = getCoHistoryExeWithoutSave();
//      OpUser opUser=getObject.getOpUser();
//      coHistoryExe.setOpUser(opUser);
      coHistoryExe.setUserId("userId");
      dbExecutionService.save(coHistoryExe);
      assertNotNull(coHistoryExe);
      assertNotNull(coHistoryExe.getId());
      CoHistoryExe coHistoryExeTest = dbExecutionService.findUserByIdHist("userId");
      assertNotNull(coHistoryExeTest);
      assertEquals(coHistoryExeTest.getId(),coHistoryExe.getId());
  }

  @Test
  public void findUserByIdScen() throws DBNONullException, DBNOUniqueException {
      System.out.println("*** TEST scenarioService.findUserById ***");
      CoScenario coScenario = getCoScenarioWithoutSave();
//      OpUser opUser=getObject.getOpUser();
//      coScenario.setOpUser(opUser);
      coScenario.setUserId("userId");
      dbExecutionService.save(coScenario);
      assertNotNull(coScenario);
      assertNotNull(coScenario.getId());
      CoScenario coScenarioTest = dbExecutionService.findUserByIdScen("userId");
      assertNotNull(coScenarioTest);
      assertEquals(coScenarioTest.getId(),coScenario.getId());
  }

  @Test
  public void get() throws DBNONullException, DBNOUniqueException {
	  CoHistoryExe coHistoryExe = getCoHistoryExeWithoutSave();
	  coHistoryExe.setScenarioId("SCENA1");
	  coHistoryExe.setId("IDEXE1");
	  dbExecutionService.save(coHistoryExe);
	  CoHistoryExe result=dbExecutionService.get("SCENA1", "IDEXE1");
	     assertNotNull(result);
  }

  @Test
  public void getOrSaveCoHistoryExe() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST getOrSaveCoHistoryExe ***");
		CoHistoryExe coHistoryExe = getCoHistoryExeWithoutSave();
		dbExecutionService.getOrSave(coHistoryExe);
		assertNotNull(coHistoryExe.getId());
		CoHistoryExe result = dbExecutionService.findByAll(coHistoryExe);
    assertNotNull(result);
  }

  @Test
  public void getOrSaveCoParameterExe() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST getOrSaveCoParameterExee ***");
		CoParameterExe coParameterExe = getCoParameterExeWithoutSave();
		dbExecutionService.getOrSave(coParameterExe);
		assertNotNull(coParameterExe.getId());
		CoParameterExe result = dbExecutionService.findByAll(coParameterExe);
    assertNotNull(result);
  }

  @Test
  public void getOrSaveCoScenario() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST getOrSaveCoScenario ***");
		CoScenario coScenario = getCoScenarioWithoutSave();
		dbExecutionService.getOrSave(coScenario);
		assertNotNull(coScenario.getId());
		CoScenario result = dbExecutionService.findByAll(coScenario);
    assertNotNull(result);
  }

  @Test
  public void listHistory() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST listHistory ***");
		getCoHistoryExeListWithSave(5);
			
	    List<CoHistoryExe> coHistoryExeList = (List<CoHistoryExe>) dbExecutionService.listHistory();
	    assertEquals(coHistoryExeList.size(), 5);
  }

  @Test
  public void listScenario() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST listScenario ***");
		getCoScenarioListWithSave(5);
			
	    List<CoScenario> coScenarioList =  dbExecutionService.listScenario();
	    assertEquals(coScenarioList.size(), 5);
  }

  @Test(enabled=false)
  public void loadWorkflow() {
    throw new RuntimeException("Test not implemented");
  }

  @Test
  public void saveCoHistoryExe() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST saveCoHistoryExe ***");
		CoHistoryExe coHistoryExe = getCoHistoryExeWithoutSave();
		dbExecutionService.save(coHistoryExe);
		assertNotNull(coHistoryExe.getId());
		CoHistoryExe result = dbExecutionService.findByAll(coHistoryExe);
    assertNotNull(result);
  }

  @Test
  public void saveCoParameterExe() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST saveCoParameterExe ***");
		CoParameterExe coParameterExe = getCoParameterExeWithoutSave();
		dbExecutionService.save(coParameterExe);
		assertNotNull(coParameterExe.getId());
		CoParameterExe result = dbExecutionService.findByAll(coParameterExe);
    assertNotNull(result);
  }

  @Test
  public void saveCoScenario() throws DBNONullException, DBNOUniqueException {
		System.out.println("*** TEST saveCoScenario ***");
		CoScenario coScenario = getCoScenarioWithoutSave();
		dbExecutionService.save(coScenario);
		assertNotNull(coScenario.getId());
		CoScenario result = dbExecutionService.findByAll(coScenario);
    assertNotNull(result);
  }
  
  @Test
  public void deleteScenario() throws DBNONullException, DBNOUniqueException{
	  System.out.println("*** TEST deleteScenario ***");
	  CoScenario coScenario = getCoScenarioWithSave();
	    List<CoScenario> coScenarioList =  dbExecutionService.listScenario();
	    assertEquals(coScenarioList.size(), 1);
	    dbExecutionService.deleteScenario(coScenario.getId());
	    List<CoScenario> coScenarioListResult =  dbExecutionService.listScenario();
	    assertEquals(coScenarioListResult.size(), 0);
	  
  }
  
}
