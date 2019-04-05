<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>

<%@ page pageEncoding="UTF-8" %>
<%-- Imports --%>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>

<%-- Local variables --%>

<%-- Content --%>
<article>
    <cbc:menuComponent menu="${menuItem}"/>
    <div class="center">
        <table class="scheduleBox">
            <c:forEach var="scheduleItem" items="${scheduleItems}">
                <cbc:scheduleItem scheduleItem="${scheduleItem}"/>
            </c:forEach>
        </table>
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>