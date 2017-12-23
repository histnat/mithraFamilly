package net.mithra.familly.db.util;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Map;

/**
 * Created by SRobin on 19/10/2016
 */
public class CriteriaHelper {
    public static void criteriaFilter(Map<String, Object> filters, boolean exactMatchOption, Query query, String filter) {
        if (exactMatchOption) {
            query.addCriteria(Criteria.where(filter).is(filters.get(filter)));
        } else {
            if (filters.get(filter) instanceof String) {
                query.addCriteria(Criteria.where(filter).regex((String) filters.get(filter), "i"));
            } else {
                String[] filterList = (String[]) filters.get(filter);
                System.out.println("filterList taille:" + filterList.length);
                for (String filterValue : filterList) {
                    query.addCriteria(Criteria.where(filter).regex(filterValue, "i"));
                }
            }
        }
    }
}
