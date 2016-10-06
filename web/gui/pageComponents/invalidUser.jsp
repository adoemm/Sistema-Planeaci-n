<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@page import="jspread.core.util.PageParameters"%>

<jsp:include page='<%= PageParameters.getParameter("msgUtil") + "/msgNRedirectFull.jsp"%>' flush = 'true'>
    <jsp:param name='title' value='Error' />
    <jsp:param name='msg' value='Intento de acceso Ilegal - Usted NO cuenta con el permiso para accesar a esta pagina' />
    <jsp:param name='type' value='error' />
    <jsp:param name='url' value='<%=PageParameters.getParameter("mainMenu")%>' />
</jsp:include>