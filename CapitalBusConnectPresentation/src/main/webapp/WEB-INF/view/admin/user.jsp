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
        <div class="searchBar">
            <table class="searchBar-content" data-url="${requestScope[AttributeNames.SEARCH_URL]}">
                <tr>
                    <td><label for="dateSearch"><spring:message code="searchBox.date.text"/></label></td>
                    <td>
                        <input data-type="date"
                               data-max="<fmt:formatDate value='${currentDate}' pattern='yyyy-MM-dd'/>"
                               name="dateSearch" id="dateSearch"
                               value="${requestScope[AttributeNames.SEARCH_DATE]}"
                               required
                        >
                    </td>
                    <td></td>
                    <td></td>
                    <td>
                        <button onclick="Search.findResult(this)"><span class="icon">&#xf002;</span></button>
                    </td>
                </tr>
            </table>
        </div>
        <h2 class="article-title"><spring:message code="userData.loginTitle.text"/></h2>
        <c:choose>
            <c:when test="${not empty requestScope[AttributeNames.LOGIN_ITEMS]}">
                <cbc:infoItems infoItems="${requestScope[AttributeNames.LOGIN_ITEMS]}"/>
                <cbc:paginationComponent
                        numberOfItems="${requestScope[AttributeNames.PAGINATION_NUMBER_OF_ITEMS.concat(AttributeNames.PAGINATION_APPENDER_LOGIN)]}"
                        currentPageNumber="${requestScope[AttributeNames.PAGINATION_CURRENT_PAGE.concat(AttributeNames.PAGINATION_APPENDER_LOGIN)]}"
                        leftUrlPart="${requestScope[AttributeNames.PAGINATION_LEFT_URL_PART.concat(AttributeNames.PAGINATION_APPENDER_LOGIN)]}"
                        rightUrlPart="${requestScope[AttributeNames.PAGINATION_RIGHT_URL_PART.concat(AttributeNames.PAGINATION_APPENDER_LOGIN)]}"
                />
            </c:when>
            <c:otherwise>
                <p><spring:message code="empty.login.text"/></p>
            </c:otherwise>
        </c:choose>
        <h2 class="article-title"><spring:message code="userData.travelTitle.text"/></h2>
        <c:choose>
            <c:when test="${not empty requestScope[AttributeNames.TRAVEL_ITEMS]}">
                <cbc:scheduleItems scheduleItems="${requestScope[AttributeNames.TRAVEL_ITEMS]}"/>
                <cbc:paginationComponent
                        numberOfItems="${requestScope[AttributeNames.PAGINATION_NUMBER_OF_ITEMS.concat(AttributeNames.PAGINATION_APPENDER_TRAVEL)]}"
                        currentPageNumber="${requestScope[AttributeNames.PAGINATION_CURRENT_PAGE.concat(AttributeNames.PAGINATION_APPENDER_TRAVEL)]}"
                        leftUrlPart="${requestScope[AttributeNames.PAGINATION_LEFT_URL_PART.concat(AttributeNames.PAGINATION_APPENDER_TRAVEL)]}"
                        rightUrlPart="${requestScope[AttributeNames.PAGINATION_RIGHT_URL_PART.concat(AttributeNames.PAGINATION_APPENDER_TRAVEL)]}"
                />
            </c:when>
            <c:otherwise>
                <p><spring:message code="empty.travel.criteria.text"/></p>
            </c:otherwise>
        </c:choose>
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>