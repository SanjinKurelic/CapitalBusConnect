<%--
  Document   : travels
  Created on : 28.02.2019
  Author     : Sanjin Kurelic
--%>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>

<article>
    <cbc:menuComponent menu="${menuItem}"/>
    <div class="center">
        <table class="scheduleBox">
            <c:forEach var="scheduleItem" items="${scheduleItems}">
                <cbc:scheduleItem scheduleItem="${scheduleItem}"/>
            </c:forEach>
        </table>
        <c:if test="${empty scheduleItems}">
            <p><spring:message code="empty.schedule.text"/></p>
        </c:if>
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>