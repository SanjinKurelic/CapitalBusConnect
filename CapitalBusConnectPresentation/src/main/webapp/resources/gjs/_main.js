/* Created by Sanjin Kurelić (kurelic@sanjin.eu) */

/*global window, $$, SelectItem, cbc_addEventListener, setDatePicker, initMap, cbc_async */

/**
 * On resize
 * @returns {void}
 */
function cbc_setDomOnResize() {
  "use strict";
  $$("article")[0].style.paddingBottom = $$("footer")[0].clientHeight + "px";
}

/**
 * First called after DOM is loaded
 * @returns {void}
 */
function cbc_setDom() {
  "use strict";
  // Resize event
  cbc_addEventListener("resize", window, cbc_setDomOnResize);
  cbc_setDomOnResize();
  // Custom stuff
  SelectItem.setSelectItems();
  setDatePicker();
  cbc_async(initMap);
}

cbc_addEventListener("load", window, cbc_setDom);
// Firefox FIX
cbc_addEventListener("load", $$("footer")[0], cbc_setDomOnResize);