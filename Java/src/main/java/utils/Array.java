package utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.stream.*;

/**
 * {@code Array} is a convenient wrapper around {@code ArrayList}.
 * <br><br>
 * It streamlines support between operations, providing useful UX that {@code ArrayList} lacks.
 */
public class Array<T> {
    public ArrayList<T> array;
    /**
     * Creates an {@code Array} with content specified automatically appended.
     * @param content The content to be included within the array.
     */
    public Array(T... content) {
        this();
        this.array.addAll(this.list(content));
    }
    /**
     * Creates an {@code Array}.
     */
    public Array() {
        this.array = new ArrayList<T>();
    }
    private List<T> list(T... x) {
        return Arrays.asList(x);
    }
    /**
     * Appends the elements as specified in {@code x} to the array.
     * @param x The elements to add.
     */
    public void add(T... x) {
        this.array.addAll(this.list(x));
    }
    /**
     * Removes the elements as specified in {@code x} from the array.
     * <br><br>
     * Elements removed will be removed once, as opposed to {@code rmAll}.
     * @param x The elements to remove.
     * @see #rmAll
     */
    public void rm(T... x) {
        for(T y : x) this.array.remove(y);
    }
    /**
     * Removes the elements as specified in {@code x} from the array.
     * <br><br>
     * Elements removed will be removed infinitely many times, as opposed to {@code rm}.
     * @param x The elements to remove.
     * @see #rm
     */
    public void rmAll(T... x) {
        this.array.removeAll(this.list(x));
    }
    /**
     * Removes an element at the specified index.
     * @param index The index to remove at.
     */
    public void rmAt(int index) {
        this.array.remove(index);
    }
    /**
     * Substitutes values matching {@code x} with {@code y}.
     * <br><br>
     * Elements substituted will only be substituted once, as opposed to {@code subAll}.
     * @param x The old value.
     * @param y The new value.
     * @see #subAll
     */
    public void sub(T x, T y) {
        int i = this.idx(x);
        if(i != -1) this.set(y, i);
    }
    /**
     * Substitutes values matching {@code x} with {@code y}.
     * <br><br>
     * Elements substituted will be substituted infinitely many times, as opposed to {@code sub}.
     * @param x The old value.
     * @param y The new value.
     * @see #sub
     */
    public void subAll(T x, T y) {
        int i = this.idx(x);
        while(i != -1) {
            this.set(y, i);
            i = this.idx(x);
        }
    }
    /**
     * Returns the length of the array.
     * @return The length.
     */
    public int len() {
        return this.array.size();
    }
    /**
     * Returns an element at the specified index.
     * @param index The index to get.
     * @return An element.
     */
    public T get(int index) {
        return this.array.get(index);
    }
    /**
     * Sets an element at a specified index.
     * @param item The item to set.
     * @param index The index to set at.
     */
    public void set(T item, int index) {
        this.array.set(index, item);
    }
    /**
     * Returns the index of an item.
     * @param item The item to find.
     * @return The index (or -1, if it isn't present).
     */
    public int idx(T item) {
        return this.idx(item, 0, this.len(), 1);
    }
    /**
     * Returns the index of an item.
     * @param item The item to find.
     * @param start The starting index (inclusive).
     * @return The index (or -1, if it isn't present).
     */
    public int idx(T item, int start) {
        return idx(item, start, this.len(), 1);
    }
    /**
     * Returns the index of an item.
     * @param item The item to find.
     * @param start The starting index (inclusive).
     * @param end The ending index (exclusive).
     * @return The index (or -1, if it isn't present).
     */
    public int idx(T item, int start, int end) {
        return idx(item, start, end, 1);
    }
    /**
     * Returns the index of an item.
     * @param item The item to find.
     * @param start The starting index (inclusive).
     * @param end The ending index (exclusive).
     * @param occur The occurence to find.
     * @return The index (or -1, if it isn't present).
     * <pre>
     * // finding the nth occurence of an element
     * Array<String> arr = new Array<String>("foo", "bar", "foo");
     * System.out.println(arr.idx("foo", 0, arr.len(), 2)); // expected output: 2
     * </pre>
     */
    public int idx(T item, int start, int end, int occur) {
        int n = 0;
        for(int i = start; i < end; i++) if(this.get(i).equals(item)) if(n == occur) return i; else n++;
        return -1;
    }
    /**
     * Returns whether this array contains all the elements specified in {@code x}.
     * @param x The elements to test.
     * @return Whether they are all contained.
     */
    public boolean has(T... x) {
        return this.array.containsAll(this.list(x));
    }
    /**
     * Clears the array.
     */
    public void empty() {
        this.array.clear();
    }
    private T[] array(int size) {
        return (T[])new Object[size];
    }
    /**
     * Takes a shallow slice of the array, starting from index 0.
     * @return A shallow copy.
     */
    public T[] slice() {
        return this.slice(0);
    }
    /**
     * Takes a shallow slice of the array, starting from index {@code start}.
     * @param start The starting index.
     * @return A shallow copy.
     */
    public T[] slice(int start) {
        return this.slice(start, this.len());
    }
    /**
     * Takes a shallow slice of the array, starting from index {@code start} and ending with {@code end}.
     * @param start The starting index.
     * @param end The ending index.
     * @return A shallow copy.
     */
    public T[] slice(int start, int end) {
        return this.slice(start, end, 1);
    }
    /**
     * Takes a shallow slice of the array, starting from index {@code start} and ending with {@code end}.
     * <br><br>
     * Captures every {@code increment} element.
     * @param start The starting index.
     * @param end The ending index.
     * @param increment The step to capture.
     * @return A shallow copy.
     * <pre>
     * // capturing every nth element
     * Array<String> arr = new Array<String>("foo", "bar", "foo", "bar");
     * // capturing every other element will be 'bar', 'bar'
     * System.out.println(java.util.Arrays.toString(arr.slice(1, arr.len(), 2))); // expected output: ["bar", "bar"]
     * </pre>
     */
    public T[] slice(int start, int end, int increment) {
        ArrayList<T> slice = new ArrayList<T>();
        for(int i = start; i < this.len(); i += increment) slice.add(this.get(i));
        T[] result = this.array(slice.size());
        for(int i = 0; i < slice.size(); i++) result[i] = slice.get(i);
        return result;
    }
    /**
     * Removes elements, starting from index 0.
     */
    public void splice() {
        this.splice(0);
    }
    /**
     * Removes elements, starting from index {@code start}.
     * @param start The starting index.
     */
    public void splice(int start) {
        this.splice(start, this.len());
    }
    /**
     * Removes elements, starting from index {@code start} and ending with {@code end}.
     * @param start The starting index.
     * @param end The ending index.
     */
    public void splice(int start, int end) {
        this.splice(start, end, 1);
    }
    /**
     * Removes elements, starting from index {@code start} and ending with {@code end}.
     * <br><br>
     * Removes every {@code increment} element.
     * @param start The starting index.
     * @param end The ending index.
     * @param increment The step to remove.
     * <pre>
     * // removing every nth element
     * Array<String> arr = new Array<String>("foo", "bar", "foo", "bar");
     * System.out.println(arr.toString());
     * arr.splice(1, arr.len(), 2); // remove 'bar', 'bar'
     * System.out.println(arr.toString()); // expected output: ["foo", "foo"]
     * </pre>
     */
    public void splice(int start, int end, int increment) {
        for(int i = start; i < end; i += increment) this.rmAt(i);
    }
    /**
     * Removes elements, starting from index {@code start}.
     * <br><br>
     * Appends elements {@code addElements} after removal.
     * @param start The starting index.
     * @param addElements The elements to add.
     */
    public void splice(int start, T... addElements) {
        this.splice(start, this.len(), addElements);
    }
    /**
     * Removes elements, starting from index {@code start} and ending with {@code end}.
     * <br><br>
     * Appends elements {@code addElements} after removal.
     * @param start The starting index.
     * @param end The ending index.
     * @param addElements The elements to add.
     */
    public void splice(int start, int end, T... addElements) {
        this.splice(start, end, 1, addElements);
    }
    /**
     * Removes elements, starting from index {@code start} and ending with {@code end}.
     * <br><br>
     * Removes every {@code increment} element.
     * <br><br>
     * Appends elements {@code addElements} after removal.
     * @param start The starting index.
     * @param end The ending index.
     * @param increment The step to remove.
     * @param addElements The elements to add.
     * <pre>
     * // removing every nth element, adding new elements
     * Array<String> arr = new Array<String>("foo", "bar", "foo", "bar");
     * System.out.println(arr.toString());
     * arr.splice(1, arr.len(), 2, "hello", "world"); // remove 'bar', 'bar'
     * System.out.println(arr.toString()); // expected output: ["foo", "foo", "hello", "world"]
     * </pre>
     */
    public void splice(int start, int end, int increment, T... addElements) {
        this.splice(start, end, increment);
        this.add(addElements);
    }
    /**
     * Returns this array as a string, given a delimiter.
     * @param delim The delimiter.
     * @return This array, joined by the delimiter.
     */
    public String join(Object delim) {
        String string = "";
        for(int i = 0; i < this.len(); i++) {
            if(i < this.len() - 1) {
                string += Utils.str(this.get(i)) + Utils.str(delim);
            } else {
                string += Utils.str(this.get(i));
            }
        }
        return string;
    }
    /**
     * Counts the times an item, {@code item}, shows up.
     * @param item The item to count.
     * @return The times it shows up.
     */
    public int count(T item) {
        return Utils.count(this.array.toArray(), item);
    }
    /**
     * Resizes this array to the length.
     * <br><br>
     * Effectively takes a splice from {@code length} onward.
     * @param length The new length.
     */
    public void len(int length) {
        this.array.subList(length, this.len()).clear();
    }
    /**
     * Returns this array as an array.
     * @return This, as an array.
     */
    public T[] toArray() {
        return (T[])this.array.toArray();
    }
    public void sort() {
        this.array.sort();
    }
    public void sortr() {
        Collections.reverse(this.array);
    }
    public Stream<T> stream() {
        return this.array.stream();
    }
}