<%--
    Document   : menu
    Created on : Oct 27, 2018, 10:23:47 AM
    Author     : Sanjin Kurelic
--%>
<%@ tag description="Tag for displaying info item column" pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ tag import="eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItemColumnType" %>
<%@ tag import="eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItemButtonType" %>
<%@ tag import="eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItemIconType" %>

<%-- Attributes: --%>
<%@ attribute name="column" required="true" type="eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItemColumn" %>

<%-- Local Variables: --%>

<%-- Content: --%>
<c:choose>
    <c:when test="${column.columnType eq InfoItemColumnType.TEXT}">
        <td class="infoBox-item-column">${column.value}</td>
    </c:when>
    <c:when test="${column.columnType eq InfoItemColumnType.ICON}">
        <td class="infoBox-item-column">
            <c:choose>
                <c:when test="${column.value eq InfoItemIconType.ARROW_ICON}">
                    <span class="icon">&#xf061;</span>
                </c:when>
            </c:choose>
        </td>
    </c:when>
    <c:when test="${column.columnType eq InfoItemColumnType.BUTTON}">
        <td class="infoBox-item-button">
            <c:choose>
                <c:when test="${column.value eq InfoItemButtonType.EDIT_ROUTE}">
                    <a class="button"><span class="icon">&#xf044;</span></a>
                </c:when>
                <c:when test="${column.value eq InfoItemButtonType.BUY_INFO}">
                    <%--suppress ELValidationInJSP --%>
                    <spring:url value="admin/user/${column.urlId}" var="href"/>
                    <a class="button" href="${href}"><span class="icon">&#xf129;</span></a>
                </c:when>
            </c:choose>
        </td>
    </c:when>
</c:choose>