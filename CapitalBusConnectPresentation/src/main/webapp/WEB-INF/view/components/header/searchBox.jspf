<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>
<%@ page import="eu.sanjin.kurelic.cbc.view.components.AttributeNames" %>
<%@ page import="eu.sanjin.kurelic.cbc.view.controller.api.SearchController" %>

<%-- Local variables: --%>
<c:set var="searchCityIcon" value='<span class="icon">&#xf041;</span>'/>
<c:set var="searchArrowIcon" value='<span class="icon">&#xf061;</span>'/>
<c:set var="searchDateIcon" value='<span class="icon">&#xf073;</span>'/>
<c:set var="searchButtonIcon" value='<span class="icon">&#xf002;</span>'/>
<c:set var="placeholder" value=""/>

<%-- Content: --%>
<div class="searchBox">
    <div class="center">
        <div class="searchBox-content">
            <c:forTokens items="searchBox-content-full,searchBox-content-compact" delims="," var="cssClass">
                <div class="${cssClass}">
                    <table data-url="schedule">
                        <tr>
                            <td>${searchCityIcon}</td>
                            <td>
                                <label>
                                    <spring:message code='searchBox.fromCity.placeholder' var="placeholder"/>
                                    <cbc:searchComponent
                                            searchUrl="${SearchController.SEARCH_CITY_FULL_URL}"
                                            inputName="fromCitySearch"
                                            required="true"
                                            inputValue="${requestScope[AttributeNames.SEARCH_FROM_CITY]}"
                                            searchValue="${requestScope[AttributeNames.SEARCH_FROM_CITY_URL]}"
                                            inputPlaceholder="${placeholder}"
                                    />
                                </label>
                            </td>
                            <c:if test="${cssClass.contains('-full')}">
                                <td>${searchArrowIcon}</td>
                            </c:if>
                            <td>${searchCityIcon}</td>
                            <td>
                                <label>
                                    <spring:message code='searchBox.toCity.placeholder' var="placeholder"/>
                                    <cbc:searchComponent
                                            searchUrl="${SearchController.SEARCH_CITY_FULL_URL}"
                                            inputName="toCitySearch"
                                            required="true"
                                            inputValue="${requestScope[AttributeNames.SEARCH_TO_CITY]}"
                                            searchValue="${requestScope[AttributeNames.SEARCH_TO_CITY_URL]}"
                                            inputPlaceholder="${placeholder}"
                                    />
                                </label>
                            </td>
                            <c:if test="${cssClass.contains('-compact')}">
                                <c:out value="</tr><tr>" escapeXml="false"/>
                            </c:if>
                            <td>${searchDateIcon}</td>
                            <td>
                                <label>
                                    <input
                                            data-type="date"
                                            data-min="<fmt:formatDate value='${currentDate}' pattern='yyyy-MM-dd'/>"
                                            required name="dateSearch"
                                            value="${requestScope[AttributeNames.SEARCH_DATE]}"
                                            placeholder="<spring:message code='searchBox.date.placeholder' />"
                                    />
                                </label>
                            </td>
                            <c:choose>
                                <c:when test="${cssClass.contains('-compact')}">
                                    <td>${searchButtonIcon}</td>
                                    <td>
                                        <button onclick="Search.findResult(this)"><spring:message
                                                code='searchBox.button.text'/></button>
                                    </td>
                                </c:when>
                                <c:otherwise>
                                    <td>
                                        <button onclick="Search.findResult(this)">${searchButtonIcon}</button>
                                    </td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </table>
                </div>
            </c:forTokens>
        </div>
    </div>
</div>
