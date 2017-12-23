/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mithra.toolbox.xml;

import java.util.Iterator;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

/**
 *
 * @author frebeche
 */
public class XSLNamespaceContext implements NamespaceContext {

        public String getNamespaceURI(String prefix) {
                if (prefix == null)
                        throw new NullPointerException("Null prefix");
                else if ("dc".equals(prefix))
                        return "http://www.purl.org/dc/elements/1.1/";
                else if ("rdf".equals(prefix))
                        return "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
                else if ("xsl".equals(prefix))
                {
                    return "http://www.w3.org/1999/XSL/Transform";
                }
                else if ("xml".equals(prefix))
                        return XMLConstants.XML_NS_URI;
                return XMLConstants.NULL_NS_URI;
        }

        public String getPrefix(String namespaceURI){
            if("http://www.w3.org/1999/XSL/Transform".equals(namespaceURI)){
                return "xsl";
            } else {
                return null;
            }
        }
        
        public java.util.Iterator getPrefixes(String namespaceURI){
            return null;
        } 

	
}
