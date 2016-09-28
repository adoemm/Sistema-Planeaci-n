<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@page import="jspread.core.util.PageParameters"%>

<jsp:forward page='<%=PageParameters.getParameter("msgUtil") + "/msgNRedirectFull.jsp"%>'>
    <jsp:param name='type' value='error'/>
    <jsp:param name='title' value='Error Grave'/>
    <jsp:param name='msg' value='Lo sentimos la pagina ha tenido un error grave'/>
    <jsp:param name='url' value='<%=PageParameters.getParameter("errorURL")%>'/>
</jsp:forward>