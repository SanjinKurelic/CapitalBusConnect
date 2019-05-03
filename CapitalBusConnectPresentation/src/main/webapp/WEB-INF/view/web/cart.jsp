<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ page import="eu.sanjin.kurelic.cbc.view.components.AttributeNames" %>
<%@ page import="eu.sanjin.kurelic.cbc.view.controller.web.CommerceController" %>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>

<%-- Local variables: --%>
<c:set var="hidePayPal" value="buyPayPalButton"/>
<spring:url var="buyUrl" value="${cbcf:buildUrl(CommerceController.BUY_URL, 'money')}"/>
<security:authorize access="!isAuthenticated()">
    <c:set var="hidePayPal" value="buyUponArrivalButton"/>
    <spring:url var="buyUrl" value="${WebController.CART_URL_ALTERNATIVE}"/>
</security:authorize>

<%-- Content: --%>
<article>
    <cbc:menuComponent menu="${requestScope[AttributeNames.MENU_ITEM]}"/>
    <div class="center">
        <c:choose>
            <c:when test="${empty requestScope[AttributeNames.SCHEDULE_ITEMS]}">
                <p><spring:message code="empty.cart.text"/></p>
            </c:when>
            <c:otherwise>
                <table class="scheduleBox">
                    <c:forEach var="scheduleItem" items="${requestScope[AttributeNames.SCHEDULE_ITEMS]}">
                        <cbc:scheduleItem scheduleItem="${scheduleItem}"/>
                    </c:forEach>
                    <tr class="scheduleBox-item">
                        <td></td>
                        <td></td>
                        <td class="totalBox">
                            <spring:message code="cart.totalPrice.text"/>
                        </td>
                        <td class="totalBox">
                            <div class="totalBox-price" id="cartTotalPrice">
                                <fmt:formatNumber
                                        value="${requestScope[AttributeNames.CART_TOTAL_PRICE]}"
                                        type="currency"
                                        currencySymbol="kn"
                                        pattern="#,##0.00 ¤"
                                />
                            </div>
                        </td>
                    </tr>
                </table>
                <div class="paymentBox">
                    <div class="paymentBox-content radioBox-parent">
                        <h3 class="paymentBox-content-title"><spring:message code="paymentType.title.text"/> :</h3>
                        <div class="paymentBox-content-values">
                            <input
                                    type="radio"
                                    name="paymentMethod"
                                    id="paymentMethodBill"
                                    onchange="RadioBox.selectElement($$('.paymentBox-button')[0], 'buyUponArrivalButton')"
                                    checked value="uponArrival"
                            />
                            <input
                                    type="radio"
                                    name="paymentMethod"
                                    id="paymentMethodPayPal"
                                    onchange="RadioBox.selectElement($$('.paymentBox-button')[0], '${hidePayPal}')"
                                    value="PayPal"
                            />
                        </div>
                        <table class="paymentBox-content-container">
                            <tr
                                    class="paymentBox-content-container-item active radioBox-item"
                                    onclick="RadioBox.changeSelection(this, 'uponArrival')"
                            >
                                <td>
                                    <span class="icon">&#xf207;</span>
                                </td>
                                <td>
                                    <p>
                                        <label for="paymentMethodBill">
                                            <spring:message code="paymentType.uponArrival.text"/>
                                        </label>
                                    </p>
                                </td>
                            </tr>
                            <tr
                                    class="paymentBox-content-container-item radioBox-item"
                                    onclick="RadioBox.changeSelection(this, 'PayPal')"
                            >
                                <td>
                                    <span class="icon">&#xf1ed;</span>
                                </td>
                                <td>
                                    <p>
                                        <label for="paymentMethodPayPal">
                                            <spring:message code="paymentType.paypal.text"/>
                                        </label>
                                    </p>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="paymentBox-button">
                        <div id="buyUponArrivalButton">
                            <a class="button" href="${buyUrl}">
                                <spring:message code="navigation.buyButton.text"/>
                            </a>
                        </div>
                        <div class="hidden paymentBox-button-paypal" id="buyPayPalButton"></div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>
<%-- PayPal button --%>
<c:if test="${not empty requestScope[AttributeNames.SCHEDULE_ITEMS]}">
    <%--suppress JSUnresolvedVariable, JSValidateTypes, JSUnusedLocalSymbols, JSUnresolvedFunction --%>
    <script>
        paypal.Buttons({
            createOrder: function (data, actions) {
                return actions.order.create({
                    purchase_units: [{
                        amount: {
                            value: cbc_hrkToEuro(parseFloat($("cartTotalPrice").innerHTML.trim().replace(",", "")))
                        }
                    }]
                });
            },
            onApprove: function (data, actions) {
                return actions.order.capture().then(function (details) {
                    // use firstChildElement instead of children[0] if IE >= 9
                    location.href = $("buyUponArrivalButton").children[0].href.replace("money", "pay-pal");
                });
            },
            style: {
                color: "gold",
                shape: "rect",
                label: "paypal",
                height: 50,
                tagline: false
            }
        }).render("#buyPayPalButton");
    </script>
</c:if>