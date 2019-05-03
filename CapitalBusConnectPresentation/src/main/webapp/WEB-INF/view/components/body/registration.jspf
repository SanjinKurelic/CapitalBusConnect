<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ page import="eu.sanjin.kurelic.cbc.view.components.AttributeNames" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.temporal.ChronoUnit" %>

<%-- Local Variables: --%>
<c:set var="maxDOB" value="${LocalDate.now().minus(18, ChronoUnit.YEARS)}"/>
<c:set var="minDOB" value="${LocalDate.now().minus(18 + 120, ChronoUnit.YEARS)}"/>
<spring:message code='userData.password.placeholder' var="passwordPlaceholder"/>

<%-- Content: --%>
<%--@elvariable id="${AttributeNames.USER_DATA}" type="eu.sanjin.kurelic.cbc.business.viewmodel.user.RegistrationUserForm"--%>
<form:form action="register" method="post" modelAttribute="${AttributeNames.USER_DATA}">
    <table class="userBox-content">
        <tr class="userBox-content-socialNetworks">
            <td colspan="2">
                <img
                        src="<spring:url value='${ cbcf:getImageResourceUrl(\'fbLogin.png\') }' />"
                        width="254" height="41"
                        alt="<spring:message code='userData.facebookLogin.placeholder' />"
                >
            </td>
        </tr>
        <tr class="userBox-content-title">
            <td colspan="2">
                <h3 class="userBox-content-title-content"><spring:message code="userData.alternativeLogin.text"/></h3>
                <div class="userBox-content-title-decoration"></div>
            </td>
        </tr>
        <tr class="userBox-content-item">
            <td colspan="2">
                    <%--@elvariable id="regErrors" type="java.util.List"--%>
                <c:if test="${not empty requestScope[AttributeNames.ERROR_COPY]}">
                    <p class="userBox-content-item-error">
                            <%--@elvariable id="regError" type="org.springframework.validation.ObjectError"--%>
                        <c:forEach items="${requestScope[AttributeNames.ERROR_COPY]}" var="regError">
                            <spring:message code="${regError.defaultMessage}" text="${regError.defaultMessage}"/><br/>
                        </c:forEach>
                    </p>
                </c:if>
            </td>
        </tr>
        <tr class="userBox-content-item">
            <td>
                <form:label path="name"><spring:message code="userData.name.text"/></form:label>
            </td>
            <td>
                <form:input path="name" required="required"/>
            </td>
        </tr>
        <tr class="userBox-content-item">
            <td>
                <form:label path="surname"><spring:message code="userData.surname.text"/></form:label>
            </td>
            <td>
                <form:input path="surname"/>
            </td>
        </tr>
        <tr class="userBox-content-item">
            <td>
                <form:label path="dateOfBirth"><spring:message code="userData.dateOfBirth.text"/></form:label>
            </td>
            <td>
                <form:input
                        path="dateOfBirth" required="required"
                        data-type="date" data-max="${maxDOB}" data-min="${minDOB}"
                        data-default="${empty requestScope[AttributeNames.USER_DATA].dateOfBirth
                                        ? maxDOB
                                        : requestScope[AttributeNames.USER_DATA].dateOfBirth}"
                />
            </td>
        </tr>
        <tr class="userBox-content-item">
            <td>
                <form:label path="email"><spring:message code="userData.email.text"/></form:label>
            </td>
            <td>
                <form:input path="email" type="email" required="required"/>
            </td>
        </tr>
        <tr class="userBox-content-item">
            <td>
                <form:label path="identification"><spring:message code="userData.password.text"/></form:label>
            </td>
            <td>
                <form:password path="identification" required="required" placeholder="${passwordPlaceholder}"/>
            </td>
        </tr>
        <tr class="userBox-content-item">
            <td>
                <form:label path="newsletter"><spring:message code="userData.newsletter.text"/></form:label>
            </td>
            <td>
                <cbc:switchElement
                        checked="${not empty requestScope[AttributeNames.USER_DATA].newsletter
                                   and requestScope[AttributeNames.USER_DATA].newsletter eq true}"
                        name="newsletter"/>
            </td>
        </tr>
        <tr class="userBox-content-buttons">
            <td></td>
            <td>
                <input class="button" type="submit" value="<spring:message code='navigation.registerButton.text' />"/>
            </td>
        </tr>
    </table>
</form:form>