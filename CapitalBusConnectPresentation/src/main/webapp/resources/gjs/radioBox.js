/* Created by Sanjin Kurelić (kurelic@sanjin.eu) */

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
        // noinspection JSUndefinedPropertyAssignment
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