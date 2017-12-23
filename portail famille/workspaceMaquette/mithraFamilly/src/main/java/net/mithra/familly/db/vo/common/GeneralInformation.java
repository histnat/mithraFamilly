/**
 * 
 */
package net.mithra.familly.db.vo.common;

import java.util.HashMap;

/**
 * @author frebeche
 *
 */
public class GeneralInformation extends Common {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5300176755415920245L;

	private String code;

    private HashMap<String, String> name;

    private HashMap<String, String> descrs;


    public GeneralInformation() {
        name = new HashMap<>();
        descrs = new HashMap<>();
    }

    public String getName(String idLangue) {
        return name.get(idLangue);
    }

    public void addName(String idLangue, String name) {
        this.name.put(idLangue, name);
    }

    public String getDescr(String idLangue) {
        return descrs.get(idLangue);
    }

    public void addDescr(String idLangue, String descr) {
        this.descrs.put(idLangue, descr);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public HashMap<String, String> getName() {
        return name;
    }

    public void setName(HashMap<String, String> name) {
        this.name = name;
    }

    public HashMap<String, String> getDescrs() {
        return descrs;
    }

    public void setDescrs(HashMap<String, String> descrs) {
        this.descrs = descrs;
    }

}
