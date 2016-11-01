<%-- 
    Document   : agregaActividad
    Created on : Oct 28, 2016, 4:40:13 PM
    Author     : emmanuel
--%>

<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
 <link href="../rsc/css/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css" /> 
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>

<%    try {
        if (fine) {
             
            if (request.getParameter(WebUtil.encode(session, "imix")) != null) {
                String access4ThisPage = "addActivity";
                LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
                if (UserUtil.isAValidUser(access4ThisPage, userAccess)) {
                    if (PageParameters.getParameter("SiteOnMaintenance").equals("true")) {
                        String redirectURL = "" + PageParameters.getParameter("mainController") + "?exit=1";
                        response.sendRedirect(redirectURL);
                    } else {
                       
                        int idPlantel= Integer.parseInt(WebUtil.decode(session, request.getParameter(WebUtil.encode(session, "idPlantel"))));
                         int idEtapa= Integer.parseInt(WebUtil.decode(session,  request.getParameter(WebUtil.encode(session, "idEtapa"))));
%>
<!DOCTYPE html>

<html lang="<%=PageParameters.getParameter("Content-Language")%>">
    <head>
        <title>Actividades</title>
        <jsp:include page='<%=PageParameters.getParameter("globalLibs")%>'/>        
        <jsp:include page='<%=PageParameters.getParameter("styleFormCorrections")%>'/>
        <script type="text/javascript" language="javascript" charset="utf-8">
            window.history.forward();
            function noBack() {
                window.history.forward();
            }
            function resetForm() {
                document.getElementById("valueNombreEtapa").value='';
                document.getElementById("valueDescripccionEtapa").value='';
                document.getElementById("valueFechaInicioEtapa").value='';
                document.getElementById("valueFechaFinEtapa").value='';
                document.getElementById("valueStatusEtapa").value='';
                document.getElementById("valueTipoEtapa").value='';
            }
            function enviarInfocontroller() {
                $.ajax({type: 'POST'
                    , contentType: 'application/x-www-form-urlencoded;charset=utf-8'
                    , cache: false
                    , async: false
                    , url: '<%=PageParameters.getParameter("mainController")%>'
                    , data: $('#agregaActividad').serialize()
                    , success: function (response) {
                        $('#wrapper').find('#divResult').html(response);
                    }});
            }
           </script>
      
      <link href="../rsc/css/styleAddActivity.css" rel="stylesheet" type="text/css" /> 
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
                            >
                            <a class="NVL" href="<%=PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui")%>/plantelSelect.jsp?<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>"> Ficha Técnica</a>
                            >
                            <a class="NVL" href="<%=PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui")%>/consultaEtapaDesarrollo.jsp?<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>&idPlantel=<%=WebUtil.encode(session, idPlantel)%>">Consulta Etapas</a>
                            >
                             <a class="NVL" href="<%=PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui")%>/consultaActividad.jsp?<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>&idPlantel=<%=WebUtil.encode(session, idPlantel)%>&idEtapa=<%=WebUtil.encode(session, idEtapa)%>">Consulta Actividades</a>
                            >
                            <a> Agregar Actividad</a>
                        </td>
                        <td width="36" align="right" valign="top">
                            <script language="JavaScript" src="<%=PageParameters.getParameter("jsRcs")%>/funcionDate.js" type="text/javascript"></script>
                        </td>
                    </tr>                        
                </table>
                <br>
                <br>
                <div id="bodyagregaActividad">
                    <form id="agregaActividad" name="agregaActividad">
                        <input type="hidden" name="FormForm" value="agregaActivity"/>
                       <input type="hidden" name="idPlantel" value="<%=WebUtil.encode(session, idPlantel) %>"/>
                        <input type="hidden" name="sesion" value="<%=WebUtil.encode(session, "imix") %>"/>
                         <input type="hidden" name="idEtapa" value="<%=WebUtil.encode(session, idEtapa) %>"/>
                        <legend align="center" ><%=QUID.selectNombrePlantel(idPlantel) %></legend>
                        <br>
                        <fieldset id="fieldDatosActividad" name="fieldDatosActividad"style="margin-left: 3%; margin-bottom: 5%; margin-right: 3%;">
                           <legend  id="tituloActividad"  align="center">Nueva Actividad</legend>
                           <br>
                          
                           
                        <div id="divActividad" name="divActividad">
                            <table>
                                
                                <tr>
                                    <td> 
                                        <label id="labelNombreActividad"class="">Nombre</label>
                                        <input id="valueNombreActividad" class="form-control"  name="valueNombreActividad" title="Nombre de Actividad" placeholder="Nombre de Actividad">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelDescripccionActividad"class="">Descripcción  </label>
                                        <input id="valueDescripccionActividad" class="form-control"  name="valueDescripccionActividad" value="" title="Descripcción de la Etapa" placeholder="Descripccion de la Etapa">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    
                                    <td> 
                                        <label id="labelCantidad"class="firstLabelDataSheet">Construcción de Espacios</label>
                                        <input name="valueCantidad" class="form-control" id="valueCantidad" value="" size="20" placeholder="Cantidad de Espacios en Construir" title="Espacios en Construcción">                    
                                    </td>
                                    
                                     
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelCostoOperacion"class="">Costo de Operación $</label>
                                        <input id="valueCostoOperacion" class="form-control" name="valueCostoOperacion" value="" title="Costo de Actividad" placeholder="0.00">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelResponsable"class="">Responsable</label>
                                        <input id="valueResponsable" class="form-control" name="valueResponsable" value="" title="Responsable de Act." placeholder="Responsable de la Actividad">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelStatusActividad" class="">Estatus </label>
                                        <select id="valueStatusActividad"  class="form-control" name="valueStatusActividad" >
                                            <option value="" selected > </option>
                                            <option value="Proceso">Proceso</option>
                                            <option value="Completa">Completa</option>
                                            <option value="Pausada">Cancelada</option>
                                        </select>
                                    </td>
                                   
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelFechaInicioActividad"class="firstLabelDataSheet">Fecha de Inicio  </label>
                                        <input id="valueFechaInicioActividad"  name="valueFechaInicioActividad"class="form-control" value="" title="Fecha de Inicio de la Actividad" placeholder="YYYY-MM-DD">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelFechaFinActividad"class="firstLabelDataSheet">Fecha de Finalización  </label>
                                        <input id="valueFechaFinActividad"  name="valueFechaFinActividad"class="form-control" value="" title="Fecha de Finalización de la Actividad" placeholder="YYYY-MM-DD">                              
                                    </td>
                                   
                                </tr>
                               
                                 <tr>
                                    <td> 
                                        <label id="labelAvanceActividad"class="firstLabelDataSheet">Avance </label>
                                        <input id="valueAvanceActividad" class="form-control"  name="valueAvanceActividad"c value="" title="Avance de la Actividad" placeholder="0.0 %">                                                                 </td>
                                   
                                </tr>
                            </table>
                            
                        </div>
                            
                            </fieldset>
                         <div id="botonEnviarDiv">
                             <input id="addActividad" type="button" class="btn btn-default" value="Agregar Actividad" name="addActividad" onclick="enviarInfocontroller()"/>
                           
                        </div> 
                    </form>
                   <div id="divResult"> 
                    
                   </div>  
                        <div id="divFoot">
                    <jsp:include page='<%=(PageParameters.getParameter("footer"))%>' />
                </div> 
                </div>
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