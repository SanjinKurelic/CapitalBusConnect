/*global cbc_findUpTag, $$, Dialog, DialogType, DialogMessage, DialogMessageType, window, Fetch, cbc_addClass, cbc_addClickEventListener */

var Search = {
    search: function (element, url) {
        "use strict";
        var fetch, items;
        // Search element items
        items = element.nextElementSibling;
        if (element.value.trim() === "" || element.value.length < 2) {
            Search.clearResults(items);
            return;
        }
        fetch = new Fetch(url + "/" + element.value);
        fetch.onSuccess = function (responseObject) {
            var i, item;
            // Remove all children
            Search.clearResults(items);
            // Append result to root element
            for (i = 0; i < responseObject.length; i = i + 1) {
                item = document.createElement("LI");
                cbc_addClass(item, "searchContainer-elements-element");
                item.innerHTML = responseObject[i];
                cbc_addClickEventListener(item, Search.selectResult.bind(element));
                items.appendChild(item);
            }
        };
        cbc_addClickEventListener(document, function () {Search.clearResults(items);});
        fetch.fetch();
    },
    clearResults: function (items) {
        "use strict";
        while (items.lastChild) {
            items.removeChild(items.lastChild);
        }
    },
    selectResult: function (event) {
        "use strict";
        this.value = event.target.innerHTML;
        Search.clearResults(this.nextElementSibling);
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