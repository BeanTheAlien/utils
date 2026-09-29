package utils.array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * {@code BArray} is a custom {@code List} implementation.
 * <br><br>
 * It has custom methods like {@code removes} to leave {@code null} references.
 * <br><br>
 * The array automatically grows to store new elements.
 * @param <T> The type of element to store.
 */
public class BArray<T> implements List<T> {
    public static void main(String[] args) {
        BArray<String> x = new BArray<>();
        x.add("Hello");
        x.add("World");
        System.out.println(x);
    }
    public static final int size = 10;
    T[] arr;
    public BArray() {
        this.arr = this.__arr(size);
    }
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
    public int length() {
        return this.arr.length;
    }
    public int size() {
        return this.length();
    }
    public T get(int index) {
        return this.arr[index];
    }
    public T set(T element, int index) {
        if(!this.__isok(index)) throw new IndexOutOfBoundsException("Index of " + index + " exceeds length of " + this.length());
        T e = this.get(index);
        this.sets(element, index);
        return e;
    }
    public T set(int index, T element) {
        return this.set(element, index);
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
    public boolean add(T element) {
        int i = this.__spaceidx();
        this.sets(element, this.__isok(i) ? i : this.length());
        return true;
    }
    public void add(int index, T element) {
        this.__shift(index);
        this.set(index, element);
    }
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
    public int indexOf(Object element) {
        for(int i = 0; i < this.length(); i++) {
            if(this.get(i).equals(element)) return i;
        }
        return -1;
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
    public boolean retainAll(Collection<?> c) {
        c.forEach(x -> {
            if(!this.contains(x)) this.removes((T)x);
        });
        this.__cull();
        return true;
    }
    public int lastIndexOf(Object o) {
        int i = -1;
        for(int j = 0; j < this.length(); j++) {
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
    public String join(String delim) {
        this.__shr();
        StringBuilder s = new StringBuilder("[");
        for(int i = 0; i < this.length(); i++) {
            T e = this.get(i);
            if(e == null) continue;
            s.append(e).append(i < this.length() - 1 ? delim : "");
        }
        return s.append("]").toString();
    }
}