package utils;
import utils.tpl.Tuple2;

public interface Entries<K, V> {
    public Array<K> keys();
    public Array<V> values();
    public Array<Tuple2<K, V>> entries();
}
