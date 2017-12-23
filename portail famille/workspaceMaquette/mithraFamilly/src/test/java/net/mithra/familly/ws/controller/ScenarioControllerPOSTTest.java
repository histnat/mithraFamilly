package net.mithra.familly.ws.controller;

import java.util.List;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.testng.Assert.assertEquals;


import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;

import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;

import net.mithra.familly.db.vo.execution.CoScenario;
import net.mithra.familly.db.vo.user.FaUser;
import net.mithra.familly.utils.FileAccess;
import net.mithra.familly.utils.TestDBHelper;

import net.mithra.familly.ws.config.AppConfigTest;
import net.mithra.familly.ws.controller.model.ScenarioDetailledModel;


@ContextConfiguration(classes = {AppConfigTest.class})
//@ContextConfiguration(locations = "/applicationContext-test.xml")
@WebAppConfiguration
public class ScenarioControllerPOSTTest extends TestDBHelper {
    @Resource
    private WebApplicationContext wac;
    
    
    private MockMvc mockMvc;
    
    
    private String username;
    private String scenarioName;
    
    private List<CoScenario> coScenarioList;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
            MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    
    @BeforeMethod
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        deleteAll();
        username="lee";
        try {

			coScenarioList=getCoScenarioListWithSave(6);
			FileAccess.createOrInitializeDirectory(new File(fileService.getFolderBase()+"\\"));
		} catch (DBNONullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DBNOUniqueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
   }
    
    
    @Test
    public void save() throws Exception {
    	String requestPath="/rest/conv/scenario/save";
    	System.out.println("TEST : POST "+requestPath);
    	
    	assertEquals(countCoScenarioInDB(), coScenarioList.size());
    	mockMvc.perform(MockMvcRequestBuilders.post(requestPath)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new ScenarioDetailledModel(getCoScenarioWithoutSave())))
    			.accept(MediaType.APPLICATION_JSON))
//    			.andDo(print())
                .andExpect(status().isCreated());
    	assertEquals(countCoScenarioInDB(), coScenarioList.size()+1);
    }
    
    
    
    @Test (dependsOnMethods={"save"})
    public void delete() throws Exception {
    	String requestPath1="/rest/conv/scenario/";
    	String requestPath2="/delete";
    	String scenId="SCEN_ID";
    	System.out.println("TEST : delete "+requestPath1+scenId+requestPath2);
    	CoScenario coScenario=getCoScenarioWithoutSave();
    	coScenario.setId(scenId);
    	coScenario.setUserId(username);//------------------>>>>>>> to change
    	update(coScenario);
    	assertEquals(countCoScenarioInDB(), coScenarioList.size()+1);
    	Authentication authentication = new UsernamePasswordAuthenticationToken(username, "pwd");
    	SecurityContextHolder.getContext().setAuthentication(authentication);
    	mockMvc.perform(MockMvcRequestBuilders.delete(requestPath1+scenId+requestPath2)
    			.accept(MediaType.APPLICATION_JSON))
//    			.andDo(print())
                .andExpect(status().isOk());
    	assertEquals(countCoScenarioInDB(), coScenarioList.size());
    }
    
    //deseable for OPENCONV-46, it will be corriged
    @Test (dependsOnMethods={"delete"}, groups={"runGroup"}, enabled=true)
    public void run() throws Exception {
    	String requestPath1="/rest/conv/scenario/";
    	String requestPath2="/run";
    	String scenId="SCEN_ID";
    	System.out.println("TEST : run "+requestPath1+scenId+requestPath2);

    	CoScenario coScenario=getCoScenarioWithoutSave();
    	coScenario.setId(scenId);
    	update(coScenario);
        MockMultipartFile multipartFile = new MockMultipartFile("files","in.zip",MediaType.APPLICATION_OCTET_STREAM_VALUE,new FileInputStream(new File("src/test/resources/in/in.zip")));
    	String jsonListParamsString= IOUtils.toString(utilsService.getFile("in/listJsonParams.txt"), "UTF-8"); 

    	MvcResult result=mockMvc.perform(MockMvcRequestBuilders.fileUpload(requestPath1+scenId+requestPath2)
    			.file(multipartFile)
    			.contentType(MediaType.MULTIPART_FORM_DATA)
    			.param("listParam", jsonListParamsString)
    			.accept(MediaType.MULTIPART_FORM_DATA))
//    			.andDo(print())
    			.andExpect(status().isCreated())
    			.andReturn();

    	idExe=result.getResponse().getContentAsString();
    }
    
    
    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            System.out.println(jsonContent);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
