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
        toNum(): number;
        toInt(): number;
        toFloat(): number;
        toUpper(): string;
        toLower(): string;
        toTitle(): string;
    }
    interface Number {
        roof(): number;
        floor(): number;
        round(): number;
    }
    interface Array<T> {
        add(...items: T[]): void;
        rm(...items: T[]): void;
        has(...items: T[]): void;
        sub(old: T, nw: T): void;
        random(): T;
    }
    interface Object {
        toBool(): boolean;
        keys(): string[];
        values<T>(): T[];
        items<T>(): [string, T][];
        str(): string;
        has(key: string): boolean;
    }
}
function attach<C extends new (...args: any[]) => any>(ctor: C, name: string, fn: (this: InstanceType<C>, ...args: any[]) => any) {
  Object.defineProperty(ctor.prototype, name, {
    value: fn,
    enumerable: false, // Standard methods are usually non-enumerable
    configurable: true,
    writable: true,
  });
}
const strMth = (name: string, fn: (this: String) => any) => attach(String, name, fn);
const numMth = (name: string, fn: (this: Number) => any) => attach(Number, name, fn);
const arrMth = <T>(name: string, fn: (this: Array<T>) => any) => attach(Array<T>, name, fn);
const objMth = (name: string, fn: (this: Object) => any) => attach(Object, name, fn);

strMth("toNum", function(this) {
    return Number(this);
});
strMth("toInt", function(this) {
    return parseInt(this.valueOf());
});
strMth("toFloat", function(this) {
    return parseFloat(this.valueOf());
});
strMth("toUpper", function(this) {
    return this.toUpperCase();
});
strMth("toLower", function(this) {
    return this.toLowerCase();
});
strMth("toTitle", function(this) {
    return this.replace(/\w\S*/g, w => w.charAt(0).toUpperCase() + w.substring(1).toLowerCase());
});
numMth("roof", function(this) {
    return Math.ceil(this.valueOf());
});
numMth("floor", function(this) {
    return Math.floor(this.valueOf());
});
function round(this: Number): number;
function round(this: Number, places: number): string;
function round(this: Number, places?: number): number | string {
    if(places) return this.toFixed(places);
    return Math.round(this.valueOf());
}
Number.prototype.round = round;
function add<T>(this: Array<T>, ...items: T[]) {
    this.push(...items);
}
function rm<T>(this: Array<T>, ...items: T[]) {
    for(let i = this.length - 1; i >= 0; i--) {
        if(items.includes(this[i])) {
            this.splice(i, 1);
        }
    }
}
function has<T>(this: Array<T>, ...items: T[]) {
    return items.every(i => this.includes(i));
}
function sub<T>(this: Array<T>, old: T, nw: T): void;
function sub<T>(this: Array<T>, old: T, nw: T, count: number): void;
function sub<T>(this: Array<T>, old: T, nw: T, count = Infinity) {
    while(this.includes(old) && count > 0) {
        this[this.indexOf(old)] = nw;
        count--;
    }
}
Array.prototype.add = add;
Array.prototype.rm = rm;
Array.prototype.has = has;
Array.prototype.sub = sub;
arrMth("random", function(this) {
    return this[random(0, this.length)];
});
objMth("toBool", function(this) {
    return !!this;
});
objMth("keys", function(this) {
    return Object.keys(this);
});
objMth("values", function(this) {
    return Object.values(this);
});
objMth("items", function(this) {
    return Object.entries(this);
});
objMth("str", function(this) {
    return JSON.stringify(this);
});
function objHas(this: Object, key: string) {
    return Object.hasOwn(this, key);
}
Object.prototype.has = objHas;

export { random, chance, wait };