<%-- 
    Document   : impresionFichaTecnica
    Created on : Nov 7, 2016, 11:23:46 AM
    Author     : emmanuel
--%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
 <link href="../../rsc/css/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet" type="text/css" /> 
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>
<%    try {
        if (fine) {
            if (request.getParameter(WebUtil.encode(session, "imix")) != null) {
                String access4ThisPage = "printDataSheet";
                LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
                if (UserUtil.isAValidUser(access4ThisPage, userAccess)) {
                    if (PageParameters.getParameter("SiteOnMaintenance").equals("true")) {
                        String redirectURL = "" + PageParameters.getParameter("mainController") + "?exit=1";
                        response.sendRedirect(redirectURL);
                    } else {
                                 int idPlantel= Integer.parseInt(WebUtil.decode(session, request.getParameter("idPlantel")));
                                 String plantel=QUID.selectNombrePlantel(idPlantel);
                                 LinkedList infoToDataSheet= QUID.selectDataToDataSheet(idPlantel);
                                 
%>

<!DOCTYPE html>
<html lang="<%=PageParameters.getParameter("Content-Language")%>">
    <head>
        <title> Impresión Ficha Tecnica</title>
        <jsp:include page='<%=PageParameters.getParameter("globalLibs")%>'/>        
         <script type="text/javascript" language="javascript" charset="utf-8">
            window.history.forward();
            function noBack() {
                window.history.forward();
            }
            </script>
            <link href="../../rsc/css/stylePrintDataSheet.css" rel="stylesheet" type="text/css" />
    </head>
    <body style="background-color:#ffffff;">
        <div align="center" style="width: 25cm; height: 28cm; margin: 0 auto;">
            <div>
                <div align="center">
                    <br><br>
                      <table width="92%" border="0" align="center">
                        <tr>
                            <td align="left" width="7%">
                                <img src="<%=PageParameters.getParameter("rsc")%>/img/edomex.png"  align="left"  height="105px" width="160px">
                            </td>
                            <td class="estilo2" width="80%">
                                <p align="center">
                                    <strong>COLEGIO DE ESTUDIOS CIENTÍFICOS Y TECNOLÓGICOS DEL ESTADO DE MÉXICO</strong>
                                    <br>
                                    <br><b><strong>SISTEMA DE PLANEACIÓN INSTITUCIONAL DE CECYTEM (SPIC)</strong></b><br>
                                    <br><b><strong>FICHA TÉCNICA PLANTEL <%=plantel.toUpperCase() %></strong></b>
                                </p></td>
                            <td align="right" width="13%">
                                <img src="<%=PageParameters.getParameter("rsc")%>/img/cecytem2.jpg"  align="center"  height="60px" width="50px">    
                            </td>
                        </tr>
                    </table>
                            <br>
                    <div class="container-fluid">
                       
                       
                   
                    <table style="width: 100%"  class="onlyBorde" rules="all">
                        <tr  class="celdaRellenoCenter">
                            <td colspan="6" class="classRowTitlePrints">Datos Generales</td>
                            
                        </tr>
                        <tr>
                            <td  colspan="2"class="classRow">Dirección: </td>
                            <td colspan="4" class="classRow"><%=infoToDataSheet.get(1).toString() %></td>
                            
                        </tr>
                        <tr>
                            <td colspan="2"class="classRow">Municipio:</td>
                            <td class="classRow"><%=infoToDataSheet.get(2).toString() %></td>
                            <td class="classRow">Estado: </td>
                            <td colspan="2" class="classRow"><%=infoToDataSheet.get(3).toString() %></td>
                        </tr>
                        <tr>
                            <td colspan="2"class="classRow">Año de Creación: </td>
                            <td class="classRow"><%=infoToDataSheet.get(5).toString() %></td>
                            <td class="classRow">C.C.T:</td>
                            <td colspan="2" class="classRow"><%=infoToDataSheet.get(4).toString() %></td>
                            
                        </tr>
                        <tr>
                            <td colspan="2" class="classRow">Teléfono: </td>
                            <td class="classRow"><%=infoToDataSheet.get(6).toString() %></td>
                            <td class="classRow">Correo:</td>
                            <td class="classRow"colspan="2"><%=infoToDataSheet.get(7).toString() %></td>
                            
                        </tr>
                        <tr>
                            <td colspan="2"class="classRow">Latitud: </td>
                            <td class="classRow"><%=infoToDataSheet.get(8).toString() %></td>
                            <td class="classRow">Longitud:</td>
                            <td class="classRow"colspan="2"><%=infoToDataSheet.get(9).toString() %></td>
                            
                        </tr>
                         <tr  class="celdaRellenoCenter">
                             <td colspan="6" class="classRowTitlePrints">Datos Academicos</td>
                            
                        </tr>
                        <tr>
                            <td  colspan="2"class="classRow">Director: </td>
                            <td class="classRow"><%=infoToDataSheet.get(10).toString() %></td>
                            <td class="classRow">Personal Administrativo: </td>
                           <td  class="classRow"colspan="2"><%=infoToDataSheet.get(11).toString() %></td>
                            
                        </tr>
                        <tr>
                            <td class="classRow">Docentes: </td>
                            <td class="classRow"><%=infoToDataSheet.get(12).toString() %></td>
                            <td class="classRow">Matricula:</td>
                            <td class="classRow"><%=infoToDataSheet.get(13).toString() %></td>
                            <td class="classRow">Turno:</td>
                            <td class="classRow"><%=infoToDataSheet.get(14).toString() %></td>
                        </tr>
                        <tr>
                            <td colspan="2"class="classRow">Carreras Vigentes: </td>
                            <td  class="classRow"colspan="4"><%=infoToDataSheet.get(16).toString() %></td>
                                                        
                        </tr>
                        <tr>
                            <td colspan="2"class="classRow">Carreras Liquidadas:</td>
                            <td class="classRow"><%=infoToDataSheet.get(17).toString() %></td>
                            <td class="classRow">Periodo Escolar</td>
                            <td class="classRow" colspan="2"><%=infoToDataSheet.get(15).toString() %></td>
                        </tr>
                        
                        <tr  class="celdaRellenoCenter">
                             <td colspan="6" class="classRowTitlePrints">Infraestructura</td>
                            
                        </tr>
                        <tr>
                            <td colspan="2"class="classRow">Superficie del Predio: </td>
                           <td class="classRow"><%=infoToDataSheet.get(18).toString() %></td>
                            <td class="classRow">Superficie Construida: </td>
                           <td class="classRow" colspan="2"><%=infoToDataSheet.get(19).toString() %></td>
                            
                        </tr>
                        <tr>
                            <td class="classRow">Aulas Didácticas: </td>
                           <td class="classRow"><%=infoToDataSheet.get(20).toString() %></td>
                            <td class="classRow">Laboratorios: </td>
                            <td class="classRow"><%=infoToDataSheet.get(21).toString() %></td>
                            <td class="classRow">Biblioteca: </td>
                            <td class="classRow"><%=infoToDataSheet.get(22).toString() %></td>
                        </tr>
                        <tr>
                            <td colspan="2"class="classRow">Talleres de Cómputo: </td>
                            <td class="classRow"><%=infoToDataSheet.get(23).toString() %></td>
                            <td class="classRow">Otros Talleres: </td>
                            <td class="classRow" colspan="2"><%=infoToDataSheet.get(24).toString() %></td>
                            
                        </tr>
                        <tr>
                            <td colspan="2"class="classRow">Área Administrativa</td>
                           <td class="classRow"><%=infoToDataSheet.get(25).toString() %></td>
                            <td class="classRow">Sala de Medios o Audiovisual: </td>
                            <td class="classRow"colspan="2"><%=infoToDataSheet.get(27).toString() %></td>
                            
                        </tr>
                        <tr>
                            <td class="classRow">Caseta de Vigilancia: </td>
                            <td class="classRow"><%=infoToDataSheet.get(28).toString() %></td>
                            <td class="classRow">Cafetería: </td>
                            <td class="classRow"><%=infoToDataSheet.get(26).toString() %></td>
                            <td class="classRow">Barda Perimetral: </td>
                            <td class="classRow"><%=infoToDataSheet.get(29).toString() %></td>
                        </tr>
                        <tr>
                            <td colspan="2"class="classRow">Áreas Deportivas: </td>
                           <td colspan="4" class="classRow"><%=infoToDataSheet.get(30).toString() %></td>
                            
                        </tr>
                        <tr  class="celdaRellenoCenter">
                             <td colspan="6" class="classRowTitlePrints">Etapas de Desarrollo</td>
                            
                        </tr>
                        <tr style="text-align: center">
                            <td class="classRow">Etapa</td>
                            <td class="classRow">Año de Construcción</td>
                            <td colspan="2" class="classRow">Espacios Construidos</td>
                            <td class="classRow">Status</td>
                            <td class="classRow">Avance</td>
                        </tr>
                        <%
                             LinkedList listAux= new LinkedList();
                            Iterator it= QUID.selectEtapasDesarrollo(idPlantel).iterator();
                            if(it.hasNext())
                            {
                            while (it.hasNext()) {
                                listAux = (LinkedList) it.next();
                                
                                %>
                                <tr style="text-align: center">
                                    <td class="classRow"><%=listAux.get(6) %></td>
                                    <td class="classRow"><%=listAux.get(1) %></td>
                                    <td class="classRow" colspan="2"><%=listAux.get(3) %></td>
                                    <td class="classRow"><%=listAux.get(4) %></td>
                                    <td class="classRow"><%=listAux.get(5) %></td>
                                </tr>
                        
                        <%
                            }
}else
{
    %>
                        <tr style="text-align: center">
                            <td class="classRow" colspan="6">No hay Etapas</td>
                                
                                </tr>
                                
                                <%
}
                        %>
                        
                        
                    </table>
                    
                    <br><br>
                    
                    
                    
                    
                   
                    
                    
                    
                    
                    
                    
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