package utils;
import java.util.HashMap;
import java.util.Set;

public class Store<I, O> {
    public HashMap<I, O> map;
    public Store() {
        this.map = new HashMap<I, O>();
    }
    public void rm(I x) {
        this.map.remove(x);
    }
    public int length() { return this.map.size(); }
    public O get(I k) {
        return this.map.get(k);
    }
    public void set(I k, O v) {
        this.map.put(k, v);
    }
    public boolean has(I k) {
        return this.map.containsKey(k);
    }
    public boolean hasVal(O v) {
        return this.map.containsValue(v);
    }
    public boolean hasPair(I k, O v) {
        return this.has(k) && this.hasVal(v);
    }
    public void empty() {
        this.map.clear();
    }
    public void length(int length) {
        Set<I> ks = this.map.keySet();
        HashMap<I, O> out = new HashMap<I, O>();
        for(int i = 0; i < length && i < ks.size(); i++) {
            I[] arr = (I[])ks.toArray();
            I e = arr[i];
            out.put(e, this.map.get(e));
        }
    }
}