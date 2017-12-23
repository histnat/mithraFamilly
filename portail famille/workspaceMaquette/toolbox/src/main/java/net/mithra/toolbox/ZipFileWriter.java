package net.mithra.toolbox;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Compresser des fichiers dans un ZIP avec ZipOutputStream
 * 
 * @author Fobec 2011
 */
public class ZipFileWriter {

        /**
         * Flux de l'archive zip
         */
        private ZipOutputStream zos;
        
        /**
         * Constructor: recupere un ZipOutputStream
         * 
         * @param zipFile
         * @throws FileNotFoundException
         */
        public ZipFileWriter(ZipOutputStream zipFile) {
                this.zos = zipFile;
        }


        /**
         * Constructor: creation d'une nouvelle archive
         * 
         * @param zipFile
         * @throws FileNotFoundException
         */
        public ZipFileWriter(String zipFile) throws FileNotFoundException {
                FileOutputStream fos = new FileOutputStream(zipFile);
                CheckedOutputStream checksum = new CheckedOutputStream(fos, new Adler32());
                this.zos = new ZipOutputStream(new BufferedOutputStream(checksum));
        }

        /**
         * Ajouter un fichier au zip
         * 
         * @param fileName
         * @throws FileNotFoundException
         * @throws IOException
         */
        public void addFile(InputStream fileName, String pathInZip) throws FileNotFoundException,
                        IOException {
                int size = 0;
                byte[] buffer = new byte[2048];

                
                ZipEntry zipEntry = new ZipEntry(pathInZip);
                this.zos.putNextEntry(zipEntry);

                while ((size = fileName.read(buffer, 0, buffer.length)) > 0) {
                        this.zos.write(buffer, 0, size);
                }

                this.zos.closeEntry();
                fileName.close();
        }
        
        public void addFile(File fileName, String pathInZip) throws FileNotFoundException, IOException
        {
            if(fileName==null || !fileName.exists())
            {
            	throw new FileNotFoundException(pathInZip);
            }
        	addFile(new FileInputStream(fileName),pathInZip);
        }
        
        public void addFile(File fileName) throws FileNotFoundException, IOException
        {
        	addFile(new FileInputStream(fileName),fileName.getName());
        }
        
        public void addFile(String fileName) throws FileNotFoundException, IOException
        {
        	File file = new File(fileName);
        	addFile(file);
        }

        /**
         * Fermer le fichier zip
         * 
         * @throws IOException
         */
        public void close() throws IOException {
                this.zos.close();
        }
}
