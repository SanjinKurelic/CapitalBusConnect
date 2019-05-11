<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ include file="/WEB-INF/view/components/header/banner.jsp" %>

<%-- Local Variables: --%>

<%-- Content: --%>
<%@ include file="/WEB-INF/view/components/header/searchBox.jsp" %>
<article class="center">
    <cbc:scheduleItems scheduleItems="${requestScope[AttributeNames.SCHEDULE_ITEMS]}"/>
    <c:if test="${empty requestScope[AttributeNames.SCHEDULE_ITEMS]}">
        <p><spring:message code="empty.schedule.text"/></p>
    </c:if>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jsp" %>
