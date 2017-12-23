package net.mithra.familly.db.vo.user;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import net.mithra.familly.db.vo.common.GeneralInformation;

@Document(collection = "FaGroup")
public class FaGroup extends GeneralInformation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3326597863129406690L;

	
	/**
     * roleList
     */
    @DBRef(lazy = false)
    private List<FaRole> roleList;

	
    
    public FaGroup() {
		super();
	}

	public List<FaRole> getRoleList() {
		return roleList;
	}
	
	public void addRole(FaRole role)
	{
		this.roleList.add(role);
	}
	

	public void setRoleList(List<FaRole> roleList) {
		this.roleList = roleList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((roleList == null) ? 0 : roleList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FaGroup other = (FaGroup) obj;
		if (roleList == null) {
			if (other.roleList != null)
				return false;
		} else if (!roleList.equals(other.roleList))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "group [roleList=" + roleList + ", getCode()=" + getCode() + ", getName()=" + getName() + ", getDescrs()=" + getDescrs() + ", getId()=" + getId() + "]";
	}
    
    
	
	

	

}
