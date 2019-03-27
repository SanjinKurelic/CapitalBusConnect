<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>

<article>
    <cbc:menuComponent menu="${menuItem}" />
    <div class="center">
        <!-- Search bar -->
        <input name="date" value="${date}" />
        <cbc:infoItem infoItems="${item}"/>
        <cbc:paginationItem numberOfPages="${numberOfLoginPages}" currentPageNumber="${currentLoginPage}" />

        <c:forEach var="scheduleItem" items="${travelItems}">
            <cbc:scheduleItem scheduleItem="${scheduleItem}" />
        </c:forEach>
        <cbc:paginationItem numberOfPages="${numberOfTravelPages}" currentPageNumber="${currentTravelPage}" />
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>
