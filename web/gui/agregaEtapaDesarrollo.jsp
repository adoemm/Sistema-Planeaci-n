<%-- 
    Document   : agregaEtapaDesarrollo
    Created on : Oct 14, 2016, 12:03:21 PM
    Author     : emmanuel
--%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
 <link href="../rsc/css/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css" /> 
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>

<%    try {
        if (fine) {
             
            if (request.getParameter(WebUtil.encode(session, "imix")) != null) {
                String access4ThisPage = "addStage";
                LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
                if (UserUtil.isAValidUser(access4ThisPage, userAccess)) {
                    if (PageParameters.getParameter("SiteOnMaintenance").equals("true")) {
                        String redirectURL = "" + PageParameters.getParameter("mainController") + "?exit=1";
                        response.sendRedirect(redirectURL);
                    } else {
                       
                        int idPlantel= Integer.parseInt(WebUtil.decode(session, request.getParameter(WebUtil.encode(session, "idPlantel"))));
                        
%>
<!DOCTYPE html>

<html lang="<%=PageParameters.getParameter("Content-Language")%>">
    <head>
        <title>Etapas de Desarrollo</title>
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
                    , data: $('#agregaEtapaDesarrollo').serialize()
                    , success: function (response) {
                        $('#wrapper').find('#divResult').html(response);
                    }});
            }
           </script>
      <link href="../rsc/css/styleDataSheet.css" rel="stylesheet" type="text/css" /> 
      <link href="../rsc/css/styleAddStage.css" rel="stylesheet" type="text/css" /> 
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
                            <a> Agregar Etapa de Desarrollo</a>
                        </td>
                        <td width="36" align="right" valign="top">
                            <script language="JavaScript" src="<%=PageParameters.getParameter("jsRcs")%>/funcionDate.js" type="text/javascript"></script>
                        </td>
                    </tr>                        
                </table>
                <br>
                <br>
                <div id="bodyagregaEtapaDesarrollo">
                    <form id="agregaEtapaDesarrollo" name="agregaEtapaDesarrollo">
                        <input type="hidden" name="FormForm" value="agregaEtapaDesarrollo"/>
                       <input type="hidden" name="idPlantel" value="<%=WebUtil.encode(session, idPlantel) %>"/>
                        <input type="hidden" name="sesion" value="<%=WebUtil.encode(session, "imix") %>"/>
                         
                        <legend align="center" ><%=QUID.selectNombrePlantel(idPlantel) %></legend>
                        <br>
                        <fieldset id="fieldDatosEtapaDesarrollo" name="fieldDatosEtapaDesarrollo"style="margin-left: 3%; margin-bottom: 5%; margin-right: 3%;">
                           <legend  id="tituloEtapaDesarrollo"  align="center">Nueva Etapa de Desarrollo</legend>
                           <br>
                          
                           
                        <div id="divEtapaDesarrollo" name="divEtapaDesarrollo">
                            <table>
                                
                               
                                <tr>
                                    <td> 
                                        <label id="labelNombreEtapa"class="firstLabelDataSheet">Nombre de la Etapa  </label>
                                        <input id="valueNombreEtapa" class="form-control"  name="valueNombreEtapa"class="form-control, InputDataSheet" value="" title="Nombre de la Etapa" placeholder="Escriba Nombre de la Etapa">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    
                                    <td> 
                                        <label id="labelDescripccionEtapa"class="firstLabelDataSheet">Descripcción de la Etapa</label>
                                        <textarea name="valueDescripcionStage" class="form-control" id="valueDescripcionStage" value="" size="20" placeholder="Descripcción Breve de la Etapa" title="Descripcción"></textarea>                    
                                    </td>
                                    
                                     
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelFechaInicioEtapa"class="firstLabelDataSheet">Fecha de Inicio  </label>
                                        <input id="valueFechaInicioEtapa" class="form-control" name="valueFechaInicioEtapa"class="form-control" value="" title="Fecha de Inicio de la Etapa" placeholder="YYYY-MM-DD">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelFechaFinEtapa"class="firstLabelDataSheet">Fecha de Fin  </label>
                                        <input id="valueFechaFinEtapa"  name="valueFechaFinEtapa"class="form-control" value="" title="Fecha de Finalización de la Etapa" placeholder="YYYY-MM-DD">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelStatusEtapa" class="labelInfraestructura">Estatus </label>
                                        <select id="valueStatusEtapa"  class="form-control" name="valueStatusEtapa" class="form-control, valueFirstColumnInfraestructura"   >
                                            <option value="" selected > </option>
                                            <option value="Proceso">Proceso</option>
                                            <option value="Completada">Completada</option>
                                            <option value="Cancelada">Cancelada</option>
                                        </select>
                                    </td>
                                   
                                </tr>
                                <tr>
                                    
                                    <td> 
                                        <label id="labelTipoEtapa"class="firstLabelDataSheet">Tipo de Etapa</label>
                                        <input id="valueTipoEtapa" class="form-control" name="valueTipoEtapa" class="form-control, InputDataSheet"  value="" title="Tipo de Etapa" placeholder="Equipamiento, Contrucción, etc.">                       
                                    </td>
                                    
                                     
                                </tr>
                                 <tr>
                                    <td> 
                                        <label id="labelNumeroActividades"class="firstLabelDataSheet">N° de Actividades  </label>
                                        <input id="valueNumeroActividades" class="form-control"  name="valueNumeroActividades"class="form-control, InputDataSheet" value="" title="Número de Actividades" placeholder="N° Act. para la Etapa">                                                                 </td>
                                   
                                </tr>
                            </table>
                            
                        </div>
                            
                            </fieldset>
                         <div id="botonEnviarDiv">
                             <input id="addEtapaDesarrollo" type="button" class="btn btn-default" value="Agregar Etapa" name="addEtapaDesarrollo" onclick="enviarInfocontroller()"/>
                           
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