/**
 * 
 */
package net.mithra.familly.db.bo.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import net.mithra.familly.db.bo.DBUserService;
import net.mithra.familly.db.dao.OpGroupDao;
import net.mithra.familly.db.dao.OpRoleDao;
import net.mithra.familly.db.dao.OpUserDao;
import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.user.FaGroup;
import net.mithra.familly.db.vo.user.FaRole;
import net.mithra.familly.db.vo.user.FaUser;
import net.mithra.familly.security.model.UserOpen;

/**
 * @author Frebeche
 *
 */
@Service("DBUserService")
@PropertySource(value = "classpath:openconv.properties")
public class DBUserServiceImpl implements DBUserService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -698253837007835546L;

	@Autowired
	private OpUserDao opUserDao;
	
	@Autowired
	private OpGroupDao opGroupDao;
	
	@Autowired
	private OpRoleDao opRoleDao;

//	
////	OpUser
//	
//	
	@Override
	public FaUser getOrSave(FaUser opUser) throws DBNONullException, DBNOUniqueException{ 
		FaUser ou = findByAll(opUser);
		if (ou == null) {
			opUserDao.save(opUser);
		} else {
			opUser = ou;
		}

		return opUser;
	}

	@Override
	public FaUser findByAll(FaUser opUser) {
		return opUserDao.findByAll(opUser.getName(), opUser.getLogin());
	}

	@Override
	public FaUser save(FaUser opUser) throws DBNONullException, DBNOUniqueException {
		opUserDao.save(opUser);
		return opUser;
	}
	
    @Override
    public FaUser findUserByLogin(String login) {
        return opUserDao.findByLogin(login);
    }
    

    @Override
    public FaUser findUserById(String id) {
        return opUserDao.findById(id, FaUser.class);
    }
    
    @Override
    public List<FaUser> listUser() {
        return opUserDao.findAll(FaUser.class);
    }
	
    
    @Override
    public UserOpen getUserOpenByLogin(String uid) {
    	UserOpen newUser = null;
    	FaUser user = opUserDao.findByLogin(uid);
        if (null != user) {
            newUser = new UserOpen();
            newUser.setUid(user.getLogin());
            newUser.setBlocked(!user.isBlocked());
            newUser.setNom(user.getName());
            newUser.setPrenom(user.getName());
            newUser.setPassword(user.getPassword());
            newUser.setMail(user.getEmail());
        }

        // chargement des roles
        if (null != user) {
//            LogMes.log(DBUserServiceImpl.class, LogMes.DEBUG, "user=" + user);
            newUser.setAuthorities(getGrantedAuthorities(user));
//            LogMes.log(UserServiceImpl.class, LogMes.DEBUG, "authorities=" + newUser.getAuthorities());
        }
        return newUser;

    }
    
    
    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(FaUser user) {
        List<FaGroup> groups = user.getGroupList();

        Collection<SimpleGrantedAuthority> col = new HashSet<>();
        if (groups != null) {
            for (FaGroup group : groups) {
                List<FaRole> roles = group.getRoleList();

                if (roles != null) {
                    for (FaRole r : roles) {
                        col.add(new SimpleGrantedAuthority(r.getCode()));
                    }
                }
            }
        }
        return col;
    }
	
//	
////OpGroup
//
//
	
	@Override
	public FaGroup getOrSave(FaGroup opGroup) throws DBNONullException, DBNOUniqueException{ 
		FaGroup ou = findByAll(opGroup);
		if (ou == null) {
			opGroupDao.save(opGroup);
		} else {
			opGroup = ou;
		}

		return opGroup;
	}


	@Override
	public FaGroup findByAll(FaGroup opGroup) {
		return opGroupDao.findByCode(opGroup.getCode());
	}

	@Override
	public FaGroup save(FaGroup opGroup) throws DBNONullException, DBNOUniqueException {
		opGroupDao.save(opGroup);
		return opGroup;
	}	
	
    @Override
    public List<FaGroup> listGroup() {
        return opGroupDao.findAll(FaGroup.class);
    }
	
//	
////OpRole
//
//
	
	@Override
	public FaRole save(FaRole opRole) throws DBNONullException, DBNOUniqueException {
		opRoleDao.save(opRole);
		return opRole;
	}

	@Override
	public FaRole getOrSave(FaRole opRole) throws DBNONullException, DBNOUniqueException {
		FaRole ou = findByAll(opRole);
		if (ou == null) {
			opRoleDao.save(opRole);
		} else {
			opRole = ou;
		}

		return opRole;
	}

	@Override
	public FaRole findByAll(FaRole opRole) {
		return opRoleDao.findByCode(opRole.getCode());
	}


	

	
	public OpUserDao getOpUserDao() {
		return opUserDao;
	}

	public void setOpUserDao(OpUserDao opUserDao) {
		this.opUserDao = opUserDao;
	}

	public OpGroupDao getOpGroupDao() {
		return opGroupDao;
	}

	public void setOpGroupDao(OpGroupDao opGroupDao) {
		this.opGroupDao = opGroupDao;
	}

	public OpRoleDao getOpRoleDao() {
		return opRoleDao;
	}

	public void setOpRoleDao(OpRoleDao opRoleDao) {
		this.opRoleDao = opRoleDao;
	}



}
