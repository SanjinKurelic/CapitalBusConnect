/* Created by Sanjin Kurelić (kurelic@sanjin.eu) */

/*global $, $$, Pikaday */

function setDatePicker() {
    "use strict";
    var parseFunction, toStringFunction, i, datePickers, datePicker, minDate, maxDate, defaultDate, picker;
    // Use default format YYYY-MM-DD
    parseFunction = function (date) {
        var parts = date.split("-");
        return new Date(parseInt(parts[0], 10), parseInt(parts[1], 10) - 1, parseInt(parts[2], 10));
    };
    toStringFunction = function (date) {
        var day, month, year;
        year = date.getFullYear();
        month = date.getMonth() + 1;
        if (month < 10) {
            month = "0" + month;
        }
        day = date.getDate();
        if (day < 10) {
            day = "0" + day;
        }
        return year + "-" + month + "-" + day;
    };
    // Get all date pickers
    datePickers = $$("input[data-type=\"date\"]");
    for (i = 0; i < datePickers.length; i += 1) {
        datePicker = datePickers[i];
        if (!datePicker.hasAttribute("pattern")) {
            datePicker.setAttribute("pattern", "^[1-3][0-9]{3}-[0-1][0-9]-[0-3][0-9]$"); // just for typo errors
        }
        if (datePicker.hasAttribute("data-min")) {
            minDate = parseFunction(datePicker.getAttribute("data-min"));
        } else {
            minDate = null;
        }
        if (datePicker.hasAttribute("data-max")) {
            maxDate = parseFunction(datePicker.getAttribute("data-max"));
        } else {
            maxDate = null;
        }
        if (datePicker.hasAttribute("data-default")) {
            defaultDate = parseFunction(datePicker.getAttribute("data-default"));
        } else {
            defaultDate = null;
        }
        picker = new Pikaday({
            field: datePickers[i],
            format: "YYYY-MM-DD",
            toString: toStringFunction,
            parse: parseFunction,
            minDate: minDate,
            maxDate: maxDate,
            defaultDate: defaultDate,
            yearSuffix: ".",
            firstDay: 1,
            i18n: {
                previousMonth: $("datePicker-ui-previousMonth").innerHTML,
                nextMonth: $("datePicker-ui-nextMonth").innerHTML,
                months: $("datePicker-ui-months").innerHTML.split(","),
                weekdays: $("datePicker-ui-weekdays").innerHTML.split(","),
                weekdaysShort: $("datePicker-ui-weekdaysShort").innerHTML.split(",")
            }
        });
        picker.hide();
    }
}