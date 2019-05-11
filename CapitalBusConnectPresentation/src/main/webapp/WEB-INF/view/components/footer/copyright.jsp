<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>

<%-- Local variables: --%>

<%-- Content: --%>
<p class="footer-copyright">
    Capital Bus Connect &COPY; <fmt:formatDate value="${currentDate}" pattern="yyyy" />
    <a href="<spring:url value="admin"/>">/<spring:message code="navigation.adminButton.title" /></a>
</p>