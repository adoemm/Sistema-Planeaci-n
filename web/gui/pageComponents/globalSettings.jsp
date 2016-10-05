<%@page import="jspread.core.util.SessionUtil"%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@page import="jspread.core.util.security.EDP"%>
<%@page import="jspread.core.db.QUID"%>
<%@page import="jspread.core.util.PageParameters"%>
<%@page import="systemSettings.SystemSettings"%>
<%@page import="java.util.logging.Logger"%>
<%@page import="java.util.logging.Level"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="jspread.core.util.UTime"%>



<jsp:useBean id="QUID" scope="page" class="jspread.core.db.QUID"/>

<%
    //inicializacion del jspread
    boolean fine = true;

    try {
        SystemSettings.ignition();

        request.setCharacterEncoding(PageParameters.getParameter("charset").toString());
        response.setCharacterEncoding(PageParameters.getParameter("charset").toString());
        session = request.getSession(true);
        SessionUtil.addIfNotExistSession(session);
        if (session.getAttribute("encryptedKey") == null) {
            session.setAttribute("encryptedKey", EDP.encryptedKey(session.getId() + PageParameters.getParameter("encodeParametersSemilla") + (UTime.getTimeMilis() - 4)));
            
        }
        QUID.setRequest(request);

        Iterator it = null;
        LinkedList listAux = null;
        Iterator itAux = null;
    } catch (Exception ex) {
        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        fine = false;
%>
<script type="text/javascript" language="javascript" charset="utf-8">
    alert("Lo sentimos :(  la pagina ha tenido un error fatal.");
    window.location.href = 'http://cecytem.edomex.gob.mx/';
</script>
<%
    }
%>
