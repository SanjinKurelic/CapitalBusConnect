<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" contentType="text/html" %>

<%-- Imports: --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="cbc" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="cbcf" uri="/WEB-INF/tlds/functions" %>

<%-- Language: --%>
<%-- <c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<c:if test="${not empty language}">
    <fmt:setLocale value="${language}" scope="session" />
</c:if>
<fmt:setBundle basename="eu.kurelic.sanjin.cbc.view.i18n.text" scope="session" /> --%>

<%-- Global bean --%>
<jsp:useBean id="currentDate" class="java.util.Date" scope="application"/>

<%-- Content: --%>
<!DOCTYPE html>
<html lang="${pageContext.response.locale}">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <title>Capital Bus Connect</title>
        <meta name="viewport" content="width=device-width,initial-scale=1">
        <base href="${pageContext.request.contextPath}/"/>
        <link href="https://fonts.googleapis.com/css?family=Roboto:100,400&amp;subset=latin-ext" rel="stylesheet">        
        <link rel="stylesheet" href="<spring:url value="${ cbcf:getStylesheetUrl('style.css')}"/>">
        <security:csrfMetaTags/>
        <%-- Favicon --%>
        <link rel="apple-touch-icon" sizes="76x76" href="<spring:url value="${ cbcf:getFaviconImageResourceUrl('apple-touch-icon.png') }"/>">
        <link rel="icon" type="image/png" sizes="32x32" href="<spring:url value="${ cbcf:getFaviconImageResourceUrl('favicon-32x32.png') }"/>">
        <link rel="icon" type="image/png" sizes="16x16" href="<spring:url value="${ cbcf:getFaviconImageResourceUrl('favicon-16x16.png') }"/>">
        <link rel="manifest" href="<spring:url value="${ cbcf:getFaviconImageResourceUrl('site.webmanifest') }"/>">
        <link rel="mask-icon" href="<spring:url value="${ cbcf:getFaviconImageResourceUrl('safari-pinned-tab.svg') }"/>" color="#5bbad5">
        <meta name="msapplication-TileColor" content="#da532c">
        <meta name="theme-color" content="#ffffff">
    </head>
    <body>