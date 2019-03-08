<%--
  Document   : settings
  Created on : 28.02.2019
  Author     : Sanjin Kurelic
--%>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>
<%@ page import="eu.sanjin.kurelic.cbc.business.viewmodel.user.UserFormType" %>

<article>
    <cbc:menuComponent menu="${menuItem}" />
    <div class="center">
        <form:form action="register" method="post">
            <cbc:userDataComonent userData="${userData}" userFormType="${UserFormType.SETTINGS}" error="${userDataError}" />
        </form:form>
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>