<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ tag description="Tag for displaying info item" pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cbcf" uri="/WEB-INF/tlds/functions" %>
<%@ tag import="eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItemButtonType" %>
<%@ tag import="eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItemColumnType" %>
<%@ tag import="eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItemIconType" %>
<%@ tag import="eu.sanjin.kurelic.cbc.view.controller.web.AdminController" %>

<%-- Attributes: --%>
<%@ attribute name="infoItem" required="true" type="eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItem" %>

<%-- Local Variables: --%>

<%-- Content: --%>
<tr class="infoBox-item">
    <c:forEach var="column" items="${infoItem.columns}">
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
                            <spring:url value="${cbcf:buildUrl(AdminController.USER_FULL_URL, column.urlId)}"
                                        var="href"/>
                            <a class="button" href="${href}"><span class="icon">&#xf129;</span></a>
                        </c:when>
                    </c:choose>
                </td>
            </c:when>
        </c:choose>
    </c:forEach>
</tr>