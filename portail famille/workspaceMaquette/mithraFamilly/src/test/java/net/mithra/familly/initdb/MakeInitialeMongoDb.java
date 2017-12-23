/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mithra.familly.initdb;


//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.*;

import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.execution.CoHistoryExe;
import net.mithra.familly.db.vo.execution.CoScenario;
import net.mithra.familly.db.vo.user.FaUser;
import net.mithra.familly.ws.config.AppConfigTest;

import static org.testng.AssertJUnit.assertTrue;

import java.util.List;

/**
 * @author FRebeche
 */
//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigTest.class})
@WebAppConfiguration
public class MakeInitialeMongoDb extends AbstractTestNGSpringContextTests {


    @Autowired
    MakeUser makeUser;

    @Autowired
    MakeRole makeRole;
    
    @Autowired
    MakeCoScenario makeCoScenario;
    
    @Autowired
    MakeCoParameterExe makeCoParameterExe;
    
    @Autowired
    MakeCoHistoryExe makeCoHistoryExe;
    
    @Autowired
    MakeConversion makeConversion;
    
    @Autowired
    MakeTask makeTask;
    
    @Autowired
    MakeConversionObject makeConversionObject;
    
    @Autowired
    MakeLogLine makeLogLine;
    

    public MakeInitialeMongoDb() {
    	System.out.println("coco");
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    @Test()
    public void createBaseDeTest() throws DBNONullException, DBNOUniqueException {

        deleteAll();
        //user/group/role
        makeRole.make();
        FaUser user;
        
        String names[]={"lee","user"};

        
        for(String name:names){
         user=makeUser.make(name);
        //Scenerio/History/parameter
        List<CoScenario> scenarioList=makeCoScenario.make(user);
        user.setScenarioList(scenarioList);
        for(CoScenario scen :scenarioList){
        	for(int i=0;i<3;i++){
        	List<CoHistoryExe> historyList=makeCoHistoryExe.make(user,scen,"exec"+i);
        	for(CoHistoryExe hist:historyList){
        		 makeCoParameterExe.make(hist);
        	}
        	}
        }       
        }
        
        makeConversion.make();
        makeTask.make();
        makeConversionObject.make();
        makeLogLine.make();
        assertTrue(true);

    }

    private void deleteAll() {
    	makeRole.clean();
        makeUser.clean();
        makeCoScenario.clean();
        makeCoParameterExe.clean();
        makeCoHistoryExe.clean();
        makeConversion.clean();
        makeTask.clean();
        makeConversionObject.clean();
        makeLogLine.clean();
    }



    public MakeUser getMakeUser() {
        return makeUser;
    }

    public void setMakeUser(MakeUser makeUser) {
        this.makeUser = makeUser;
    }

    public MakeRole getMakeRole() {
        return makeRole;
    }

    public void setMakeRole(MakeRole makeRole) {
        this.makeRole = makeRole;
    }
}
