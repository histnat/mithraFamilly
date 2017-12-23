package net.mithra.familly.db.vo.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import net.mithra.familly.db.vo.common.Common;


@Document(collection = "FaUser")
public class FaUser extends Common {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3326597863129406690L;

	private String name;

    @NotNull
    @Indexed(unique = true)
	private String login;

	private String password;

	private String email;

	private Long avatar;

	private Date lastLogin;
	
	private int nbrFail;
	
    /**
     * user is blocked
     */
    private boolean blocked;
	
    /**
     * groupList
     */
    @DBRef(lazy = false)
    private List<FaGroup> groupList;
    
    	public FaUser(String name, String login, String password, String email, Long avatar, Date lastLogin, int nbrFail) {
		super();
		this.name = name;
		this.login = login;
		this.password = password;
		this.email = email;
		this.avatar = avatar;
		this.lastLogin = lastLogin;
		this.nbrFail = nbrFail;
	}

	public FaUser() {
		super();
		 groupList = new ArrayList<>();
	}

	public FaUser(String id) {
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getAvatar() {
		return avatar;
	}

	public void setAvatar(Long avatar) {
		this.avatar = avatar;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public int getNbrFail() {
		return nbrFail;
	}

	public void setNbrFail(int nbrFail) {
		this.nbrFail = nbrFail;
	}

	
	
	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public List<FaGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<FaGroup> groupList) {
		this.groupList = groupList;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((avatar == null) ? 0 : avatar.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((lastLogin == null) ? 0 : lastLogin.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + nbrFail;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((groupList == null) ? 0 : groupList.hashCode());
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
		FaUser other = (FaUser) obj;
		if (avatar == null) {
			if (other.avatar != null)
				return false;
		} else if (!avatar.equals(other.avatar))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (lastLogin == null) {
			if (other.lastLogin != null)
				return false;
		} else if (!lastLogin.equals(other.lastLogin))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nbrFail != other.nbrFail)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
        if (groupList == null) {
            if (other.groupList != null)
                return false;
        } else if (!groupList.equals(other.groupList))
            return false;
		return true;
	}

	@Override
	public String toString() {
		return "OpUser [name=" + name + ", login=" + login + ", password=" + password + ", email=" + email + ", avatar=" + avatar + ", lastLogin=" + lastLogin + ", nbrFail=" + nbrFail + ", roleList=" + groupList +"]";
	}

	

}
