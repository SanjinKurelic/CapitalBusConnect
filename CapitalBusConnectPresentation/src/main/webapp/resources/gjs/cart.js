/* Created by Sanjin Kurelic (kurelic@sanjin.eu) */

/*global cbc_addClass, $$, cbc_findUpTag, cbc_addClickEventListener, Fetch, FetchHttpMethods, Dialog, DialogType, DialogMessageType, DialogButtonType */

var Cart = {
    url: "api/cart",
    item: function () {
        "use strict";
        this.scheduleId = 0;
        this.numberOfAdults = 0;
        this.numberOfChildren = 0;
        this.date = null;
        /*this.toString = function () {
            return "scheduleId=" + this.scheduleId +
                    "&numberOfAdults=" + this.numberOfAdults +
                    "&numberOfChildren=" + this.numberOfChildren +
                    "&date=" + this.date;
        };*/
    },

    getItem: function (itemElement) {
        "use strict";
        var item = new Cart.item();

        item.scheduleId = itemElement.getAttribute("data-id-param");
        item.date = itemElement.getAttribute("data-date-param");
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
            cbc_addClass(itemElement, "active");
            // disable all select items
            selectItems = $$(".select", itemElement);
            for (i = 0; i < selectItems.length; i += 1) {
                cbc_addClass(selectItems[i], "disabled");
            }
            // cancel event propagation
            cbc_addClickEventListener(itemElement, function (e) {
                e.cancelBubble = true;
                if (e.stopPropagation) {
                    e.stopPropagation();
                }
                return false;
            });
        };
        fetch.onError = function () {
            var dialog = new Dialog(DialogType.TOAST, "Dodavanje stavke u košarice nije uspjelo!", DialogMessageType.ERROR);
            dialog.show();
        };
        fetch.fetch();
    },

    removeFromCart: function (buttonElement) {
        "use strict";
        var question = new Dialog(DialogType.DIALOG, "Da li ste sigurni da želite ukloniti stavku?", DialogMessageType.WARNING);
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
                if(parent.children.length <= 0){ // hasChildNodes also return non Element nodes!!!
                    window.location.reload();
                }
            };
            fetch.onError = function () {
                var dialog = new Dialog(DialogType.TOAST, "Brisanje stavke iz košarice nije uspjelo!", DialogMessageType.ERROR);
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
            var dialog = new Dialog(DialogType.TOAST, "Ažuriranje košarice nije uspjelo!", DialogMessageType.ERROR);
            dialog.show();
        };
        fetch.fetch();
    }

};