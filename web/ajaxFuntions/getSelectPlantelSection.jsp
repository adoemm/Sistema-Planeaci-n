
<%@page import="jspread.core.util.WebUtil"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>

<jsp:useBean id="QUIDAux" scope="page" class="jspread.core.db.QUID"/>

<%
    LinkedList<String> userAccess = (LinkedList<String>) session.getAttribute("userAccess");

%>

<div>
    <label for="idPlantel">*Plantel</label>
    <select name="idPlantel" id="idPlantel" >

        <%            Iterator itI = null;
            LinkedList plantelList = null;
            plantelList = QUIDAux.getSelectPlantel();
            itI = plantelList.iterator();
            
            while (itI.hasNext()) {
                String plantel;
                plantel= itI.next().toString();
                int y=6;
        %>
       
            <option value="<%=WebUtil.encode(session, plantel)%>" selected><%=plantel %></option>
               

        <%   }     %>
    </select>
</div>