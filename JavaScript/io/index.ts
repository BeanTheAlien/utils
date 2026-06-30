import * as fs from "fs";
import * as path from "path";

function readdirDeep(dir: string, ignore: string) {
    let out: string[] = [];
    const x = fs.readdirSync(dir, { withFileTypes: true });
    for(const i of x) {
        if(ignore.includes(i.name)) continue;
        const fp = path.join(dir, i.name);
        if(i.isDirectory()) {
            out = out.concat(readdirDeep(fp, ignore));
        } else {
            out.push(fp);
        }
    }
    return out;
}

/**
 * const fs = require('fs');
const path = require('path');

function getFilesClean(dirPath, ignoreList = []) {
    let results = [];
    const items = fs.readdirSync(dirPath, { withFileTypes: true });

    for (const item of items) {
        if (ignoreList.includes(item.name)) continue;

        const fullPath = path.join(dirPath, item.name);
        if (item.isDirectory()) {
            results = results.concat(getFilesClean(fullPath, ignoreList));
        } else {
            results.push(fullPath);
        }
    }
    return results;
}

const files = getFilesClean('./src', ['node_modules', 'dist']);
console.log(files);

 */