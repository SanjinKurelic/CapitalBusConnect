<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>

<article>
    <cbc:menuComponent menu="${menuItem}" />
    <div class="center">
        <div class="searchBar">
            <table class="searchBar-content" data-url="${searchUrl}">
                <tr>
                    <td><label for="dateSearch"><spring:message code="searchBox.date.text"/></label></td>
                    <td><input data-type="date" data-max="<fmt:formatDate value='${currentDate}' pattern='yyyy-MM-dd'/>" name="dateSearch" id="dateSearch" value="${date}" required></td>
                    <td></td>
                    <td></td>
                    <td><button onclick="Search.findResult(this)"><span class="icon">&#xf002;</span></button></td>
                </tr>
            </table>
        </div>
        <h2 class="article-title"><spring:message code="userData.loginTitle.text"/></h2>
        <c:choose>
            <c:when test="${not empty loginItems}">
                <cbc:infoItem infoItems="${loginItems}"/>
                <cbc:paginationItem numberOfItems="${numberOfLoginPages}" currentPageNumber="${currentLoginPage}" leftUrlPart="${leftLoginUrlPart}" rightUrlPart="${rightLoginUrlPart}" />
            </c:when>
            <c:otherwise>
                <p><spring:message code="empty.login.text" /></p>
            </c:otherwise>
        </c:choose>
        <h2 class="article-title"><spring:message code="userData.travelTitle.text"/></h2>
        <c:choose>
            <c:when test="${not empty travelItems}">
                <table class="scheduleBox">
                    <c:forEach var="scheduleItem" items="${travelItems}">
                        <cbc:scheduleItem scheduleItem="${scheduleItem}" />
                    </c:forEach>
                </table>
                <cbc:paginationItem numberOfItems="${numberOfTravelPages}" currentPageNumber="${currentTravelPage}" leftUrlPart="${leftTravelUrlPart}" rightUrlPart="${rightTravelUrlPart}" />
            </c:when>
            <c:otherwise>
                <p><spring:message code="empty.travel.criteria.text" /></p>
            </c:otherwise>
        </c:choose>
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>
