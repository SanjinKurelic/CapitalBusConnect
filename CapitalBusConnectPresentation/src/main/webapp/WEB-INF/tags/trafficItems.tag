<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ tag description="Tag for displaying traffic information items" pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cbc" uri="http://eu.sanjin.cbc.com/tags" %>

<%-- Attributes: --%>
<%@ attribute name="trafficItems" required="true"
              type="eu.sanjin.kurelic.cbc.business.viewmodel.traffic.TrafficInfoItems" %>

<%-- Local Variables: --%>

<%-- Content: --%>
<div class="trafficInfoBox-text">
    <c:forEach var="trafficItem" items="${trafficItems}">
        <cbc:trafficItem trafficInfoItem="${trafficItem}"/>
    </c:forEach>
</div>