<%-- 
    Document   : modificaFichaTecnica
    Created on : Oct 17, 2016, 11:23:37 AM
    Author     : emmanuel
--%>

<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
 <link href="../rsc/css/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css" /> 
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>





<%    try {
        if (fine) {
            if (request.getParameter(WebUtil.encode(session, "imix")) != null) {
                String access4ThisPage = "updateDataSheet";
                LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
                if (UserUtil.isAValidUser(access4ThisPage, userAccess)) {
                    if (PageParameters.getParameter("SiteOnMaintenance").equals("true")) {
                        String redirectURL = "" + PageParameters.getParameter("mainController") + "?exit=1";
                        response.sendRedirect(redirectURL);
                    } else {
                        LinkedList infoDataSheet= null;
                        int idPlantel= Integer.parseInt(WebUtil.decode(session, request.getParameter("idPlantel")));
                              infoDataSheet= QUID.selectDataToDataSheet(idPlantel);
                        int idAcademico= Integer.parseInt(infoDataSheet.get(31).toString());
                        int idInfraestructura= Integer.parseInt(infoDataSheet.get(32).toString());
                        int idMunicipio= Integer.parseInt(infoDataSheet.get(33).toString());
                        int idEstado= Integer.parseInt(infoDataSheet.get(34).toString());
%>

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
             function enviarInfocontroller() {
                $.ajax({type: 'POST'
                    , contentType: 'application/x-www-form-urlencoded;charset=utf-8'
                    , cache: false
                    , async: false
                    , url: '<%=PageParameters.getParameter("mainController")%>'
                    , data: $('#modificaFichaTecnica').serialize()
                    , success: function (response) {
                        $('#wrapper').find('#divResult').html(response);
                    }});
            }
          
            
            function resetForm() {
                document.getElementById("valueNombrePlantel").value = '';
                document.getElementById("valueDireccion").value = '';
                document.getElementById("valueCCT").value = '';
                document.getElementById("valueAnioCreacion").value = '';
                document.getElementById("valueTelefono").value = '';
                document.getElementById("valueCorreo").value = '';
                document.getElementById("valueLatitud").value = '';
                document.getElementById("valueLongitud").value = '';
                document.getElementById("valueDirector").value = '';
                document.getElementById("valuePersonalAdmin").value = '';
                document.getElementById("valueDocentes").value = '';
                document.getElementById("valueMatricula").value = '';
                document.getElementById("valueTurno").value = '';
                document.getElementById("valuePeriodoEscolar").value = '';
                document.getElementById("valueCarrerasVigentes").value = '';
                document.getElementById("valuePeriodoEscolar").value = '';
                document.getElementById("valueCarrerasLiquidadas").value = '';
                document.getElementById("valueSuperficiePredio").value = '';
                document.getElementById("valueSuperficieConstruida").value = '';
                document.getElementById("valueAulasDidacticas").value = '';
                document.getElementById("valueLaboratorios").value = '';
                document.getElementById("valueBiblioteca").value = '';
                document.getElementById("valueTalleresComputo").value = '';
                document.getElementById("valueOtrosTalleres").value = '';
                document.getElementById("valueAreaAdministrativa").value = '';
                document.getElementById("valueCafeteria").value = '';
                document.getElementById("valueSalaMedios").value = '';
                document.getElementById("valueCaseta").value = '';
                document.getElementById("valueBardaPerimetral").value = '';
                document.getElementById("valueAreasDeportivas").value = '';
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
                            <a>> Modificar Ficha Técnica</a>
                        </td>
                        <td width="36" align="right" valign="top">
                            <script language="JavaScript" src="<%=PageParameters.getParameter("jsRcs")%>/funcionDate.js" type="text/javascript"></script>
                        </td>
                    </tr>                        
                </table>
                <br>
                <br>
                <div id="bodyModificaFichaTecnica">
                    <form id="modificaFichaTecnica" name="modificaFichaTecnica">
                       <input type="hidden" name="FormForm" value="modificaFichaTecnica"/>
                       <input type="hidden" name="idPlantel" value="<%=WebUtil.encode(session, idPlantel) %>"/>
                        <input type="hidden" name="sesion" value="<%=WebUtil.encode(session, "imix") %>"/>
                        <input type="hidden" name="idAcademico" value="<%=WebUtil.encode(session, idAcademico) %>"/>
                        <input type="hidden" name="idInfraestructura" value="<%=WebUtil.encode(session, idInfraestructura) %>"/>
                        <input type="hidden" name="idMunicipio" value="<%=WebUtil.encode(session, idMunicipio) %>"/>
                        <input type="hidden" name="idEstado" value="<%=WebUtil.encode(session, idEstado) %>"/>
                        <legend align="center" ><%=QUID.selectNombrePlantel(idPlantel) %></legend>
                        <br>
                       <fieldset id="fieldDatosGenerales" name="fieldDatosGenerales"style="margin-left: 3%; margin-bottom: 5%; margin-right: 3%;">
                           <legend  id="tituloDatosGenerales"  align="center">Datos Generales</legend>
                        <div id="divDatosGenerales" name="divDatosGenerales">
                            <table>
                                <tr>
                                    <td> 
                                        <label id="labelNombrePlantel"class="firstLabelDataSheet">Nombre del Plantel  </label>
                                        <input id="valueNombrePlantel" name="valueNombrePlantel"class="form-control, InputDataSheet"  value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(0).toString(): "" %>" title="Nombre de Plantel">                              
                                    </td>
                                    <td> 
                                        <label id="labelDireccion" class="labelSecondColumnDatosGenerales">Dirección</label>
                                        <input id="valueDireccion" name="valueDireccion" class="form-control, InputDataSheet"   value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(1).toString(): "" %>" title="Dirección">                        
                                    </td>
                                   
                                </tr>
                                <tr>
                                    
                                    <td> 
                                        <label id="laberMunicipio"class="firstLabelDataSheet">Municipio</label>
                                        <input id="valueMunicipio" name="valueMunicipio" class="form-control, InputDataSheet"  disabled value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(2).toString(): "" %>" title="Municipio">                       
                                    </td>
                                    <td> 
                                        <label id="labelEstado" class="labelSecondColumnDatosGenerales">Estado</label>
                                        <input id="valueEstado" name="valueEstado" class="form-control, InputDataSheet, valuesSecondColumnDatosGenerales" disabled value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(3).toString(): "" %>" title="Estado">                            
                                    </td>
                                     
                                </tr>
                                <tr>
                                   
                                    <td> 
                                        <label id="labelCCT"class="firstLabelDataSheet">C.C.T</label>
                                        <input id="valueCCT"  name="valueCCT" class="form-control, InputDataSheet"  value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(4).toString(): "" %>" title="Clave de Centro de Trabajo">                          
                                    </td>
                                    <td> 
                                        <label id="labelAnioCreacion" class="labelSecondColumnDatosAcademicos">Año de Creación</label>
                                        <input id="valueAnioCreacion"  name="valueAnioCreacion" class="form-control, InputDataSheet, valuesSecondColumnDatosGenerales"   value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(5).toString(): "" %>" title="Año de Creación">                     
                                    </td>
                                </tr>
                                <tr>
                                   <td> 
                                       <label id="labelTelefono" class="firstLabelDataSheet">Teléfono</label>
                                       <input id="valueTelefono"  name="valueTelefono" class="form-control, InputDataSheet"  value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(6).toString(): "" %>" title="Teléfono">                         
                                    </td>
                                    <td> 
                                        <label id="labelCorreo" class="labelSecondColumnDatosGenerales">Correo</label>
                                        <input id="valueCorreo"  name="valueCorreo"class="form-control, InputDataSheet, valuesSecondColumnDatosGenerales"  value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(7).toString(): "" %>" title="Correo">                            
                                    </td>
                                </tr>
                                
                                <tr>
                                    <td> 
                                        <label  id="labelLatitud"class="firstLabelDataSheet">Latitud</label>
                                        <input id="valueLatitud"  name="valueLatitud" class="form-control, InputDataSheet"   value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(8).toString(): "" %>" title="Latitud en Grados">                         
                                    </td>
                                    <td> 
                                        <label id="labelLongitud" class="labelSecondColumnDatosGenerales">Longitud</label>
                                        <input id="valueLongitud"  name="valueLongitud" class="form-control, InputDataSheet, valuesSecondColumnDatosGenerales"   value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(9).toString(): "" %>" title="Longitud en Grados">                              
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
                                   <td colspan="3"> 
                                        <label id="labelDirector" class="firstLabelDataSheet">Director</label>
                                        <input id="valueDirector"  name="valueDirector" class="form-control, InputDataSheet"   value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(10).toString(): "" %>" title="Nombre del Director">                              
                                    </td>
                                   
                                </tr>
                                <tr>
                                    
                                    <td> 
                                        <label id="labelPersonalAdmin" class="firstLabelDataSheet">Personal Administrativo</label>
                                        <input id="valuePersonalAdmin" name="valuePersonalAdmin" class="form-control, InputDataSheet" value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(11).toString(): "" %>" title="Número de Personal Administrativo">                              
                                    </td>
                                    <td> 
                                        <label id="labelDocentes" class="">Docentes</label>
                                        <input id="valueDocentes" name="valueDocentes" class="form-control, InputDataSheet" value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(12).toString(): "" %>" title="Número de Docentes">                              
                                    </td>
                                    <td> 
                                        <label id="labelMatricula" class="firstLabelDataSheet">Matrícula</label>
                                        <input id="valueMatricula" name="valueMatricula" class="form-control, InputDataSheet"  value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(13).toString(): "" %>" title="Cantidad de Alumnos">                              
                                    </td>
                                 </tr>
                                <tr>
                                     <td> 
                                        <label id="labelTurno" class="firstLabelDataSheet">Turno </label>
                                        <select id="valueTurno" name="valueTurno" class="form-control, valueFirstColumnInfraestructura">
                                            <% String value= infoDataSheet.get(14).toString();
                                                boolean flag1=false;
                                                 boolean  flag2=false;
                                                 boolean flag3=false;
                                                 boolean flag4=false;
                                                if(value.equals(""))
                                                {
                                                    flag1=true;
                                                }else if(value.equals("Matutino"))
                                                {
                                                    flag2=true;
                                                }else if(value.equals("Vespertino"))
                                                {
                                                    flag3=true;
                                                }else if(value.equals("Matutino y Vespertino"))
                                                {
                                                    flag4=true;
                                                }
                                            %>
                                            <option value="" <%if(flag1==true) { %>selected<%}%>> </option>
                                            <option value="Matutino" <%if(flag2==true) { %>selected<%}%>>Matutino</option>
                                            <option value="Vespertino" <%if(flag3==true) { %>selected<%}%>>Vespertino</option>
                                            <option value="Matutino y Vespertino" <%if(flag4==true) { %>selected<%}%>>Matutino y Vespertino</option>
                                        </select>
                                    </td>
                                    <td colspan="2"> 
                                        <label id="labelPeriodoEscolar" class="labelSecondColumnDatosAcademicos">Periodo Escolar</label>
                                        <input id="valuePeriodoEscolar"  name="valuePeriodoEscolar" class="form-control, InputDataSheet" value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(15).toString(): "" %>" title="Periodo Escolar">                              
                                    </td>        
                                </tr>
                               
                                <tr>
                                    <td  colspan="2"> 
                                        <label id="labelCarrerasVigentes" class="firstLabelDataSheet">Carreras Vigentes</label>
                                        <input id="valueCarrerasVigentes" name="valueCarrerasVigentes" class="form-control, InputDataSheet" value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(16).toString(): "" %>" title="Describir Carreras del Plantel">                              
                                    </td>
                                    
                                                          
                                </tr>
                                <tr>
                                    <td  colspan="2"> 
                                        <label id="labelCarrerasLiquidadas" class="firstLabelDataSheet">Carreras Liquidadas</label>
                                        <input id="valueCarrerasLiquidadas" name="valueCarrerasLiquidadas" class="form-control, InputDataSheet" value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(17).toString(): "" %>" title="Describir Carreras en Liquidación">                              
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
                                        <input id="valueSuperficiePredio" name="valueSuperficiePredio" class="form-control, InputDataSheet, valueFirstColumnInfraestructura" value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(18).toString(): "" %>" title="Cantidad de Superficie">                              
                                    </td>
                                   <td> 
                                        <label id="labelSuperficieConstruida" class="labelInfraestructura">Superficie Construida </label>
                                        <input id="valueSuperficieConstruida" name="valueSuperficieConstruida" class="form-control, InputDataSheet, valueSecondColumnInfraestructura" value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(19).toString(): "" %>" title="Cantidad de Superficie Construida">                              
                                    </td>
                                    <td> 
                                        <label id="labelAulasDidacticas" class="labelInfraestructura, labelthirdColumnInfraestructura">Aulas Didacticas</label>
                                        <input id="valueAulasDidacticas"  name="valueAulasDidacticas" class="form-control, InputDataSheet, valueThirdColumnInfraestructura, valueThirdColumnInfraestructura2" value="<%=infoDataSheet.size()>20 ? infoDataSheet.get(5).toString(): "" %>" title="Número de Aulas Didacticas">                              
                                    </td>
                                    
                                </tr>
                                <tr style="height: 40px">
                                    
                                    <td> 
                                        <label id="labelLaboratorios" class="firstLabelDataSheet">Laboratorios </label>
                                        <input id="valueLaboratorios"  name="valueLaboratorios" class="form-control, InputDataSheet, valueFirstColumnInfraestructura" value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(21).toString(): "" %>" title="Número de Laboratorios">                              
                                    </td>
                                     <td> 
                                        <label id="labelBiblioteca" class="labelInfraestructura">Biblioteca </label>
                                        <select id="valueBiblioteca" name="valueBiblioteca" class="form-control, valueFirstColumnInfraestructura">
                                            <% value= infoDataSheet.get(22).toString();
                                                 flag1=false;
                                                   flag2=false;
                                                 flag3=false;
                                                if(value.equals(""))
                                                {
                                                    flag1=true;
                                                }else if(value.equals("Si"))
                                                {
                                                    flag2=true;
                                                }else if(value.equals("No"))
                                                {
                                                    flag3=true;
                                                }
                                            %>
                                            <option value="" <%if(flag1==true) { %>selected<%}%>> </option>
                                            <option value="Si" <%if(flag2==true) { %>selected<%}%>>Si</option>
                                            <option value="No" <%if(flag3==true) { %>selected<%}%>>No</option>
                                        </select>
                                    </td>
                                    <td> 
                                        <label id="labelTalleresComputo" class="labelInfraestructura, labelthirdColumnInfraestructura">Talleres de Cómputo</label>
                                        <input id="valueTalleresComputo"  name="valueTalleresComputo" class="form-control, InputDataSheet, valueThirdColumnInfraestructura, valueThirdColumnInfraestructura2" value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(23).toString(): "" %>" title="Número de Talleres de Cómputo">                              
                                    </td>
                                </tr>
                                 <tr style="height: 40px">
                                    
                                    <td> 
                                        <label id="labelOtrosTalleres" class="firstLabelDataSheet">Otros Talleres</label>
                                        <input id="valueOtrosTalleres"  name="valueOtrosTalleres" class="form-control, InputDataSheet, valueFirstColumnInfraestructura" value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(24).toString(): "" %>" title="Describir si hay mas Talleres">                              
                                    </td>
                                    <td> 
                                        <label id="labelAreaAdministrativa" class="labelInfraestructura">Area Administrativa</label>
                                        <select id="valueAreaAdministrativa" name="valueAreaAdministrativa" class="form-control, valueFirstColumnInfraestructura">
                                            <%  value= infoDataSheet.get(25).toString();
                                                    flag1=false;
                                                   flag2=false;
                                                   flag3=false;
                                                if(value.equals(""))
                                                {
                                                    flag1=true;
                                                }else if(value.equals("Si"))
                                                {
                                                    flag2=true;
                                                }else if(value.equals("No"))
                                                {
                                                    flag3=true;
                                                }
                                            %>
                                            <option value="" <%if(flag1==true) { %>selected<%}%>> </option>
                                            <option value="Si" <%if(flag2==true) { %>selected<%}%>>Si</option>
                                            <option value="No" <%if(flag3==true) { %>selected<%}%>>No</option>
                                        </select>
                                    </td>
                                    <td> 
                                        <label id="labelCafeteria" class="labelInfraestructura, labelthirdColumnInfraestructura">Cafetería</label>
                                       <select id="valueCafeteria" name="valueCafeteria" class="form-control, valueFirstColumnInfraestructura">
                                             <%  value= infoDataSheet.get(26).toString();
                                                 flag1=false;
                                                   flag2=false;
                                                  flag3=false;
                                                if(value.equals(""))
                                                {
                                                    flag1=true;
                                                }else if(value.equals("Si"))
                                                {
                                                    flag2=true;
                                                }else if(value.equals("No"))
                                                {
                                                    flag3=true;
                                                }
                                            %>
                                            <option value="" <%if(flag1==true) { %>selected<%}%>> </option>
                                            <option value="Si" <%if(flag2==true) { %>selected<%}%>>Si</option>
                                            <option value="No" <%if(flag3==true) { %>selected<%}%>>No</option>
                                        </select> 
                                    </td>
                                    
                                </tr>
                                
                                <tr>
                                    <td> 
                                        <label id="labelSalaMedios" class="firstLabelDataSheet">Sala de Medios o AudioVisual</label>
                                          <select id="valueSalaMedios" name="valueSalaMedios" class="form-control, valueFirstColumnInfraestructura">
                                            <%  value= infoDataSheet.get(27).toString();
                                                 flag1=false;
                                                   flag2=false;
                                                  flag3=false;
                                                if(value.equals(""))
                                                {
                                                    flag1=true;
                                                }else if(value.equals("Si"))
                                                {
                                                    flag2=true;
                                                }else if(value.equals("No"))
                                                {
                                                    flag3=true;
                                                }
                                            %>
                                            <option value="" <%if(flag1==true) { %>selected<%}%>> </option>
                                            <option value="Si" <%if(flag2==true) { %>selected<%}%>>Si</option>
                                            <option value="No" <%if(flag3==true) { %>selected<%}%>>No</option>
                                        </select>                                  
                                    </td>
                                    <td> 
                                        <label id="labelCaseta" class="labelInfraestructura">Caseta de Vigilancia</label>
                                        <select id="valueCaseta" name="valueCaseta" class="form-control, valueFirstColumnInfraestructura">
                                           <%  value= infoDataSheet.get(28).toString();
                                                 flag1=false;
                                                   flag2=false;
                                                  flag3=false;
                                                if(value.equals(""))
                                                {
                                                    flag1=true;
                                                }else if(value.equals("Si"))
                                                {
                                                    flag2=true;
                                                }else if(value.equals("No"))
                                                {
                                                    flag3=true;
                                                }
                                            %>
                                            <option value="" <%if(flag1==true) { %>selected<%}%>> </option>
                                            <option value="Si" <%if(flag2==true) { %>selected<%}%>>Si</option>
                                            <option value="No" <%if(flag3==true) { %>selected<%}%>>No</option>
                                        </select>                            
                                    </td> 
                                     <td> 
                                        <label id="labelBardaPerimetral" class="labelInfraestructura, labelthirdColumnInfraestructura">Barda Perimetral</label>
                                        <select id="valueBardaPerimetral" name="valueBardaPerimetral" class="form-control, valueFirstColumnInfraestructura">
                                            <%  value= infoDataSheet.get(29).toString();
                                                 flag1=false;
                                                   flag2=false;
                                                  flag3=false;
                                                if(value.equals(""))
                                                {
                                                    flag1=true;
                                                }else if(value.equals("Si"))
                                                {
                                                    flag2=true;
                                                }else if(value.equals("No"))
                                                {
                                                    flag3=true;
                                                }
                                            %>
                                            <option value="" <%if(flag1==true) { %>selected<%}%>> </option>
                                            <option value="Si" <%if(flag2==true) { %>selected<%}%>>Si</option>
                                            <option value="No" <%if(flag3==true) { %>selected<%}%>>No</option>
                                        </select>    
                                    </td>
                                </tr>
                                 <tr style="height: 40px">
                                    
                                    
                                   
                                    <td >
                                        <label id="labelAreasDeportivas" class="firstLabelDataSheet">Areas Deportivas</label>
                                        <input id="valueAreasDeportivas"  name="valueAreasDeportivas" class="form-control, InputDataSheet, valueFirstColumnInfraestructura" value="<%=infoDataSheet.size()>0 ? infoDataSheet.get(30).toString(): "" %>" title="Numero de Áreas">
                                    </td>
                                </tr>
                                <tr>
                                    
                                </tr>
                            </table>
                            
                        </div>
                        </fieldset>
                       <div id="botonEnviarDiv"  >
                           <input id="addDataSheet" type="button" class="btn btn-default" value="Guardar Ficha Técnica" name="addDataSheet" onclick="enviarInfocontroller();"/>
                           
                          <!-- <input id="cleanDataSheet" type="button" class="btn btn-default" value="Limpiar Ficha Técnica" name="cleanDataSheet" onclick="resetForm();"/>-->
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