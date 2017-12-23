/**
 * 
 */
package net.mithra.familly.db.cir;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.w3c.dom.Node;


@Component("SessionStockageRes")
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class SessionStockageRes implements Serializable {
	
 /**
	 * 
	 */
private static final long serialVersionUID = 1L;
private String keyHash;
 private Node dataXml;
 
 
public SessionStockageRes(String keyHash, Node dataXml) {
	super();
	this.keyHash = keyHash;
	this.dataXml = dataXml;
}
public String getKeyHash() {
	return keyHash;
}
public void setKeyHash(String keyHash) {
	this.keyHash = keyHash;
}
public Node getDataXml() {
	return dataXml;
}
public void setDataXml(Node dataXml) {
	this.dataXml = dataXml;
}
 
   

}
