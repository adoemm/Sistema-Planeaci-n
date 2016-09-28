<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <title><%=PageParameters.getParameter("appName")%></title>
        <jsp:include page='<%=PageParameters.getParameter("globalLibs")%>'/>
        <script type="text/javascript" language="javascript" charset="utf-8">
            function mensaje() {
                $.msgBox({
                    title: "<%=request.getParameter("title")%>",
                    content: "<%=request.getParameter("msg")%>",
                    //info
                    //confirm
                    //error
                    type: "<%=request.getParameter("type")%>",
                    opacity: 0.75,
                    beforeClose: function() {
                        window.location.href = '<%out.print(request.getParameter("url").replaceAll("_param_", "&"));%>';
                    }
                });
            }
        </script>
    </head>
    <body onload="mensaje();">
    </body>
</html>
