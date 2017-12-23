package net.mithra.familly.db.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.mithra.familly.db.vo.user.FaUser;

@Repository
public interface OpUserDao extends CommonDao<FaUser>, Serializable  {


	FaUser findOne(String id);

	void deleteAll();

	List<FaUser> findAll();

	FaUser findByAll(String name, String login);

	/**
	 * return user by login
	 * @param uid
	 * @return
	 */
	FaUser findByLogin(String uid);
	/**
	 * return number of result for given filter
	 * @param filters - filter applied
	 * @param exactMatchOption - if true the matching will be exact
	 * @return
	 */
	long countUser(Map<String, Object> filters, boolean exactMatchOption);
	
	List<FaUser> findUser(int first, int pageSize, Map<String, String> multiSortMeta, Map<String, Object> filters, boolean exactMatchOption);


}
