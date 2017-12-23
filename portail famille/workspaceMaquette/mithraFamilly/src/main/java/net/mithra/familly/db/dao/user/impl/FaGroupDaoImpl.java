package net.mithra.familly.db.dao.user.impl;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import net.mithra.familly.db.dao.OpGroupDao;
import net.mithra.familly.db.dao.impl.CommonDaoImpl;
import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.common.Common;
import net.mithra.familly.db.vo.user.FaGroup;

@Repository(value="FaGroupDao")
public class FaGroupDaoImpl  extends CommonDaoImpl<FaGroup> implements OpGroupDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2559203684440841983L;
	
    @Override
    public FaGroup findByCode(String code) {
        if (code == null) {
            LogMes.log(FaGroupDaoImpl.class, LogMes.ERROR, "findByCode : code can't be null");
            return null;
        }
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("code").is(code)
                )
        );
        return mongoTemplate.findOne(query, FaGroup.class);

    }

    /**
     * {@inheritDoc}
     *
     * @throws com.sedoc.sysdoc.dbno.exception.DBNONullException
     * @throws com.sedoc.sysdoc.dbno.exception.DBNOUniqueException
     */
    @Override
    public <T extends Common> void add(T t) throws DBNONullException, DBNOUniqueException {

    	FaGroup u = (FaGroup) t;
        if (!mongoTemplate.collectionExists(t.getClass())) {
            mongoTemplate.createCollection(t.getClass());
        }

        FaGroup testU = find(u);

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

        mongoTemplate.insert(u);

    }
}
