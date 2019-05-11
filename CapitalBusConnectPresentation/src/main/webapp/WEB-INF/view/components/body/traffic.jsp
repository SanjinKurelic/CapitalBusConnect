<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cbc" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="cbcf" uri="/WEB-INF/tlds/functions" %>
<%@ page import="eu.sanjin.kurelic.cbc.view.components.AttributeNames" %>

<%-- Local variables: --%>

<%-- Content: --%>
<section class="trafficInfoBox">
    <div class="center">
        <h2 class="article-title"><spring:message code="trafficInfoBox.title.text"/></h2>
        <cbc:trafficItems trafficItems="${requestScope[AttributeNames.TRAFFIC_ITEMS]}"/>
        <div class="trafficInfoBox-image">
            <img alt="" src="<spring:url value="${cbcf:getImageResourceUrl('construction.gif')}"/>"/>
        </div>
    </div>
</section>