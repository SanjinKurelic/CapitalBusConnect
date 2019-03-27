<%--
    Document   : menu
    Created on : Oct 27, 2018, 10:23:47 AM
    Author     : Sanjin Kurelic
--%>
<%@ tag description="Tag for displaying info item column" pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag import="eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItemColumnType" %>

<%-- Attributes: --%>
<%@ attribute name="type" required="true" type="eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItemColumnType" %>
<%@ attribute name="value" required="true" type="java.lang.String" %>

<%-- Local Variables: --%>

<%-- Content: --%>
<c:choose>
    <c:when test="${type eq InfoItemColumnType.TEXT}">
        ${value}
    </c:when>
    <c:when test="${type eq InfoItemColumnType.ARROW_ICON}">
        <span class="icon">&#xf061;</span>
    </c:when>
</c:choose>