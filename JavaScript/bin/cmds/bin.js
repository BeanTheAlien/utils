import * as fs from "fs";
import * as readline from "readline";

export default async function(arg) {
    const s = arg[0] ?? "cjs";
    /**
     * @type {"cjs" | "mjs"}
     */
    const style = s != "cjs" && s != "mjs" && s != "common" && s != "module" ? (() => {
        console.log(`Not a valid type '${s}' (expected 'cjs'/'common', 'mjs'/'module')`);
        return "cjs";
    })() : (s == "cjs" || s == "mjs" ? s : (s == "common" ? "cjs" : "mjs"));
    console.log("Creating bin dir...");
    fs.mkdirSync("bin");
    console.log("Creating bin/cmds dir...");
    fs.mkdirSync("bin/cmds");
    console.log("Writing bin file...");
    fs.writeFileSync("bin/bin.js", `#!/usr/bin/env node
${style == "cjs" ? `const fs = require("fs");` : `import * as fs from "fs";`}
${style == "cjs" ? `const path = require("path");` : `import * as path from "path";`}

const [,, cmd, ...args] = process.argv;
const commandsDir = path.join(__dirname, "cmds");

const commands = Object.fromEntries(
    fs.readdirSync(commandsDir)
        .map(file => [file.replace(".js", ""), () => ${style == "cjs" ? "require" : "import"}(path.join(commandsDir, file))])
);

if(commands[cmd]) {
    const module = commands[cmd]();
    module.default(args);
} else {
    console.log(\`Unknown command: \${cmd}\`);
}`
    );
    console.log("Done!");
    const rl = readline.createInterface({
        input: process.stdin,
        output: process.stdout
    });
    const ask = (q) => new Promise(res => rl.question(q, res));
    if((await ask("Add to package.json? (y/n): ")).toLowerCase() == "y") {
        const x = await ask("Enter names (seperate by ,): ");
        const pkg = JSON.parse(fs.readFileSync("package.json", "utf8"));
        pkg["bin"] = { ...pkg["bin"] };
        for(const y of x.split(",")) pkg["bin"][y.trim()] = "bin/bin.js";
        fs.writeFileSync("package.json", JSON.stringify(pkg));
    }
    rl.close();
}