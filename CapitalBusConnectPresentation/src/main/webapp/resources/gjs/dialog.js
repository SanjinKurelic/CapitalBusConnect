/* Created by Sanjin Kurelic (kurelic@sanjin.eu) */

/*global $, $$, cbc_removeClass, cbc_addClass, cbc_addClickEventListener, cbc_enum, cbc_voidFunction */

var DialogType = cbc_enum({
    TOAST: "toast",
    DIALOG: "dialog"
});

var DialogMessageType = cbc_enum({
    ERROR: "error",
    WARNING: "warning",
    NOTICE: "notice"
});

var DialogMessage = cbc_enum({
    CART_ADD_ERROR: "dialog-content-cartAdd-error",
    CART_DELETE_CONFIRM: "dialog-content-cartDelete-confirm",
    CART_DELETE_ERROR: "dialog-content-cartDelete-error",
    CART_UPDATE_ERROR: "dialog-content-cartUpdate-error",
    USER_SAVE_SUCCESS: "dialog-content-userSave-success",
    USER_SAVE_ERROR: "dialog-content-userSave-error"
});

var DialogButtonType = cbc_enum({
    OK: "ok",
    OK_CANCEL: "ok_cancel"
});

var Dialog = function (dialogType, text, messageType) {
    "use strict";
    this.text = text;
    this.dialogType = dialogType;
    this.messageType = messageType;
    this.buttonType = DialogButtonType.OK;
    this.onOk = cbc_voidFunction();
    this.onCancel = cbc_voidFunction();
};

Dialog.prototype.setDialogMessage = function () {
    "use strict";
    var messages, i;
    messages = $("dialog-content").children; // do not use comments inside dialog messages for IE6-8 error
    for (i = 0; i < messages.length; i += 1) {
        if (messages[i].id === this.text) {
            messages[i].style.display = "block";
        } else {
            messages[i].style.display = "none";
        }
    }
};

Dialog.prototype.show = function () {
    "use strict";
    var element, ok, cancel, onOk, onCancel, close, dialog, id;
    element = $(this.dialogType);
    element.open = true;
    element.style.display = "block";
    cbc_addClass(element, this.messageType);
    this.setDialogMessage();

    if (this.dialogType === DialogType.DIALOG) {
        // Block scroll
        document.body.style.overflowY = "hidden";
        close = this.close.bind(this);
        // Set OK
        ok = $("dialog-ok");
        ok.style.display = "inline-block";
        if (typeof this.onOk === "function") {
            onOk = this.onOk;
        } else {
            onOk = cbc_voidFunction();
        }
        cbc_addClickEventListener(ok, function () {
            onOk();
            close();
        });

        if (this.buttonType === DialogButtonType.OK_CANCEL) {
            // Set cancel
            cancel = $("dialog-cancel");
            cancel.style.display = "inline-block";
            if (typeof this.onCancel === "function") {
                onCancel = this.onCancel;
            } else {
                onCancel = cbc_voidFunction();
            }
            cbc_addClickEventListener(cancel, function () {
                onCancel();
                close();
            });
        }
    } else if (this.dialogType === DialogType.TOAST) {
        // Set close interval
        dialog = this;
        id = setInterval(function () {
            dialog.close();
            clearInterval(id);
        }, 3000);
    }
};

Dialog.prototype.close = function () {
    "use strict";
    var element = $(this.dialogType);
    element.open = false;
    element.style.display = "none";
    cbc_removeClass(element, this.messageType);
    $(this.dialogType + "-content").innerHTML = "";
    if (this.dialogType === DialogType.DIALOG) {
        // Unblock scroll
        document.body.style.overflowY = "auto";
        // Hide dialog
        $("dialog-ok").style.display = "none";
        $("dialog-cancel").style.display = "none";
    }
};