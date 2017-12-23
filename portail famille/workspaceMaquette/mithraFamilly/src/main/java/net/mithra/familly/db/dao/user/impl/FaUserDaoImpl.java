package net.mithra.familly.db.dao.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.sedoc.toolbox.LogMes;
import net.mithra.familly.db.dao.OpRoleDao;
import net.mithra.familly.db.dao.OpUserDao;
import net.mithra.familly.db.dao.impl.CommonDaoImpl;
import net.mithra.familly.db.vo.common.Common;
import net.mithra.familly.db.vo.user.FaRole;
import net.mithra.familly.db.vo.user.FaUser;

import static net.mithra.familly.db.util.CriteriaHelper.criteriaFilter;

@Repository(value="FaUserDao")
public class FaUserDaoImpl  extends CommonDaoImpl<FaUser> implements OpUserDao{
    private final static String DEFAULT_SORT_DIRECTION = "asc";
	/**
	 * 
	 */
	private static final long serialVersionUID = 2324361442980503029L;
	
    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Autowired
    OpRoleDao roleDao;
	
	@Override
	public FaUser findByAll(String name, String login) {
		Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("name").is(name),
                        Criteria.where("login").is(login)
                )
        );
        List<FaUser> lll= mongoTemplate.find(query, FaUser.class);

        if (lll != null && lll.size() > 0) {
            return lll.get(0);
        }
        return null;
	}

	
	
	@Override
	public FaUser findOne(String id)
	{
		return super.findOne(id, FaUser.class);
	}

	@Override
	public void deleteAll()
	{
		super.deleteAll(FaUser.class);
	}
	
	@Override
	public List<FaUser> findAll()
	{
		return super.findAll(FaUser.class);
	}



	@Override
	public FaUser findByLogin(String login) {
        if (login == null) {
            LogMes.log(FaUserDaoImpl.class, LogMes.ERROR, "findByLogin : login can't be null");
            return null;
        }
        Query query = new Query();
        query.addCriteria(
                new Criteria().andOperator(
                        Criteria.where("login").is(login)
                )
        );
        return mongoTemplate.findOne(query, FaUser.class);
	}






	@Override
	public long countUser(Map<String, Object> filters, boolean exactMatchOption) {
        Query query = new Query();
        // critere
        if (filters != null && !filters.isEmpty()) {
            for (String filter : filters.keySet()) {
                LogMes.log(FaUserDaoImpl.class, LogMes.DEBUG, "countUser:filter:" + filter + " : " + filters.get(filter));
                if ("idRole".equals(filter)) {
                    FaRole role = roleDao.findById((String) filters.get(filter), FaRole.class);
                    query.addCriteria(Criteria.where("roleList.$id").is(new ObjectId(role.getId())));

                } else {
                    if (exactMatchOption) {
                        query.addCriteria(Criteria.where(filter).is(filters.get(filter)));
                    } else {
                        if (filters.get(filter) instanceof String) {
                            query.addCriteria(Criteria.where(filter).regex((String) filters.get(filter), "i"));
                        } else {
                            String[] filterList = (String[]) filters.get(filter);
                            for (String filterValue : filterList) {
                                query.addCriteria(Criteria.where(filter).regex(filterValue, "i"));
                            }
                        }
                    }
                }
            }

        }
        return mongoTemplate.count(query, FaUser.class);
	}



	@Override
	public List<FaUser> findUser(int first, int pageSize, Map<String, String> multiSortMeta,
			Map<String, Object> filters, boolean exactMatchOption) {
		  Query query = new Query();
	        if (pageSize > 0) {
	            query.limit(pageSize);
	        }
	        query.skip(first);

	        // trie
	        if (multiSortMeta != null && !multiSortMeta.isEmpty()) {
	            for (String sort : multiSortMeta.keySet()) {

	                String sortDirection = multiSortMeta.get(sort);
	                if (sortDirection == null) {
	                    sortDirection = DEFAULT_SORT_DIRECTION;
	                }
	                LogMes.log(FaUserDaoImpl.class, LogMes.DEBUG, "findUser:sort:" + sort + " " + sortDirection);
	                Sort s = new Sort(Sort.Direction.fromString(sortDirection), sort);
	                query.with(s);
	            }
	        }

	        // init filters if not yet
	        if (filters == null) {
	            filters = new HashMap<>();
	        }

	        // critere
	        if (!filters.isEmpty()) {

	            for (String filter : filters.keySet()) {
	                LogMes.log(FaUserDaoImpl.class, LogMes.DEBUG, "findUser:filter:" + filter + " : " + filters.get(filter));
	                if ("idRole".equals(filter)) {
	                    FaRole role = roleDao.findById((String) filters.get(filter), FaRole.class);
	                    query.addCriteria(Criteria.where("roleList.$id").is(new ObjectId(role.getId())));
	                } else {
	                    criteriaFilter(filters, exactMatchOption, query, filter);
	                }
	            }
	        }

	        return mongoTemplate.find(query, FaUser.class);
	}


	
}
