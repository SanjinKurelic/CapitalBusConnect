<%-- 
    Document   : line
    Created on : Oct 25, 2018, 6:28:32 AM
    Author     : Sanjin Kurelic
--%>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>
<%@ include file="/WEB-INF/view/components/header/searchBox.jspf" %>
<article>
    <cbc:menuComponent menu="${menuItem}" />
    <section class="cityInfoBox center">
        <div class="cityInfoBox-image">
            <img src="<spring:url value='${ cbcf:getCityImageResourceUrl(cityInfoItem.imageName) }' />" width="100%" alt="">
        </div>
        <div class="cityInfoBox-description">
            ${cityInfoItem.description}
        </div>
        <div class="cityInfoBox-buttons">
            <a class="button" href="<spring:url value='${menuItem.menuItems[0].onClick}' />">
                <spring:message code="${menuItem.menuItems[0].name}" />
            </a>
        </div>
    </section>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>
