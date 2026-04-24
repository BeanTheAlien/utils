import java.util.*;

public class Array<T> {
    public ArrayList<T> array;
    public Array(T... content) {
        this();
        this.array.addAll(this.list(content));
    }
    public Array() {
        this.array = new ArrayList();
    }
    private List<T> list(T... x) {
        return Arrays.asList(x);
    }
    public void add(T... x) {
        this.array.addAll(this.list(x));
    }
    public void rm(T... x) {
        for(T y : x) this.array.remove(y);
    }
    public void rmAll(T... x) {
        this.array.removeAll(this.list(x));
    }
    public void rmAt(int index) {
        this.array.remove(index);
    }
    public void sub(T x, T y) {
        int i = this.idx(x);
        if(i != -1) this.set(y, i);
    }
    public void subAll(T x, T y) {
        int i = this.idx(x);
        while(i != -1) {
            this.set(y, i);
            i = this.idx(x);
        }
    }
    public int len() {
        return this.array.size();
    }
    public T get(int index) {
        return this.array.get(index);
    }
    public void set(T item, int index) {
        this.array.set(index, item);
    }
    public int idx(T item) {
        return this.idx(item, 0, this.len(), 1);
    }
    public int idx(T item, int start) {
        return idx(item, start, this.len(), 1);
    }
    public int idx(T item, int start, int end) {
        return idx(item, start, end, 1);
    }
    public int idx(T item, int start, int end, int occur) {
        int n = 0;
        for(int i = start; i < end; i++) if(this.get(i) == item) if(n == occur) return i; else n++;
        return -1;
    }
    public boolean has(T... x) {
        return this.array.containsAll(this.list(x));
    }
    public void empty() {
        this.array.clear();
    }
    private T[] array(int size) {
        return (T[])new Object[size];
    }
    public T[] slice() {
        return this.slice(0);
    }
    public T[] slice(int start) {
        return this.slice(start, this.len());
    }
    public T[] slice(int start, int end) {
        return this.slice(start, end, 1);
    }
    public T[] slice(int start, int end, int increment) {
        ArrayList<T> slice = new ArrayList();
        for(int i = start; i < this.len(); i += increment) slice.add(this.get(i));
        T[] result = this.array(slice.size());
        for(int i = 0; i < slice.size(); i++) result[i] = slice.get(i);
        return result;
    }
    public void splice() {
        this.splice(0);
    }
    public void splice(int start) {
        this.splice(start, this.len());
    }
    public void splice(int start, int end) {
        this.splice(start, end, 1);
    }
    public void splice(int start, int end, int increment) {
        for(int i = start; i < end; i += increment) this.rmAt(i);
    }
    public void splice(int start, T... addElements) {
        this.splice(start, this.len(), addElements);
    }
    public void splice(int start, int end, T... addElements) {
        this.splice(start, end, 1, addElements);
    }
    public void splice(int start, int end, int increment, T... addElements) {
        this.splice(start, end, increment);
        this.add(addElements);
    }
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
    public int count(T item) {
        return Utils.count(this.array.toArray(), Utils.str(item));
    }
    public void len(int length) {
        this.array.subList(length, this.len()).clear();
    }
}