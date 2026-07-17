/**
 * Returns a `Promise` to wait for a specified amount of time.
 * @param ms The time to wait.
 * @returns A `Promise` to wait for.
 */
function wait(ms: number): Promise<void> {
    return new Promise((resolve) => setTimeout(resolve, ms));
}
/**
 * Returns a random number from [0, 100].
 * @returns The random number.
 * @since v1.0.5
 */
function random(): number;
/**
 * Returns a random number from [0, `max`).
 * @returns The random number.
 * @since v1.0.5
 */
function random(max: number): number;
/**
 * Returns a random number from (`min`, `max`).
 * 
 * Automatically swaps `min` and `max` if `min` > `max`.
 * @returns The random number.
 * @since v1.0.5
 */
function random(min: number, max: number): number;
function random(a?: number, b?: number): number {
    let min = 0; let max = 0;
    if(a == undefined && b == undefined) {
        min = 0; max = 101;
    } else if(a && b == undefined) {
        min = 0; max = a;
    } else if(a && b) {
        min = a; max = b;
    }
    if(min > max) {
        [min, max] = [max, min];
    }
    return Math.floor(Math.random() * (max - min)) + min;
}
/**
 * Returns a `boolean` of whether a randomly-generated number is less-equal `max`.
 * 
 * Uses random(101).
 * @param max The cutoff point.
 * @returns If the number generated is in the cutoff point.
 * @since v1.0.10
 */
function chance(max: number): boolean;
/**
 * Returns a `boolean` of whether a randomly-generated number is less-equal `max`.
 * 
 * Uses random(`upperBound` + 1).
 * @param max The cutoff point.
 * @param upperBound The maximum bound to use.
 * @returns If the number generated is in the cutoff point.
 * @since v1.0.10
 */
function chance(max: number, upperBound: number): boolean;
function chance(max: number, upperBound?: number): boolean {
    return max <= random((upperBound ?? 100) + 1);
}

declare global {
    interface String {
        /**
         * Converts a string to its numeric form.
         */
        toNum(): number;
        /**
         * Converts a string to an int.
         */
        toInt(): number;
        /**
         * Converts a string to a float.
         */
        toFloat(): number;
        /**
         * Converts a string to uppercase format.
         */
        toUpper(): string;
        /**
         * Converts a string to lowercase format.
         */
        toLower(): string;
        /**
         * Converts a string to titlecase format.
         */
        toTitle(): string;
        /**
         * Shorthand for `indexOf`.
         * @param search The string to look for.
         * @param offset The offset.
         */
        idxOf(search: string, offset?: number): number;
        /**
         * Returns all indexes where `search` occurs.
         * @param search The string to look for.
         * @param offset The offset.
         */
        idxsOf(search: string, offset?: number): number[];
    }
    interface Number {
        /**
         * Returns the ceiling value.
         */
        roof(): number;
        /**
         * Returns the floor value.
         */
        floor(): number;
        /**
         * Returns the value, rounded to the nearest place.
         */
        round(): number;
        /**
         * Returns a string of this, rounded to the amount of places.
         * @param places The places to round to.
         */
        round(places: number): string;
        /**
         * Returns whether this is an integer.
         */
        isInt(): boolean;
        /**
         * Returns whether this is not an integer.
         */
        isFloat(): boolean;
    }
    interface Array<T> {
        /**
         * Appends items to the array.
         * @param items The items to add.
         */
        add(...items: T[]): void;
        /**
         * Removes all occurences of the items from the array.
         * @param items The items to remove.
         */
        rm(...items: T[]): void;
        /**
         * Checks if all the items are present in the array.
         * @param items The items to check.
         */
        has(...items: T[]): void;
        /**
         * Substitutes old values for new values in the array.
         * @param old The old value.
         * @param nw The new value.
         */
        sub(old: T, nw: T): void;
        /**
         * Returns a random element from the array.
         */
        random(): T;
        /**
         * Shorthand for `indexOf`.
         * @param search The element to look for.
         * @param offset The offset.
         */
        idxOf(search: T, offset?: number): number;
        /**
         * Returns all indexes where `search` occurs.
         * @param search The element to look for.
         * @param offset The offset.
         */
        idxsOf(search: T, offset?: number): number[];
    }
    interface Object {
        /**
         * Converts this object to its boolean representation.
         */
        toBool(): boolean;
        /**
         * Returns the keys of the object.
         */
        keys(): string[];
        /**
         * Returns the values of the object.
         */
        values<T>(): T[];
        /**
         * Returns the entries of the object.
         */
        items<T>(): [string, T][];
        /**
         * Converts this object to a string.
         */
        str(): string;
        /**
         * Returns whether this object contains a key `key`.
         * @param key The key to check.
         */
        has(key: string): boolean;
    }
}
String.prototype.toNum = function(this) {
    return Number(this);
}
String.prototype.toInt = function(this) {
    return parseInt(this.valueOf());
}
String.prototype.toFloat = function(this) {
    return parseFloat(this.valueOf());
}
String.prototype.toUpper = function(this) {
    return this.toUpperCase();
}
String.prototype.toLower = function(this) {
    return this.toLowerCase();
}
String.prototype.toTitle = function(this) {
    return this.replace(/\w\S*/g, w => w.charAt(0).toUpperCase() + w.substring(1).toLowerCase());
}
String.prototype.idxOf = String.prototype.indexOf;
String.prototype.idxsOf = function(this, off) {
    return idxsOfCore(this, off);
}
function idxsOfCore<T>(thisArg: String | Array<T>, search: string | T, off?: number) {
    const out: number[] = [];
    const idx = (o: number) => thisArg instanceof String ? thisArg.indexOf(search as string, o) : thisArg.indexOf(search as T, o);
    let next = off ?? 0;
    let i = idx(next);
    while(i != -1) {
        out.push(i);
        i = idx(next);
    }
    return out;
}

Number.prototype.roof = function(this) {
    return Math.ceil(this.valueOf());
}
Number.prototype.floor = function(this) {
    return Math.floor(this.valueOf());
}
function round(this: Number): number;
function round(this: Number, places: number): string;
function round(this: Number, places?: number) {
    if(places) return this.toFixed(places);
    return Math.round(this.valueOf());
}
Number.prototype.round = round;
Number.prototype.isInt = function(this) {
    return Number.isInteger(this);
}
Number.prototype.isFloat = function(this) {
    return !this.isInt();
}

function sub<T>(this: Array<T>, old: T, nw: T): void;
function sub<T>(this: Array<T>, old: T, nw: T, count: number): void;
function sub<T>(this: Array<T>, old: T, nw: T, count = Infinity) {
    while(this.includes(old) && count > 0) {
        this[this.indexOf(old)] = nw;
        count--;
    }
}
Array.prototype.add = function<T>(this, ...items: T[]) {
    this.push(...items);
}
Array.prototype.rm = function<T>(this, ...items: T[]) {
    for(let i = this.length - 1; i >= 0; i--) {
        if(items.includes(this[i])) {
            this.splice(i, 1);
        }
    }
}
Array.prototype.has = function<T>(this, ...items: T[]) {
    return items.every(i => this.includes(i));
}
Array.prototype.sub = sub;
Array.prototype.random = function(this) {
    return this[random(0, this.length)];
}
Array.prototype.idxOf = Array.prototype.indexOf;
Array.prototype.idxsOf = function(this, off) {
    return idxsOfCore(this, off);
}

Object.prototype.toBool = function(this) {
    return !!this;
}
Object.prototype.keys = function(this) {
    return Object.keys(this.valueOf());
}
Object.prototype.values = function(this) {
    return Object.values(this.valueOf());
}
Object.prototype.items = function(this) {
    return Object.entries(this.valueOf());
}
Object.prototype.str = function(this) {
    return JSON.stringify(this);
}
Object.prototype.has = function(this, key) {
    return Object.hasOwn(this, key);
}

export { random, chance, wait };