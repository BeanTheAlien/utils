package utils;
import java.util.HashMap;
import java.util.Set;

/**
 * Enhances the capabilities of {@code HashMap}.
 * <br><br>
 * Provides new UX.
 */
public class Store<I, O> {
    public HashMap<I, O> map;
    /**
     * Creates a {@code Store}.
     */
    public Store() {
        this.map = new HashMap<I, O>();
    }
    /**
     * Removes a pair from the map.
     * @param x The key to remove.
     */
    public void rm(I x) {
        this.map.remove(x);
    }
    /**
     * Returns the length of the map.
     * @return The length.
     */
    public int length() { return this.map.size(); }
    /**
     * Returns the associated value with key {@code k}.
     * @param k The key.
     * @return The value.
     */
    public O get(I k) {
        return this.map.get(k);
    }
    /**
     * Sets an element in the map.
     * @param k The key to set.
     * @param v The value to set.
     */
    public void set(I k, O v) {
        this.map.put(k, v);
    }
    /**
     * Returns whether this key is contained in the map.
     * @param k The key to check.
     * @return If it is in the map.
     */
    public boolean has(I k) {
        return this.map.containsKey(k);
    }
    /**
     * Returns whether this value is contained in the map.
     * @param v The value to check.
     * @return If it is in the map.
     */
    public boolean hasVal(O v) {
        return this.map.containsValue(v);
    }
    /**
     * Returns whether this pair is contained in the map.
     * @param k The key to check.
     * @param v The value to check.
     * @return If it is in the map.
     */
    public boolean hasPair(I k, O v) {
        return this.has(k) && this.hasVal(v);
    }
    /**
     * Clears the map.
     */
    public void empty() {
        this.map.clear();
    }
    /**
     * Resizes this map to the specified length.
     * @param length The new length.
     */
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