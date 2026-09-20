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
const template = (i, n, t, slices, subI) => `package utils.fn;

@FunctionalInterface
public interface ${gn(i, n, subI)}${i == 0 ? "" : `<${ltrs(i).join(", ")}>`} {
    ${t} run(${i == 0 ? "" : (slices ? ltrs(i-1).slice(0, i) : ltrs(i)).map((a, i) => `${a} arg${i}`).join(", ")});
}`
const bog = (i, n, t, slices, subI) => [gn(i, n, subI), template(i, n, t, slices, subI)];
const ct = 10;
for(let i = 1; i <= ct; i++) {
    let [x, y] = bog(i, "Func", alpha[i-1], true, true);
    fs.writeFileSync(`../Java/src/main/java/utils/fn/${x}.java`, y);
    [x, y] = bog(i, "BoolFunc", "boolean", false, false);
    fs.writeFileSync(`../Java/src/main/java/utils/fn/${x}.java`, y);
    [x, y] = bog(i, "VoidFunc", "void", false, false);
    fs.writeFileSync(`../Java/src/main/java/utils/fn/${x}.java`, y);
    [x, y] = bog(i, "TFunc", alpha[i-1], true, false);
    fs.writeFileSync(`../Java/src/main/java/utils/fn/${x}.java`, y);
}