package jspread.core.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author S2 Sistemas
 */
public final class UTime {

    //Esta es la version de esta clase
    private static final String version = "V0.22";

    public static String getVersion() {
        return version;
    }
    public static final int DOMINGO = 1;
    public static final int LUNES = 2;
    public static final int MARTES = 3;
    public static final int MIERCOLES = 4;
    public static final int JUEVES = 5;
    public static final int VIERNES = 6;
    public static final int SABADO = 7;

    /**
     * <p> Metodo - Obtiene el dia("dd") de un "Calendar"
     *
     * <p>
     *
     * @param cal - Calendar
     * @return String - "dd" (Sin comillas)
     */
    public static String calcularDiaDD(Calendar cal) {
        String dia;
        dia = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
        if (dia.length() == 1) {
            dia = "0" + dia;
        }
        return dia;
    }

    /**
     * <p> Metodo - Obtiene el dia("d") de un "Calendar"
     *
     * <p>
     *
     * @param cal - Calendar
     * @return String - "d" (Sin comillas)
     */
    public static String calcularDiaD(Calendar cal) {
        return Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * <p> Metodo - Obtiene el dia("dd") de un "Calendar"
     *
     * <p>
     *
     * @param cal - Calendar
     * @return String - "dd" (Sin comillas)
     */
    public static int calcularDiaDeLaSemana(Calendar cal) {
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * <p> Metodo - Obtiene el dia("dd") de un "Calendar"
     *
     * <p>
     *
     * @param cal - Calendar
     * @return String - "dd" (Sin comillas)
     */
    public static String calcularDiaDeLaSemanaString(Calendar cal) {
        String dia = null;
        switch (calcularDiaDeLaSemana(cal)) {
            case 1:
                //dia = "Domingo";
                return "Domingo";
            //break;
            case 2:
                //dia = "Lunes";
                return "Lunes";
            //break;
            case 3:
                //dia = "Martes";
                return "Martes";
            //break;
            case 4:
                //dia = "Miercoles";
                return "Miercoles";
            //break;
            case 5:
                //dia = "Jueves";
                return "Jueves";
            //break;
            case 6:
                //dia = "Viernes";
                return "Viernes";
            //break;
            case 7:
                //dia = "Sabado";
                return "Sabado";
            //break;
        }
        return dia;
    }

    /**
     * <p> Metodo - Obtiene el dia("d") de un "Calendar"
     *
     * <p>
     *
     * @param cal - Calendar
     * @return String - "d" (Sin comillas) EJ: Lunes = L, Martes=MA Miercoles =
     * MI
     */
    public static String calcularDiaDeLaSemanaCortoString(Calendar cal) {
        String dia = null;
        switch (calcularDiaDeLaSemana(cal)) {
            case 1:
                //dia = "Domingo";
                return "D";
            //break;
            case 2:
                //dia = "Lunes";
                return "L";
            //break;
            case 3:
                //dia = "Martes";
                return "MA";
            //break;
            case 4:
                //dia = "Miercoles";
                return "MI";
            //break;
            case 5:
                //dia = "Jueves";
                return "J";
            //break;
            case 6:
                //dia = "Viernes";
                return "V";
            //break;
            case 7:
                //dia = "Sabado";
                return "S";
            //break;
        }
        return dia;
    }

    /**
     * <p> Metodo - Obtiene el mes("mm") de un "Calendar"
     *
     * <p>
     *
     * @param cal - Calendar
     * @return String - "mm" (Sin comillas)
     */
    public static String calcularMesMM(Calendar cal) {
        String mes;
        mes = Integer.toString(cal.get(Calendar.MONTH) + 1);
        if (mes.length() == 1) {
            mes = "0" + mes;
        }
        return mes;
    }

    /**
     * <p> Metodo - Obtiene el anio("aa") de un "Calendar"
     *
     * <p>
     *
     * @param cal - Calendar
     * @return String - "aa" (Sin comillas)
     */
    public static String calcularAnioAA(Calendar cal) {
        String anio;
        anio = Integer.toString(cal.get(Calendar.YEAR));
        return anio.substring(2);
    }

    /**
     * <p> Metodo - Obtiene el mes("mm") de un "Calendar"
     *
     * <p>
     *
     * @param cal - Calendar
     * @return String - "mm" (Sin comillas)
     */
    public static String calcularMesM(Calendar cal) {
        return Integer.toString(cal.get(Calendar.MONTH) + 1);
    }

    /**
     * <p> Metodo - Compara entre 2 fechas para saber cual es la fecha mayor
     *
     * <p>
     *
     * @param cal1 - Calendar
     * @param cal2 - Calendar
     * @return boolean - true(si cal1>=cal2) false(cal2>cal1)
     */
    public static boolean cualFechaEsMayorOIgual(Calendar cal1, Calendar cal2) {
        boolean bool;
        if (cal1.getTimeInMillis() >= cal2.getTimeInMillis()) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    // devuelve true si la primera fecha es menor o igual a la segunda, false si la segunda es mayor
    public static boolean cualFechaEsMayor(Calendar cal1, Calendar cal2) {
        boolean bool;
        if (cal1.getTimeInMillis() <= cal2.getTimeInMillis()) {
            bool = true;
        } else {
            bool = false;
        }
        return bool;
    }

    /**
     * <p> Metodo - Genera el formato "aaaa-mm-dd" apartir de un "Calendar"
     *
     * <p>
     *
     * @param cal - Calendar
     * @return String - "aaaa-mm-dd" (Sin comillas)
     */
    public static String calendar2SQLDateFormat(Calendar cal) {
        //String date = "0000-00-00";
        //cal.get(Calendar.DATE);
        //date = cal.get(Calendar.YEAR) + "-" + calcularMes(cal) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        //return date;
        return cal.get(Calendar.YEAR) + "-" + calcularMesMM(cal) + "-" + calcularDiaDD(cal);
    }

    /**
     * <p> Metodo - Genera el formato "aaaa-m-d" apartir de un "Calendar"
     *
     * <p>
     *
     * @param cal - Calendar
     * @return String - "aaaa-m-d" (Sin comillas)
     */
    public static String calendar2aaaamd(Calendar cal) {
        //String date = "0000-00-00";
        //cal.get(Calendar.DATE);
        //date = cal.get(Calendar.YEAR) + "-" + calcularMes(cal) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        //return date;
        return cal.get(Calendar.YEAR) + "-" + calcularMesM(cal) + "-" + calcularDiaD(cal);
    }

    /**
     * <p> Metodo - Genera un "Calendar" apartir del formato "aaaa-mm-dd"
     *
     * <p>
     *
     * @param String - "aaaa-mm-dd" (Sin comillas)
     * @return cal - Calendar
     */
    public static Calendar aaaa_mm_dd2calendar(String aaaa_mm_dd) {
        //String date = "0000-00-00";
        //cal.get(Calendar.DATE);
        //date = cal.get(Calendar.YEAR) + "-" + calcularMes(cal) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        //return date;

        if (aaaa_mm_dd.length() == 10
                && aaaa_mm_dd.charAt(4) == '-'
                && aaaa_mm_dd.charAt(7) == '-') {
            String anio = "" + aaaa_mm_dd.charAt(0) + aaaa_mm_dd.charAt(1) + aaaa_mm_dd.charAt(2) + aaaa_mm_dd.charAt(3);
            String mes = "" + aaaa_mm_dd.charAt(5) + aaaa_mm_dd.charAt(6);
            String dia = "" + aaaa_mm_dd.charAt(8) + aaaa_mm_dd.charAt(9);

            int anioI = Integer.parseInt(anio);
            int mesI = Integer.parseInt(mes);
            int diaI = Integer.parseInt(dia);

            if (diaI > 0
                    && diaI <= 31) {
                if (mesI > 0
                        && mesI <= 12) {
                    if (anioI > 2005
                            && anioI <= 2100) {
                        Calendar c = new GregorianCalendar();
                        c.set(anioI, mesI - 1, diaI);
                        return c;
                    }
                }
            }
        }
        return null;
    }

    /**
     * <p> Metodo - Convierte el formato "m/d/aaaa" a "aaaa-mm-dd"
     *
     * <p>
     *
     * @param String - fecha m/d/aaaa
     * @return String - "aaaa-mm-dd" (Sin comillas)
     */
    public static String mdaaaa2aaaa_mm_dd(String fechaAConvertir) {
        String[] aux = fechaAConvertir.split("/");

        String anio = "0000";
        String dia = "00";
        String mes = "00";

        anio = aux[2];
        mes = aux[0];
        dia = aux[1];

        if (mes.length() == 1) {
            mes = "0" + mes;
        }

        if (dia.length() == 1) {
            dia = "0" + dia;
        }
        return anio + "-" + mes + "-" + dia;
    }

    /**
     * <p> Metodo - Convierte el formato "dd/mm/aaaa" a "aaaa-mm-dd"
     *
     * <p>
     *
     * @param String - fecha dd/mm/aaaa
     * @return String - "aaaa-mm-dd" (Sin comillas)
     */
    
    public static String getDateString(Calendar cal) {
        //String date = "0000-00-00";
        //cal.get(Calendar.DATE);
        //date = cal.get(Calendar.YEAR) + "-" + calcularMes(cal) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        //return date;
        return cal.get(Calendar.YEAR) + "" + cal.get(Calendar.MONTH)
                + cal.get(Calendar.DAY_OF_MONTH) + cal.get(Calendar.HOUR) + cal.get(Calendar.MINUTE)
                + cal.get(Calendar.SECOND);
    }

    public static String ddmmaaaa2aaaa_mm_dd(String fechaAConvertir) {
        String[] aux = fechaAConvertir.split("/");

        String anio = "0000";
        String dia = "00";
        String mes = "00";

        anio = aux[2];
        mes = aux[1];
        dia = aux[0];

        if (mes.length() == 1) {
            mes = "0" + mes;
        }

        if (dia.length() == 1) {
            dia = "0" + dia;
        }
        return anio + "-" + mes + "-" + dia;
    }

    /**
     * <p> Metodo - Convierte el formato "aaaa-mm-dd" a "dd/mm/aaaa"
     *
     * <p>
     *
     * @param String - fecha dd/mm/aaaa
     * @return String - "aaaa-mm-dd" (Sin comillas)
     */
    public static String aaaa_mm_dd2ddmmaaaa(String fechaAConvertir) {
        String[] aux = fechaAConvertir.split("-");

        String anio = "0000";
        String dia = "00";
        String mes = "00";

        anio = aux[2];
        mes = aux[1];
        dia = aux[0];

        if (mes.length() == 1) {
            mes = "0" + mes;
        }

        if (dia.length() == 1) {
            dia = "0" + dia;
        }
        return anio + "/" + mes + "/" + dia;
    }

    /**
     * <p> Metodo - Convierte el formato "d/m/aaaa" a "aaaa-mm-dd"
     *
     * <p>
     *
     * @param String - fecha d/m/aaaa
     * @return String - "aaaa-mm-dd" (Sin comillas)
     */
    public static String dmaaaa2aaaa_mm_dd(String fechaAConvertir) {
        String[] aux = fechaAConvertir.split("/");

        String anio = "0000";
        String dia = "00";
        String mes = "00";

        anio = aux[2];
        mes = aux[1];
        dia = aux[0];

        if (mes.length() == 1) {
            mes = "0" + mes;
        }

        if (dia.length() == 1) {
            dia = "0" + dia;
        }

        if (anio.length() != 4) {
            throw new UnsupportedOperationException("El año no esta en formato 'aaaa'");
        } else if (mes.length() != 2) {
            throw new UnsupportedOperationException("El año no esta en formato 'mm'");
        } else if (mes.length() != 2) {
            throw new UnsupportedOperationException("El año no esta en formato 'dd'");
        }

        return anio + "-" + mes + "-" + dia;
    }

    /**
     * <p> Metodo - Valida si la fecha dada es un formato de MySQL
     *
     * <p>
     *
     * @param String - fechaAValidar sugerido aaaa-mm-dd
     * @return true - si es un formato "aaaa-mm-dd" (Sin comillas)
     */
    public static boolean isMySQLFormat(String fechaAValidar) {
        boolean esValida = false;

        if (fechaAValidar.length() == 10
                && fechaAValidar.charAt(4) == '-'
                && fechaAValidar.charAt(7) == '-') {
            String anio = "" + fechaAValidar.charAt(0) + fechaAValidar.charAt(1) + fechaAValidar.charAt(2) + fechaAValidar.charAt(3);
            String mes = "" + fechaAValidar.charAt(5) + fechaAValidar.charAt(6);
            String dia = "" + fechaAValidar.charAt(8) + fechaAValidar.charAt(9);

            int anioI = Integer.parseInt(anio);
            int mesI = Integer.parseInt(mes);
            int diaI = Integer.parseInt(dia);

            if (diaI > 0
                    && diaI <= 31) {
                if (mesI > 0
                        && mesI <= 12) {
                    if (anioI > 2005
                            && anioI <= 2100) {
                        esValida = true;
                    }
                }
            }
        }

//        String[] aux = fechaAConvertir.split("/");
//
//        
//
//        anio = aux[2];
//        mes = aux[1];
//        dia = aux[0];
//
//        if (mes.length() == 1) {
//            mes = "0" + mes;
//        }
//
//        if (dia.length() == 1) {
//            dia = "0" + dia;
//        }
//
//        if (anio.length() != 4) {
//            throw new UnsupportedOperationException("El año no esta en formato 'aaaa'");
//        } else if (mes.length() != 2) {
//            throw new UnsupportedOperationException("El año no esta en formato 'mm'");
//        } else if (mes.length() != 2) {
//            throw new UnsupportedOperationException("El año no esta en formato 'dd'");
//        }
//
//        return anio + "-" + mes + "-" + dia;
        return esValida;
    }

    /**
     * <p> Metodo - Transforma un "Date (Java Util)" a un "Calendar"
     *
     * <p>
     *
     * @param Date - date
     * @return Calendar
     */
    public static Calendar dateUtil2Calendar(java.util.Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        return c;
    }

    /**
     * <p> Metodo - Transforma un "Date (Java Util)" a un "Calendar"
     *
     * <p>
     *
     * @param Date - date
     * @return Calendar
     */
    public static Calendar dateSQLl2Calendar(java.sql.Date date) {
        Calendar c = new GregorianCalendar();
        c.setTime(date);
        return c;
    }

    public static LinkedList getSimpleTimeStamp() {
        LinkedList timeStamp = new LinkedList();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd--HH:mm:ss");
        Date date = new Date();
        String[] aux = dateFormat.format(date).split("--");
        timeStamp.add(aux[0]);
        timeStamp.add(aux[1]);
        //System.out.println(dateFormat.format(date));
        return timeStamp;
    }

    public static String getSimpleTimeStampString() {
        String timeStamp = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd--HH-mm-ss");
        Date date = new Date();
        String[] aux = dateFormat.format(date).split("--");
        timeStamp = timeStamp + aux[0];
        timeStamp = timeStamp + "--";
        timeStamp = timeStamp + aux[1];
        return timeStamp;
    }

    public static Calendar sumaDiasMySQLFormat(String fecha, int diasASumar) {
        Calendar cal = UTime.aaaa_mm_dd2calendar(fecha);
        //86400000 es la cantidad de milisegundos que tiene un dia
        long nuevaFecha = cal.getTimeInMillis() + (86400000L * diasASumar);
        cal.setTimeInMillis(nuevaFecha);
        return cal;
    }

    public static String mili2SQLDateFormat(long mili) {
        Calendar cal = null;
        cal.setTimeInMillis(mili);
        return UTime.calendar2SQLDateFormat(cal);
    }

    public static long getTimeMilis() {
        Calendar c = new GregorianCalendar();
        return c.getTimeInMillis();
    }

    public static boolean validaFecha(String fecha) {

        if (fecha == null) {
            return false;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");


        if (fecha.trim().length() != dateFormat.toPattern().length()) {
            return false;
        }

        dateFormat.setLenient(false);

        try {
            dateFormat.parse(fecha.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    /**
     * Calcula los años transcurridos dada una fecha y hasta la fecha actual
     *
     * @return byte con el calculo.
     */
    public static byte calculaAnios(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-mm-dd");
        byte anios = -1;
        Date tiempo = null;
        try {
            tiempo = formato.parse(fecha);
            GregorianCalendar fechaMenor = new GregorianCalendar();
            fechaMenor.setTime(tiempo);
            GregorianCalendar fechaMayor = new GregorianCalendar();
            fechaMayor.setTime(Calendar.getInstance().getTime());
            anios = (byte) (fechaMayor.get(Calendar.YEAR) - fechaMenor.get(Calendar.YEAR));
        } catch (ParseException ex) {
            Logger.getLogger(UTime.class.getName()).log(Level.SEVERE, null, ex);
        }
        return anios;

    }

    public static int calculaAnios2(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-mm-dd");
        int anios = -1;
        Date tiempo = null;
        try {
            tiempo = formato.parse(fecha);
            GregorianCalendar fechaMenor = new GregorianCalendar();
            fechaMenor.setTime(tiempo);
            GregorianCalendar fechaMayor = new GregorianCalendar();
            fechaMayor.setTime(Calendar.getInstance().getTime());
            anios = (Integer) (fechaMayor.get(Calendar.YEAR) - fechaMenor.get(Calendar.YEAR));
        } catch (ParseException ex) {
            Logger.getLogger(UTime.class.getName()).log(Level.SEVERE, null, ex);
        }
        return anios;

    }

    /**
     * obtiene el año como entero, dada una fecha en formato aaaa-mm-dd
     *
     * @return int con el calculo.
     */
    public static int getAnio(String fecha) {
        int anio = 0;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-mm-dd");
        Date fecha2 = null;
        try {
            fecha2 = formato.parse(fecha);
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(fecha2);
            anio = (Integer) calendar.get(Calendar.YEAR);

        } catch (ParseException ex) {
            Logger.getLogger(UTime.class.getName()).log(Level.SEVERE, null, ex);
        }
        return anio;
    }

    public static Calendar string2Calendar(String date) {
        Calendar cal = null;
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = (Date) format.parse(date);
            cal = Calendar.getInstance();
            cal.setTime(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(UTime.class.getName()).log(Level.SEVERE, null, ex);
        }

        return cal;
    }
    /*
     * Devuelve true si fecha esta dentro del periodo establecido por fechaInicio y fechaFin
     */

    public static boolean dateWithinPeriod(String fecha, String fechaInicio, String fechaFin) {
        boolean respuesta = false;
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaTest = dateFormat.parse(fecha);
            Date limiteInferior = dateFormat.parse(fechaInicio);
            Date limiteSuperior = dateFormat.parse(fechaFin);
            if (fechaTest.compareTo(limiteInferior) >= 0
                    && fechaTest.compareTo(limiteSuperior) <= 0) {
                respuesta = true;
            }
        } catch (ParseException ex) {
            Logger.getLogger(UTime.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public static boolean dateWithinPeriod(Date fecha, Date fechaInicio, Date fechaFin) {
        boolean respuesta = false;

        if (fecha.compareTo(fechaInicio) >= 0
                && fecha.compareTo(fechaFin) < 0) {
            respuesta = true;
        }
        return respuesta;
    }

    public static boolean HourWithinPeriod(String hora, String horaInicio, String horaFin) {
        boolean respuesta = false;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date fechaTest = dateFormat.parse(hora);
            Date limiteInferior = dateFormat.parse(horaInicio);
            Date limiteSuperior = dateFormat.parse(horaFin);
            if (fechaTest.compareTo(limiteInferior) >= 0
                    && fechaTest.compareTo(limiteSuperior) <= 0) {
                respuesta = true;
            }
        } catch (ParseException ex) {
            Logger.getLogger(UTime.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    //devuelve true si dada una hora, esta se encuentra dentro de un rango permitiendo tocar el limite superior
    public static boolean HourWithinUpClosePeriod(String hora, String horaInicio, String horaFin) {
        boolean respuesta = false;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date fechaTest = dateFormat.parse(hora);
            Date limiteInferior = dateFormat.parse(horaInicio);
            Date limiteSuperior = dateFormat.parse(horaFin);
            if (fechaTest.compareTo(limiteInferior) >= 0
                    && fechaTest.compareTo(limiteSuperior) < 0) {
                respuesta = true;
            }
        } catch (ParseException ex) {
            Logger.getLogger(UTime.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }
    //devuelve true si dada una hora, esta se encuentra dentro de un rango permitiendo tocar el limite inferior

    public static boolean HourWithinLowerClosePeriod(String hora, String horaInicio, String horaFin) {
        boolean respuesta = false;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date fechaTest = dateFormat.parse(hora);
            Date limiteInferior = dateFormat.parse(horaInicio);
            Date limiteSuperior = dateFormat.parse(horaFin);
            if (fechaTest.compareTo(limiteInferior) > 0
                    && fechaTest.compareTo(limiteSuperior) <= 0) {
                respuesta = true;
            }
        } catch (ParseException ex) {
            Logger.getLogger(UTime.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuesta;
    }

    public static boolean isTimeOverLap(String hraInicio1, String hraFin1, String hraInicio2, String hraFin2) {
        boolean respuesta = false;
        if (UTime.HourWithinUpClosePeriod(hraInicio2, hraInicio1, hraFin1)) {
            respuesta = true;
        } else if (UTime.HourWithinPeriod(hraFin2, hraInicio1, hraFin1)) {
            respuesta = true;
        } else if (UTime.HourWithinPeriod(hraInicio1, hraInicio2, hraFin2)) {
            respuesta = true;
        } else if (UTime.HourWithinLowerClosePeriod(hraFin1, hraInicio2, hraFin2)) {
            respuesta = true;
        }
        return respuesta;
    }

    public static Date string2Date(String strfecha) {
        Date fecha = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            fecha = dateFormat.parse(strfecha);
        } catch (ParseException ex) {
            Logger.getLogger(UTime.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fecha;
    }

    public static Calendar string2Calendar2(String strfecha) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        try {
            fecha = dateFormat.parse(strfecha);
        } catch (ParseException ex) {
            Logger.getLogger(UTime.class.getName()).log(Level.SEVERE, null, ex);
        }
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        return c;
    }

    public static String getNameOfMonth(int mes) {
        String nombreMes = "unknow";
        if (mes > 0 && mes <= 12) {
            switch (mes) {
                case 1:
                    nombreMes = "Enero";
                    break;
                case 2:
                    nombreMes = "Febrero";
                    break;
                case 3:
                    nombreMes = "Marzo";
                    break;
                case 4:
                    nombreMes = "Abril";
                    break;
                case 5:
                    nombreMes = "Mayo";
                    break;
                case 6:
                    nombreMes = "Junio";
                    break;
                case 7:
                    nombreMes = "Julio";
                    break;
                case 8:
                    nombreMes = "Agosto";
                    break;
                case 9:
                    nombreMes = "Septiembre";
                    break;
                case 10:
                    nombreMes = "Octubre";
                    break;
                case 11:
                    nombreMes = "Noviembre";
                    break;
                case 12:
                    nombreMes = "Diciembre";
                    break;
            }
        }
        return nombreMes;
    }

    public static String getNombreMes(int mes) {
        String nombreMes = "unknow";

        switch (mes) {
            case 0:
                nombreMes = "Enero";
                break;
            case 1:
                nombreMes = "Febrero";
                break;
            case 2:
                nombreMes = "Marzo";
                break;
            case 3:
                nombreMes = "Abril";
                break;
            case 4:
                nombreMes = "Mayo";
                break;
            case 5:
                nombreMes = "Junio";
                break;
            case 6:
                nombreMes = "Julio";
                break;
            case 7:
                nombreMes = "Agosto";
                break;
            case 8:
                nombreMes = "Septiembre";
                break;
            case 9:
                nombreMes = "Octubre";
                break;
            case 10:
                nombreMes = "Noviembre";
                break;
            case 11:
                nombreMes = "Diciembre";
                break;
        }

        return nombreMes;
    }

    public static Date stringDateTime(String strfecha) {
        Date fecha = null;//*** Formato de 12Hrs ***//
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            fecha = dateFormat.parse(strfecha);
        } catch (ParseException ex) {
            Logger.getLogger(UTime.class.getName()).log(Level.SEVERE, null, ex);
        }

        return fecha;
    }

    public static Date stringDateTime24Hr(String strfecha) {
        Date fecha = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            fecha = dateFormat.parse(strfecha);
        } catch (ParseException ex) {
            Logger.getLogger(UTime.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fecha;
    }

    public static String getDateTime24Hr() {
        Date date = new Date();
        String fechaFormat = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            fechaFormat = dateFormat.format(date).toString();
        } catch (Exception ex) {
            Logger.getLogger(UTime.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechaFormat;
    }

    public static String calculaHoraClase(String fechaHora, int minutes) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar calendarDate = Calendar.getInstance();
        Date fecha = null;
        String horas = "";

        try {
            fecha = UTime.stringDateTime24Hr(fechaHora);
            calendarDate.setTime(fecha);
            calendarDate.add(Calendar.MINUTE, minutes);
            horas = sdf.format(calendarDate.getTime());
        } catch (Exception ex) {
            Logger.getLogger(UTime.class.getName()).log(Level.SEVERE, null, ex);
        }

        return horas;
    }

    public static int convertDayOfWeekToDayDEO(int dayOfWeek) {
        int dia = dayOfWeek;
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                dia = 7;
                break;
            case Calendar.MONDAY:
                dia = 1;
                break;
            case Calendar.TUESDAY:
                dia = 2;
                break;
            case Calendar.WEDNESDAY:
                dia = 3;
                break;
            case Calendar.THURSDAY:
                dia = 4;
                break;
            case Calendar.FRIDAY:
                dia = 5;
                break;
            case Calendar.SATURDAY:
                dia = 6;
                break;
        }
        return dia;
    }
    //obtiene la diferencia entre dos horas con formato HH:mm en minutos 

    public static int restarHoras(String horaInicio, String horaFin) {
        int resta = 0;
        String[] strHoraInicio = horaInicio.split(":");
        String[] strHoraFin = horaFin.split(":");
        resta = Integer.parseInt(strHoraFin[0]) - Integer.parseInt(strHoraInicio[0]);
        resta = (resta * 60) + (Integer.parseInt(strHoraFin[1]) - Integer.parseInt(strHoraInicio[1]));
        return resta;
    }

    public static String formatCalendar2dMMaa(Date fecha) {
        String formatedDate = "";
        DateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        formatedDate = formater.format(fecha);
        return formatedDate;
    }
    //valida que dado un rango de horas( horoaInicio y horaFin), el rango sea correcto

    public static boolean validaPeriodoHoras(String horaInicio, String horaFin) {
        boolean valido = false;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            Date horaI = dateFormat.parse(horaInicio);
            Date horaF = dateFormat.parse(horaFin);
            if (horaI.compareTo(horaF) < 0) {
                valido = true;
            }
        } catch (ParseException ex) {
            Logger.getLogger(UTime.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valido;
    }
    //devuelve true si dada una fecha inicio es menor o igual que una fecha fin. Ambas tipo string

    public static boolean comparaStringFechas(String fechaInicio, String fechaFin) {
        boolean valido = false;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaI = dateFormat.parse(fechaInicio);
            Date fechaF = dateFormat.parse(fechaFin);
            if (fechaI.compareTo(fechaF) <= 0) {
                valido = true;
            }
        } catch (ParseException ex) {
            Logger.getLogger(UTime.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valido;
    }

    public static boolean isValidHour(String hora) {
        boolean valido = false;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
            dateFormat.parse(hora);
            valido = true;
        } catch (ParseException ex) {
            valido = false;
        }
        return valido;
    }

    public static String getDateLong(Calendar cal) {
        String date = "";
        if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
            date = " al primer día del mes de "
                    + UTime.getNameOfMonth(cal.get(Calendar.MONTH) + 1) + " del año " + cal.get(Calendar.YEAR) + ".";            
        } else {
            date = " a los " + cal.get(Calendar.DAY_OF_MONTH) + " días del mes de "
                    + UTime.getNameOfMonth(cal.get(Calendar.MONTH) + 1) + " del año " + cal.get(Calendar.YEAR) + ".";
        }
        return date;
    }

    public static String getStrDatePeriodo(String fecha, String periodoSemestral, int dias) {
        String strdate = "";
        String tempdate = "";
        Date date = null;
        Calendar cal = new GregorianCalendar();
        int mes = 0;

        if (periodoSemestral.equalsIgnoreCase("semestral")) {
            mes = Integer.parseInt(fecha.trim().substring(5, 7));
            strdate = fecha.trim().substring(8, 10) + " de " + UTime.getNameOfMonth(mes) + " del año " + fecha.trim().substring(0, 4);
        } else {
            date = UTime.string2Date(fecha);
            cal.setTimeInMillis(date.getTime());
            cal.add(Calendar.DATE, dias);
            tempdate = UTime.formatCalendar(cal);
            mes = Integer.parseInt(tempdate.trim().substring(5, 7));
            strdate = tempdate.trim().substring(8, 10) + " de " + UTime.getNameOfMonth(mes) + " del año " + tempdate.trim().substring(0, 4);
        }
        return strdate;
    }

    public static String formatCalendar(Calendar cal) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha = null;
        String strfecha = "";
        try {
            fecha = cal.getTime();
            strfecha = dateFormat.format(fecha);
        } catch (Exception Ex) {
            Logger.getLogger(UTime.class.getName()).log(Level.SEVERE, null, Ex);
        }
        return strfecha;
    }
    public static String getHourHHMMSS(Calendar cal){
        String hour="00:00:00";
        hour=cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
        return hour;
    }
    public static String getStringDateLongFormat(Long milis) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(milis);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd--HH:mm:ss");
        return dateFormat.format(cal.getTime());
    }

    public static String getDiferenciaEnHoras(Long milisFechaPosterior, Long milisFechaAnterior) {
        Long diferencia = milisFechaPosterior - milisFechaAnterior;
        Long hora = diferencia / 3600000;
        Long restohora = diferencia % 3600000;
        Long minuto = restohora / 60000;
        Long restominuto = restohora % 60000;
        Long segundo = restominuto / 1000;
        Long restosegundo = segundo % 1000;
//
//        int seconds = (int) (diferencia / 1000) % 60;
//        int minutes = (int) ((diferencia / (1000 * 60)) % 60);
//        int hours = (int) ((diferencia / (1000 * 60 * 60)) % 24);

        return "" + UTime.fotmatToCC(hora.toString(), "0") + ":" + UTime.fotmatToCC(minuto.toString(), "0") + ":" + UTime.fotmatToCC(segundo.toString(), "0");
//        return  hours+":"+minutes+":"+seconds; 
//diferencia.toString();
    }

    public static Long getDiferenciaEnSegundos(Long milisFechaPosterior, Long milisFechaAnterior) {
        Long diferencia = milisFechaPosterior - milisFechaAnterior;
        diferencia = diferencia / 1000;
        return diferencia;
    }

    public static int getDiferenciaEnMinutos(Long milisFechaPosterior, Long milisFechaAnterior) {
        Long diferencia = milisFechaPosterior - milisFechaAnterior;
        int minutes = (int) ((diferencia / (1000 * 60)) % 60);
        return minutes;
    }

    public static String milisToHours(Long milis) {
        return UTime.calcMilisToHours(milis);
    }

    public static String milisToHours(int milis) {
        return UTime.calcMilisToHours(new Long(milis));
    }

    public static String calcMilisToHours(Long milis) {
        Long hora = milis / 3600000;
        Long restohora = milis % 3600000;
        Long minuto = restohora / 60000;
        Long restominuto = restohora % 60000;
        Long segundo = restominuto / 1000;
        Long restosegundo = segundo % 1000;
        // HACE LO MISMO SI FUNCIONA
//
//        int seconds = (int) (diferencia / 1000) % 60;
//        int minutes = (int) ((diferencia / (1000 * 60)) % 60);
//        int hours = (int) ((diferencia / (1000 * 60 * 60)) % 24);
        return "" + UTime.fotmatToCC(hora.toString(), "0") + ":" + UTime.fotmatToCC(minuto.toString(), "0") + ":" + UTime.fotmatToCC(segundo.toString(), "0");
    }

    public static String fotmatToCC(String aRellenar, String caracterFill) {
        if (aRellenar.length() <= 1) {
            aRellenar = caracterFill + aRellenar;
        }
        return aRellenar;

    }
}
