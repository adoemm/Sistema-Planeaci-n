<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            out.println("<p>antes SiteOnMaintenance "+PageParameters.getParameter("SiteOnMaintenance"));
            PageParameters.addParameter("SiteOnMaintenance", "false");
            out.println("<p>despues SiteOnMaintenance "+PageParameters.getParameter("SiteOnMaintenance"));
        %>
        <h1>Hello World!</h1>
    </body>
</html>