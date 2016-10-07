/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemSettings;

import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import jspread.core.db.JSpreadConnectionPool;
import jspread.core.util.PageParameters;

/**
 *
 * @author emmanuel
 */
public final class SystemSettings {

    private static boolean isBurning = false;

    public static void ignition() throws Exception {
        if (!isBurning) {
            try {
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //parametros de clases para el spread
                //Falta agregar codigo. Se necesita verificar si Sirve en la aplicacion.
                //posiblemente se pueda omitir.

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //parametros de paginas
                PageParameters.getSingleInstance();
                PageParameters.addParameter("appName", "SPIC");
                PageParameters.addParameter("controllerName", "controller");
                PageParameters.addParameter("mainContext", "/" + PageParameters.getParameter("appName"));
                PageParameters.addParameter("charset", "utf-8");
                System.setProperty("file.encoding", "UTF8");//-Dfile.encoding=UTF8 en la jvm
                PageParameters.addParameter("servletSetContentType", "text/html;charset=UTF-8");
                PageParameters.addParameter("Content-Language", "es-mx");
                PageParameters.addParameter("faviconType", "image/x-icon"); // image/png  image/jpg
                PageParameters.addParameter("exitmessageask", "Estas a punto de salir de la pagina deseas hacerlo?");
                PageParameters.addParameter("cssType", "text/css"); // text/css
                PageParameters.addParameter("encodeParameters", "true");
                PageParameters.addParameter("encodeParametersSemilla", "pipiol");
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //parametros de componentes.
                //pageComoponents URL
                PageParameters.addParameter("pageComponents", "/gui/pageComponents");
                //globalLibs URL
                PageParameters.addParameter("globalLibs", PageParameters.getParameter("pageComponents") + "/globalLibsInstitucional.jsp"); //checar
                //header URL
                PageParameters.addParameter("header", PageParameters.getParameter("pageComponents") + "/globalLibsInstitucional.jsp"); //checar
                //logo URL
                PageParameters.addParameter("logo", PageParameters.getParameter("pageComponents") + "/logoInstitucional.jsp");
                //barMenu URL
                PageParameters.addParameter("barMenu", PageParameters.getParameter("pageComponents") + "/barMenu.jsp"); 
                //footer URL
                PageParameters.addParameter("footer", PageParameters.getParameter("pageComponents") + "/footerInstitucional.jsp");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //Paginas del Sistema.
//            //MainController URL
                PageParameters.addParameter("mainController", PageParameters.getParameter("mainContext") + "/" + PageParameters.getParameter("controllerName"));
//            //interface URL

                PageParameters.addParameter("gui", "/gui");
//            PageParameters.addParameter("scanedFiles", PageParameters.getParameter("mainContext") + "/scanedFiles");
//            //MainMenu URL
            PageParameters.addParameter("mainMenu", PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/mainMenu.jsp");
//            //MainMenu ServLet
            PageParameters.addParameter("mainMenuServLet", PageParameters.getParameter("gui") + "/mainMenu.jsp");
//            //acceso ilegal URL
//            PageParameters.addParameter("illegalAttempt", PageParameters.getParameter("mainContext") + "/JSpread/JSPTemplates/BlankSpreadTemplateUserIllegalAttempt.jsp");
//            //error URL
//            //PageParameters.addParameter("errorURL", PageParameters.getParameter("mainContext") + "http://www.google.com/ncr");
                PageParameters.addParameter("errorURL", "http://cecytem.edomex.gob.mx/");
            //LogInPage URL
            PageParameters.addParameter("LogInPage", PageParameters.getParameter("gui") + "/login.jsp");
//            //index URL
//            PageParameters.addParameter("index", PageParameters.getParameter("mainContext") + "/index.jsp");
//            //AJAXFunctions URL
                PageParameters.addParameter("ajaxFuntions", "/ajaxFuntions");
//            //msgUtil URL
                PageParameters.addParameter("msgUtil", PageParameters.getParameter("ajaxFuntions") + "/msgUtil");
//            //reports URL
//            PageParameters.addParameter("reports", PageParameters.getParameter("gui") + "/reports");
//            //prints URL
//            //los reportes de impresion estan basados en el navegador firefox v23, sin margenes, en hoja carta, impresoras de inyeccion de tinta y sin la opcion de imprimir fondo
//            PageParameters.addParameter("prints", PageParameters.getParameter("gui") + "/prints");
//            //excel reports URL
//            PageParameters.addParameter("excelReports", PageParameters.getParameter("reports") + "/aExcel");
//            //Pagina en mantenimiento
                PageParameters.addParameter("SiteOnMaintenance", "false");
                PageParameters.addParameter("SiteOnMaintenanceURL", PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui") + "/SiteOnMaintenance.jsp");
                PageParameters.addParameter("msgOnMaintenance", "Sitio en mantenimiento, intente nuevamente, disculpe las molestias.");
//            PageParameters.addParameter("emptyField", "Este campo no puede estar vacio");
//            //Notificacion
//            PageParameters.addParameter("comunicado", "");
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //recursos del Sistema.
                //resources rsc URL
                PageParameters.addParameter("rsc", PageParameters.getParameter("mainContext") + "/rsc");
                //jq resources rsc URL 
                PageParameters.addParameter("jqRsc", PageParameters.getParameter("rsc") + "/jq");
                //css resources rsc URL
                PageParameters.addParameter("cssRsc", PageParameters.getParameter("rsc") + "/css");
                //img resources rsc URL
                PageParameters.addParameter("imgRsc", PageParameters.getParameter("rsc") + "/img");
                //javascript resources URL
                PageParameters.addParameter("jsRcs", PageParameters.getParameter("rsc") + "/js");
                //fuentes URL
                PageParameters.addParameter("fontsPath", PageParameters.getParameter("rsc") + "/fonts");
                //ajax URL
                PageParameters.addParameter("ajax", PageParameters.getParameter("jsRcs") + "/ajax.js");
//            //jquery URL
//            // para jalarlo desde internet http://code.jquery.com/jquery-latest.js
//            //direccion de google apis https://developers.google.com/speed/libraries/devguide
//            //PageParameters.addParameter("JQueryLink", PageParameters.getParameter("jqRsc") + "/jquery-1.8.2_google_ajax.js");
//            //PageParameters.addParameter("JQueryLink", PageParameters.getParameter("jqRsc") + "/jquery-1.7.2.js"); 
                PageParameters.addParameter("JQueryLink", PageParameters.getParameter("jqRsc") + "/jquery-2.0.3_google_ajax.js");
//            //jqCalendar Funcion URL
//            PageParameters.addParameter("jqCalendar", PageParameters.getParameter("jqRsc") + "/jqCalendar");
//            //jqMsgBox - requiere licencia
//            PageParameters.addParameter("jqMsgBox", PageParameters.getParameter("mainContext") + "/JSpread/JS/jqMsgBox");
//            PageParameters.addParameter("jqAlerts", PageParameters.getParameter("mainContext") + "/JSpread/JS/jqAlerts");
//            //google bar
                PageParameters.addParameter("googleBarScript", PageParameters.getParameter("cssRsc") + "/google_bar/jquery.google_menu.js");
                PageParameters.addParameter("googleBarCSS", PageParameters.getParameter("cssRsc") + "/google_bar/google_menu.css");
//            //jqDataTables URL
                PageParameters.addParameter("jqDataTables", PageParameters.getParameter("jqRsc") + "/jqDataTables");
//            //datePicker URL
//            PageParameters.addParameter("datePicker", PageParameters.getParameter("jqRsc") + "/date-picker");
//            //javaCaptcha Funcion URL
                PageParameters.addParameter("javacaptcha", PageParameters.getParameter("mainContext") + "/jspread/javacaptcha/javacaptcha.jsp");
//            //jspBarcode Funcion URL
//            PageParameters.addParameter("jspBarcode", PageParameters.getParameter("mainContext") + "/jspread/jspBarcode/jspBarcode.jsp");
//            //favicon URL
                PageParameters.addParameter("faviconLink", PageParameters.getParameter("imgRsc") + "/ico/control_escolar.ico"); //http://www.wikipedia.org/favicon.ico
//            //PageParameters.addParameter("uploadArchivo", PageParameters.getParameter("mainContext") + "/uploadArchivo");
//            //PageParameters.addParameter("leerArchivo", PageParameters.getParameter("mainContext") + "/leerArchivo");
//            //styleFormCorrections URL
            PageParameters.addParameter("styleFormCorrections", PageParameters.getParameter("pageComponents") + "/styleFormCorrections.jsp");
//            //dataTablesFullFunctionParameters URL
//            PageParameters.addParameter("dataTablesFullFunctionParameters", PageParameters.getParameter("pageComponents") + "/dataTablesFullFunctionParameters.jsp");
//            //dataTablesBasicFunctionParameters URL
//            PageParameters.addParameter("dataTablesBasicFunctionParameters", PageParameters.getParameter("pageComponents") + "/dataTablesBasicFunctionParameters.jsp");
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //Parametros del Sistema.
                PageParameters.addParameter("leyendaoficio", "2016. Año del Centenario de la instalación del Congreso Constituyente.");
                PageParameters.addParameter("revision", "1");
//            PageParameters.addParameter("folderDocs", "c:\\sibien_Files\\files\\");//carpeta donde el sistema guarda archivos, sino existe se crea automaticamente 
//            PageParameters.addParameter("folderDocs", "/home/v10x/sibien_Files/");//carpeta donde el sistema guarda archivos, sino existe se crea automaticamente 
//            PageParameters.addParameter("folderLogs", "c:\\sibien_Files\\log\\");
//            PageParameters.addParameter("maxSizeToUpload", "130971520");//en bytes (115MB)
//            PageParameters.addParameter("deleteFileOnModifyBD", "1");//Elimina los archivos adjuntos en el servidor
//            Indica con que extensiones trabaja el sistema banned o permited, si es baned el sistema permite subir cualquier extension que no este baneada
//             si el parametro es permited, el sistema permite solo las extensiones permitidas indcadas en la clase SystemUtil
//            PageParameters.addParameter("workExtensions", "banned");
//            PageParameters.addParameter("workExtensions", "permited");
//            PageParameters.addParameter("timeOutToUploadFile", "0");// tiempo de espera para poder subir un archivo en milis (11min)
//            PageParameters.addParameter("fileSizeLimited", "1");//Indica si se limita el tamaño de los archivos que son subidos al servidor 1=si 0=no
//            PageParameters.addParameter("inicioAnioContable", "2015-01-01");
//            PageParameters.addParameter("finAnioContable", "2015-12-31");
                PageParameters.addParameter("testDBLabel", "");
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //Database parameters
                try {
                    JSpreadConnectionPool jscp = JSpreadConnectionPool.getSingleInstance();
                    jscp.setCloseNOpen(true);
                    jscp.setIsolationLevel(Connection.TRANSACTION_SERIALIZABLE);
                    jscp.setClassforname("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    jscp.setProtocol("jdbc");
                    jscp.setDBMS("sqlserver");
                    PageParameters.addParameter("DBDir", "172.16.1.13");
                    jscp.setHost("172.16.1.13");
                    jscp.setPort(1433);
                    jscp.setSchema("DBSPIC");
                    jscp.setUser("lgplanea");
                    jscp.setPassword("lpd8909&ball");
                    jscp.setMAX_POOL_SIZE(5);
                    jscp.initialize();
                    System.out.println("Direccion de la base de datos: " + jscp.getDBURL());
                } catch (Exception ex) {
                    DBError(ex);
                }
                isBurning = true;
            } catch (Exception ex) {
                Logger.getLogger(SystemSettings.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception(ex);
            }
        }

    }

    /**
     * Processes Data Base Conections methods.
     *
     * @param test test parameter to a data base conection
     * @throws IOException if an I/O error occurs
     */
    private static void DBError(Exception ex) throws Exception {
        System.out.println("\n\n\n\n");
        System.out.println("El sisguiente error de base de datos se puede resolver verificando que:");
        System.out.println("-Que el protocolo y el DBMS ya este programado en el jspread (driver)");
        System.out.println("-Que la direccion URL al host y DBMS que te quieres conectar sea la correcta");
        System.out.println("-Usuario y contraseña sean correctas");
        System.out.println("-Que se tenga ping al host donde esta la base de datos");
        System.out.println("-La red en donde se encuentre el cliente no bloque los puestos hacia la base datos");
        System.out.println("-La red en donde se encuentra el host de la base este configurado el firewall tanto de host como el de la propia red");
        System.out.println("-Verificar el puerto de la base datos sea el correcto");
        System.out.println("-Que el servicio de la base de datos este arriba");
        System.out.println("-Que la base datos exista en el host, asi tambien con el path de las mismas");
        System.out.println("\n");
        throw new Exception(ex);
    }
}
