<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ page import="eu.sanjin.kurelic.cbc.view.configuration.SpringSecurityConfiguration" %>
<%@ include file="/WEB-INF/view/components/header/banner.jsp" %>

<%-- Local variables: --%>

<%-- Content: --%>
<article>
    <section class="errorPage center">
        <h2 class="errorPage-title"><spring:message code="errorPage.accessDenied.title.text"/></h2>
        <p class="errorPage-content"><spring:message code="errorPage.accessDenied.content.text"/></p>
        <div class="errorPage-buttons">
            <a class="button" href="<spring:url value="${SpringSecurityConfiguration.LOGIN_PAGE_URL}" />">
                <spring:message code="navigation.loginButton.text"/>
            </a>
        </div>
    </section>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jsp" %>
