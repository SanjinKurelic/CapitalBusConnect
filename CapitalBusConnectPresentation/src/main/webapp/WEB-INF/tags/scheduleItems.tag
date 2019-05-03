<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ tag description="Tag for displaying schedule items" pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cbc" tagdir="/WEB-INF/tags" %>

<%-- Attributes: --%>
<%@ attribute name="scheduleItems" required="true"
              type="eu.sanjin.kurelic.cbc.business.viewmodel.schedule.ScheduleItems" %>

<%-- Local Variables: --%>

<%-- Content: --%>
<table class="scheduleBox">
    <c:forEach var="scheduleItem" items="${scheduleItems}">
        <cbc:scheduleItem scheduleItem="${scheduleItem}"/>
    </c:forEach>
</table>