<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ page import="eu.sanjin.kurelic.cbc.view.components.AttributeNames" %>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>

<%-- Local variables: --%>

<%-- Content: --%>
<article>
    <cbc:menuComponent menu="${requestScope[AttributeNames.MENU_ITEM]}"/>
    <div class="center">
        <br>
        <p class="marginLeft">
            <spring:message code="statistics.numberOfPassengers.text"/>:
            ${requestScope[AttributeNames.NUMBER_OF_PASSENGERS]}
        </p>
        <p class="marginLeft">
            <spring:message code="statistics.numberOfTrips.text"/>:
            ${requestScope[AttributeNames.NUMBER_OF_LINE_ITEMS]}
        </p>
        <h2 class="article-title"><spring:message code="statistics.topTravelUser.title.text"/></h2>
        <c:choose>
            <c:when test="${empty requestScope[AttributeNames.USER_ITEMS]}">
                <spring:message code="empty.login.text"/>
            </c:when>
            <c:otherwise>
                <cbc:infoItems infoItems="${requestScope[AttributeNames.USER_ITEMS]}"/>
            </c:otherwise>
        </c:choose>
        <h2 class="article-title"><spring:message code="statistics.topBusLines.title.text"/></h2>
        <c:choose>
            <c:when test="${empty requestScope[AttributeNames.LINE_ITEMS]}">
                <spring:message code="empty.travel.criteria.text"/>
            </c:when>
            <c:otherwise>
                <cbc:infoItems infoItems="${requestScope[AttributeNames.LINE_ITEMS]}"/>
            </c:otherwise>
        </c:choose>
        <h2 class="article-title"><spring:message code="statistics.overbookedLines.title.text"/></h2>
        <c:choose>
            <c:when test="${empty requestScope[AttributeNames.OVERBOOKED_ITEMS]}">
                <spring:message code="empty.travel.criteria.text"/>
            </c:when>
            <c:otherwise>
                <cbc:infoItems infoItems="${requestScope[AttributeNames.OVERBOOKED_ITEMS]}"/>
            </c:otherwise>
        </c:choose>
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>
