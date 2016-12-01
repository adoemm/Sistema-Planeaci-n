
<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>

<%    try {
        if (request.getParameter(WebUtil.encode(session, "imix")) != null) {
            LinkedList<String> access4ThisPage = new LinkedList();
            access4ThisPage.add("LoggedUser");
            LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");

            if (UserUtil.isAValidUser(access4ThisPage, userAccess)) {
                Iterator it = null;
                if(request.getParameter("FormFrom").equalsIgnoreCase("file4Software")){
                    //it = QUID.select_Archivo4Software(WebUtil.decode(session, request.getParameter("mainID"))).iterator();
                }else if(request.getParameter("FormFrom").equalsIgnoreCase("file4Bien")){
                    //it = QUID.select_Archivo4Bien(WebUtil.decode(session, request.getParameter("mainID"))).iterator();
                }else{
                    System.out.println("NULL");
                }
                
%>

<table  class="cssLayout" style="width:60%;" >
    <tr>
        <td style="width: 5%;">NP</td>
        <td style="width: 20%;">Documento</td>
        <td style="width: 25%;">Nombre de Archivo</td>
        <td style="width: 20%;">Descripción</td>
        <td style="width: 10%;">Extensión</td>
        <td style="width: 10%;"></td>
        <%
            if (request.getParameter("consulta") == null) {
        %>
        <td style="width: 10%;"></td>
        <%
            }
        %>

    </tr>
    <%        int i = 1;
        while (it.hasNext()) {
            LinkedList datos = (LinkedList) it.next();
    %>
    <tr>
        <td><%=i%></td>
        <td style="text-align: left;"><%=datos.get(6)%></td>
        <td style="text-align: left;"><%=datos.get(1)%></td>
        <td style="text-align: left;"><%=datos.get(2).toString()%></td>
        <td><%=datos.get(4)%></td>
        <td style="text-align: center;">
            <a target="_blank"  href="<%=PageParameters.getParameter("mainContext") + PageParameters.getParameter("gui")%>/DescargaArchivo.jsp?<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>&idArchivo=<%=WebUtil.encode(session, datos.get(0))%>&see=<%=WebUtil.encode(session, "1")%>">
                <img src="<%=PageParameters.getParameter("imgRsc")%>/icons/Gnome-Go-Down-64.png" title="Descargar" width="22" height="23" alt="Descargar">
            </a>
        </td>
        <%
            if (request.getParameter("consulta") == null) {
        %>
        <td><img src="<%=PageParameters.getParameter("imgRsc")%>/icons/Gnome-Process-Stop-64.png" title="Eliminar" width="22" height="23" alt="Eliminar" onclick="eliminarArchivo('<%=WebUtil.encode(session, datos.get(8))%>', '<%=request.getParameter("mainID")%>','<%=WebUtil.encode(session, datos.get(0))%>','<%=request.getParameter("FormFrom")%>')"></td>
            <%
                }
            %>
    </tr>
    <%
            i += 1;
        }
    %> 
</table>


<%        } else {
                //System.out.println("Usuario No valido para esta pagina");
                out.print("Usted no cuenta con el permiso para accesar a esta pagina");
            }
        } else {
            //System.out.println("No se ha encontrado a imix");
            out.print("Peticion invalida");
        }
    } catch (Exception ex) {
        out.print("Lo sentimos la petición ha tenido un error :(");
    }
%>