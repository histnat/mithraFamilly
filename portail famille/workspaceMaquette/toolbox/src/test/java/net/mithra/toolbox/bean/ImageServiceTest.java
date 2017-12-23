package net.mithra.toolbox.bean;

import net.mithra.toolbox.bean.UtilsService;
import net.mithra.toolbox.bean.impl.UtilsServiceImpl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.*;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 *
 * @author Frebeche
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/spring.xml"})
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class ImageServiceTest  {
    
    @Autowired
    ImageService imageService;
    
    

    /**
     * Test of FloatToInt method, of class UtilsServiceImpl.
     */
    @Ignore
    @Test
    public void testFindMimeOfFile() {
        System.out.println("findMimeOfFile");
        String expect = "image/png";
        File fileTest= new File(getClass().getClassLoader().getResource("image/fileTest.png").getPath());
        String result=imageService.findMimeOfFile(fileTest);
        System.out.println("r:"+result);
        assertEquals("Test png", expect, result);
        
        expect = "image/gif";
        fileTest= new File(getClass().getClassLoader().getResource("image/fileTest.gif").getPath());
        result=imageService.findMimeOfFile(fileTest);
        assertEquals("Test gif", expect, result);
        
        expect = "image/bmp";
        fileTest= new File(getClass().getClassLoader().getResource("image/fileTest.bmp").getPath());
        result=imageService.findMimeOfFile(fileTest);
        assertEquals("Test bmp", expect, result);
        
        expect = "image/jpeg";
        fileTest= new File(getClass().getClassLoader().getResource("image/fileTest.jpg").getPath());
        result=imageService.findMimeOfFile(fileTest);
        assertEquals("Test jpg", expect, result);
        
        expect = "image/tiff";
        fileTest= new File(getClass().getClassLoader().getResource("image/fileTest.tif").getPath());
        result=imageService.findMimeOfFile(fileTest);
        assertEquals("Test tif", expect, result);
        
        expect = "image/gif";
        fileTest= new File(getClass().getClassLoader().getResource("image/fileTestGif.jpg").getPath());
        result=imageService.findMimeOfFile(fileTest);
        assertEquals("Test faux gif", expect, result);
        //System.out.println(imageService.findMimeOfFile(fileTest));
     }
    
    @Ignore
    @Test
    public void convertCGMToJpeg() throws IOException {
        System.out.println("convertCGMToJpeg");
        
        File fileTest= new File(getClass().getClassLoader().getResource("image/fileTest.cgm").getPath());
        
        File fileResult= new File("testResult.jpg");
        imageService.convertCGMToJpeg(fileTest, fileResult);
        System.out.println("r:"+fileResult.getAbsolutePath());
        assertTrue(fileResult.exists());
        assertTrue(fileResult.isFile());
        fileResult.delete();
     }

}
