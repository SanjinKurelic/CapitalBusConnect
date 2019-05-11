<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ page import="eu.sanjin.kurelic.cbc.view.components.ActiveTabItem" %>
<%@ include file="/WEB-INF/view/components/header/banner.jsp" %>

<%-- Local variables: --%>
<c:set var="hidden" value='style="display:none"'/>

<%-- Contetnt: --%>
<%@ include file="/WEB-INF/view/components/header/searchBox.jsp" %>
<article>
    <cbc:menuComponent menu="${requestScope[AttributeNames.MENU_ITEM]}"/>
    <div class="center">
        <section id="loginTab"
                 class="userBox" ${requestScope[AttributeNames.ACTIVE_TAB_ITEM] eq ActiveTabItem.LOGIN_PAGE ? "" : hidden}>
            <%@ include file="/WEB-INF/view/components/body/login.jsp" %>
        </section>
        <section id="registerTab"
                 class="userBox" ${requestScope[AttributeNames.ACTIVE_TAB_ITEM] eq ActiveTabItem.REGISTER_PAGE ? "" : hidden}>
            <%@ include file="/WEB-INF/view/components/body/registration.jsp" %>
        </section>
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jsp" %>