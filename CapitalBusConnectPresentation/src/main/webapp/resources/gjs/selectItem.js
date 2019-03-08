/* 
 * Created by Sanjin Kurelic (kurelic@sanjin.eu)
 */

/*jslint browser: true */
/*global $$, cbc_addClass, cbc_removeClass, cbc_hasClass, cbc_addClickEventListener */

var SelectItem = {

    wrapSelectItem: function (selectTag) {
        "use strict";
        var parentElement, wrapElement;
        parentElement = selectTag.parentNode;
        wrapElement = document.createElement("DIV");
        // Copy attributes from select tag to class list of wrapper
        cbc_addClass(wrapElement, (selectTag.hasAttribute("data-size") ? selectTag.getAttribute("data-size") : "select-normal"));
        cbc_addClass(wrapElement, "select");
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

            // Area for hodling select items
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
        selectNode.selectedIndex = element.getAttribute("data-index");
        if (selectNode.hasAttribute('onchange')) {
            selectNode.onchange();
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