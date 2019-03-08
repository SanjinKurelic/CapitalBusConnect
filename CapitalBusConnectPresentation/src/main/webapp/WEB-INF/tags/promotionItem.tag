<%-- 
    Document   : promotionItem
    Created on : Nov 5, 2018, 11:32:55 AM
    Author     : Sanjin Kurelic
--%>
<%@ tag description="put the tag description here" pageEncoding="UTF-8" %>

<%-- Imports --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cbcf" uri="/WEB-INF/tlds/functions" %>

<%-- Attributes: --%>
<%@ attribute name="promotionItem" type="eu.sanjin.kurelic.cbc.business.viewmodel.promotion.PromotionItem" required="true" %>

<%-- Content: --%>
<div class="promotionBox-item">
    <a href="<spring:url value='line/${fn:toLowerCase(promotionItem.fromCity)}/${fn:toLowerCase(promotionItem.toCity)}' />">
        <img class="promotionBox-item-image" src="<spring:url value="${cbcf:getCityImageResourceUrl(promotionItem.imageUrl)}"/>" alt=""/>
        <div class="promotionBox-item-info">
            <h3 class="promotionBox-item-info-title">${promotionItem.fromCity} - ${promotionItem.toCity}</h3>
            <div class="promotionBox-item-info-price">
                <spring:message code="promotionBox.button.text" />
            </div>
        </div>
    </a>
</div>