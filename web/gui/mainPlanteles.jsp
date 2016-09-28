<%-- 
    Document   : mainMenu
    Created on : Oct 4, 2016, 11:23:21 AM
    Author     : emmanuel
--%>


<%@page import="jspread.core.util.WebUtil"%>
<%@page import="jspread.core.util.UserUtil"%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>
<%    try {
        if (fine) {
            //if (request.getParameter(WebUtil.encode(session, "imix")) == null) {
            LinkedList<String> access4ThisPage = new LinkedList();
            access4ThisPage.add("LoggedUser");

            LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");
            if (UserUtil.isAValidUser(access4ThisPage, userAccess)) {
                if (PageParameters.getParameter("SiteOnMaintenance").equals("true")) {
                    String redirectURL = "" + PageParameters.getParameter("mainController") + "?exit=1";
                    response.sendRedirect(redirectURL);
                } else {
                    Iterator it = null;
                    LinkedList listAux = null;
%>
<!DOCTYPE html>
<html lang="<%=PageParameters.getParameter("Content-Language")%>">
    <head>
        <title>Menú Planteles</title>
        <jsp:include page='<%=PageParameters.getParameter("globalLibs")%>'/>        

        <script type="text/javascript" language="javascript" charset="utf-8">
            window.history.forward();
            function noBack() {
                window.history.forward();
            }
        </script>
    </head>

    <body>
        <div id="wrapper">
            <div id="divBody">
                <jsp:include page='<%=("" + PageParameters.getParameter("logo"))%>' />
                <p></p>
                <table width="100%" height="25px" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                        <td width="64%" height="50" align="left" valign="top">
                            Menú Principal
                        </td>
                        <td width="36" align="right" valign="top">
                            <script language="JavaScript" src="<%=PageParameters.getParameter("jsRcs")%>/funcionDate.js" type="text/javascript"></script>
                        </td>
                    </tr>                        
                </table>
                <br>
                <br>
                <form id="report" name="report" method="post" enctype="application/x-www-form-urlencoded" action="">
                    <input type="hidden" name="<%=WebUtil.encode(session, "imix")%>" value="<%=WebUtil.encode(session, UTime.getTimeMilis())%>"/>
                    <div >
                        <fieldset>
                            <legend>Seleccione el Plantel</legend>
                            <div>
                                <jsp:include page='<%=PageParameters.getParameter("ajaxFuntions") + "/getSelectPlantelSection.jsp?conTokyo=1"%>' flush = 'true'>
                                    <jsp:param name='sesion' value='<%=WebUtil.encode(session, "imix")%>' />

                                </jsp:include>
                            </div>
                            <p></p>
                            <input style="margin-left: 0px" type="submit" value="Ir a plantel" name="irPlantel" />           
                        </fieldset>
                    </div>


                </form>
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
    //System.out.println("Usuario No valido para esta pagina");
%>                
<%
    response.sendRedirect(PageParameters.getParameter("mainContext") + PageParameters.getParameter("LogInPage"));
%>
<%    }
    }
} catch (Exception ex) {
    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
%>
<%@ include file="/gui/pageComponents/handleUnExpectedError.jsp"%>
</body>
</html>
<%
    }
%>
