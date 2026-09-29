package utils.array;
import java.util.List;

public class CArray<T> implements List<T> {
    public static final size = 10;
    T[] arr;
    private T[] __arr(int s) {
        T[] a = new T[s];
    }
    private void __rs() {
        var a = this.__arr(this.length() + size);
        for(int i = 0; i < this.length(); i++) {
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
        return i > 0 && i < this.length();
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
    public int length() {
        return this.arr.length;
    }
    public int size() {
        return this.length();
    }
    public T get(int index) {
        return this.arr[index];
    }
    public void set(T element, int index) {
        if(!this.__isok(index)) throw new IndexOutOfBoundsException("Index of " + index + " exceeds length of " + this.length());
        this.sets(element, index);
    }
    public void set(int index, T element) {
        this.set(element, index);
    }
    /**
     * Sets an element at an index, ignoring bounds restrictions.
     * 
     * If the index is out of bounds, resizes the array.
     */
    public void sets(T element, int index) {
        while(!this.__isok(index)) this.__rs();
        this.arr[index] = element;
    }
    public void add(T element) {
        this.sets(element, this.length());
    }
    public void add(int index, T element) {
        this.__shift();
        this.set(index, element);
    }
    public void remove(T element) {
        this.remove(this.indexOf(element));
    }
    public void remove(int index) {
        this.removes(index);
        this.__cull();
    }
    public void removes(T element) {
        this.removes(this.indexOf(element));
    }
    /**
     * Removes an index from the array.
     * 
     * Leaves a null reference where the element (if there was one) was.
     */
    public void removes(int index) {
        this.set(null, index);
    }
    public int indexOf(T element) {
        for(int i = 0; i < this.length(); i++) {
            if(this.get(i).equals(element)) return i;
        }
        return -1;
    }
    public void clear() {
        this.arr = this.__arr(size);
    }
    public boolean contains(T element) {
        return this.indexOf(element) != -1;
    }
}