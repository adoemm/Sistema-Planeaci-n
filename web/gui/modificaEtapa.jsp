<%-- 
    Document   : modificaEtapa
    Created on : Nov 3, 2016, 12:05:29 PM
    Author     : emmanuel
--%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
 <link href="../rsc/css/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css" /> 
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>

<%    try {
        if (fine) {
             
            if (request.getParameter(WebUtil.encode(session, "imix")) != null) {
                String access4ThisPage = "updateStage";
                LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
                if (UserUtil.isAValidUser(access4ThisPage, userAccess)) {
                    if (PageParameters.getParameter("SiteOnMaintenance").equals("true")) {
                        String redirectURL = "" + PageParameters.getParameter("mainController") + "?exit=1";
                        response.sendRedirect(redirectURL);
                    } else {
                       
                        int idPlantel= Integer.parseInt(WebUtil.decode(session, request.getParameter("idPlantel")));
                        int idEtapa= Integer.parseInt(WebUtil.decode(session, request.getParameter("idEtapa")));
                        LinkedList infoDataStage= QUID.selectDataToStage(idEtapa);
%>
<!DOCTYPE html>

<html lang="<%=PageParameters.getParameter("Content-Language")%>">
    <head>
        <title>Modificación de Etapas de Desarrollo</title>
        <jsp:include page='<%=PageParameters.getParameter("globalLibs")%>'/>        
        <jsp:include page='<%=PageParameters.getParameter("styleFormCorrections")%>'/>
        <script type="text/javascript" language="javascript" charset="utf-8">
            window.history.forward();
            function noBack() {
                window.history.forward();
            }
           
            function enviarInfocontroller() {
                $.ajax({type: 'POST'
                    , contentType: 'application/x-www-form-urlencoded;charset=utf-8'
                    , cache: false
                    , async: false
                    , url: '<%=PageParameters.getParameter("mainController")%>'
                    , data: $('#modificaEtapaDesarrollo').serialize()
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
                            <a> Modifica Etapa de Desarrollo</a>
                        </td>
                        <td width="36" align="right" valign="top">
                            <script language="JavaScript" src="<%=PageParameters.getParameter("jsRcs")%>/funcionDate.js" type="text/javascript"></script>
                        </td>
                    </tr>                        
                </table>
                <br>
                <br>
                <div id="bodyModificaEtapaDesarrollo">
                    <form id="modificaEtapaDesarrollo" name="modificaEtapaDesarrollo">
                        <input type="hidden" name="FormForm" value="modificaEtapaDesarrollo"/>
                       <input type="hidden" name="idPlantel" value="<%=WebUtil.encode(session, idPlantel) %>"/>
                        <input type="hidden" name="sesion" value="<%=WebUtil.encode(session, "imix") %>"/>
                         <input type="hidden" name="idEtapa" value="<%=WebUtil.encode(session, idEtapa) %>"/>
                        <legend align="center" ><%=QUID.selectNombrePlantel(idPlantel) %></legend>
                        <br>
                        <fieldset id="fieldDatosEtapaDesarrollo" name="fieldDatosEtapaDesarrollo"style="margin-left: 3%; margin-bottom: 5%; margin-right: 3%;">
                           <legend  id="tituloEtapaDesarrollo"  align="center">Modificación de Etapa de Desarrollo</legend>
                           <br>
                          
                           
                        <div id="divEtapaDesarrollo" name="divEtapaDesarrollo">
                            <table>
                                
                               
                                <tr>
                                    <td> 
                                        <label id="labelNombreEtapa"class="firstLabelDataSheet">Nombre de la Etapa  </label>
                                        <input id="valueNombreEtapa" class="form-control"  name="valueNombreEtapa"class="form-control, InputDataSheet" value="<%=infoDataStage.size()>0 ? infoDataStage.get(0).toString(): "" %>" title="Nombre de la Etapa" placeholder="Escriba Nombre de la Etapa">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    
                                    <td> 
                                        <label id="labelDescripccionEtapa"class="firstLabelDataSheet">Descripcción de la Etapa</label>
                                        <textarea name="valueDescripcionStage" class="form-control" id="valueDescripcionStage" size="20" placeholder="Descripcción Breve de la Etapa" title="Descripcción"><%=infoDataStage.size()>0 ? infoDataStage.get(1).toString(): "" %></textarea>                    
                                    </td>
                                    
                                     
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelFechaInicioEtapa"class="firstLabelDataSheet">Fecha de Inicio  </label>
                                        <input id="valueFechaInicioEtapa" class="form-control" name="valueFechaInicioEtapa"class="form-control" value="<%=infoDataStage.size()>0 ? infoDataStage.get(2).toString(): "" %>" title="Fecha de Inicio de la Etapa" placeholder="YYYY-MM-DD">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelFechaFinEtapa"class="firstLabelDataSheet">Fecha de Fin  </label>
                                        <input id="valueFechaFinEtapa"  name="valueFechaFinEtapa"class="form-control" value="<%=infoDataStage.size()>0 ? infoDataStage.get(3).toString(): "" %>" title="Fecha de Finalización de la Etapa" placeholder="YYYY-MM-DD">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelStatusEtapa" class="labelInfraestructura">Estatus </label>
                                        <select id="valueStatusEtapa"  class="form-control" name="valueStatusEtapa" class="form-control, valueFirstColumnInfraestructura"   >
                                            <% String value= infoDataStage.get(4).toString();
                                                boolean flag1=false;
                                                 boolean  flag2=false;
                                                 boolean flag3=false;
                                                 boolean flag4=false;
                                                if(value.equals(""))
                                                {
                                                    flag1=true;
                                                }else if(value.equals("Proceso"))
                                                {
                                                    flag2=true;
                                                }else if(value.equals("Completada"))
                                                {
                                                    flag3=true;
                                                }else if(value.equals("Cancelada"))
                                                {
                                                    flag4=true;
                                                }
                                            %>
                                            <option value="" <%if(flag1==true) { %>selected<%}%>> </option>
                                            <option value="Proceso" <%if(flag2==true) { %>selected<%}%>>Proceso</option>
                                            <option value="Completada" <%if(flag3==true) { %>selected<%}%>>Completada</option>
                                            <option value="Cancelada" <%if(flag4==true) { %>selected<%}%>>Cancelada</option>
                                        </select>
                                    </td>
                                   
                                </tr>
                                <tr>
                                    
                                    <td> 
                                        <label id="labelTipoEtapa"class="firstLabelDataSheet">Tipo de Etapa</label>
                                        <input id="valueTipoEtapa" class="form-control" name="valueTipoEtapa" class="form-control, InputDataSheet"  value="<%=infoDataStage.size()>0 ? infoDataStage.get(5).toString(): "" %>" title="Tipo de Etapa" placeholder="Equipamiento, Contrucción, etc.">                       
                                    </td>
                                    
                                     
                                </tr>
                                 <tr>
                                    <td> 
                                        <label id="labelNumeroActividades"class="firstLabelDataSheet">N° de Actividades  </label>
                                        <input id="valueNumeroActividades" class="form-control"  name="valueNumeroActividades"class="form-control, InputDataSheet" value="<%=infoDataStage.size()>0 ? infoDataStage.get(6).toString(): "" %>"title="Número de Actividades" placeholder="N° Act. para la Etapa">                                                                 </td>
                                   
                                </tr>
                            </table>
                            
                        </div>
                            
                            </fieldset>
                         <div id="botonEnviarDiv">
                             <input id="updateEtapaDesarrollo" type="button" class="btn btn-default" value="Modificar Etapa" name="addEtupdateEtapaDesarrolloapaDesarrollo" onclick="enviarInfocontroller()"/>
                           
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