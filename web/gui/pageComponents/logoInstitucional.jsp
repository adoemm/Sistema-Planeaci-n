
<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>

<!--
<div id="headerFrame" style="position:relative;text-align:center;left:0px;top:0px;width:100%;height:181px;z-index:4;" title="">
    <div id="headerFrame_Container" style="width:900px;position:relative;margin-left:auto;margin-right:auto;text-align:left;">
    </div>
</div>
-->
<div id="headerFrame" style="position:relative;text-align:center;height:181px;z-index:4;" title="">
    <div id="headerFrame_Container" style="width:100%;position:relative;text-align:left;">
    </div>
</div>
<div id="HeaderLayer" style="position:absolute;text-align:left;top:0px;width:900px;height:197px;z-index:5;" title="">
    <div id="imgLogo" style="position:absolute;left:0px;top:29px;width:800px;height:153px;z-index:0;padding:0;">
        <img src="<%=PageParameters.getParameter("imgRsc")%>/headerc2.png" id="imgLogo" alt="" border="0" style="width:850px;height:153px;"></div>
    <div id="organizacion_empresa" style="position:absolute;left:235px;top:95px;width:700px;height:15px;z-index:1;">
        <span style="color:#282828;font-family:'Gotham Book';font-size:17px;"><strong>Colegio de Estudios Científicos y Tecnológicos del Estado de México</strong></span></div>
    <div id="nombreSistema" style="position:absolute;left:235px;top:122px;width:700px;height:15px;z-index:2;">
        <span style="color:#DA001B;font-family:'Gotham Book';font-size:15px;"><strong>SPIC -Sistema de Planeación Institucional de CECYTEM -Rev. <%=PageParameters.getParameter("revision")%><%=PageParameters.getParameter("testDBLabel")%></strong></span></div>
    <div id="wb_LogoGov" style="position:absolute;left:223px;top:29px;width:64px;height:24px;z-index:3;padding:0;">
        <img src="<%=PageParameters.getParameter("imgRsc")%>/gobiernogn.png" id="LogoGov" alt="" border="0" style="width:64px;height:24px;"></div>
</div>