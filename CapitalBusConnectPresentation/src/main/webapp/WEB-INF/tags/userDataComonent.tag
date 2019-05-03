<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ tag description="Show user information for editing current user or for registering new user" pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cbcf" uri="/WEB-INF/tlds/functions" %>
<%@ taglib prefix="cbc" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ tag import="eu.sanjin.kurelic.cbc.business.viewmodel.user.UserFormType" %>
<%@ tag import="java.time.LocalDate" %>
<%@ tag import="java.time.temporal.ChronoUnit" %>

<%-- Attributes: --%>
<%@ attribute name="userData" type="eu.sanjin.kurelic.cbc.business.viewmodel.user.UserForm" required="true" %>
<%@ attribute name="userFormType" type="eu.sanjin.kurelic.cbc.business.viewmodel.user.UserFormType" required="true" %>
<%@ attribute name="error" required="false" %>

<%-- Local Variables: --%>
<c:set var="maxDOB" value="${LocalDate.now().minus(18, ChronoUnit.YEARS)}"/>
<c:set var="minDOB" value="${LocalDate.now().minus(18 + 120, ChronoUnit.YEARS)}"/>

<%-- Content: --%>
<table class="userBox-content">
    <tr class="userBox-content-socialNetworks">
        <td colspan="2">
            <img src="<spring:url value='${ cbcf:getImageResourceUrl(\'fbLogin.png\') }' />" width="254" height="41"
                 alt="Facebook Login">
        </td>
    </tr>
    <tr class="userBox-content-title">
        <td colspan="2">
            <h3 class="userBox-content-title-content"><spring:message code="userData.alternativeLogin.text"/></h3>
            <div class="userBox-content-title-decoration"></div>
        </td>
    </tr>
    <%--<c:if test="${not empty error}">
        <tr class="userBox-content-item">
            <td colspan="2">
                <p class="userBox-content-item-error">
                        ${error}
                </p>
            </td>
        </tr>
    </c:if>--%>
    <tr class="userBox-content-item">
        <td colspan="2">
            <p class="userBox-content-item-error">
                <form:errors path="*" cssClass="userBox-content-item-error"/>
            </p>
        </td>
    </tr>
    <tr class="userBox-content-item">
        <td>
            <label for="name"><spring:message code="userData.name.text"/></label>
        </td>
        <td>
            <input name="name" id="name" type="text" value="${userData.name}" required/>
        </td>
    </tr>
    <tr class="userBox-content-item">
        <td>
            <label for="surname"><spring:message code="userData.surname.text"/></label>
        </td>
        <td>
            <input name="surname" id="surname" type="text" value="${userData.surname}"/>
        </td>
    </tr>
    <tr class="userBox-content-item">
        <td>
            <label for="dateOfBirth"><spring:message code="userData.dateOfBirth.text"/></label>
        </td>
        <td>
            <input name="dateOfBirth" id="dateOfBirth" type="text" required
                   data-type="date" data-max="${maxDOB}" data-min="${minDOB}"
                   data-default="${empty userData.dateOfBirth ? maxDOB : userData.dateOfBirth}"
            />
        </td>
    </tr>
    <tr class="userBox-content-item">
        <td>
            <label for="email"><spring:message code="userData.email.text"/></label>
        </td>
        <td>
            <input name="email" id="email" type="email" value="${userData.email}" required/>
        </td>
    </tr>
    <c:if test="${userFormType eq UserFormType.REGISTRATION}">
        <tr class="userBox-content-item">
            <td>
                <label for="identification"><spring:message code="userData.password.text"/></label>
            </td>
            <td>
                    <%--suppress XmlDuplicatedId --%>
                <input name="identification" id="identification" type="password" required/>
            </td>
        </tr>
    </c:if>
    <tr class="userBox-content-item">
        <td>
            <label for="newsletter"><spring:message code="userData.newsletter.text"/></label>
        </td>
        <td>
            <cbc:switchElement checked="${not empty userData.newsletter and userData.newsletter eq true}"
                               name="newsletter"/>
        </td>
    </tr>
    <c:if test="${userFormType eq UserFormType.SETTINGS}">
        <tr class="userBox-content-item">
            <td>
                <label for="identification"><spring:message code="userData.newPassword.text"/></label>
            </td>
            <td>
                    <%--suppress XmlDuplicatedId --%>
                <input name="identification" id="identification" type="password" required/>
            </td>
        </tr>
        <tr class="userBox-content-item">
            <td>
                <label for="confirmedIdentification"><spring:message code="userData.repeatPassword.text"/></label>
            </td>
            <td>
                <input name="confirmedIdentification" id="confirmedIdentification" type="password" required/>
            </td>
        </tr>
    </c:if>
    <tr class="userBox-content-buttons">
        <td></td>
        <td>
            <c:choose>
                <c:when test="${userFormType eq UserFormType.SETTINGS}">
                    <input class="button" type="submit" value="<spring:message code='navigation.saveButton.text' />"/>
                </c:when>
                <c:when test="${userFormType eq UserFormType.REGISTRATION}">
                    <input class="button" type="submit"
                           value="<spring:message code='navigation.registerButton.text' />"
                    />
                </c:when>
            </c:choose>
        </td>
    </tr>
</table>