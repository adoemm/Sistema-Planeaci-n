<%@page import="jspread.core.util.WebUtil"%>
<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java"%>
<%@ include file="/gui/pageComponents/globalSettings.jsp"%>

<div class="menu">
    <ul>

        <!-- Using class="single-link" for links with no dropdown-->

        <li class="single-link">
            <a target="_self" href="<%=PageParameters.getParameter("mainController")%>?exit=1">Salir</a>
        </li>


        <!-- Using class="current" for the link of the current page -->
        <!--
        <li class="single-link">
            <a target="_blank" href="http://www.google.co.in/">Web</a>
        </li>
        -->
        <!-- Do not add any class for links with dropdown -->
        <!-- Drop Down menu Items -->
        <!--
<li>
<a href="#">More<span class="arrow"></span></a>
<ul>
    <li><a href="http://www.google.co.in/reader">Reader</a></li>
    <li><a href="https://sites.google.com">Sites</a></li>
    <li><a href="http://groups.google.co.in">Groups</a></li>
    <li><a href="http://www.youtube.com">YouTube</a></li>
    <li>
        <div class="mid-line">
        </div>
    </li>
    <li><a href="http://www.google.co.in/imghp?hl=en&tab=wi">Images</a></li>
    <li><a href="http://maps.google.co.in/maps?hl=en&tab=wl">Maps</a></li>
    <li><a href="http://translate.google.co.in/">Translate</a></li>
    <li><a href="http://books.google.co.in">Books</a></li>
    <li><a href="http://scholar.google.co.in/">Scholar</a></li>
    <li><a href="http://blogsearch.google.co.in">Blogs</a></li>
    <li>
        <div class="mid-line">
        </div>
    </li>
    <li><a href="http://www.google.co.in/intl/en/options/">even more >></a></li>
    <li>
        <div class="mid-line">
        </div>
    </li>
</ul>
</li>
        -->
        <li class="single-link">
            <a target="_self" >
                <%=session.getAttribute("userName")%>
            </a>
        </li>
        <li class="current"><!-- Using class="single-link" for links with no dropdown -->
            <a target="_self" href="<%=PageParameters.getParameter("mainMenu")%>">Menú Principal</a>
        </li>
    </ul>
</div>