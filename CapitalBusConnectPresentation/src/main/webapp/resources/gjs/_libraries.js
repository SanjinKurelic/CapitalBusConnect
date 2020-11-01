/* Created by Sanjin Kurelić (kurelic@sanjin.eu) */

/*global Function */

/**
 * Get element by id from document
 * @param {string} id
 * @returns {Element}
 */
function $(id) {
  "use strict";
  return document.getElementById(id);
}

/**
 * Query selector all from parent (default = window)
 * @param {string} element
 * @param {Element} [parent]
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
 * @param {string} tag
 * @returns {Element}
 */
function cbc_findUpTag(elem, tag) {
  "use strict";
  while (elem.parentElement) {
    elem = elem.parentElement;
    if (elem.tagName.toUpperCase() === tag.toUpperCase()) {
      return elem;
    }
  }
  return null;
}

/**
 * Remove class from element
 * @param {Element} element
 * @param {string} className
 * @returns {void}
 */
function cbc_removeClass(element, className) {
  "use strict";
  element.className = element.className.replace(className, "");
}

/**
 * Return true if element has class
 * @param {Element} element
 * @param {string} className
 * @returns {Boolean}
 */
function cbc_hasClass(element, className) {
  "use strict";
  return (element.className.split(" ").indexOf(className) !== -1);
}

/**
 * Add class to element
 * @param {Element} element
 * @param {string} className
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
 * @param {string} className
 * @returns {void}
 *
 function cbc_toggleClass(element, className) {
    "use strict";
    if (cbc_hasClass(element, className)) {
        cbc_removeClass(element, className);
    } else {
        cbc_addClass(element, className);
    }
}*/

/**
 * Find parent node with class name
 * @param {Element} elem
 * @param {string} className
 * @returns {Element}
 */
function cbc_findUpClass(elem, className) {
  "use strict";
  while (elem.parentElement) {
    elem = elem.parentElement;
    if (cbc_hasClass(elem, className)) {
      return elem;
    }
  }
  return null;
}

/**
 * Add event listener
 * @param {string} event
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
 * @param {Element | Document} element
 * @param {function} func
 * @returns {void}
 */
function cbc_addClickEventListener(element, func) {
  "use strict";
  cbc_addEventListener("click", element, func);
}

/**
 * Remove all event listeners
 * @param {Element} element
 */
function cbc_removeEventListeners(element) {
  "use strict";
  element.parentElement.replaceChild(element.cloneNode(true), element);
}

/**
 * Add event listener to element that blocks all clicks
 * @param {Event} e
 */
function cbc_blockEvents(e) {
  "use strict";
  e.cancelBubble = true;
  if (e.stopPropagation) {
    e.stopPropagation();
  }
  return false;
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
    return undefined;
  };
}

/**
 * Async call to function
 * @param {function} func
 */
function cbc_async(func) {
  "use strict";
  setTimeout(func, 0);
}

/**
 * Convert decimal number to visible format
 * @param decimalNumber
 * @returns {string}
 */
function cbc_formatDecimal(decimalNumber) {
  "use strict";
  return decimalNumber.toFixed(2).toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
}

/**
 * Convert HRL currency to Euro, yeah we should use some central bank api :)
 * @param value
 * @returns {string}
 */
function cbc_hrkToEuro(value) {
  "use strict";
  var euro = value / 7.5;
  return euro.toFixed(2);
}

/**
 * ECMAScript 5 property.bind polyfill
 * @type type
 */
// noinspection JSLint
if (!Function.prototype.bind) {
  // noinspection JSLint
  Function.prototype.bind = function (oThis) {
    "use strict";
    var aArgs, fToBind, FNOP, fBound;
    if (typeof this !== "function") {
      throw new TypeError("Function.prototype.bind - what is trying to be bound is not callable");
    }
    aArgs = Array.prototype.slice.call(arguments, 1);
    fToBind = this;
    FNOP = cbc_voidFunction();
    fBound = function () {
      // noinspection JSLint
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
    var from, len, radix = 10;
    len = parseInt(Math.abs(this.length).toString(radix), radix);
    from = num || 0;
    if (from < 0) {
      from = Math.ceil(from);
    } else {
      from = Math.floor(from);
    }
    if (from < 0) {
      from += len;
    }
    // noinspection JSLint
    for (from; from < len; from += 1) {
      if (this.hasOwnProperty(from) && this[from] === elt) {
        return from;
      }
    }
    return -1;
  };
}