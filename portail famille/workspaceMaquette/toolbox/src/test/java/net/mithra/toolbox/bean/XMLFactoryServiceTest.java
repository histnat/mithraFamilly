
package net.mithra.toolbox.bean;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.junit.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author frebeche
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/spring/spring.xml"})
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
public class XMLFactoryServiceTest {
    
    @Autowired
    XMLFactoryService instance;
    
    @Autowired
    UtilsService utilsService;
            
    public XMLFactoryServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addTransformerParameter method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testAddTransformerParameter_String_String() {
        System.out.println("addTransformerParameter");
        String strParamName = "";
        String strParamValue = "";
        instance.addTransformerParameter(strParamName, strParamValue);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addTransformerParameter method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testAddTransformerParameter_String_boolean() {
        System.out.println("addTransformerParameter");
        String strParamName = "";
        boolean bParamValue = false;
        instance.addTransformerParameter(strParamName, bParamValue);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addTransformerParameter method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testAddTransformerParameter_String_int() {
        System.out.println("addTransformerParameter");
        String strParamName = "";
        int nParamValue = 0;
        instance.addTransformerParameter(strParamName, nParamValue);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transform method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testTransform_3args_1() {
        System.out.println("transform");
        Document xmlDocument = null;
        File xslFile = null;
        Writer writer = null;
        instance.transform(xmlDocument, xslFile, writer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transform method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testTransform_3args_2() {
        System.out.println("transform");
        String strXML = "";
        File xslFile = null;
        Writer writer = null;
        instance.transform(strXML, xslFile, writer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transform method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testTransform_3args_3() {
        System.out.println("transform");
        File xmlFile = null;
        File xslFile = null;
        Writer writer = null;
        instance.transform(xmlFile, xslFile, writer);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transform method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testTransform_3args_4() {
        System.out.println("transform");
        Source source = null;
        File xslFile = null;
        OutputStream OutputStream = null;
        instance.transform(source, xslFile, OutputStream);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDOMDocumentFromTransformedDocument method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testGetDOMDocumentFromTransformedDocument() throws Exception {
        System.out.println("getDOMDocumentFromTransformedDocument");
        Document domDoc = null;
        File xslFile = null;
        Document expResult = null;
        Document result = instance.getDOMDocumentFromTransformedDocument(domDoc, xslFile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDOMDocumentFromTransformedXML method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testGetDOMDocumentFromTransformedXML() throws Exception {
        System.out.println("getDOMDocumentFromTransformedXML");
        File xmlFile = null;
        File xslFile = null;
        Document expResult = null;
        Document result = instance.getDOMDocumentFromTransformedXML(xmlFile, xslFile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of parseXmlDoc method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testParseXmlDoc_String() {
        System.out.println("parseXmlDoc");
        String strXmlFile = "";
        Document expResult = null;
        Document result = instance.parseXmlDoc(strXmlFile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of parseXmlDoc method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testParseXmlDoc_InputSource() throws Exception {
        System.out.println("parseXmlDoc");
        InputSource source = null;
        Document expResult = null;
        Document result = instance.parseXmlDoc(source);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of parseXmlDoc method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testParseXmlDoc_File() {
        System.out.println("parseXmlDoc");
        File xmlFile = null;
        Document expResult = null;
        Document result = instance.parseXmlDoc(xmlFile);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of parseXmlDoc method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testParseXmlDoc_InputStream() {
        System.out.println("parseXmlDoc");
        InputStream stream = null;
        Document expResult = null;
        Document result = instance.parseXmlDoc(stream);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkParse method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testCheckParse() throws Exception {
        System.out.println("checkParse");
        File xmlFile = null;
        instance.checkParse(xmlFile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of newDomDoc method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testNewDomDoc() {
        System.out.println("newDomDoc");
        Document expResult = null;
        Document result = instance.newDomDoc();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of executeXpathQuery method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testExecuteXpathQuery() throws Exception {
        System.out.println("executeXpathQuery");
        Document domDoc = null;
        String strQuery = "";
        boolean bList = false;
        Object expResult = null;
        Object result = instance.executeXpathQuery(domDoc, strQuery, bList);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of writeXML method, of class XMLFactoryService.
     */
    @Test
    public void testWriteXML() throws Exception {
        System.out.println("writeXML");
        Document domDoc = null;
        File dataFile = new File("test.xml");
		DocumentBuilder db	= DocumentBuilderFactory.newInstance().newDocumentBuilder();
		domDoc = db.newDocument();
		
		Element rootElem = domDoc.createElement("test");
		domDoc.appendChild(rootElem);
		
        instance.writeXML(domDoc, dataFile);
        
        assertTrue(dataFile.exists());
        Document domResult=instance.parseXmlDoc(dataFile);
        String result=domResult.getFirstChild().getNodeName();
        assertEquals(result, "test");
        dataFile.delete();
    }

    /**
     * Test of toPDF method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testToPDF() {
        System.out.println("toPDF");
        File xmlFile = null;
        File xslFile = null;
        OutputStream os = null;
        instance.toPDF(xmlFile, xslFile, os);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toRTF method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testToRTF() {
        System.out.println("toRTF");
        File xmlFile = null;
        File xslFile = null;
        OutputStream os = null;
        instance.toRTF(xmlFile, xslFile, os);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transform method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testTransform_3args_5() {
        System.out.println("transform");
        Source source = null;
        Source xslFile = null;
        OutputStream OutputStream = null;
        instance.transform(source, xslFile, OutputStream);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getXmlFilesFromDataModuleList method, of class XMLFactoryService.
     */
    @Test
    @Ignore
    public void testGetXmlFilesFromDataModuleList() {
        System.out.println("getXmlFilesFromDataModuleList");
        String sFiltre = "";
        String sDocType = "";
        String[] expResult = null;
        String[] result = instance.getXmlFilesFromDataModuleList(sFiltre, sDocType);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    
    /**
     * Test of fusionXSL method, of class XMLFactoryService.
     */
    @Test
    public void testFusionXSL() {
        System.out.println("fusionXSL");
        InputStream sFile1=getClass().getClassLoader().getResourceAsStream("xslFile/Fusion/file1.xsl");
        InputStream sFile2=getClass().getClassLoader().getResourceAsStream("xslFile/Fusion/file2.xsl");
        String sDocType = "";
        Document result = instance.fusionXSL(sFile1, sFile2);
        utilsService.afficheXML(result);
        assertEquals(result.toString(), result.toString());
    }
    
    @Test
    public void testExecuteXpathQueryNode() throws Exception {
        System.out.println("executeXpathQueryNode");
        Node node = getOperationNode("<aa><bb>bbb</bb><cc>ccc</cc></aa>");
        String strQuery = "//bb";
        String expResult = "bbb";
        Node result = (Node)instance.executeXpathQuery(node, strQuery, false);
        assertEquals(expResult, result.getTextContent());
    }
    

    private Node getOperationNode(String nodeString) {
        StringWriter toParseXML = new StringWriter();
        toParseXML.append("<root>" + nodeString + "</root>");
        Document doc = instance.parseXmlDoc(utilsService.writerToInputStream(toParseXML));
        return doc.getFirstChild().getFirstChild();
    }

}
