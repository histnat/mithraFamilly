/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.mithra.familly.db.dao.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import net.mithra.familly.db.dao.CommonDao;
import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.common.Common;

/**
 * @author shordoir
 */


public class CommonDaoImpl<T> implements CommonDao<T> {


    @Qualifier("mongoTemplate")
    @Autowired
    protected MongoTemplate mongoTemplate;

    @Override
    public <T extends Common> T findOne(String id, Class<T> clazz) {
    	
        return mongoTemplate.findById(id, clazz);
    }


    /**
     * {@inheritDoc}
     *
     * @throws com.sedoc.sysdoc.dbno.exception.DBNONullException
     * @throws com.sedoc.sysdoc.dbno.exception.DBNOUniqueException
     */

    @Override
    public <T extends Common> void save(T t) throws DBNONullException, DBNOUniqueException {
        if (t.getId() != null || find(t) != null) {
            update(t);
        } else {
            add(t);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Common> void update(T t) {
        mongoTemplate.save(t);
    }

    /**
     * {@inheritDoc}
     *
     * @throws com.sedoc.sysdoc.dbno.exception.DBNONullException
     * @throws com.sedoc.sysdoc.dbno.exception.DBNOUniqueException
     */
    @Override
    public <T extends Common> void add(T t) throws DBNONullException, DBNOUniqueException {

        if (!mongoTemplate.collectionExists(t.getClass())) {
            mongoTemplate.createCollection(t.getClass());
        }

        T testT = find(t);

        if (testT != null) {
            throw new DBNOUniqueException(" id  must be unique :" + testT.getId());
        }

        ////System.out.println("CommonDao, add, t: " + t.toString());

        //////////////////////////////////////////////////////////////
        // très important ON NE SAUVEGARDE PAS testT MAIS t
        ///////////////////////////////////////////////////////////////
        mongoTemplate.insert(t);

    }


    /**
     * {@inheritDoc}
     *
     * @param <T>
     * @param t
     * @return Common au lieu de T parce qu
     */
    @Override
    public <T extends Common> T find(T t) {
        return (T) mongoTemplate.findById(t.getId(), t.getClass());
    }

    /**
     * {@inheritDoc}
     *
     * @param <T>
     * @param t
     * @return Common au lieu de T parce qu
     */
    @Override
    public <T extends Common> List<T> findAll(Class<T> clazz) {
        return mongoTemplate.findAll(clazz);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public <T extends Common> void delete(T t) {
        mongoTemplate.remove(t);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Common> void deleteAll(Class<T> clazz) {
        mongoTemplate.remove(new Query(), clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends Common> void drop(Class<T> clazz) {
        mongoTemplate.dropCollection(clazz);
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


	@Override
	public <T extends Common> T findById(String id, Class<T> clazz) {
        return mongoTemplate.findById(id, clazz);
	}


}
