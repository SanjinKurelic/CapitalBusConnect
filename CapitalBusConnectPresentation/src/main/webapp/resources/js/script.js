/* Created by Sanjin Kurelic (kurelic@sanjin.eu) */

/*global window, cbc_addClass, $, $$, cbc_findUpTag, cbc_addClickEventListener, cbc_blockEvents, Fetch, FetchHttpMethods, Dialog, DialogType, DialogMessageType, DialogButtonType, DialogMessage, cbc_formatDecimal */

var CartItem = function () {
    "use strict";
    this.scheduleId = 0;
    this.numberOfAdults = 0;
    this.numberOfChildren = 0;
    this.date = null;
    this.tripType = null;
    this.toString = function () {
        var text = "scheduleId=" + this.scheduleId;
        text = text + "&numberOfAdults=" + this.numberOfAdults;
        text = text + "&numberOfChildren=" + this.numberOfChildren;
        text = text + "&date=" + this.date;
        text = text + "&trip" + this.tripType;
        return text;
    };
};

var Cart = {
    url: "api/cart",
    getItem: function (itemElement) {
        "use strict";
        var item = new CartItem();

        item.scheduleId = itemElement.getAttribute("data-id-param");
        item.date = itemElement.getAttribute("data-date-param");
        item.tripType = itemElement.getAttribute("data-trip-param");
        item.numberOfAdults = $$("select[name=numberOfAdults]", itemElement)[0].value;
        item.numberOfChildren = $$("select[name=numberOfChildren]", itemElement)[0].value;

        return item;
    },

    addToCart: function (buttonElement) {
        "use strict";
        var itemElement, fetch, selectItems, i;
        itemElement = cbc_findUpTag(buttonElement, "tr");
        // fetch
        fetch = new Fetch(Cart.url);
        fetch.method = FetchHttpMethods.POST;
        fetch.bodyParameters = Cart.getItem(itemElement);
        fetch.onSuccess = function () {
            // disable
            cbc_addClass(buttonElement, "disabled");
            cbc_addClass(itemElement, "disabled");
            // disable all select items
            selectItems = $$(".select", itemElement);
            for (i = 0; i < selectItems.length; i += 1) {
                cbc_addClass(selectItems[i], "disabled");
            }
            // cancel event propagation
            cbc_addClickEventListener(itemElement, cbc_blockEvents);
        };
        fetch.onError = function () {
            var dialog = new Dialog(DialogType.TOAST, DialogMessage.CART_ADD_ERROR, DialogMessageType.ERROR);
            dialog.show();
        };
        fetch.fetch();
    },

    removeFromCart: function (buttonElement) {
        "use strict";
        var question = new Dialog(DialogType.DIALOG, DialogMessage.CART_DELETE_CONFIRM, DialogMessageType.WARNING);
        question.buttonType = DialogButtonType.OK_CANCEL;
        question.onOk = function () {
            var itemElement, fetch;
            itemElement = cbc_findUpTag(buttonElement, "tr");
            // fetch
            fetch = new Fetch(Cart.url);
            fetch.method = FetchHttpMethods.DELETE;
            fetch.bodyParameters = Cart.getItem(itemElement);
            fetch.onSuccess = function () {
                var parent = itemElement.parentNode;
                parent.removeChild(itemElement);
                if (parent.children.length <= 0) { // hasChildNodes also return non Element nodes!!!
                    window.location.reload();
                }
            };
            fetch.onError = function () {
                var dialog = new Dialog(DialogType.TOAST, DialogMessage.CART_DELETE_ERROR, DialogMessageType.ERROR);
                dialog.show();
            };
            fetch.fetch();
        };
        question.show();
    },

    updateCart: function (updatedValue) {
        "use strict";
        var itemElement, fetch;
        itemElement = cbc_findUpTag(updatedValue, "tr");
        // fetch
        fetch = new Fetch(Cart.url);
        fetch.method = FetchHttpMethods.PUT;
        fetch.bodyParameters = Cart.getItem(itemElement);
        // no onSuccess
        fetch.onError = function () {
            var dialog = new Dialog(DialogType.TOAST, DialogMessage.CART_UPDATE_ERROR, DialogMessageType.ERROR);
            dialog.show();
        };
        fetch.onSuccess = function () {
            var priceBox, basePrice, item, price, priceElements, currency;
            // Get price box
            priceBox = $$(".scheduleBox-item-price", itemElement)[0];
            // Get item data
            item = Cart.getItem(itemElement);
            basePrice = priceBox.getAttribute("data-base-price");
            // Set price and currency
            price = item.numberOfAdults * basePrice + item.numberOfChildren * basePrice;
            priceElements = priceBox.innerHTML.trim().split(" ");
            currency = priceElements[priceElements.length - 1];
            priceBox.innerHTML = cbc_formatDecimal(price) + " " + currency;
            Cart.calculateTotal(cbc_findUpTag(itemElement, "table"), currency);
        };
        fetch.fetch();
    },
    calculateTotal: function (itemsTable, currency) {
        "use strict";
        var prices, i, total = 0;
        prices = $$(".scheduleBox-item-price", itemsTable);
        for (i = 0; i < prices.length; i = i + 1) {
            total = total + parseFloat(prices[i].innerHTML.trim().replace(",", ""));
        }
        $("cartTotalPrice").innerHTML = cbc_formatDecimal(total) + " " + currency;
    }

};
/* Created by Sanjin Kurelic (kurelic@sanjin.eu) */

/*global $$, Pikaday */

function setDatePicker() {
    "use strict";
    var parseFunction, toStringFunction, i, datePickers, datePicker, minDate, maxDate, defaultDate, picker;
    // Use default format YYYY-MM-DD
    parseFunction = function (date) {
        var parts = date.split("-");
        return new Date(parseInt(parts[0], 10), parseInt(parts[1], 10) - 1, parseInt(parts[2], 10));
    };
    toStringFunction = function (date) {
        var day, month, year;
        year = date.getFullYear();
        month = date.getMonth() + 1;
        if (month < 10) {
            month = "0" + month;
        }
        day = date.getDate();
        if (day < 10) {
            day = "0" + day;
        }
        return year + "-" + month + "-" + day;
    };
    // Get all date pickers
    datePickers = $$("input[data-type=\"date\"]");
    for (i = 0; i < datePickers.length; i += 1) {
        datePicker = datePickers[i];
        if (!datePicker.hasAttribute("pattern")) {
            datePicker.setAttribute("pattern", "^[1-3][0-9]{3}-[0-1][0-9]-[0-3][0-9]$"); // just for typo errors
        }
        if (datePicker.hasAttribute("data-min")) {
            minDate = parseFunction(datePicker.getAttribute("data-min"));
        } else {
            minDate = null;
        }
        if (datePicker.hasAttribute("data-max")) {
            maxDate = parseFunction(datePicker.getAttribute("data-max"));
        } else {
            maxDate = null;
        }
        if (datePicker.hasAttribute("data-default")) {
            defaultDate = parseFunction(datePicker.getAttribute("data-default"));
        } else {
            defaultDate = null;
        }
        picker = new Pikaday({
            field: datePickers[i],
            format: "YYYY-MM-DD",
            toString: toStringFunction,
            parse: parseFunction,
            minDate: minDate,
            maxDate: maxDate,
            defaultDate: defaultDate,
            yearSuffix: ".",
            firstDay: 1,
            i18n: {
                previousMonth: "Previous Month",
                nextMonth: "Next Month",
                months: ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"],
                weekdays: ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"],
                weekdaysShort: ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"]
            }
        });
        picker.hide();
    }
}
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
    USER_SAVE_ERROR: "dialog-content-userSave-error",
    ALL_SEARCH_FIELDS_REQUIRED: "dialog-content-allSearchFieldsRequired-error"
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
/* 
 * Created by Sanjin Kurelic (kurelic@sanjin.eu)
 */

/*jslint browser: true */
/*global FB */

// Removed becouse it's slow, implement it on your own from facebook documentation :)
/* Created by Sanjin Kurelic (kurelic@sanjin.eu) */

/*global cbc_enum, cbc_voidFunction, $$ */

var FetchHttpMethods = cbc_enum({
    GET: "GET",
    POST: "POST",
    PUT: "PUT",
    DELETE: "DELETE"
});

var Fetch = function (url) {
    "use strict";
    this.method = FetchHttpMethods.GET;
    this.url = url;
    this.username = null;
    this.password = null;
    this.bodyParameters = null; // JSON
    this.urlParameters = "";
    this.onSuccess = cbc_voidFunction();
    this.onError = cbc_voidFunction();

};

Fetch.prototype.fetch = function () {
    "use strict";
    var xhttp, onSuccess, onError, csrf, csrfParameter, csrfToken, csrfHeader;
    xhttp = new XMLHttpRequest();
    // CSRF
    csrf = $$("meta[name='_csrf_parameter']").length !== 0;
    if (csrf) {
        csrfParameter = $$("meta[name='_csrf_parameter']")[0].getAttribute("content");
        csrfToken = $$("meta[name='_csrf']")[0].getAttribute("content");
        csrfHeader = $$("meta[name='_csrf_header']")[0].getAttribute("content");
    }

    if (typeof this.onSuccess === "function") {
        onSuccess = this.onSuccess;
    } else {
        onSuccess = cbc_voidFunction();
    }
    if (typeof this.onError === "function") {
        onError = this.onError;
    } else {
        onError = cbc_voidFunction();
    }
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4) {
            if (this.status === 200) {
                // noinspection JSLint
                try {
                    onSuccess(JSON.parse(this.responseText));
                } catch (e) {
                    onSuccess(this.responseText);
                }
            } else if (this.status === 204) {
                onSuccess();
            } else if (this.status >= 300) {
                // noinspection JSLint
                try {
                    onError(JSON.parse(this.responseText));
                } catch (ex) {
                    onError(this.responseText);
                }
            }
        }
    };
    // Append CRSF
    if (csrf) {
        if (this.urlParameters !== "") {
            this.urlParameters += "&";
        }
        this.urlParameters += csrfParameter + "=" + csrfToken;
    }
    xhttp.open(this.method, this.url + "?" + this.urlParameters, true, this.username, this.password);
    xhttp.setRequestHeader(csrfHeader, csrfToken);
    // if bodyParameters is JS object, convert it to JSON
    if (typeof this.bodyParameters === "object" && this.bodyParameters !== null) {
        this.bodyParameters = JSON.stringify(this.bodyParameters);
        xhttp.setRequestHeader("Content-Type", "application/json");
    }
    xhttp.send(this.bodyParameters);
};
/* 
 * Created by Sanjin Kurelic (kurelic@sanjin.eu)
 */

/*jslint browser: true */
/*global google, $ */

/*
 * Google maps
 */
function initMap() {
    "use strict";
    var map, position, marker;
    position = {lat: 45.803934, lng: 15.993249};

    map = new google.maps.Map($('location-map'), {
        center: position,
        zoom: 16,
        mapTypeControlOptions: {
            mapTypeIds: [google.maps.MapTypeId.ROADMAP]
        },
        mapTypeControl: false,
        streetViewControl: false,
        styles: [
            {elementType: 'geometry', stylers: [{color: '#242f3e'}]},
            {elementType: 'labels.text.stroke', stylers: [{color: '#242f3e'}]},
            {elementType: 'labels.text.fill', stylers: [{color: '#746855'}]},
            {
                featureType: 'administrative.locality',
                stylers: [{visibility: 'off'}]
            },
            {
                featureType: 'poi',
                stylers: [{visibility: 'off'}]
            },
            {
                featureType: 'road',
                elementType: 'geometry',
                stylers: [{color: '#38414e'}]
            },
            {
                featureType: 'road',
                elementType: 'geometry.stroke',
                stylers: [{color: '#212a37'}]
            },
            {
                featureType: 'road',
                elementType: 'labels.text.fill',
                stylers: [{color: '#9ca5b3'}]
            },
            {
                featureType: 'road.highway',
                elementType: 'geometry',
                stylers: [{color: '#746855'}]
            },
            {
                featureType: 'road.highway',
                elementType: 'geometry.stroke',
                stylers: [{color: '#1f2835'}]
            },
            {
                featureType: 'road.highway',
                elementType: 'labels.text.fill',
                stylers: [{color: '#f3d19c'}]
            },
            {
                featureType: 'transit',
                stylers: [{visibility: 'off'}]
            },
            {
                featureType: 'water',
                elementType: 'geometry',
                stylers: [{color: '#17263c'}]
            },
            {
                featureType: 'water',
                elementType: 'labels.text.fill',
                stylers: [{color: '#515c6d'}]
            },
            {
                featureType: 'water',
                elementType: 'labels.text.stroke',
                stylers: [{color: '#17263c'}]
            }
        ]
    });

    // marker
    marker = new google.maps.Marker({
        position: position,
        title: 'Capital Bus Connect',
        icon: 'http://maps.google.com/mapfiles/ms/icons/ylw-pushpin.png',
        size: new google.maps.Size(80, 80)
    });
    marker.setMap(map);
}
/* Created by Sanjin Kurelic (kurelic@sanjin.eu) */

/*global $$, cbc_addClass, cbc_removeClass, cbc_findUpClass */

var RadioBox = {
    changeSelection: function (element, selectionName) {
        "use strict";
        var i, items, radios, parent;
        // Radio box item
        parent = cbc_findUpClass(element, "radioBox-parent");
        // Select radio item
        radios = $$("input[type=radio]", parent);
        for (i = 0; i < radios.length; i += 1) {
            if (radios[i].value === selectionName) {
                radios[i].checked = true;
                radios[i].onchange();
                break;
            }
        }
        // Set active item
        items = $$(".radioBox-item", parent);
        for (i = 0; i < items.length; i += 1) {
            cbc_removeClass(items[i], "active");
        }
        cbc_addClass(element, "active");
    },
    selectElement: function (elementsRoot, elementId) {
        "use strict";
        var i, elements = elementsRoot.children;
        for (i = 0; i < elements.length; i = i + 1) {
            if (elements[i].id === elementId) {
                cbc_removeClass(elements[i], "hidden");
            } else {
                cbc_addClass(elements[i], "hidden");
            }
        }
    }
};
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
/* Created by Sanjin Kurelic (kurelic@sanjin.eu) */

/*jslint browser: true */
/*global $$, cbc_addClass, cbc_removeClass, cbc_hasClass, cbc_addClickEventListener */

var SelectItem = {

    wrapSelectItem: function (selectTag) {
        "use strict";
        var parentElement, wrapElement;
        parentElement = selectTag.parentNode;
        wrapElement = document.createElement("DIV");
        // Copy attributes from select tag to class list of wrapper
        if (selectTag.hasAttribute("data-size")) {
            cbc_addClass(wrapElement, selectTag.getAttribute("data-size"));
        } else {
            cbc_addClass(wrapElement, "select-normal");
        }
        cbc_addClass(wrapElement, "select");
        // Also copy disabled attribute
        if (selectTag.hasAttribute("disabled")) {
            cbc_addClass(wrapElement, "disabled");
        }
        // Do the wrapping
        parentElement.replaceChild(wrapElement, selectTag);
        wrapElement.appendChild(selectTag);
    },

    setSelectItems: function () {
        "use strict";
        var selectElement, newSelectElement, newItemsElement, newItemElement, selectElements, i, j;
        // Foreach custom select component
        selectElements = $$("select");
        for (i = 0; i < selectElements.length; i += 1) {
            selectElement = selectElements[i];
            // Wrap around select element
            SelectItem.wrapSelectItem(selectElement);

            // Area for holding text of selected item (and opening select items)
            newSelectElement = document.createElement("DIV");
            newSelectElement.setAttribute("class", "select-selected");
            newSelectElement.innerHTML = selectElement.options[selectElement.selectedIndex].innerHTML;
            cbc_addClickEventListener(newSelectElement, SelectItem.showSelectItem);
            selectElement.parentNode.appendChild(newSelectElement);

            // Area for holding select items
            newItemsElement = document.createElement("DIV");
            newItemsElement.setAttribute("class", "select-items select-hide");
            for (j = 0; j < selectElement.length; j += 1) {
                newItemElement = document.createElement("DIV");
                // Attribute data-index is used on click - that index is set on real select component
                newItemElement.setAttribute("data-index", j);
                newItemElement.innerHTML = selectElement.options[j].innerHTML;
                cbc_addClickEventListener(newItemElement, SelectItem.selectSelectItem);
                newItemsElement.appendChild(newItemElement);
            }
            selectElement.parentNode.appendChild(newItemsElement);
        }
        cbc_addClickEventListener(document, SelectItem.closeAllSelectItems);
    },

    selectSelectItem: function (e) {
        "use strict";
        var selectNode, items, i, element;
        // Sender element
        element = e.target || e.srcElement;
        // Select element and fire onchange if exists
        selectNode = element.parentNode.parentNode.getElementsByTagName("select")[0];
        selectNode.selectedIndex = parseInt(element.getAttribute("data-index"));
        if (selectNode.hasAttribute("onchange")) {
            selectNode.onchange(e);
        }
        // Copy value to selected box
        element.parentNode.previousSibling.innerHTML = element.innerHTML;
        // Add selected styling to select items
        items = $$(".select-items-selected", element.parentNode);
        for (i = 0; i < items.length; i += 1) {
            cbc_removeClass(items[i], "select-items-selected");
        }
        cbc_addClass(element, "select-items-selected");
    },

    showSelectItem: function (e) {
        "use strict";
        var element, isOpened, isDisabled;
        // Sender element (select component)
        element = e.target || e.srcElement;
        isOpened = cbc_hasClass(element, "select-arrow-active");
        isDisabled = cbc_hasClass(element.parentNode, "disabled");
        e.stopPropagation();
        // Close all select components on page
        SelectItem.closeAllSelectItems();
        // Show this component
        if (!isOpened && !isDisabled) {
            cbc_removeClass(element.nextSibling, "select-hide");
            cbc_addClass(element, "select-arrow-active");
        }
    },

    closeAllSelectItems: function () {
        "use strict";
        var selectElement, i;
        selectElement = $$(".select");
        for (i = 0; i < selectElement.length; i += 1) {
            cbc_removeClass($$(".select-selected", selectElement[i])[0], "select-arrow-active");
            cbc_addClass($$(".select-items", selectElement[i])[0], "select-hide");
        }
    }
};
/* Created by Sanjin Kurelic (kurelic@sanjin.eu) */

/*global $$, $, cbc_addClass, cbc_removeClass */

function tabItemOpen(elem) {
    "use strict";
    var id, parent, tabs, i, tab;
    // Id of opened tab
    id = elem.getAttribute("data-tabId");
    // Parent containing tab menu selector
    parent = elem.parentNode;
    // List of all tabs in menu selector
    tabs = $$("[data-tabId]", parent);
    // Close all tabs and open right one + style menu with active class
    for (i = 0; i < tabs.length; i += 1) {
        tab = $(tabs[i].getAttribute("data-tabId"));
        if (tab.id === id) {
            cbc_addClass(tabs[i], "active");
            tab.style.display = "block";
        } else {
            cbc_removeClass(tabs[i], "active");
            tab.style.display = "none";
        }
    }
}
/* Created by Sanjin Kurelic (kurelic@sanjin.eu) */

/*global Function */

/**
 * Get element by id from document
 * @param {String} id
 * @returns {Element}
 */
function $(id) {
    "use strict";
    return document.getElementById(id);
}

/**
 * Query selector all from parent (default = window)
 * @param {String} element
 * @param {Element} parent
 * @returns {NodeList}
 */
function $$(element, parent) {
    "use strict";
    parent = parent || document;
    return parent.querySelectorAll(element);
}

/**
 * Find parent node with tag name
 * @param {Element} elem
 * @param {String} tag
 * @returns {Element}
 */
function cbc_findUpTag(elem, tag) {
    "use strict";
    while (elem.parentNode) {
        elem = elem.parentNode;
        if (elem.tagName.toUpperCase() === tag.toUpperCase()) {
            return elem;
        }
    }
    return null;
}

/**
 * Remove class from element
 * @param {Element} element
 * @param {String} className
 * @returns {void}
 */
function cbc_removeClass(element, className) {
    "use strict";
    element.className = element.className.replace(className, "");
}

/**
 * Return true if element has class
 * @param {Element} element
 * @param {String} className
 * @returns {Boolean}
 */
function cbc_hasClass(element, className) {
    "use strict";
    return (element.className.split(" ").indexOf(className) !== -1);
}

/**
 * Add class to element
 * @param {Element} element
 * @param {String} className
 * @returns {void}
 */
function cbc_addClass(element, className) {
    "use strict";
    if (!cbc_hasClass(element, className)) {
        element.className += " " + className;
    }
}

/**
 * Add or remove class from element
 * @param {Element} element
 * @param {String} className
 * @returns {void}
 */
function cbc_toggleClass(element, className) {
    "use strict";
    if (cbc_hasClass(element, className)) {
        cbc_removeClass(element, className);
    } else {
        cbc_addClass(element, className);
    }
}

/**
 * Find parent node with class name
 * @param {Element} elem
 * @param {String} className
 * @returns {Element}
 */
function cbc_findUpClass(elem, className) {
    "use strict";
    while (elem.parentNode) {
        elem = elem.parentNode;
        if (cbc_hasClass(elem, className)) {
            return elem;
        }
    }
    return null;
}

/**
 * Add event listener
 * @param {event} event
 * @param {function} func
 * @param {Element} element
 * @returns {void}
 */
function cbc_addEventListener(event, element, func) {
    "use strict";
    element = element || document;
    if (element.addEventListener) {
        element.addEventListener(event, func, true);
    } else if (element.attachEvent) {
        element.attachEvent("on" + event, func);
    }
}

/**
 * Add onclick
 * @param {Element} element
 * @param {function} func
 * @returns {void}
 */
function cbc_addClickEventListener(element, func) {
    "use strict";
    cbc_addEventListener("click", element, func);
}

/**
 * Add event listener to element that blocks all clicks
 * @param {Event} e
 */
function cbc_blockEvents(e) {
    "use strict";
    e.cancelBubble = true;
    if (e.stopPropagation) {
        e.stopPropagation();
    }
    return false;
}

/**
 * If browser support ECMAScript 5, freez the object
 * @param {Object} object
 * @returns {Object}
 */
function cbc_enum(object) {
    "use strict";
    if (Object.freeze) {
        return Object.freeze(object);
    }
    return object;
}

/**
 * Get empty function
 * @returns {Function}
 */
function cbc_voidFunction() {
    "use strict";
    return function () {
        return undefined;
    };
}

/**
 * Convert decimal number to visible format
 * @param decimalNumber
 * @returns {string}
 */
function cbc_formatDecimal(decimalNumber) {
    "use strict";
    return decimalNumber.toFixed(2).toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
}

/**
 * Convert HRL currency to Euro, yeah we should use some central bank api :)
 * @param value
 * @returns {number}
 */
function cbc_hrkToEuro(value) {
    "use strict";
    return value / 7.5;
}

/**
 * ECMAScript 5 property.bind polyfill
 * @type type
 */
if (!Function.prototype.bind) {
    Function.prototype.bind = function (oThis) {
        "use strict"; //for jslint
        var aArgs, fToBind, FNOP, fBound;
        if (typeof this !== "function") {
            throw new TypeError("Function.prototype.bind - what is trying to be bound is not callable");
        }
        aArgs = Array.prototype.slice.call(arguments, 1);
        fToBind = this;
        FNOP = cbc_voidFunction();
        fBound = function () {
            return fToBind.apply(this instanceof FNOP
                ? this
                : oThis,
                aArgs.concat(Array.prototype.slice.call(arguments)));
        };
        if (this.prototype) {
            FNOP.prototype = this.prototype;
        }
        fBound.prototype = new FNOP();
        return fBound;
    };
}

/**
 * IE 8 indexOf support
 */
if (!Array.prototype.indexOf) {
    Array.prototype.indexOf = function (elt, num) {
        "use strict";
        var from, len;
        len = parseInt(Math.abs(this.length), 10);
        from = num || 0;
        if (from < 0) {
            from = Math.ceil(from);
        } else {
            from = Math.floor(from);
        }
        if (from < 0) {
            from += len;
        }
        for (from; from < len; from += 1) {
            if (this.hasOwnProperty(from) && this[from] === elt) {
                return from;
            }
        }
        return -1;
    };
}
/* 
 * Created by Sanjin Kurelic (kurelic@sanjin.eu)
 */

/*jslint browser: true */
/*global $$, SelectItem, cbc_addEventListener */

/**
 * On resize
 * @returns {void}
 */
function cbc_setDomOnResize() {
    "use strict";
    // Custom stuff
    $$("article")[0].style.paddingBottom = $$("footer")[0].clientHeight + "px";
}

/**
 * First called after DOM is loaded
 * @returns {void}
 */
function cbc_setDom() {
    "use strict";
    // Custom stuff
    SelectItem.setSelectItems();
    setDatePicker();

    // Resize event
    cbc_addEventListener('resize', window, cbc_setDomOnResize);
    cbc_setDomOnResize();
}
cbc_addEventListener('load', window, cbc_setDom);