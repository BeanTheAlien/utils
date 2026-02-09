import java.util.ArrayList;
import java.util.Arrays;

public class Array {
    public ArrayList<Object> array;
    public Array(Object... content) {
        this.array = new ArrayList<>();
        this.array.addAll(Arrays.asList(content));
    }
    public Array() { this.array = new ArrayList<>(); }
    public void add(Object x) {
        this.array.add(x);
    }
    public void adds(Object... x) {
        this.array.addAll(Arrays.asList(x));
    }
    public void remove(Object x) {
        while(this.array.contains(x)) this.array.remove(x);
    }
    public void removes(Object... x) {
        for(Object y : x) while(this.array.contains(y)) this.array.remove(y);
    }
    public void remove(int index) {
        this.array.remove(index);
    }
    public void removes(int... indexes) {
        for(int x : indexes) this.array.remove(x);
    }
    public void sub(Object x, Object y) {
        for(int i = 0; i < this.array.size(); i++) if(this.array.get(i) == x) this.array.set(i, y);
    }
    public void subs(Object... subs) {
        for(int i = 0; i < subs.length; i += 2) if(this.array.get(i) == subs[i]) this.array.set(i, subs[i + 1]);
    }
    public void removeSome(Object x, int n) {
        for(int i = 0; i < n; i++) this.array.remove(x);
    }
    public int length() { return this.array.size(); }
    public Object get(int index) { return this.array.get(index); }
    public Object[] gets(int... indexes) {
        ArrayList<Object> gotten = new ArrayList<>();
        for(int i : indexes) gotten.add(this.array.get(i));
        Object[] arr = new Object[gotten.size()];
        for(int i = 0; i < gotten.size(); i++) arr[i] = gotten.get(i);
        return arr;
    }
    public void set(Object item, int index) { this.array.set(index, item); }
    public int indexOf(Object item) {
        for(int i = 0; i < this.array.size(); i++) if(this.array.get(i) == item) return i;
        return -1;
    }
    public int indexOf(Object item, int start) {
        for(int i = start; i < this.array.size(); i++) if(this.array.get(i) == item) return i;
        return -1;
    }
    public int indexOf(Object item, int start, int end) {
        for(int i = start; i < end; i++) if(this.array.get(i) == item) return i;
        return -1;
    }
    public int indexOf(Object item, int start, int end, int occur) {
        int n = 0;
        for(int i = start; i < end; i++) if(this.array.get(i) == item) if(n == occur) return i; else n++;
        return -1;
    }
    public boolean has(Object x) { return this.array.contains(x); }
    public boolean has(Object... items) {
        boolean result = true;
        for(Object x : items) {
            if(!this.array.contains(x)) {
                result = false;
                break;
            }
        }
        return result;
    }
    public void empty() { this.array = new ArrayList<>(); }
    public Object[] slice(int start) {
        ArrayList<Object> slice = new ArrayList<>();
        for(int i = start; i < this.array.size(); i++) slice.add(this.array.get(i));
        Object[] result = new Object[slice.size()];
        for(int i = 0; i < slice.size(); i++) result[i] = slice.get(i);
        return result;
    }
    public Object[] slice(int start, int end) {
        ArrayList<Object> slice = new ArrayList<>();
        for(int i = start; i < end; i++) slice.add(this.array.get(i));
        Object[] result = new Object[slice.size()];
        for(int i = 0; i < slice.size(); i++) result[i] = slice.get(i);
        return result;
    }
    public Object[] slice(int start, int end, int increment) {
        ArrayList<Object> slice = new ArrayList<>();
        for(int i = start; i < array.size(); i += increment) slice.add(this.array.get(i));
        Object[] result = new Object[slice.size()];
        for(int i = 0; i < slice.size(); i++) result[i] = slice.get(i);
        return result;
    }
    public void splice(int start) {
        for(int i = start; i < this.array.size(); i++) this.array.remove(i);
    }
    public void splice(int start, int end) {
        for(int i = start; i < end; i++) this.array.remove(i);
    }
    public void splice(int start, int end, int increment) {
        for(int i = start; i < end; i += increment) this.array.remove(i);
    }
    public String join(Object delimiter) {
        String string = "";
        for(int i = 0; i < this.array.size(); i++) {
            if(i < this.array.size() - 1) {
                string += this.array.get(i) + String.valueOf(delimiter);
            } else {
                string += this.array.get(i);
            }
        }
        return string;
    }
    public int count(Object item) {
        int result = 0;
        for(Object x : this.array) if(x == item) result++;
        return result;
    }
    public Object[] entries() {
        Object[] entries = new Object[this.array.size()];
        for(int i = 0; i < this.array.size(); i++) entries[i] = this.array.get(i);
        return entries;
    }
    public Object[] entries(int limit) {
        Object[] entries = new Object[limit];
        for(int i = 0; i < limit; i++) entries[i] = this.array.get(i);
        return entries;
    }
    public void length(int length) {
        ArrayList<Object> newArray = new ArrayList<>();
        length = length > this.array.size() ? this.array.size() : length;
        for(int i = 0; i < length; i++) newArray.add(this.array.get(i));
        this.array = newArray;
    }
}