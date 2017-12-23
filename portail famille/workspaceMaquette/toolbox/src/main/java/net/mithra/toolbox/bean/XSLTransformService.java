package net.mithra.toolbox.bean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;

import org.w3c.dom.Document;

import net.mithra.toolbox.bean.impl.XSLTransformServiceImpl.TransformParameters;

public interface XSLTransformService {

	public void addTransformerParameter(String strParamName, String strParamValue);

	public void addTransformerParameter(String strParamName, boolean bParamValue);

	public void addTransformerParameter(String strParamName, int nParamValue);

	public void setTransformerParameter(TransformParameters param);
	
	public void setOmitXmlDeclaration(String omitXmlDeclaration);

	public void clearTransformerParameters();

	public void transform(String strXML, File xslFile, Writer writer);

	public void transform(File xmlFile, File xslFile, Writer writer);

	public void transform(InputStream xmlDocument, InputStream xslFile, Writer writer, URIResolver uriResolver);

	public void transform(Document xmlDocument, File xslFile, Writer writer);

	public void transform(Document xmlDocument, InputStream xslFile, Writer writer);

	public void transform(Document xmlDocument, InputStream xslFile, Writer writer, URIResolver uriResolver);

	public void transform(Document xmlDocument, Source xslFile, Writer writer);

	public void transform(Document xmlDocument, Source xslFile, Writer writer, URIResolver uriResolver);

	public void transform(Source source, File xslFile, OutputStream OutputStream);

	public void transform(Source source, File xslFile, Writer writer);

	public void transform(Source source, InputStream xslFile, Writer writer);

	public void transform(Source source, InputStream xslFile, Writer writer, URIResolver uriResolver);

	public void transform(Source source, Source xslFile, OutputStream OutputStream);

	public void transform(Source source, Source xslFile, Result OutputStream);

	public void transform(Source source, Source xslFile, Result result, URIResolver uriResolver);

	public void transform(InputStream xmlDocument, InputStream xslFile, Writer writer);

	public void transform(InputStream xmlDocument, InputStream xslFile, OutputStream OutputStream, URIResolver uriResolver);

	public void transform(InputStream xmlDocument, InputStream xslFile, OutputStream OutputStream);

	public void transformWithEx(Source source, Source xslFile, Result result, URIResolver uriResolver) throws TransformerConfigurationException, TransformerException;

	public void transformWithEx(InputStream xmlDocument, InputStream xslFile, Writer writer, URIResolver uriResolver) throws TransformerConfigurationException, TransformerException;

	void transformWithEx(Source source, Source xslFile, Result OutputStream) throws TransformerException;

	void transformWithEx(Source source, Source xslFile, OutputStream OutputStream) throws TransformerException;

	void transformWithEx(InputStream xmlDocument, InputStream xslFile, OutputStream OutputStream) throws TransformerException;

	void transformWithEx(Source source, Source xslFile, OutputStream OutputStream, URIResolver uriResolver)	throws TransformerException;

	void transformWithEx(InputStream xmlDocument, InputStream xslFile, OutputStream OutputStream,URIResolver uriResolver) throws TransformerException;

	void transformWithEx(Document xmlDocument, InputStream xslFile, ByteArrayOutputStream out, URIResolver uriResolver) throws TransformerException;

	void transform(Source source, Source xslFile, Result res, URIResolver uriResolver,	ErrorListener errorListener);


	
}
