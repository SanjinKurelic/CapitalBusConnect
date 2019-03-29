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
<%@ tag import="eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItemButtonType" %>

<%-- Attributes: --%>
<%@ attribute name="infoItems" required="true" type="eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItems" %>

<%-- Local Variables: --%>

<%-- Content: --%>
<table class="infoBox">
    <%--@elvariable id="infoItem" type="eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItem"--%>
    <c:forEach var="infoItem" items="${infoItems}">
        <tr class="infoBox-item">
            <td class="infoBox-item-column">
                <cbc:infoItemColumn type="${infoItem.columnType1}" value="${infoItem.column1}"/>
            </td>
            <td class="infoBox-item-column">
                <cbc:infoItemColumn type="${infoItem.columnType2}" value="${infoItem.column2}"/>
            </td>
            <td class="infoBox-item-column">
                <cbc:infoItemColumn type="${infoItem.columnType3}" value="${infoItem.column3}"/>
            </td>
            <td class="infoBox-item-button">
                <c:choose>
                    <c:when test="${infoItem.buttonType eq InfoItemButtonType.EDIT_ROUTE}">
                        <a class="button"><span class="icon">&#xf044;</span></a>
                    </c:when>
                    <c:when test="${infoItem.buttonType eq InfoItemButtonType.BUY_INFO}">
                        <spring:url value="admin/user/${infoItem.column1}" var="href"/>
                        <a class="button" href="${href}"><span class="icon">&#xf129;</span></a>
                    </c:when>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>