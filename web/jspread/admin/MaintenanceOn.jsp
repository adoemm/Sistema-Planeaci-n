<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>

<%    try {
        if (fine) {

            LinkedList<String> access4ThisPage = new LinkedList();
            access4ThisPage.add("MatenimientoOn");

            LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
            if (UserUtil.isAValidUser(access4ThisPage, userAccess)) {
                Iterator it = null;
                LinkedList listAux = null;
%>
<!DOCTYPE html>
<html lang="<%=PageParameters.getParameter("Content-Language")%>">
    <head>
        <title>Seguridad</title>
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
                        <td width="64%" height="25" align="left" valign="top">
                            <a class="NVL" href="<%=PageParameters.getParameter("mainMenu")%>?<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>"> Menú Principal</a> 
                            > <a class="NVL" href="<%=PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui")%>/MenuSeguridad.jsp?<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>">Seguridad</a> 
                            > Poner en Mantenimiento
                        </td>
                        <td width="36" align="right" valign="top">
                            <script language="JavaScript" src="<%=PageParameters.getParameter("jsRcs")%>/funcionDate.js" type="text/javascript"></script>
                        </td>
                    </tr>                        
                </table>
                <br>
                <br>
                <br>
                <div>
                    <div>
                        <p>
                            <%
                                out.println("<p>antes SiteOnMaintenance: " + PageParameters.getParameter("SiteOnMaintenance"));
                                out.println("<p>antes msgOnMaintenance: " + PageParameters.getParameter("msgOnMaintenance"));

                                if (request.getParameter("msgOnMaintenance") != null) {
                                    PageParameters.addParameter("msgOnMaintenance", "" + request.getParameter("msgOnMaintenance"));
                                }

                                PageParameters.addParameter("SiteOnMaintenance", "true");
                                out.println("<p>despues SiteOnMaintenance " + PageParameters.getParameter("SiteOnMaintenance"));
                                out.println("<p>despues msgOnMaintenance " + PageParameters.getParameter("msgOnMaintenance"));
                            %>
                        </p>
                    </div>
                    <div>
                        <form id="form" name="form" enctype="application/x-www-form-urlencoded" method="post" action="">
                            <label>
                                <input name="msgOnMaintenance" type="text" id="msgOnMaintenance" value="">
                            </label>
                            <input type="submit" name="enviar" id="enviar" value="Enviar Mensaje">
                        </form>
                    </div>
                </div>
                <br>
                <br>
                <div id="divFoot">
                    <jsp:include page='<%=(PageParameters.getParameter("footer"))%>' />
                </div> 
            </div>            
        </div>
    </body>
</html>
<%
} else {

%>                
<%@ include file="/gui/pageComponents/invalidUser.jsp"%>
<%    }
    }
} catch (Exception ex) {
    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
%>
<%@ include file="/gui/pageComponents/handleUnExpectedError.jsp"%>
</body>
</html>
<%
    }
%>




>