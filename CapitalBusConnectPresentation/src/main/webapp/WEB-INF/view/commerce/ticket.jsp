<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>

<%@ page pageEncoding="UTF-8" %>
<%-- Imports --%>
<%@ include file="/WEB-INF/view/components/header/head.jspf" %>
<%@ page import="eu.sanjin.kurelic.cbc.view.components.TicketGenerator" %>

<%-- Content --%>
<div class="ticket">
    <form:form modelAttribute="ticket">
        <h3><spring:message code="ticket.title.text"/></h3>
        <table class="ticket-content">
            <tr>
                <td><form:label path="fromCity"><spring:message code="ticket.fromCity.text"/></form:label></td>
                <td><form:label path="toCity"><spring:message code="ticket.toCity.text"/></form:label></td>
            </tr>
            <tr>
                <td><form:input path="fromCity" disabled="true"/></td>
                <td><form:input path="toCity" disabled="true"/></td>
            </tr>
            <tr>
                <td><form:label path="date"><spring:message code="ticket.date.text"/></form:label></td>
                <td><form:label path="time"><spring:message code="ticket.time.text"/></form:label></td>
            </tr>
            <tr>
                <td>
                    <fmt:parseDate pattern="yyyy-MM-dd" value="${ticket.date}" var="ticketDate" />
                    <fmt:formatDate value="${ticketDate}" pattern="dd-MM-yyyy" var="formatedDate" />
                    <form:input path="date" value="${formatedDate}" disabled="true"/>
                </td>
                <td>
                    <fmt:parseDate pattern="HH:mm" value="${ticket.time}" var="ticketTime" />
                    <fmt:formatDate value="${ticketTime}" pattern="HH:mm" var="formatedTime" />
                    <form:input path="time" disabled="true" value="${formatedTime}"/>
                </td>
            </tr>
            <tr>
                <td><form:label path="name"><spring:message code="ticket.passengerName.text"/></form:label></td>
                <td><form:label path="surname"><spring:message code="ticket.passengerSurname.text"/></form:label></td>
            </tr>
            <tr>
                <td><form:input path="name" disabled="true"/></td>
                <td><form:input path="surname" disabled="true"/></td>
            </tr>
            <tr>
                <td><form:label path="numberOfAdults"><spring:message
                        code="ticket.numberOfAdults.text"/></form:label></td>
                <td><form:label path="numberOfChildren"><spring:message
                        code="ticket.numberOfChildren.text"/></form:label></td>
            </tr>
            <tr>
                <td><form:input path="numberOfAdults" disabled="true"/></td>
                <td><form:input path="numberOfChildren" disabled="true"/></td>
            </tr>
            <tr>
                <td colspan="2" class="center"><span><spring:message code="ticket.qr.text"/></span></td>
            </tr>
            <tr>
                <td colspan="2" class="center">
                    <img width="${TicketGenerator.QR_IMAGE_WIDTH}" height="${TicketGenerator.QR_IMAGE_WIDTH}"
                         src="<spring:url value='/ticket/image/${ticket.code}'/>" alt="${ticket.code}">
                </td>
            </tr>
        </table>
    </form:form>
</div>
