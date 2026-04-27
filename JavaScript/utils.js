/**
 * Returns a `Promise` to wait for a specified amount of time.
 * @param ms The time to wait.
 * @returns A `Promise` to wait for.
 */
function wait(ms) {
    return new Promise((resolve) => setTimeout(resolve, ms));
}
function random(a, b) {
    let min = 0;
    let max = 0;
    if (a == undefined && b == undefined) {
        min = 0;
        max = 101;
    }
    else if (a && b == undefined) {
        min = 0;
        max = a;
    }
    else if (a && b) {
        min = a;
        max = b;
    }
    if (min > max) {
        [min, max] = [max, min];
    }
    return Math.floor(Math.random() * (max - min)) + min;
}
function chance(max, upperBound) {
    return max <= random((upperBound ?? 100) + 1);
}
function attach(ctor, name, fn) {
    Object.defineProperty(ctor.prototype, name, {
        value: fn,
        enumerable: false, // Standard methods are usually non-enumerable
        configurable: true,
        writable: true,
    });
}
const strMth = (name, fn) => attach(String, name, fn);
const numMth = (name, fn) => attach(Number, name, fn);
const arrMth = (name, fn) => attach((Array), name, fn);
const objMth = (name, fn) => attach(Object, name, fn);
String.prototype.toNum = function () {
    return Number(this);
};
String.prototype.toInt = function () {
    return parseInt(this.valueOf());
};
String.prototype.toFloat = function () {
    return parseFloat(this.valueOf());
};
String.prototype.toUpper = function () {
    return this.toUpperCase();
};
String.prototype.toLower = function () {
    return this.toLowerCase();
};
String.prototype.toTitle = function () {
    return this.replace(/\w\S*/g, w => w.charAt(0).toUpperCase() + w.substring(1).toLowerCase());
};
Number.prototype.roof = function () {
    return Math.ceil(this.valueOf());
};
Number.prototype.floor = function () {
    return Math.floor(this.valueOf());
};
function round(places) {
    if (places)
        return this.toFixed(places);
    return Math.round(this.valueOf());
}
Number.prototype.round = round;
function add(...items) {
    this.push(...items);
}
function rm(...items) {
    for (let i = this.length - 1; i >= 0; i--) {
        if (items.includes(this[i])) {
            this.splice(i, 1);
        }
    }
}
function has(...items) {
    return items.every(i => this.includes(i));
}
function sub(old, nw, count = Infinity) {
    while (this.includes(old) && count > 0) {
        this[this.indexOf(old)] = nw;
        count--;
    }
}
Array.prototype.add = add;
Array.prototype.rm = rm;
Array.prototype.has = has;
Array.prototype.sub = sub;
Array.prototype.random = function () {
    return this[random(0, this.length)];
};
Object.prototype.toBool = function () {
    return !!this;
};
Object.prototype.keys = function () {
    return Object.keys(this.valueOf());
};
Object.prototype.values = function () {
    return Object.values(this.valueOf());
};
Object.prototype.items = function () {
    return Object.entries(this.valueOf());
};
Object.prototype.str = function () {
    return JSON.stringify(this);
};
function objHas(key) {
    return Object.hasOwn(this, key);
}
Object.prototype.has = objHas;
Number.prototype.isInt = function () {
    return Number.isInteger(this);
};
Number.prototype.isFloat = function () {
    return !this.isInt();
};
export { random, chance, wait };
