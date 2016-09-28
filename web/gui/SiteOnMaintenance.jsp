<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>
<%
    try {
        if (fine) {
            //if (request.getParameter(WebUtil.encode(session, "imix")) == null) {
            Iterator it = null;
            LinkedList listAux = null;
%>
<!DOCTYPE html>
<html lang="<%=PageParameters.getParameter("Content-Language")%>">
    <head>
        <title>Sitio en mantenimiento</title>
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
                <script type="text/javascript" language="javascript" charset="utf-8">
                    session.invalidate();
                </script> 
                <jsp:include page='<%=("" + PageParameters.getParameter("logo"))%>' />                             
                <div>
                    <div class="errors">
                        <p>
                            <em><%=PageParameters.getParameter("msgOnMaintenance").toUpperCase().trim()%></em>
                        </p>
                    </div>
                    <p><br></p>
                    <p><br></p>
                    <table width="200" border="0" align="center"  >
                        <tr>
                            <td><img src="<%out.print(PageParameters.getParameter("imgRsc").toString());%>/mantenimiento-equipos-servidores-redes.png" /></td>
                        </tr>
                    </table>
                </div>
                <p></p>
                <br>
                <div id="divFoot">
                    <jsp:include page='<%=(PageParameters.getParameter("footer"))%>' />
                </div> 
            </div>            
        </div>
    </body>
</html>
<%
    }
} catch (Exception ex) {
    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
%>
<%@ include file="/gui/pageComponents/handleUnExpectedError.jsp"%>
</body>
</html>
<%
        //response.sendRedirect(redirectURL);
    }
%>
