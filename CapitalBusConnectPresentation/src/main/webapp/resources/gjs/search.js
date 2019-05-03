/* Created by Sanjin Kurelić (kurelic@sanjin.eu) */

/*global cbc_findUpTag, $$, Dialog, DialogType, DialogMessage, DialogMessageType, window, Fetch, cbc_addClass, cbc_addClickEventListener */

var Search = {
    search: function (element, url) {
        "use strict";
        var fetch, items;
        // Search element items
        items = element.nextElementSibling;
        if (element.value.trim() === "" || element.value.length < 2) {
            // noinspection JSLint
            Search.clearResults(items);
            return;
        }
        fetch = new Fetch(url + "/" + element.value);
        fetch.onSuccess = function (responseObject) {
            var i, item;
            // Remove all children
            // noinspection JSLint
            Search.clearResults(items);
            // Append result to root element
            for (i = 0; i < responseObject.length; i = i + 1) {
                item = document.createElement("LI");
                cbc_addClass(item, "searchContainer-elements-element");
                if (responseObject[i].hasOwnProperty("url")) {
                    item.innerHTML = responseObject[i].name;
                    item.setAttribute("data-value", responseObject[i].url);
                } else {
                    item.innerHTML = responseObject[i];
                }
                // noinspection JSLint
                cbc_addClickEventListener(item, Search.selectResult.bind(element));
                items.appendChild(item);
            }
        };
        cbc_addClickEventListener(document, function () {
            // noinspection JSLint
            Search.clearResults(items);
        });
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
        if (this.hasAttribute("data-value")) {
            this.setAttribute("data-value", event.target.getAttribute("data-value"));
        }
        // noinspection JSLint
        Search.clearResults(this.nextElementSibling);
    },
    findResult: function (element) {
        "use strict";
        var root, elements, i, url, value;
        root = cbc_findUpTag(element, "table");
        url = root.getAttribute("data-url");
        elements = $$("input", root);
        for (i = 0; i < elements.length; i = i + 1) {
            if (elements[i].hasAttribute("required")) {
                if (elements[i].value.trim() === "") {
                    (new Dialog(DialogType.DIALOG, DialogMessage.ALL_SEARCH_FIELDS_REQUIRED, DialogMessageType.ERROR)).show();
                    return;
                }
            }
            if (elements[i].hasAttribute("data-value")) {
                value = elements[i].getAttribute("data-value");
            } else {
                value = elements[i].value;
            }
            url = url + "/" + value.toLowerCase();
        }
        if (url.startsWith("/")) {
            url = url.slice(1);
        }
        window.location.href = url;
    }
};