/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.mithra.toolbox.bean;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * @author Frebeche
 */
public interface UtilsService extends Serializable {

    /**
     * escape caracter &'"<> for xml
     *
     * @param chaine
     * @return
     */
    public String escapeXML(String chaine);

    public String convertStringToHex(String str);

    public String convertHexToString(String hex);

    public int convertStringHexToHex(String str);

    public String convertHexUTF16ToString(String hex);

    /**
     * DoubleToInt : convertie un Double en int
     *
     * @param sF
     * @return
     */
    int doubleToInt(Double sF);

    /**
     * FloatToInt : convertie un Float en int
     *
     * @param sF
     * @return
     */
    int floatToInt(Float sF);

    /**
     * convertie StreamSource to StringBuffer
     *
     * @param w
     * @return
     */
    StringBuffer streamSourceToStringBuffer(StreamSource sc);

    StringBuffer stringWriterToStringBuffer(StringWriter sw);

    /**
     * convertie Writer to inputStream
     *
     * @param w
     * @return
     */
    InputStream writerToInputStream(Writer w);

    String replaceSpaceByUnderscore(String sS);

    /**
     * convertie un String en float
     *
     * @param s
     * @return
     */
    float strToFloat(String s);

    /**
     * convertie un String en int
     *
     * @param s
     * @return
     */
    int strToInt(String s);

    public InputStream getXSLFile(String xslFile);

    public InputStream getFile(String xslFile);

    /**
     * renvoie le fichier xslfo situé dans les ressource formulaire/xslfo
     *
     * @param xslFile nom du fichier avec sont extension
     * @return
     */
    public InputStream getXSLFOFile(String xslFile);

    public org.w3c.dom.Document convertToDOM(org.jdom.Document jdomDoc);

    public String convertDOMToString(org.w3c.dom.Document documentDOM);

    public org.jdom.Document convertDOMtoJDOM(org.w3c.dom.Document documentDOM) throws Exception;
    
    public org.w3c.dom.Document loadDOMfromXML(String xml) throws Exception;

    public void afficheXML(Document document);

    public void afficheXML(org.jdom.Document document);

    /**
     * Ecrit le contenue de is dans un fichier  dont le nom est transmit dans fileName
     *
     * @param is       : contenue a écrire
     * @param fileName : nom du fichier
     */
    public void ecritFile(InputStream is, String fileName);

    /**
     * Convertie un ByteArrayOutputStream en InputStream
     *
     * @param out :ByteArrayOutputStream convertie en inputStream
     * @return
     */
    public InputStream outputStreamToInputStream(ByteArrayOutputStream out);

    /**
     * Supprime le dossier transmit en parametre
     *
     * @param d dossier supprime
     */
    public void fileDeleteFolder(File d);

    /**
     * Supprime le fichier transmit en parametre
     *
     * @param d fichier supprime
     */
    public void fileDeleteFile(File fichier);

    /**
     * ecrit dans f le contenue de is
     *
     * @param is
     * @param f
     */
    public void fileWrite(InputStream is, File f);

    /**
     * copy source dans destination
     *
     * @param source
     * @param destination
     */
    public boolean fileCopier(File source, File destination);

    /**
     * convertie l'is en tableau de byte
     *
     * @param is :  InputStream convertie en byteArray
     * @return byte[]
     */
    public byte[] inputStreamToByteArray(InputStream is);

    /**
     * incremente la chaine transmise en parametre en suivant l'ordre alphabetique
     *
     * @param s
     * @return
     */
    public String incrementString(String chaine);

    /**
     * permet d'effectuer un dir sur un répertoire
     *
     * @param prefix : prefix appliqué au filtre de recherche
     * @param suffix : suffix appliqué au filtre de recherche
     * @param regex  : regex appliqué au filtre de recherche
     * @return
     */
    public String[] dir(String folderPath, String prefix, String suffix, String regex);

    /**
     * convertie le fichier File en InputSource
     *
     * @param f
     * @return
     * @throws IOException
     */
    public InputSource fileToInputSource(File f) throws IOException;

    /**
     * Converts Source into InputSource
     *
     * @param source
     * @return InputSource
     */
    public InputSource convertSourceToInputSource(Source source);

    /**
     * return la date de creation du fichier
     *
     * @param file
     * @return
     */
    public Date getFileCreationDate(File file);

    /**
     * @param dir
     * @return le fichier le plus récent en termes de modification
     */
    public File getLatestFileModified(String dir);

    /**
     * convertit un Document en inputStream
     *
     * @param file
     * @return InputStream
     */
    public InputStream convertDocToInputStream(Document doc);

    public void copyWholeDirectory(File source, File destination);

    public int stringToInt(String s);

    public String floatMinutesToHours(float f);

    public String replaceHtmlEntities (String s);

    public String replaceISOLatin1SpecialCharacters (String s);

    public String eliminatePunctuationSigns (String s);

}
