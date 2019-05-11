<%-- Created by Sanjin Kurelić (kurelic@sanjin.eu) --%>
<%@ page pageEncoding="UTF-8" %>

<%-- Imports: --%>

<%-- Local variables: --%>

<%-- Content: --%>
<dialog id="dialog" class="dialog">
    <div class="dialog-box">
        <div class="dialog-box-title">
            <span class="icon">&#xf06a;</span>
            <h4 class="dialog-box-title-error"><spring:message code="dialog.title.error.text"/></h4>
            <h4 class="dialog-box-title-warning"><spring:message code="dialog.title.warning.text"/></h4>
            <h4 class="dialog-box-title-notice"><spring:message code="dialog.title.notice.text"/></h4>
        </div>
        <div id="dialog-content" class="dialog-box-content"></div>
        <div class="dialog-box-buttons">
            <div class="dialog-box-buttons-button negative" id="dialog-cancel"><spring:message
                    code="dialog.button.cancel.text"/></div>
            <div class="dialog-box-buttons-button" id="dialog-ok"><spring:message code="dialog.button.ok.text"/></div>
        </div>
    </div>
</dialog>
<dialog id="toast" class="toast">
    <span id="toast-content" class="toast-text"></span>
</dialog>
<div class="messages-dialog-toast">
    <p id="dialog-content-userSave-success"><spring:message code="dialog.content.userSave.success.text"/></p>
    <p id="dialog-content-userSave-error"><spring:message code="dialog.content.userSave.error.text"/></p>
    <p id="dialog-content-cartAdd-error"><spring:message code="dialog.content.cartAdd.error.text"/></p>
    <p id="dialog-content-cartDelete-confirm"><spring:message code="dialog.content.cartDelete.confirm.text"/></p>
    <p id="dialog-content-cartDelete-error"><spring:message code="dialog.content.cartDelete.error.text"/></p>
    <p id="dialog-content-cartUpdate-error"><spring:message code="dialog.content.cartUpdate.error.text"/></p>
    <p id="dialog-content-allSearchFieldsRequired-error"><spring:message
            code="dialog.content.allSearchFieldsRequired.error.text"/></p>
</div>
<div class="messages-datepicker">
    <div id="datePicker-ui-nextMonth"><spring:message code="datePicker.nextMonth.text"/></div>
    <div id="datePicker-ui-previousMonth"><spring:message code="datePicker.previousMonth.text"/></div>
    <div id="datePicker-ui-months"><spring:message code="datePicker.month.array"/></div>
    <div id="datePicker-ui-weekdays"><spring:message code="datePicker.weekdays.array"/></div>
    <div id="datePicker-ui-weekdaysShort"><spring:message code="datePicker.weekdaysShort.array"/></div>
</div>
<%@ include file="scripts.jsp" %>
