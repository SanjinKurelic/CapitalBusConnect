<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>

<article>
    <cbc:menuComponent menu="${menuItem}"/>
    <div class="center">
        <!--  search bar start -->
        <input name="city1" />
        <input name="city2" >
        <!-- search bar end -->
        <cbc:infoItem infoItems="${routeItems}"/>
        <cbc:paginationItem numberOfPages="${numberOfPages}" currentPageNumber="${currentPageNumber}"/>
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>
