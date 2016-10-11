<%-- 
    Document   : mainMenu
    Created on : Oct 4, 2016, 11:23:21 AM
    Author     : emmanuel
--%>


<%@page import="jspread.core.util.WebUtil"%>
<%@page import="jspread.core.util.UserUtil"%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<link href="../rsc/css/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css" /> 
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>
<%    try {
        if (fine) {
            //if (request.getParameter(WebUtil.encode(session, "imix")) == null) {
            
            String access4ThisPage="accessMain";

            LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
            if (UserUtil.isAValidUser(access4ThisPage, userAccess)) {
                if (PageParameters.getParameter("SiteOnMaintenance").equals("true")) {
                    String redirectURL = "" + PageParameters.getParameter("mainController") + "?exit=1";
                    response.sendRedirect(redirectURL);
                } else {
                    
%>
<!DOCTYPE html>
<html lang="<%=PageParameters.getParameter("Content-Language")%>">
    <head>
        <title>Menú Principal</title>
        <jsp:include page='<%=PageParameters.getParameter("globalLibs")%>'/>        

        <script type="text/javascript" language="javascript" charset="utf-8">
            window.history.forward();
            function noBack() {
                window.history.forward();
            }
        </script>
    </head>

    <body>
        <div id="wrapper">
            <div id="divBody">
                <jsp:include page='<%=("" + PageParameters.getParameter("logo"))%>' />
                <div id="barMenu">
                    <jsp:include page='<%=(PageParameters.getParameter("barMenu"))%>' />
                </div>
                <p></p>
                <table width="100%" height="25px" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="64%" height="50" align="left" valign="top">
                            <a>Menú Principal</a>
                        </td>
                        <td width="36" align="right" valign="top">
                            <script language="JavaScript" src="<%=PageParameters.getParameter("jsRcs")%>/funcionDate.js" type="text/javascript"></script>
                        </td>
                    </tr>                        
                </table>
                <br>
                <br>
                <div>
                    <table width="200" border="0" align="center">
                        <tr></tr>
                        <tr>
                            <td><a href="<%=PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui")%>/plantelSelect.jsp?<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>">Ficha Técnica</a></td>
                         
                        </tr>
                        
                    </table>
                </div>
                <div id="divFoot">
                    <jsp:include page='<%=(PageParameters.getParameter("footer"))%>' />
                </div> 

            </div>            
        </div>
    </body>
</html>
<%
    }
} else {
    //System.out.println("Usuario No valido para esta pagina");
%>                
<%
    response.sendRedirect(PageParameters.getParameter("mainContext") + PageParameters.getParameter("LogInPage"));
%>
<%    }
    }
} catch (Exception ex) {
    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    //Mensaje de Error para los errores en servidor.
%>
<%@ include file="/gui/pageComponents/handleUnExpectedError.jsp"%>
</body>
</html>
<%
    }
%>
