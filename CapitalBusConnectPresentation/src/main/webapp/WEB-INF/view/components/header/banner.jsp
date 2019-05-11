<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ page import="eu.sanjin.kurelic.cbc.view.controller.web.AdminController" %>
<%@ page import="eu.sanjin.kurelic.cbc.view.controller.web.UserController" %>
<%@ page import="eu.sanjin.kurelic.cbc.view.controller.web.WebController" %>
<%@ page import="java.util.Locale" %>
<%@ include file="head.jsp" %>

<%-- Local variables --%>
<c:set var="currentLang" value="${ cbcf:getCurrentLocale() }"/>
<security:authorize url="/admin/" var="isAdmin"/>

<%-- Content: --%>
<header class="header">
    <div class="center">
        <a href="<spring:url value='${WebController.HOME_URL_ALTERNATIVE}'/>">
            <img
                    class="header-logo"
                    src="<spring:url value='${cbcf:getImageResourceUrl("logoBig.png")}' />"
                    height="100%"
                    alt="Capital Bus Connect"
            />
        </a>
        <nav class="header-buttons">
            <a
                    title="<spring:message code='navigation.cartButton.title'/>"
                    class="icon"
                    href="<spring:url value='${WebController.CART_URL}'/>"
            >&#xf0f2;</a>&nbsp;
            <c:choose>
                <c:when test="${isAdmin}">
                    <a
                            title="<spring:message code='navigation.adminButton.title'/>"
                            class="icon"
                            href="<spring:url value='${AdminController.STATISTICS_FULL_URL_ALTERNATIVE}'/>"
                    >&#xf007;</a>
                </c:when>
                <c:otherwise>
                    <a
                            title="<spring:message code='navigation.userButton.title'/>"
                            class="icon"
                            href="<spring:url value='${UserController.TRAVELS_FULL_URL}'/>"
                    >&#xf007;</a>
                </c:otherwise>
            </c:choose>
            <form:form method="get">
                <%--suppress HtmlFormInputWithoutLabel --%>
                <select data-size="select-tiny" id="language" name="language" onchange="submit()">
                    <option value="hr" ${currentLang.equals(Locale.forLanguageTag("hr-HR")) ? 'selected':''}>HR</option>
                    <option value="en" ${currentLang.equals(Locale.ENGLISH) ? 'selected' : ''}>EN</option>
                    <option value="de" ${currentLang.equals(Locale.GERMAN) ? 'selected' : ''}>DE</option>
                    <option value="it" ${currentLang.equals(Locale.ITALIAN) ? 'selected' : ''}>IT</option>
                </select>
            </form:form>
        </nav>
    </div>
</header>
