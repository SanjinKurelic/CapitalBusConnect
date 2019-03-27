<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports --%>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>

<article>
    <cbc:menuComponent menu="${menuItem}" />
    <div class="center">
        <!--  search bar start -->
        <input name="username" value="${username}" />
        <input name="date" value="${date}">
        <!-- search bar end -->
        <cbc:infoItem infoItems="${item}"/>
        <cbc:paginationItem numberOfPages="${numberOfPages}" currentPageNumber="${currentPage}" leftUrlPart="${leftUrlPart}" rightUrlPart="${rightUrlPart}" />
    </div>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>
