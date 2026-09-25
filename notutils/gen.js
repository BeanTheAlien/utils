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
}`;
const ttm = (i, n, si) => `package utils.tpl;

public record ${gn(i, n, si)}<${ltrs(i).join(", ")}>(${ltrs(i).map((a, i) => `${a} ${alpha[i].toLowerCase()}`).join(", ")}) {}`;
const bog = (i, n, t, slices, subI) => [gn(i, n, subI), template(i, n, t, slices, subI)];
const bog2 = (i, n) => [gn(i, n, false), ttm(i, n, false)];
const ct = 10;
for(let i = 1; i <= ct; i++) {
    const w = (k, v) => fs.writeFileSync(`../Java/src/main/java/utils/${k}.java`, v);
    let [x, y] = bog(i, "Func", alpha[i-1], true, true);
    w(`fn/${x}`, y);
    [x, y] = bog(i, "BoolFunc", "boolean", false, false);
    w(`fn/${x}`, y);
    [x, y] = bog(i, "VoidFunc", "void", false, false);
    w(`fn/${x}`, y);
    [x, y] = bog2(i, "Tuple");
    w(`tpl/${x}`, y);
}