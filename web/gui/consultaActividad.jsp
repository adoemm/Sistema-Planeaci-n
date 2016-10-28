<%-- 
    Document   : consultaActividad
    Created on : Oct 28, 2016, 4:43:38 PM
    Author     : emmanuel
--%>

<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
 <link href="../rsc/css/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css" /> 
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>
<%    try {
        if (fine) {
            
            if (request.getParameter(WebUtil.encode(session, "imix")) != null) {
                String access4ThisPage = "accessToViewActivity";
                LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
                if (UserUtil.isAValidUser(access4ThisPage, userAccess)) {
                    if (PageParameters.getParameter("SiteOnMaintenance").equals("true")) {
                        String redirectURL = "" + PageParameters.getParameter("mainController") + "?exit=1";
                        response.sendRedirect(redirectURL);
                    } else {
                         Iterator it = null;
                         
                        int idPlantel= Integer.parseInt(WebUtil.decode(session, request.getParameter("idPlantel")));
                             
%>


<!DOCTYPE html>
<html>
    <head>
        
        <title>Etapas de Desarrollo</title>
        <jsp:include page='<%=PageParameters.getParameter("globalLibs")%>'/>
        <script type="text/javascript" language="javascript" charset="utf-8">
            window.history.forward();
            function noBack() {
                window.history.forward();
            }
            function sendInfoToAgregaEtapaDesarrollo()
            {
               
                    $.ajax({type: 'POST'
                        , contentType: 'application/x-www-form-urlencoded;charset=utf-8'
                        , cache: false
                        , async: false
                        , url: "<%=PageParameters.getParameter("mainContext") + PageParameters.getParameter("ajaxFuntions")%>/compruebaFichaTecnica.jsp"
                        , data: '<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>&idPlantel=' + idPlantel
                        , success: function (response) {
                            $('#wrapper').find('#divBody').find('#bodySelectPlantel').find('#selectPlantel').find('#divDataSheet').html(response);
                        }});
                
            }
             
        </script>
        <%@ include file="/gui/pageComponents/dataTablesFullFunctionParameters.jsp"%>
    </head>
    <body  onload="cargaTabla();">
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
                            >
                            <a class="NVL" href="<%=PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui")%>/plantelSelect.jsp?<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>"> Ficha Técnica</a>
                            >
                            <a> Consulta Etapas</a>
                        </td>
                        <td width="36" align="right" valign="top">
                            <script language="JavaScript" src="<%=PageParameters.getParameter("jsRcs")%>/funcionDate.js" type="text/javascript"></script>
                        </td>
                    </tr>                        
                </table>
                <br>
                <br>
                <div id="contenent_info">
                           <form name="usuario" method="post" action="" enctype="application/x-www-form-urlencoded" id="form">
                               <legend align="center" ><%=QUID.selectNombrePlantel(idPlantel) %></legend>
                        <br>
                            <fieldset>
                                <legend align="center">Etapas de Desarrollo</legend>
                                <div style="margin-left: 2%; margin-right: 2%; font-size: 12px; ">
                                    <table border="0" width="100%" cellpadding="0" cellspacing="0" style="text-align: left" class="display" id="example">
                                    <thead>
                                        <tr>
                                            <th style="text-align: center">N° Etapa</th>
                                            <th style="text-align: center">Año Construcción</th>
                                            <th style="text-align: center">Nombre Etapa</th>
                                            <th style="text-align: center">Descripcción</th>
                                            <th style="text-align: center">Status</th>
                                            <th style="text-align: center">Avance %</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody style="text-align: center">
                                    </tbody>
                                </table>
                                </div>
                                
                            </fieldset>
                        </form>
                </div>
                <br>
                <br>
                <div id="botonEnviarDiv"  style="margin-left: 5%">
                          
                    <a href="<%=PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui")%>/agregaEtapaDesarrollo.jsp?<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>&<%=WebUtil.encode(session, "idPlantel") %>=<%=request.getParameter("idPlantel")%>">
                               <button class="btn btn-default">Agregar Etapa de Desarrollo</button>
                           </a>
                          
                            </div> 
                 <div id="divFoot">
                    <jsp:include page='<%=(PageParameters.getParameter("footer"))%>' />
                </div> 
                <script type="text/javascript" language="javascript" charset="utf-8">
        function cargaTabla()
        {
            var t = $('#example').dataTable();
            t.fnClearTable();
            var data = new Array();
            var ids = new Array();
            var cells = new Array();

                    <%
                        LinkedList listAux= new LinkedList();
                        int cont = 0;
                        it = QUID.selectEtapasDesarrollo(idPlantel).iterator();
                        while (it.hasNext()) {
                            listAux = (LinkedList) it.next();
                    %>
            ids[<%=cont%>] = '<%=listAux.get(0)%>';
            cells[0] = '<%=listAux.get(0).toString()%>';
            cells[1] = '<%=listAux.get(1).toString()%>';
            cells[2] = '<%=listAux.get(2).toString()%>';
            cells[3] = '<%=listAux.get(3).toString()%>';
            cells[4] = '<%=listAux.get(4).toString()%>';
            cells[5] = '<%=listAux.get(5).toString()%>';
            cells[6] = '<a href="<%=PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui")%>/agregaActividad.jsp?<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>&idPlantel=<%=WebUtil.encode(session, idPlantel)%>"><img src="<%=PageParameters.getParameter("imgRsc")%>/icons/Gnome-Accessories-Text-Editor-64.png" title="Actividades" width="22" height="23" alt="Actividad"></a>';
                        data[<%=cont%>] = t.fnAddData(cells, false);
                    <%
                            cont++;
                        }
                    %>

            $("tfoot th").each(function (i) {
                this.innerHTML = fnCreateSelect(t.fnGetColumnData(i));
                $('select', this).change(function () {
                    t.fnFilter($(this).val(), i);
                });
            });
            t.fnDraw();
            document.getElementById('contenent_info').style.display = 'block';
        }
                </script>
            </div>
        </div>
                     
    </body>
</html>

<%
  
    }
        
} else {
    //Mensaje de Error para permisos de acceso a la pagina.
%>                
<%@ include file="/gui/pageComponents/invalidUser.jsp"%>
<%    }
} else {
    //Mensaje de Error para sesiones caducadas 
%>
<%@ include file="/gui/pageComponents/invalidParameter.jsp"%>
<%        }
    }
} catch (Exception ex) {
    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
    //Mensaje de Error para los errores en servidor.
%>

<%@ include file="/gui/pageComponents/handleUnExpectedError.jsp"%>
</body>
</html>
<%
        //response.sendRedirect(redirectURL);
    }
%>
