/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jspread.core.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jspread.core.db.QUID;
import jspread.core.models.Transporter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.servlet.http.HttpSession;

/**
 *
 * @author JeanPaul
 */
public final class SystemUtil {

    //Esta es la version de esta clase
    private static final String version = "V0.09";

    public static String getVersion() {
        return version;
    }

    public static boolean validarCCT(String cadena) {
        boolean valido = false;
        String patron = "([\\d]{2})([\\w]{3})([\\d]{4})([a-zA-Z]{1})";

        Pattern p = Pattern.compile(patron);
        Matcher matcher = p.matcher(cadena);

        if (matcher.matches()) {
            valido = true;
        } else {
            valido = false;
        }
        return valido;
    }

    public static boolean isValidInt(String str) {
        boolean valid = false;

        try {
            if (str.equalsIgnoreCase("BD")) {
                valid = true;
            } else {
                Integer.parseInt(str);
                valid = true;
            }
        } catch (Exception ex) {
        }
        return valid;
    }

    public static boolean haveAcess(String permiso, LinkedList userAccess) {
        boolean access = false;
        if (userAccess != null && !userAccess.isEmpty()) {
            for (int i = 0; i < userAccess.size() && !access; i++) {
                if (userAccess.get(i).toString().equalsIgnoreCase(permiso)) {
                    access = true;
                }
            }
        }
        return access;
    }

    public static String getEstatusBien(String statusActual) {
        String status = statusActual;
        if (statusActual.equalsIgnoreCase("FUNCIONAL")) {
            status = "F";
        } else if (statusActual.equalsIgnoreCase("NO FUNCIONA")) {
            status = "R";
        } else if (statusActual.equalsIgnoreCase("NO FUNCIONA REPARABLE")) {
            status = "NR";
        }
        return status;
    }

    public static String getMotivoBaja(String motivoActual) {
        String motivo = motivoActual;
        if (motivoActual.equalsIgnoreCase("DAÑADO")) {
            motivo = "A";
        } else if (motivoActual.equalsIgnoreCase("DESUSO")) {
            motivo = "D";
        } else if (motivoActual.equalsIgnoreCase("IRREPARABLE")) {
            motivo = "I";
        }
        return motivo;
    }

   

    public static LinkedList getPermitedExtensions() {
        LinkedList extensiones = new LinkedList();
        extensiones.add(".doc");
        extensiones.add(".docx");
        extensiones.add(".ppt");
        extensiones.add(".pptx");
        extensiones.add(".xls");
        extensiones.add(".xlsx");
        extensiones.add(".txt");
        extensiones.add(".csv");
        extensiones.add(".rar");
        extensiones.add(".zip");
        extensiones.add(".bmp");
        extensiones.add(".gif");
        extensiones.add(".png");
        extensiones.add(".jpeg");
        extensiones.add(".jpg");
        extensiones.add(".pdf");
        return extensiones;
    }

    public static LinkedList getBannedExtensions() {
        LinkedList extensiones = new LinkedList();
        extensiones.add(".exe");
        extensiones.add(".bat");
        extensiones.add(".vbs");
        extensiones.add(".js");
        extensiones.add(".sql");
        extensiones.add(".jar");
        extensiones.add(".msi");
        extensiones.add(".inf");
        extensiones.add(".lnk");
        extensiones.add(".bd");
        extensiones.add(".pif");
        extensiones.add(".com");
        extensiones.add(".dll");
        extensiones.add(".src");
        extensiones.add(".cab");
        extensiones.add("");
        return extensiones;
    }

    public static boolean isABannedExtension(String extension, LinkedList bannedEstensions) {
        boolean access = false;
        if (bannedEstensions != null && !bannedEstensions.isEmpty()) {
            for (int i = 0; i < bannedEstensions.size() && !access; i++) {
                if (bannedEstensions.get(i).toString().equalsIgnoreCase(extension)) {
                    access = true;
                }
            }
        }
        return access;
    }

    public static boolean isAPermitedExtension(String extension, LinkedList permitedEstensions) {
        boolean access = false;
        if (permitedEstensions != null && !permitedEstensions.isEmpty()) {
            for (int i = 0; i < permitedEstensions.size() && !access; i++) {
                if (permitedEstensions.get(i).toString().equalsIgnoreCase(extension)) {
                    access = true;
                }
            }
        }
        return access;
    }

    public static boolean isSystemAllowedExtension(String extension) {
        boolean allowed = false;
        if (PageParameters.getParameter("workExtensions").equalsIgnoreCase("banned")
                && !SystemUtil.isABannedExtension(extension, SystemUtil.getBannedExtensions())) {
            allowed = true;
        } else if (PageParameters.getParameter("workExtensions").equalsIgnoreCase("permited")
                && SystemUtil.isAPermitedExtension(extension, SystemUtil.getPermitedExtensions())) {
            allowed = true;
        }
        return allowed;
    }

    public static String[] getDecodeArray(String[] encodeArray, HttpSession session) {
        String[] decodeArray = new String[encodeArray.length];
        for (int i = 0; i < encodeArray.length; i++) {
            decodeArray[i] = WebUtil.decode(session, encodeArray[i]);
        }
        return decodeArray;
    }

    public static String getMymeTypeIcon(String extension) {
        String mimeType = "message.png";
        switch (extension.trim()) {
            case ".doc":
            case ".docx":
            case ".txt":
            case ".rtf":
            case ".ppt":
                mimeType = "Gnome-X-Office-Document-64.png";
                break;
            case ".pdf":
                mimeType = "application-pdf.png";
                break;
            case ".png":
            case ".jpg":
            case ".jpeg":
            case ".bmp":
            case ".gif":
            case ".ico":
                mimeType = "Gnome-Image-X-Generic-64.png";
                break;
            case ".zip":
            case ".rar":
            case ".gzip":
                mimeType = "gnome-mime-application-x-gzip.png";
                break;
            case ".mp3":
            case ".wav":
            case ".wma":
            case ".ogg":
                mimeType = "gnome-mime-application-x-gzip.png";
                break;
        }
        return mimeType;
    }
    public static String getColor4puntuacion(Double puntuacion){
        String  color="#CCCCCC"; //gris
        if(puntuacion >=9){
            color = "#8FBA00"; //verde
        }else if(puntuacion <9 && puntuacion>=7){
            color = "#FFE51E"; //amarillo
        }else if(puntuacion >=0 &&  puntuacion <7 ){
            color = "#DA001B"; //rojo
        }
        return color;
    }
}
