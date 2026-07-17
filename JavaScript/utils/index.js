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
String.prototype.idxOf = String.prototype.indexOf;
String.prototype.idxsOf = function (off) {
    return idxsOfCore(this, off);
};
function idxsOfCore(thisArg, search, off) {
    const out = [];
    const idx = (o) => thisArg instanceof String ? thisArg.indexOf(search, o) : thisArg.indexOf(search, o);
    let next = off ?? 0;
    let i = idx(next);
    while (i != -1) {
        out.push(i);
        i = idx(next);
    }
    return out;
}
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
Number.prototype.isInt = function () {
    return Number.isInteger(this);
};
Number.prototype.isFloat = function () {
    return !this.isInt();
};
function sub(old, nw, count = Infinity) {
    while (this.includes(old) && count > 0) {
        this[this.indexOf(old)] = nw;
        count--;
    }
}
Array.prototype.add = function (...items) {
    this.push(...items);
};
Array.prototype.rm = function (...items) {
    for (let i = this.length - 1; i >= 0; i--) {
        if (items.includes(this[i])) {
            this.splice(i, 1);
        }
    }
};
Array.prototype.has = function (...items) {
    return items.every(i => this.includes(i));
};
Array.prototype.sub = sub;
Array.prototype.random = function () {
    return this[random(0, this.length)];
};
Array.prototype.idxOf = Array.prototype.indexOf;
Array.prototype.idxsOf = function (off) {
    return idxsOfCore(this, off);
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
Object.prototype.has = function (key) {
    return Object.hasOwn(this, key);
};
export { random, chance, wait };
