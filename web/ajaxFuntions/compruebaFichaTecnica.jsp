<%-- 
    Document   : compruebaFichaTecnica
    Created on : Oct 6, 2016, 1:06:06 PM
    Author     : emmanuel
--%>

<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>

<%@ include file="/gui/pageComponents/globalSettings.jsp"%>

<%    try {
        if (request.getParameter(WebUtil.encode(session, "imix")) != null) {
            if (!request.getParameter("idPlantel").equals("")) {

                int idPlantel = Integer.parseInt(WebUtil.decode(session, request.getParameter("idPlantel")));
                boolean existeFicha = false;
                existeFicha = QUID.getFichaTecnica(idPlantel);
                if (existeFicha != true) {
%>
<button type="button" onclick="enviarInfotoAgregaFichaTecnica(document.getElementById('selectPlantel'))" class="btn btn-default" style="margin-left: 44%; margin-top: 2%;">Agregar Ficha Técnica</button>
<%
} else {
%>
<button type="button" class="btn btn-default" style="margin-top: 2%; margin-left: 37%; margin-right: 2%;">Modificar Ficha Técnica</button>
<button type="button" class="btn btn-default" style="margin-top: 2%;">Imprimir Ficha Técnica</button>

<%
    }
%>

<%                            }
        } else {
            //System.out.println("No se ha encontrado a imix");
            out.print("Peticion invalida");
        }
    } catch (Exception ex) {
        out.print("Lo sentimos la petición ha tenido un error :(");
    }
%>