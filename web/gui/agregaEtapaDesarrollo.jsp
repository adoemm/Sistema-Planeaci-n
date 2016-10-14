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
             
            if (request.getParameter("sesion") != null) {
                String access4ThisPage = "accessToAddDataSheet";
                LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
                if (UserUtil.isAValidUser(access4ThisPage, userAccess)) {
                    if (PageParameters.getParameter("SiteOnMaintenance").equals("true")) {
                        String redirectURL = "" + PageParameters.getParameter("mainController") + "?exit=1";
                        response.sendRedirect(redirectURL);
                    } else {
                        
                        int idPlantel= Integer.parseInt(WebUtil.decode(session, request.getParameter("idPlantel")));
                        
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
           </script>
      <link href="../rsc/css/styleDataSheet.css" rel="stylesheet" type="text/css" /> 
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
                            <a class="NVL" href="<%=PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui")%>/agregaFichaTecnica.jsp?<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%><%=WebUtil.encode(session, "&idPlantel")%>=<%=idPlantel%>"> Agregar Ficha Técnica</a>
                            <a>> Agregar Etapa de Desarrollo</a>
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
                        <input type="hidden" name="FormForm" value="agregaFichaTecnica"/>
                       <input type="hidden" name="idPlantel" value="<%=WebUtil.encode(session, idPlantel) %>"/>
                        <input type="hidden" name="sesion" value="<%=WebUtil.encode(session, "imix") %>"/>
                        <fieldset id="fieldDatosEtapaDesarrollo" name="fieldDatosEtapaDesarrollo"style="margin-left: 3%; margin-bottom: 5%; margin-right: 3%;">
                           <legend  id="tituloEtapaDesarrollo"  align="center">Nueva Etapa de Desarrollo</legend>
                        <div id="divEtapaDesarrollo" name="divEtapaDesarrollo">
                            <table>
                                <tr>
                                    <td> 
                                        <label id="labelNombreEtapa"class="firstLabelDataSheet">Nombre de la Etapa  </label>
                                        <input id="valueNombreEtapa" name="valueNombreEtapa"class="form-control, InputDataSheet" value="" title="Nombre de la Etapa">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    
                                    <td> 
                                        <label id="labelDescripccionEtapa"class="firstLabelDataSheet">Descripcción de la Etapa</label>
                                        <input id="valueDescripccionEtapa" name="valueDescripccionEtapa" class="form-control, InputDataSheet"  value="" title="Descripcción Etapa">                       
                                    </td>
                                    
                                     
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelFechaInicioEtapa"class="firstLabelDataSheet">Fecha de Inicio  </label>
                                        <input id="valueFechaInicioEtapa" name="valueFechaInicioEtapa"class="form-control, InputDataSheet" value="" title="Nombre de la Etapa">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelFechaFinEtapa"class="firstLabelDataSheet">Fecha de Fin  </label>
                                        <input id="valueFechaFinEtapa" name="valueFechaFinEtapa"class="form-control, InputDataSheet" value="" title="Nombre de la Etapa">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelStatus" class="labelInfraestructura">Status </label>
                                        <select id="valueStatus" name="valueStatus" class="form-control, valueFirstColumnInfraestructura" >
                                            <option value="" selected > </option>
                                            <option value="Proceso">Proceso</option>
                                            <option value="Completa">Completa</option>
                                            <option value="Pausada">Pausada</option>
                                        </select>
                                    </td>
                                   
                                </tr>
                                <tr>
                                    
                                    <td> 
                                        <label id="labelTipoEtapa"class="firstLabelDataSheet">Descripcción de la Etapa</label>
                                        <input id="valueTipoEtapa" name="valueTipoEtapa" class="form-control, InputDataSheet"  value="" title="Descripcción Etapa">                       
                                    </td>
                                    
                                     
                                </tr>
                            </table>
                            
                        </div>
                            
                            </fieldset>
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