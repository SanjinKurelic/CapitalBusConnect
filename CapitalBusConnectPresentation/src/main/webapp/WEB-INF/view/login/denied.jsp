<%--
  Created by IntelliJ IDEA.
  User: Sanjin
  Date: 1.3.2019.
  Time: 15:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>
<article>
    <section class="errorPage center">
        <h2 class="errorPage-title"><spring:message code="errorPage.accessDenied.title.text" /></h2>
        <p class="errorPage-content"><spring:message code="errorPage.accessDenied.content.text" /></p>
        <div class="errorPage-buttons">
            <a class="button" href="<spring:url value="/login" />"><spring:message code="navigation.loginButton.text" /></a>
        </div>
    </section>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>
