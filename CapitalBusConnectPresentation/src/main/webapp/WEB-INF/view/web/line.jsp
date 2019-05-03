<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>

<%-- Local variables: --%>

<%-- Content: --%>
<%@ include file="/WEB-INF/view/components/header/searchBox.jspf" %>
<article>
    <cbc:menuComponent menu="${requestScope[AttributeNames.MENU_ITEM]}"/>
    <section class="cityInfoBox center">
        <div class="cityInfoBox-image">
            <img
                    src="<spring:url value='${ cbcf:getCityImageResourceUrl(requestScope[AttributeNames.CITY_INFO_ITEM].imageName) }' />"
                    width="100%"
                    alt=""
            >
        </div>
        <div class="cityInfoBox-description">
            <p>${requestScope[AttributeNames.CITY_INFO_ITEM].description}</p>
        </div>
        <div class="cityInfoBox-buttons">
            <a
                    class="button"
                    href="<spring:url value='${requestScope[AttributeNames.MENU_ITEM].menuItems[0].onClick}' />"
            >
                <spring:message code="${requestScope[AttributeNames.MENU_ITEM].menuItems[0].name}"/>
            </a>
        </div>
    </section>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>