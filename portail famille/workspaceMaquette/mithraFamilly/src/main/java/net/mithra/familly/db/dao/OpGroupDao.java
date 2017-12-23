package net.mithra.familly.db.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import net.mithra.familly.db.vo.user.FaGroup;

@Repository
public interface OpGroupDao extends CommonDao<FaGroup>, Serializable  {

	/**
	 * find group by is code
	 * @param code
	 * @return
	 */
	FaGroup findByCode(String code);

	
}
