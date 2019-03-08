<%-- 
    Document   : cart
    Created on : Nov 3, 2018, 7:12:44 AM
    Author     : Sanjin Kurelic
--%>

<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>

<article>
    <cbc:menuComponent menu="${menuItem}" />
    <div class="center">
        <c:choose>
            <c:when test="${empty scheduleItems}">
                <p><spring:message code="empty.cart.text"/></p>
            </c:when>
            <c:otherwise>
                <table class="scheduleBox">
                    <c:forEach var="scheduleItem" items="${scheduleItems}">
                        <cbc:scheduleItem scheduleItem="${scheduleItem}" />
                    </c:forEach>
                </table>
                <div class="paymentBox">
                    <div class="paymentBox-content radioBox-parent">
                        <h3 class="paymentBox-content-title"><spring:message code="paymentType.title.text" /> :</h3>
                        <div class="paymentBox-content-values">
                            <input type="radio" name="paymentMethod" id="paymentMethodPayPal" checked value="PayPal" />
                            <input type="radio" name="paymentMethod" id="paymentMethodBill" value="uponArrival" />
                        </div>
                        <table class="paymentBox-content-container">
                            <tr class="paymentBox-content-container-item active radioBox-item" onclick="RadioBox.changeSelection(this, 'PayPal')">
                                <td>
                                    <span class="icon">&#xf1ed;</span>
                                </td>
                                <td>
                                    <p><label for="paymentMethodPayPal"><spring:message code="paymentType.paypal.text" /></label></p>
                                </td>
                            </tr>
                            <tr class="paymentBox-content-container-item radioBox-item" onclick="RadioBox.changeSelection(this, 'uponArrival')">
                                <td>
                                    <span class="icon">&#xf207;</span>
                                </td>
                                <td>
                                    <p><label for="paymentMethodBill"><spring:message code="paymentType.uponArrival.text" /></label></p>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="paymentBox-button">
                        <a class="button" href="<spring:url value='buy'/>">
                            <spring:message code="navigation.buyButton.text" />
                        </a>
                    </div>
                </div>

            </c:otherwise>
        </c:choose>
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>