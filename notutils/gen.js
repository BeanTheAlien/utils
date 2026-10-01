const fs = require("fs");
const alpha = "abcdefghijklmnopqrstuvwxyz".toUpperCase();
const ltrs = (ct) => {
    const out = [];
    for(let i = 0; i < ct; i++) {
        out.push(alpha[i]);
    }
    return out;
}
const gn = (i, n, subI) => `${n}${i == 1 ? "" : (subI ? i - 1 : i)}`;
const ls = (i) => i == 1 ? "st" : i == 2 ? "nd" : i == 3 ? "rd" : "th";
const template = (i, n, t, slices, subI, desc, ret) => `package utils.fn;

@FunctionalInterface
/**
 * A functional interface that provides multi-argument options.
 * <br><br>
 * ${desc}
 * ${ltrs(i).map((x, j) => `@param arg${x} The ${j}${ls(j)} parameter.`).join("\n")}
 * ${ltrs(i).map((x, j) => `@param <${x}> The type of the ${j}${ls(j)} paramter.`)}
 * ${ret != undefined ? `@return ${ret}` : ""}
 */
public interface ${gn(i, n, subI)}${i == 0 ? "" : `<${ltrs(i).join(", ")}>`} {
    ${t} run(${i == 0 ? "" : (slices ? ltrs(i-1).slice(0, i) : ltrs(i)).map((a, i) => `${a} arg${i}`).join(", ")});
}`;
const ttm = (i, n, si, desc) => `package utils.tpl;

/**
 * A tuple of ${i} elements.
 * <br><br>
 * ${desc}
 * ${ltrs(i).map((x, j) => `@param ${x.toLowerCase()} The ${j}${ls(j)} element.`).join("\n")}
 * ${ltrs(i).map((x, j) => `@param <${x}> The type of the ${j}${ls(j)} element.`)}
 */
public record ${gn(i, n, si)}<${ltrs(i).join(", ")}>(${ltrs(i).map(a => `${a} ${a.toLowerCase()}`).join(", ")}) {}`;
const bog = (i, n, t, slices, subI, desc, ret) => [gn(i, n, subI), template(i, n, t, slices, subI, desc, ret)];
const bog2 = (i, n, desc) => [gn(i, n, false), ttm(i, n, false, desc)];
const ct = 10;
for(let i = 1; i <= ct; i++) {
    const w = (k, v) => fs.writeFileSync(`../Java/src/main/java/utils/${k}.java`, v);
    let [x, y] = bog(i, "Func", alpha[i-1], true, true, `Function with ${i} arguments. Returns object of type ${alpha[i-1]}.`, `An object of type ${alpha[i-1]}.`);
    w(`fn/${x}`, y);
    [x, y] = bog(i, "BoolFunc", "boolean", false, false, `Predicate function with ${i} arguments. Returns a boolean.`, "A boolean.");
    w(`fn/${x}`, y);
    [x, y] = bog(i, "VoidFunc", "void", false, false);
    w(`fn/${x}`, y);
    [x, y] = bog2(i, "Tuple", `A tuple with ${i} elements.`);
    w(`tpl/${x}`, y);
}