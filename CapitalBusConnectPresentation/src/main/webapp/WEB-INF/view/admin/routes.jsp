<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>

<article>
    <cbc:menuComponent menu="${menuItem}"/>
    <div class="center">
        <div class="searchBar">
            <table class="searchBar-content">
                <tr>
                    <td><label for="fromCity"><spring:message code="searchBox.fromCity.placeholder"/></label></td>
                    <td><input name="fromCity" id="fromCity"/></td>
                    <td><label for="toCity"><spring:message code="searchBox.toCity.placeholder"/></label></td>
                    <td><input name="toCity" id="toCity"/></td>
                    <td><button><span class="icon">&#xf002;</span></button></td>
                </tr>
            </table>
        </div>
        <c:choose>
            <c:when test="${not empty routeItems}">
                <cbc:infoItem infoItems="${routeItems}"/>
                <cbc:paginationItem numberOfItems="${numberOfPages}" currentPageNumber="${currentPageNumber}"
                                    leftUrlPart="${leftUrlPart}"/>
            </c:when>
            <c:otherwise>
                <spring:message code="empty.route.text"/>
            </c:otherwise>
        </c:choose>
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>
