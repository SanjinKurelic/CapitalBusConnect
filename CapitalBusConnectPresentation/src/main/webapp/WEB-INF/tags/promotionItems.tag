<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ tag description="Tag for displaying promotion items" pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cbc" tagdir="/WEB-INF/tags" %>

<%-- Attributes: --%>
<%@ attribute name="promotionItems" required="true"
              type="eu.sanjin.kurelic.cbc.business.viewmodel.promotion.PromotionItems" %>

<%-- Local Variables: --%>

<%-- Content: --%>
<c:forEach var="promotionItem" items="${promotionItems}">
    <cbc:promotionItem promotionItem="${promotionItem}"/>
</c:forEach>