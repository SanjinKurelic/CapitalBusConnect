<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ page import="eu.sanjin.kurelic.cbc.view.components.AttributeNames" %>
<%@ page import="eu.sanjin.kurelic.cbc.view.components.TicketGenerator" %>
<%@ page import="eu.sanjin.kurelic.cbc.view.controller.web.CommerceController" %>
<%@ include file="/WEB-INF/view/components/header/head.jspf" %>

<%-- Local variables: --%>

<%-- Content: --%>
<%--@elvariable id="requestScope[AttributeNames.TICKET]" type="eu.sanjin.kurelic.cbc.business.viewmodel.ticket.Ticket"--%>
<div class="ticket">
    <h3><spring:message code="ticket.title.text"/></h3>
    <table class="ticket-content">
        <tr>
            <td><label for="fromCity"><spring:message code="ticket.fromCity.text"/></label></td>
            <td><label for="toCity"><spring:message code="ticket.toCity.text"/></label></td>
        </tr>
        <tr>
            <td><input id="fromCity" value="${requestScope[AttributeNames.TICKET].fromCity}" disabled/></td>
            <td><input id="toCity" value="${requestScope[AttributeNames.TICKET].toCity}" disabled/></td>
        </tr>
        <tr>
            <td><label for="date"><spring:message code="ticket.date.text"/></label></td>
            <td><label for="time"><spring:message code="ticket.time.text"/></label></td>
        </tr>
        <tr>
            <td>
                <fmt:parseDate pattern="yyyy-MM-dd"
                               value="${requestScope[AttributeNames.TICKET].date}"
                               var="ticketDate"
                />
                <fmt:formatDate value="${ticketDate}" pattern="dd-MM-yyyy" var="formattedDate"/>
                <input id="date" value="${formattedDate}" disabled/>
            </td>
            <td>
                <fmt:parseDate pattern="HH:mm"
                               value="${requestScope[AttributeNames.TICKET].time}"
                               var="ticketTime"
                />
                <fmt:formatDate value="${ticketTime}" pattern="HH:mm" var="formattedTime"/>
                <input id="time" value="${formattedTime}" disabled/>
            </td>
        </tr>
        <tr>
            <td><label for="name"><spring:message code="ticket.passengerName.text"/></label></td>
            <td><label for="surname"><spring:message code="ticket.passengerSurname.text"/></label></td>
        </tr>
        <tr>
            <td><input id="name" value="${requestScope[AttributeNames.TICKET].name}" disabled/></td>
            <td><input id="surname" value="${requestScope[AttributeNames.TICKET].surname}" disabled/></td>
        </tr>
        <tr>
            <td><label for="adults"><spring:message code="ticket.numberOfAdults.text"/></label></td>
            <td><label for="children"><spring:message code="ticket.numberOfChildren.text"/></label></td>
        </tr>
        <tr>
            <td><input id="adults" value="${requestScope[AttributeNames.TICKET].numberOfAdults}" disabled/></td>
            <td><input id="children" value="${requestScope[AttributeNames.TICKET].numberOfChildren}" disabled/></td>
        </tr>
        <tr>
            <td colspan="2" class="center"><span><spring:message code="ticket.qr.text"/></span></td>
        </tr>
        <tr>
            <td colspan="2" class="center">
                <c:set var="ticketCode" value="${requestScope[AttributeNames.TICKET].code}"/>
                <img width="${TicketGenerator.QR_IMAGE_WIDTH}"
                     height="${TicketGenerator.QR_IMAGE_HEIGHT}"
                     src="<spring:url value='${cbcf:buildUrl(CommerceController.TICKET_IMAGE_URL, ticketCode)}'/>"
                     alt="${ticketCode}"
                >
            </td>
        </tr>
    </table>
</div>
