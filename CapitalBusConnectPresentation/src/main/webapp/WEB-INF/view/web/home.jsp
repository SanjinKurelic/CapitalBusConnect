<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ include file="/WEB-INF/view/components/header/banner.jspf" %>

<%-- Local variables: --%>

<%-- Content: --%>
<%@ include file="/WEB-INF/view/components/header/searchBox.jspf" %>
<article>
    <jsp:include page="/WEB-INF/view/components/body/promotion.jspf" />
    <jsp:include page="/WEB-INF/view/components/body/traffic.jspf" />
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jspf" %>