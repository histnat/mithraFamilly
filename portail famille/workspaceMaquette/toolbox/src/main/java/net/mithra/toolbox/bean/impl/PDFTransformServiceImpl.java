package net.mithra.toolbox.bean.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.fop.events.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import net.mithra.toolbox.LogMes;
import net.mithra.toolbox.bean.PDFTransformService;
import net.mithra.toolbox.bean.XSLTransformService;
import net.mithra.toolbox.bean.impl.XSLTransformServiceImpl.TransformParameters;

/**
 * Service specialized in PDF transformation (pdf, fo and rtf). Contains some methods with different input and output.
 * 
 * See XSLTransformerServiceImpl for the thread-safe compliant of parameters.
 * 
 * WARNING : fopConfig, baseFopFont and strictValidationFOP are not thread-safe. If a thread change one of this value, it change for all threads !!
 * Since this config doesn't change after set them, this is not a big deal, but if it become a problem, use XSLTransformerServiceImpl tricks for add thread-safe compliant
 * 
 * @author NCoquelet
 */
@Component("PDFTransformService")
public class PDFTransformServiceImpl implements PDFTransformService {

    private Configuration fopConfig = null;
    
    private String baseFopFont = null;
    
    private boolean strictValidationFOP = true;
	
	@Autowired
	@Qualifier("XSLTransformService")
	private XSLTransformService xslTransformService;
	
	@Override
	public void addTransformerParameter(String strParamName, String strParamValue) {
		xslTransformService.addTransformerParameter(strParamName, strParamValue);
	}

	@Override
	public void addTransformerParameter(String strParamName, boolean bParamValue) {
		xslTransformService.addTransformerParameter(strParamName, bParamValue);
	}

	@Override
	public void addTransformerParameter(String strParamName, int nParamValue) {
		xslTransformService.addTransformerParameter(strParamName, nParamValue);
	}
	
	@Override
	public void setTransformerParameter(TransformParameters param){
		xslTransformService.setTransformerParameter(param);
	}

	@Override
	public void clearTransformerParameters() {
		xslTransformService.clearTransformerParameters();
	}

    @Override
    public void addFopConfigFile(InputStream configFile) {
        try {
            DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
            fopConfig = cfgBuilder.build(configFile);
        } catch (SAXException ex) {
            LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR, "addFopConfigFile: impossible de parser la configuration :" + ex.getMessage());
        } catch (IOException ex) {
            LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR, "addFopConfigFile: impossible de lire le fichier de configuration :" + ex.getMessage());
        } catch (ConfigurationException ex) {
            LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR, "addFopConfigFile: impossible de charger la configuration :" + ex.getMessage());
        }
    }

    @Override
    public void addFopConfigFile(File configFile) {
        try {
            DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
            fopConfig = cfgBuilder.buildFromFile(configFile);
        } catch (SAXException ex) {
            LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR, "addFopConfigFile: impossible de parser la configuration :" + ex.getMessage());
        } catch (IOException ex) {
            LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR, "addFopConfigFile: impossible de lire le fichier de configuration :" + ex.getMessage());
        } catch (ConfigurationException ex) {
            LogMes.log(XMLFactoryServiceImpl.class, LogMes.ERROR, "addFopConfigFile: impossible de charger la configuration :" + ex.getMessage());
        }
    }

    @Override
    public void setBaseFopFont(String baseFopFont) {
        this.baseFopFont = baseFopFont;
    }

    @Override
    public void setStrictValidationFOP(boolean strictValidationFOP) {
        this.strictValidationFOP = strictValidationFOP;
    }
	
    // ---------- TO PDF ----------

	@Override
    public void toPDF(String strXML, InputStream xslFile, OutputStream os) {
        toPDF(new ByteArrayInputStream(strXML.getBytes()), xslFile, os);
    }

	@Override
    public void toPDF(File xmlFile, File xslFile, OutputStream os) {
        toPDF(new StreamSource(xmlFile.getAbsolutePath()), xslFile, os);
    }
	
	@Override
    public void toPDF(File xmlFile, InputStream xslFile, OutputStream os) {
        toPDF(new StreamSource(xmlFile.getAbsolutePath()), xslFile, os);
    }

	@Override
    public void toPDF(InputStream stream, InputStream xslFile, OutputStream os) {
        toPDF(new StreamSource(stream), xslFile, os);
    }
    
    @Override
    public void toPDF(Document xmlDoc, File xslFile, OutputStream os, URIResolver uriResolver) {
        toPDF(new DOMSource(xmlDoc), new StreamSource(xslFile.getAbsolutePath()), os, uriResolver);
    }
    
    @Override
    public void toPDFWithEx(Document doc, InputStream xslInputSource, java.io.ByteArrayOutputStream out, URIResolver uriResolver) throws FOPException {
    	toPDFWithEx(new DOMSource(doc), new StreamSource(xslInputSource), out,uriResolver);
    };

	@Override
    public void toPDF(Document xmlDocument, InputStream xslFile, OutputStream os) {
        toPDF(new DOMSource(xmlDocument), new StreamSource(xslFile), os);
    }

	@Override
    public void toPDF(Document xmlDocument, InputStream xslFile, OutputStream os, URIResolver uriResolver) {
        toPDF(new DOMSource(xmlDocument), new StreamSource(xslFile), os, uriResolver);
    }

    @Override
    public void toPDF(Document source, Source xslFile, OutputStream os) {
        toPDF(new DOMSource(source), xslFile, os);
    }

    @Override
    public void toPDF(Source source, File xslFile, OutputStream os) {
        toPDF(source, new StreamSource(xslFile), os);
    }

	@Override
    public void toPDF(Source source, InputStream xslFile, OutputStream os) {
        toPDF(source, new StreamSource(xslFile), os);
    }
    
    @Override
    public void toPDF(Source source, Source xslFile, OutputStream os) {
        toPDF(source, xslFile, os, null);
    }

	@Override
	public void toPDF(Source source, Source xslFile, OutputStream os, URIResolver uriResolver){
		toPDF(source, xslFile, os, MimeConstants.MIME_PDF, uriResolver);
    }  
	
	@Override
	public void toPDFWithEx(Source source, Source xslFile, OutputStream os, URIResolver uriResolver) throws FOPException{
		toPDFWithEx(source, xslFile, os, MimeConstants.MIME_PDF, uriResolver);
    }  
	
	private void toPDFWithEx(Source source, Source xslFile, OutputStream os, String outputFormat, URIResolver uriResolver) throws FOPException{        
        toPDFWithEx(source,xslFile,os,outputFormat,uriResolver,null,null);        
	}  
	
	private void toPDF(Source source, Source xslFile, OutputStream os, String outputFormat, URIResolver uriResolver){
        try {
        	toPDFWithEx(source,xslFile,os,outputFormat,uriResolver,null,null);
        } catch (Exception e) {
            LogMes.log(PDFTransformServiceImpl.class, LogMes.ERROR, e.getMessage());
            e.printStackTrace();
        }
    } 
	
	@Override
	public void toPDF(Source source, Source xslFile, OutputStream os, URIResolver uriResolver,	EventListener eventListener, ErrorListener errorListener) {
		try {
        	toPDFWithEx(source,xslFile,os,MimeConstants.MIME_PDF,uriResolver,eventListener,errorListener);
        } catch (Exception e) {
            LogMes.log(PDFTransformServiceImpl.class, LogMes.ERROR, e.getMessage());
            e.printStackTrace();
        }
	}
	
	private void toPDFWithEx(Source source, Source xslFile, OutputStream os, String outputFormat, URIResolver uriResolver,EventListener eventListener, ErrorListener errorListener) throws FOPException{
        
        FopFactory fopFactory = FopFactory.newInstance();
        
        fopFactory.setStrictValidation(strictValidationFOP);
        
        if (fopConfig != null) {
            fopFactory.setUserConfig(fopConfig);
        }
        
        if (baseFopFont != null && !baseFopFont.isEmpty()) {
            try {
                fopFactory.getFontManager().setFontBaseURL(baseFopFont);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        if (uriResolver != null) {
            fopFactory.setURIResolver(uriResolver);
        }
        // configure foUserAgent as desired
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        if(eventListener!=null)
        	foUserAgent.getEventBroadcaster().addEventListener(eventListener);

        // Construct fop with desired output format.
        Fop fop = fopFactory.newFop(outputFormat, foUserAgent, os);

        // Resulting SAX events (the generated FO) must be piped through to FOP
        Result res = new SAXResult(fop.getDefaultHandler());

        xslTransformService.transform(source, xslFile, res, uriResolver,errorListener);
    
}  
	
	
	
	 
    
    // ---------- FO TO PDF ----------
	
    @Override
    public void foToPDF(File foFile, OutputStream out) {
        foToPDF(new StreamSource(foFile), out, null);
    }

    @Override
    public void foToPDF(File foFile, OutputStream out, URIResolver uriResolver) {
        foToPDF(new StreamSource(foFile), out, uriResolver);
    }

    @Override
    public void foToPDF(Source foFile, OutputStream out) {
        foToPDF(foFile, out, null);
    }

    @Override
    public void foToPDF(Source foFile, OutputStream out, URIResolver uriResolver) {
        toPDF(foFile, null, out, uriResolver);
    }

	// ---------- TO FO ----------

    @Override
    public void toFO(File xmlFile, File xslFile, OutputStream os) {
        toPDF(new StreamSource(xmlFile.getAbsolutePath()), xslFile, os);
    }

    @Override
    public void toFO(Document xmlDocument, InputStream xslFile, OutputStream os) {
        toFO(new DOMSource(xmlDocument), xslFile, os);
    }

    @Override
    public void toFO(Document xmlDocument, InputStream xslFile, OutputStream os, URIResolver uriResolver) {
    	xslTransformService.transform(new DOMSource(xmlDocument), xslFile, new OutputStreamWriter(os), uriResolver);
    }

    @Override
    public void toFO(Source source, InputStream xslFile, OutputStream os) {
    	xslTransformService.transform(source, xslFile, new OutputStreamWriter(os));
    }

    @Override
    public void toFO(Source source, InputStream xslFile, OutputStream os, URIResolver uriResolver) {
        xslTransformService.transform(source, xslFile, new OutputStreamWriter(os), uriResolver);
    }
    
    // ---------- TO RTF ----------
	
    @Override
    public void toRTF(File xmlFile, File xslFile, OutputStream os) {
        toRTF(new StreamSource(xmlFile.getAbsolutePath()), new StreamSource(xslFile), os);
    }

    private void toRTF(Source source, Source xslFile, OutputStream os) {
    	toPDF(source, xslFile, os, MimeConstants.MIME_RTF, null);
    }
    
    // ---------- INJECTION ----------
	
    public void setXslTransformService(XSLTransformService xslTransformService) {
		this.xslTransformService = xslTransformService;
	}

}
