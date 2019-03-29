
/*global cbc_findUpTag, $$, Dialog, DialogType, DialogMessage, DialogMessageType, window */

var Search = {
    apiUrl: "",
    searchCity: function () {
        "use strict";
    },
    searchUser: function () {
        "use strict";
    },
    findResult: function (element) {
        "use strict";
        var root, elements, i, url;
        root = cbc_findUpTag(element, "table");
        url = root.getAttribute("data-url");
        elements = $$("input", root);
        for (i = 0; i < elements.length; i = i + 1) {
            if (elements[i].hasAttribute("required")) {
                if (elements[i].value.trim() === "") {
                    (new Dialog(DialogType.DIALOG, DialogMessage.ALL_SEARCH_FIELDS_fREQUIRED, DialogMessageType.ERROR)).show();
                    return;
                }
            }
            url = url + "/" + elements[i].value.toLowerCase();
        }
        window.location.href = url;
    }
};