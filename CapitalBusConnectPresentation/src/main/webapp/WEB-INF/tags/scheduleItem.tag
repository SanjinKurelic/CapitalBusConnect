<%-- 
    Document   : scheduleItem
    Created on : Nov 1, 2018, 11:53:28 AM
    Author     : Sanjin Kurelic
--%>
<%@ tag description="put the tag description here" pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ tag import="eu.sanjin.kurelic.cbc.business.viewmodel.schedule.ScheduleItemType" %>
<%@ tag import="eu.sanjin.kurelic.cbc.business.viewmodel.schedule.ScheduleButtonType" %>
<%@ tag import="eu.sanjin.kurelic.cbc.business.viewmodel.schedule.ScheduleUpdateType" %>
<%@ tag import="eu.sanjin.kurelic.cbc.business.viewmodel.schedule.SchedulePayingMethod" %>

<%-- Attributes: --%>
<%@ attribute name="scheduleItem" type="eu.sanjin.kurelic.cbc.business.viewmodel.schedule.ScheduleItem"
              required="true" %>

<%-- Local variables --%>
<c:set var="disabled" value="${scheduleItem.disabled() ? 'disabled' : ''}" />
<c:set var="selectDisabled" value="${scheduleItem.button eq ScheduleButtonType.VIEW_TICKET ? 'disabled' : ''}" />

<%-- Content: --%>
<tr class="scheduleBox-item ${disabled}" data-id-param="${scheduleItem.id}" data-date-param="${scheduleItem.date}" data-trip-param="${scheduleItem.tripType.name()}">
    <td class="scheduleBox-item-title">
        <div class="scheduleBox-item-title-main">
            ${scheduleItem.leftTitle}
            <span class="icon">&#xf061;</span>
            ${scheduleItem.rightTitle}
        </div>
        <div class="scheduleBox-item-title-description">
            <c:choose>
                <c:when test="${scheduleItem.type eq ScheduleItemType.TIME}">
                    <b><spring:message code="schedule.travelingTime.text"/>:</b>
                    ${scheduleItem.description}
                </c:when>
                <c:when test="${scheduleItem.type eq ScheduleItemType.PLACE}">
                    <b><spring:message code="schedule.departure.text"/>:</b>
                    <fmt:parseDate pattern="yyyy-MM-dd'T'HH:mm" value="${scheduleItem.description}" var="departureDate" />
                    <fmt:formatDate type="both" value="${departureDate}" />
                </c:when>
            </c:choose>
        </div>
    </td>
    <c:choose>
        <c:when test="${scheduleItem.onUpdate eq ScheduleUpdateType.UPDATE_CART}">
            <c:set var="onChange" value="onchange=\"Cart.updateCart(this)\"" />
        </c:when>
        <c:otherwise>
            <c:set var="onChange" value="" />
        </c:otherwise>
    </c:choose>
    <td class="scheduleBox-item-adults">
        <label for="numberOfAdults"><spring:message code="schedule.adults.text"/></label>
        <select name="numberOfAdults" id="numberOfAdults" data-size="select-tiny" ${onChange} ${disabled} ${selectDisabled}>
            <c:forEach var="i" begin="1" end="10">
                <option value="${i}" ${(scheduleItem.numberOfAdults eq i) ? 'selected' : ''}>${i}</option>
            </c:forEach>
        </select>
    </td>
    <td class="scheduleBox-item-children">
        <label for="numberOfChildren"><spring:message code="schedule.children.text"/></label>
        <select name="numberOfChildren" id="numberOfChildren" data-size="select-tiny" ${onChange} ${disabled} ${selectDisabled}>
            <c:forEach var="i" begin="0" end="10">
                <option value="${i}" ${(scheduleItem.numberOfChildren eq i) ? 'selected' : ''}>${i}</option>
            </c:forEach>
        </select>
    </td>
    <td class="scheduleBox-item-price" data-base-price="${scheduleItem.basePrice}">
        <c:choose>
            <c:when test="${scheduleItem.payingMethod eq SchedulePayingMethod.PAY_PAL}">
                <span title="<spring:message code='schedule.payingMethod.payPal.tooltip'/>" class="scheduleBox-item-price-payingMethod icon">&#xf1ed;</span>
            </c:when>
            <c:when test="${scheduleItem.payingMethod eq SchedulePayingMethod.MONEY}">
                <span title="<spring:message code='schedule.payingMethod.money.tooltip'/>" class="scheduleBox-item-price-payingMethod icon">&#xf155;</span>
            </c:when>
        </c:choose>
        <fmt:formatNumber value="${scheduleItem.price}" type="currency" currencySymbol="kn" pattern="#,##0.00 ¤"/>
    </td>
    <td class="scheduleBox-item-buttons">
        <%-- Standard values --%>
        <c:set var="hasButton" value="true"/>
        <c:set var="buttonIcon" value=""/>
        <c:set var="buttonCss" value=""/>
        <c:set var="buttonOnClick" value=""/>
        <c:set var="buttonText" value=""/>
        <%-- Button builder --%>
        <c:choose>
            <c:when test="${scheduleItem.button eq ScheduleButtonType.ADD_TO_CART}">
                <c:set var="buttonIcon" value="&#xf07a;"/> <%-- CART ICON --%>
                <c:set var="buttonOnClick" value="Cart.addToCart(this)"/>
                <c:set var="buttonText" value="schedule.addToCartButton.text"/>
            </c:when>
            <c:when test="${scheduleItem.button eq ScheduleButtonType.REMOVE_FROM_CART}">
                <c:set var="buttonIcon" value="&#xf1f8;"/> <%-- TRASH ICON --%>
                <c:set var="buttonCss" value="error"/>
                <c:set var="buttonOnClick" value="Cart.removeFromCart(this)"/>
                <c:set var="buttonText" value="schedule.removeButton.text"/>
            </c:when>
            <c:when test="${scheduleItem.button eq ScheduleButtonType.VIEW_TICKET}">
                <c:set var="buttonIcon" value="&#xf145;"/> <%-- TICKET ICON --%>
                <c:set var="buttonOnClick" value="alert('TODO')"/>
                <c:set var="buttonText" value="schedule.ticketButton.text"/>
            </c:when>
            <c:otherwise>
                <c:set var="hasButton" value="false"/>
            </c:otherwise>
        </c:choose>
        <c:if test="${hasButton eq true}">
            <%-- If disabled remove onClick --%>
            <c:if test="${not empty disabled}">
                <c:set var="buttonOnClick" value="" />
            </c:if>
            <div class="button ${buttonCss} ${disabled}" onclick="${buttonOnClick}">
                <span class="icon desktop-tablet">${buttonIcon}</span>
                <span class="mobile"><spring:message code="${buttonText}"/></span>
            </div>
        </c:if>
    </td>
</tr>