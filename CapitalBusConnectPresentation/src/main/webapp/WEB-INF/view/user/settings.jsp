<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ page import="eu.sanjin.kurelic.cbc.view.components.AttributeNames" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.temporal.ChronoUnit" %>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>

<%-- Local Variables: --%>
<c:set var="maxDOB" value="${LocalDate.now().minus(18, ChronoUnit.YEARS)}"/>
<c:set var="minDOB" value="${LocalDate.now().minus(18 + 120, ChronoUnit.YEARS)}"/>
<spring:message code='userData.password.placeholder' var="passwordPlaceholder"/>

<%-- Content: --%>
<article>
    <cbc:menuComponent menu="${requestScope[AttributeNames.MENU_ITEM]}"/>
    <div class="center" style="padding-bottom: 20px;">
        <%--@elvariable id="${AttributeNames.USER_DATA}" type="eu.sanjin.kurelic.cbc.business.viewmodel.user.SettingsUserForm"--%>
        <form:form action="user/settings" method="post" modelAttribute="${AttributeNames.USER_DATA}">
            <%-- Only for validation, this field is not used in code behind! --%>
            <form:input path="email" cssStyle="display: none;"/>
            <table class="userBox-content">
                <tr class="userBox-content-item">
                    <td colspan="2">
                        <c:if test="${not empty requestScope[AttributeNames.ERROR]}">
                            <p class="userBox-content-item-error">
                                    <%--@elvariable id="saveError" type="org.springframework.validation.ObjectError"--%>
                                <c:forEach items="${requestScope[AttributeNames.ERROR]}" var="saveError">
                                    <spring:message
                                            code="${saveError.defaultMessage}"
                                            text="${saveError.defaultMessage}"
                                    />
                                    <br/>
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
                                path="dateOfBirth"
                                required="required"
                                data-type="date"
                                data-max="${maxDOB}"
                                data-min="${minDOB}"
                                data-default="${empty requestScope[AttributeNames.USER_DATA].dateOfBirth
                                                ? maxDOB
                                                : requestScope[AttributeNames.USER_DATA].dateOfBirth}"
                        />
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
                                name="newsletter"
                        />
                    </td>
                </tr>
                <tr class="userBox-content-title">
                    <td colspan="2">
                        <h4 class="userBox-content-title-content">
                            <spring:message code="userData.changePassword.text"/>
                        </h4>
                    </td>
                </tr>
                <tr class="userBox-content-item">
                    <td>
                        <form:label path="identification">
                            <spring:message code="userData.newPassword.text"/>
                        </form:label>
                    </td>
                    <td>
                        <form:password path="identification" placeholder="${passwordPlaceholder}"/>
                    </td>
                </tr>
                <tr class="userBox-content-item">
                    <td>
                        <form:label path="confirmedIdentification">
                            <spring:message code="userData.repeatPassword.text"/>
                        </form:label>
                    </td>
                    <td>
                        <form:password path="confirmedIdentification" placeholder="${passwordPlaceholder}"/>
                    </td>
                </tr>
                <tr class="userBox-content-buttons">
                    <td></td>
                    <td>
                        <input
                                class="button"
                                type="submit"
                                value="<spring:message code='navigation.saveButton.text' />"
                        />
                    </td>
                </tr>
            </table>
        </form:form>
    </div>
</article>
<c:if test="${not empty requestScope[AttributeNames.SUCCESS]}">
    <c:choose>
        <c:when test="${requestScope[AttributeNames.SUCCESS] eq true}">
            <c:set var="dialogText" value="DialogMessage.USER_SAVE_SUCCESS"/>
        </c:when>
        <c:otherwise>
            <c:set var="dialogText" value="DialogMessage.USER_SAVE_ERROR"/>
        </c:otherwise>
    </c:choose>
    <script>
        window.onload = function () {
            (new Dialog(DialogType.TOAST, ${dialogText}, DialogMessageType.NOTICE)).show();
        };
    </script>
</c:if>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>