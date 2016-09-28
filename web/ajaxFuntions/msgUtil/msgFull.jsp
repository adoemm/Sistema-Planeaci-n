<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <title><%=PageParameters.getParameter("appName")%></title>
        <jsp:include page='<%=PageParameters.getParameter("globalLibs")%>'/>
    </head>
    <body>
        <script type="text/javascript" language="javascript" charset="utf-8">
            $.msgBox({
                title: "<%=request.getParameter("title")%>",
                content: "<%=request.getParameter("msg")%>",
                //info
                //confirm
                //error
                type: "<%=request.getParameter("type")%>",
                opacity: 0.75
            });
        </script>
    </body>
</html>
