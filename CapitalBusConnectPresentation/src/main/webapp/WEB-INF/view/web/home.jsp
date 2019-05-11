<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ include file="/WEB-INF/view/components/header/banner.jsp" %>

<%-- Local variables: --%>

<%-- Content: --%>
<%@ include file="/WEB-INF/view/components/header/searchBox.jsp" %>
<article>
    <jsp:include page="/WEB-INF/view/components/body/promotion.jsp"/>
    <jsp:include page="/WEB-INF/view/components/body/traffic.jsp"/>
</article>
<%@ include file="/WEB-INF/view/components/footer/footer.jsp" %>