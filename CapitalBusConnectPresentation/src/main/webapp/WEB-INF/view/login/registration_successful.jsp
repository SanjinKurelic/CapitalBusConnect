<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>

<article>
    <section class="successPage center">
        <h2 class="successPage-title"><spring:message code="successPage.registrationSuccessful.title.text" /></h2>
        <p class="successPage-content"><spring:message code="successPage.registrationSuccessful.content.text" /></p>
        <div class="successPage-buttons">
            <a class="button" href="<spring:url value="/login" />"><spring:message code="navigation.loginButton.text" /></a>
        </div>
    </section>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>
