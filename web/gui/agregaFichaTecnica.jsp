<%-- 
    Document   : addDataSheet
    Created on : Oct 6, 2016, 5:55:06 PM
    Author     : emmanuel
--%>

<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
 <link href="../rsc/css/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css" /> 
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>





<%    try {
        if (fine) {
            if (request.getParameter(WebUtil.encode(session, "imix")) != null) {
                String access4ThisPage = "accessToAddDataSheet";
                LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
                if (UserUtil.isAValidUser(access4ThisPage, userAccess)) {
                    if (PageParameters.getParameter("SiteOnMaintenance").equals("true")) {
                        String redirectURL = "" + PageParameters.getParameter("mainController") + "?exit=1";
                        response.sendRedirect(redirectURL);
                    } else {
                        
%>
<!DOCTYPE html>
<!DOCTYPE html>
<html lang="<%=PageParameters.getParameter("Content-Language")%>">
    <head>
        <title>Ficha Tecnica</title>
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
                            <a>> Agregar Ficha Técnica</a>
                        </td>
                        <td width="36" align="right" valign="top">
                            <script language="JavaScript" src="<%=PageParameters.getParameter("jsRcs")%>/funcionDate.js" type="text/javascript"></script>
                        </td>
                    </tr>                        
                </table>
                <br>
                <br>
                <div id="bodyagregaFichaTecnica">
                    <form id="agregaFichaTecnica" name="agregaFichaTecnica">
                        <fieldset  style="margin-left: 3%; margin-bottom: 5%; margin-right: 3%;">
                            <legend align="center">Datos Generales</legend>
                        <div id="divDatosGenerales" name="divDatosGenerales">
                            <table>
                                <tr>
                                    <td> 
                                        <label id="labelNombrePlantel"class="firstLabelDataSheet">Nombre del Plantel  </label>
                                        <input id="valueNombrePlantel" class="form-control, InputDataSheet">                              
                                    </td>
                                    <td> 
                                        <label id="labelDireccion" class="labelSecondColumnDatosGenerales">Dirección</label>
                                        <input id="valueDireccion" class="form-control, InputDataSheet">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    
                                    <td> 
                                        <label id="laberMunicipio"class="firstLabelDataSheet">Municipio</label>
                                        <input id="valueMunicipio" class="form-control, InputDataSheet">                              
                                    </td>
                                    <td> 
                                        <label id="labelEstado" class="labelSecondColumnDatosGenerales">Estado</label>
                                        <input id="valueEstado" class="form-control, InputDataSheet, valuesSecondColumnDatosGenerales">                              
                                    </td>
                                     
                                </tr>
                                <tr>
                                   
                                    <td> 
                                        <label id="labelCCT"class="firstLabelDataSheet">C.C.T</label>
                                        <input id="valueCCT" class="form-control, InputDataSheet">                              
                                    </td>
                                    <td> 
                                        <label id="labelAnioCreacion" class="labelSecondColumnDatosAcademicos">Año de Creación</label>
                                        <input id="valueAnioCreacion" class="form-control, InputDataSheet, valuesSecondColumnDatosGenerales">                              
                                    </td>
                                </tr>
                                <tr>
                                   <td> 
                                       <label id="labelTelefono" class="firstLabelDataSheet">Teléfono</label>
                                       <input id="valueTelefono" class="form-control, InputDataSheet">                              
                                    </td>
                                    <td> 
                                        <label id="labelCorreo" class="labelSecondColumnDatosGenerales">Correo</label>
                                        <input id="valueCorreo" class="form-control, InputDataSheet, valuesSecondColumnDatosGenerales">                              
                                    </td>
                                </tr>
                                
                                <tr>
                                    <td> 
                                        <label  id="labelLatitud"class="firstLabelDataSheet">Latitud</label>
                                        <input id="valueLatitud" class="form-control, InputDataSheet">                              
                                    </td>
                                    <td> 
                                        <label id="labelLongitud" class="labelSecondColumnDatosGenerales">Longitud</label>
                                        <input id="valueLongitud" class="form-control, InputDataSheet, valuesSecondColumnDatosGenerales">                              
                                    </td>
                                   
                                </tr>
                            </table>
                            
                        </div>
                            
                            </fieldset>
                        <fieldset  style="margin-left: 3%; margin-bottom: 5%; margin-right: 3%;">
                            <legend align="center">Datos Academicos</legend>
                            <div id="divDatosAcademicos" name="divDatosAcademicos">
                            <table>
                                <tr>
                                    <td> 
                                        <label id="labelDirector" class="firstLabelDataSheet">Director</label>
                                        <input id="valueDirector" class="form-control, InputDataSheet">                              
                                    </td>
                                   <td> 
                                        <label id="labelPersonalAdmin" class="labelSecondColumnDatosAcademicos">Personal Administrativo</label>
                                        <input id="valuePersonalAdmin" class="form-control, InputDataSheet">                              
                                    </td>
                                    <td> 
                                        <label id="labelDocentes" class="">Docentes</label>
                                        <input id="valueDocentes" class="form-control, InputDataSheet">                              
                                    </td>
                                </tr>
                                <tr>
                                    
                                    
                                    <td> 
                                        <label id="labelMatricula" class="firstLabelDataSheet">Matricula</label>
                                        <input id="valueMatricula" class="form-control, InputDataSheet">                              
                                    </td>
                                     <td> 
                                        <label id="labelTurno" class="labelSecondColumnDatosAcademicos">Turno</label>
                                        <input id="valueTurno" class="form-control, InputDataSheet">                              
                                    </td>
                                    
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelCarrerasVigentes" class="firstLabelDataSheet">Carreras Vigentes</label>
                                        <input id="valueCarrerasVigentes" class="form-control, InputDataSheet">                              
                                    </td>
                                    
                                    <td> 
                                        <label id="labelPeriodoEscolar" class="labelSecondColumnDatosAcademicos">Periodo Escolar</label>
                                        <input id="valuePeriodoEscolar" class="form-control, InputDataSheet">                              
                                    </td>                              
                                </tr>
                                <tr>
                                    <td> 
                                        <label id="labelCarrerasLiquidadas" class="firstLabelDataSheet">Carreras Liquidadas</label>
                                        <input id="valueCarrerasLiquidadas" class="form-control, InputDataSheet">                              
                                    </td>
                                </tr>
                               
                            </table>
                            
                        </div>
                        </fieldset>
                        <fieldset  style="margin-left: 3%; margin-bottom: 5%; margin-right: 3%;">
                            <legend align="center">Infraestructura </legend>
                            <div id="divInfraestructura" name="divInfraestructura">
                            <table>
                                 <tr style="height: 40px">
                                    <td> 
                                        <label id="labelSuperficiePredio" class="firstLabelDataSheet">Superficie del Predio </label>
                                        <input id="valueSuperficiePredio" class="form-control, InputDataSheet, valueFirstColumnInfraestructura">                              
                                    </td>
                                   <td> 
                                        <label id="labelSuperficieConstruida" class="labelInfraestructura">Superficie Construida </label>
                                        <input id="valueSuperficieConstruida" class="form-control, InputDataSheet, valueSecondColumnInfraestructura">                              
                                    </td>
                                    <td> 
                                        <label id="labelAulasDidacticas" class="labelInfraestructura, labelthirdColumnInfraestructura">Aulas Didacticas</label>
                                        <input id="valueAulasDidacticas" class="form-control, InputDataSheet, valueThirdColumnInfraestructura, valueThirdColumnInfraestructura2">                              
                                    </td>
                                    
                                </tr>
                                <tr style="height: 40px">
                                    
                                    <td> 
                                        <label id="labelLaboratorios" class="firstLabelDataSheet">Laboratorios </label>
                                        <input id="valueLaboratorios" class="form-control, InputDataSheet, valueFirstColumnInfraestructura">                              
                                    </td>
                                     <td> 
                                        <label id="labelBiblioteca" class="labelInfraestructura">Biblioteca </label>
                                        <input id="valueBiblioteca" class="form-control, InputDataSheet, valueSecondColumnInfraestructura">                              
                                    </td>
                                    <td> 
                                        <label id="labelTalleresComputo" class="labelInfraestructura, labelthirdColumnInfraestructura">Talleres de Computo</label>
                                        <input id="valueTalleresComputo" class="form-control, InputDataSheet, valueThirdColumnInfraestructura, valueThirdColumnInfraestructura2">                              
                                    </td>
                                </tr>
                                 <tr style="height: 40px">
                                    
                                    <td> 
                                        <label id="labelOtrosTalleres" class="firstLabelDataSheet">Otros Talleres</label>
                                        <input id="valueOtrosTalleres" class="form-control, InputDataSheet, valueFirstColumnInfraestructura">                              
                                    </td>
                                    <td> 
                                        <label id="labelAreaAdministrativa" class="labelInfraestructura">Area Administrativa</label>
                                        <input id="valueAreaAdministrativa" class="form-control, InputDataSheet, valueSecondColumnInfraestructura">                              
                                    </td>
                                    <td> 
                                        <label id="labelCafeteria" class="labelInfraestructura, labelthirdColumnInfraestructura">Cafeteria</label>
                                        <input id="valueCafeteria" class="form-control, InputDataSheet, valueThirdColumnInfraestructura, valueThirdColumnInfraestructura2">                              
                                    </td>
                                    
                                </tr>
                                
                                <tr>
                                    <td> 
                                        <label id="labelSalaMedios" class="firstLabelDataSheet">Sala de Medios o AudioVisual</label>
                                        <input id="valueSalaMedios" class="form-control, InputDataSheet, valueFirstColumnInfraestructura">                              
                                    </td>
                                    <td> 
                                        <label id="labelCaseta" class="labelInfraestructura">Caseta de Vigilancia</label>
                                        <input id="valueCaseta" class="form-control, InputDataSheet valueSecondColumnInfraestructura">                              
                                    </td> 
                                     <td> 
                                        <label id="labelBardaPerimetral" class="labelInfraestructura, labelthirdColumnInfraestructura">Barda Perimetral</label>
                                        <input id="valueBardaPerimetral" class="form-control, InputDataSheet, valueThirdColumnInfraestructura, valueThirdColumnInfraestructura2">                              
                                    </td>
                                </tr>
                                 <tr style="height: 40px">
                                    
                                    
                                   
                                    <td >
                                        <label id="labelAreasDeportivas" class="firstLabelDataSheet">Areas Deportivas</label>
                                        <input id="valueAreasDeportivas" class="form-control, InputDataSheet, valueFirstColumnInfraestructura">
                                    </td>
                                </tr>
                                <tr>
                                    
                                </tr>
                            </table>
                            
                        </div>
                        </fieldset>
                        <div id="" name=""></div>
                        <div id="" name=""></div>
                    </form>
                    
                </div>
                <div id="divFoot">
                    <jsp:include page='<%=(PageParameters.getParameter("footer"))%>' />
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