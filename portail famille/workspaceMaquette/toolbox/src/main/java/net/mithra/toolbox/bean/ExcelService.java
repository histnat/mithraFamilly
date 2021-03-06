/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mithra.toolbox.bean;

import org.apache.poi.ss.usermodel.Row;

import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 *
 * @author frebeche
 */
public interface ExcelService  extends Serializable {
    
    public void writeReportToExcel(HashMap datas,String path);
    /**
     * The keys to datas hashmap have to be the Class of the objects in the list.
     * We use reflection to read the fields from class, so we know what columns the are in the excel sheet
     * (Ex: String.class -> List<String>)
     */
    public void export(HashMap datas,OutputStream output);
    /**
     * 
     * @param cl
     * @param row
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
	Object convertRowToObject(Class<?> cl, Row row)
			throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, NoSuchFieldException, SecurityException,
			NoSuchMethodException, InvocationTargetException;
            
}
