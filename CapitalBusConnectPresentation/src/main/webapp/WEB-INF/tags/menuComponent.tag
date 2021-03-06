<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ tag description="Tag for displaying menu component" pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ tag import="eu.sanjin.kurelic.cbc.view.components.menu.MenuType" %>
<%@ tag import="eu.sanjin.kurelic.cbc.view.controller.web.WebController" %>

<%-- Attributes: --%>
<%@ attribute name="menu" required="true" type="eu.sanjin.kurelic.cbc.view.components.menu.Menu" %>

<%-- Local Variables: --%>
<c:set var="buttonStyle" value="${fn:toLowerCase(fn:replace(menu.menuType.name(), '_', '-'))}"/>
<c:set var="selectedElement" value="false"/>

<%-- Content: --%>
<nav class="navigation">
    <div class="center">
        <a class="navigation-${buttonStyle}-backButton"
           href="<spring:url value='${WebController.HOME_URL_ALTERNATIVE}'/>"><spring:message
                code="navigation.homeButton.text"/></a>
        <c:if test="${menu.menuType eq MenuType.SIMPLE}">
            <h2 class="navigation-${buttonStyle}-title">
                <spring:message code="${menu.menuTitle}" text="${menu.menuTitle}"/>
            </h2>
        </c:if>
        <div class="navigation-${buttonStyle}-buttons">
            <%--@elvariable id="menuItem" type="eu.sanjin.kurelic.cbc.view.components.menu.MenuItem"--%>
            <c:forEach var="menuItem" items="${menu.menuItems}">
                <c:choose>
                    <c:when test="${menu.menuType eq MenuType.TABULAR_BASED}">
                        <%--suppress ELValidationInJSP --%>
                        <div class="navigation-${buttonStyle}-button ${menuItem.extraClass}"
                             data-tabId="${menuItem.onClick}" onclick="tabItemOpen(this)">
                                <%--suppress ELValidationInJSP --%>
                            <spring:message code="${menuItem.name}" text="${menuItem.name}"/>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <%--suppress ELValidationInJSP --%>
                        <c:if test="${ requestScope['javax.servlet.forward.request_uri'].endsWith(menuItem.onClick) }">
                            <c:set var="selectedElement" value="true"/>
                        </c:if>
                        <%--suppress ELValidationInJSP --%>
                        <a class="navigation-${buttonStyle}-button ${(selectedElement eq true) ? 'active' : ''} ${menuItem.extraClass}"
                           href="<spring:url value='${menuItem.onClick}'/>">
                                <%--suppress ELValidationInJSP --%>
                            <spring:message code="${menuItem.name}" text="${menuItem.name}"/>
                        </a>
                    </c:otherwise>
                </c:choose>
                <c:set var="selectedElement" value="false"/>
            </c:forEach>
        </div>
    </div>
</nav>