package net.mithra.toolbox.bean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.URIResolver;

import org.apache.fop.apps.FOPException;
import org.apache.fop.events.EventListener;
import org.w3c.dom.Document;

import net.mithra.toolbox.bean.impl.XSLTransformServiceImpl.TransformParameters;

public interface PDFTransformService {

	void addTransformerParameter(String strParamName, String strParamValue);

	void addTransformerParameter(String strParamName, boolean bParamValue);

	void addTransformerParameter(String strParamName, int nParamValue);

	void clearTransformerParameters();
	
	void setTransformerParameter(TransformParameters param);

	void setBaseFopFont(String baseFopFont);

	void setStrictValidationFOP(boolean strictValidationFOP);

	void addFopConfigFile(InputStream configFile);

	void addFopConfigFile(File configFile);

	void toPDF(String strXML, InputStream xslFile, OutputStream os);

	void toPDF(File xmlFile, File xslFile, OutputStream os);

	void toPDF(File xmlFile, InputStream xslFile, OutputStream os);

	void toPDF(InputStream stream, InputStream xslFile, OutputStream os);

	void toPDF(Document xmlDocument, File xslFile, OutputStream os, URIResolver uriResolver);

	void toPDF(Document xmlDocument, InputStream xslFile, OutputStream os);

	void toPDF(Document xmlDocument, InputStream xslFile, OutputStream os, URIResolver uriResolver);

	void toPDF(Document source, Source xslFile, OutputStream os);

	void toPDF(Source source, File xslFile, OutputStream os);

	void toPDF(Source source, InputStream xslFile, OutputStream os);

	void toPDF(Source source, Source xslFile, OutputStream os);

	void toPDF(Source source, Source xslFile, OutputStream os, URIResolver uriResolver);

	void toPDF(Source source, Source xslFile, OutputStream os, URIResolver uriResolver, EventListener eventListener, ErrorListener errorListener);
	
	void toPDFWithEx(Document doc, InputStream xslInputSource, ByteArrayOutputStream out, URIResolver uriResolver) throws FOPException;
	

	void foToPDF(File foFile, OutputStream out);

	void foToPDF(File foFile, OutputStream out, URIResolver uriResolver);

	void foToPDF(Source foFile, OutputStream out);

	void foToPDF(Source foFile, OutputStream out, URIResolver uriResolver);

	void toFO(File xmlFile, File xslFile, OutputStream os);

	void toFO(Document xmlDocument, InputStream xslFile, OutputStream os);

	void toFO(Document xmlDocument, InputStream xslFile, OutputStream os, URIResolver uriResolver);

	void toFO(Source source, InputStream xslFile, OutputStream os);

	void toFO(Source source, InputStream xslFile, OutputStream os, URIResolver uriResolver);

	void toRTF(File xmlFile, File xslFile, OutputStream os);

	void toPDFWithEx(Source source, Source xslFile, OutputStream os, URIResolver uriResolver) throws FOPException;

	

}
