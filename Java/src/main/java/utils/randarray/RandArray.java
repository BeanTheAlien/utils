package utils.randarray;
import java.util.Arrays;
import java.util.Collections;
import utils.Random;

/**
 * This is a completley <strong>unusable data structure</strong>.
 * <br><br>
 * Want to do anything?
 * <br><br>
 * How about we <strong>r n g</strong> that for you.
 * <br><br>
 * Add a random factor to your array! It's grotesque!
 * @param <T> The type of element. Not that it matters.
 */
public class RandArray<T> {
    public static void main(String[] args) {
        var r = new RandArray<String>(10);
        r.set("Hello");
        r.set("World");
        r.set("HHIIIDHJF");
        System.out.println(r.length());
        System.out.println(r);
    }
    T[] arr;
    private T[] __arr(int size) {
        return (T[])(new Object[size]);
    }
    public RandArray(int size) {
        this.arr = this.__arr(size);
    }
    /**
     * Retrieve an element.
     * <br><br>
     * Not at a specified index, that's boring.
     * @return A completley psuedo-random element.
     */
    public T get() {
        return Random.item(this.arr);
    }
    /**
     * Set an element.
     * <br><br>
     * Again, not at a specified index. How about a random one instead?
     * @param item The element to be tossed in.
     */
    public void set(T item) {
        this.arr[Random.index(this.arr)] = item;
    }
    /**
     * Ever wonder what the chance of suspicious things are?
     * <br><br>
     * Well it's this. I don't know why it's called JPMorgan.
     */
    public static final int JPMorgan = 5;
    private boolean __jpmorgan() {
        return Random.chance(RandArray.JPMorgan);
    }
    private int __big() {
        return Random.random(0, Integer.MAX_VALUE);
    }
    /**
     * Set the length of the array.
     * <br><br>
     * ...Unless the length you wanted gets lost. I don't know why it would.
     * @param len The length you want. (or another random number)
     */
    public void length(int len) {
        len = this.__jpmorgan() ? this.__big() : len;
        var x = this.__arr(len);
        for(int i = 0; i < len; i++) {
            x[i] = this.arr[i];
        }
        this.arr = x;
    }
    /**
     * Get the length of the array.
     * <br><br>
     * Unless this is another random number. Who knows.
     * @return The length. (or maybe a random number)
     */
    public int length() {
        if(Random.chance(5)) return this.__big();
        return this.arr.length;
    }
    public String toString() {
        var s = new StringBuilder("[");
        for(int i = 0; i < this.length(); i++) {
            s.append(this.arr[i]).append(i < this.length() - 1 ? ", " : "");
        }
        return s.append("]").toString();
    }
    /**
     * Pray that your array will be sorted.
     * <br><br>
     * Because there's nothing better than completley random shuffling.
     * @param prayers The amount of times to pray. May be corrupted and turned into a random number.
     */
    public void sort(int prayers) {
        prayers = this.__jpmorgan() ? this.__big() : prayers;
        for(int i = 0; i < prayers; i++) {
            Collections.shuffle(Arrays.asList(this.arr));
        }
    }
    /**
     * Remove an element.
     * <br><br>
     * Maybe it removes something you want to remove, maybe not.
     * @return The removed element. Or not. I dunno.
     */
    public T remove() {
        int j = this.__jpmorgan() ? this.__big() : Random.index(this.arr);
        var k = this.get();
        var x = this.__arr(this.arr.length - 1);
        for(int i = 0; i < x.length; i++) {
            if(i != j) x[i] = this.get();
        }
        this.arr = x;
        return k;
    }
}