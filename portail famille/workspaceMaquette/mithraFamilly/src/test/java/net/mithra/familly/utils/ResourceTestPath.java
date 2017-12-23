package net.mithra.familly.utils;

public enum ResourceTestPath {

	MIN("/xml/min/","xsl/min/min.xsl",""),FRACT("/fractionnement/in/","xsl/min/min.xsl",""),CIR("/in/cir/","xsl/min/min.xsl",""),SGML("/sgml/in/","/sgml/dtd/honeywell/cmm.dtd",""),COBRA("/xml/cobra/","xsl/cobra/convCobra2ASD.xsl","/workflow/cobra/cobraWF.xml"),BADUD("/xml/badxml/","",""),TITUS("/xml/titus/","",""),HTML_TABLE_CALS("/xml/tableau2/","D:/developpement/workspacePaperpubSVN/openconv/target/test-classes/xsl/tableau2/dtd_ns_blinde_to_s1000d_v41.xsl","");
	
	private String  xml;
	
	private String xsl;
	
	private String wf;
	
	private ResourceTestPath(String xml,String xsl, String wf) {
		this.xml=xml;
		this.xsl=xsl;
		this.wf=wf;
	}

	/**
	 * @return the xml
	 */
	public String getXml() {
		return xml;
	}

	/**
	 * @param xml the xml to set
	 */
	public void setXml(String xml) {
		this.xml = xml;
	}

	/**
	 * @return the xsl
	 */
	public String getXsl() {
		return xsl;
	}

	/**
	 * @param xsl the xsl to set
	 */
	public void setXsl(String xsl) {
		this.xsl = xsl;
	}

	public String getWf() {
		return wf;
	}

	public void setWf(String wf) {
		this.wf = wf;
	}
	
	
	
}
