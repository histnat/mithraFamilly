/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mithra.toolbox.bean;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author frebeche
 */
public interface ImageService  extends Serializable {
    
    /**
     * Retourne le type mime de <code>sF</code> transmit en parametre
     * @param sF : File explore
     * @return type mime
     */
    public String findMimeOfFile(File sF);
    
    /**
     * Retourne le type mime de <code>sB</code> transmit en parametre
     * @param sB : byte[] explore
     * @return type mime
     */
    public String findMimeOfFile(byte[] sB);
    
    /**
     * Convert file cgm to jpegFile
     * @param cgmFile
     * @param jpegFile
     * @throws IOException 
     */
    public void convertCGMToJpeg(File cgmFile, File jpegFile) throws IOException;
            
}
