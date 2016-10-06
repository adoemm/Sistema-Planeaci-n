<%-- 
    Document   : plantelSelect
    Created on : Oct 5, 2016, 11:59:40 AM
    Author     : emmanuel
--%>



<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>
<jsp:useBean id="QUIDAux" scope="page" class="jspread.core.db.QUID"/>



<%    try {
        if (fine) {
            if (request.getParameter(WebUtil.encode(session, "imix")) != null) {
                String access4ThisPage = "accessPlantelSelect";
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
        <title>Selección Plantel</title>
        <jsp:include page='<%=PageParameters.getParameter("globalLibs")%>'/>        

        <script type="text/javascript" language="javascript" charset="utf-8">
            window.history.forward();
            function noBack() {
                window.history.forward();
            }

            function addButtonDataSheet(idPlantel)
            {
                 if (idPlantel !== null) {
                    $.ajax({type: 'POST'
                        , contentType: 'application/x-www-form-urlencoded;charset=utf-8'
                        , cache: false
                        , async: false
                        , url: "<%=PageParameters.getParameter("mainContext") + PageParameters.getParameter("ajaxFuntions")%>/compruebaFichaTecnica.jsp"
                        , data: '<%=WebUtil.encode(session, "imix")%>=<%=WebUtil.encode(session, UTime.getTimeMilis())%>&idPlantel=' + idPlantel
                        , success: function(response) {
                            $('#wrapper').find('#divBody').find('#bodySelectPlantel').find('#selectPlantel').find('#divDataSheet').html(response);
                        }});
                }

            }
        </script>
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
                <div id="bodySelectPlantel">
                    <form name="selectPlantel" method="post" enctype="application/x-www-form-urlencoded">
                        <input type="hidden" name="<%=WebUtil.encode(session, "imix")%>" value="<%=WebUtil.encode(session, UTime.getTimeMilis())%>"/>

                        <fieldset id="selectPlantel"style="width: 50%; align-items: center;  margin-left: 25%; margin-bottom: 5%; ">
                            <legend align="center">Seleccione el Plantel</legend>
                            <br>
                            <div style="margin-left: 30%">
                                <jsp:include page='<%=PageParameters.getParameter("ajaxFuntions") + "/getPlanteles.jsp"%>' flush = 'true'>
                                    <jsp:param name='onChange' value='addButtonDataSheet(this.value)' />
                                </jsp:include> 
                            </div>
                            <br>
                            <div id="divDataSheet"></div>

                        </fieldset>



                    </form>
                    <div id="divFoot">
                        <jsp:include page='<%=(PageParameters.getParameter("footer"))%>' />
                    </div> 

                </div>            
            </div>
        </div>
    </body>

</html>
<<%
    }
} else {
    //System.out.println("Usuario No valido para esta pagina");
%>                
<%@ include file="/gui/pageComponents/invalidUser.jsp"%>
<%    }
} else {
    //System.out.println("No se ha encontrado a imix");
%>
<%@ include file="/gui/pageComponents/invalidParameter.jsp"%>
<%        }
    }
} catch (Exception ex) {
    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
%>
<%@ include file="/gui/pageComponents/handleUnExpectedError.jsp"%>
</body>
</html>
<%
        //response.sendRedirect(redirectURL);
    }
%>
