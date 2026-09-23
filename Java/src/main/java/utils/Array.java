package utils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.*;
import java.util.Collections;
import java.util.stream.*;

import utils.fn.Func;
import utils.fn.Func1;
import utils.fn.Func2;
import utils.fn.VoidFunc;
import utils.fn.VoidFunc2;

/**
 * {@code Array} is a convenient wrapper around {@code ArrayList}.
 * <br><br>
 * It streamlines support between operations, providing useful UX that {@code ArrayList} lacks.
 * <br><br>
 * Most methods return a reference to {@code this} for method chaining.
 * @param <T> The type of object this array contains.
 */
public class Array<T> extends ArrayList<T> {
    /**
     * Creates an {@code Array} with content specified automatically appended.
     * @param content The content to be included within the array.
     */
    public Array(T... content) {
        super();
        this.addAll(this.__list(content));
    }
    public Array() {
        super();
    }
    public Array(Collection<T> collection) {
        super(collection);
    }
    private List<T> __list(T... x) {
        return Arrays.asList(x);
    }
    /**
     * Appends the elements as specified in {@code x} to the array.
     * @param x The elements to add.
     * @return A reference to {@code this}.
     */
    public Array<T> add(T... x) {
        this.addAll(this.__list(x));
        return this;
    }
    /**
     * Removes the elements as specified in {@code x} from the array.
     * <br><br>
     * Elements removed will be removed once, as opposed to {@code rmAll}.
     * @param x The elements to remove.
     * @return A reference to {@code this}.
     * @see #rmAll
     */
    public Array<T> rm(T... x) {
        for(T y : x) this.remove(y);
        return this;
    }
    /**
     * Removes the elements as specified in {@code x} from the array.
     * <br><br>
     * Elements removed will be removed infinitely many times, as opposed to {@code rm}.
     * @param x The elements to remove.
     * @return A reference to {@code this}.
     * @see #rm
     */
    public Array<T> rmAll(T... x) {
        this.removeAll(this.__list(x));
        return this;
    }
    /**
     * Removes an element at the specified index.
     * @param index The index to remove at.
     * @return A reference to {@code this}.
     */
    public Array<T> rmAt(int index) {
        this.remove(index);
        return this;
    }
    /**
     * Substitutes values matching {@code x} with {@code y}.
     * <br><br>
     * Elements substituted will only be substituted once, as opposed to {@code subAll}.
     * @param x The old value.
     * @param y The new value.
     * @return A reference to {@code this}.
     * @see #subAll
     */
    public Array<T> sub(T x, T y) {
        int i = this.idx(x);
        if(i != -1) this.set(y, i);
        return this;
    }
    /**
     * Substitutes values matching {@code x} with {@code y}.
     * <br><br>
     * Elements substituted will be substituted infinitely many times, as opposed to {@code sub}.
     * @param x The old value.
     * @param y The new value.
     * @return A reference to {@code this}.
     * @see #sub
     */
    public Array<T> subAll(T x, T y) {
        int i = this.idx(x);
        while(i != -1) {
            this.set(y, i);
            i = this.idx(x);
        }
        return this;
    }
    /**
     * Returns the length of the array.
     * @return The length.
     */
    public int len() {
        return this.size();
    }
    /**
     * Sets an element at a specified index.
     * @param item The item to set.
     * @param index The index to set at.
     * @return A reference to {@code this}.
     */
    public Array<T> set(T item, int index) {
        this.set(index, item);
        return this;
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
        return this.containsAll(this.__list(x));
    }
    /**
     * Clears the array.
     * @return A reference to {@code this}.
     */
    public Array<T> empty() {
        this.clear();
        return this;
    }
    // private T[] array(int size) {
    //     return (T[])new Object[size];
    // }
    /**
     * Takes a shallow slice of the array, starting from index 0.
     * @return A shallow copy.
     */
    public T[] slice() {
        return this.slice(0);
    }
    /**
     * Takes a shallow slice of the array, starting from index 0.
     * @return A shallow copy (as an array).
     */
    public Array<T> slicea() {
        return this.slicea(0);
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
     * Takes a shallow slice of the array, starting from index {@code start}.
     * @param start The starting index.
     * @return A shallow copy (as an array).
     */
    public Array<T> slicea(int start) {
        return this.slicea(start, this.len());
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
     * @param start The starting index.
     * @param end The ending index.
     * @return A shallow copy (as an array).
     */
    public Array<T> slicea(int start, int end) {
        return this.slicea(start, end, 1);
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
        return this.slicea(start, end, increment).toArray();
    }
    /**
     * Takes a shallow slice of the array, starting from index {@code start} and ending with {@code end}.
     * <br><br>
     * Captures every {@code increment} element.
     * @param start The starting index.
     * @param end The ending index.
     * @param increment The step to capture.
     * @return A shallow copy (as an array).
     * <pre>
     * // capturing every nth element
     * Array<String> arr = new Array<String>("foo", "bar", "foo", "bar");
     * // capturing every other element will be 'bar', 'bar'
     * System.out.println(arr.slicea(1, arr.len(), 2).toString()); // expected output: ["bar", "bar"]
     * </pre>
     */
    public Array<T> slicea(int start, int end, int increment) {
        Array<T> slice = new Array<T>();
        for(int i = start; i < this.len(); i += increment) slice.add(this.get(i));
        return slice;
    }
    /**
     * Removes elements, starting from index 0.
     * @return A reference to {@code this}.
     */
    public Array<T> splice() {
        return this.splice(0);
    }
    /**
     * Removes elements, starting from index {@code start}.
     * @param start The starting index.
     * @return A reference to {@code this}.
     */
    public Array<T> splice(int start) {
        return this.splice(start, this.len());
    }
    /**
     * Removes elements, starting from index {@code start} and ending with {@code end}.
     * @param start The starting index.
     * @param end The ending index.
     * @return A reference to {@code this}.
     */
    public Array<T> splice(int start, int end) {
        return this.splice(start, end, 1);
    }
    /**
     * Removes elements, starting from index {@code start} and ending with {@code end}.
     * <br><br>
     * Removes every {@code increment} element.
     * @param start The starting index.
     * @param end The ending index.
     * @param increment The step to remove.
     * @return A reference to {@code this}.
     * <pre>
     * // removing every nth element
     * Array<String> arr = new Array<String>("foo", "bar", "foo", "bar");
     * System.out.println(arr.toString());
     * arr.splice(1, arr.len(), 2); // remove 'bar', 'bar'
     * System.out.println(arr.toString()); // expected output: ["foo", "foo"]
     * </pre>
     */
    public Array<T> splice(int start, int end, int increment) {
        for(int i = start; i < end; i += increment) this.rmAt(i);
        return this;
    }
    /**
     * Removes elements, starting from index {@code start}.
     * <br><br>
     * Appends elements {@code addElements} after removal.
     * @param start The starting index.
     * @param addElements The elements to add.
     * @return A reference to {@code this}.
     */
    public Array<T> splice(int start, T... addElements) {
        return this.splice(start, this.len(), addElements);
    }
    /**
     * Removes elements, starting from index {@code start} and ending with {@code end}.
     * <br><br>
     * Appends elements {@code addElements} after removal.
     * @param start The starting index.
     * @param end The ending index.
     * @param addElements The elements to add.
     * @return A reference to {@code this}.
     */
    public Array<T> splice(int start, int end, T... addElements) {
        return this.splice(start, end, 1, addElements);
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
     * @return A reference to {@code this}.
     * <pre>
     * // removing every nth element, adding new elements
     * Array<String> arr = new Array<String>("foo", "bar", "foo", "bar");
     * System.out.println(arr.toString());
     * arr.splice(1, arr.len(), 2, "hello", "world"); // remove 'bar', 'bar'
     * System.out.println(arr.toString()); // expected output: ["foo", "foo", "hello", "world"]
     * </pre>
     */
    public Array<T> splice(int start, int end, int increment, T... addElements) {
        return this.splice(start, end, increment).add(addElements);
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
        return Utils.count(this.toArray(), item);
    }
    /**
     * Resizes this array to the length.
     * <br><br>
     * Effectively takes a splice from {@code length} onward.
     * @param length The new length.
     * @return A reference to {@code this}.
     */
    public Array<T> len(int length) {
        this.subList(length, this.len()).clear();
        return this;
    }
    /**
     * Reverses this array.
     * @return A reference to {@code this}.
     */
    public Array<T> reverse() {
        Collections.reverse(this);
        return this;
    }
    /**
     * Returns whether the predicate matches any element in the array.
     * @param predicate The predicate test.
     * @return Whether an element matched.
     */
    public boolean some(Predicate<? super T> predicate) {
        return this.stream().anyMatch(predicate);
    }
    /**
     * Returns whether the predicate matches every element in the array.
     * @param predicate The predicate test.
     * @return Whether all elements matched.
     */
    public boolean every(Predicate<? super T> predicate) {
        return this.stream().allMatch(predicate);
    }
    /**
     * Returns whether the predicate matches no elements in the array.
     * @param predicate The predicate test.
     * @return Whether no elements matched.
     */
    public boolean none(Predicate<? super T> predicate) {
        return this.stream().noneMatch(predicate);
    }
    /**
     * Tries to find an element in the array, given a predicate.
     * @param predicate The predicate test.
     * @return The element, or {@code null} if the predicate failed.
     */
    public Optional<T> find(Predicate<? super T> predicate) {
        return this.stream().filter(predicate).findFirst();
    }
    private <R> List<R> __clist(Function<String, ? extends R> func) {
        return this.stream().map(x -> func.apply(String.valueOf(x))).collect(Collectors.toList());
    }
    public List<String> toStringList() {
        return this.__clist(String::valueOf);
    }
    public List<Integer> toIntList() {
        return this.__clist(Integer::valueOf);
    }
    public List<Double> toDblList() {
        return this.__clist(Double::valueOf);
    }
    public List<Float> toFloatList() {
        return this.__clist(Float::valueOf);
    }
    public T[] toArray() {
        return (T[])super.toArray();
    }
    public Array<T> clone() {
        return (Array<T>)super.clone();
    }
    public Array<T> forEach(VoidFunc<T> callback) {
        return this.forEach((e, i) -> callback.run(e));
    }
    public Array<T> forEach(VoidFunc2<T, Integer> callback) {
        for(int i = 0; i < this.len(); i++) {
            callback.run(this.get(i), i);
        }
        return this;
    }
    public Array<T> submap(Func1<T, T> callback) {
        this.replaceAll((v) -> callback.run(v));
        return this;
    }
    public Array<T> submap(Func2<T, Integer, T> callback) {
        for(int i = 0; i < this.len(); i++) {
            this.set(i, callback.run(this.get(i), i));
        }
        return this;
    }
}