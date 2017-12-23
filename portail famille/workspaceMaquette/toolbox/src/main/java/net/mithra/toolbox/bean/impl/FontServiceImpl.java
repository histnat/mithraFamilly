/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mithra.toolbox.bean.impl;

import net.mithra.toolbox.LogMes;
import net.mithra.toolbox.bean.FontService;
import net.mithra.toolbox.bean.UtilsService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author frebeche
 */
@Service("FontService")
public class FontServiceImpl implements FontService, java.io.Serializable{

    @Autowired
    UtilsService utilsService;
    /**
     * Constante de convertion pour CombiNumerals
     */
    static HashMap<String,String> convertTableCombiNumerals = new HashMap<String,String>();
    
    static {
        convertTableCombiNumerals.put("1D","F021");
        convertTableCombiNumerals.put("2D","F040");
        convertTableCombiNumerals.put("3D","F023");
        convertTableCombiNumerals.put("4D","F024");
        convertTableCombiNumerals.put("5D","F025");
        convertTableCombiNumerals.put("6D","F05E");
        convertTableCombiNumerals.put("7D","F026");
        convertTableCombiNumerals.put("8D","F02A");
        convertTableCombiNumerals.put("9D","F028");
        convertTableCombiNumerals.put("0D","F029");
        
        convertTableCombiNumerals.put("0G","F030");       
        convertTableCombiNumerals.put("1G","F031");
        convertTableCombiNumerals.put("2G","F032");
        convertTableCombiNumerals.put("3G","F033");
        convertTableCombiNumerals.put("4G","F034");
        convertTableCombiNumerals.put("5G","F035");
        convertTableCombiNumerals.put("6G","F036");
        convertTableCombiNumerals.put("7G","F037");
        convertTableCombiNumerals.put("8G","F038");
        convertTableCombiNumerals.put("9G","F039");
        
        convertTableCombiNumerals.put("0","F070");
        convertTableCombiNumerals.put("1","F071");
        convertTableCombiNumerals.put("2","F077");
        convertTableCombiNumerals.put("3","F065");
        convertTableCombiNumerals.put("4","F072");
        convertTableCombiNumerals.put("5","F074");
        convertTableCombiNumerals.put("6","F079");
        convertTableCombiNumerals.put("7","F075");
        convertTableCombiNumerals.put("8","F069");
        convertTableCombiNumerals.put("9","F06F");
        convertTableCombiNumerals.put("10","F061");
        convertTableCombiNumerals.put("11","F073");
        convertTableCombiNumerals.put("12","F064");
        convertTableCombiNumerals.put("13","F066");
        convertTableCombiNumerals.put("14","F067");
        convertTableCombiNumerals.put("15","F068");
        convertTableCombiNumerals.put("16","F06A");
        convertTableCombiNumerals.put("17","F06B");
        convertTableCombiNumerals.put("18","F06C");
        convertTableCombiNumerals.put("19","F03B");
    }
    
    /**
     * Convertie la chaine resus pour la police CombiNumerals
     * @param sC : <code> String </code> a convertir
     * @return La chaine traduite
     */
    public String getCombiNumerals(String sC) {

        LogMes.log(FontServiceImpl.class, LogMes.DEBUG, "getCombiNumerals: avant netoyage:"+"sC:"+sC);
        sC = sC.replaceAll("[^\\d]", "");
        LogMes.log(FontServiceImpl.class, LogMes.DEBUG, "getCombiNumerals: après netoyage:"+"sC:"+sC);
        String result = convertTableCombiNumerals.get(sC);

        if(result==null)
        {
            result="";
            int t=sC.length();
            for(int i=0;i<t;i++)
            {
                String s=sC.charAt(i)+"";
                if(i==0)
                {
                    s+="G";
                }
                else if((i+1)==t)
                {
                    s+="D";
                }
                s = convertTableCombiNumerals.get(s);
                if(s!=null)
                {
                    LogMes.log(FontServiceImpl.class, LogMes.DEBUG, "getCombiNumerals: valeur convertie:"+s);
                    result += utilsService.convertHexUTF16ToString(s);
                }
            }
        }
        else
        {
            result = utilsService.convertHexUTF16ToString(result);
        }
        return result;
    }
    
}
