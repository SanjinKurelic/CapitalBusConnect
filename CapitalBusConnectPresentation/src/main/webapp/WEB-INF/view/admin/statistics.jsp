<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>

<article>
    <cbc:menuComponent menu="${menuItem}"/>
    <div class="center">
        <br>
        <p><spring:message code="statistics.numberOfPassengers.text"/>: ${numberOfPassengers}</p>
        <p><spring:message code="statistics.numberOfTrips.text"/>: ${numberOfTrips}</p>
        <h2 class="article-title">Users with highest number of travels</h2>
        <c:choose>
            <c:when test="${userItems.size() < 1}">
                <spring:message code="empty.login.text"/>
            </c:when>
            <c:otherwise>
                <cbc:infoItem infoItems="${userItems}"/>
            </c:otherwise>
        </c:choose>
        <h2 class="article-title">Bus lines with highest number of operating</h2>
        <c:choose>
            <c:when test="${lineItems.size() < 1}">
                <spring:message code="empty.travel.criteria.text"/>
            </c:when>
            <c:otherwise>
                <cbc:infoItem infoItems="${lineItems}"/>
            </c:otherwise>
        </c:choose>
        <h2 class="article-title">Overbooked bus lines</h2>
        <c:choose>
            <c:when test="${overbookedItems.size() < 1}">
                <spring:message code="empty.travel.criteria.text"/>
            </c:when>
            <c:otherwise>
                <cbc:infoItem infoItems="${overbookedItems}"/>
            </c:otherwise>
        </c:choose>
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>
