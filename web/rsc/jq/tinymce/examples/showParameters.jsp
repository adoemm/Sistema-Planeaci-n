<%-- 
    Document   : showParameters
    Created on : Jan 11, 2013, 12:19:55 PM
    Author     : JeanPaul
--%>

<%@page import="java.util.Enumeration"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%

            //--------------------------------------------------------------
            //herramienta para diagnostico y visualizacion de parametros
            out.println("<br><br>Parametros:");
            Enumeration enu = request.getParameterNames();
            String param = "";
            while (enu.hasMoreElements()) {
                param = enu.nextElement().toString();
                out.println("<br>" + param + " = " + request.getParameter(param));
            }
            out.println("<br><br>");
            //--------------------------------------------------------------

        %>
    </body>
</html>
