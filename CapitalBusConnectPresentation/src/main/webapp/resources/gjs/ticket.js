/*global window, cbc_findUpTag */

var Ticket = {
    url: "ticket/",
    width: 320,
    height: 430,
    show: function (element) {
        "use strict";
        var id, dualScreenLeft, dualScreenTop, width, height, left, top, systemZoom, position;
        id = cbc_findUpTag(element, "tr").getAttribute("data-id-param");
        // Set variables
        dualScreenLeft = window.screenLeft || window.screenX;
        dualScreenTop = window.screenTop || window.screenY;
        width = window.innerWidth || document.documentElement.clientWidth || screen.width;
        height = window.innerHeight || document.documentElement.clientHeight || screen.height;
        systemZoom = width / window.screen.availWidth;
        // noinspection JSLint
        left = (width - Ticket.width) / 2 / systemZoom + dualScreenLeft;
        // noinspection JSLint
        top = (height - Ticket.height) / 2 / systemZoom + dualScreenTop;
        // noinspection JSLint
        position = "width=" + Ticket.width + ",height=" + Ticket.height + ",left=" + left + ",top=" + top;
        // noinspection JSLint
        window.open(Ticket.url + id, "_blank", "menubar=no,status=no,toolbar=no,location=no," + position);
    }
};