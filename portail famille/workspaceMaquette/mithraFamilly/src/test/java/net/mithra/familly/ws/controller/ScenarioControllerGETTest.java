package net.mithra.familly.ws.controller;

import java.util.List;
import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.Assert.assertEquals;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.sedoc.toolbox.SpringBeanProvider;
import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.execution.CoHistoryExe;
import net.mithra.familly.db.vo.execution.CoScenario;
import net.mithra.familly.db.vo.user.FaUser;
import net.mithra.familly.initdb.MakeCoHistoryExe;
import net.mithra.familly.initdb.MakeCoScenario;
import net.mithra.familly.initdb.MakeUser;
import net.mithra.familly.service.FileService;
import net.mithra.familly.utils.FileAccess;
import net.mithra.familly.utils.TestDBHelper;
import net.mithra.familly.utils.TestHelper;
import net.mithra.familly.wf.model.RunningStatus;
import net.mithra.familly.ws.config.AppConfigTest;


import org.apache.commons.lang3.NotImplementedException;
import  org.hamcrest.Matchers.*;
//import static org.hamcrest.CoreMatchers.*;

@WebAppConfiguration
@ContextConfiguration(classes = {AppConfigTest.class})
public class ScenarioControllerGETTest extends TestDBHelper {
    @Resource
    private WebApplicationContext wac;
    
    
    private MockMvc mockMvc;
    
    
    private List<CoHistoryExe> coHistoryList;
    private List<CoScenario> coScenarioList;
    private FaUser myUser;
    
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    @BeforeMethod
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    	SpringBeanProvider.setApplicationContext2(appContext);
        deleteAll();
//        FileAccess.cleanDirectory(new File(ScenarioControllerGET.TEMP_DIR));
        try {
        	coHistoryList=getCoHistoryExeListWithSave(5);
			coScenarioList=getCoScenarioListWithSave(6);
			myUser=getOpUserWithSave();
		} catch (DBNONullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBNOUniqueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
   }
    
    
    @Test
    public void provideScenarioList() throws Exception {
    	String requestPath="/rest/conv/scenario";
    	System.out.println("TEST : GET "+requestPath);
    	 mockMvc.perform(MockMvcRequestBuilders.get(requestPath).accept(MediaType.APPLICATION_JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
                 .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                 .andDo(print())
                 .andExpect(jsonPath("$",org.hamcrest.Matchers.hasSize(coScenarioList.size())))
                 .andExpect(jsonPath("$[0].id",is(coScenarioList.get(0).getId())))
                 .andExpect(jsonPath("$[0].name", is(coScenarioList.get(0).getName("Fr_fr"))))
                 .andExpect(jsonPath("$[1].id", is(coScenarioList.get(1).getId())))/////////////////Problème d'ordonnancement des test : il y a un test qui a fait un update sur le SCEN1, du coup, il se trouve à la fin de la liste dans la base...
                 .andExpect(jsonPath("$[1].name", is(coScenarioList.get(1).getName("Fr_fr"))));
    }
    
    
    
    @Test
    public void getScenario() throws Exception {
    	String requestPath="/rest/conv/scenario/"+coScenarioList.get(3).getId();
    	System.out.println("TEST : GET "+requestPath);
    	 mockMvc.perform(MockMvcRequestBuilders.get(requestPath).accept(MediaType.APPLICATION_JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
                 .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                 .andDo(print())
                 .andExpect(jsonPath("$.id",is(coScenarioList.get(3).getId())))
                 .andExpect(jsonPath("$.creatorName", is(coScenarioList.get(3).getUserId())))
                 .andExpect(jsonPath("$.name", is(coScenarioList.get(3).getName("Fr_fr"))))
//                 .andExpect(jsonPath("$.fileName", is(coScenarioList.get(3).getFileName())))
                 .andExpect(jsonPath("$.creationDate", is(coScenarioList.get(3).getCreationDate().getTime())))
//                 .andExpect(jsonPath("$.modificationdate", is(coScenarioList.get(3).getModificationdate().getTime())))
                 .andExpect(jsonPath("$.nbSteps", is(coScenarioList.get(3).getNbrStep())));
//                 .andExpect(jsonPath("$.shared", is((int)coScenarioList.get(3).getShared())))
//                 .andExpect(jsonPath("$.code", is(coScenarioList.get(3).getCode())));
    }
    
    
    @Test
    public void getResumeExec() throws Exception {
    	CoHistoryExe coHistoryExe=coHistoryList.get(1);
    	coHistoryExe.setScenarioId(coScenarioList.get(2).getId());
       	update(coHistoryExe);
    	String requestPath="/rest/conv/scenario/"+coHistoryExe.getScenarioId()+"/"+coHistoryExe.getId();
    	System.out.println("TEST : GET "+requestPath);
        mockMvc.perform(MockMvcRequestBuilders.get(requestPath).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                .andDo(print())
//                .andExpect(jsonPath("$",org.hamcrest.Matchers.hasSize(1)))
//                .andExpect(jsonPath("$[0].id",is(coHistoryList.get(2).getId())))
                .andExpect(jsonPath("$.userId", is(coHistoryExe.getUserId())))
//                .andExpect(jsonPath("$[0].scenarioId", is(coHistoryList.get(2).getScenarioId())))
//                .andExpect(jsonPath("$[0].executionId", is(coHistoryList.get(2).getExecutionId())))
                .andExpect(jsonPath("$.nbrFiles", is(coHistoryExe.getNbrFiles())))
                .andExpect(jsonPath("$.nbrOK", is(coHistoryExe.getNbrOK())))
                .andExpect(jsonPath("$.nbrNOK", is(coHistoryExe.getNbrNOK())))
                .andExpect(jsonPath("$.nbrNBloquant", is(coHistoryExe.getNbrNBloquant())))
                .andExpect(jsonPath("$.startTime", is(coHistoryExe.getStartTime().getTime())))
                .andExpect(jsonPath("$.endTime", is(coHistoryExe.getEndTime().getTime())));
    }
  

    
    @Test(enabled=false)
    public void getHistory() throws Exception{
    	String requestPath="/rest/conv/history";
    	System.out.println("TEST : GET "+requestPath);
        mockMvc.perform(MockMvcRequestBuilders.get(requestPath).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$",org.hamcrest.Matchers.hasSize(coHistoryList.size())))
                .andExpect(jsonPath("$[0].idExe",is(coHistoryList.get(0).getId())))
                .andExpect(jsonPath("$[0].userId", is(coHistoryList.get(0).getUserId())))
//                .andExpect(jsonPath("$[0].scenarioId", is(coHistoryList.get(0).getScenarioId())))
//                .andExpect(jsonPath("$[0].executionId", is(coHistoryList.get(0).getExecutionId())))
                .andExpect(jsonPath("$[0].nbrFiles", is(coHistoryList.get(0).getNbrFiles())))
                .andExpect(jsonPath("$[0].nbrOK", is(coHistoryList.get(0).getNbrOK())))
                .andExpect(jsonPath("$[0].nbrNOK", is(coHistoryList.get(0).getNbrNOK())))
                .andExpect(jsonPath("$[0].nbrNBloquant", is(coHistoryList.get(0).getNbrNBloquant())))
                .andExpect(jsonPath("$[0].startTime", is(coHistoryList.get(0).getStartTime().getTime())))
                .andExpect(jsonPath("$[0].endTime", is(coHistoryList.get(0).getEndTime().getTime())));

    }
    
  

  
    @Test
    public void getScenarioByUser() throws Exception {
    	String requestPath="/rest/conv/scenario";
    	System.out.println("TEST : GET "+requestPath+" "+myUser.getLogin());
    	coScenarioList.get(1).setUserId(myUser.getId());
    	coScenarioList.get(3).setUserId(myUser.getId());
    	update(coScenarioList.get(1));
    	update(coScenarioList.get(3));

    	 mockMvc.perform(MockMvcRequestBuilders.get(requestPath).param("username", myUser.getLogin()).accept(MediaType.APPLICATION_JSON))
         .andExpect(MockMvcResultMatchers.status().isOk())
                 .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                 .andDo(print())
                 .andExpect(jsonPath("$",org.hamcrest.Matchers.hasSize(2)))
                 .andExpect(jsonPath("$[1].id",is(coScenarioList.get(3).getId())))
                 .andExpect(jsonPath("$[1].name", is(coScenarioList.get(3).getName("Fr_fr"))))
                 .andExpect(jsonPath("$[1].creatorName", is(coScenarioList.get(3).getUserId())))
                 .andExpect(jsonPath("$[1].nbSteps", is(coScenarioList.get(3).getNbrStep())))
                 .andExpect(jsonPath("$[1].descr", is(coScenarioList.get(3).getDescr("Fr_fr"))))
                 .andExpect(jsonPath("$[1].creationDate", is(coScenarioList.get(3).getCreationDate().getTime())));
    }
  
  
    @Test
    public void getScenarioSetup() throws Exception {
    	String requestPath="/rest/conv/scenario/"+coScenarioList.get(3).getId()+"/setup";
    	System.out.println("TEST : GET "+requestPath);
    	
        mockMvc.perform(MockMvcRequestBuilders.get(requestPath).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.id",is(coScenarioList.get(3).getId())))
                .andExpect(jsonPath("$.name", is(coScenarioList.get(3).getName("Fr_fr"))))
                .andExpect(jsonPath("$.creatorName", is(coScenarioList.get(3).getUserId())))
                .andExpect(jsonPath("$.nbSteps", is(coScenarioList.get(3).getNbrStep())))
                .andExpect(jsonPath("$.descr", is(coScenarioList.get(3).getDescr("Fr_fr"))))
                .andExpect(jsonPath("$.creationDate", is(coScenarioList.get(3).getCreationDate().getTime())))
                .andExpect(jsonPath("$.taskList", org.hamcrest.Matchers.hasSize(6)))
                .andExpect(jsonPath("$.taskList[2].name", is("conversion xsl")))
                .andExpect(jsonPath("$.taskList[2].id", is("3")))
                .andExpect(jsonPath("$.taskList[2].parameterList", org.hamcrest.Matchers.hasSize(2)))
                ;
    }
  
    @Test
    public void getScenarioEdit() throws Exception {
    	String requestPath="/rest/conv/scenario/"+coScenarioList.get(4).getId()+"/edit";
    	System.out.println("TEST : GET "+requestPath);
    	
        mockMvc.perform(MockMvcRequestBuilders.get(requestPath).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                .andDo(print())
                .andExpect(jsonPath("$.id",is(coScenarioList.get(4).getId())))
                .andExpect(jsonPath("$.name", is(coScenarioList.get(4).getName("Fr_fr"))))
                .andExpect(jsonPath("$.creatorName", is(coScenarioList.get(4).getUserId())))
                .andExpect(jsonPath("$.nbSteps", is(coScenarioList.get(4).getNbrStep())))
                .andExpect(jsonPath("$.descr", is(coScenarioList.get(4).getDescr("Fr_fr"))))
                .andExpect(jsonPath("$.creationDate", is(coScenarioList.get(4).getCreationDate().getTime())))
                .andExpect(jsonPath("$.taskList", org.hamcrest.Matchers.hasSize(6)))
                .andExpect(jsonPath("$.taskList[2].name", is("conversion xsl")))
                .andExpect(jsonPath("$.taskList[2].id", is("3")))
                .andExpect(jsonPath("$.taskList[2].parameterList", org.hamcrest.Matchers.hasSize(3)))
                .andExpect(jsonPath("$.taskList[2].parameterList[2].label", is("Classe de codification")))
                .andExpect(jsonPath("$.taskList[2].parameterList[2].code", is("codificationClass")))
                .andExpect(jsonPath("$.taskList[2].parameterList[2].type", is("TXT")))
                .andExpect(jsonPath("$.taskList[2].parameterList[2].descr", is("Classe de la codification")))
                ;
    }
    @Test(enabled=false)
    public void getScenarioExecutedByUser() throws Exception {
    	String requestPath="/rest/conv/execution/list";
    	System.out.println("TEST : GET "+requestPath);
    
    	coScenarioList.get(1).setUserId(myUser.getId());
    	coScenarioList.get(1).addHistory(coHistoryList.get(1));
    	coScenarioList.get(1).addHistory(coHistoryList.get(2));
    	coScenarioList.get(3).setUserId(myUser.getId());
    	update(coScenarioList.get(1));
    	update(coScenarioList.get(3));
    	
   	 mockMvc.perform(MockMvcRequestBuilders.get(requestPath).param("username", myUser.getLogin()).accept(MediaType.APPLICATION_JSON))
     .andExpect(MockMvcResultMatchers.status().isOk())
             .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//             .andDo(print())
             .andExpect(jsonPath("$",org.hamcrest.Matchers.hasSize(2)))
             .andExpect(jsonPath("$[1].id",is(coScenarioList.get(1).getId())))
             .andExpect(jsonPath("$[1].name", is(coScenarioList.get(1).getName("Fr_fr"))))
             .andExpect(jsonPath("$[1].creatorName", is(coScenarioList.get(1).getUserId())))
             .andExpect(jsonPath("$[1].listHistoryDetailedModel",org.hamcrest.Matchers.hasSize(2)))
             .andExpect(jsonPath("$[1].listHistoryDetailedModel[0].nbrFiles", is(coScenarioList.get(1).getHistoryList().get(0).getNbrFiles())))
             .andExpect(jsonPath("$[1].listHistoryDetailedModel[0].nbrOK", is(coScenarioList.get(1).getHistoryList().get(0).getNbrOK())))
             .andExpect(jsonPath("$[1].listHistoryDetailedModel[0].nbrNOK", is(coScenarioList.get(1).getHistoryList().get(0).getNbrNOK())))
             .andExpect(jsonPath("$[1].listHistoryDetailedModel[0].nbrNBloquant", is(coScenarioList.get(1).getHistoryList().get(0).getNbrNBloquant())))
             .andExpect(jsonPath("$[1].listHistoryDetailedModel[0].startTime", is(coScenarioList.get(1).getHistoryList().get(0).getStartTime().getTime())))
             .andExpect(jsonPath("$[1].listHistoryDetailedModel[0].endTime", is(coScenarioList.get(1).getHistoryList().get(0).getEndTime().getTime())))
             .andExpect(jsonPath("$[1].listHistoryDetailedModel[0].idExe", is(coScenarioList.get(1).getHistoryList().get(0).getId())));
    
    }
    
    @Test
    public void getExecutionProgress() throws Exception {
    	String requestPath="/rest/conv/execution/"+coHistoryList.get(2).getId()+"/progress";
    	System.out.println("TEST : GET "+requestPath);
    	//status finished
    	coHistoryList.get(2).setNbrNOK(0);
       	update(coHistoryList.get(2));
        mockMvc.perform(MockMvcRequestBuilders.get(requestPath).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                .andDo(print())
                .andExpect(jsonPath("$.nbrFiles", is(coHistoryList.get(2).getNbrFiles())))
                .andExpect(jsonPath("$.nbrOK", is(coHistoryList.get(2).getNbrOK())))
                .andExpect(jsonPath("$.nbrNOK", is(coHistoryList.get(2).getNbrNOK())))
                .andExpect(jsonPath("$.nbrNBloquant", is(coHistoryList.get(2).getNbrNBloquant())))
                .andExpect(jsonPath("$.startTime", is(coHistoryList.get(2).getStartTime().getTime())))
                .andExpect(jsonPath("$.endTime", is(coHistoryList.get(2).getEndTime().getTime())))
                .andExpect(jsonPath("$.zipAvailable", is(false)))
                .andExpect(jsonPath("$.status", is(RunningStatus.FINISHED.name())))
                .andExpect(jsonPath("$.idExe", is(coHistoryList.get(2).getId())));
        //status running
        requestPath="/rest/conv/execution/"+coHistoryList.get(3).getId()+"/progress";
        System.out.println("TEST : GET "+requestPath);
    	coHistoryList.get(3).setEndTime(null);
    	update(coHistoryList.get(3));
        mockMvc.perform(MockMvcRequestBuilders.get(requestPath).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
//                .andDo(print())
                .andExpect(jsonPath("$.nbrFiles", is(coHistoryList.get(3).getNbrFiles())))
                .andExpect(jsonPath("$.nbrOK", is(coHistoryList.get(3).getNbrOK())))
                .andExpect(jsonPath("$.nbrNOK", is(coHistoryList.get(3).getNbrNOK())))
                .andExpect(jsonPath("$.nbrNBloquant", is(coHistoryList.get(3).getNbrNBloquant())))
                .andExpect(jsonPath("$.startTime", is(coHistoryList.get(3).getStartTime().getTime())))
                .andExpect(jsonPath("$.endTime", is(coHistoryList.get(3).getEndTime())))
                .andExpect(jsonPath("$.zipAvailable", is(false)))
                .andExpect(jsonPath("$.status", is(RunningStatus.RUNNING.name())))
                .andExpect(jsonPath("$.idExe", is(coHistoryList.get(3).getId())));
    }
    
    @Test (enabled=false, dependsOnGroups={"runGroup"})
    public void getLogPDF() throws Exception {
//    	String idExe="coco";
    	String requestPath="/rest/conv/execution/"+idExe+"/logPDF";//TODO : JWL -> coco à changer par idExecution, après mis en place système de dossiers
    	System.out.println("TEST : GET "+requestPath);
    

   	 mockMvc.perform(MockMvcRequestBuilders.get(requestPath).accept(MediaType.parseMediaType("application/octet-stream")))
     .andExpect(MockMvcResultMatchers.status().isOk())
             .andExpect(content().contentType(MediaType.parseMediaType("application/octet-stream")));
//             .andDo(print())
    }
    
    @Test(enabled=false)
    public void getResult() throws Exception {
//    	String idExe="coco";
    	String requestPath="/rest/conv/execution/"+idExe+"/result";//TODO : JWL -> coco à changer par idExecution, après mis en place système de dossiers
    	System.out.println("TEST : GET "+requestPath);
    	
    	File zipFileBefore=fileService.getResultZip("idExe");//TODO : JWL -> coco à changer par idExecution, après mis en place système de dossiers
    	assertEquals(zipFileBefore.exists(), true);
      	 mockMvc.perform(MockMvcRequestBuilders.get(requestPath).accept(MediaType.parseMediaType("application/octet-stream")))
         .andExpect(MockMvcResultMatchers.status().isOk())
                 .andExpect(content().contentType(MediaType.parseMediaType("application/octet-stream")));
       	assertEquals(zipFileBefore.exists(), true);
 
    }
    
    @Test
    public void getExecutionLog() throws Exception {
    	CoHistoryExe coHistoryExe=coHistoryList.get(1);
    	String idExe=coHistoryExe.getId();
    	coHistoryExe.setScenarioId(coScenarioList.get(2).getId());
       	update(coHistoryExe);
    	String requestPath="/rest/conv/execution/"+idExe+"/log";
    	System.out.println("TEST : GET "+requestPath);
        mockMvc.perform(MockMvcRequestBuilders.get(requestPath).accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$",org.hamcrest.Matchers.hasSize(6)));
//                .andExpect(jsonPath("$[1].name", is(coHistoryList.get(2).getNbrOK())))
//                .andExpect(jsonPath("$[1].id", is(coHistoryList.get(2).getNbrNOK())))
//                .andExpect(jsonPath("$[1].xmlLog", is(coHistoryList.get(2).getNbrNBloquant())));
    }

}
