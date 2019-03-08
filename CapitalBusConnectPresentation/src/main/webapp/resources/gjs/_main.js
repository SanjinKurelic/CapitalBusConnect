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