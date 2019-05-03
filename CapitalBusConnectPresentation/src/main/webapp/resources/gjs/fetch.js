/* Created by Sanjin Kurelić (kurelic@sanjin.eu) */

/*global cbc_enum, cbc_voidFunction, $$ */

var FetchHttpMethods = cbc_enum({
    GET: "GET",
    POST: "POST",
    PUT: "PUT",
    DELETE: "DELETE"
});

var Fetch = function (url) {
    "use strict";
    this.method = FetchHttpMethods.GET;
    // noinspection JSLint
    this.url = url.startsWith("/") ? url.slice(1) : url;
    this.username = null;
    this.password = null;
    this.bodyParameters = null; // JSON
    this.urlParameters = "";
    this.onSuccess = cbc_voidFunction();
    this.onError = cbc_voidFunction();

};

Fetch.prototype.fetch = function () {
    "use strict";
    var xhttp, onSuccess, onError, csrf, csrfParameter, csrfToken, csrfHeader;
    xhttp = new XMLHttpRequest();
    // CSRF
    csrf = $$("meta[name='_csrf_parameter']").length !== 0;
    if (csrf) {
        csrfParameter = $$("meta[name='_csrf_parameter']")[0].getAttribute("content");
        csrfToken = $$("meta[name='_csrf']")[0].getAttribute("content");
        csrfHeader = $$("meta[name='_csrf_header']")[0].getAttribute("content");
    }

    if (typeof this.onSuccess === "function") {
        onSuccess = this.onSuccess;
    } else {
        onSuccess = cbc_voidFunction();
    }
    if (typeof this.onError === "function") {
        onError = this.onError;
    } else {
        onError = cbc_voidFunction();
    }
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4) {
            if (this.status === 200) {
                // noinspection JSLint
                try {
                    onSuccess(JSON.parse(this.responseText));
                } catch (e) {
                    onSuccess(this.responseText);
                }
            } else if (this.status === 204) {
                onSuccess();
            } else if (this.status >= 300) {
                // noinspection JSLint
                try {
                    onError(JSON.parse(this.responseText));
                } catch (ex) {
                    onError(this.responseText);
                }
            }
        }
    };
    // Append CRSF
    if (csrf) {
        if (this.urlParameters !== "") {
            this.urlParameters += "&";
        }
        this.urlParameters += csrfParameter + "=" + csrfToken;
    }
    xhttp.open(this.method, this.url + "?" + this.urlParameters, true, this.username, this.password);
    xhttp.setRequestHeader(csrfHeader, csrfToken);
    // if bodyParameters is JS object, convert it to JSON
    if (typeof this.bodyParameters === "object" && this.bodyParameters !== null) {
        this.bodyParameters = JSON.stringify(this.bodyParameters);
        xhttp.setRequestHeader("Content-Type", "application/json");
    }
    xhttp.send(this.bodyParameters);
};