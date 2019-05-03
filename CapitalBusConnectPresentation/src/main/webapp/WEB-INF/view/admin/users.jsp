<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ page import="eu.sanjin.kurelic.cbc.view.components.AttributeNames" %>
<%@ page import="eu.sanjin.kurelic.cbc.view.controller.api.SearchController" %>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>

<%-- Local variables: --%>

<%-- Content: --%>
<article>
    <cbc:menuComponent menu="${requestScope[AttributeNames.MENU_ITEM]}"/>
    <div class="center">
        <div class="searchBar">
            <table class="searchBar-content" data-url="${requestScope[AttributeNames.SEARCH_URL]}">
                <tr>
                    <td>
                        <%--suppress XmlInvalidId --%>
                        <label for="usernameSearch">
                            <spring:message code="searchBox.username.text"/>
                        </label>
                    </td>
                    <td>
                        <cbc:searchComponent searchUrl="${SearchController.SEARCH_USER_FULL_URL}"
                                             inputName="usernameSearch"
                                             inputId="usernameSearch"
                                             inputValue="${requestScope[AttributeNames.SEARCH_USERNAME]}"
                                             required="true"/>
                    </td>
                    <td></td>
                    <td></td>
                    <td>
                        <button onclick="Search.findResult(this)">
                            <span class="icon">&#xf002;</span>
                        </button>
                    </td>
                </tr>
            </table>
        </div>
        <c:choose>
            <c:when test="${not empty requestScope[AttributeNames.LOGIN_ITEMS]}">
                <cbc:infoItems infoItems="${requestScope[AttributeNames.LOGIN_ITEMS]}"/>
                <cbc:paginationComponent
                        numberOfItems="${requestScope[AttributeNames.PAGINATION_NUMBER_OF_ITEMS]}"
                        currentPageNumber="${requestScope[AttributeNames.PAGINATION_CURRENT_PAGE]}"
                        leftUrlPart="${requestScope[AttributeNames.PAGINATION_LEFT_URL_PART]}"
                        rightUrlPart="${requestScope[AttributeNames.PAGINATION_RIGHT_URL_PART]}"
                />
            </c:when>
            <c:otherwise>
                <p><spring:message code="empty.login.text"/></p>
            </c:otherwise>
        </c:choose>
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>