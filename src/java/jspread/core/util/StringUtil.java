package jspread.core.util;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.StringTokenizer;

/**
 *
 * @author S2 Sistemas
 */
public final class StringUtil {

    //Esta es la version de esta clase
    private static final String version = "V0.31";
    private static double mexicanBillion = Double.parseDouble("1000000000000");

    public static String getVersion() {
        return version;
    }

    /**
     * <p>
     * Metodo - Confirma si un caracter forma parte del 0 al 9
     *
     * <p>
     *
     * @param char c - caracter
     * @return boolean - "true" si si, "false" si no
     */
    public static String convertir2CadenaSat(String cadena, int longitud) {
        cadena = cadena.replace("@", "");
        cadena = cadena.replace("@", "");
        cadena = cadena.replace("\'", "");
        cadena = cadena.replace("%", "");
        cadena = cadena.replace("!", "");
        cadena = cadena.replace("¡", "");
        cadena = cadena.replace(".", "");
        cadena = cadena.replace("$", "");
        cadena = cadena.replace("&", "");
        cadena = cadena.replace("¿", "");
        cadena = cadena.replace("?", "");
        cadena = cadena.replace("Ñ", "");
        cadena = cadena.replace("ñ", "");
        if (cadena.length() > longitud) {
            cadena = cadena.replace(" ", "");
            if (cadena.length() > longitud) {
                cadena = cadena.substring(0, longitud);
            }
        }
        return cadena;
    }

    public static boolean validaCadenaSat(String nombre) {
        boolean valido = true;
        for (int i = 0; i < nombre.length(); i++) {
            if (nombre.charAt(i) == '@' || nombre.charAt(i) == '\'' || nombre.charAt(i) == '%'
                    || nombre.charAt(i) == '!' || nombre.charAt(i) == '¡'
                    || nombre.charAt(i) == '.' || nombre.charAt(i) == '$'
                    || nombre.charAt(i) == '&' || nombre.charAt(i) == '¿'
                    || nombre.charAt(i) == '?' || nombre.charAt(i) == 'Ñ') {
                valido = false;
            }
        }
        return valido;
    }

    public static boolean validaRFCSat(String rfc) {
        boolean valido = false;
        if (rfc.length() == 12 || rfc.length() == 13) {
            valido = true;
        }
        return valido;

    }

    public static boolean isUnNumeroZero_Ten(char c) {
        boolean esNumero = false;
        switch (c) {
            case '0':
                esNumero = true;
                break;

            case '1':
                esNumero = true;
                break;

            case '2':
                esNumero = true;
                break;

            case '3':
                esNumero = true;
                break;

            case '4':
                esNumero = true;
                break;

            case '5':
                esNumero = true;
                break;

            case '6':
                esNumero = true;
                break;

            case '7':
                esNumero = true;
                break;

            case '8':
                esNumero = true;
                break;

            case '9':
                esNumero = true;
                break;

            case '.':
                esNumero = true;
                break;

            default:
                esNumero = false;
        }
        return esNumero;
    }

    /**
     * <p>
     * Metodo - Quita el caracter "," de una cadena de texto
     *
     * <p>
     *
     * @param String whitComma - String para depurar
     * @return String - String sin coma
     */
    public static String withOutComma(String whitComma) {
        String withOutComma = "";
        String[] separated = whitComma.split(",");
        for (int i = 0; i < separated.length; i++) {
            withOutComma = withOutComma + separated[i];
        }
        return withOutComma;
    }

    /**
     * <p>
     * Metodo - Quita el caracter "$" de una cadena de texto
     *
     * <p>
     *
     * @param String whit$ - String para depurar
     * @return String - String sin singo de pesos
     */
    public static String withOut$(String whit$) {
        return whit$.replaceAll("\\$", "");
    }
//    public static void main(String[] args) {
//        System.out.println("" + esUnNumeroZero_Diez('1'));
//        System.out.println("" + esUnNumeroZero_Diez('5'));
//    }

    public static boolean validaTelefono(String valor) {
        boolean valido = false;

        if (valor.length() == 10) {
            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(valor);
            valido = m.matches();
        }
        return valido;
    }

    public static boolean validaEmail(String valor) {
        boolean valido = false;

        if (valor.length() < 51) {
            Pattern p = Pattern.compile("[a-zA-Z0-9]+[.[a-zA-Z0-9_-]+]*@[a-z0-9][\\w\\.-]*[a-z0-9]\\.[a-z][a-z\\.]*[a-z]$");
            Matcher m = p.matcher(valor);
            valido = m.matches();
        }
        return valido;

    }

    public static boolean validaNombre(String valor) {
        boolean valido = false;
        if (valor.length() < 61 && valor.length() > 1) {
            //Pattern p = Pattern.compile("[[a-zA-Z]+[[á]*[é]*[í]*[ó]*[ú]*[Á]*[É]*[Í]*[Ó]*[Ú]*[ñ]*[Ñ]]*[']*[a-zA-Z]*[ ]*]*");//validacion original
            Pattern p = Pattern.compile("[[a-zA-Z]*[[á]*[é]*[í]*[ó]*[ú]*[Á]*[É]*[Í]*[Ó]*[Ú]*[ñ]*[Ñ]]*[']*[.]*[a-zA-Z]*[ ]*]*");
            Matcher m = p.matcher(valor);//[[á]*[é]*[í]*[ó]*[ú]*]* Mayusculas [Á]*[É]*[Í]*[Ó]*[Ú]*
            valido = m.matches();
        }
        return valido;
    }

    public static boolean validaNombreExtended(String valor) {
        boolean valido = false;
        if (valor.length() < 61 && valor.length() > 2) {
            Pattern p = Pattern.compile("[[a-zA-Z]*[[á]*[é]*[í]*[ó]*[ú]*[Á]*[É]*[Í]*[Ó]*[Ú]*[ñ]*[Ñ]]*[']*[.]*[a-zA-Z]*[ ]*]*");
            Matcher m = p.matcher(valor);//[[á]*[é]*[í]*[ó]*[ú]*]* Mayusculas [Á]*[É]*[Í]*[Ó]*[Ú]*
            valido = m.matches();
        }
        return valido;
    }

    public static boolean validaNombre(String valor, int logMax, int logMin) {
        boolean valido = false;
        if (valor.length() <= logMax && valor.length() >= logMin) {
            Pattern p = Pattern.compile("[[a-zA-Z]*[[á]*[é]*[í]*[ó]*[ú]*[Á]*[É]*[Í]*[Ó]*[Ú]*]*[']*[a-zA-Z]*[ ]*]*");
            Matcher m = p.matcher(valor);//[[á]*[é]*[í]*[ó]*[ú]*]* Mayusculas [Á]*[É]*[Í]*[Ó]*[Ú]*
            valido = m.matches();
        }
        return valido;
    }

    @SuppressWarnings("empty-statement")
    public static boolean validaPassword(String password) {
        int aux = 0;
        boolean valido = false;
        b:
        while (aux < password.length()) {
            if (Character.isUpperCase(password.charAt(aux))) {
                valido = true;
                //System.out.println("" + Character.isUpperCase(password.charAt(aux)));
                break b;
            }
            aux++;
        }
        if (!valido) {
            return false;
        } else {
            valido = false;
            aux = 0;
            c:
            while (aux < password.length()) {
                if (Character.isDigit(password.charAt(aux))) {
                    valido = true;
                    break c;
                }
                aux++;
            }
        }
        if (!valido) {
            return false;
        } else {
            valido = false;
            if (password.length() > 5) {
                return true;
            };
        }
        return valido;
    }

    public static boolean validaCurp(String curp) {
        curp = curp.toUpperCase().trim();
        return curp.matches("[A-Z]{4}[0-9]{6}[H,M][A-Z]{5}[A-Z0-9]{2}");
    }

    public static boolean isValidStringLength(String str, int min, int max) {
        boolean valid = false;

        if (str.length() <= max && str.length() >= min) {
            valid = true;
        }
        return valid;
    }

    public static boolean isValidInt(String str, int min, int max) {
        boolean valid = false;

        if (StringUtil.isValidInt(str)) {
            int aux = Integer.parseInt(str);
            if (aux >= min && aux <= max) {
                valid = true;
            }
        }

        return valid;
    }

    public static boolean isNumber(String str) {
        boolean valid = true;
        int aux = 0;
        while (aux < str.length()) {
            if (!isUnNumeroZero_Ten(str.charAt(aux))) {
                return false;
            }
            aux++;
        }
        return valid;
    }

    public static boolean hasNumber(String str) {
        boolean has = false;
        int aux = 0;
        while (aux < str.length()) {
            if (Character.isDigit(str.charAt(aux))) {
                return true;
            }
            aux++;
        }
        return has;
    }

    public static boolean isValidInt(String str) {
        boolean valid = false;

        try {
            Integer.parseInt(str);
            valid = true;
        } catch (Exception ex) {
        }

        return valid;
    }

    public static boolean isPositiveInt(String str) {
        boolean valid = false;

        try {
            if (Integer.parseInt(str) >= 0) {
                valid = true;
            }
        } catch (Exception ex) {
        }

        return valid;
    }

    public static boolean isValidFloat(String str) {
        boolean valid = false;

        try {
            Float.parseFloat(str);
            valid = true;
        } catch (Exception ex) {
        }

        return valid;
    }

    public static boolean isValidDouble(String str) {
        boolean valid = false;

        try {
            Double.parseDouble(str);
            valid = true;
        } catch (Exception ex) {
        }

        return valid;
    }

    public static boolean isValidDoubleMayorCero(String str) {
        boolean valid = false;

        try {
            Double d = Double.parseDouble(str);
            if (d > 0) {
                valid = true;
            }

        } catch (Exception ex) {

        }

        return valid;
    }

    public static boolean isValidDoubleMayorIgualCero(String str) {
        boolean valid = false;

        try {
            Double d = Double.parseDouble(str);
            if (d >= 0) {
                valid = true;
            }

        } catch (Exception ex) {

        }

        return valid;
    }

    public static boolean isValidNote(String str) {
        boolean valid = false;

        try {
            Double d = Double.parseDouble(str);
            if (d >= 0 && d <= 10) {
                valid = true;
            }

        } catch (Exception ex) {
        }

        return valid;
    }

    public static String totalEnLetras(String num) {
        String res = "", dec = "";
        double entero;
        int decimales;
        double nro = 0;

        try {
            nro = Double.parseDouble(num);
        } catch (Exception ex) {
        }

        entero = (double) Math.floor(nro);
        decimales = (int) Math.floor((nro - entero) * 100);

        if (String.valueOf(decimales).length() == 1) {
            dec = " PESOS 0" + decimales + "/100 M.N.";
        } else {
            dec = " PESOS " + decimales + "/100 M.N.";
        }

        res = numberToText((Double.parseDouble("" + entero))) + dec;
        return res;
    }

    public static String numberToText(double value) {
        String Num2Text = "";
        value = Math.floor(value);

        if (value == 0) {
            Num2Text = "CERO";
        } else if (value == 1) {
            Num2Text = "UNO";
        } else if (value == 2) {
            Num2Text = "DOS";
        } else if (value == 3) {
            Num2Text = "TRES";
        } else if (value == 4) {
            Num2Text = "CUATRO";
        } else if (value == 5) {
            Num2Text = "CINCO";
        } else if (value == 6) {
            Num2Text = "SEIS";
        } else if (value == 7) {
            Num2Text = "SIETE";
        } else if (value == 8) {
            Num2Text = "OCHO";
        } else if (value == 9) {
            Num2Text = "NUEVE";
        } else if (value == 10) {
            Num2Text = "DIEZ";
        } else if (value == 11) {
            Num2Text = "ONCE";
        } else if (value == 12) {
            Num2Text = "DOCE";
        } else if (value == 13) {
            Num2Text = "TRECE";
        } else if (value == 14) {
            Num2Text = "CATORCE";
        } else if (value == 15) {
            Num2Text = "QUINCE";
        } else if (value < 20) {
            Num2Text = "DIECI" + numberToText(value - 10);
        } else if (value == 20) {
            Num2Text = "VEINTE";
        } else if (value < 30) {
            Num2Text = "VEINTI" + numberToText(value - 20);
        } else if (value == 30) {
            Num2Text = "TREINTA";
        } else if (value == 40) {
            Num2Text = "CUARENTA";
        } else if (value == 50) {
            Num2Text = "CINCUENTA";
        } else if (value == 60) {
            Num2Text = "SESENTA";
        } else if (value == 70) {
            Num2Text = "SETENTA";
        } else if (value == 80) {
            Num2Text = "OCHENTA";
        } else if (value == 90) {
            Num2Text = "NOVENTA";
        } else if (value < 100) {
            Num2Text = numberToText(Math.floor(value / 10) * 10) + " Y " + numberToText(value % 10);
        } else if (value == 100) {
            Num2Text = "CIEN";
        } else if (value < 200) {
            Num2Text = "CIENTO " + numberToText(value - 100);
        } else if ((value == 200) || (value == 300) || (value == 400) || (value == 600) || (value == 800)) {
            Num2Text = numberToText(Math.floor(value / 100)) + "CIENTOS";
        } else if (value == 500) {
            Num2Text = "QUINIENTOS";
        } else if (value == 700) {
            Num2Text = "SETECIENTOS";
        } else if (value == 900) {
            Num2Text = "NOVECIENTOS";
        } else if (value < 1000) {
            Num2Text = numberToText(Math.floor(value / 100) * 100) + " " + numberToText(value % 100);
        } else if (value == 1000) {
            Num2Text = "MIL";
        } else if (value < 2000) {
            Num2Text = "MIL " + numberToText(value % 1000);
        } else if (value < 1000000) {
            Num2Text = numberToText(Math.floor(value / 1000)) + " MIL";
            if ((value % 1000) > 0) {
                Num2Text = Num2Text + " " + numberToText(value % 1000);
            }
        } else if (value == 1000000) {
            Num2Text = "UN MILLON";
        } else if (value < 2000000) {
            Num2Text = "UN MILLON " + numberToText(value % 1000000);
        } else if (value < mexicanBillion) {
            Num2Text = numberToText(Math.floor(value / 1000000)) + " MILLONES";
            if ((value - Math.floor(value / 1000000) * 1000000) > 0) {
                Num2Text = Num2Text + " " + numberToText(value - Math.floor(value / 1000000) * 1000000);
            }
        } else if (value == mexicanBillion) {
            Num2Text = "UN BILLON";
        } else if (value < mexicanBillion * 2) {
            Num2Text = "UN BILLON " + numberToText(value - Math.floor(value / mexicanBillion) * mexicanBillion);
        } else {
            Num2Text = numberToText(Math.floor(value / mexicanBillion)) + " BILLONES";
            if ((value - Math.floor(value / mexicanBillion) * mexicanBillion) > 0) {
                Num2Text = Num2Text + " " + numberToText(value - Math.floor(value / mexicanBillion) * mexicanBillion);
            }
        }
        return Num2Text;
    }

    public static String firstLetterInMayus(String text) {
        String aux = text;
        if (text.length() > 0) {
            text = "" + text.charAt(0);
            text = text.toUpperCase();
            text = text + aux.substring(aux.length() - aux.length() + 1, aux.length());
        }
        return text;
    }

    public static String everyFirstLetterInMayus(String text) {
        String finalText = "";
        String[] words = text.split("\\s");
        int cont = 0;
        while (cont < words.length) {
            finalText = finalText + " " + firstLetterInMayus(words[cont]);
            cont++;
        }
        finalText = finalText.substring(1);
        text = null;
        words = null;
        return finalText;
    }

    public static int wordsCount(String text) {
        return text.split("\\s").length;
    }

    public static String insertarSeparador(String original, int espacioEntreSeparadores, String separador) {
        String conSeparador = "";
        int cont = 0;
        int contAux = 1;
        while (cont < original.length()) {
            conSeparador = conSeparador + original.charAt(cont);
            if (contAux == espacioEntreSeparadores && cont + 1 < original.length()) {
                conSeparador = conSeparador + separador;
                contAux = 0;
            }
            contAux++;
            cont++;
        }
        return conSeparador;
    }

    public static boolean validaCodigoPostal(String cp) {
        boolean valido = false;

        if (cp.length() == 5) {
            valido = true;
            for (int i = 0; i < cp.length(); i++) {
                if (!Character.isDigit(cp.charAt(i))) {
                    valido = false;
                }
            }
        }
        return valido;
    }

    public static boolean validaColonia(String colonia) {
        boolean valido = false;
        colonia = colonia.trim();
        String[] nombre = colonia.split(" ");
        if (nombre.length > 0 && StringUtil.validaNombreExtended(nombre[0]) || ((nombre[0].equalsIgnoreCase("la")
                || nombre[0].equalsIgnoreCase("el") || nombre[0].equalsIgnoreCase("las")) || nombre[0].equalsIgnoreCase("los")
                || nombre[0].equalsIgnoreCase("las") && (nombre.length > 1 && StringUtil.validaNombreExtended(nombre[1])))) {
            valido = true;
        }
        return valido;
    }

    public static String generaPassword(int longitud) {
        String password = "";
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz+-%$&@-_/*";
        for (int i = 0; i < longitud; i++) {
            password += (chars.charAt((int) (Math.random() * chars.length())));
        }

        return password;
    }

    public static String insertaSeparador(String cadena, String separador, int posicion) {
        String resultado = "";
        for (int i = 0; i < cadena.length(); i++) {
            resultado = resultado + cadena.charAt(i);
            if (((i + 1) % posicion == 0) && i < cadena.length() - 1) {
                resultado = resultado + separador;
            }

        }

        return resultado;
    }

    public static String eliminaCaracter(String cadena, char caracter) {
        String resultado = "";
        for (int i = 0; i < cadena.length(); i++) {
            if (cadena.charAt(i) != caracter) {
                resultado += cadena.charAt(i);
            }
        }
        return resultado;
    }

    /**
     * Obtiene la clave curp
     *
     * @params nombre|aPaterno|aMaterno|fechaNacimiento->debe tener el formato
     * aaaa-mm-dd genero -> masculino/femenino | entidad -> ver metodo para
     * verificar nombre excato de entidades
     * @return String - con clave CURP generada
     */
    public static String generaCurp(String nombres, String aPaterno, String aMaterno, String fNacimiento, String genero, String edoNacimiento) {
        String curp = "";
        //System.out.println("apaterno:"+aPaterno);
        aPaterno = curpCharSet1(aPaterno.toUpperCase());
        aMaterno = curpCharSet1(aMaterno.toUpperCase());
        nombres = curpCharSet1(nombres.toUpperCase());

        nombres = getValidNameCurp(nombres, false).trim();
        aPaterno = getValidNameCurp(aPaterno, true).trim();
        aMaterno = getValidNameCurp(aMaterno, true).trim();

        edoNacimiento = removeLastSpace(edoNacimiento.toUpperCase());

        curp = aPaterno.charAt(0) + firsVowelAfterFirstLetterCURP(aPaterno);

        aPaterno = curpCharSet2(aPaterno.toUpperCase());
        aMaterno = curpCharSet2(aMaterno.toUpperCase());
        nombres = curpCharSet2(nombres.toUpperCase());

        if (aMaterno.length() > 0) {
            curp = curp + aMaterno.charAt(0);
        } else {
            curp = curp + "X";
        }

        curp = curp + nombres.charAt(0);
        curp = curp + fNacimiento.charAt(2)
                + fNacimiento.charAt(3)
                + fNacimiento.charAt(5)
                + fNacimiento.charAt(6)
                + fNacimiento.charAt(8)
                + fNacimiento.charAt(9);

        curp += (genero.equalsIgnoreCase("masculino")) ? "H" : "M";

        curp = curp + calcEstadoCrup(edoNacimiento);
        curp = curp + firstConsonantAfterFirstLetterCURP(aPaterno);
        curp = curp + firstConsonantAfterFirstLetterCURP(aMaterno);
        curp = curp + firstConsonantAfterFirstLetterCURP(nombres);

        curp += (UTime.getAnio(fNacimiento) <= 2000) ? "0" : "A";

        curp = curpBlackList(curp.substring(0, 4)) + curp.substring(4);
        curp = curp + calculaDigitoCURP(curp);
        return curp;

    }

    //    public static String generaCurp(String nombre, String aPaterno, String aMaterno,
//        String fechaNacimiento, String genero, String entidad) {
//        String curp = "";
//        nombre = nombre.trim();
//        aPaterno = aPaterno.trim();
//        aMaterno = aMaterno.trim();
//        fechaNacimiento = fechaNacimiento.trim();
//        genero = genero.trim();
//        entidad = entidad.trim().toLowerCase();
//        if (!(nombre.equals("") && aPaterno.equals("") && aMaterno.equals("")
//                && fechaNacimiento.equals("") && genero.equals("")
//                && entidad.equals(""))) {
//            nombre = StringUtil.getNombrePila(nombre);
//            aPaterno = StringUtil.getNombreSinArticulos(aPaterno);
//            aMaterno = StringUtil.getNombreSinArticulos(aMaterno);
//
//            curp += aPaterno.charAt(0);
//
//            curp = curp + StringUtil.getVocal(aPaterno, 1) + aMaterno.charAt(0) + nombre.charAt(0);
//            String anio = fechaNacimiento.substring(0, 4);
//            curp += anio.substring(2, 4);
//            curp += StringUtil.eliminaCaracter(fechaNacimiento.substring(5, fechaNacimiento.length()), '-');
//            curp += (genero.equalsIgnoreCase("masculino")) ? "H" : "M";
//            String estado = "";
//            //System.out.println("estado:" + entidad);
//            switch (entidad) {
//                case "estado de méxico":
//                    estado = "MC";
//                    break;
//
//                case "aguascalientes":
//                    estado = "AS";
//                    break;
//                case "baja california":
//                    estado = "BC";
//                    break;
//
//                case "baja california sur":
//                    estado = "BS";
//                    break;
//                case "campeche":
//                    estado = "CC";
//                    break;
//                case "coahuila":
//                    estado = "CL";
//                    break;
//                case "colima":
//                    estado = "CM";
//                    break;
//                case "chiapas":
//                    estado = "CS";
//                    break;
//                case "chihuahua":
//                    estado = "CH";
//                    break;
//                case "distrito federal":
//                    estado = "DF";
//                    break;
//                case "durango":
//                    estado = "DG";
//                    break;
//                case "guanajuato":
//                    estado = "GT";
//                    break;
//                case "guerrero":
//                    estado = "GR";
//                    break;
//                case "hidalgo":
//                    estado = "HG";
//                    break;
//                case "jalisco":
//                    estado = "JC";
//                    break;
//                case "michoacán":
//                    estado = "MN";
//                    break;
//                case "morelos":
//                    estado = "MS";
//                    break;
//                case "nayarit":
//                    estado = "NT";
//                    break;
//                case "nuevo león":
//                    estado = "NL";
//                    break;
//                case "oaxaca":
//                    estado = "OC";
//                    break;
//                case "puebla":
//                    estado = "PL";
//                    break;
//                case "querétaro":
//                    estado = "QT";
//                    break;
//                case "quintana roo":
//                    estado = "QR";
//                    break;
//                case "san luis potosí":
//                    estado = "SP";
//                    break;
//                case "sinaloa":
//                    estado = "SL";
//                    break;
//                case "sonora":
//                    estado = "SR";
//                    break;
//                case "tabasco":
//                    estado = "TC";
//                    break;
//                case "tamaulipas":
//                    estado = "TS";
//                    break;
//                case "tlaxcala":
//                    estado = "TL";
//                    break;
//                case "veracruz":
//                    estado = "VZ";
//                    break;
//                case "yucatán":
//                    estado = "YN";
//                    break;
//                case "zacatecas":
//                    estado = "ZS";
//                    break;
//                default:
//                    estado = "NE";
//            }
//            curp = curp + estado + StringUtil.getConsonatInterna(aPaterno, 1);
//            curp += StringUtil.getConsonatInterna(aMaterno, 1);
//            curp += StringUtil.getConsonatInterna(nombre, 1);
//
//        }
//
//        return curp.toUpperCase();
//
//    }
///**
//     *Obtiene el nombre de pila de una persona
//     * omitiendo los articulos de los la del, pero si el primer
//     * nombre es Jose o Maria devuelve el segundo nombre si es que existe
//     * 
//     *@params nombre
//     * @return String - con el nombre de pila
//     */
//    public static String getNombrePila(String nombre) {
//        String nPila = "";
//        if (StringUtil.validaNombre(nombre)) {
//          //  System.out.println("llega nombre:" + nombre);
//            String[] tokens = nombre.trim().split(" ");
//            for (int i = 0; i < tokens.length; i++) {
//            //    System.out.println("token :" + i + tokens[i]);
//                if (!(tokens[i].equalsIgnoreCase("la")
//                        || tokens[i].equalsIgnoreCase("las")
//                        || tokens[i].equalsIgnoreCase("de")
//                        || tokens[i].equalsIgnoreCase("del")
//                        || tokens[i].equalsIgnoreCase("los"))) {
//                    nPila += tokens[i] + " ";
//                }
//            }
//            tokens = nPila.trim().split(" ");
//            if (tokens.length > 1) {
//                if (!tokens[0].equalsIgnoreCase("jose") && !tokens[0].equalsIgnoreCase("maria")) {
//                    nPila = tokens[0];
//                } else {
//                    nPila = tokens[1];
//                }
//            }
//        }
//        return nPila;
//    }
    /**
     * Obtiene el nombre de pila de una persona omitiendo los articulos de los
     * la del,
     *
     * @params nombre
     * @return String - con el nombre sin articulos
     */
    public static String getNombreSinArticulos(String nombre) {
        String nPila = "";
        if (StringUtil.validaNombre(nombre)) {
            // System.out.println("llega nombre:" + nombre);
            String[] tokens = nombre.trim().split(" ");
            for (int i = 0; i < tokens.length; i++) {
                //  System.out.println("token :" + i + tokens[i]);
                if (!(tokens[i].equalsIgnoreCase("la")
                        || tokens[i].equalsIgnoreCase("las")
                        || tokens[i].equalsIgnoreCase("de")
                        || tokens[i].equalsIgnoreCase("del")
                        || tokens[i].equalsIgnoreCase("los"))) {
                    nPila += tokens[i] + " ";
                }
            }

        }
        return nPila.trim();
    }

    /**
     * Obtiene la vocal ubucada en la posicion n dentro de una cadena
     *
     * @params cadena|n ->posicion deseada
     * @return char - con la vocal deseada, regresa - si no se encuentra
     */
    public static char getVocal(String cadena, int n) {
        char letra = '-';
        int matches = 0;
        cadena = cadena.toUpperCase();
        for (int i = 0; i < cadena.length() && letra == '-'; i++) {

            if (cadena.charAt(i) == 'A' || cadena.charAt(i) == 'E' || cadena.charAt(i) == 'I'
                    || cadena.charAt(i) == 'O' || cadena.charAt(i) == 'U') {
                matches += 1;

                if (matches == n) {
                    letra = cadena.charAt(i);
                }

            }
        }
        return letra;
    }

    /**
     * Obtiene la vocal ubucada en la posicion n dentro de una cadena ignorando
     * la primera posicion
     *
     * @params cadena|n ->posicion deseada
     * @return char - con la vocal deseada, regresa - si no se encuentra
     */
    public static char getVocalInterna(String cadena, int n) {
        char letra = '-';
        int matches = 0;
        cadena = cadena.toUpperCase();
        cadena = cadena.substring(1, cadena.length() - 1);
        for (int i = 0; i < cadena.length() && letra == '-'; i++) {

            if (cadena.charAt(i) == 'A' || cadena.charAt(i) == 'E' || cadena.charAt(i) == 'I'
                    || cadena.charAt(i) == 'O' || cadena.charAt(i) == 'U') {
                matches += 1;

                if (matches == n) {
                    letra = cadena.charAt(i);
                }

            }
        }
        return letra;
    }

    /**
     * Obtiene la consonante ubucada en la posicion n dentro de una cadena
     * ignorando la primera posicion
     *
     * @params cadena|n ->posicion deseada
     * @return char - con la consonante deseada, regresa - si no se encuentra
     */
    public static char getConsonatInterna(String cadena, int n) {
        char letra = '-';
        int matches = 0;
        cadena = cadena.toUpperCase();
        //System.out.print(cadena);
        cadena = cadena.substring(1, cadena.length());
        for (int i = 0; i < cadena.length() && letra == '-'; i++) {
            //System.out.print(cadena.charAt(i));
            if ((cadena.charAt(i) >= 'A' && cadena.charAt(i) <= 'Z')
                    && !(cadena.charAt(i) == 'A' || cadena.charAt(i) == 'E' || cadena.charAt(i) == 'I'
                    || cadena.charAt(i) == 'O' || cadena.charAt(i) == 'U')) {
                matches += 1;
                //  System.out.print(cadena.charAt(i));
                if (matches == n) {
                    letra = cadena.charAt(i);
                }
            }
        }
        return letra;
    }

    /**
     * Obtiene la consonante ubucada en la posicion n dentro de una cadena
     *
     * @params cadena|n ->posicion deseada
     * @return char - con la consonante deseada, regresa - si no se encuentra
     */
    public static char getConsonat(String cadena, int n) {
        char letra = '-';
        int matches = 0;
        cadena = cadena.toUpperCase();
        //System.out.print(cadena);
        for (int i = 0; i < cadena.length() && letra == '-'; i++) {
            //System.out.print(cadena.charAt(i));
            if ((cadena.charAt(i) >= 'A' && cadena.charAt(i) <= 'Z')
                    && !(cadena.charAt(i) == 'A' || cadena.charAt(i) == 'E' || cadena.charAt(i) == 'I'
                    || cadena.charAt(i) == 'O' || cadena.charAt(i) == 'U')) {
                matches += 1;
                //  System.out.print(cadena.charAt(i));
                if (matches == n) {
                    letra = cadena.charAt(i);
                }
            }
        }
        return letra;
    }

    public static String curpBlackList(String primerasLetras) {
        switch (primerasLetras) {
            case "BACA":
                primerasLetras = "BXCA";
                break;
            case "BAKA":
                primerasLetras = "BXKA";
                break;
            case "BUEI":
                primerasLetras = "BXEI";
                break;
            case "BUEY":
                primerasLetras = "BXEY";
                break;
            case "CACA":
                primerasLetras = "CXCA";
                break;
            case "CACO":
                primerasLetras = "CXCO";
                break;
            case "CAGA":
                primerasLetras = "CXGA";
                break;
            case "CAGO":
                primerasLetras = "CXGO";
                break;
            case "CAKA":
                primerasLetras = "CXKA";
                break;
            case "CAKO":
                primerasLetras = "CXKO";
                break;
            case "COGE":
                primerasLetras = "CXGE";
                break;
            case "COGI":
                primerasLetras = "CXGI";
                break;
            case "COJA":
                primerasLetras = "CXJA";
                break;
            case "COJE":
                primerasLetras = "CXJE";
                break;
            case "COJI":
                primerasLetras = "CXJI";
                break;
            case "COJO":
                primerasLetras = "CXJO";
                break;
            case "COLA":
                primerasLetras = "CXLA";
                break;
            case "CULO":
                primerasLetras = "CXLO";
                break;
            case "FALO":
                primerasLetras = "FXLO";
                break;
            case "FETO":
                primerasLetras = "FXTO";
                break;
            case "GETA":
                primerasLetras = "GXTA";
                break;
            case "GUEI":
                primerasLetras = "GXEI";
                break;
            case "GUEY":
                primerasLetras = "GXEY";
                break;
            case "JETA":
                primerasLetras = "JXTA";
                break;
            case "JOTO":
                primerasLetras = "JXTO";
                break;
            case "KACA":
                primerasLetras = "KXCA";
                break;
            case "KACO":
                primerasLetras = "KXCO";
                break;
            case "KAGA":
                primerasLetras = "KXGA";
                break;
            case "KAGO":
                primerasLetras = "KXGO";
                break;
            case "KAKA":
                primerasLetras = "KXKA";
                break;
            case "KAKO":
                primerasLetras = "KXKO";
                break;
            case "KOGE":
                primerasLetras = "KXGE";
                break;
            case "KOGI":
                primerasLetras = "KXGI";
                break;
            case "KOJA":
                primerasLetras = "KXJA";
                break;
            case "KOJE":
                primerasLetras = "KXJE";
                break;
            case "KOJI":
                primerasLetras = "KXJI";
                break;
            case "KOJO":
                primerasLetras = "KXJO";
                break;
            case "KOLA":
                primerasLetras = "KXLA";
                break;
            case "KULO":
                primerasLetras = "KXLO";
                break;
            case "LILO":
                primerasLetras = "LXLO";
                break;
            case "LOCA":
                primerasLetras = "LXCA";
                break;
            case "LOCO":
                primerasLetras = "LXCO";
                break;
            case "LOKA":
                primerasLetras = "LXKA";
                break;
            case "LOKO":
                primerasLetras = "LXKO";
                break;
            case "MAME":
                primerasLetras = "MXME";
                break;
            case "MAMO":
                primerasLetras = "MXMO";
                break;
            case "MEAR":
                primerasLetras = "MXAR";
                break;
            case "MEAS":
                primerasLetras = "MXAS";
                break;
            case "MEON":
                primerasLetras = "MXON";
                break;
            case "MIAR":
                primerasLetras = "MXAR";
                break;
            case "MION":
                primerasLetras = "MXON";
                break;
            case "MOCO":
                primerasLetras = "MXCO";
                break;
            case "MOKO":
                primerasLetras = "MXKO";
                break;
            case "MULA":
                primerasLetras = "MXLA";
                break;
            case "MULO":
                primerasLetras = "MXLO";
                break;
            case "NACA":
                primerasLetras = "NXCA";
                break;
            case "NACO":
                primerasLetras = "NXCO";
                break;
            case "PEDA":
                primerasLetras = "PXDA";
                break;
            case "PEDO":
                primerasLetras = "PXDO";
                break;
            case "PENE":
                primerasLetras = "PXNE";
                break;
            case "PIPI":
                primerasLetras = "PXPI";
                break;
            case "PITO":
                primerasLetras = "PXTO";
                break;
            case "POPO":
                primerasLetras = "PXPO";
                break;
            case "PUTA":
                primerasLetras = "PXTA";
                break;
            case "PUTO":
                primerasLetras = "PXTO";
                break;
            case "QULO":
                primerasLetras = "QXLO";
                break;
            case "RATA":
                primerasLetras = "RXTA";
                break;
            case "ROBA":
                primerasLetras = "RXBA";
                break;
            case "ROBE":
                primerasLetras = "RXBE";
                break;
            case "ROBO":
                primerasLetras = "RXBO";
                break;
            case "RUIN":
                primerasLetras = "RXIN";
                break;
            case "SENO":
                primerasLetras = "SXNO";
                break;
            case "TETA":
                primerasLetras = "TXTA";
                break;
            case "VACA":
                primerasLetras = "VXCA";
                break;
            //case "VAGA":
            //    primerasLetras = "VXGA";
            //    break;
            case "VAGO":
                primerasLetras = "VXGO";
                break;
            case "VAKA":
                primerasLetras = "VXKA";
                break;
            case "VUEI":
                primerasLetras = "VXEI";
                break;
            case "VUEY":
                primerasLetras = "VXEY";
                break;
            case "WUEI":
                primerasLetras = "WXEI";
                break;
            case "WUEY":
                primerasLetras = "WXEY";
                break;
        }
        return primerasLetras;
    }

    public static String firstConsonant(String word) {
        String firstConsonat = "";
        String vowels = "AEIOU";

        int cont = 0;
        while (cont < word.length()) {
            if (!vowels.contains("" + word.charAt(cont))) {
                return "" + word.charAt(cont);
            }
            cont++;
        }
        return firstConsonat;
    }

    public static String getValidNameCurp(String nombre, boolean apellido) {

        String nPila = "";
        //if (StringUtil.validaNombre(nombre)) {
        //  System.out.println("llega nombre:" + nombre);
        String[] tokens = nombre.trim().split(" ");
        for (int i = 0; i < tokens.length; i++) {
            // System.out.println("token :" + i + tokens[i]);
            if (!(tokens[i].equalsIgnoreCase("la")
                    || tokens[i].equalsIgnoreCase("las")
                    || tokens[i].equalsIgnoreCase("de")
                    || tokens[i].equalsIgnoreCase("del")
                    || tokens[i].equalsIgnoreCase("los")
                    || tokens[i].equalsIgnoreCase("der")
                    || tokens[i].equalsIgnoreCase("di")
                    || tokens[i].equalsIgnoreCase("die")
                    || tokens[i].equalsIgnoreCase("dd")
                    || tokens[i].equalsIgnoreCase("le")
                    || tokens[i].equalsIgnoreCase("les")
                    || tokens[i].equalsIgnoreCase("mac}")
                    || tokens[i].equalsIgnoreCase("mc")
                    || tokens[i].equalsIgnoreCase("van")
                    || tokens[i].equalsIgnoreCase("von")
                    || tokens[i].equalsIgnoreCase("y")
                    || tokens[i].equalsIgnoreCase("das")
                    || tokens[i].equalsIgnoreCase("da"))) {
                nPila += tokens[i] + " ";
            }
        }
        tokens = nPila.trim().split(" ");
        // System.out.println("npila:" + nPila);
        if (!apellido) {
            if (tokens.length > 1) {
                if (!tokens[0].equalsIgnoreCase("jose")
                        && !tokens[0].equalsIgnoreCase("maria")
                        && !tokens[0].equalsIgnoreCase("ma.")
                        && !tokens[0].equalsIgnoreCase("j")
                        && !tokens[0].equalsIgnoreCase("j.")
                        && !tokens[0].equalsIgnoreCase("ma")) {
                    nPila = tokens[0];
                } else {
                    nPila = tokens[1];
                }
            }
        }
        //}
        return nPila;
    }

    public static String firstConsonantAfterFirstLetter(String word) {
        String firstConsonat = "";
        String vowels = "AEIOU";

        int cont = 1;
        while (cont < word.length()) {
            if (!vowels.contains("" + word.charAt(cont))) {
                return "" + word.charAt(cont);
            }
            cont++;
        }
        return firstConsonat;
    }

    public String firsVowel(String word) {
        String firsVowel = "";
        String vowels = "AEIOU";

        int cont = 0;
        while (cont < word.length()) {
            if (vowels.contains("" + word.charAt(cont))) {
                return "" + word.charAt(cont);
            }
            cont++;
        }
        return firsVowel;
    }

    public static String firsVowelAfterFirstLetter(String word) {
        String firsVowel = "";
        String vowels = "AEIOU";

        int cont = 1;
        while (cont < word.length()) {
            //System.out.println("word.charAt(cont): "+word.charAt(cont));
            if (vowels.contains("" + word.charAt(cont))) {
                return "" + word.charAt(cont);
            }
            cont++;
        }
        return firsVowel;
    }

    public static String removeLastSpace(String word) {
        if (word.charAt(word.length() - 1) == ' ' && word.length() > 1) {
            word = word.substring(0, word.length() - 2);
        }
        return word;
    }

    public static String removeFirstSpace(String word) {
        if (word.charAt(0) == ' ' && word.length() > 1) {
            word = word.substring(1, word.length() - 1);
        }
        return word;
    }

    public static String calcEstadoCrup(String edoNacimiento) {
        String estadoCurp = "";
        switch (edoNacimiento.trim().toLowerCase()) {
            case "estado de méxico":
                estadoCurp = "MC";
                break;
            case "aguascalientes":
                estadoCurp = "AS";
                break;
            case "baja california":
                estadoCurp = "BC";
                break;
            case "baja california sur":
                estadoCurp = "BS";
                break;
            case "campeche":
                estadoCurp = "CC";
                break;
            case "coahuila":
                estadoCurp = "CL";
                break;
            case "colima":
                estadoCurp = "CM";
                break;
            case "chiapas":
                estadoCurp = "CS";
                break;
            case "chihuahua":
                estadoCurp = "CH";
                break;
            case "distrito federal":
                estadoCurp = "DF";
                break;
            case "durango":
                estadoCurp = "DG";
                break;
            case "guanajuato":
                estadoCurp = "GT";
                break;
            case "guerrero":
                estadoCurp = "GR";
                break;
            case "hidalgo":
                estadoCurp = "HG";
                break;
            case "jalisco":
                estadoCurp = "JC";
                break;
            case "michoacán":
                estadoCurp = "MN";
                break;
            case "morelos":
                estadoCurp = "MS";
                break;
            case "nayarit":
                estadoCurp = "NT";
                break;
            case "nuevo león":
                estadoCurp = "NL";
                break;
            case "oaxaca":
                estadoCurp = "OC";
                break;
            case "puebla":
                estadoCurp = "PL";
                break;
            case "querétaro":
                estadoCurp = "QT";
                break;
            case "quintana roo":
                estadoCurp = "QR";
                break;
            case "san luis potosí":
                estadoCurp = "SP";
                break;
            case "sinaloa":
                estadoCurp = "SL";
                break;
            case "sonora":
                estadoCurp = "SR";
                break;
            case "tabasco":
                estadoCurp = "TC";
                break;
            case "tamaulipas":
                estadoCurp = "TS";
                break;
            case "tlaxcala":
                estadoCurp = "TL";
                break;
            case "veracruz":
                estadoCurp = "VZ";
                break;
            case "yucatán":
                estadoCurp = "YN";
                break;
            case "zacatecas":
                estadoCurp = "ZS";
                break;
            default:
                estadoCurp = "NE";
        }

        //System.out.println("estadoCurp: "+estadoCurp);
        return estadoCurp;
    }

    public static String removeSpecialCharacters(String word) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = word;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }

    public static String curpCharSet1(String word) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ-/";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUXcCXX";
        String output = word;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }

    public static String curpCharSet2(String word) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ.-/'";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUXcCXXXX";
        String output = word;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }

    public static String quitarPreposicionesCurp(String word) {
        if (StringUtil.wordsCount(word) > 1) {
            if (word.contains("DA")) {
                word = word.replaceFirst("DA", "");
            }
            if (word.contains("DAS")) {
                word = word.replaceFirst("DAS", "");
            }
            if (word.contains("DE")) {
                word = word.replaceFirst("DE", "");
            }
            if (word.contains("DEL")) {
                word = word.replaceFirst("DEL", "");
            }
            if (word.contains("DER")) {
                word = word.replaceFirst("DER", "");
            }
            if (word.contains("DI")) {
                word = word.replaceFirst("DI", "");
            }
            if (word.contains("DIE")) {
                word = word.replaceFirst("DIE", "");
            }
            if (word.contains("DD")) {
                word = word.replaceFirst("DD", "");
            }
            if (word.contains("EL")) {
                word = word.replaceFirst("EL", "");
            }
            if (word.contains("LA")) {
                word = word.replaceFirst("LA", "");
            }
            if (word.contains("LOS")) {
                word = word.replaceFirst("LOS", "");
            }
            if (word.contains("LAS")) {
                word = word.replaceFirst("LAS", "");
            }
            if (word.contains("LE")) {
                word = word.replaceFirst("LE", "");
            }
            if (word.contains("LES")) {
                word = word.replaceFirst("LES", "");
            }
            if (word.contains("MAC")) {
                word = word.replaceFirst("MAC", "");
            }
            if (word.contains("MC")) {
                word = word.replaceFirst("MC", "");
            }
            if (word.contains("VAN")) {
                word = word.replaceFirst("VAN", "");
            }
            if (word.contains("VON")) {
                word = word.replaceFirst("VON", "");
            }
            if (word.contains("Y")) {
                word = word.replaceFirst("Y", "");
            }
        }
        return word;
    }

    public static String firstConsonantCURP(String word) {
        String firstConsonat = "X";
        String vowels = "AEIOU";

        int cont = 0;
        while (cont < word.length()) {
            if (!vowels.contains("" + word.charAt(cont))) {
                return "" + word.charAt(cont);
            }
            cont++;
        }
        return firstConsonat;
    }

    public static String firstConsonantAfterFirstLetterCURP(String word) {
        String firstConsonat = "X";
        String vowels = "AEIOU";

        int cont = 1;
        while (cont < word.length()) {
            if (!vowels.contains("" + word.charAt(cont))) {
                return "" + word.charAt(cont);
            }
            cont++;
        }
        return firstConsonat;
    }

    public String firsVowelCURP(String word) {
        String firsVowel = "CURP";
        String vowels = "AEIOU";

        int cont = 0;
        while (cont < word.length()) {
            if (vowels.contains("" + word.charAt(cont))) {
                return "" + word.charAt(cont);
            }
            cont++;
        }
        return firsVowel;
    }

    public static String firsVowelAfterFirstLetterCURP(String word) {
        String firsVowel = "X";
        String vowels = "AEIOU'";

        int cont = 1;
        while (cont < word.length()) {
            //System.out.println("word.charAt(cont): "+word.charAt(cont));
            if (vowels.contains("" + word.charAt(cont))) {
                if (word.charAt(cont) == '\'') {
                    return "X";
                } else {
                    return "" + word.charAt(cont);
                }
            }
            cont++;
        }
        return firsVowel;
    }

    public static int calculaDigitoCURP(String curp) {
        String segRaiz = curp.substring(0, 17);
        String chrCaracter = "0123456789ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
        //int[] intFactor    = int[17];
        int intFactor[] = new int[17];
        int lngSuma = 0;
        int lngDigito = 0;

        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 37; j++) {
                if (segRaiz.substring(i, i + 1).equals(chrCaracter.substring(j, j + 1))) {
                    intFactor[i] = j;
                }
            }
        }

        for (int k = 0; k < 17; k++) {
            lngSuma = lngSuma + ((intFactor[k]) * (18 - k));
        }

        lngDigito = (10 - (lngSuma % 10));

        if (lngDigito == 10) {
            lngDigito = 0;
        }

        return lngDigito;
    }

    public static boolean matchCurp(
            String curp,
            String nombres,
            String aPaterno,
            String aMaterno,
            String fNacimiento,
            String sexo,
            String eNacimiento) {
        // System.out.println("curp escrito: "+curp);

        boolean match = false;
        char digito = '-';
        int anio = 0;
        String myCurp = StringUtil.generaCurp(nombres, aPaterno, aMaterno, fNacimiento, sexo, eNacimiento);
        if (curp.length() > 1) {
            curp = curp.toUpperCase().trim();
            digito = curp.charAt(curp.length() - 2);
            anio = UTime.getAnio(fNacimiento);

            //System.out.println("digito:" + digito);
            //System.out.println("año:" + anio);
            //System.out.println("curp:" + myCurp);
//            if ((anio < 2000 && digito == '0') && curp.contains(myCurp)) {
//                match = true;
//            } else if (anio >= 2000 && (digito >= 'A' && digito <= 'Z') && curp.contains(myCurp)) {
//                match = true;
//            }
            myCurp = myCurp.substring(0, 16);
            myCurp = myCurp + curp.charAt(16);
            myCurp = myCurp + calculaDigitoCURP(myCurp);

            //System.out.println("CURP CALCULADO: " + myCurp);
            //System.out.println("CURP INTRODUCIDO PARA VALIDAD: " + curp);
            if (myCurp.equals(curp)) {
                match = true;
            }
        }
        //System.out.println("curp generado: "+myCurp);
        return match;
    }

    public static String getEdoFromCurp(String curp) {
        String edo = "";
        switch (curp.substring(11, 13).trim().toUpperCase()) {
            case "MC":
                edo = "estado de méxico";
                break;
            case "AS":
                edo = "aguascalientes";
                break;
            case "BC":
                edo = "baja california";
                break;
            case "BS":
                edo = "baja california sur";
                break;
            case "CC":
                edo = "campeche";
                break;
            case "CL":
                edo = "campeche";
                break;
            case "CM":
                edo = "colima";
                break;
            case "CS":
                edo = "chiapas";
                break;
            case "CH":
                edo = "chihuahua";
                break;
            case "DF":
                edo = "distrito federal";
                break;
            case "DG":
                edo = "durango";
                break;
            case "GT":
                edo = "guanajuato";
                break;
            case "GR":
                edo = "guerrero";
                break;
            case "HG":
                edo = "hidalgo";
                break;
            case "JC":
                edo = "jalisco";
                break;
            case "MN":
                edo = "michoacán";
                break;
            case "MS":
                edo = "morelos";
                break;
            case "NT":
                edo = "nayarit";
                break;
            case "NL":
                edo = "nuevo león";
                break;
            case "OC":
                edo = "oaxaca";
                break;
            case "PL":
                edo = "puebla";
                break;
            case "QT":
                edo = "querétaro";
                break;
            case "QR":
                edo = "quintana roo";
                break;
            case "SP":
                edo = "san luis potosí";
                break;
            case "SL":
                edo = "sinaloa";
                break;
            case "SR":
                edo = "sonora";
                break;
            case "TC":
                edo = "tabasco";
                break;
            case "TS":
                edo = "tamaulipas";
                break;
            case "TL":
                edo = "tlaxcala";
                break;
            case "VZ":
                edo = "veracruz";
                break;
            case "YN":
                edo = "yucatán";
                break;
            case "ZS":
                edo = "zacatecas";
                break;
            default:
                edo = "Nacido Extragero";
        }
        //System.out.println("edo: " + StringUtil.everyFirstLetterInMayus(edo));
        return StringUtil.everyFirstLetterInMayus(edo);
    }

    public static String toTipoOracion(String cadena) {
        String oracion = "";
//        if(!cadena.equalsIgnoreCase("")){
//            cadena=cadena.trim();
//            String[] token=cadena.split(" ");
//            for (int i = 0; i < token.length; i++) {
//                token[i]=token[i].toLowerCase();
//                if(token[i].charAt(1)>='a' && token[i].charAt(1)>='z'){
//                    String aux=(token[i].length()>1)?token[i].substring(i, token[i].length()):token[i];
//                    Character x=token[i].charAt(1);
//                    oracion=oracion+" "+x+" "aux+" ";
//                    
//                }
//            }
//        }
        return oracion;
    }

    public static String quitarComillas(String cadena) {
        cadena = cadena.replace("\"", "");
        return cadena.trim();
    }

    public static boolean isCifraEntera(String numero) {
        boolean respuesta = false;
        if (StringUtil.isValidDouble(numero)) {
            Double newNumber = Double.parseDouble(numero);
            if (newNumber % 1 == 0) {
                respuesta = true;
            }
        }
        return respuesta;
    }

    public static int double2Int(String numero) {
        int resultado = 0;
        if (StringUtil.isValidDouble(numero)) {
            Double newNumber = Double.parseDouble(numero);
            if (newNumber % 1 == 0) {
                return (int) Math.floor(newNumber);
            }
        }
        return resultado;
    }

    public static String roudDobule2Int(String valor) {
        double numero = Math.rint(Double.parseDouble(valor));
        int nuevoNumero = 0;
        nuevoNumero = (int) numero;
        return new Integer(nuevoNumero).toString();
    }

    public static Integer roudDobule2Int(double valor) {
        double numero = Math.rint(valor);
        int nuevoNumero = 0;
        nuevoNumero = (int) numero;
        return new Integer(nuevoNumero);
    }

    public static String formatDouble1Decimals(Double numero) {
        String resultado = "0.0";
        DecimalFormat fomater = new DecimalFormat("#.#");
        fomater.setRoundingMode(RoundingMode.DOWN);
        resultado = fomater.format(numero);
        return resultado;
    }

    public static String formatDouble1Decimals(String numero) {
        String resultado = "0.0";
        if (StringUtil.isValidDouble(numero)) {
            DecimalFormat fomater = new DecimalFormat("#.#");
            resultado = fomater.format(Double.parseDouble(numero));
        }
        return resultado;
    }

    public static String formatDouble2Decimals(String numero) {
        String resultado = "0.00";
        if (StringUtil.isValidDouble(numero)) {
            DecimalFormat fomater = new DecimalFormat("#.00");
            resultado = fomater.format(Double.parseDouble(numero));
        }
        return resultado;
    }

    public static String formatDouble2Decimals(Double numero) {
        String resultado = "0.0";
        DecimalFormat fomater = new DecimalFormat("#.00");
        resultado = fomater.format(numero);
        return resultado;
    }

    public static void printHashMap(HashMap map) {
        System.out.println("imprimiendo: " + map.size());
        Iterator iterator = map.keySet().iterator();

        while (iterator.hasNext()) {
            String key = iterator.next().toString();
            String value = map.get(key).toString();
        }
    }

    public static void printFromWeb2Console(String mensaje) {
        System.out.println(mensaje);
    }

    public static String getRoudDobule2Int(String valor) {
        double numero = Math.rint(Double.parseDouble(valor));
        int nuevoNumero = 0;
        nuevoNumero = (int) numero;
        return new Integer(nuevoNumero).toString();
    }

    public static int getMaxValue(HashSet numeros) {
        int max = 0;
        max = (int) Collections.max(numeros);
        return max;
    }

    //elimina los espacios en blanco que se encuentran en una cadena
    public static String middle(String word) {
        String result = "";
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != ' ') {
                result += word.charAt(i);
            }
        }
        return result;
    }

    public static String trunc(String cadena, int index) {
        if (index < cadena.length()) {
            cadena = cadena.substring(0, index);
        }
        return cadena;
    }

    public static Double formatDoubleTwoDecimals(Double number) {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(number));
    }

    public static String sformatDoubleTwoDecimals(Double number) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(number);
    }

    public static String truncString(String s, int maxLenght) {
        if (s.length() > maxLenght) {
            s = s.substring(0, maxLenght);
        }
        return s;
    }

    public static int isTipoDatoChar(String s) {
        int td = -1;
        if (Character.isDigit(s.charAt(0))) {
            //System.out.println("Digito");
            td = 1;
        } else if (Character.isLetter(s.charAt(0))) {
            //System.out.println("letra");
            td = 2;
        } else if (!Character.isLetterOrDigit(s.charAt(0))) {
            //System.out.println("Simbolo");//el espacio es considerado como simbolo
            td = 0;
        } else if (Character.isAlphabetic(s.charAt(0))) {
            //System.out.println("Alfbetico"); // caracter especial como los acentos, las yerisis, los circunflejos
            td = 3;
        }
        return td;
    }
    
    //remueve vuelta de carro  (\r) y enter (\n) 
    public static String removeEnter(String cadena) {
        cadena = cadena.replace(System.getProperty("line.separator"), "");
        cadena = cadena.replaceAll("\n", "");
        cadena = cadena.replaceAll("\t", "");
        cadena = cadena.replaceAll("\\n", "");
        cadena = cadena.replaceAll("\\t", "");
        cadena = cadena.replaceAll("\r", "");
        cadena = cadena.replaceAll("\\r", "");
        cadena = cadena.replaceAll("\r\n", "");
        cadena = cadena.replaceAll("\\r\\n", "");
        return cadena;
    }
}
