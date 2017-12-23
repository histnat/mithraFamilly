package net.mithra.familly.db.bo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.user.FaGroup;
import net.mithra.familly.db.vo.user.FaRole;
import net.mithra.familly.db.vo.user.FaUser;
import net.mithra.familly.security.model.UserOpen;

public interface DBUserService extends Serializable {

	/**
	 * 
	 * @param opUser
	 * @return
	 * @throws DBNOUniqueException 
	 * @throws DBNONullException 
	 */
	FaUser save(FaUser opUser) throws DBNONullException, DBNOUniqueException;

	/**
	 * 
	 * @param opUser
	 * @return
	 * @throws DBNOUniqueException 
	 * @throws DBNONullException 
	 */
	FaUser getOrSave(FaUser opUser) throws DBNONullException, DBNOUniqueException;

	/**
	 * 
	 * @param opUser
	 * @return
	 */
	FaUser findByAll(FaUser opUser);

	/**
	 * 
	 * @param opGroup
	 * @return
	 */
	FaGroup save(FaGroup opGroup) throws DBNONullException, DBNOUniqueException;
	
	/**
	 * 
	 * @param opGroup
	 * @return
	 * @throws DBNONullException
	 * @throws DBNOUniqueException
	 */
	FaGroup getOrSave(FaGroup opGroup) throws DBNONullException, DBNOUniqueException;

	/**
	 * 
	 * @param opGroup
	 * @return
	 */
	FaGroup findByAll(FaGroup opGroup);
	
	/**
	 * 
	 * @param opGroup
	 * @return
	 */
	FaRole save(FaRole opRole) throws DBNONullException, DBNOUniqueException;
	
	/**
	 * 
	 * @param opGroup
	 * @return
	 * @throws DBNONullException
	 * @throws DBNOUniqueException
	 */
	FaRole getOrSave(FaRole opRole) throws DBNONullException, DBNOUniqueException;

	/**
	 * 
	 * @param opGroup
	 * @return
	 */
	FaRole findByAll(FaRole opRole);

	FaUser findUserByLogin(String login);

	FaUser findUserById(String id);

	List<FaUser> listUser();

	List<FaGroup> listGroup();

	UserOpen getUserOpenByLogin(String uid);

	Collection<? extends GrantedAuthority> getGrantedAuthorities(FaUser user);
}
