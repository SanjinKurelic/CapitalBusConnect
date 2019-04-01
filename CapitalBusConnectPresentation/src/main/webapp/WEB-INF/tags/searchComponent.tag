<%--
    Document   : menu
    Created on : Oct 27, 2018, 10:23:47 AM
    Author     : Sanjin Kurelic
--%>
<%@ tag description="Tag for search input and results" pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cbc" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%-- Attributes: --%>
<%@ attribute name="searchUrl" required="true" type="java.lang.String" %>
<%@ attribute name="inputName" required="false" type="java.lang.String" %>
<%@ attribute name="inputValue" required="false" type="java.lang.String" %>
<%@ attribute name="inputPlaceholder" required="false" type="java.lang.String" %>
<%@ attribute name="inputExtraAttribute" required="false" type="java.lang.String" %>
<%@ attribute name="inputId" required="false" type="java.lang.String" %>

<%-- Local Variables: --%>

<%-- Content: --%>
<div class="searchContainer">
    <input class="searchContainer-searchInput" value="${inputValue}" id="${inputId}"
           placeholder="${inputPlaceholder}" ${inputExtraAttribute} name="${inputName}"
           onkeyup="Search.search(this, '${searchUrl}')"/>
    <ul class="searchContainer-elements"></ul>
    <%-- <li class="searchContainer-elements-element"></li> --%>
</div>