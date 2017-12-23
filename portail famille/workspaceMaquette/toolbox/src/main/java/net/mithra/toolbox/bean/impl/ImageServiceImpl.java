
package net.mithra.toolbox.bean.impl;

import net.mithra.toolbox.LogMes;
import net.mithra.toolbox.bean.ImageService;

import java.awt.image.BufferedImage;
//import eu.medsea.mimeutil.MimeType;
//import eu.medsea.mimeutil.MimeUtil;
//import eu.medsea.mimeutil.MimeUtil2;
//import eu.medsea.mimeutil.detector.OpendesktopMimeDetector;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;


/**
 *
 * @author frebeche
 */
@Service("ImageService")
public class ImageServiceImpl implements ImageService{
    
     
     //static OpendesktopMimeDetector o;

    
    /**
     * Retourne le type mime de <code>sF</code> transmit en parametre
     * @param sF : File explore
     * @return type mime
     */
    @Override
    public String findMimeOfFile(File sF){  
       /* if(o==null)
        {
            o = new OpendesktopMimeDetector(getClass().getClassLoader().getResource("mime.cache").getPath());
        }
        Collection<String> mt=o.getMimeTypes(sF);
        if(mt!=null)
        {
            for(String m:mt)
            {
                return m.toString();
            }
        }*/
        return null;
    }
    
    
     /**
     * Retourne le type mime de <code>sB</code> transmit en parametre
     * @param sB : byte[] explore
     * @return type mime
     */
    @Override
    public String findMimeOfFile(byte[] sB){      
       /* if(o==null)
        {
            o = new OpendesktopMimeDetector(getClass().getClassLoader().getResource("mime.cache").getPath());
        }
        System.out.println("r:"+getClass().getClassLoader().getResource("mime.cache").getPath());
        Collection<String> mt=o.getMimeTypes(sB);
        if(mt!=null)
        {
            for(String m:mt)
            {
                return m.toString();
            }
        }*/
        return null;
        
    }
    
    @Override
    public void convertCGMToJpeg(File cgmFile, File jpegFile) throws IOException {
    	BufferedImage image = ImageIO.read(cgmFile);
    	ImageIO.write(image, "JPEG", jpegFile);
    	
    }
}
