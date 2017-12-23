package net.mithra.familly.db.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import net.mithra.familly.db.vo.user.FaGroup;
import net.mithra.familly.db.vo.user.FaRole;

@Repository
public interface OpRoleDao extends CommonDao<FaRole>, Serializable  {


	/**
	 * find role by is code
	 * @param code
	 * @return
	 */
	FaRole findByCode(String code);

	
}
