package utils.array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import utils.fn.BoolFunc;
import utils.fn.Func2;

/**
 * {@code BArray} is a custom {@code List} implementation.
 * <br><br>
 * It has custom methods like {@code removes} to leave {@code null} references.
 * <br><br>
 * The array automatically grows to store new elements.
 * <br><br>
 * This implementation mimics how a JavaScript array would function.
 * @param <T> The type of element to store.
 * @apiNote
 * This implementation was designed to follow the behavior of the JavaScript array.
 * <br><br>
 * It is important to note that this doesn't translate 1:1 from the source JS array.
 * <br><br>
 * That being said, you can find more information on <a href="https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array">MDN Web Docs</a>.
 */
public class BArray<T> implements List<T> {
    public static void main(String[] args) {
        BArray<String> x = new BArray<>();
        x.add("Hello");
        x.add("World");
        x.printRaw();
        x.removes(1);
        x.printRaw();
        x.sets("HIIII", 4);
        x.printRaw();
        x.removesIndexes(List.of(1, 2, 3, 4, 5, 6, 7));
        x.printRaw();
    }
    public static final int size = 10;
    T[] arr;
    /**
     * Constructs the array.
     * <br><br>
     * Has an initial capacity of {@code size} (10).
     */
    public BArray() {
        this(size);
    }
    /**
     * Constructs the array with an inital capacity.
     * <br><br>
     * Has an initial capacity of {@code capacity}.
     * @param capacity The initial capacity to start with.
     */
    public BArray(int capacity) {
        this.arr = this.__arr(capacity);
    }
    /**
     * Constructs the array with elements.
     * <br><br>
     * Has an initial capacity of {@code size} (10),
     * or expands to fit {@code c}.
     * @param c The items to add.
     */
    public BArray(Collection<? extends T> c) {
        this();
        this.addAll(c);
    }
    private T[] __arr(int s) {
        return (T[])(new Object[s]);
    }
    private void __rs() {
        var a = this.__arr(this.length() + size);
        for(int i = 0; i < this.length(); i++) {
            a[i] = this.get(i);
        }
        this.arr = a;
    }
    private void __shr() {
        var l = this.stream().filter(x -> x != null).toList();
        var a = this.__arr(l.size());
        for(int i = 0; i < a.length; i++) {
            a[i] = this.get(i);
        }
        this.arr = a;
    }
    private int __spaceidx() {
        for(int i = 0; i < this.length(); i++) {
            if(this.get(i) == null) return i;
        }
        return -1;
    }
    private boolean __space() {
        return this.__spaceidx() != -1;
    }
    private boolean __isok(int i) {
        return i >= 0 && i < this.length();
    }
    private void __shift(int ind) {
        var a = this.__arr(this.length() + size);
        for(int i = 0; i < ind; i++) {
            a[i] = this.get(i);
        }
        for(int i = ind + 1; i < this.length(); i++) {
            a[i] = this.get(i-1);
        }
        this.arr = a;
    }
    private void __cull() {
        var a = this.__arr(this.length());
        for(int i = 0; i < this.length(); i++) {
            if(this.get(i) != null) a[i] = this.get(i);
        }
        this.arr = a;
    }
    /**
     * Returns the length of this array.
     * @return The array length.
     */
    public int length() {
        return this.arr.length;
    }
    /**
     * @see java.util.List#size()
     * @return The array length.
     */
    public int size() {
        return this.length();
    }
    /**
     * Returns the position of the element at the given position.
     * <br><br>
     * Supports negative indexing.
     * <br><br>
     * Negative indexing example
     * <pre>
     * public static void main(String[] args) {
     *      BArray<String> array = new BArray<>();
     *      array.addAll(List.of("Hello", "World"));
     *      System.out.println(array.get(-1)); // "World"
     * }
     * </pre>
     * @return The element at the provided index (or {@code null}, if the index is non-negative and exceeds the length).
     * @see java.util.List#get(int)
     */
    public T get(int index) {
        if(!this.__isok(index)) return index < 0 ? this.arr[this.length() + index] : null;
        return this.arr[index];
    }
    /**
     * Replaces the element at the specified position in this list with the specified element (optional operation).
     * @param element The element to replace at the index.
     * @param index The index to replace at.
     * @return The element that was previously there (or {@code null}, if there wasn't one).
     * @throws ArrayIndexOutOfBoundsException If the index does not conform to {@code i >= 0 && i < this.length()}.
     */
    public T set(T element, int index) {
        if(!this.__isok(index)) throw new ArrayIndexOutOfBoundsException(index);
        T e = this.get(index);
        this.sets(element, index);
        return e;
    }
    public T set(int index, T element) {
        return this.set(element, index);
    }
    /**
     * Sets an element at an index, ignoring bounds restrictions.
     * <br><br>
     * If the index is out of bounds, resizes the array.
     * <br><br>
     * Fills empty locations with {@code null}.
     * @param element The element to set.
     * @param index The index to set at.
     */
    public void sets(T element, int index) {
        while(!this.__isok(index)) this.__rs();
        this.arr[index] = element;
    }
    public boolean add(T element) {
        int i = this.__spaceidx();
        this.sets(element, this.__isok(i) ? i : this.length());
        return true;
    }
    public void add(int index, T element) {
        this.__shift(index);
        this.set(index, element);
    }
    /**
     * Inserts the specified element at the specified position in this list
     * (optional operation).  Shifts the element currently at that position
     * (if any) and any subsequent elements to the right (adds one to their
     * indices).
     *
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     */
    public void add(T element, int index) {
        this.add(index, element);
    }
    public boolean remove(Object element) {
        int index = this.indexOf(element);
        if(index == -1) return false;
        this.remove(index);
        return true;
    }
    public T remove(int index) {
        T e = this.get(index);
        this.removes(index);
        this.__cull();
        return e;
    }
    /**
     * Removes an index from the array.
     * <br><br>
     * Leaves a null reference where the element (if there was one) was.
     * @param element The element to remove.
     */
    public void removes(T element) {
        this.removes(this.indexOf(element));
    }
    /**
     * Removes an index from the array.
     * <br><br>
     * Leaves a null reference where the element (if there was one) was.
     * @param index The index to remove.
     * @throws ArrayIndexOutOfBoundsException If the index to remove is out-of-bounds.
     */
    public void removes(int index) {
        this.set(null, index);
    }
    public int indexOf(Object element) {
        return this.indexOf(element, 0);
    }
    /**
     * Returns the index of the element provided, given an index to start from.
     * @param element The element to search for.
     * @param fromIndex The index to start searching from.
     * @return The index, or -1 if no such element exists.
     */
    public int indexOf(Object element, int fromIndex) {
        for(int i = fromIndex; i < this.length(); i++) if(this.get(i).equals(element)) return i;
        return -1;
    }
    /**
     * Returns an array of all indexes {@code element} appears at.
     * @param element The element to search for.
     * @return The indexes it shows up at. (empty if no such element exists)
     */
    public Integer[] indexesOf(Object element) {
        return this.indexesOf(element, 0);
    }
    /**
     * Returns an array of all indexes {@code element} appears at, given an index to start from.
     * @param element The element to search for.
     * @param fromIndex The index to start searching from.
     * @return The indexes it shows up at. (empty if no such element exists)
     */
    public Integer[] indexesOf(Object element, int fromIndex) {
        var a = new BArray<Integer>();
        for(int i = fromIndex; i < this.length(); i++) {
            if(this.get(i).equals(element)) a.add(i);
        }
        return (Integer[])a.toArray();
    }
    public void clear() {
        this.arr = this.__arr(size);
    }
    public boolean contains(Object element) {
        return this.indexOf(element) != -1;
    }
    public boolean isEmpty() {
        return this.length() == 0;
    }
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int i = 0;
            public boolean hasNext() {
                return BArray.this.__isok(this.i);
            }
            public T next() {
                T e = BArray.this.get(this.i);
                this.i++;
                return e;
            }
        };
    }
    public Object[] toArray() {
        return this.arr;
    }
    public <K> K[] toArray(K[] a) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toArray'");
    }
    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(this::contains);
    }
    public boolean addAll(Collection<? extends T> c) {
        c.forEach(this::add);
        return true;
    }
    public boolean addAll(int index, Collection<? extends T> c) {
        c.forEach(x -> this.add(index, x));
        return true;
    }
    public boolean removeAll(Collection<?> c) {
        c.forEach(this::remove);
        return true;
    }
    /**
     * Deletes all indexes provided.
     * <br><br>
     * Runs {@code remove} operation.
     * @param indexes The indexes to remove.
     * @see #remove(int)
     */
    public void removeIndexes(Collection<Integer> indexes) {
        indexes.forEach(this::remove);
    }
    /**
     * Deletes all indexes provided.
     * <br><br>
     * Runs {@code removes} operation.
     * @param indexes The indexes to remove.
     * @see #removes(int)
     */
    public void removesIndexes(Collection<Integer> indexes) {
        indexes.forEach(this::removes);
    }
    public boolean retainAll(Collection<?> c) {
        c.forEach(x -> {
            if(!this.contains(x)) this.removes((T)x);
        });
        this.__cull();
        return true;
    }
    public int lastIndexOf(Object o) {
        return this.lastIndexOf(o, 0);
    }
    public int lastIndexOf(Object o, int fromIndex) {
        int i = -1;
        for(int j = fromIndex; j < this.length(); j++) {
            if(this.get(j).equals(o)) i = j;
        }
        return i;
    }
    public ListIterator<T> listIterator() {
        return this.listIterator(0);
    }
    public ListIterator<T> listIterator(int index) {
        return new ListIterator<>() {
            private int i = index;
            public boolean hasNext() {
                return BArray.this.__isok(this.i+1);
            }
            public T next() {
                return BArray.this.get(++this.i);
            }
            public boolean hasPrevious() {
                return BArray.this.__isok(this.i-1);
            }
            public T previous() {
                return BArray.this.get(--this.i);
            }
            public int nextIndex() {
                return this.i + 1;
            }
            public int previousIndex() {
                return this.i - 1;
            }
            public void remove() {
                BArray.this.remove(this.i);
            }
            public void set(T e) {
                BArray.this.set(e, this.i);
            }
            public void add(T e) {
                BArray.this.add(e, this.i);
            }
        };
    }
    public List<T> subList(int fromIndex, int toIndex) {
        T[] a = this.__arr(toIndex - fromIndex);
        for(int i = fromIndex; i < toIndex; i++) {
            a[i] = this.get(i);
        }
        return List.of(a);
    }
    public String toString() {
        return this.join(", ");
    }
    /**
     * Returns a raw join of the array.
     * @return The raw representation.
     * @see #rawJoin(String)
     */
    public String toRawString() {
        return this.rawJoin(", ");
    }
    private String __join(Func2<BArray<T>, Integer, Void> func, String delim) {
        var self = this.clone();
        StringBuilder s = new StringBuilder("[");
        for(int i = 0; i < self.length(); i++) {
            func.run(self, i);
            s.append(self.get(i)).append(i < self.length() - 1 ? delim : "");
        }
        return s.append("]").toString();
    }
    /**
     * Returns this as a string, joined by a given delimiter.
     * @param delim The delimiter to use.
     * @return This, as a string, joined by the delimiter.
     */
    public String join(String delim) {
        return this.__join((self, i) -> {
            if(i == 0) self.__shr();
            return null;
        }, delim);
    }
    /**
     * Returns this as a string, joined by a given delimiter.
     * <br><br>
     * Includes null elements in final {@code String}, instead of cleaning them out.
     * @param delim The delimiter to use.
     * @return This, as a string, joined by the delimiter.
     */
    public String rawJoin(String delim) {
        return this.__join((self, i) -> null, delim);
    }
    public BArray<T> clone() {
        var x = new BArray<T>(this.length());
        x.apply(this.arr);
        return x;
    }
    /**
     * Returns all the elements that are not {@code null}.
     * @return A list of non-null elements.
     */
    public List<T> nonNull() {
        return this.stream().filter(x -> x != null).toList();
    }
    /**
     * Counts the amount of times {@code element} shows up.
     * @param element The element to count.
     * @return The amount of times {@code element} shows up in the array.
     */
    public int count(T element) {
        return this.count(element, 0);
    }
    /**
     * Counts the amount of times {@code element} shows up.
     * @param element The element to count.
     * @param startIndex The index to start counting from. (inclusive)
     * @return The amount of times {@code element} shows up in the array.
     */
    public int count(T element, int startIndex) {
        return this.count(element, startIndex, this.length());
    }
    /**
     * Counts the amount of times {@code element} shows up.
     * @param element The element to count.
     * @param startIndex The index to start counting from. (inclusive)
     * @param endIndex The index to end counting at. (exclusive)
     * @return The amount of times {@code element} shows up in the array.
     */
    public int count(T element, int startIndex, int endIndex) {
        int i = 0;
        int j = this.indexOf(element, startIndex);
        while(j != -1 && j < endIndex) {
            i++;
            j = this.indexOf(element, j);
        }
        return i;
    }
    /**
     * Fills {@code null} elements with {@code element}.
     * @param element The element to fill.
     */
    public void fill(T element) {
        this.fill(element, 0);
    }
    /**
     * Fills {@code null} elements with {@code element}, given a start index.
     * @param element The element to fill.
     * @param startIndex The index to start filling from. (inclusive)
     */
    public void fill(T element, int startIndex) {
        this.fill(element, startIndex, this.length());
    }
    /**
     * Fills {@code null} elements with {@code element}, given a start and end index.
     * @param element The element to fill.
     * @param startIndex The index to start filling from. (inclusive)
     * @param endIndex The index to end filling at. (exclusive)
     */
    public void fill(T element, int startIndex, int endIndex) {
        for(int i = startIndex; i < endIndex; i++) {
            this.setIfNull(element, i);
        }
    }
    /**
     * Sets an element at the index if the predicate is true.
     * <br><br>
     * Runs a safe-set operation (sets an element at an index potentially out-of-bounds).
     * @param element The element to set.
     * @param index The index to set at.
     * @param predicate The predicate to test.
     * @see #sets(T, int)
     */
    public void setIf(T element, int index, BoolFunc<T> predicate) {
        if(predicate.run(this.get(index))) this.sets(element, index);
    }
    /**
     * Sets an element at the index if the predicate is true.
     * <br><br>
     * Runs a safe-set operation (sets an element at an index potentially out-of-bounds).
     * @param element The element to set.
     * @param index The index to set at.
     * @param predicate The predicate to test.
     * @see #sets(T, int)
     */
    public void setIf(int index, T element, BoolFunc<T> predicate) {
        this.setIf(element, index, predicate);
    }
    /**
     * Sets an element at the index if the element at the index is null.
     * <br><br>
     * Runs a safe-set operation (sets an element at an index potentially out-of-bounds).
     * @param element The element to set.
     * @param index The index to set at.
     * @see #sets(T, int)
     */
    public void setIfNull(T element, int index) {
        this.setIf(element, index, x -> x == null);
    }
    /**
     * Sets an element at the index if the element at the index is null.
     * <br><br>
     * Runs a safe-set operation (sets an element at an index potentially out-of-bounds).
     * @param element The element to set.
     * @param index The index to set at.
     * @see #sets(T, int)
     */
    public void setIfNull(int index, T element) {
        this.setIfNull(element, index);
    }
    /**
     * Applies all values from an array to this.
     * <br><br>
     * Auto-clones the array to prevent references from carrying over.
     * @param array The array to apply to {@code this}.
     */
    public void apply(T[] array) {
        this.arr = (T[])array.clone();
    }
    /**
     * Removes a range from the array.
     * <br><br>
     * Runs a {@code removes} operation.
     * @param start The starting index. (inclusive)
     * @param end The ending index. (exclusive)
     * @see #removes(int)
     */
    public void removeRange(int start, int end) {
        for(int i = start; i < end; i++) {
            this.removes(i);
        }
    }
    /**
     * Takes a slice of this array.
     * @return A slice of this array.
     */
    public BArray<T> slice() {
        return this.slice(0);
    }
    /**
     * Takes a slice of this array, from {@code fromIndex}.
     * @param fromIndex The index to start at. (inclusive)
     * @return A slice of this array.
     */
    public BArray<T> slice(int fromIndex) {
        return this.slice(fromIndex, this.length());
    }
    /**
     * Takes a slice of this array, from {@code fromIndex} to {@code endIndex}.
     * @param fromIndex The index to start at. (inclusive)
     * @param endIndex The index to end at. (exclusive)
     * @return A slice of this array.
     */
    public BArray<T> slice(int fromIndex, int endIndex) {
        return new BArray<>(this.subList(fromIndex, endIndex));
    }
    /**
     * Splices this array, from {@code fromIndex}.
     * <br><br>
     * Runs a {@code removes} operation.
     * @param fromIndex The index to start splicing from. (inclusive)
     * @return The removed elements.
     * @see #removes(int)
     */
    public BArray<T> splice(int fromIndex) {
        return this.splice(fromIndex, this.length() - fromIndex);
    }
    /**
     * Splices this array, from {@code fromIndex}.
     * <br><br>
     * Runs a {@code removes} operation.
     * @param fromIndex The index to start splicing from. (inclusive)
     * @param deleteCount How many elements to delete.
     * @param addElements Optional elements to be inserted at {@code fromIndex}.
     * @return The removed elements.
     * @see #removes(int)
     */
    public BArray<T> splice(int fromIndex, int deleteCount, T... addElements) {
        var slice = this.slice(fromIndex, fromIndex + deleteCount);
        for(int i = fromIndex; i < fromIndex + deleteCount; i++) this.removes(i);
        this.addAll(fromIndex, List.of(addElements));
        return slice;
    }
    /**
     * Cleans up this array.
     * <br><br>
     * Deletes {@code null} elements and shifts elements to fill the space.
     * <br><br>
     * Automatically shrinks the array to de-size for now-deleted {@code null} elements.
     */
    public void clean() {
        this.__shr();
    }
    public void print() {
        System.out.println(this);
    }
    public void printRaw() {
        System.out.println(this.toRawString());
    }
}