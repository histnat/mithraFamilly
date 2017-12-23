package net.mithra.familly.db.dao.user.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.sedoc.toolbox.LogMes;
import net.mithra.familly.db.dao.OpGroupDao;
import net.mithra.familly.db.dao.OpRoleDao;
import net.mithra.familly.db.dao.impl.CommonDaoImpl;
import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.common.Common;
import net.mithra.familly.db.vo.user.FaGroup;
import net.mithra.familly.db.vo.user.FaRole;

@Repository(value="FaRoleDao")
public class FaRoleDaoImpl  extends CommonDaoImpl<FaRole> implements OpRoleDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2559203684440841983L;
	
	   @Override
	    public FaRole findByCode(String code) {
	        if (code == null) {
	            LogMes.log(FaRoleDaoImpl.class, LogMes.ERROR, "findByCode : code can't be null");
	            return null;
	        }
	        Query query = new Query();
	        query.addCriteria(
	                new Criteria().andOperator(
	                        Criteria.where("code").is(code)
	                )
	        );
	        return mongoTemplate.findOne(query, FaRole.class);

	    }


	    /**
	     * {@inheritDoc}
	     *
	     * @throws com.sedoc.sysdoc.dbno.exception.DBNONullException
	     * @throws com.sedoc.sysdoc.dbno.exception.DBNOUniqueException
	     */
	    @Override
	    public <T extends Common> void add(T t) throws DBNONullException, DBNOUniqueException {

	    	FaRole u = (FaRole) t;
	        if (!mongoTemplate.collectionExists(t.getClass())) {
	            mongoTemplate.createCollection(t.getClass());
	        }

	        FaRole testU = find(u);

	        if (u.getCode() == null || u.getCode().isEmpty()) {
	            throw new DBNONullException("Code can't be null :" + u);
	        }

	        if (testU != null) {
	            throw new DBNOUniqueException(" id  must be unique :" + testU.getId());
	        }

	        testU = findByCode(u.getCode());

	        if (testU != null) {
	            throw new DBNOUniqueException(" code  must be unique :" + testU.getCode());
	        }


	        ////System.out.println("CommonDao, add, t: " + t.toString());

	        //////////////////////////////////////////////////////////////
	        // très important ON NE SAUVEGARDE PAS testT MAIS t
	        ///////////////////////////////////////////////////////////////
	        mongoTemplate.insert(u);

	    }
}
