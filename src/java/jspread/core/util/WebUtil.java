package jspread.core.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jspread.core.util.security.EDP;

/**
 *
 * @author Administrador
 */
public final class WebUtil {

    private static final String version = "V0.15";

    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String encodeParam(HttpSession session, String parameterName, String parameterValue) {
        String parameter = "";
        if (PageParameters.getParameter("encodeParameters").equalsIgnoreCase("true")) {
            parameter = EDP.encrypt(session, parameterName);
            parameter = parameter + EDP.encrypt(session, parameterValue);
        } else {
            parameter = parameterName + "=" + "parameterValue";
        }
        return parameter;
    }

    public static String encodeURL(HttpSession session, String parameters) {
        if (PageParameters.getParameter("encodeParameters").equalsIgnoreCase("true")) {
            parameters = EDP.encrypt(session, parameters);
        }
        return parameters;
    }

    public static String encode(HttpSession session, Object parameter) {
        String sParameter = "";
        if (parameter != null) {
            sParameter = parameter.toString();
            if (PageParameters.getParameter("encodeParameters").equalsIgnoreCase("true")) {
                sParameter = EDP.encrypt(session, sParameter);
                sParameter = sParameter.replaceAll("=", "_jspread_");
                sParameter = sParameter.replaceAll("\\+", "_A2psljspread_");
                sParameter = sParameter.replaceAll(" ", "_spcjspread_");
            }
        }
        return sParameter;
    }

    public static String encode(String key, Object parameter) {
        String sParameter = "";
        if (parameter != null) {
            sParameter = parameter.toString();
            if (PageParameters.getParameter("encodeParameters").equalsIgnoreCase("true")) {
                sParameter = EDP.encrypt(key, sParameter);
                sParameter = sParameter.replaceAll("=", "_jspread_");
                sParameter = sParameter.replaceAll("\\+", "_A2psljspread_");
                sParameter = sParameter.replaceAll(" ", "_spcjspread_");
            }
        }
        return sParameter;
    }

    public static String decodeParam(HttpSession session, String parameterName, String parameterValue) {
        String parameter = "";
        if (PageParameters.getParameter("encodeParameters").equalsIgnoreCase("true")) {
            parameter = EDP.decrypt(session, parameterName);
            parameter = parameter + EDP.encrypt(session, parameterValue);
        } else {
            parameter = parameterName + "=" + "parameterValue";
        }
        return parameter;
    }

    public static void decodeParam(HttpSession session, ServletRequest request) {
        if (PageParameters.getParameter("encodeParameters").equalsIgnoreCase("true")) {
            String param = "";
            String pametroAux = "";
            Enumeration enu = request.getParameterNames();
            while (enu.hasMoreElements()) {
                param = enu.nextElement().toString();
                pametroAux = EDP.decrypt(session, param);
                request.setAttribute(pametroAux, EDP.decrypt(session, request.getParameter(param)));
            }
        }
    }

    public static void decodeParam(HttpSession session, HttpServletRequest request) {
        if (PageParameters.getParameter("encodeParameters").equalsIgnoreCase("true")) {
            String param = "";
            String pametroAux = "";
            Enumeration enu = request.getParameterNames();
            while (enu.hasMoreElements()) {
                param = enu.nextElement().toString();
                pametroAux = EDP.decrypt(session, param);
                request.setAttribute(pametroAux, EDP.decrypt(session, request.getParameter(param)));
            }
        }
    }

    public static void decodeURL(HttpSession session, HttpServletRequest request) {
        //System.out.println("request.getQueryString(): " + request.getQueryString());
        if (request.getQueryString() != null) {
            String[] parametros = null;
            if (PageParameters.getParameter("encodeParameters").equalsIgnoreCase("true")) {
                parametros = EDP.decrypt(session, request.getQueryString()).split("&");
            } else {
                parametros = request.getQueryString().split("&");
            }
            for (int i = 0; i < parametros.length; i++) {
                String[] parametro = parametros[i].split("=");
                request.setAttribute(parametro[0], parametro[1]);
            }
        }
    }

    public static String decode(HttpSession session, String parameter) {
        if (parameter != null) {
            if (PageParameters.getParameter("encodeParameters").equalsIgnoreCase("true")) {
                parameter = parameter.replaceAll("_spcjspread_", " ");
                parameter = parameter.replaceAll("_A2psljspread_", "\\+");
                parameter = parameter.replaceAll("_jspread_", "=");
                parameter = EDP.decrypt(session, parameter);
            }
        } else {
            parameter = "";
        }
        return parameter;
    }

    public static String decode(String key, String parameter) {
        if (parameter != null) {
            parameter = parameter.replaceAll("_spcjspread_", " ");
            parameter = parameter.replaceAll("_A2psljspread_", "\\+");
            parameter = parameter.replaceAll("_jspread_", "=");
            parameter = EDP.decrypt(key, parameter);
            return parameter;
        }
        return parameter;
    }

    public static String msgboxNBack(String message, String type) {
        StringBuilder htmlFragment = new StringBuilder();
        htmlFragment.append("<html>");
        htmlFragment.append("<head>");
        htmlFragment.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        htmlFragment.append(jsHeader(PageParameters.getParameter("JQueryLink").toString()));
        htmlFragment.append(jsHeader(PageParameters.getParameter("jqMsgBox").toString() + "/jquery.msgbox.min.js"));
        htmlFragment.append(cssHeader(PageParameters.getParameter("jqMsgBox").toString() + "/jquery.msgbox.css", "text/css"));
        htmlFragment.append("</head>");
        htmlFragment.append("<body>");
        htmlFragment.append("<SCRIPT type=\"text/javascript\" LANGUAGE=\"JavaScript\">");
        htmlFragment.append("window.onload = function () {");
        htmlFragment.append("$.msgbox(\"").append(message).append("\", {");
        htmlFragment.append("type: \"").append(type).append("\",");
        htmlFragment.append("buttons : [");
        htmlFragment.append("{type: \"submit\", value: \"OK\"},");
        htmlFragment.append("]");
        htmlFragment.append("}, function(result) {");
        htmlFragment.append("window.history.back();");
        htmlFragment.append("});");
        htmlFragment.append("}");
        htmlFragment.append("</SCRIPT>");
        htmlFragment.append("</body>");
        htmlFragment.append("</html>");
        return htmlFragment.toString();
    }

    /*
     //jqMsgBox - requiere licencia
     public static String messageNBack(String message, String type) {
     StringBuilder htmlFragment = new StringBuilder();
     htmlFragment.append("<html>");
     htmlFragment.append("<head>");
     htmlFragment.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
     htmlFragment.append(jsHeader(PageParameters.getParameter("JQueryLink").toString()));
     htmlFragment.append(jsHeader(PageParameters.getParameter("jqMsgBox").toString() + "/jquery.msgbox.min.js"));
     htmlFragment.append(cssHeader(PageParameters.getParameter("jqMsgBox").toString() + "/jquery.msgbox.css", "text/css"));
     htmlFragment.append("</head>");
     htmlFragment.append("<body>");
     htmlFragment.append("<SCRIPT type=\"text/javascript\" LANGUAGE=\"JavaScript\">");
     htmlFragment.append("window.onload = function () {");
     htmlFragment.append("$.msgbox(\"").append(message).append("\", {");
     htmlFragment.append("type: \"").append(type).append("\",");
     htmlFragment.append("buttons : [");
     htmlFragment.append("{type: \"submit\", value: \"OK\"},");
     htmlFragment.append("]");
     htmlFragment.append("}, function(result) {");
     htmlFragment.append("window.history.back();");
     htmlFragment.append("});");
     htmlFragment.append("}");
     htmlFragment.append("</SCRIPT>");
     htmlFragment.append("</body>");
     htmlFragment.append("</html>");
     return htmlFragment.toString();
     }

     public static String messageNRedirect(String message, String type, String url) {
     StringBuilder htmlFragment = new StringBuilder();
     htmlFragment.append("<html>");
     htmlFragment.append("<head>");
     htmlFragment.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
     htmlFragment.append(jsHeader(PageParameters.getParameter("JQueryLink").toString()));
     htmlFragment.append(jsHeader(PageParameters.getParameter("jqMsgBox").toString() + "/jquery.msgbox.min.js"));
     htmlFragment.append(cssHeader(PageParameters.getParameter("jqMsgBox").toString() + "/jquery.msgbox.css", "text/css"));
     htmlFragment.append("</head>");
     htmlFragment.append("<body>");
     htmlFragment.append("<SCRIPT type=\"text/javascript\" LANGUAGE=\"JavaScript\">");
     htmlFragment.append("window.onload = function () {");
     htmlFragment.append("$.msgbox(\"").append(message).append("\", {");
     htmlFragment.append("type: \"").append(type).append("\",");
     htmlFragment.append("buttons : [");
     htmlFragment.append("{type: \"submit\", value: \"OK\"},");
     htmlFragment.append("]");
     htmlFragment.append("}, function(result) {");
     htmlFragment.append("window.location.href=\"");
     htmlFragment.append(url);
     htmlFragment.append("\";");
     htmlFragment.append("});");
     htmlFragment.append("}");
     htmlFragment.append("</SCRIPT>");
     htmlFragment.append("</body>");
     htmlFragment.append("</html>");
     return htmlFragment.toString();
     }
     * 
     */
    //
    //
    public static String messageNRedirect(String message, String type, String title, String url) {
        StringBuilder htmlFragment = new StringBuilder();
        htmlFragment.append("<html>");
        htmlFragment.append("<head>");
        htmlFragment.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=").append(PageParameters.getParameter(("charset"))).append("\">");
        htmlFragment.append(jsHeader(PageParameters.getParameter("JQueryLink").toString()));
        htmlFragment.append(jsHeader(PageParameters.getParameter("jqAlerts").toString() + "/jquery.alerts.js"));
        htmlFragment.append(jsHeader(PageParameters.getParameter("jqAlerts").toString() + "/jquery.ui.draggable.js"));
        htmlFragment.append(cssHeader(PageParameters.getParameter("jqAlerts").toString() + "/jquery.alerts.css", "text/css"));
        htmlFragment.append("</head>");
        htmlFragment.append("<body>");
        htmlFragment.append("<SCRIPT type=\"text/javascript\" LANGUAGE=\"JavaScript\">");

        if (type.equalsIgnoreCase("error")) {
            htmlFragment.append("jError('").append(message).append("', '").append(title).append("', function(r) {window.location.href='").append(url).append("'});");
        } else if (type.equalsIgnoreCase("info")) {
            htmlFragment.append("jInfo('").append(message).append("', '").append(title).append("', function(r) {window.location.href='").append(url).append("'});");
        } else if (type.equalsIgnoreCase("alert")) {
            htmlFragment.append("jAlert('").append(message).append("', '").append(title).append("', function(r) {window.location.href='").append(url).append("'});");
        }

        htmlFragment.append("</SCRIPT>");
        htmlFragment.append("</body>");
        htmlFragment.append("</html>");
        return htmlFragment.toString();
    }

    public static String messageNBack(String message, String type, String title) {
        StringBuilder htmlFragment = new StringBuilder();
        htmlFragment.append("<html>");
        htmlFragment.append("<head>");
        htmlFragment.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        htmlFragment.append(jsHeader(PageParameters.getParameter("JQueryLink").toString()));
        htmlFragment.append(jsHeader(PageParameters.getParameter("jqAlerts").toString() + "/jquery.alerts.js"));
        htmlFragment.append(jsHeader(PageParameters.getParameter("jqAlerts").toString() + "/jquery.ui.draggable.js"));
        htmlFragment.append(cssHeader(PageParameters.getParameter("jqAlerts").toString() + "/jquery.alerts.css", "text/css"));
        htmlFragment.append("</head>");
        htmlFragment.append("<body>");
        htmlFragment.append("<SCRIPT type=\"text/javascript\" LANGUAGE=\"JavaScript\">");

        if (type.equalsIgnoreCase("error")) {
            htmlFragment.append("jError('").append(message).append("', '").append(title).append("', function(r) {window.history.back();});");
        } else if (type.equalsIgnoreCase("info")) {
            htmlFragment.append("jInfo('").append(message).append("', '").append(title).append("', function(r) {window.history.back();});");
        } else if (type.equalsIgnoreCase("alert")) {
            htmlFragment.append("jAlert('").append(message).append("', '").append(title).append("', function(r) {window.history.back();});");
        }

        htmlFragment.append("</SCRIPT>");
        htmlFragment.append("</body>");
        htmlFragment.append("</html>");
        return htmlFragment.toString();
    }

    public static String messageNClose(String message, String type, String title) {
        StringBuilder htmlFragment = new StringBuilder();
        htmlFragment.append("<html>");
        htmlFragment.append("<head>");
        htmlFragment.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        htmlFragment.append(jsHeader(PageParameters.getParameter("JQueryLink").toString()));
        htmlFragment.append(jsHeader(PageParameters.getParameter("jqAlerts").toString() + "/jquery.alerts.js"));
        htmlFragment.append(jsHeader(PageParameters.getParameter("jqAlerts").toString() + "/jquery.ui.draggable.js"));
        htmlFragment.append(cssHeader(PageParameters.getParameter("jqAlerts").toString() + "/jquery.alerts.css", "text/css"));
        htmlFragment.append("</head>");
        htmlFragment.append("<body>");
        htmlFragment.append("<SCRIPT type=\"text/javascript\" LANGUAGE=\"JavaScript\">");

        if (type.equalsIgnoreCase("error")) {
            htmlFragment.append("jError('").append(message).append("', '").append(title).append("', function(r) {window.close();});");
        } else if (type.equalsIgnoreCase("info")) {
            htmlFragment.append("jInfo('").append(message).append("', '").append(title).append("', function(r) {window.close();});");
        } else if (type.equalsIgnoreCase("alert")) {
            htmlFragment.append("jAlert('").append(message).append("', '").append(title).append("', function(r) {window.close();});");
        }

        htmlFragment.append("</SCRIPT>");
        htmlFragment.append("</body>");
        htmlFragment.append("</html>");
        return htmlFragment.toString();
    }

    public static String message(String message, String type, String title) {
        StringBuilder htmlFragment = new StringBuilder();
        htmlFragment.append("<html>");
        htmlFragment.append("<head>");
        htmlFragment.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        htmlFragment.append(jsHeader(PageParameters.getParameter("JQueryLink").toString()));
        htmlFragment.append(jsHeader(PageParameters.getParameter("jqAlerts").toString() + "/jquery.alerts.js"));
        htmlFragment.append(jsHeader(PageParameters.getParameter("jqAlerts").toString() + "/jquery.ui.draggable.js"));
        htmlFragment.append(cssHeader(PageParameters.getParameter("jqAlerts").toString() + "/jquery.alerts.css", "text/css"));
        htmlFragment.append("</head>");
        htmlFragment.append("<body>");
        htmlFragment.append("<SCRIPT type=\"text/javascript\" LANGUAGE=\"JavaScript\">");

        if (type.equalsIgnoreCase("error")) {
            htmlFragment.append("jError('").append(message).append("', '").append(title).append("');");
        } else if (type.equalsIgnoreCase("info")) {
            htmlFragment.append("jInfo('").append(message).append("', '").append(title).append("');");
        } else if (type.equalsIgnoreCase("alert")) {
            htmlFragment.append("jAlert('").append(message).append("', '").append(title).append("');");
        }

        htmlFragment.append("</SCRIPT>");
        htmlFragment.append("</body>");
        htmlFragment.append("</html>");
        return htmlFragment.toString();
    }

    public static String simpleMessage(String message, String type, String title) {
        StringBuilder htmlFragment = new StringBuilder();
        htmlFragment.append(jsHeader(PageParameters.getParameter("jqAlerts").toString() + "/jquery.alerts.js"));
        htmlFragment.append(jsHeader(PageParameters.getParameter("jqAlerts").toString() + "/jquery.ui.draggable.js"));
        htmlFragment.append(cssHeader(PageParameters.getParameter("jqAlerts").toString() + "/jquery.alerts.css", "text/css"));
        htmlFragment.append("<script type=\"text/javascript\" LANGUAGE=\"JavaScript\">");
        if (type.equalsIgnoreCase("error")) {
            htmlFragment.append("jError('").append(message).append("', '").append(title).append("');");
        } else if (type.equalsIgnoreCase("info")) {
            htmlFragment.append("jInfo('").append(message).append("', '").append(title).append("');");
        } else if (type.equalsIgnoreCase("alert")) {
            htmlFragment.append("jAlert('").append(message).append("', '").append(title).append("');");
        }
        htmlFragment.append("</script>");
        return htmlFragment.toString();
    }

    public static String messageNBackBasic(String message) {
        StringBuilder htmlFragment = new StringBuilder();
        htmlFragment.append("<html>");
        htmlFragment.append("<head>");
        htmlFragment.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        htmlFragment.append("<SCRIPT type=\"text/javascript\" LANGUAGE=\"JavaScript\">");
        htmlFragment.append("alert('");
        htmlFragment.append(message);
        htmlFragment.append("');");
        htmlFragment.append("window.history.back();");
        htmlFragment.append("</SCRIPT>");
        htmlFragment.append("</head>");
        htmlFragment.append("</html>");
        return htmlFragment.toString();
    }

    public static String messageNRedirectBasic(String message, String url) {
        StringBuilder htmlFragment = new StringBuilder();
        htmlFragment.append("<html>");
        htmlFragment.append("<head>");
        htmlFragment.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        htmlFragment.append("<SCRIPT type=\"text/javascript\" LANGUAGE=\"JavaScript\">");
        htmlFragment.append("alert('");
        htmlFragment.append(message);
        htmlFragment.append("');");
        htmlFragment.append("window.location.href=\"");
        htmlFragment.append(url);
        htmlFragment.append("\";");
        htmlFragment.append("</SCRIPT>");
        htmlFragment.append("</head>");
        htmlFragment.append("</html>");
        return htmlFragment.toString();
    }

    public static String popupHeader(String url, String funcionName) {
        StringBuilder htmlFragment = new StringBuilder();
        htmlFragment.append("<SCRIPT type=\"text/javascript\" LANGUAGE=\"JavaScript\">");
        htmlFragment.append("function ");
        htmlFragment.append(funcionName);
        htmlFragment.append("() {");
        htmlFragment.append("window.open(\"");
        htmlFragment.append(url);
        htmlFragment.append("\", \"myWindow\", ");
        htmlFragment.append("\"status = 1, height = 300, width = 300, resizable = 0\" )");
        htmlFragment.append("}");
        htmlFragment.append("</SCRIPT>");
        return htmlFragment.toString();
    }

    public static String popupHeader(String url, String funcionName, int height, int width) {
        StringBuilder htmlFragment = new StringBuilder();
        htmlFragment.append("<SCRIPT type=\"text/javascript\" LANGUAGE=\"JavaScript\">");
        htmlFragment.append("function ");
        htmlFragment.append(funcionName);
        htmlFragment.append("() {");
        htmlFragment.append("window.open(\"");
        htmlFragment.append(url);
        htmlFragment.append("\", \"myWindow\", ");
        htmlFragment.append("\"status = 1, height = ").append(height).append(", width = ").append(width).append(", resizable = 0\" )");
        htmlFragment.append("}");
        htmlFragment.append("</SCRIPT>");
        return htmlFragment.toString();
    }

    public static String popupbutton(String funcionName, String namebuttom) {
        String x = "<input type=\"button\" onClick=\"" + funcionName + "()\" value=\"" + namebuttom + "!\">";
        return x;
    }

    public static String popupbuttonOnClick(String funcionName, String namebuttom) {
        String x = "<input type=\"button\" onClick=\"" + funcionName + "()\" value=\"" + namebuttom + "!\">";
        return x;
    }

    public static String askToLeaveHeader(String message) {
        StringBuilder htmlFragment = new StringBuilder();
        htmlFragment.append("<SCRIPT type=\"text/javascript\" LANGUAGE=\"JavaScript\">");
        htmlFragment.append("window.onbeforeunload = confirmExit;");
        htmlFragment.append("function confirmExit()");
        htmlFragment.append("{");
        htmlFragment.append("return \"");
        htmlFragment.append(message);
        htmlFragment.append("\"");
        htmlFragment.append("}");
        htmlFragment.append("</SCRIPT>");
        return htmlFragment.toString();
    }

    public static String faviconHeader(String URI) {
        StringBuilder htmlFragment = new StringBuilder();
        //<link rel="shortcut icon" href="http://www.wikipedia.org/favicon.ico" />
        htmlFragment.append("<link rel=\"shortcut icon\" href=\"");
        htmlFragment.append(URI);
        htmlFragment.append("\"/>");
        return htmlFragment.toString();
    }

    public static String faviconHeader(String URI, String type) {
        StringBuilder htmlFragment = new StringBuilder();
        //<link rel="shortcut icon" href="http://www.wikipedia.org/favicon.ico" />
        htmlFragment.append("<link rel=\"shortcut icon\" href=\"");
        htmlFragment.append(URI);
        htmlFragment.append("\" type=\"").append(type).append("\"/>");
        return htmlFragment.toString();
    }

    public static String charset() {
        StringBuilder htmlFragment = new StringBuilder();
        htmlFragment.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
        return htmlFragment.toString();
    }

    public static String charset(String charset) {
        StringBuilder htmlFragment = new StringBuilder();
        htmlFragment.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=");
        htmlFragment.append(charset);
        htmlFragment.append("\">");
        return htmlFragment.toString();
    }

    public static String cssHeader(String URI, String type) {
        StringBuilder htmlFragment = new StringBuilder();
        //<link rel="shortcut icon" href="http://www.wikipedia.org/favicon.ico" />
        htmlFragment.append("<link rel=\"STYLESHEET\" href=\"");
        htmlFragment.append(URI);
        htmlFragment.append("\" type=\"").append(type).append("\"/>");
        return htmlFragment.toString();
    }

    public static String jsHeader(String URI) {
        StringBuilder htmlFragment = new StringBuilder();
        //<script src="http://code.jquery.com/jquery-latest.js"></script>
        htmlFragment.append("<script type=\"text/javascript\" LANGUAGE=\"JavaScript\" src=\"");
        htmlFragment.append(URI);
        htmlFragment.append("\"></script>");
        return htmlFragment.toString();
    }

    public static String jqFancyTransitionsHeader(String divID, int width, int height) {
        StringBuilder htmlFragment = new StringBuilder();
        htmlFragment.append("<script type=\"text/javascript\" LANGUAGE=\"JavaScript\" >");
        htmlFragment.append("$(document).ready( function(){");
        htmlFragment.append("$('#").append(divID).append("').jqFancyTransitions({ width: ").append(width).append(", height: ").append(height).append(" });");
        htmlFragment.append("});");
        htmlFragment.append("</script>");
        return htmlFragment.toString();
    }

    public static String jqCalendar() {
        StringBuilder htmlFragment = new StringBuilder();
        htmlFragment.append(jsHeader(PageParameters.getParameter("jqCalendar").toString() + "/jsDatePick.jquery.min.1.3.js"));
        htmlFragment.append(cssHeader(PageParameters.getParameter("jqCalendar").toString() + "/jsDatePick_ltr.css", "text/css"));
        htmlFragment.append("<script type=\"text/javascript\" LANGUAGE=\"JavaScript\" >");
        htmlFragment.append("function calendarField(id){");
        htmlFragment.append("new JsDatePick({");
        htmlFragment.append("useMode:2,");
        htmlFragment.append("target:id,");
        htmlFragment.append("dateFormat:\"%Y-%M-%d\"");
        htmlFragment.append("});");
        htmlFragment.append("}");
        htmlFragment.append("</script>");
        return htmlFragment.toString();
    }

    public static String datePicker() {
        StringBuilder htmlFragment = new StringBuilder();
        htmlFragment.append(jsHeader(PageParameters.getParameter("datePicker").toString() + "/js/datepicker.js"));
        htmlFragment.append(cssHeader(PageParameters.getParameter("datePicker").toString() + "/css/datepicker.css", "text/css"));
        return htmlFragment.toString();
    }

    public static String jqDataTables() {
        StringBuilder htmlFragment = new StringBuilder();
        htmlFragment.append(jsHeader(PageParameters.getParameter("jqDataTables").toString() + "/media/js/jquery.dataTables.min.js"));
        htmlFragment.append(cssHeader(PageParameters.getParameter("jqDataTables").toString() + "/media/css/demo_table_jui.css", "text/css"));
        htmlFragment.append(cssHeader(PageParameters.getParameter("jqDataTables").toString() + "/media/themes/smoothness/jquery-ui-1.8.4.custom.css", "text/css"));
        return htmlFragment.toString();
    }

    public static String jqDataTablesUpdate() {
        StringBuilder htmlFragment = new StringBuilder();
        htmlFragment.append(jsHeader(PageParameters.getParameter("jqDataTables").toString() + "/media/js/jquery.dataTables.min.js"));
        htmlFragment.append(cssHeader(PageParameters.getParameter("jqDataTables").toString() + "/media/css/demo_table_jui.css", "text/css"));
        htmlFragment.append(cssHeader(PageParameters.getParameter("jqDataTables").toString() + "/media/themes/smoothness/jquery-ui-1.8.4.custom.css", "text/css"));
        htmlFragment.append(jsHeader(PageParameters.getParameter("jqDataTables").toString() + "/media/js/jquery.jeditable.js"));
        return htmlFragment.toString();
    }

    public static void printItartor(Iterator t) {
        while (t.hasNext()) {
            System.out.println("element: " + t.toString());
        }
    }

    public static String getContentType(String extension) {
        String content = "application/octet-stream";
        switch (extension) {
            case ".pdf":
                content = "application/pdf";
                break;
            case ".png":
                content = "image/png";
                break;
            case ".jpg":
                content = "image/jpeg";
                break;
            case ".gif":
                content = "image/gif";
                break;
            case ".bmp":
                content = "image/bmp";
                break;
        }
        return content;
    }
}
