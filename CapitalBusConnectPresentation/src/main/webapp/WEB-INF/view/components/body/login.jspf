<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>

<%-- Local variables: --%>

<%-- Content: --%>
<form:form action="authenticate" method="post">
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
        <c:if test="${param.error != null}">
            <tr class="userBox-content-item">
                <td colspan="2">
                    <p class="userBox-content-item-error">
                        <spring:message code="userData.loginError.text"/>
                    </p>
                </td>
            </tr>
        </c:if>
        <tr class="userBox-content-item">
            <td>
                <label for="username"><spring:message code="userData.email.text"/></label>
            </td>
            <td>
                <input id="username" name="username" type="email" required="required"/>
            </td>
        </tr>
        <tr class="userBox-content-item">
            <td>
                <label for="password"><spring:message code="userData.password.text"/></label>
            </td>
            <td>
                <input id="password" name="password" type="password" required="required"/>
            </td>
        </tr>
        <tr class="userBox-content-buttons">
            <td></td>
            <td>
                <input class="button" type="submit" value="<spring:message code='navigation.loginButton.text' />"/>
            </td>
        </tr>
    </table>
</form:form>