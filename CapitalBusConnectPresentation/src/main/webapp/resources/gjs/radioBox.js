/* 
 * Created by Sanjin Kurelic (kurelic@sanjin.eu)
 */

/*jslint browser: true */
/*global $$, cbc_addClass, cbc_removeClass, cbc_findUpTag */

var RadioBox = {
    changeSelection: function (element, selectionName) {
        "use strict";
        var i, items, radios, parent;
        // Radio box item
        parent = cbc_findUpClass(element, "radioBox-parent");
        // Select radio item
        radios = $$('input[type=radio]', parent);
        for (i = 0; i < radios.length; i += 1) {
            if (radios[i].value === selectionName) {
                radios[i].checked = true;
                break;
            }
        }
        // Set active item
        items = $$('.radioBox-item', parent);
        for (i = 0; i < items.length; i += 1) {
            cbc_removeClass(items[i], 'active');
        }
        cbc_addClass(element, 'active');
    }
};