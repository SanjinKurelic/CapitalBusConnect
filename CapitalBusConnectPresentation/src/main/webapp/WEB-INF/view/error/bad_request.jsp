<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ include file="/WEB-INF/view/components/header/banner.jsp" %>

<%-- Local variables: --%>

<%-- Content: --%>
<article>
    <section class="errorPage center">
        <h2 class="errorPage-title"><spring:message code="errorPage.badRequest.title.text"/></h2>
        <p class="errorPage-content"><spring:message code="errorPage.badRequest.content.text"/></p>
        <div class="errorPage-buttons">
            <a class="button" href="<spring:url value="${WebController.HOME_URL_ALTERNATIVE}" />">
                <spring:message code="navigation.homeButton.text"/>
            </a>
        </div>
    </section>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jsp" %>