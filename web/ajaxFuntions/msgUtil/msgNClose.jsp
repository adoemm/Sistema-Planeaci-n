<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>

<script type="text/javascript" language="javascript" charset="utf-8">
    $.msgBox({
        title: "<%=request.getParameter("title")%>",
        content: "<%=request.getParameter("msg")%>",
        //info
        //confirm
        //error
        type: "<%=request.getParameter("type")%>",
        opacity: 0.75,
        beforeClose: function() {
            window.close();
        }
    });
</script>

