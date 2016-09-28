<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>
<%    try {
        if (fine) {
            if (request.getParameter(WebUtil.encode(session, "imix")) != null) {
                LinkedList<String> access4ThisPage = new LinkedList();
                access4ThisPage.add("SessionManager");
                LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
                if (UserUtil.isAValidUser(access4ThisPage, userAccess)) {
                    Iterator it = null;
                    LinkedList listAux = null;
%>
<!DOCTYPE html>
<html lang="<%=PageParameters.getParameter("Content-Language")%>">
    <head>
        <title>Sessions</title>
        <jsp:include page='<%=PageParameters.getParameter("globalLibs")%>'/>
        <script type="text/javascript" charset="utf-8">
            window.history.forward();
            function noBack() {
                window.history.forward();
            }
        </script>
        <%@ include file="/gui/pageComponents/dataTablesFullFunctionParameters.jsp"%>
        <script type="text/javascript" language="javascript" charset="utf-8">
            window.history.forward();
            function noBack() {
                window.history.forward();
            }
            function getSessions() {
                $.ajax({type: 'POST'
                    , contentType: 'application/x-www-form-urlencoded;charset=utf-8'
                    , cache: false
                    , async: false
                    , url: '<%=PageParameters.getParameter("mainController")%>'
                    , data: '<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>&FormFrom=getSesiones'
                    , success: function (response) {
                        $('#wrapper').find('#divSessions').html(response);




                    }});
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
                <div class="form-container" width="100%">
                    <p></p>
                    <table width="100%" height="25px" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr>
                            <td width="64%" height="25" align="left" valign="top">
                                <a class="NVL" href="<%=PageParameters.getParameter("mainMenu")%>?<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>"> Menú Principal</a> 
                                > Session Manager    
                            </td>
                            <td width="36" align="right" valign="top">
                                <script language="JavaScript" src="<%=PageParameters.getParameter("jsRcs")%>/funcionDate.js" type="text/javascript"></script>
                            </td>
                        </tr>                        
                    </table>
                    <div id="loadingScreen" style="width:100%; height: 350px; font-size: 22px">                                
                    </div>
                    <script type="text/javascript" language="javascript" charset="utf-8">
            $("#loadingScreen").Loadingdotdotdot({
                "speed": 400,
                "maxDots": 6
            });
                    </script>
                    <div id="contenent_info" style="display:none; width: 100%" width="100%">
                        <form name="usuario" method="post" action="" enctype="application/x-www-form-urlencoded" id="form" width="100%">
                            <fieldset width="90%">
                                <legend>Administración de Sesiones</legend>
                                Fecha de consulta: <%=UTime.getSimpleTimeStampString()%>
                                <p align="right"><a href="javascript:location.reload()"><img src="<%=PageParameters.getParameter("imgRsc")%>/icons/Gnome-View-Refresh-64.png"/></a></p>
                                <table border="0" width="50%" cellpadding="0" cellspacing="0" class="display" id="example">

                                    <thead>
                                        <tr>
                                            <th>NP</th>
                                            <th>Logged</th>
                                            <th>ID</th>
                                            <th>Plantel</th>
                                            <th>Usuario</th>              
                                            <th>Rol</th>                  
                                            <th>Tiempo creacion</th>
                                            <th>Ultimo acceso</th>
                                            <th>Tiempo en uso</th>
                                            <th>Tiempo inactivo</th>
                                            <th>TTL</th>
                                            <th>MAX TTL</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody id="divSessions">
                                    <script>
                                        getSessions();
                                    </script>
                                    </tbody>
                                </table>
                                <table border="0" width="100%" cellpadding="0" cellspacing="0" class="display" id="exampleFilters">
                                    <tfoot>
                                        <tr>
                                            <th>NP</th>
                                            <th>Logged</th>
                                            <th>ID</th>
                                            <th>Plantel</th>
                                            <th>Usuario</th>              
                                            <th>Rol</th>                  
                                            <th>Tiempo creacion</th>
                                            <th>Ultimo acceso</th>
                                            <th>Tiempo en uso</th>
                                            <th>Tiempo inactivo</th>
                                            <th>TTL</th>
                                            <th>MAX TTL</th>
                                        </tr>
                                    </tfoot>
                                    <thead>
                                        <tr>
                                            <th>NP</th>
                                            <th>Logged</th>
                                            <th>ID</th>
                                            <th>Plantel</th>
                                            <th>Usuario</th>              
                                            <th>Rol</th>                  
                                            <th>Tiempo creacion</th>
                                            <th>Ultimo acceso</th>
                                            <th>Tiempo en uso</th>
                                            <th>Tiempo inactivo</th>
                                            <th>TTL</th>
                                            <th>MAX TTL</th>
                                        </tr>
                                    </thead>
                                </table>
                            </fieldset>
                        </form>
                    </div>
                    <%@ include file="/gui/pageComponents/reinicializarDataTables.jsp"%>
                    <a href="<%=PageParameters.getParameter("mainController")%>?<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>%>&FormFrom=terminarAllSesions"><input type="button" value="Terminar Todas"></a>
                    <br>
                    <form name="usuario" method="post" action="<%=PageParameters.getParameter("mainController")%>" enctype="application/x-www-form-urlencoded" id="form">
                        <input type="hidden" name="imix" value="<%=WebUtil.encode(session, UTime.getTimeMilis())%>"/>
                        <input type="hidden" name="FormFrom" value="terminarAllSessionsTime"/>
                        <div>
                            <br>
                            <label for="sessionTime">*Terminar sesiones inactivas mayor igual a (min.)</label>
                            <input type="text" name="sessionTime">
                            <input type="submit" value="Aceptar">
                        </div>
                    </form>
                    <div id="divResult"> 
                    </div> 

                </div>                
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
} else {
%>
<%@ include file="/gui/pageComponents/invalidParameter.jsp"%>
<%        }
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