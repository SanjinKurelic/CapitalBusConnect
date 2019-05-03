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
        <cbc:scheduleItems scheduleItems="${requestScope[AttributeNames.SCHEDULE_ITEMS]}"/>
        <c:choose>
            <c:when test="${empty requestScope[AttributeNames.SCHEDULE_ITEMS]}">
                <p><spring:message code="empty.travel.text"/></p>
            </c:when>
            <c:otherwise>
                <cbc:paginationComponent
                        numberOfItems="${requestScope[AttributeNames.PAGINATION_NUMBER_OF_ITEMS]}"
                        currentPageNumber="${requestScope[AttributeNames.PAGINATION_CURRENT_PAGE]}"
                        leftUrlPart="${requestScope[AttributeNames.PAGINATION_LEFT_URL_PART]}"
                />
            </c:otherwise>
        </c:choose>
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>