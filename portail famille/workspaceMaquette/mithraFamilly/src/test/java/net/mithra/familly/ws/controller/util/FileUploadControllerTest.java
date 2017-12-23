package net.mithra.familly.ws.controller.util;

import java.util.List;
import javax.annotation.Resource;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
//import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import net.mithra.familly.ws.config.AppConfigTest;


//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfigTest.class})
@WebAppConfiguration
public class FileUploadControllerTest {
//    @Resource
//    private WebApplicationContext wac;
//    
//    
//    private MockMvc mockMvc;
//    
//    @BeforeTest
//    public void init() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//   }
//    
//    
//	 @Test
//    public void checkProvideUploadInfo() throws Exception{
//	        mockMvc.perform(MockMvcRequestBuilders.get("/rest/upload"))
//            .andExpect(MockMvcResultMatchers.status().isOk());
//    }
//    
//	 @Test
//    public void provideUploadInfo2() {
//    
//    }
//
//	 @Test
//    public void handleFileUpload(){
//    }

}
