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
        <div class="searchBar wrap">
            <table class="searchBar-content">
                <tr>
                    <td><label for="fromCity"><spring:message code="searchBox.fromCity.placeholder"/></label></td>
                    <td><input name="fromCity" id="fromCity"/></td>
                    <td><label for="toCity"><spring:message code="searchBox.toCity.placeholder"/></label></td>
                    <td><input name="toCity" id="toCity"/></td>
                    <td>
                        <button><span class="icon">&#xf002;</span></button>
                    </td>
                </tr>
            </table>
        </div>
        <c:choose>
            <c:when test="${not empty requestScope[AttributeNames.ROUTE_ITEMS]}">
                <cbc:infoItems infoItems="${requestScope[AttributeNames.ROUTE_ITEMS]}"/>
                <cbc:paginationComponent
                        numberOfItems="${requestScope[AttributeNames.PAGINATION_NUMBER_OF_ITEMS]}"
                        currentPageNumber="${requestScope[AttributeNames.PAGINATION_CURRENT_PAGE]}"
                        leftUrlPart="${requestScope[AttributeNames.PAGINATION_LEFT_URL_PART]}"/>
            </c:when>
            <c:otherwise>
                <p><spring:message code="empty.route.text"/></p>
            </c:otherwise>
        </c:choose>
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>
