/* Created by Sanjin Kurelic (kurelic@sanjin.eu) */

/*global Function */

/**
 * Get element by id from document
 * @param {String} id
 * @returns {Element}
 */
function $(id) {
    "use strict";
    return document.getElementById(id);
}

/**
 * Query selector all from parent (default = window)
 * @param {String} element
 * @param {Element} parent
 * @returns {NodeList}
 */
function $$(element, parent) {
    "use strict";
    parent = parent || document;
    return parent.querySelectorAll(element);
}

/**
 * Find parent node with tag name
 * @param {Element} elem
 * @param {String} tag
 * @returns {Element}
 */
function cbc_findUpTag(elem, tag) {
    "use strict";
    while (elem.parentNode) {
        elem = elem.parentNode;
        if (elem.tagName.toUpperCase() === tag.toUpperCase()) {
            return elem;
        }
    }
    return null;
}

/**
 * Remove class from element
 * @param {Element} element
 * @param {String} className
 * @returns {void}
 */
function cbc_removeClass(element, className) {
    "use strict";
    element.className = element.className.replace(className, "");
}

/**
 * Return true if element has class
 * @param {Element} element
 * @param {String} className
 * @returns {Boolean}
 */
function cbc_hasClass(element, className) {
    "use strict";
    return (element.className.split(" ").indexOf(className) !== -1);
}

/**
 * Add class to element
 * @param {Element} element
 * @param {String} className
 * @returns {void}
 */
function cbc_addClass(element, className) {
    "use strict";
    if (!cbc_hasClass(element, className)) {
        element.className += " " + className;
    }
}

/**
 * Add or remove class from element
 * @param {Element} element
 * @param {String} className
 * @returns {void}
 */
function cbc_toggleClass(element, className) {
    "use strict";
    if (cbc_hasClass(element, className)) {
        cbc_removeClass(element, className);
    } else {
        cbc_addClass(element, className);
    }
}

/**
 * Find parent node with class name
 * @param {Element} elem
 * @param {String} className
 * @returns {Element}
 */
function cbc_findUpClass(elem, className) {
    "use strict";
    while (elem.parentNode) {
        elem = elem.parentNode;
        if (cbc_hasClass(elem, className)) {
            return elem;
        }
    }
    return null;
}

/**
 * Add event listener
 * @param {event} event
 * @param {function} func
 * @param {Element} element
 * @returns {void}
 */
function cbc_addEventListener(event, element, func) {
    "use strict";
    element = element || document;
    if (element.addEventListener) {
        element.addEventListener(event, func, true);
    } else if (element.attachEvent) {
        element.attachEvent("on" + event, func);
    }
}

/**
 * Add onclick
 * @param {Element} element
 * @param {function} func
 * @returns {void}
 */
function cbc_addClickEventListener(element, func) {
    "use strict";
    cbc_addEventListener("click", element, func);
}

/**
 * If browser support ECMAScript 5, freez the object
 * @param {Object} object
 * @returns {Object}
 */
function cbc_enum(object) {
    "use strict";
    if (Object.freeze) {
        return Object.freeze(object);
    }
    return object;
}

/**
 * Get empty function
 * @returns {Function}
 */
function cbc_voidFunction() {
    "use strict";
    return function () {
        return;
    };
}

/**
 * ECMAScript 5 property.bind polyfill
 * @type type
 */
if (!Function.prototype.bind) {
    Function.prototype.bind = function (oThis) {
        "use strict"; //for jslint
        var aArgs, fToBind, FNOP, fBound;
        if (typeof this !== 'function') {
            throw new TypeError('Function.prototype.bind - what is trying to be bound is not callable');
        }
        aArgs = Array.prototype.slice.call(arguments, 1);
        fToBind = this;
        FNOP = cbc_voidFunction();
        fBound = function () {
            return fToBind.apply(this instanceof FNOP
                    ? this
                    : oThis,
                    aArgs.concat(Array.prototype.slice.call(arguments)));
        };
        if (this.prototype) {
            FNOP.prototype = this.prototype;
        }
        fBound.prototype = new FNOP();
        return fBound;
    };
}

/**
 * IE 8 indexOf support
 */
if (!Array.prototype.indexOf) {
    Array.prototype.indexOf = function (elt, num) {
        "use strict";
        var from, len;
        len = parseInt(Math.abs(this.length), 10);
        from = num || 0;
        if (from < 0) {
            from = Math.ceil(from);
        } else {
            from = Math.floor(from);
        }
        if (from < 0) {
            from += len;
        }
        for (from; from < len; from += 1) {
            if (this.hasOwnProperty(from) && this[from] === elt) {
                return from;
            }
        }
        return -1;
    };
}