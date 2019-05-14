<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ tag description="Tag for displaying promotion item" pageEncoding="UTF-8" %>

<%-- Imports --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cbcf" uri="http://eu.sanjin.cbc.com/functions" %>
<%@ tag import="eu.sanjin.kurelic.cbc.view.controller.web.WebController" %>

<%-- Attributes: --%>
<%@ attribute name="promotionItem" required="true"
              type="eu.sanjin.kurelic.cbc.business.viewmodel.promotion.PromotionItem" %>

<%-- Local variables: --%>
<c:set var="href">
    ${cbcf:buildUrl2(
        WebController.BUS_LINE_URL,
        fn:toLowerCase(promotionItem.fromCityUrl),
        fn:toLowerCase(promotionItem.toCityUrl)
    )}
</c:set>

<%-- Content: --%>
<div class="promotionBox-item">
    <a href="<spring:url value='${href}' />">
        <img class="promotionBox-item-image"
             src="<spring:url value="${cbcf:getCityImageResourceUrl(promotionItem.imageUrl)}"/>" alt=""/>
        <div class="promotionBox-item-info">
            <h3 class="promotionBox-item-info-title">${promotionItem.fromCity} - ${promotionItem.toCity}</h3>
            <div class="promotionBox-item-info-price">
                <spring:message code="promotionBox.button.text"/>
            </div>
        </div>
    </a>
</div>