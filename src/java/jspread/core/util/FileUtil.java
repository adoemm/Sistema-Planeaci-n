package jspread.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author JeanPaul
 */
public class FileUtil {

    private static final String version = "V0.2";

    public static String getVersion() {
        return version;
    }

    public static String getExtension(String file) {
        String ext = "";
        if (file.lastIndexOf('.') != -1) {
            ext = file.substring(file.lastIndexOf('.'), file.length());
            ext = ext.toLowerCase();
        }
        return ext;
    }

    public static String saveZipFile(FileItem item, String localPath, String fileName) {
        String msg = "";
        String itemName = item.getName();
        try {
            String ext = getExtension(itemName);
            if (ext.contains(".zip") == false) {
                msg = ("error: El archivo debe de tener extension .zip");
            } else {
                //System.out.println("itemName: " + itemName);
                //System.out.println("itemName win: " + itemName.lastIndexOf("\\"));
                //System.out.println("itemName uni " + itemName.lastIndexOf("/"));
                if (fileName.equalsIgnoreCase("")) {
                    if (itemName.lastIndexOf("\\") == -1 && itemName.lastIndexOf("/") == -1) {
                        fileName = itemName;
                    } else if (itemName.lastIndexOf("\\") != -1) {
                        fileName = (itemName.substring(itemName.lastIndexOf("\\") + 1));
                    } else if (itemName.lastIndexOf("/") != -1) {
                        fileName = (itemName.substring(itemName.lastIndexOf("/") + 1));
                    }
                    fileName = fileName.substring(0, fileName.lastIndexOf('.'));
                }
                item.write(new File(localPath + fileName + ext));
                msg = "" + localPath + fileName + ext;
            }
        } catch (Exception ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
            msg = "ERROR";
        }
        return msg;
    }

    public static boolean unZip(String filename, String destFolder, String destFileName) {
        boolean exitoso = false;
        try {
//Nuestro OutputStream
            BufferedOutputStream dest = null;
//InputStream a partir del archivo ZIP a leer
            FileInputStream fis = new FileInputStream(filename);
//Obtenemos el checksum
            CheckedInputStream checksum = new CheckedInputStream(fis, new Adler32());
//Indicamos que será un archivo ZIP
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(checksum));
            ZipEntry entry;
            //Configuramos el tamaño del buffer
            int BUFFER = 1 * 1024 * 1024;
//Iteramos por el contenido del ZIP
            while ((entry = zis.getNextEntry()) != null) {
                if (!entry.getName().equalsIgnoreCase("Thumbs.db")) {
                    System.out.println("Extrayendo del ZIP: " + entry);
                    int count;
                    byte data[] = new byte[BUFFER];
// Escribimos los archivos en la ubicación deseada
                    FileOutputStream fos;
                    if (destFileName.equalsIgnoreCase("")) {
                        fos = new FileOutputStream(destFolder + entry.getName());
                    } else {
                        fos = new FileOutputStream(destFolder + destFileName + getExtension(entry.getName()));
                    }
                    dest = new BufferedOutputStream(fos, BUFFER);
                    while ((count = zis.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, count);
                    }
                    dest.flush();
                    dest.close();
                }
            }
            zis.close();
            System.out.println("Checksum:" + checksum.getChecksum().getValue());
            exitoso = true;
        } catch (Exception ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exitoso;
    }

    public static boolean copyFile(File sourceFilename, File destFile) {
        boolean copiado = false;
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            //File afile = new File("Afile.txt");
            //File bfile = new File("Bfile.txt");

            inStream = new FileInputStream(sourceFilename);
            outStream = new FileOutputStream(destFile);

            byte[] buffer = new byte[1024 * 1024];
            int length;
            //copy the file content in bytes 
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }
            inStream.close();
            outStream.close();
            System.out.println("File is copied successful!");
            copiado = true;
        } catch (IOException ex) {
            Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return copiado;
    }
}
