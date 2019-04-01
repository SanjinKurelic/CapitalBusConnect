<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports --%>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>

<article>
    <cbc:menuComponent menu="${menuItem}"/>
    <div class="center">
        <div class="searchBar">
            <table class="searchBar-content" data-url="${searchUrl}">
                <tr>
                    <td>
                        <%--suppress XmlInvalidId --%>
                        <label for="usernameSearch"><spring:message code="searchBox.username.text"/></label>
                    </td>
                    <td>
                        <cbc:searchComponent searchUrl="api/search/user" inputName="usernameSearch"
                                             inputId="usernameSearch" inputValue="${username}"
                                             inputExtraAttribute="required" />
                    </td>
                    <td></td>
                    <td></td>
                    <td>
                        <button onclick="Search.findResult(this)"><span class="icon">&#xf002;</span></button>
                    </td>
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
                <p><spring:message code="empty.login.text"/></p>
            </c:otherwise>
        </c:choose>
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>
