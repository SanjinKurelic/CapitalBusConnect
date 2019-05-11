<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>

<%-- Local variables: --%>

<%-- Content: --%>
<footer class="footer">
    <div class="center">
        <div class="footer-contactInfo">
            <table>
                <tr>
                    <td><span class="icon">&#xf095;</span></td>
                    <td><a href="tel:+3850123456789">+385 (01) 2345 6789</a></td>
                </tr>
                <tr>
                    <td><span class="icon">&#xf003;</span></td>
                    <td><a href="mailto:info@example.com">info@example.com</a></td>
                </tr>
                <tr>
                    <td><span class="icon">&#xf041;</span></td>
                    <td>Avenija Marina Držića 4, Zagreb, <spring:message code="footer.croatia"/></td>
                </tr>
                <tr>
                    <td></td>
                    <td class="footer-contactInfo-socialNetworks">
                        <span class="icon">&#xf082;</span>
                        <span class="icon">&#xf081;</span>
                        <span class="icon">&#xf0d4;</span>
                    </td>
                </tr>
                <tr>
                    <td class="footer-contactInfo-applicationStore" colspan="2">
                        <img
                                alt="Google play"
                                width="150" height="58"
                                src="<spring:url value='${cbcf:getImageResourceUrl(\'googlePlay.png\')}' />"
                        />
                        <img
                                alt="Apple store"
                                width="150" height="58"
                                src="<spring:url value='${cbcf:getImageResourceUrl(\'appStore.png\')}' />"
                        />
                    </td>
                </tr>
            </table>
        </div>
        <div class="footer-locationInfo">
            <div id="location-map"></div>
        </div>
    </div>
    <%@ include file="copyright.jsp" %>
</footer>
<%@ include file="dialog.jsp" %>