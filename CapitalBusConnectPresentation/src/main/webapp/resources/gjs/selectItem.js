/* Created by Sanjin Kurelić (kurelic@sanjin.eu) */

/*global $$, cbc_addClass, cbc_removeClass, cbc_hasClass, cbc_addClickEventListener */

var SelectItem = {

    wrapSelectItem: function (selectTag) {
        "use strict";
        var parentElement, wrapElement;
        parentElement = selectTag.parentElement;
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
            // noinspection JSLint
            SelectItem.wrapSelectItem(selectElement);

            // Area for holding text of selected item (and opening select items)
            newSelectElement = document.createElement("DIV");
            newSelectElement.setAttribute("class", "select-selected");
            newSelectElement.innerHTML = selectElement.options[selectElement.selectedIndex].innerHTML;
            // noinspection JSLint
            cbc_addClickEventListener(newSelectElement, SelectItem.showSelectItem);
            selectElement.parentElement.appendChild(newSelectElement);

            // Area for holding select items
            newItemsElement = document.createElement("DIV");
            newItemsElement.setAttribute("class", "select-items select-hide");
            for (j = 0; j < selectElement.length; j += 1) {
                newItemElement = document.createElement("DIV");
                // Attribute data-index is used on click - that index is set on real select component
                newItemElement.setAttribute("data-index", j);
                newItemElement.innerHTML = selectElement.options[j].innerHTML;
                // noinspection JSLint
                cbc_addClickEventListener(newItemElement, SelectItem.selectSelectItem);
                newItemsElement.appendChild(newItemElement);
            }
            selectElement.parentElement.appendChild(newItemsElement);
        }
        // noinspection JSLint
        cbc_addClickEventListener(document, SelectItem.closeAllSelectItems);
    },

    selectSelectItem: function (e) {
        "use strict";
        var selectElement, items, i, element;
        // Sender element
        // noinspection JSDeprecatedSymbols
        element = e.target || e.srcElement;
        // Select element and fire onchange if exists
        selectElement = element.parentElement.parentElement.getElementsByTagName("select")[0];
        selectElement.selectedIndex = parseInt(element.getAttribute("data-index"));
        if (selectElement.hasAttribute("onchange")) {
            selectElement.onchange(e);
        }
        // Copy value to selected box
        element.parentElement.previousSibling.innerHTML = element.innerHTML;
        // Add selected styling to select items
        items = $$(".select-items-selected", element.parentElement);
        for (i = 0; i < items.length; i += 1) {
            cbc_removeClass(items[i], "select-items-selected");
        }
        cbc_addClass(element, "select-items-selected");
    },

    showSelectItem: function (e) {
        "use strict";
        var element, isOpened, isDisabled;
        // Sender element (select component)
        // noinspection JSDeprecatedSymbols
        element = e.target || e.srcElement;
        isOpened = cbc_hasClass(element, "select-arrow-active");
        isDisabled = cbc_hasClass(element.parentElement, "disabled");
        e.stopPropagation();
        // Close all select components on page
        // noinspection JSLint
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