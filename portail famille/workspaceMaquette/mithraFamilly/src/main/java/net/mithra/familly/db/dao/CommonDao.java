package net.mithra.familly.db.dao;

import java.io.Serializable;
import java.util.List;

import net.mithra.familly.db.exception.DBNONullException;
import net.mithra.familly.db.exception.DBNOUniqueException;
import net.mithra.familly.db.vo.common.Common;

/**
 *
 * @author FRebeche
 */
public interface CommonDao<T> extends Serializable {
    
    
 
    <T extends Common> T findOne(String id, Class<T> clazz);
    
    <T extends Common> void save(T t) throws DBNONullException,DBNOUniqueException;
    
    <T extends Common> T findById(String id, Class<T> clazz);
    
    /**
     * update codeInfo if existe
     * @param codeInfo 
     */
    <T extends Common> void update(T t);
    
    
    
    /**
     * add codeInfo if the idCodeInfo doesn't already exist
     * @param codeInfo
     * @throws DBNONullException : if havn't idCodeInfo
     * @throws DBNOUniqueException : if idCodeInfo not unique
     */
    <T extends Common> void add(T t) throws DBNONullException,DBNOUniqueException;
    
    
    /**
     * find codeInfo with the id of <param>codeInfo</param>
     * @param codeInfo
     * @return 
     */
    <T extends Common> T find(T t);
    
    /**
     * find all with the id of <param>codeInfo</param>
     * @param codeInfo
     * @return 
     */
    <T extends Common> List<T> findAll(Class<T> clazz);
    

    /**
     * delete codeInfo and all depends
     * @param codeInfo 
     */
    <T extends Common> void delete(T t);
    
        /**
     * {@inheritDoc}
     */
        <T extends Common> void deleteAll(Class<T> clazz);

    /**
     * drop collection in database
     * @param clazz
     */
    <T extends Common> void drop(Class<T> clazz);
 
    
 



}
