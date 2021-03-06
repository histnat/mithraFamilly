package net.mithra.toolbox.bean.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import net.mithra.toolbox.LogMes;
import net.mithra.toolbox.bean.XSLTransformService;

/**
 * Service specialized in xsl transformation. Contains some methods with different input and output.
 * You can pass parameters with addTransformerParameter() methods and omit xml declaration with setOmitXmlDeclaration().
 * 
 * WARNING: Since this class is thread-safe, parameters are auto cleaned when thread is ended.
 * For example, in webapp container, each request create a new thread. There is no way to keep this parameters between request, or put it into your code logic.
 * 
 * In stand-alone application, since there is one thread, transform method can't be call in concurrence (or explicitly in your code) and paramaters are kept.
 * 
 * @author NCoquelet
 */
@Component("XSLTransformService")
public class XSLTransformServiceImpl implements XSLTransformService {

	private static final ThreadLocal<TransformParameters> threadSafeParameters = new ThreadLocal<TransformParameters>() {
		@Override
		protected TransformParameters initialValue() {
			return new TransformParameters();
		}
	};

	@Override
	public void addTransformerParameter(String strParamName, String strParamValue) {
		threadSafeParameters.get().putParam(strParamName, strParamValue);
	}

	@Override
	public void addTransformerParameter(String strParamName, boolean bParamValue) {
		threadSafeParameters.get().putParam(strParamName, String.valueOf(bParamValue));
	}

	@Override
	public void addTransformerParameter(String strParamName, int nParamValue) {
		threadSafeParameters.get().putParam(strParamName, String.valueOf(nParamValue));
	}
	
	@Override
	public void setTransformerParameter(TransformParameters param) {
		threadSafeParameters.set(param);
	}
	
	@Override
	public void clearTransformerParameters() {
		threadSafeParameters.get().getParams().clear();
	}

	@Override
	public void setOmitXmlDeclaration(String omitXmlDeclaration) {
		threadSafeParameters.get().setOmitXmlDeclaration(omitXmlDeclaration);
	}

	@Override
	public void transform(String strXML, File xslFile, Writer writer) {
		LogMes.log(XSLTransformServiceImpl.class, LogMes.DEBUG, "transformation à partir de la chaine: " + strXML);
		ByteArrayInputStream bais = new ByteArrayInputStream(strXML.getBytes());
		transform(new StreamSource(bais), xslFile, writer);
	}

	@Override
	public void transform(File xmlFile, File xslFile, Writer writer) {
		LogMes.log(XSLTransformServiceImpl.class, LogMes.DEBUG, "transformation à partir du fichier: " + xmlFile.getName());
		transform(new StreamSource(xmlFile.getAbsolutePath()), xslFile, writer);
	}

	@Override
	public void transform(InputStream xmlDocument, InputStream xslFile, Writer writer, URIResolver uriResolver) {
		transform(new StreamSource(xmlDocument), new StreamSource(xslFile), new StreamResult(writer), uriResolver);
	}
	
	@Override
	public void transformWithEx(InputStream xmlDocument, InputStream xslFile, Writer writer, URIResolver uriResolver) throws TransformerException {
		transformWithEx(new StreamSource(xmlDocument), new StreamSource(xslFile), new StreamResult(writer), uriResolver);
	}
	
	@Override
	public void transform(InputStream xmlDocument, InputStream xslFile, Writer writer) {
		transform(new StreamSource(xmlDocument), new StreamSource(xslFile), new StreamResult(writer));
	}
	
	@Override
	public void transform(InputStream xmlDocument, InputStream xslFile, OutputStream OutputStream) {
		transform(new StreamSource(xmlDocument), new StreamSource(xslFile), OutputStream);
	}
	
	@Override
	public void transformWithEx(InputStream xmlDocument, InputStream xslFile, OutputStream OutputStream) throws TransformerException {
		transformWithEx(new StreamSource(xmlDocument), new StreamSource(xslFile), OutputStream);
	}
	
	@Override
	public void transformWithEx(InputStream xmlDocument, InputStream xslFile, OutputStream OutputStream, URIResolver uriResolver) throws TransformerException {
		transformWithEx(new StreamSource(xmlDocument), new StreamSource(xslFile), OutputStream,uriResolver);
	}
	
	@Override
	public void transformWithEx(Document doc, InputStream xslFile, ByteArrayOutputStream out, URIResolver uriResolver) throws TransformerException {
		transformWithEx(new DOMSource(doc), new StreamSource(xslFile), out,uriResolver);
		
	}
	
	@Override
	public void transform(InputStream xmlDocument, InputStream xslFile, OutputStream OutputStream, URIResolver uriResolver) {
		transform(new StreamSource(xmlDocument), new StreamSource(xslFile), new StreamResult(OutputStream), uriResolver);
	}

	@Override
	public void transform(Document xmlDocument, File xslFile, Writer writer) {
		LogMes.log(XSLTransformServiceImpl.class, LogMes.DEBUG, "transformation à partir de l'objet DOM: " + xmlDocument);
		transform(new DOMSource(xmlDocument), xslFile, writer);
	}

	@Override
	public void transform(Document xmlDocument, InputStream xslFile, Writer writer) {
		transform(new DOMSource(xmlDocument), xslFile, writer);
	}

	@Override
	public void transform(Document xmlDocument, InputStream xslFile, Writer writer, URIResolver uriResolver) {
		transform(new DOMSource(xmlDocument), new StreamSource(xslFile), new StreamResult(writer), uriResolver);
	}

	@Override
	public void transform(Document xmlDocument, Source xslFile, Writer writer) {
		LogMes.log(XSLTransformServiceImpl.class, LogMes.DEBUG, "transformation à partir de l'objet DOM: " + xmlDocument);
		transform(new DOMSource(xmlDocument), xslFile, new StreamResult(writer));
	}

	@Override
	public void transform(Document xmlDocument, Source xslFile, Writer writer, URIResolver uriResolver) {
		LogMes.log(XSLTransformServiceImpl.class, LogMes.DEBUG, "transformation à partir de l'objet DOM: " + xmlDocument);
		transform(new DOMSource(xmlDocument), xslFile, new StreamResult(writer), uriResolver);
	}

	@Override
	public void transform(Source source, File xslFile, Writer writer) {
		transform(source, new StreamSource(xslFile.getAbsolutePath()), new StreamResult(writer));
	}

	@Override
	public void transform(Source source, File xslFile, OutputStream OutputStream) {
		transform(source, new StreamSource(xslFile.getAbsolutePath()), OutputStream);
	}

	@Override
	public void transform(Source source, InputStream xslFile, Writer writer) {
		transform(source, xslFile, writer, null);
	}

	@Override
	public void transform(Source source, InputStream xslFile, Writer writer, URIResolver uriResolver) {
		transform(source, new StreamSource(xslFile), new StreamResult(writer), uriResolver);
	}

	@Override
	public void transform(Source source, Source xslFile, OutputStream OutputStream) {
		transform(source, xslFile, new StreamResult(OutputStream));
	}
	
	@Override
	public void transformWithEx(Source source, Source xslFile, OutputStream OutputStream) throws TransformerException {
		transformWithEx(source, xslFile, new StreamResult(OutputStream));
	}
	
	@Override
	public void transformWithEx(Source source, Source xslFile, OutputStream OutputStream, URIResolver uriResolver) throws TransformerException {
		transformWithEx(source, xslFile, new StreamResult(OutputStream),uriResolver);
	}

	@Override
	public void transform(Source source, Source xslFile, Result OutputStream) {
		transform(source, xslFile, OutputStream, null);
	}
	
	@Override
	public void transformWithEx(Source source, Source xslFile, Result OutputStream) throws TransformerException {
		transformWithEx(source, xslFile, OutputStream, null);
	}

	@Override
	public void transform(Source source, Source xslFile, Result result, URIResolver uriResolver) {
		transform(source,xslFile,result,uriResolver,null);
	}
	
	@Override
	public void transform(Source source, Source xslFile, Result result, URIResolver uriResolver,
			ErrorListener errorListener) {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			if (uriResolver != null) {
				tFactory.setURIResolver(uriResolver);
			}
			Transformer transformer = null;
			if (xslFile == null) {
				transformer = tFactory.newTransformer();
			} else {
				transformer = tFactory.newTransformer(xslFile);
			}

			String omitXmlDeclaration = threadSafeParameters.get().isOmitXmlDeclaration();
			if (omitXmlDeclaration != null && !omitXmlDeclaration.isEmpty()) {
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, omitXmlDeclaration);
			}

			addTransformerParameters(transformer);
			if(errorListener!=null)
				transformer.setErrorListener(errorListener);
			transformer.transform(source, result);
		} catch (Exception e) {
			LogMes.log(XSLTransformServiceImpl.class, LogMes.ERROR, "transform : Exception lors de la transformation XML:2 " + e.getMessage());
			e.printStackTrace();
		} finally {
			threadSafeParameters.remove();
		}
		
	}
	
	@Override
	public void transformWithEx(Source source, Source xslFile, Result result, URIResolver uriResolver) throws TransformerException {
		try{
			TransformerFactory tFactory = TransformerFactory.newInstance();
			if (uriResolver != null) {
				tFactory.setURIResolver(uriResolver);
			}
			Transformer transformer = null;
			if (xslFile == null) {
				transformer = tFactory.newTransformer();
			} else {
				transformer = tFactory.newTransformer(xslFile);
			}

			String omitXmlDeclaration = threadSafeParameters.get().isOmitXmlDeclaration();
			if (omitXmlDeclaration != null && !omitXmlDeclaration.isEmpty()) {
				transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, omitXmlDeclaration);
			}

			addTransformerParameters(transformer);
			transformer.transform(source, result);
		}  finally {
			threadSafeParameters.remove();
		}
	}
	

	public void addTransformerParameters(Transformer transformer) {
		if (threadSafeParameters.get().getParams() == null) {
			return;
		}

		Iterator<Map.Entry<String, String>> iteParams = threadSafeParameters.get().getParams().entrySet().iterator();

		while (iteParams.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iteParams.next();
			String strParamName = (String) entry.getKey();
			String strParamValue = (String) entry.getValue();

			transformer.setParameter(strParamName, strParamValue);
		}
	}
	
	

	/**
	 * @return the threadsafeparameters
	 */
	protected static ThreadLocal<TransformParameters> getThreadsafeparameters() {
		return threadSafeParameters;
	}



	public static final class TransformParameters {

		private HashMap<String, String> params = new HashMap<String, String>();
		private String omitXmlDeclaration = null;
		
		public TransformParameters(){};
		
		public TransformParameters(String key, String value){
			putParam(key,value);
		}

		public HashMap<String, String> getParams() {
			return params;
		}

		public void putParam(String key, String value) {
			this.params.put(key, value);
		}

		public String isOmitXmlDeclaration() {
			return omitXmlDeclaration;
		}

		public void setOmitXmlDeclaration(String omitXmlDeclaration) {
			this.omitXmlDeclaration = omitXmlDeclaration;
		}
	}

}
