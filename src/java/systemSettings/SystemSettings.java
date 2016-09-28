/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemSettings;

import jspread.core.util.PageParameters;
import jspread.core.util.SpreadParameters;

/**
 *
 * @author emmanuel
 */
public final class SystemSettings {

    private static boolean isBurning = false;

    public static void ignition() throws Exception {
        if (!isBurning) {
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
            //(PageParameters.addParameter("barMenu", PageParameters.getParameter("pageComponents") + "/barMenu.jsp"); checar
            //footer URL
            PageParameters.addParameter("footer", PageParameters.getParameter("pageComponents") + "/footerInstitucional.jsp");

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //Paginas del Sistema.
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

        }
    }
}
