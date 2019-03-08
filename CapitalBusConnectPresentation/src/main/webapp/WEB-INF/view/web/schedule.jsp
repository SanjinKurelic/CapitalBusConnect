<%-- 
    Document   : schedule
    Created on : Nov 1, 2018, 11:47:10 AM
    Author     : Sanjin Kurelic
--%>

<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>
<%@ include file="/WEB-INF/view/components/header/searchBox.jspf" %>
<article class="center">
    <table class="scheduleBox">
        <c:forEach var="scheduleItem" items="${scheduleItems}">
            <cbc:scheduleItem scheduleItem="${scheduleItem}" />
        </c:forEach>
    </table>
    <c:if test="${empty scheduleItems}">
        <p><spring:message code="empty.schedule.text"/></p>
    </c:if>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>
