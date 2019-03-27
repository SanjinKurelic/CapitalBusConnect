<%--
    Document   : menu
    Created on : Oct 27, 2018, 10:23:47 AM
    Author     : Sanjin Kurelic
--%>
<%@ tag description="Tag for displaying menu item" pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cbf" uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%-- Attributes: --%>
<%@ attribute name="numberOfPages" required="true" type="java.lang.Integer" %>
<%@ attribute name="currentPageNumber" required="true" type="java.lang.Integer" %>

<%-- Local Variables: --%>
<c:set var="href" value=""/>
<c:set var="disabled" value="disabled"/>
<c:set var="active" value="" />

<%-- Content: --%>
<div class="paginationBox">
    <%-- LEFT BUTTON --%>
    <c:choose>
        <c:when test="${currentPageNumber > 1}">
            <c:set var="href" value="${pageContext.request.requestURL.toString()}/${currentPageNumber - 1}"/>
            <c:set var="disabled" value=""/>
        </c:when>
        <c:otherwise>
            <c:set var="href" value=""/>
            <c:set var="disabled" value="disabled"/>
        </c:otherwise>
    </c:choose>
    <a class="paginationBox-button ${disabled}" href="<spring:url value="${href}" />">
        <span class="paginationBox-button-icon icon">&#xf053;</span>
        <span class="paginationBox-button-text"><spring:message code="navigation.previousPage.text"/></span>
    </a>
    <%-- PAGE CHOOSE --%>
    <div class="paginationBox-numbers">
        <table class="paginationBox-numbers-items">
            <tr>
                <c:forEach var="number" items="${cbf:getPageList(currentPageNumber, numberOfPages)}">
                    <c:choose>
                        <c:when test="${number eq currentPageNumber}">
                            <c:set var="active" value="active" />
                        </c:when>
                        <c:otherwise>
                            <c:set var="active" value="" />
                        </c:otherwise>
                    </c:choose>
                    <td class="paginationBox-numbers-items-item ${active}">
                        <a href="${pageContext.request.requestURL.toString()}/${number}">${number}</a>
                    </td>
                </c:forEach>
            </tr>
        </table>
    </div>
    <%-- RIGHT BUTTON --%>
    <c:choose>
        <c:when test="${currentPageNumber < numberOfPages}">
            <c:set var="href" value="${pageContext.request.requestURL.toString()}/${currentPageNumber + 1}"/>
            <c:set var="disabled" value=""/>
        </c:when>
        <c:otherwise>
            <c:set var="href" value=""/>
            <c:set var="disabled" value="disabled"/>
        </c:otherwise>
    </c:choose>
    <a class="paginationBox-button ${disabled}" href="<spring:url value="${href}" />">
        <span class="paginationBox-button-text"><spring:message code="navigation.nextPage.text"/></span>
        <span class="paginationBox-button-icon icon">&#xf054;</span>
    </a>
</div>