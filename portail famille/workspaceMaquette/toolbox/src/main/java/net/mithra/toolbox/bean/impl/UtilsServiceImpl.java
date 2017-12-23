package net.mithra.toolbox.bean.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom.JDOMException;
import org.jdom.input.DOMBuilder;
import org.jdom.output.DOMOutputter;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import net.mithra.toolbox.LogMes;
import net.mithra.toolbox.bean.UtilsService;

/**
 * @author f.rebeche
 */
@Service("UtilsService")
public class UtilsServiceImpl implements UtilsService {

	 @Value("#{propertiesFile['UtilsService.path_ressource']}")
    String path_ressource;


    /**
     * escape caracter &'"<> for xml
     *
     * @param chaine
     * @return
     */
    public String escapeXML(String chaine) {
        if (chaine == null || chaine.isEmpty()) {
            return chaine;
        }
        String result = chaine;
        try {
            result = chaine.replace("&", "&amp;").replace("'", "&apos;").replace(">", "&gt;").replace("<", "&lt;").replace("\"", "&quot;");
        } catch (Exception e) {
            LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, "impossible d'échapé les charactère xml :" + e.getMessage());
        }
        return result;
    }


    /**
     * FloatToInt : convertie un Float en int
     *
     * @param sF
     * @return
     */
    @Override
    public int floatToInt(Float sF) {
        try {
            return sF.intValue();
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * DoubleToInt : convertie un Double en int
     *
     * @param sF
     * @return
     */
    @Override
    public int doubleToInt(Double sF) {
        try {
            return sF.intValue();
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * convertie un String en int
     *
     * @param s
     * @return
     */
    @Override
    public int strToInt(String s) {
        int f;
        try {
            f = Integer.valueOf(s.trim());
        } catch (NumberFormatException e) {
            return 0;
        } catch (java.lang.NullPointerException e) {
            return 0;
        }
        return f;
    }

    public String convertStringToHex(String str) {

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            hex.append(Integer.toHexString((int) chars[i]));
        }

        return hex.toString();
    }

    public int convertStringHexToHex(String str) {

        int f;
        try {
            f = Integer.parseInt(str, 16);
        } catch (NumberFormatException e) {
            return 0;
        } catch (java.lang.NullPointerException e) {
            return 0;
        }
        return f;
    }

    public String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < hex.length() - 1; i += 2) {

            String output = hex.substring(i, (i + 2));
            int decimal = Integer.parseInt(output, 16);
            sb.append((char) decimal);
            temp.append(decimal);
        }
        LogMes.log(UtilsServiceImpl.class, LogMes.DEBUG, "convertHexToString: Decimal :" + temp.toString());
        return sb.toString();
    }

    public String convertHexUTF16ToString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();

        for (int i = 0; i < hex.length() - 1; i += 4) {

            String output = hex.substring(i, (i + 4));
            int decimal = Integer.parseInt(output, 16);
            sb.append((char) decimal);
            temp.append(decimal);
        }
        LogMes.log(UtilsServiceImpl.class, LogMes.DEBUG, "convertHexUTF16ToString: Decimal :" + temp.toString());
        return sb.toString();
    }

    /**
     * convertie un String en float
     *
     * @param s
     * @return
     */
    @Override
    public float strToFloat(String s) {
        float f;
        try {
            f = Float.valueOf(s.trim()).floatValue();
        } catch (NumberFormatException e) {
            return 0;
        } catch (java.lang.NullPointerException e) {
            return 0;
        }
        return f;
    }

    @Override
    public String replaceSpaceByUnderscore(String sS) {
        return sS.replaceAll(" ", "_");
    }

    //conversion des Stream

    /**
     * convertie Writer to inputStream
     *
     * @param w
     * @return
     */
    @Override
    public InputStream writerToInputStream(Writer w) {
        return new ByteArrayInputStream(w.toString().getBytes());
    }

    /**
     * convertie StreamSource to StringBuffer
     *
     * @param sc
     * @return
     */
    @Override
    public StringBuffer streamSourceToStringBuffer(StreamSource sc) {
        if (sc != null) {
            InputStream is = sc.getInputStream();
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            Reader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } catch (IOException ex) {
                LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, ex);
                return new StringBuffer("");
            } finally {
                try {
                    is.close();
                } catch (IOException ex) {
                    LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, ex);
                    return new StringBuffer("");
                }
                try {
                    reader.close();
                } catch (IOException ex) {
                    LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, ex);
                }
            }
            return new StringBuffer(writer.toString());
        } else {
            return new StringBuffer("");
        }
    }

    @Override
    public StringBuffer stringWriterToStringBuffer(StringWriter sw) {
        if (sw == null) {
            return new StringBuffer("");
        }


        return new StringBuffer(sw.getBuffer());
    }

    @Override
    public org.w3c.dom.Document convertToDOM(org.jdom.Document jdomDoc) {
        try {
            DOMOutputter outputter = new DOMOutputter();
            return outputter.output(jdomDoc);
        } catch (JDOMException ex) {
            LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, ex);
        }
        return null;
    }

    /*
     * TODO rendre générique
     */
    @Override
    public InputStream getXSLFile(String xslFile) {
        LogMes.log(UtilsServiceImpl.class, LogMes.DEBUG, "getXSLFile : file path=" + path_ressource + xslFile);
        return getClass().getClassLoader().getResourceAsStream(path_ressource + xslFile);
    }

    @Override
    public InputStream getFile(String pathFile) {
        LogMes.log(UtilsServiceImpl.class, LogMes.DEBUG, "getXSLFile : file path=" + pathFile);
        return getClass().getClassLoader().getResourceAsStream(pathFile);
    }

    public InputStream getXSLFOFile(String xslFile) {
        LogMes.log(UtilsServiceImpl.class, LogMes.DEBUG, "getXSLFOFile : path_ressource=" + path_ressource);
        return getClass().getClassLoader().getResourceAsStream(path_ressource + xslFile);
    }
    
	@Override
	public Document loadDOMfromXML(String xml) throws Exception {
		  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		    factory.setNamespaceAware(true);
		    DocumentBuilder builder = factory.newDocumentBuilder();

		    return builder.parse(new ByteArrayInputStream(xml.getBytes()));
	}

    /**
     * convertie un org.w3c.dom.Document en org.jdom.Document
     *
     * @param documentDOM
     * @return
     * @throws Exception
     */
    @Override
    public org.jdom.Document convertDOMtoJDOM(org.w3c.dom.Document documentDOM) throws Exception {
        DOMBuilder builder = new DOMBuilder();
        org.jdom.Document documentJDOM = builder.build(documentDOM);
        return documentJDOM;
    }

    /**
     * affiche un document XML sur la sortie System
     *
     * @param document
     */
    @Override
    public void afficheXML(Document document) {
        try {
            afficheXML(convertDOMtoJDOM(document));
        } catch (Exception ex) {
            LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, ex);
        }
    }

    /**
     * affiche un document XML sur la sortie System
     *
     * @param document
     */
    @Override
    public void afficheXML(org.jdom.Document document) {
        try {
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, System.out);
        } catch (Exception ex) {
            LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, ex);
        }
    }

    /**
     * Convertie un ByteArrayOutputStream en InputStream
     *
     * @param out :ByteArrayOutputStream convertie en inputStream
     * @return
     */
    @Override
    public InputStream outputStreamToInputStream(ByteArrayOutputStream out) {
        InputStream is = new ByteArrayInputStream(out.toByteArray());
        return is;
    }

    /**
     * Supprime le dossier transmit en parametre
     *
     * @param d dossier supprime
     */
    @Override
    public void fileDeleteFolder(File d) {
        if (d == null) {
            LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, "FileDeleteFolder : d est null");
            return;
        }
        if (!d.exists()) {
            LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, "FileDeleteFolder : " + d.getAbsolutePath() + " n'existe pas");
            return;
        }
        if (!d.isDirectory()) {
            LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, "FileDeleteFolder : " + d.getAbsolutePath() + " n'est pas un dossier");
            return;
        }
        File[] sFileL = d.listFiles();
        for (File f : sFileL) {
            if (f.isDirectory()) {
                fileDeleteFolder(f);
            } else if (f.isFile()) {
                fileDeleteFile(f);
            } else {
                LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, "FileDeleteFolder : " + f.getAbsolutePath() + " n'est pas un dossier ni un fichier!!!!");
            }
        }
        d.delete();
    }

    /**
     * ecrit dans f le contenue de is
     *
     * @param is
     * @param f
     */
    @Override
    public void fileWrite(InputStream is, File f) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(f);
            int stream = is.read();
            while (stream != -1) {
                outputStream.write(stream);
                stream = is.read();
            }
        } catch (IOException ex) {
            LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, "FileWrite : " + f.getAbsolutePath() + " ioException" + ex);
        } finally {
            try {
                outputStream.close();
            } catch (IOException ex) {
                LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, "FileWrite : " + f.getAbsolutePath() + " ioException" + ex);
            }
        }
    }

    /**
     * copy source dans destination
     *
     * @param source
     * @param destination
     */
    @Override
    public boolean fileCopier(File source, File destination) {
        boolean resultat = false;

        java.io.FileInputStream sourceFile = null;
        java.io.FileOutputStream destinationFile = null;
        try {
            destination.createNewFile();
            sourceFile = new java.io.FileInputStream(source);
            destinationFile = new java.io.FileOutputStream(destination);
            byte buffer[] = new byte[512 * 1024];
            int nbLecture;
            while ((nbLecture = sourceFile.read(buffer)) != -1) {
                destinationFile.write(buffer, 0, nbLecture);
            }

            resultat = true;
        } catch (java.io.FileNotFoundException f) {
        } catch (java.io.IOException e) {
        } finally {
            try {
                sourceFile.close();
            } catch (Exception e) {
            }
            try {
                destinationFile.close();
            } catch (Exception e) {
            }
        }
        return (resultat);
    }

    /**
     * Supprime le fichier transmit en parametre
     *
     * @param fichier supprime
     */
    @Override
    public void fileDeleteFile(File fichier) {
        if (fichier == null) {
            LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, "FileDeleteFile : fichier est null");
            return;
        }
        if (!fichier.exists()) {
            LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, "FileDeleteFile : " + fichier.getAbsolutePath() + " n'existe pas");
            return;
        }
        if (fichier.isDirectory()) {
            LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, "FileDeleteFile : " + fichier.getAbsolutePath() + " n'est pas un fichier");
            return;
        }
        fichier.delete();
    }

    /**
     * incremente la chaine transmise en parametre en suivant l'ordre
     * alphabetique
     *
     * @param s
     * @return
     */
    public String incrementString(String s) {
        char[] str = s.toCharArray();
        for (int pos = str.length - 1; pos >= 0; pos--) {
            if (Character.toUpperCase(str[pos]) != 'Z') {
                str[pos]++;
                break;
            } else {
                str[pos] = 'A';
                if (pos == 0) {
                    str = new char[str.length + 1];
                    for (int i = 0; i < str.length; i++) {
                        str[i] = 'A';
                    }
                }
            }
        }
        return new String(str);
    }

    /**
     * Ecrit le contenue de is dans un fichier dont le nom est transmit dans
     * fileName
     *
     * @param is       : contenue a écrire
     * @param fileName : nom du fichier
     */
    @Override
    public void ecritFile(InputStream is, String fileName) {
        CopyInputStream cis = new CopyInputStream(is);
        try {

            File f = new File(fileName);
            OutputStream out = new FileOutputStream(f);
            byte buf[] = new byte[1024];
            int len;
            is = cis.getCopy();
            while ((len = is.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
        } catch (IOException e) {
            LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, e);
        }
        is = cis.getCopy();
    }

    @Override
    public byte[] inputStreamToByteArray(InputStream is) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int l = 0;
        do {
            System.out.println("l:" + l);
            try {
                l = (is.read(buffer));
                if (l > 0) {
                    out.write(buffer, 0, l);
                }
            } catch (IOException ex) {
                LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, ex);
            }
        } while (l > 0);
        return out.toByteArray();
    }

    @Override
    public String[] dir(String folderPath, String prefix, String suffix, String regex) {
        File dirXML = new File(folderPath);
        String[] fileList = dirXML.list(new FileFilter(prefix, suffix, regex));
        return fileList;
    }

    /**
     * il existe maintenant une interface pls simpel en java7 mais il faut faire valider le java7 par le client
     * return null si le File n'est pas un fichier
     *
     * @param file
     * @return
     */
    @Override
    public Date getFileCreationDate(File file) {

        if (file == null || !file.exists()) {
            return null;
        }

        Process proc;
        Date dateResult = null;
        try {
            proc = Runtime.getRuntime().exec("cmd /c dir " + file.getAbsolutePath() + " /tc");
            BufferedReader br =
                    new BufferedReader(
                            new InputStreamReader(proc.getInputStream()));

            String data = "";

            for (int i = 0; i < 6; i++) {
                data = br.readLine();
            }
            if (data == null) {
                return null;
            }

            StringTokenizer st = new StringTokenizer(data);
            String date = st.nextToken();//Get date
            String time = st.nextToken();//Get time
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            try {
                dateResult = simpleDateFormat.parse(date + " " + time);
            } catch (ParseException e) {
                LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, e);
            }
        } catch (IOException ex) {
            LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, ex);
        }
        return dateResult;

    }

    private class FileFilter implements FilenameFilter {

        private String suffix = null;
        private String prefix = null;
        private String regex = null;

        private FileFilter() {
        }

        public FileFilter(String prefix, String suffix, String regex) {
            if (prefix != null) {
                this.prefix = prefix.toUpperCase();
            }
            if (suffix != null) {
                this.suffix = suffix.toUpperCase();
            }
            this.regex = regex;
        }

        public boolean accept(File dir, String name) {
            boolean accept = true;
            if (prefix != null && !prefix.isEmpty()) {
                if (!name.toUpperCase().startsWith(prefix)) {
                    accept = false;
                }
            }
            if (accept == true && suffix != null && !suffix.isEmpty()) {
                if (!name.toUpperCase().endsWith(suffix)) {
                    accept = false;
                }
            }
            if (accept == true && regex != null && !regex.isEmpty()) {
                if (!name.matches(regex)) {
                    accept = false;
                }
            }
            return accept;
        }
    }

    private class CopyInputStream {

        private InputStream _is;
        private ByteArrayOutputStream _copy = new ByteArrayOutputStream();

        public CopyInputStream(InputStream is) {
            _is = is;

            try {
                copy();
            } catch (IOException ex) {
            }
        }

        private int copy() throws IOException {
            int read = 0;
            int chunk = 0;
            byte[] data = new byte[256];

            while (-1 != (chunk = _is.read(data))) {
                read += data.length;
                _copy.write(data, 0, chunk);
            }

            return read;
        }

        public InputStream getCopy() {
            return (InputStream) new ByteArrayInputStream(_copy.toByteArray());
        }
    }

    public String convertDOMToString(Document documentDOM) {
        try {
            DOMSource domSource = new DOMSource(documentDOM);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();

            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.transform(domSource, result);
            writer.flush();
            return writer.toString();
        } catch (TransformerException ex) {
            LogMes.log(UtilsServiceImpl.class, LogMes.ERROR, ex);
            return "";
        }
    }

    /**
     * convertie le fichier File en InputSource
     *
     * @param f
     * @return
     * @throws IOException
     */
    @Override
    public InputSource fileToInputSource(File f) throws IOException {
        InputStream in = null;
        in = new FileInputStream(f);
        InputSource inputSource = new InputSource(new InputStreamReader(in));
        return inputSource;
    }

    /**
     * Converts Source into InputSource
     *
     * @param source
     * @return InputSource
     */
    @Override
    public InputSource convertSourceToInputSource(Source source) {
        try {
            if (source instanceof SAXSource) {
                return ((SAXSource) source).getInputSource();
            } else if (source instanceof DOMSource) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Node node = ((DOMSource) source).getNode();
                if (node instanceof Document) {
                    node = ((Document) node).getDocumentElement();
                }
                Element domElement = (Element) node;

                // transform DOM Element into stream
                DOMSource domSource = new DOMSource(domElement);
                StreamResult result = new StreamResult(baos);
                TransformerFactory transFactory = TransformerFactory.newInstance();
                Transformer transformer = transFactory.newTransformer();
                transformer.transform(domSource, result);

                // create the new InputSource
                InputSource inputsource = new InputSource(source.getSystemId());
                // set content stream
                inputsource.setByteStream(new ByteArrayInputStream(baos.toByteArray()));
                return inputsource;
            } else if (source instanceof StreamSource) {
                StreamSource ss = (StreamSource) source;
                InputSource isource = new InputSource(ss.getSystemId());
                isource.setByteStream(ss.getInputStream());
                isource.setCharacterStream(ss.getReader());
                isource.setPublicId(ss.getPublicId());
                return isource;
            } else {
                //TODO : to check if necessary
                // get InputSource from bytes URI. For authenticated URLs, get with username and password
                /*return new InputSource(source.getSystemId());*/
            }
        } catch (Exception expt) {
        }

        return null;
    }


    public File getLatestFileModified(String dir) {
        File fl = new File(dir);
        File[] files = fl.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isFile();
            }
        });
        long lastMod = Long.MIN_VALUE;
        File choise = null;
        for (File file : files) {
            if (file.lastModified() > lastMod) {
                choise = file;
                lastMod = file.lastModified();
            }
        }
        return choise;
    }


    public InputStream convertDocToInputStream(Document doc) {

        DOMSource ds = new DOMSource(doc);
        InputStream is = convertSourceToInputSource(ds).getByteStream();
        return is;
    }
    
    

    @Override
    public void copyWholeDirectory(File source, File destination) {

        if (source.isDirectory()) {

            //if directory not exists, create it
            if (!destination.exists()) {
                destination.mkdir();
                System.out.println("Directory copied from "
                        + source + "  to " + destination);
            }

            //list all the directory contents
            String files[] = source.list();

            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(source, file);
                File destFile = new File(destination, file);
                //recursive copy
                copyWholeDirectory(srcFile, destFile);
            }

        } else {
            fileCopier(source, destination);
        }
    }

    @Override
    public int stringToInt(String s) {
        int f;
        try {
            f = Integer.valueOf(s.trim());
        } catch (NumberFormatException e) {
            return 0;
        } catch (java.lang.NullPointerException e) {
            return 0;
        }
        return f;
    }

    @Override
    public String floatMinutesToHours(float f) {

        String HOURS_MINUTES_SEPARATOR = "H";

        String retour = "00" + HOURS_MINUTES_SEPARATOR + "00";

        try {
            int m = (int) (f * 60);

            int hours = m / 60;
            int minutes = m % 60;

            retour = String.format("%02d", hours) + HOURS_MINUTES_SEPARATOR + String.format("%02d", minutes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return retour;

    }

    @Override
    public String replaceHtmlEntities(String strSource) {
        StringBuffer strBuf = new StringBuffer();

        if (strSource == null)
            return "";
        final char[] buffer = strSource.toCharArray();
        final int length = strSource.length();

        for(int i=0; i<length; i++) {
            final char c = buffer[i];
            switch (c) {
                case 0x0020 : strBuf.append(' ');break;
                case 0x0021 : strBuf.append('!');break;
                case 0x0022 : strBuf.append('"');break;
                case 0x0023 : strBuf.append('#');break;
                case 0x0024 : strBuf.append('$');break;
                case 0x0025 : strBuf.append('%');break;
                case 0x0026 : strBuf.append('&');break;
                case 0x0027 : strBuf.append('\'');break;
                case 0x0028 : strBuf.append('(');break;
                case 0x0029 : strBuf.append(')');break;
                case 0x002A : strBuf.append('*');break;
                case 0x002B : strBuf.append('+');break;
                case 0x002C : strBuf.append(',');break;
                case 0x002D : strBuf.append('-');break;
                case 0x002E : strBuf.append('.');break;
                case 0x002F : strBuf.append('/');break;
                case 0x0030 : strBuf.append('0');break;
                case 0x0031 : strBuf.append('1');break;
                case 0x0032 : strBuf.append('2');break;
                case 0x0033 : strBuf.append('3');break;
                case 0x0034 : strBuf.append('4');break;
                case 0x0035 : strBuf.append('5');break;
                case 0x0036 : strBuf.append('6');break;
                case 0x0037 : strBuf.append('7');break;
                case 0x0038 : strBuf.append('8');break;
                case 0x0039 : strBuf.append('9');break;
                case 0x003A : strBuf.append(':');break;
                case 0x003B : strBuf.append(';');break;
                case 0x003C : strBuf.append('<');break;
                case 0x003D : strBuf.append('=');break;
                case 0x003E : strBuf.append('>');break;
                case 0x003F : strBuf.append('?');break;
                case 0x0040 : strBuf.append('@');break;
                case 0x0041 : strBuf.append('A');break;
                case 0x0042 : strBuf.append('B');break;
                case 0x0043 : strBuf.append('C');break;
                case 0x0044 : strBuf.append('D');break;
                case 0x0045 : strBuf.append('E');break;
                case 0x0046 : strBuf.append('F');break;
                case 0x0047 : strBuf.append('G');break;
                case 0x0048 : strBuf.append('H');break;
                case 0x0049 : strBuf.append('I');break;
                case 0x004A : strBuf.append('J');break;
                case 0x004B : strBuf.append('K');break;
                case 0x004C : strBuf.append('L');break;
                case 0x004D : strBuf.append('M');break;
                case 0x004E : strBuf.append('N');break;
                case 0x004F : strBuf.append('O');break;
                case 0x0050 : strBuf.append('P');break;
                case 0x0051 : strBuf.append('Q');break;
                case 0x0052 : strBuf.append('R');break;
                case 0x0053 : strBuf.append('S');break;
                case 0x0054 : strBuf.append('T');break;
                case 0x0055 : strBuf.append('U');break;
                case 0x0056 : strBuf.append('V');break;
                case 0x0057 : strBuf.append('W');break;
                case 0x0058 : strBuf.append('X');break;
                case 0x0059 : strBuf.append('Y');break;
                case 0x005A : strBuf.append('Z');break;
                case 0x005B : strBuf.append('[');break;
                case 0x005C : strBuf.append('\\');break;
                case 0x005D : strBuf.append(']');break;
                case 0x005E : strBuf.append('^');break;
                case 0x005F : strBuf.append('_');break;
                case 0x0060 : strBuf.append('`');break;
                case 0x0061 : strBuf.append('a');break;
                case 0x0062 : strBuf.append('b');break;
                case 0x0063 : strBuf.append('c');break;
                case 0x0064 : strBuf.append('d');break;
                case 0x0065 : strBuf.append('e');break;
                case 0x0066 : strBuf.append('f');break;
                case 0x0067 : strBuf.append('g');break;
                case 0x0068 : strBuf.append('h');break;
                case 0x0069 : strBuf.append('i');break;
                case 0x006A : strBuf.append('j');break;
                case 0x006B : strBuf.append('k');break;
                case 0x006C : strBuf.append('l');break;
                case 0x006D : strBuf.append('m');break;
                case 0x006E : strBuf.append('n');break;
                case 0x006F : strBuf.append('o');break;
                case 0x0070 : strBuf.append('p');break;
                case 0x0071 : strBuf.append('q');break;
                case 0x0072 : strBuf.append('r');break;
                case 0x0073 : strBuf.append('s');break;
                case 0x0074 : strBuf.append('t');break;
                case 0x0075 : strBuf.append('u');break;
                case 0x0076 : strBuf.append('v');break;
                case 0x0077 : strBuf.append('w');break;
                case 0x0078 : strBuf.append('x');break;
                case 0x0079 : strBuf.append('y');break;
                case 0x007A : strBuf.append('z');break;
                case 0x007B : strBuf.append('{');break;
                case 0x007C : strBuf.append('|');break;
                case 0x007D : strBuf.append('}');break;
                case 0x007E : strBuf.append('~');break;
                case 0x00A3 : strBuf.append('\u00a3');break;
                case 0x00A4 : strBuf.append('\u00a4');break;
                case 0x00A7 : strBuf.append('\u00a7');break;
                case 0x00A8 : strBuf.append('\u00a8');break;
                case 0x00A9 : strBuf.append('\u00a9');break;
                case 0x00AB : strBuf.append('\u00ab');break;
                case 0x00AE : strBuf.append('\u00ae');break;
                case 0x00B0 : strBuf.append('\u00b0');break;
                case 0x00B1 : strBuf.append('\u00b1');break;
                case 0x00B2 : strBuf.append('\u00b2');break;
                case 0x00B3 : strBuf.append('\u00b3');break;
                case 0x00B4 : strBuf.append('\u00b4');break;
                case 0x00B5 : strBuf.append('\u00b5');break;
                case 0x00BB : strBuf.append('\u00bb');break;
                case 0x00BC : strBuf.append('\u00bc');break;
                case 0x00BD : strBuf.append('\u00bd');break;
                case 0x00BE : strBuf.append('\u00be');break;
                case 0x00C0 : strBuf.append('\u00c0');break;
                case 0x00C1 : strBuf.append('\u00c1');break;
                case 0x00C2 : strBuf.append('\u00c2');break;
                case 0x00C3 : strBuf.append('\u00c3');break;
                case 0x00C4 : strBuf.append('\u00c4');break;
                case 0x00C5 : strBuf.append('\u00c5');break;
                case 0x00C6 : strBuf.append('A').append('E');break;
                case 0x00C7 : strBuf.append('\u00c7');break;
                case 0x00C8 : strBuf.append('\u00c8');break;
                case 0x00C9 : strBuf.append('\u00c9');break;
                case 0x00CA : strBuf.append('\u00ca');break;
                case 0x00CB : strBuf.append('\u00cb');break;
                case 0x00CC : strBuf.append('\u00cc');break;
                case 0x00CD : strBuf.append('\u00cd');break;
                case 0x00CE : strBuf.append('\u00ce');break;
                case 0x00CF : strBuf.append('\u00cf');break;
                case 0x00D2 : strBuf.append('\u00d2');break;
                case 0x00D3 : strBuf.append('\u00d3');break;
                case 0x00D4 : strBuf.append('\u00d4');break;
                case 0x00D5 : strBuf.append('\u00d5');break;
                case 0x00D6 : strBuf.append('\u00d6');break;
                case 0x00D8 : strBuf.append('\u00d8');break;
                case 0x00D9 : strBuf.append('\u00d9');break;
                case 0x00DA : strBuf.append('\u00da');break;
                case 0x00DB : strBuf.append('\u00db');break;
                case 0x00DC : strBuf.append('\u00dc');break;
                case 0x00DD : strBuf.append('\u00dd');break;
                case 0x00E0 : strBuf.append('\u00e0');break;
                case 0x00E1 : strBuf.append('\u00e1');break;
                case 0x00E2 : strBuf.append('\u00e2');break;
                case 0x00E3 : strBuf.append('\u00e3');break;
                case 0x00E4 : strBuf.append('\u00e4');break;
                case 0x00E5 : strBuf.append('\u00e5');break;
                case 0x00E6 : strBuf.append('a').append('e');break;
                case 0x00E7 : strBuf.append('\u00e7');break;
                case 0x00E8 : strBuf.append('\u00e8');break;
                case 0x00E9 : strBuf.append('\u00e9');break;
                case 0x00EA : strBuf.append('\u00ea');break;
                case 0x00EB : strBuf.append('\u00eb');break;
                case 0x00EC : strBuf.append('\u00ec');break;
                case 0x00ED : strBuf.append('\u00ed');break;
                case 0x00EE : strBuf.append('\u00ee');break;
                case 0x00EF : strBuf.append('\u00ef');break;
                case 0x00F1 : strBuf.append('\u00f1');break;
                case 0x00F2 : strBuf.append('\u00f2');break;
                case 0x00F3 : strBuf.append('\u00f3');break;
                case 0x00F4 : strBuf.append('\u00f4');break;
                case 0x00F5 : strBuf.append('\u00f5');break;
                case 0x00F6 : strBuf.append('\u00f6');break;
                case 0x00F8 : strBuf.append('\u00f8');break;
                case 0x00F9 : strBuf.append('\u00f9');break;
                case 0x00FA : strBuf.append('\u00fa');break;
                case 0x00FB : strBuf.append('\u00fb');break;
                case 0x00FC : strBuf.append('\u00fc');break;
                case 0x00FD : strBuf.append('\u00fd');break;
                case 0x00FF : strBuf.append('\u00ff');break;
                case 0x0152 : strBuf.append('O').append('E');break;
                case 0x0153 : strBuf.append('o').append('e');break;
                case 0x0178 : strBuf.append('\u0178');break;
                case 0x2002 : strBuf.append(' ');break;
                case 0x2003 : strBuf.append(' ');break;
                case 0x2004 : strBuf.append(' ');break;
                case 0x2005 : strBuf.append(' ');break;
                case 0x2007 : strBuf.append(' ');break;
                case 0x2008 : strBuf.append(' ');break;
                case 0x2009 : strBuf.append(' ');break;
                case 0x200A : strBuf.append(' ');break;
                case 0x2010 : strBuf.append('-');break;
                case 0x2018 : strBuf.append('\'');break;
                case 0x2019 : strBuf.append('\'');break;
                case 0x201A : strBuf.append('\'');break;
                case 0x201C : strBuf.append('"');break;
                case 0x201D : strBuf.append('"');break;
                case 0x201E : strBuf.append('"');break;
                case 0x2264 : strBuf.append('<').append('=');break;
                case 0x2265 : strBuf.append('>').append('=');break;
                case 0x2423 : strBuf.append(' ');break;

                default :
                    if (Character.getNumericValue(c) == -1 || Character.isWhitespace(c))
                        strBuf.append(' ');
                    else
                        strBuf.append(c);
            }
        }
        return strBuf.toString();
    }
    @Override
    public String replaceISOLatin1SpecialCharacters(String strSource) {
        StringBuffer strBuf = new StringBuffer();
        final char[] buffer = strSource.toCharArray();
        final int length = strSource.length();

        for(int i=0; i<length; i++) {
            final char c = buffer[i];
            if (c >= '\u00c0' && c <= '\u2265') {
                switch (c) {
                    case '\u00C0' : // À
                    case '\u00C1' : // Á
                    case '\u00C2' : // Â
                    case '\u00C3' : // Ã
                    case '\u00C4' : // Ä
                    case '\u00C5' : // Å
                        strBuf.append('A');
                        break;
                    case '\u00C6' : // Æ
                        strBuf.append('A');
                        strBuf.append('E');
                        break;
                    case '\u00C7' : // Ç
                        strBuf.append('C');
                        break;
                    case '\u00C8' : // È
                    case '\u00C9' : // É
                    case '\u00CA' : // Ê
                    case '\u00CB' : // Ë
                        strBuf.append('E');
                        break;
                    case '\u00CC' : // Ì
                    case '\u00CD' : // Í
                    case '\u00CE' : // Î
                    case '\u00CF' : // Ï
                        strBuf.append('I');
                        break;
                    case '\u00D0' : // Ð
                        strBuf.append('D');
                        break;
                    case '\u00D1' : // Ñ
                        strBuf.append('N');
                        break;
                    case '\u00D2' : // Ò
                    case '\u00D3' : // Ó
                    case '\u00D4' : // Ô
                    case '\u00D5' : // Õ
                    case '\u00D6' : // Ö
                    case '\u00D8' : // Ø
                        strBuf.append('O');
                        break;
                    case '\u0152' : // Œ
                        strBuf.append('O');
                        strBuf.append('E');
                        break;
                    case '\u00DE' : // Þ
                        strBuf.append('T');
                        strBuf.append('H');
                        break;
                    case '\u00D9' : // Ù
                    case '\u00DA' : // Ú
                    case '\u00DB' : // Û
                    case '\u00DC' : // Ü
                        strBuf.append('U');
                        break;
                    case '\u00DD' : // Ý
                    case '\u0178' : // Ÿ
                        strBuf.append('Y');
                        break;
                    case '\u00E0' : // à
                    case '\u00E1' : // á
                    case '\u00E2' : // â
                    case '\u00E3' : // ã
                    case '\u00E4' : // ä
                    case '\u00E5' : // å
                        strBuf.append('a');
                        break;
                    case '\u00E6' : // æ
                        strBuf.append('a');
                        strBuf.append('e');
                        break;
                    case '\u00E7' : // ç
                        strBuf.append('c');
                        break;
                    case '\u00E8' : // è
                    case '\u00E9' : // é
                    case '\u00EA' : // ê
                    case '\u00EB' : // ë
                        strBuf.append('e');
                        break;
                    case '\u00EC' : // ì
                    case '\u00ED' : // í
                    case '\u00EE' : // î
                    case '\u00EF' : // ï
                        strBuf.append('i');
                        break;
                    case '\u00F0' : // ð
                        strBuf.append('d');
                        break;
                    case '\u00F1' : // ñ
                        strBuf.append('n');
                        break;
                    case '\u00F2' : // ò
                    case '\u00F3' : // ó
                    case '\u00F4' : // ô
                    case '\u00F5' : // õ
                    case '\u00F6' : // ö
                    case '\u00F8' : // ø
                        strBuf.append('o');
                        break;
                    case '\u0153' : // œ
                        strBuf.append('o');
                        strBuf.append('e');
                        break;
                    case '\u00DF' : // ß
                        strBuf.append('s');
                        strBuf.append('s');
                        break;
                    case '\u00FE' : // þ
                        strBuf.append('t');
                        strBuf.append('h');
                        break;
                    case '\u00F9' : // ù
                    case '\u00FA' : // ú
                    case '\u00FB' : // û
                    case '\u00FC' : // ü
                        strBuf.append('u');
                        break;
                    case '\u00FD' : // ý
                    case '\u00FF' : // ÿ
                        strBuf.append('y');
                        break;
                    case '\u2264' : // inf ou egal
                        strBuf.append('<');
                        strBuf.append('=');
                        break;
                    case '\u2265' : // sup ou egal
                        strBuf.append('>');
                        strBuf.append('=');
                        break;
                    default :
                        strBuf.append(c);
                        break;
                }
            } else {
                if (c == '\'')
                    strBuf.append(' ');
                else
                    strBuf.append(c);
            }
        }
        return strBuf.toString();
    }

    @Override
    public String eliminatePunctuationSigns(String s) {
        return s.replace("!", "").replace(".", "").replace("?", "").replace(",", "")
                .replace("\"","").replace("(","").replace(")","").replace("[","").replace("]","");
    }


	public String getPath_ressource() {
		return path_ressource;
	}


	public void setPath_ressource(String path_ressource) {
		this.path_ressource = path_ressource;
	}



    
    
    
    
}
