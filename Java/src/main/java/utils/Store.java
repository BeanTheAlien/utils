package utils;
import java.util.HashMap;
import java.util.Set;

import utils.tpl.Tuple2;

/**
 * Enhances the capabilities of {@code HashMap}.
 * <br><br>
 * Provides new UX.
 */
public class Store<K, V> extends HashMap<K, V> implements Entries<K, V> {
    /**
     * Creates a {@code Store}.
     */
    public Store() {
        super();
    }
    /**
     * Removes a pair from the map.
     * @param key The key to remove.
     */
    public void rm(K key) {
        this.remove(key);
    }
    /**
     * Returns the length of the map.
     * @return The length.
     */
    public int len() { return this.size(); }
    public V gets(K key) {
        return this.get(key);
    }
    /**
     * Sets an element in the map.
     * @param key The key to set.
     * @param value The value to set.
     */
    public void set(K key, V value) {
        this.put(key, value);
    }
    /**
     * Returns whether this key is contained in the map.
     * @param key The key to check.
     * @return If it is in the map.
     */
    public boolean has(K key) {
        return this.containsKey(key);
    }
    /**
     * Returns whether this value is contained in the map.
     * @param value The value to check.
     * @return If it is in the map.
     */
    public boolean hasVal(V value) {
        return this.containsValue(value);
    }
    /**
     * Returns whether this pair is contained in the map.
     * @param key The key to check.
     * @param value The value to check.
     * @return If it is in the map.
     */
    public boolean hasPair(K key, V value) {
        return this.has(key) && this.hasVal(value);
    }
    /**
     * Clears the map.
     */
    public void empty() {
        this.clear();
    }
    // /**
    //  * Resizes this map to the specified length.
    //  * @param length The new length.
    //  */
    // public void length(int length) {
    //     Set<I> ks = this.map.keySet();
    //     HashMap<I, O> out = new HashMap<I, O>();
    //     for(int i = 0; i < length && i < ks.size(); i++) {
    //         I[] arr = (I[])ks.toArray();
    //         I e = arr[i];
    //         out.put(e, this.map.get(e));
    //     }
    //     this.map = out;
    // }
    public Array<K> keys() {
        return new Array<>(this.keySet());
    }
    public Array<V> values() {
        return new Array<>(super.values());
    }
    public Array<Tuple2<K, V>> entries() {
        var out = new Array<Tuple2<K, V>>();
        for(int i = 0; i < this.len(); i++) out.add(new Tuple2<>(this.keys().get(i), this.gets(this.keys().get(i))));
        return out;
    }
}