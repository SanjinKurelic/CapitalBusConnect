/* Created by Sanjin Kurelić (kurelic@sanjin.eu) */

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
    // noinspection JSLint
    fetch = new Fetch(Cart.url);
    fetch.method = FetchHttpMethods.POST;
    // noinspection JSLint
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
      // noinspection JSLint
      fetch = new Fetch(Cart.url);
      fetch.method = FetchHttpMethods.DELETE;
      // noinspection JSLint
      fetch.bodyParameters = Cart.getItem(itemElement);
      fetch.onSuccess = function () {
        var parent = itemElement.parentElement;
        parent.removeChild(itemElement);
        if (parent.children.length <= 1) { // hasChildNodes also return non Element nodes!!!
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
    // noinspection JSLint
    fetch = new Fetch(Cart.url);
    fetch.method = FetchHttpMethods.PUT;
    // noinspection JSLint
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
      // noinspection JSLint
      item = Cart.getItem(itemElement);
      basePrice = priceBox.getAttribute("data-base-price");
      // Set price and currency
      price = item.numberOfAdults * basePrice + item.numberOfChildren * basePrice;
      priceElements = priceBox.innerHTML.trim().split(" ");
      currency = priceElements[priceElements.length - 1];
      priceBox.innerHTML = cbc_formatDecimal(price) + " " + currency;
      // noinspection JSLint
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