<%-- 
    Document   : switchElement
    Created on : Nov 30, 2018, 10:51:13 AM
    Author     : Sanjin Kurelic
--%>
<%@tag description="Switch Box element" pageEncoding="UTF-8"%>

<%-- Attributes: --%>
<%@attribute name="checked" required="true" type="java.lang.Boolean"%>
<%@attribute name="name" required="true" type="java.lang.String" %>

<%-- Content: --%>
<div class="switchBox">
    <%--suppress HtmlFormInputWithoutLabel --%>
    <input id="${name}" name="${name}" type="checkbox" ${((not empty checked) and (checked eq true)) ? 'checked' : ''} class="switchBox-checkbox">
    <div class="switchBox-knobs"></div>
    <div class="switchBox-layer"></div>
</div>