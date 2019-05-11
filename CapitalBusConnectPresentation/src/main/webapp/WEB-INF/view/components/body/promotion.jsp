<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cbc" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="eu.sanjin.kurelic.cbc.view.components.AttributeNames" %>

<%-- Local variables: --%>

<%-- Content: --%>
<section class="promotionBox center">
    <h2 class="article-title"><spring:message code="promotionBox.title.text" /></h2>
    <cbc:promotionItems promotionItems="${requestScope[AttributeNames.PROMOTION_ITEMS]}"/>
</section>