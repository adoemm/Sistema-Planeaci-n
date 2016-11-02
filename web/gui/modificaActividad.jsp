<%-- 
    Document   : modificaActividad
    Created on : Nov 1, 2016, 12:53:12 PM
    Author     : emmanuel
--%>

<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
 <link href="../rsc/css/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css" /> 
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>

<%    try {
        if (fine) {
             
            if (request.getParameter(WebUtil.encode(session, "imix")) != null) {
                String access4ThisPage = "updateActivity";
                LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
                if (UserUtil.isAValidUser(access4ThisPage, userAccess)) {
                    if (PageParameters.getParameter("SiteOnMaintenance").equals("true")) {
                        String redirectURL = "" + PageParameters.getParameter("mainController") + "?exit=1";
                        response.sendRedirect(redirectURL);
                    } else {
                       
                        int idPlantel= Integer.parseInt(WebUtil.decode(session, request.getParameter("idPlantel")));
                         int idEtapa= Integer.parseInt(WebUtil.decode(session,  request.getParameter("idEtapa")));
                         int idActividad= Integer.parseInt(WebUtil.decode(session,  request.getParameter("idActividad")));
                         LinkedList infoDataActivity= QUID.selectDataToActivity(idActividad);
                         
%>
<!DOCTYPE html>

<html lang="<%=PageParameters.getParameter("Content-Language")%>">
    <head>
        <title>Modifica Actividad</title>
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
                    , data: $('#modificaActividad').serialize()
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
                            <a> Modifica Actividad</a>
                        </td>
                        <td width="36" align="right" valign="top">
                            <script language="JavaScript" src="<%=PageParameters.getParameter("jsRcs")%>/funcionDate.js" type="text/javascript"></script>
                        </td>
                    </tr>                        
                </table>
                <br>
                <br>
                <div id="bodymodificaActividad">
                    <form id="modificaActividad" name="modificaActividad">
                        <input type="hidden" name="FormForm" value="modificaActivity"/>
                       <input type="hidden" name="idPlantel" value="<%=WebUtil.encode(session, idPlantel) %>"/>
                        <input type="hidden" name="sesion" value="<%=WebUtil.encode(session, "imix") %>"/>
                         <input type="hidden" name="idEtapa" value="<%=WebUtil.encode(session, idEtapa) %>"/>
                        <legend align="center" ><%=QUID.selectNombrePlantel(idPlantel) %></legend>
                        <br>
                        <fieldset id="fieldDatosActividad" name="fieldDatosActividad"style="margin-left: 3%; margin-bottom: 5%; margin-right: 3%;">
                           <legend  id="tituloActividad"  align="center">Actividad  a Modificar</legend>
                           <br>
                          
                           
                        <div id="divActividad" name="divActividad">
                            <table>
                                
                                <tr>
                                    <td> 
                                        <label id="labelNombreActividad" class="labelsAddActivity">Nombre</label>
                                        <input id="valueNombreActividad" class="form-control"  name="valueNombreActividad" title="Nombre de Actividad" placeholder="Nombre de Actividad" value="<%=infoDataActivity.size()>0 ? infoDataActivity.get(0).toString(): "" %>">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelDescripccionActividad"class="labelsAddActivity">Descripcción  </label>
                                        <textarea id="valueDescripccionActividad" class="form-control"  name="valueDescripccionActividad"  title="Descripcción de la Etapa" placeholder="Descripccion de la Etapa"><%=infoDataActivity.size()>0 ? infoDataActivity.get(1).toString(): "" %></textarea>                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    
                                    <td> 
                                        <label id="labelCantidad"class="labelsAddActivity">Construcción de Espacios</label>
                                        <input name="valueCantidadActividad" class="form-control" id="valueCantidadActividad" value="<%=infoDataActivity.size()>0 ? infoDataActivity.get(2).toString(): "" %>"size="20" placeholder="Cantidad de Espacios en Construir" title="Espacios en Construcción">                    
                                    </td>
                                    
                                     
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelCostoOperacion"class="labelsAddActivity">Costo de Operación $</label>
                                        <input id="valueCostoOperacionActividad" class="form-control" name="valueCostoOperacionActividad" value="<%=infoDataActivity.size()>0 ? infoDataActivity.get(3).toString(): "" %>" title="Costo de Actividad" placeholder="0.00">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelResponsable"class="labelsAddActivity">Responsable</label>
                                        <input id="valueResponsableActividad" class="form-control" name="valueResponsableActividad" value="<%=infoDataActivity.size()>0 ? infoDataActivity.get(4).toString(): "" %>" title="Responsable de Act." placeholder="Responsable de la Actividad">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelStatusActividad" class="labelsAddActivity">Estatus </label>
                                        <select id="valueStatusActividad"  class="form-control" name="valueStatusActividad" value="<%=infoDataActivity.size()>0 ? infoDataActivity.get(5).toString(): "" %>">
                                            <% String value= infoDataActivity.get(5).toString();
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
                                        <label id="labelFechaInicioActividad"class="labelsAddActivity">Fecha de Inicio  </label>
                                        <input id="valueFechaInicioActividad"  name="valueFechaInicioActividad"class="form-control" value="<%=infoDataActivity.size()>0 ? infoDataActivity.get(6).toString(): "" %>" title="Fecha de Inicio de la Actividad" placeholder="YYYY-MM-DD">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelFechaFinActividad"class="labelsAddActivity">Fecha de Finalización  </label>
                                        <input id="valueFechaFinActividad"  name="valueFechaFinActividad"class="form-control"value="<%=infoDataActivity.size()>0 ? infoDataActivity.get(7).toString(): "" %>" title="Fecha de Finalización de la Actividad" placeholder="YYYY-MM-DD">                              
                                    </td>
                                   
                                </tr>
                               
                                 <tr>
                                    <td> 
                                        <label id="labelAvanceActividad"class="labelsAddActivity">Avance </label>
                                        <input id="valueAvanceActividad" class="form-control"  name="valueAvanceActividad"c value="<%=infoDataActivity.size()>0 ? infoDataActivity.get(8).toString(): "" %>" title="Avance de la Actividad" placeholder="0.0 %">                                                                 </td>
                                   
                                </tr>
                            </table>
                            
                        </div>
                            
                            </fieldset>
                         <div id="botonEnviarDiv">
                             <input id="modificarActividad" type="button" class="btn btn-default" value="Modificar Actividad" name="modificarActividad" onclick="enviarInfocontroller()"/>
                           
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