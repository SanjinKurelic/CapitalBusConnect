<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ tag description="Tag for displaying traffic information item" pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ tag import="eu.sanjin.kurelic.cbc.business.viewmodel.traffic.TrafficWarningType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cbcf" uri="http://eu.sanjin.cbc.com/functions" %>

<%-- Attributes: --%>
<%@ attribute name="trafficInfoItem" required="true"
              type="eu.sanjin.kurelic.cbc.business.viewmodel.traffic.TrafficInfoItem"
%>

<%-- Local Variables: --%>
<c:choose>
    <c:when test="${trafficInfoItem.warningType eq TrafficWarningType.NOTICE}">
        <c:set var="iconColor" value="warningText"/>
    </c:when>
    <c:when test="${trafficInfoItem.warningType eq TrafficWarningType.IMPORTANT}">
        <c:set var="iconColor" value="errorText"/>
    </c:when>
    <c:otherwise>
        <c:set var="iconColor" value="shadowText"/>
    </c:otherwise>
</c:choose>

<%-- Content: --%>
<div class="trafficInfoBox-text-item">
    <div class="trafficInfoBox-text-item-icon"><span class="icon ${iconColor}">&#xf071;</span></div>
    <div class="trafficInfoBox-text-item-content">
        <span>${trafficInfoItem.textMessage}</span>
        <span class="trafficInfoBox-text-item-content-date">(${cbcf:formatDate(trafficInfoItem.date)})</span>
    </div>
</div>