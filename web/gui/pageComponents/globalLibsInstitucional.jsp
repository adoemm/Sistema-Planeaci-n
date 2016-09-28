<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="<%=PageParameters.getParameter("Content-Language")%>">
<meta charset="utf-8">
<!--/////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
<script type="text/javascript" language="javascript" charset="utf-8" src="<%=PageParameters.getParameter("JQueryLink")%>"></script>
<script type="text/javascript" language="javascript" charset="utf-8" src="<%=PageParameters.getParameter("googleBarScript")%>"></script>
<script type="text/javascript" language="javascript" charset="utf-8">
    $('document').ready(function() {
        $('.menu').fixedMenu();
    });
    function openDialogBox(idFloatBox) {
                document.getElementById('overlay').style.display = 'block';
                document.getElementById(idFloatBox).style.display = 'block';
            }
            function closeDialogBox(idFloatBox) {
                document.getElementById(idFloatBox).style.display = 'none';
                document.getElementById('overlay').style.display = 'none';
            }
</script>
<script src="<%=PageParameters.getParameter("jqRsc")%>/jq.msgBox/Scripts/jquery.msgBox.js" type="text/javascript"></script>
<script src="<%=PageParameters.getParameter("jqDataTables")%>/media/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="<%=PageParameters.getParameter("jqDataTables")%>/media/js/jquery.jeditable.js" type="text/javascript"></script>
<script src="<%=PageParameters.getParameter("jqRsc")%>/date-picker/js/datepicker.js" type="text/javascript"></script>
<script src="<%=PageParameters.getParameter("jqRsc")%>/jqLoadingDot/jquery.loadingdotdotdot.js" type="text/javascript"></script>
<script src="<%=PageParameters.getParameter("jsRcs")%>/globalFunctions.js" type="text/javascript"></script>
<!--
<script type="text/javascript" language="javascript" charset="utf-8">
    window.history.forward();
    function noBack() {
        window.history.forward();
    }
</script>
-->

<!--/////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
<link href="<%=PageParameters.getParameter("faviconLink")%>" rel='shortcut icon' type='image/ico'>
<link href="<%out.print(PageParameters.getParameter("googleBarCSS").toString());%>" rel="stylesheet" type="text/css" /> 
<link href="<%=PageParameters.getParameter("jqRsc")%>/jq.msgBox/Styles/msgBoxLight.css" rel="stylesheet" type="text/css" />
<link href="<%=PageParameters.getParameter("jqDataTables")%>/media/css/demo_table_jui.css" rel="stylesheet" type="text/css" />
<link href="<%=PageParameters.getParameter("jqDataTables")%>/media/themes/smoothness/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
<link href="<%=PageParameters.getParameter("jqRsc")%>/date-picker/css/datepicker.css" rel="stylesheet" type="text/css" />

<link href="<%out.print(PageParameters.getParameter("cssRsc").toString());%>/mainStyleInstitucional.css" rel="stylesheet" type="text/css" /> 
<link href="<%out.print(PageParameters.getParameter("cssRsc").toString());%>/EstiloFormInstitucional.css" rel="stylesheet" type="text/css"/> 
<link href="<%out.print(PageParameters.getParameter("cssRsc").toString());%>/prints.css" rel="stylesheet" type="text/css" /> 

<!--/////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
