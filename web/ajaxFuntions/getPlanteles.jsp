<%-- 
    Document   : getPlanteles
    Created on : Oct 6, 2016, 11:35:57 AM
    Author     : emmanuel
--%>




<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<jsp:useBean id="QUIDAux" scope="page" class="jspread.core.db.QUID"/>
 
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>
<%

    String onChange = "";
    if (request.getParameter("onChange") != null) {

        switch (request.getParameter("onChange")) {
            case "addButtonDataSheet(this.value)":
                onChange = "addButtonDataSheet(this.value)";
                break;

        }
    }
%>

<div>
   
    <select  class="form-control" name="idPlantel" id="idPlantel" style="height: 30px; font-size: 15px; width: 250px; margin-left: 40%" onChange="<%=onChange%>">
        <option selected=""/>
        <%
            Iterator itI = null;
            LinkedList plantelList = null;

            plantelList = QUIDAux.getSelectPlantel();
            itI = plantelList.iterator();
            while (itI.hasNext()) {
                LinkedList listAuxI = null;
                listAuxI = (LinkedList) itI.next();
              
        %>
        <option value="<%=WebUtil.encode(session, listAuxI.get(0))%>" style="font-size: 15px;"><%=listAuxI.get(1)%></option>
        <%
            }
        %>

    </select>
</div>