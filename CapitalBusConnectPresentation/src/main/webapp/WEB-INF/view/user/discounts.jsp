<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ page import="eu.sanjin.kurelic.cbc.view.components.AttributeNames" %>
<%@ include file="/WEB-INF/view/components/header/banner.jsp" %>

<%-- Local variables: --%>

<%-- Content: --%>
<article>
    <cbc:menuComponent menu="${requestScope[AttributeNames.MENU_ITEM]}"/>
    <div class="center">
        <%-- Hardcoded values, this items should be in tags so you can use them in cart page also --%>
        <div class="discount">
            <div class="discount-item">
                <div class="discount-item-description">
                    <spring:message code="discountItem.everyRide.text"/>
                </div>
                <div class="discount-item-discount">
                    50%
                </div>
            </div>
            <div class="discount-item">
                <div class="discount-item-description">
                    <spring:message code="discountItem.cityConnection.text"/>
                </div>
                <div class="discount-item-discount">
                    25%
                </div>
            </div>
        </div>
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jsp" %>
