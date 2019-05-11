<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ page import="eu.sanjin.kurelic.cbc.view.components.AttributeNames" %>
<%@ include file="/WEB-INF/view/components/header/banner.jsp" %>

<%-- Local variables: --%>

<%-- Content: --%>
<article>
    <cbc:menuComponent menu="${requestScope[AttributeNames.MENU_ITEM]}"/>
    <div class="center">
        <cbc:scheduleItems scheduleItems="${requestScope[AttributeNames.SCHEDULE_ITEMS]}"/>
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jsp" %>