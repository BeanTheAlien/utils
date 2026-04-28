package utils;
import java.util.HashMap;

public class object {
    public HashMap<Object, Object> object;
    public object(Object... content) {
        this.object = new HashMap<>();
        for(int i = 0; i < content.length; i++) this.object.put(content[i], content[i + 1]);
    }
    public object() { this.object = new HashMap<>(); }
    public void add(Object k, Object v) {
        this.object.putIfAbsent(k, v);
    }
    public void adds(Object... x) {
        for(int i = 0; i < x.length; i++) this.object.putIfAbsent(x[i], x[i + 1]);
    }
    public void remove(Object x) {
        this.object.remove(x);
    }
    public void removes(Object... x) {
        for(Object y : x) this.object.remove(y);
    }
    public int length() { return this.object.size(); }
    public Object get(Object k) { return this.object.get(k); }
    public Object[] gets(Object... ks) {
        Object[] g = new Object[ks.length];
        int i = 0;
        for(Object k : object.keySet()) {
            if(i >= ks.length) break;
            g[i] = k;
            i++;
        }
        return g;
    }
    public void set(Object k, Object v) { this.object.put(k, v); }
    public boolean hasKey(Object k) { return this.object.containsKey(k); }
    public boolean hasValue(Object v) { return this.object.containsValue(v); }
    public boolean hasPair(Object k, Object v) { return this.object.containsKey(k) && this.object.containsValue(v); }
    public void empty() { this.object = new HashMap<>(); }
    public void length(int length) {
        Object[] keys = new Object[length];
        Object[] values = new Object[length];
        int i = 0;
        for(Object x : this.object.keySet()) {
            if(i >= length) break;
            keys[i] = x;
            values[i] = this.object.get(x);
            i++;
        }
        this.object = new HashMap<>();
        for(int r = 0; r < length; r++) {
            this.object.put(keys[r], values[r]);
        }
    }
}