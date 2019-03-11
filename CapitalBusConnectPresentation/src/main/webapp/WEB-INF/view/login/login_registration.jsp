<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>

<%@ page pageEncoding="UTF-8"%>

<%-- Imports --%>
<%@ include file="/WEB-INF/view/components/header/banner.jspf"%>
<%@ include file="/WEB-INF/view/components/header/searchBox.jspf"%>
<%@ page import="eu.sanjin.kurelic.cbc.view.components.ActiveTabItem" %>

<%-- Local variables --%>
<c:set var="hidden" value='style="display:none"' />

<%-- Contetnt --%>
<article>
    <cbc:menuComponent menu="${menuItem}" />
    <div class="center">
        <section id="loginTab" class="userBox" ${activeTabItem eq ActiveTabItem.LOGIN_PAGE ? "" : hidden}>
            <%@ include file="/WEB-INF/view/components/body/login.jspf" %>
        </section>
        <section id="registerTab" class="userBox" ${activeTabItem eq ActiveTabItem.REGISTER_PAGE ? "" : hidden}>
            <%@ include file="/WEB-INF/view/components/body/registration.jspf" %>
        </section>
    </div>
</article>

<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>
