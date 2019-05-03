<%-- Created by: Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ tag description="Switch box element" pageEncoding="UTF-8" %>

<%-- Attributes: --%>
<%@ attribute name="checked" required="true" type="java.lang.Boolean" %>
<%@ attribute name="name" required="true" type="java.lang.String" %>

<%-- Local variables: --%>

<%-- Content: --%>
<div class="switchBox">
    <%--suppress HtmlFormInputWithoutLabel --%>
    <input id="${name}"
           name="${name}"
           type="checkbox" ${((not empty checked) and (checked eq true)) ? 'checked' : ''}
           class="switchBox-checkbox"
    >
    <div class="switchBox-knobs"></div>
    <div class="switchBox-layer"></div>
</div>