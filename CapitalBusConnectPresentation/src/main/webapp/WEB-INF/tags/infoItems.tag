<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ tag description="Tag for displaying info items" pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cbc" tagdir="/WEB-INF/tags" %>

<%-- Attributes: --%>
<%@ attribute name="infoItems" required="true" type="eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItems" %>

<%-- Local Variables: --%>

<%-- Content: --%>
<table class="infoBox">
    <%--@elvariable id="infoItem" type="eu.sanjin.kurelic.cbc.business.viewmodel.info.InfoItem"--%>
    <c:forEach var="infoItem" items="${infoItems}">
        <cbc:infoItem infoItem="${infoItem}"/>
    </c:forEach>
</table>