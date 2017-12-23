package net.mithra.familly.db.vo.user;

import org.springframework.data.mongodb.core.mapping.Document;

import net.mithra.familly.db.vo.common.GeneralInformation;

@Document(collection = "FaRole")
public class FaRole extends GeneralInformation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3326597863129406690L;

	

    public FaRole() {
        super();
    }


    @Override
    public String toString() {
        return "OpRole [ getCode()=" + getCode() + ", getName()=" + getName() + ", getDescrs()=" + getDescrs() + ", getId()=" + getId() + "]";
    }

	

}
