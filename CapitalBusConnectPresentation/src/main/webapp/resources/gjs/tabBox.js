/* Created by Sanjin Kurelić (kurelic@sanjin.eu) */

/*global $$, $, cbc_addClass, cbc_removeClass */

function tabItemOpen(elem) {
    "use strict";
    var id, parent, tabs, i, tab;
    // Id of opened tab
    id = elem.getAttribute("data-tabId");
    // Parent containing tab menu selector
    parent = elem.parentElement;
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