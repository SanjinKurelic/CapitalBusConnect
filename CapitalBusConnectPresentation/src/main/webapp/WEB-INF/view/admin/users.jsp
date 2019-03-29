<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports --%>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>

<article>
    <cbc:menuComponent menu="${menuItem}"/>
    <div class="center">
        <div class="searchBar">
            <table class="searchBar-content">
                <tr>
                    <td><label for="username"><spring:message code="searchBox.username.text"/></label></td>
                    <td><input name="username" id="username" value="${username}"/></td>
                    <td><label for="date"><spring:message code="searchBox.date.text"/></label></td>
                    <td><input data-type="date" name="date" id="date" value="${date}"></td>
                    <td><button><span class="icon">&#xf002;</span></button></td>
                </tr>
            </table>
        </div>
        <c:choose>
            <c:when test="${not empty loginItems}">
                <cbc:infoItem infoItems="${loginItems}"/>
                <cbc:paginationItem numberOfItems="${numberOfPages}" currentPageNumber="${currentPage}"
                                    leftUrlPart="${leftUrlPart}" rightUrlPart="${rightUrlPart}"/>
            </c:when>
            <c:otherwise>
                <spring:message code="empty.login.text"/>
            </c:otherwise>
        </c:choose>
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>
