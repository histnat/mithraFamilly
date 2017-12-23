package net.mithra.toolbox.bean.impl;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathVariableResolver;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.fop.events.EventListener;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import net.mithra.toolbox.LogMes;
import net.mithra.toolbox.bean.XMLFactoryService;

@Service("XMLFactoryService")
public class XMLFactoryServiceImpl implements XMLFactoryService {

	/**
     *
     */
	private static final long serialVersionUID = -9019536699757124051L;

	public final static String DEFAULT_CHARSET = "UTF-8";
	private XPathVariableResolver xPathVariableResolver;

	public XMLFactoryServiceImpl() {
		m_parameters = new HashMap<String, String>();
	}

	@Override
	public Document getDOMDocumentFromTransformedDocument(Document domDoc,
			File xslFile) throws Exception {
		StringWriter writer = new StringWriter();

		transform(domDoc, xslFile, writer);

		return getDomDocumentFromReader(new StringReader(writer.getBuffer()
				.toString()));
	}

	@Override
	public Document getDOMDocumentFromTransformedDocument(Document domDoc,
			InputStream xslFile) throws Exception {
		return getDOMDocumentFromTransformedDocument(domDoc, xslFile, null);
	}

	@Override
	public Document getDOMDocumentFromTransformedDocument(Document domDoc,
			InputStream xslFile, URIResolver uriResolver) throws Exception {
		StringWriter writer = new StringWriter();

		transform(domDoc, xslFile, writer, uriResolver);

		return getDomDocumentFromReader(new StringReader(writer.getBuffer()
				.toString()));
	}
	
	@Override
	public Document getDOMDocumentFromTransformedXML(File xmlFile, File xslFile)
			throws Exception {
		StringWriter writer = new StringWriter();

		transform(xmlFile, xslFile, writer);

		return getDomDocumentFromReader(new StringReader(writer.getBuffer()
				.toString()));
	}

	private Document getDomDocumentFromReader(StringReader reader)
			throws Exception {
		InputSource source = new InputSource(reader);
		source.setEncoding(DEFAULT_CHARSET);
		return parseXmlDoc(source);
	}

	public Document getDOMDocumentFromNode(Node node) {
		Document newDocument = null;
		newDocument = newDomDoc();
		newDocument.appendChild(newDocument.importNode(node, true));
		return newDocument;

	}

	@Override
	public Document parseXmlDoc(String strXmlFile) {
		File xml = new File(strXmlFile);
		return parseXmlDoc(xml);
	}

	@Override
	public Document parseXmlDoc(InputSource source) throws Exception {
		return getDocumentBuilder().parse(source);
	}

	@Override
	public Document parseXmlDocWithValidation(File xmlFile) throws Exception {
		return getDocumentBuilderWithValidation().parse(xmlFile);
	}

	@Override
	public Document parseXmlDocWithValidation(InputStream xmlFile)
			throws Exception {
		return getDocumentBuilderWithValidation().parse(xmlFile);
	}

	@Override
	public Document parseXmlDoc(File xmlFile) {
		Document doc;

		doc = null;

		try {
			doc = getDocumentBuilder().parse(xmlFile);
		} catch (Exception e) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					"Erreur lors du parsing du fichier " + xmlFile.getName()
							+ ". Message: " + e.getMessage());

		}
		return doc;
	}

	@Override
	public Document parseXmlDoc(InputStream stream) {
		Document doc;

		doc = null;

		try {
			doc = getDocumentBuilder().parse(stream);
		} catch (Exception e) {
			LogMes.log(
					XMLFactoryServiceImpl.class,
					LogMes.ERROR,
					"Erreur lors du parsing d'un stream. Message: "
							+ e.getMessage());
			e.printStackTrace();
		}
		return doc;
	}

	@Override
	public void checkParse(File xmlFile) throws IOException, SAXException {
		getDocumentBuilder().parse(xmlFile);
	}

	@Override
	public Document newDomDoc() {
		return getDocumentBuilder().newDocument();
	}

	static DocumentBuilder getDocumentBuilderWithValidation() {
		DocumentBuilderFactory dbf;
		DocumentBuilder db;

		db = null;

		try {
			dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			dbf.setValidating(true);
			dbf.setAttribute(
					"http://java.sun.com/xml/jaxp/properties/schemaLanguage",
					"http://www.w3.org/2001/XMLSchema");

			db = dbf.newDocumentBuilder();
			db.setErrorHandler(new ErrorHandler() {
				public void warning(SAXParseException e) throws SAXException {
					// to do
				}

				public void error(SAXParseException e) throws SAXException {
					throw e;
				}

				public void fatalError(SAXParseException e) throws SAXException {
					throw e;
				}
			}

			);
		} catch (ParserConfigurationException e) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					"Erreur lors de la cràation d'un document DOM. Message: "
							+ e.getMessage());
		}
		return db;
	}

	static DocumentBuilder getDocumentBuilder() {
		DocumentBuilderFactory dbf;
		DocumentBuilder db;

		db = null;

		try {
			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					"Erreur lors de la cràation d'un document DOM. Message: "
							+ e.getMessage());
		}
		return db;
	}

	/**
	 * execute le xpath
	 * 
	 * @param nodeDoc
	 * @param strQuery
	 * @param bList
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object executeXpathQuery(Node nodeDoc, String strQuery, boolean bList)
			throws Exception {
		LogMes.log(XMLFactoryServiceImpl.class, LogMes.DEBUG,
				"executeXpathQuery : strQuery:" + strQuery + " bList" + bList);
		if (nodeDoc == null) {
			return null;
		}
		if (strQuery == null || strQuery.length() == 0) {
			return null;
		}

		XPathFactory xpf = XPathFactory.newInstance();
		XPath xPath = xpf.newXPath();

		if (xPathVariableResolver != null)
			xPath.setXPathVariableResolver(xPathVariableResolver);

		XPathExpression xPathExp = xPath.compile(strQuery);

		if (bList) {
			return (NodeList) xPathExp
					.evaluate(nodeDoc, XPathConstants.NODESET);
		} else {
			return (Node) xPathExp.evaluate(nodeDoc, XPathConstants.NODE);
		}
	}

	/**
	 * execute le xpath
	 * 
	 * @param nodeDoc
	 * @param strQuery
	 * @param bList
	 *            0 for NODESET, 1 for NODE, 2 STRING
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object executeXpathQuery(Node nodeDoc, String strQuery, int bList)
			throws Exception {
		LogMes.log(XMLFactoryServiceImpl.class, LogMes.DEBUG,
				"executeXpathQuery : strQuery:" + strQuery + " bList" + bList);
		if (nodeDoc == null) {
			return null;
		}
		if (strQuery == null || strQuery.length() == 0) {
			return null;
		}

		XPathFactory xpf = XPathFactory.newInstance();
		XPath xPath = xpf.newXPath();

		if (xPathVariableResolver != null)
			xPath.setXPathVariableResolver(xPathVariableResolver);

		XPathExpression xPathExp = xPath.compile(strQuery);

		switch (bList) {
		case 0:
			return (NodeList) xPathExp
					.evaluate(nodeDoc, XPathConstants.NODESET);
		case 1:
			return (Node) xPathExp.evaluate(nodeDoc, XPathConstants.NODE);
		case 2:
			return (String) xPathExp.evaluate(nodeDoc, XPathConstants.STRING);
		default:
			return (NodeList) xPathExp
					.evaluate(nodeDoc, XPathConstants.NODESET);
		}
	}

	/**
	 * execute le xpath
	 * 
	 * @param nodeDoc
	 * @param strQuery
	 * @param bList
	 * @param namespaceContext
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object executeXpathQuery(Node nodeDoc, String strQuery,
			boolean bList, NamespaceContext namespaceContext) throws Exception {

		if (nodeDoc == null) {
			return null;
		}
		if (strQuery == null || strQuery.length() == 0) {
			return null;
		}
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		xpath.setNamespaceContext(namespaceContext);

		if (xPathVariableResolver != null)
			xpath.setXPathVariableResolver(xPathVariableResolver);

		XPathExpression xPathExp = xpath.compile(strQuery);

		if (bList) {
			return (NodeList) xPathExp
					.evaluate(nodeDoc, XPathConstants.NODESET);
		} else {
			return (Node) xPathExp.evaluate(nodeDoc, XPathConstants.NODE);
		}

	}

	/**
	 * execute le xpath
	 * 
	 * @param nodeDoc
	 * @param strQuery
	 * @return
	 * @throws Exception
	 */
	@Override
	public boolean executeXpathQueryBoolean(Node nodeDoc, String strQuery)
			throws Exception {
		if (nodeDoc == null) {
			return false;
		}
		if (strQuery == null || strQuery.length() == 0) {
			return false;
		}

		XPathFactory xpf = XPathFactory.newInstance();
		XPath xPath = xpf.newXPath();

		if (xPathVariableResolver != null)
			xPath.setXPathVariableResolver(xPathVariableResolver);

		XPathExpression xPathExp = xPath.compile(strQuery);
		return (Boolean) xPathExp.evaluate(nodeDoc, XPathConstants.BOOLEAN);

	}

	public boolean executeXpathQueryBoolean(Document domDoc, String strQuery)
			throws Exception {
		if (domDoc == null) {
			return false;
		}
		if (strQuery == null || strQuery.length() == 0) {
			return false;
		}

		XPathFactory xpf = XPathFactory.newInstance();
		XPath xPath = xpf.newXPath();

		if (xPathVariableResolver != null)
			xPath.setXPathVariableResolver(xPathVariableResolver);

		XPathExpression xPathExp = xPath.compile(strQuery);
		return (Boolean) xPathExp.evaluate(domDoc, XPathConstants.BOOLEAN);

	}

	@Override
	public String executeXpathFonction(Document domDoc, String strQuery)
			throws Exception {
		LogMes.log(XMLFactoryServiceImpl.class, LogMes.DEBUG,
				"executeXpathQuery : strQuery:" + strQuery);
		if (domDoc == null) {
			return null;
		}
		if (strQuery == null || strQuery.length() == 0) {
			return null;
		}

		XPathFactory xpf = XPathFactory.newInstance();
		XPath xPath = xpf.newXPath();
		XPathExpression xPathExp = xPath.compile(strQuery);
		return (String) xPathExp.evaluate(domDoc, XPathConstants.STRING);

	}

	@Override
	public String executeXpathFonction(Node node, String strQuery)
			throws Exception {
		LogMes.log(XMLFactoryServiceImpl.class, LogMes.DEBUG,
				"executeXpathQuery : strQuery:" + strQuery);
		if (node == null) {
			return null;
		}
		if (strQuery == null || strQuery.length() == 0) {
			return null;
		}

		XPathFactory xpf = XPathFactory.newInstance();
		XPath xPath = xpf.newXPath();
		XPathExpression xPathExp = xPath.compile(strQuery);
		return (String) xPathExp.evaluate(node, XPathConstants.STRING);

	}

	@Override
	public Object executeXpathQuery(Document domDoc, String strQuery,
			boolean bList) throws Exception {
		LogMes.log(XMLFactoryServiceImpl.class, LogMes.DEBUG,
				"executeXpathQuery : strQuery:" + strQuery + " bList" + bList);
		if (domDoc == null) {
			return null;
		}
		if (strQuery == null || strQuery.length() == 0) {
			return null;
		}

		XPathFactory xpf = XPathFactory.newInstance();
		XPath xPath = xpf.newXPath();
		XPathExpression xPathExp = xPath.compile(strQuery);

		if (xPathVariableResolver != null)
			xPath.setXPathVariableResolver(xPathVariableResolver);

		if (bList) {
			return (NodeList) xPathExp.evaluate(domDoc, XPathConstants.NODESET);
		} else {
			return (Node) xPathExp.evaluate(domDoc, XPathConstants.NODE);
		}
	}

	/**
	 * execute le xpath
	 *
	 * @param domDoc
	 * @param strQuery
	 * @param bList
	 * @param namespaceContext
	 * @return
	 * @throws Exception
	 */
	@Override
	public Object executeXpathQuery(Document domDoc, String strQuery,
			boolean bList, NamespaceContext namespaceContext) throws Exception {

		if (domDoc == null) {
			return null;
		}
		if (strQuery == null || strQuery.length() == 0) {
			return null;
		}
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		xpath.setNamespaceContext(namespaceContext);

		if (xPathVariableResolver != null)
			xpath.setXPathVariableResolver(xPathVariableResolver);

		XPathExpression xPathExp = xpath.compile(strQuery);

		if (bList) {
			return (NodeList) xPathExp.evaluate(domDoc, XPathConstants.NODESET);
		} else {
			return (Node) xPathExp.evaluate(domDoc, XPathConstants.NODE);
		}

	}
	
	/**
	 * renvoie le document xml domDoc dans out
	 *
	 * @param domDoc
	 * @param out
	 */
	@Override
	public void writeXML(Document domDoc, OutputStream out) {
		writeXML(domDoc, out, DEFAULT_CHARSET, true);
	}

	/**
	 * renvoie le document xml domDoc dans out
	 *
	 * @param domDoc
	 * @param out
	 */
	@Override
	public void writeXML(Document domDoc, OutputStream out, boolean indent) {
		writeXML(domDoc, out, DEFAULT_CHARSET, indent);
	}

	@Override
	public void writeXML(Document domDoc, File dataFile)
			throws FileNotFoundException, IOException {
		writeXML(domDoc, dataFile, DEFAULT_CHARSET);
	}

	@Override
	public void writeXML(Document domDoc, File dataFile, String encodage)
			throws FileNotFoundException, IOException {
		writeXML(domDoc, new FileOutputStream(dataFile), encodage, true);
	}
	public void writeXML(Document domDoc, OutputStream out,String encodage, boolean indent) {
		
		
		
		/*		LSSerializer **/
		DOMImplementationRegistry reg=null;
		try {
			reg = DOMImplementationRegistry.newInstance();
		} catch (ClassNotFoundException e) {
			LogMes.logError(XMLFactoryServiceImpl.class,"problem during writing xml", e);
		} catch (InstantiationException e) {
			LogMes.logError(XMLFactoryServiceImpl.class,"problem during writing of xml", e);
		} catch (IllegalAccessException e) {

			LogMes.logError(XMLFactoryServiceImpl.class,"problem during writing of xml", e);
		} catch (ClassCastException e) {

			LogMes.logError(XMLFactoryServiceImpl.class,"problem during writing of xml", e);
		}
		DOMImplementationLS impl = (DOMImplementationLS) reg.getDOMImplementation("LS");
		LSSerializer serializer = impl.createLSSerializer();
        serializer.getDomConfig().setParameter("format-pretty-print", indent);
        LSOutput output = impl.createLSOutput();
        output.setEncoding(encodage);
        output.setByteStream(out);
        serializer.write(domDoc, output);
		/*		LSSerializer **/
	}

	public Node addNode(Node parent, String strNewNodeName) {
		Element element = parent.getOwnerDocument().createElement(
				strNewNodeName);
		parent.appendChild(element);
		return element;
	}

	public Node addNodeWithAttribute(Node parent, String strNewNodeName,
			String strAttName, String strAttValue) {
		Element element = parent.getOwnerDocument().createElement(
				strNewNodeName);
		element.setAttribute(strAttName, strAttValue);
		parent.appendChild(element);
		return element;
	}

	public Node addNodeWithAttributes(Node parent, String strNewNodeName,
			Map<String, String> attributes) {
		Element element = parent.getOwnerDocument().createElement(
				strNewNodeName);

		Set<Entry<String, String>> entries = attributes.entrySet();
		Iterator<Entry<String, String>> iteEntries = entries.iterator();
		while (iteEntries.hasNext()) {
			Entry<String, String> entry = iteEntries.next();

			element.setAttribute(entry.getKey(), entry.getValue());
		}
		parent.appendChild(element);
		return element;
	}

	/**
	 * Fournit une liste de nom de fichier a partir du DataModuleList avec
	 * sFiltre et sDocType
	 *
	 * @param sFiltre
	 *            filtre applique sur le nom de fichier
	 * @param sDocType
	 *            type de document retourne. '' retourne tous les types
	 * @return Liste de nom de fichier
	 */
	public String[] getXmlFilesFromDataModuleList(String sFiltre,
			String sDocType) {
		XPathExpression sXPath = null;
		XPathFactory factory = XPathFactory.newInstance();
		XPath xPath = factory.newXPath();
		String m_xpath = "//dmref";
		String sResct = "";
		List<String> lResult = new ArrayList<String>();
		boolean addAnd = false;
		if (sFiltre != null && sFiltre.length() > 0) {
			addAnd = true;
			sResct += "contains(@id,'" + sFiltre + "')";
		}
		if (sDocType != null && sDocType.length() > 0) {

			if (addAnd) {
				sResct += " and ";
			}
			sResct += "@type='" + sDocType + "'";
			addAnd = true;
		}
		if (addAnd) {
			sResct += " and ";
		}
		sResct += "(contains(@id,'0350') or (@hide!='true'))";
		if (sResct != null && sResct.length() > 0) {
			m_xpath += "[" + sResct + "]";
		}
		m_xpath += "/@id";

		// System.out.println("m_xpath="+m_xpath);
		try {
			sXPath = xPath.compile(m_xpath);
			File sF = new File("dataModuleList.xml");
			Document sDML = parseXmlDoc(sF);
			NodeList sNS = (NodeList) sXPath.evaluate(sDML,
					XPathConstants.NODESET);
			int mx = sNS.getLength();
			for (int i = 0; i < mx; i++) {
				Node sItem = sNS.item(i);
				String sFichier = sItem.getNodeValue() + ".XML";
				lResult.add(sFichier);
			}
		} catch (XPathExpressionException e) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					"ce xPath pose probleme :" + m_xpath);
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					e.getMessage());
		}

		return (String[]) lResult.toArray(new String[lResult.size()]);

	}

	/**
	 * fusionne deux fichier xsl
	 */
	@Override
	public org.w3c.dom.Document fusionXSL(String file1, String file2) {
		try {
			return fusionXSL(new FileInputStream(file1), new FileInputStream(
					file2));
		} catch (FileNotFoundException ex) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					"fusionXSL:le fichier n'existe pas :" + ex);
		}
		return null;
	}

	/**
	 * fusionne deux fichier xsl
	 */
	@Override
	public org.w3c.dom.Document fusionXSL(InputStream file1, String file2) {
		try {
			return fusionXSL(file1, new FileInputStream(file2));
		} catch (FileNotFoundException ex) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					"fusionXSL:le fichier n'existe pas :" + ex);
		}
		return null;
	}

	/**
	 * fusionne deux fichier xsl
	 */
	@Override
	public org.w3c.dom.Document fusionXSL(String file1, InputStream file2) {
		try {
			return fusionXSL(new FileInputStream(file1), file2);
		} catch (FileNotFoundException ex) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					"fusionXSL:le fichier n'existe pas :" + ex);
		}
		return null;
	}

	@Override
	public org.w3c.dom.Document fusionXSL(File file1, InputStream file2) {
		try {
			return fusionXSL(new FileInputStream(file1), file2);
		} catch (FileNotFoundException ex) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					"le fiochier n'existe pas :" + ex);
		}
		return null;
	}

	/**
	 * fusionne deux fichier xsl
	 *
	 * @param file1
	 * @param file2
	 * @return
	 */
	@Override
	public org.w3c.dom.Document fusionXSL(InputStream file1, InputStream file2) {
		return fusionXSL(parseXmlDoc(file1), parseXmlDoc(file2));
	}

	@Override
	public org.w3c.dom.Document fusionXSL(Document file1, InputStream file2) {
		return fusionXSL(file1, parseXmlDoc(file2));
	}

	@Override
	public org.w3c.dom.Document fusionXSL(InputStream file1, Document file2) {
		return fusionXSL(parseXmlDoc(file1), file2);
	}

	/**
	 * fusionne deux fichier xsl
	 *
	 * @param doc1
	 * @param doc2
	 * @return
	 */
	@Override
	public org.w3c.dom.Document fusionXSL(Document doc1, Document doc2) {
		NamespaceContext namespace = new NamespaceContext() {
			public String getNamespaceURI(String prefix) {
				if ("xsl".equals(prefix)) {
					return "http://www.w3.org/1999/XSL/Transform";
				} else {
					return null;
				}
			}

			public String getPrefix(String namespaceURI) {
				if ("http://www.w3.org/1999/XSL/Transform".equals(namespaceURI)) {
					return "xsl";
				} else {
					return null;
				}
			}

			public Iterator getPrefixes(String namespaceURI) {
				return null;
			}
		};
		try {
			if (doc1 == null) {
				LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
						"File1 ne peut pas etre null");
				return null;
			}
			if (doc2 == null) {
				LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
						"File2 ne peut pas etre null");
				return null;
			}

			// Document result = doc1;
			NodeList template = (NodeList) executeXpathQuery(doc2,
					"//*[name()='xsl:template']", true, namespace);
			Element root = (Element) doc1.getFirstChild();
			if (template != null) {
				int j = template.getLength();
				for (int i = 0; i < j; i++) {
					Element sE = (Element) template.item(i);
					String name = sE.getAttribute("name");
					if (name != null && !name.isEmpty()) {
						Node sN = (Node) executeXpathQuery(doc1,
								"//*[name()='xsl:template'][@name='" + name
										+ "']", false);
						if (sN == null) {
							root.appendChild(doc1.importNode(sE, true));
						}
					}
					String match = sE.getAttribute("match");
					if (match != null && !match.isEmpty()) {
						Node sN = (Node) executeXpathQuery(doc1,
								"//*[name()='xsl:template'][@match='" + match
										+ "']", false);
						if (sN == null) {
							root.appendChild(doc1.importNode(sE, true));
						}
					}
				}
			}

			return doc1;
		} catch (Exception ex) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					ex.getMessage());
		}
		return null;
	}

	

	// ---------------------------------------
	// ---------- DEPRECATED METHOD ----------
	// ---------------------------------------

	@Deprecated
	private HashMap<String, String> m_parameters;

	@Deprecated
	private Configuration fopConfig = null;

	@Deprecated
	private boolean strictValidationFOP = true;

	@Deprecated
	private String omitXmlDeclaration = null;

	@Deprecated
	private String baseFopFont = null;

	@Deprecated
	public void reset() {
		m_parameters.clear();
	}

	@Override
	@Deprecated
	public void addTransformerParameter(String strParamName,
			String strParamValue) {
		m_parameters.put(strParamName, strParamValue);
	}

	@Override
	@Deprecated
	public void addTransformerParameter(String strParamName, boolean bParamValue) {
		m_parameters.put(strParamName, String.valueOf(bParamValue));
	}

	@Override
	@Deprecated
	public void addTransformerParameter(String strParamName, int nParamValue) {
		m_parameters.put(strParamName, String.valueOf(nParamValue));
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use XSLTransformService instead
	 */
	@Override
	@Deprecated
	public void transform(Document xmlDocument, File xslFile, Writer writer) {
		LogMes.log(XMLFactoryServiceImpl.class, LogMes.DEBUG,
				"transformation à partir de l'objet DOM: " + xmlDocument);
		transform(new DOMSource(xmlDocument), xslFile, writer);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use XSLTransformService instead
	 */
	@Override
	@Deprecated
	public void transform(Document xmlDocument, Source xslFile, Writer writer) {
		LogMes.log(XMLFactoryServiceImpl.class, LogMes.DEBUG,
				"transformation à partir de l'objet DOM: " + xmlDocument);
		transform(new DOMSource(xmlDocument), xslFile, new StreamResult(writer));
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use XSLTransformService instead
	 */
	@Override
	@Deprecated
	public void transform(Document xmlDocument, Source xslFile, Writer writer,
			URIResolver uriResolver) {
		LogMes.log(XMLFactoryServiceImpl.class, LogMes.DEBUG,
				"transformation à partir de l'objet DOM: " + xmlDocument);
		transform(new DOMSource(xmlDocument), xslFile,
				new StreamResult(writer), uriResolver);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use XSLTransformService instead
	 */
	@Override
	@Deprecated
	public void transform(String strXML, File xslFile, Writer writer) {
		LogMes.log(XMLFactoryServiceImpl.class, LogMes.DEBUG,
				"transformation à partir de la chaine: " + strXML);
		ByteArrayInputStream bais = new ByteArrayInputStream(strXML.getBytes());
		transform(new StreamSource(bais), xslFile, writer);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use XSLTransformService instead
	 */
	@Override
	@Deprecated
	public void transform(File xmlFile, File xslFile, Writer writer) {
		LogMes.log(XMLFactoryServiceImpl.class, LogMes.DEBUG,
				"transformation à partir du fichier: " + xmlFile.getName());
		transform(new StreamSource(xmlFile.getAbsolutePath()), xslFile, writer);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use XSLTransformService instead
	 */
	@Override
	@Deprecated
	public void transform(Source source, Source xslFile,
			OutputStream OutputStream) {
		transform(source, xslFile, new StreamResult(OutputStream));
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use XSLTransformService instead
	 */
	@Override
	@Deprecated
	public void transform(Source source, File xslFile, Writer writer) {
		transform(source, new StreamSource(xslFile.getAbsolutePath()),
				new StreamResult(writer));
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use XSLTransformService instead
	 */
	@Override
	@Deprecated
	public void transform(Source source, InputStream xslFile,
			StringWriter writer) {
		transform(source, new StreamSource(xslFile), new StreamResult(writer));
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use XSLTransformService instead
	 */
	@Override
	@Deprecated
	public void transform(Document xmlDocument, InputStream xslFile,
			StringWriter writer) {
		transform(new DOMSource(xmlDocument), new StreamSource(xslFile),
				new StreamResult(writer));
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use XSLTransformService instead
	 */
	@Override
	@Deprecated
	public void transform(Document xmlDocument, InputStream xslFile,
			StringWriter writer, URIResolver uriResolver) {
		transform(new DOMSource(xmlDocument), new StreamSource(xslFile),
				new StreamResult(writer), uriResolver);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use XSLTransformService instead
	 */
	@Override
	@Deprecated
	public void transform(InputStream xmlDocument, InputStream xslFile,
			Writer writer, URIResolver uriResolver) {
		transform(new StreamSource(xmlDocument), new StreamSource(xslFile),
				new StreamResult(writer), uriResolver);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use XSLTransformService instead
	 */
	@Override
	@Deprecated
	public void transform(Document xmlDocument, InputStream xslFile,
			Writer writer) {
		transform(new DOMSource(xmlDocument), xslFile, writer);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use XSLTransformService instead
	 */
	@Override
	@Deprecated
	public void transform(Source source, File xslFile, OutputStream OutputStream) {
		transform(source, new StreamSource(xslFile.getAbsolutePath()),
				OutputStream);

	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use XSLTransformService instead
	 */
	@Override
	@Deprecated
	public void transform(Source source, Source xslFile,
			StreamResult OutputStream) {
		transform(source, xslFile, OutputStream, null);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use XSLTransformService instead
	 */
	@Override
	@Deprecated
	public void transform(Source source, InputStream xslFile, Writer writer) {
		transform(source, xslFile, writer, null);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use XSLTransformService instead
	 */
	@Override
	@Deprecated
	public void transform(Source source, Source xslFile,
			StreamResult OutputStream, URIResolver uriResolver) {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			if (uriResolver != null) {
				tFactory.setURIResolver(uriResolver);
			}
			Transformer transformer = tFactory.newTransformer(xslFile);
			if (omitXmlDeclaration != null && !omitXmlDeclaration.isEmpty()) {
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
						omitXmlDeclaration);
			}
			addTransformerParameters(transformer);

			transformer.transform(source, OutputStream);
		} catch (Exception e) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					"transform : Exception lors de la transformation XML:1 "
							+ e.getMessage());
			//e.printStackTrace();
		}
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use XSLTransformService instead
	 */
	@Override
	@Deprecated
	public void transform(Source source, InputStream xslFile, Writer writer,
			URIResolver uriResolver) {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			if (uriResolver != null) {
				tFactory.setURIResolver(uriResolver);
			}
			Transformer transformer = tFactory.newTransformer(new StreamSource(
					xslFile));
			if (omitXmlDeclaration != null && !omitXmlDeclaration.isEmpty()) {
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
						omitXmlDeclaration);
			}
			addTransformerParameters(transformer);
			transformer.transform(source, new StreamResult(writer));
		} catch (Exception e) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					"transform : Exception lors de la transformation XML:2 "
							+ e.getMessage());
			e.printStackTrace();
			// //SeLog.logError(SeLog.CAT_XML,
			// "Exception lors de la transformation XML: "+e.getMessage());
		}
	}

	/**
	 * @deprecated this method isn't supported anymore, please use
	 *             XSLTransformService instead
	 */
	@Override
	@Deprecated
	public void transformSaxon(Source source, Source xslFile,
			OutputStream OutputStream) {
		// TODO Auto-generated method stub

	}

	/**
	 * @deprecated this method isn't supported anymore, please use
	 *             XSLTransformService instead
	 */
	@Override
	@Deprecated
	public void transformSaxon(Document source, Source xslFile,
			OutputStream outputStream) {
		// TODO Auto-generated method stub

	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void toPDF(Document xmlDoc, File xslFile, OutputStream os,
			URIResolver uriResolver) {
		toPDF(new DOMSource(xmlDoc), xslFile, os, uriResolver);
		System.gc();
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Deprecated
	private void toPDF(Source source, File xslFile, OutputStream os,
			URIResolver uriResolver) {
		try {
			// configure fopFactory as desired
			FopFactory fopFactory = FopFactory.newInstance();

			if (uriResolver != null) {
				fopFactory.setURIResolver(uriResolver);
			}
			// configure foUserAgent as desired
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

			// Construct fop with desired output format.
			Fop fop = fopFactory
					.newFop(MimeConstants.MIME_PDF, foUserAgent, os);

			// Resulting SAX events (the generated FO) must be piped through to
			// FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			TransformerFactory factory = TransformerFactory.newInstance();
			if (uriResolver != null) {
				factory.setURIResolver(uriResolver);
			}
			Transformer transformer = factory.newTransformer(new StreamSource(
					xslFile));

			addTransformerParameters(transformer);

			transformer.transform(source, res);

		} catch (Exception e) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					e.getMessage());
		}
	}

	public void setxPathVariableResolver(
			XPathVariableResolver xPathVariableResolver) {
		this.xPathVariableResolver = xPathVariableResolver;
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void toPDF(File xmlFile, InputStream xslFile, OutputStream os) {
		toPDF(new StreamSource(xmlFile.getAbsolutePath()), xslFile, os);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void toPDF(Document xmlDocument, InputStream xslFile, OutputStream os) {
		toPDF(new DOMSource(xmlDocument), new StreamSource(xslFile), os);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void toPDF(Document xmlDocument, InputStream xslFile,
			OutputStream os, URIResolver uriResolver) {
		toPDF(new DOMSource(xmlDocument), new StreamSource(xslFile), os,
				uriResolver);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void toPDF(String strXML, InputStream xslFile, OutputStream os) {
		ByteArrayInputStream bais = new ByteArrayInputStream(strXML.getBytes());
		toPDF(new StreamSource(bais), xslFile, os);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void toPDF(Source source, InputStream xslFile, OutputStream os) {
		toPDF(source, new StreamSource(xslFile), os);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void toPDF(File xmlFile, File xslFile, OutputStream os) {
		toPDF(new StreamSource(xmlFile.getAbsolutePath()), xslFile, os);
		System.gc();
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void toPDF(Source source, File xslFile, OutputStream os) {
		toPDF(source, new StreamSource(xslFile), os);
		System.gc();
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void toPDF(Document source, Source xslFile, OutputStream os) {
		toPDF(new DOMSource(source), xslFile, os);
		System.gc();
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void foToPDF(File foFile, OutputStream out) {
		foToPDF(new StreamSource(foFile), out, null);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void foToPDF(File foFile, OutputStream out, URIResolver uriResolver) {
		foToPDF(new StreamSource(foFile), out, uriResolver);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void addFopConfigFile(InputStream configFile) {
		try {
			DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
			fopConfig = cfgBuilder.build(configFile);
		} catch (SAXException ex) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					"addFopConfigFile: impossible de parser la configuration :"
							+ ex.getMessage());
		} catch (IOException ex) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					"addFopConfigFile: impossible de lire le fichier de configuration :"
							+ ex.getMessage());
		} catch (ConfigurationException ex) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					"addFopConfigFile: impossible de charger la configuration :"
							+ ex.getMessage());
		}
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void addFopConfigFile(File configFile) {
		try {
			DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
			fopConfig = cfgBuilder.buildFromFile(configFile);
		} catch (SAXException ex) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					"addFopConfigFile: impossible de parser la configuration :"
							+ ex.getMessage());
		} catch (IOException ex) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					"addFopConfigFile: impossible de lire le fichier de configuration :"
							+ ex.getMessage());
		} catch (ConfigurationException ex) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					"addFopConfigFile: impossible de charger la configuration :"
							+ ex.getMessage());
		}
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void foToPDF(Source foFile, OutputStream out) {
		foToPDF(foFile, out, null);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void foToPDF(Source foFile, OutputStream out, URIResolver uriResolver) {
		try {
			FopFactory fopFactory = FopFactory.newInstance();
			fopFactory.setStrictValidation(strictValidationFOP);
			if (fopConfig != null) {
				fopFactory.setUserConfig(fopConfig);
			}
			if (getBaseFopFont() != null && !getBaseFopFont().isEmpty()) {
				try {
					fopFactory.getFontManager()
							.setFontBaseURL(getBaseFopFont());
				} catch (MalformedURLException e) {
					LogMes.log(
							XMLFactoryServiceImpl.class,
							LogMes.ERROR,
							"foToPDF: l'url est mal formater :"
									+ e.getMessage());
				}
			}

			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent,
					out);
			TransformerFactory factory = TransformerFactory.newInstance();
			if (uriResolver != null) {
				factory.setURIResolver(uriResolver);
			}
			Transformer transformer = factory.newTransformer();
			Result res = new SAXResult(fop.getDefaultHandler());
			transformer.transform(foFile, res);
		} catch (TransformerException ex) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					ex.getMessage());
			if (LogMes.isDebug(XMLFactoryServiceImpl.class)) {
				ex.printStackTrace();
			}
		} catch (FOPException ex) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					ex.getMessage());
			if (LogMes.isDebug(XMLFactoryServiceImpl.class)) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Deprecated
	private void toPDF(Source source, Source xslFile, OutputStream os) {
		toPDF(source, xslFile, os, null);
	}

	@Deprecated
	public void toPDF(Source source, Source xslFile, OutputStream os,
					  URIResolver uriResolver, EventListener eventListener, ErrorListener errorListener){

		try {
			// configure fopFactory as desired
			FopFactory fopFactory = FopFactory.newInstance();
			fopFactory.setStrictValidation(strictValidationFOP);
			if (fopConfig != null) {
				fopFactory.setUserConfig(fopConfig);
			}
			if (getBaseFopFont() != null && !getBaseFopFont().isEmpty()) {
				try {
					fopFactory.getFontManager()
							.setFontBaseURL(getBaseFopFont());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}

			// configure foUserAgent as desired
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
			foUserAgent.getEventBroadcaster().addEventListener(eventListener);

			// Construct fop with desired output format.
			Fop fop = fopFactory
					.newFop(MimeConstants.MIME_PDF, foUserAgent, os);

			// Resulting SAX events (the generated FO) must be piped through to
			// FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			TransformerFactory factory = TransformerFactory.newInstance();
			if (uriResolver != null) {
				factory.setURIResolver(uriResolver);
			}
			Transformer transformer = factory.newTransformer(xslFile);

			addTransformerParameters(transformer);

			transformer.setErrorListener(errorListener);
			transformer.transform(source, res);

		} catch (Exception e) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					e.getMessage());
			e.printStackTrace();
		}


	}
	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Deprecated
	private void toPDF(Source source, Source xslFile, OutputStream os,
			URIResolver uriResolver) {
		try {
			// configure fopFactory as desired
			FopFactory fopFactory = FopFactory.newInstance();
			fopFactory.setStrictValidation(strictValidationFOP);
			if (fopConfig != null) {
				fopFactory.setUserConfig(fopConfig);
			}
			if (getBaseFopFont() != null && !getBaseFopFont().isEmpty()) {
				try {
					fopFactory.getFontManager()
							.setFontBaseURL(getBaseFopFont());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}

			// configure foUserAgent as desired
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

			// Construct fop with desired output format.
			Fop fop = fopFactory
					.newFop(MimeConstants.MIME_PDF, foUserAgent, os);

			// Resulting SAX events (the generated FO) must be piped through to
			// FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			TransformerFactory factory = TransformerFactory.newInstance();
			if (uriResolver != null) {
				factory.setURIResolver(uriResolver);
			}
			Transformer transformer = factory.newTransformer(xslFile);

			addTransformerParameters(transformer);

			transformer.transform(source, res);

		} catch (Exception e) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void toFO(File xmlFile, File xslFile, OutputStream os) {
		toPDF(new StreamSource(xmlFile.getAbsolutePath()), xslFile, os);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void toFO(Document xmlDocument, InputStream xslFile, OutputStream os) {
		toFO(new DOMSource(xmlDocument), xslFile, os);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void toFO(Source source, InputStream xslFile, OutputStream os) {
		transform(source, xslFile, (new OutputStreamWriter(os)));
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void toFO(Document xmlDocument, InputStream xslFile,
			OutputStream os, URIResolver uriResolver) {
		transform(new DOMSource(xmlDocument), xslFile, (new OutputStreamWriter(
				os)), uriResolver);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void toFO(Source source, InputStream xslFile, OutputStream os,
			URIResolver uriResolver) {
		transform(source, xslFile, (new OutputStreamWriter(os)), uriResolver);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void toFo(Document xmlDocument, InputStream xslFile,
			BufferedOutputStream os) {
		toFO(new DOMSource(xmlDocument), xslFile, os);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void toFo(Document xmlDocument, InputStream xslFile,
			BufferedOutputStream os, URIResolver uriResolver) {
		toFO(new DOMSource(xmlDocument), xslFile, os, uriResolver);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Override
	@Deprecated
	public void toRTF(File xmlFile, File xslFile, OutputStream os) {
		toRTF(new StreamSource(xmlFile.getAbsolutePath()), xslFile, os);
	}

	/**
	 * @deprecated this method isn't thread-safe caused by parameters map,
	 *             please use PDFTransformService instead
	 */
	@Deprecated
	private void toRTF(Source source, File xslFile, OutputStream os) {
		try {
			// configure fopFactory as desired
			FopFactory fopFactory = FopFactory.newInstance();
			fopFactory.setStrictValidation(strictValidationFOP);
			if (fopConfig != null) {
				fopFactory.setUserConfig(fopConfig);
			}
			if (getBaseFopFont() != null && !getBaseFopFont().isEmpty()) {
				try {
					fopFactory.getFontManager()
							.setFontBaseURL(getBaseFopFont());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}

			// configure foUserAgent as desired
			FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

			// Construct fop with desired output format.
			Fop fop = fopFactory
					.newFop(MimeConstants.MIME_RTF, foUserAgent, os);

			// Resulting SAX events (the generated FO) must be piped through to
			// FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(
					xslFile));

			addTransformerParameters(transformer);

			transformer.transform(source, res);

		} catch (Exception e) {
			LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR,
					e.getMessage());
			// e.printStackTrace();
		}

	}

	/**
	 * @return the strictValidationFOP
	 */
	@Deprecated
	public boolean isStrictValidationFOP() {
		return strictValidationFOP;
	}

	/**
	 * @param strictValidationFOP
	 *            the strictValidationFOP to set
	 */
	@Deprecated
	public void setStrictValidationFOP(boolean strictValidationFOP) {
		this.strictValidationFOP = strictValidationFOP;
	}

	@Deprecated
	public String getOmitXmlDeclaration() {
		return omitXmlDeclaration;
	}

	@Deprecated
	public void setOmitXmlDeclaration(String omitXmlDeclaration) {
		this.omitXmlDeclaration = omitXmlDeclaration;
	}

	/**
	 * @return the baseFopFont
	 */
	@Override
	@Deprecated
	public String getBaseFopFont() {
		return baseFopFont;
	}

	/**
	 * @param baseFopFont
	 *            the baseFopFont to set
	 */
	@Override
	@Deprecated
	public void setBaseFopFont(String baseFopFont) {
		this.baseFopFont = baseFopFont;
	}

	@Deprecated
	private void addTransformerParameters(Transformer transformer) {
		if (m_parameters == null) {
			return;
		}

		Iterator<Map.Entry<String, String>> iteParams = m_parameters.entrySet()
				.iterator();

		while (iteParams.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iteParams
					.next();
			String strParamName = (String) entry.getKey();
			String strParamValue = (String) entry.getValue();

			transformer.setParameter(strParamName, strParamValue);
		}
	}
}
