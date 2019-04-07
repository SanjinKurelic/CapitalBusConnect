<%--
    Document   : menu
    Created on : Oct 27, 2018, 10:23:47 AM
    Author     : Sanjin Kurelic
--%>
<%@ tag description="Tag for displaying info item" pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cbc" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%-- Attributes: --%>
<%@ attribute name="infoItems" required="true" type="eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItems" %>

<%-- Local Variables: --%>

<%-- Content: --%>
<table class="infoBox">
    <%--@elvariable id="infoItem" type="eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItem"--%>
    <c:forEach var="infoItem" items="${infoItems}">
        <tr class="infoBox-item">
            <c:forEach var="infoItemColumn" items="${infoItem.columns}">
                <cbc:infoItemColumn column="${infoItemColumn}"/>
            </c:forEach>
        </tr>
    </c:forEach>
</table>