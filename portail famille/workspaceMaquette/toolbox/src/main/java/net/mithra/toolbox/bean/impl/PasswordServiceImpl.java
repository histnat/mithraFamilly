/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mithra.toolbox.bean.impl;

import net.mithra.toolbox.bean.PasswordService;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;

/**
 *
 * @author frebeche
 */
@Service("PasswordService")
public class PasswordServiceImpl implements PasswordService {

    /**
     * Encode un mot de passe donné selon l'algorithme définie.
     * 
     * @param String password
     * @return String encryptedPassword
     */
	public String encryptPassword(String password) {
		LdapShaPasswordEncoder encoder = new LdapShaPasswordEncoder();
		return encoder.encodePassword(password, null);
	}
	
	/**
     * Vérifie la validité d'un mot de passe.
     * 
     * @param String password
     * @param String encryptedPassword
     * @return boolean
     */
	public boolean isPasswordValid(String password, String encryptedPassword) {
		LdapShaPasswordEncoder encoder = new LdapShaPasswordEncoder();
		return encoder.isPasswordValid(encryptedPassword, password, null);
	}
    
}
