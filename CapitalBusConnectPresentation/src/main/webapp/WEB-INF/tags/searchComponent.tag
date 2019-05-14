<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ tag description="Tag for search input and results" pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cbc" uri="http://eu.sanjin.cbc.com/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%-- Attributes: --%>
<%@ attribute name="searchUrl" required="true" type="java.lang.String" %>
<%@ attribute name="inputName" required="false" type="java.lang.String" %>
<%@ attribute name="inputValue" required="false" type="java.lang.String" %>
<%@ attribute name="inputPlaceholder" required="false" type="java.lang.String" %>
<%@ attribute name="required" required="false" type="java.lang.Boolean" %>
<%@ attribute name="inputId" required="false" type="java.lang.String" %>
<%@ attribute name="searchValue" required="false" type="java.lang.String" %>

<%-- Local Variables: --%>
<c:set var="realValue" value=""/>
<c:if test="${not empty searchValue}">
    <c:set var="realValue" value='data-value="${searchValue}"'/>
</c:if>

<%-- Content: --%>
<div class="searchContainer">
    <%--suppress HtmlFormInputWithoutLabel --%>
    <input class="searchContainer-searchInput" value="${inputValue}" id="${inputId}"
           placeholder="${inputPlaceholder}" ${required} name="${inputName}" ${realValue}
           onkeyup="Search.search(this, '${searchUrl}')"/>
    <ul class="searchContainer-elements"></ul>
    <%-- <li class="searchContainer-elements-element"></li> --%>
</div>