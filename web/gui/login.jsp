<%-- 
    Document   : login
    Created on : 27/06/2016, 06:00:20 PM
    Author     : Emmanuel
--%>

<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>
<%    try {
        if (fine) {
            //if (request.getParameter(WebUtil.encode(session, "imix")) == null) {

            Iterator it = null;
            LinkedList listAux = null;

            if (PageParameters.getParameter("SiteOnMaintenance").equals("true")) {
                response.sendRedirect(PageParameters.getParameter("SiteOnMaintenanceURL").toString());
            } else {
%>
<!DOCTYPE html>
<html lang="<%=PageParameters.getParameter("Content-Language")%>">
    <head>

        <title>Sistema de Planeación Institucional de CECYTEM</title>
        <jsp:include page='<%=PageParameters.getParameter("globalLibs")%>'/>        
        <script type="text/javascript" language="javascript" charset="utf-8">
            window.history.forward();
            function noBack() {
                window.history.forward();
            }
        </script>
        <script type="text/javascript">
            setTimeout("document.location=document.location", 1795000);
        </script>
    </head>
    <body onload="">
        <div id="wrapper">
            <div id="divBody">
                <jsp:include page='<%=("" + PageParameters.getParameter("logo"))%>' />
                <p><br></p>
                <p><br></p>   
                <div>                    
                    <form id="logIn" name="logIn" enctype="application/x-www-form-urlencoded" method="post" action="<%=PageParameters.getParameter("mainController")%>">
                        <input type="hidden" name="LogInPage" value="LogInPage">
                        <table width="270px" border="0" align="center">
                            <tr>
                                <td><table width="270px" border="0" align="center">
                                        <tr>
                                            <td width="88"><div align="left">Usuario:</div></td>
                                            <td width="185">
                                                <label>
                                                    <input name="user" type="text" id="user" value="" size="25" style="height: 20px; width: 100%" tabindex="1">
                                                </label>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="left">Contraseña:</td>
                                            <td>
                                                <label>
                                                    <input name="pass" type="password" id="pass" value="" size="25" style="height: 20px; width: 100%" tabindex="2">
                                                </label>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td>&nbsp;</td>
                                            <td>
                                                <div align="center"><img src="<%=PageParameters.getParameter("javacaptcha")%>" id="captcha" alt="captcha" style="width: 100%"></div>
                                                <input type="button"  id="reload" value="Cambiar código de verificación"  style="width: 100%"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="left" name="captchav" id="captchav">Código de verificación:</td>
                                            <td><input name="captcha" type="text" id="captcha" value="" style="height: 20px; width: 100%" tabindex="3"></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <input type="submit" name="login" id="login" value="Entrar" style="height: 27px; width:100%" tabindex="4">
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
                <p><br></p>
                <p><br></p> 
                <div id="divFoot">
                    <jsp:include page='<%=(PageParameters.getParameter("footer"))%>' />
                </div>
            </div>
        </div>
        <script>
            $(function () {
                $('#reload').click(function () {

                    var d = new Date();
                    $('#captcha').attr('src', '<%=PageParameters.getParameter("javacaptcha")%>?' + d.getTime());
                });
            });
        </script>
    </body>
</html>
<%
        }
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
