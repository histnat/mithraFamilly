/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mithra.toolbox.bean;

import java.io.Serializable;

/**
 *
 * @author frebeche
 */
public interface PasswordService extends Serializable  {
    
    /**
     * Encode un mot de passe donné selon l'algorithme définie.
     * 
     * @param String password
     * @return String encryptedPassword
     */
    public  String encryptPassword(String password);
    
    /**
     * Vérifie la validité d'un mot de passe.
     * 
     * @param String password
     * @param String encryptedPassword
     * @return boolean
     */
    public boolean isPasswordValid(String password, String encryptedPassword); 
}
