<%@page import="java.io.FileInputStream"%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>
<%    try {
        if (fine) {
            if (request.getParameter(WebUtil.encode(session, "imix")) != null) {
                LinkedList<String> access4ThisPage = new LinkedList();
                access4ThisPage.add("bajarArchivo");
                LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
                if (UserUtil.isAValidUser(access4ThisPage, userAccess)) {
                    LinkedList documento = QUID.select_Archivo(WebUtil.decode(session, request.getParameter("idArchivo")));
                    try {
                        String nombreArchivo = request.getParameter("hash")!=null?documento.get(8).toString():documento.get(0).toString();
                        FileInputStream archivo = new FileInputStream(documento.get(2).toString() + nombreArchivo);
                        int longitud = archivo.available();
                        byte[] datos = new byte[longitud];
                        archivo.read(datos);
                        archivo.close();
                        response.setContentType(WebUtil.getContentType(documento.get(3).toString()));
                        if (request.getParameter("see") != null && WebUtil.decode(session, request.getParameter("see")).equals("1")) {
                            response.setHeader("Content-Disposition", "inline;filename=" + nombreArchivo);
                        } else {
                            response.setHeader("Content-Disposition", "attachment;filename=" + nombreArchivo);
                        }
                        ServletOutputStream ouputStream = response.getOutputStream();
                        ouputStream.write(datos);
                        ouputStream.flush();
                        ouputStream.close();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
%>
<jsp:include page='<%= PageParameters.getParameter("msgUtil") + "/msgNRedirectFull.jsp"%>' flush = 'true'>
    <jsp:param name='title' value='Error' />
    <jsp:param name='msg' value='El archivo no se pudo descargar :(.' />
    <jsp:param name='type' value='error' />
    <jsp:param name='url' value='<%=PageParameters.getParameter("mainMenu")%>' />
</jsp:include>
<%
    }
} else {

%>                
<jsp:include page='<%= PageParameters.getParameter("msgUtil") + "/msgNRedirectFull.jsp"%>' flush = 'true'>
    <jsp:param name='title' value='Error' />
    <jsp:param name='msg' value='Intento de acceso Ilegal - Usted no cuenta con el permiso para accesar a esta pagina' />
    <jsp:param name='type' value='error' />
    <jsp:param name='url' value='<%=PageParameters.getParameter("mainMenu")%>' />
</jsp:include>
<%
    }
} else {
    //System.out.println("No se ha encontrado a imix");
%>
<jsp:include page='<%= PageParameters.getParameter("msgUtil") + "/msgNRedirectFull.jsp"%>' flush = 'true'>
    <jsp:param name='title' value='Error' />
    <jsp:param name='msg' value='Peticion invalida' />
    <jsp:param name='type' value='error' />
    <jsp:param name='url' value='<%=PageParameters.getParameter("mainMenu")%>' />
</jsp:include>
<%
        }
    }
} catch (Exception ex) {
    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
%>
<jsp:forward page='<%=PageParameters.getParameter("msgUtil") + "/msgNRedirectFull.jsp"%>'>
    <jsp:param name='type' value='error'/>
    <jsp:param name='title' value='Error Grave'/>
    <jsp:param name='msg' value='Lo sentimos la pagina ha tenido un error grave'/>
    <jsp:param name='url' value='<%=PageParameters.getParameter("errorURL")%>'/>
</jsp:forward>

<%
        //response.sendRedirect(redirectURL);
    }
%>