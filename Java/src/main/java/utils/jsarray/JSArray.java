package utils.jsarray;
import java.util.concurrent.atomic.AtomicBoolean;
import utils.fn.BoolFunc;
import utils.fn.BoolFunc2;
import utils.fn.Func;
import utils.fn.Func1;
import utils.fn.Func2;
import utils.fn.Func3;
import utils.tpl.Tuple2;

public class JSArray<T> {
    public static interface Cb<T, R> extends Func1<T, R> {}
    public static interface Cb1<T, R> extends Func2<T, Integer, R> {}
    public static interface Cb2<T, R> extends Func3<T, Integer, JSArray<T>, R> {}
    public static interface ArrayIterator<T> {
        public ArrayIteratorResult<T> next();
        public default ArrayIteratorResult<T> returns(T val) {
            return new ArrayIteratorResult<>() {
                public T value() {
                    return val;
                }
                public boolean done() {
                    return true;
                }
            };
        }
        public default ArrayIteratorResult<T> thrown(Exception exception) {
            return new ArrayIteratorResult<>() {
                public T value() {
                    return null;
                }
                public boolean done() {
                    return true;
                }
            };
        }
    }
    public static interface ArrayIteratorResult<T> {
        public T value();
        public boolean done();
    }
    public T[] arr;
    @SuppressWarnings("unchecked")
    private T[] __arr(int size) {
        return (T[])(new Object[size]);
    }
    public JSArray() {
        this.arr = this.__arr(0);
    }
    public JSArray(int size) {
        this.arr = this.__arr(size);
    }
    public int length() {
        return this.arr.length;
    }
    public void length(int length) {
        var x = this.__arr(length);
        for(int i = 0; i < length; i++) {
            x[i] = this.get(i);
        }
        this.arr = x;
    }
    public T at(int index) {
        return index < 0 ? this.get(this.length() + index) : (index >= this.length() ? null : this.get(index));
    }
    public T get(int index) {
        return this.arr[index];
    }
    public void set(T value, int index) {
        this.arr[index] = value;
    }
    @SuppressWarnings("unchecked")
    public JSArray<T> concat(JSArray<T>... arrays) {
        var x = this.clone();
        for(int i = 0; i < arrays.length; i++) {
            arrays[i].forEach(v -> x.push(v));
        }
        return x;
    }
    @SuppressWarnings("unchecked")
    public JSArray<T> clone() {
        var x = new JSArray<T>();
        this.forEach(v -> x.push(v));
        return x;
    }
    @SuppressWarnings("unchecked")
    public int push(T... elements) {
        if(elements == null) {
            return this.length();
        }
        var x = this.__arr(this.length() + elements.length);
        for(int i = 0; i < this.length(); i++) {
            x[i] = this.get(i);
        }
        for(int i = 0; i < elements.length; i++) {
            x[this.length() + i] = elements[i];
        }
        this.arr = x;
        return this.length();
    }
    public <R> void forEach(Cb<T, R> callback) {
        this.forEach((x, i) -> callback.run(x));
    }
    public <R> void forEach(Cb1<T, R> callback) {
        this.forEach((x, i, s) -> callback.run(x, i));
    }
    public <R> void forEach(Cb2<T, R> callback) {
        for(int i = 0; i < this.length(); i++) callback.run(this.get(i), i, this);
    }
    public JSArray<T> copyWithin(int target, int start) {
        return this.copyWithin(target, start, this.length());
    }
    public JSArray<T> copyWithin(int target, int start, int end) {
        for(int i = start; i < end; i++) {
            this.arr[target+(start-i)] = this.arr[i];
        }
        return this;
    }
    private boolean __test(boolean s0, BoolFunc<T> test, BoolFunc2<Boolean, Boolean> bt) {
        var b = new AtomicBoolean(s0);
        this.forEach(v -> {
            b.set(bt.run(test.run(v), b.get()));
            return null;
        });
        return b.get();
    }
    public boolean every(BoolFunc<T> predicate) {
        return this.__test(true, predicate, (a, b) -> a && b);
    }
    public boolean some(BoolFunc<T> predicate) {
        return this.__test(false, predicate, (a, b) -> a || b);
    }
    public JSArray<T> fill(T value) {
        return this.fill(value, 0);
    }
    public JSArray<T> fill(T value, int start) {
        return this.fill(value, start, this.length());
    }
    public JSArray<T> fill(T value, int start, int end) {
        for(int i = start; i < end; i++) {
            if(this.get(i) == null) this.set(value, i);
        }
        return this;
    }
    @SuppressWarnings("unchecked")
    public JSArray<T> filter(BoolFunc<T> predicate) {
        var x = this.clone();
        this.forEach(v -> {
            if(predicate.run(v)) x.push(v);
            return null;
        });
        return x;
    }
    private T __finder(Func1<BoolFunc<T>, Integer> func, BoolFunc<T> prd) {
        int i = func.run(prd);
        return i == -1 ? null : this.get(i);
    }
    public int findIndex(BoolFunc<T> predicate) {
        for(int i = 0; i < this.length(); i++) {
            if(predicate.run(this.get(i))) return i;
        }
        return -1;
    }
    public T find(BoolFunc<T> predicate) {
        return this.__finder(this::findIndex, predicate);
    }
    public int findLastIndex(BoolFunc<T> predicate) {
        int i = -1;
        for(int j = 0; j < this.length(); j++) {
            if(predicate.run(this.get(j))) i = j;
        }
        return i;
    }
    public T findLast(BoolFunc<T> predicate) {
        return this.__finder(this::findLastIndex, predicate);
    }
    // flat
    // flatMap
    public boolean includes(T element) {
        return this.some(v -> v.equals(element));
    }
    public int indexOf(T element) {
        return this.findIndex(v -> v.equals(element));
    }
    public int lastIndexOf(T element) {
        return this.findLastIndex(v -> v.equals(element));
    }
    public String join() {
        return this.join(",");
    }
    public String join(String delim) {
        var s = new StringBuilder("[");
        this.forEach(v -> s.append(v).append(delim));
        return s.append("]").toString();
    }
    public ArrayIterator<Tuple2<Integer, T>> entries() {
        return new ArrayIterator<>() {
            int i = 0;
            public ArrayIteratorResult<Tuple2<Integer, T>> next() {
                i++;
                return new ArrayIteratorResult<>() {
                    public Tuple2<Integer, T> value() {
                        return new Tuple2<>(i-1, JSArray.this.at(i-1));
                    }
                    public boolean done() {
                        return i-1 < JSArray.this.length();
                    }
                };
            }
        };
    }
    public ArrayIterator<Integer> keys() {
        return new ArrayIterator<>() {
            int i = 0;
            public ArrayIteratorResult<Integer> next() {
                i++;
                return new ArrayIteratorResult<>() {
                    public Integer value() {
                        return i-1;
                    }
                    public boolean done() {
                        return i-1 < JSArray.this.length();
                    }
                };
            }
        };
    }
    public ArrayIterator<T> values() {
        return new ArrayIterator<>() {
            int i = 0;
            public ArrayIteratorResult<T> next() {
                i++;
                return new ArrayIteratorResult<>() {
                    public T value() {
                        return JSArray.this.get(i-1);
                    }
                    public boolean done() {
                        return i-1 < JSArray.this.length();
                    }
                };
            }
        };
    }
    public <R> JSArray<R> map(Cb<T, R> callback) {
        return this.map((v, i) -> callback.run(v));
    }
    public <R> JSArray<R> map(Cb1<T, R> callback) {
        return this.map((v, i, s) -> callback.run(v, i));
    }
    @SuppressWarnings("unchecked")
    public <R> JSArray<R> map(Cb2<T, R> callback) {
        var x = new JSArray<R>();
        this.forEach((v, i) -> x.push(callback.run(v, i, this)));
        return x;
    }
    public T pop() {
        return this.splice(this.length() - 1, 1).get(0);
    }
    public T shift() {
        return this.splice(0, 1).get(0);
    }
    public JSArray<T> splice(int start) {
        return this.splice(start, this.length() - start);
    }
    public JSArray<T> splice(int start, int deleteCount) {
        return this.splice(start, deleteCount, (T[])null);
    }
    @SuppressWarnings("unchecked")
    public JSArray<T> splice(int start, int deleteCount, T... addElements) {
        if(start < 0) {
            start = Math.max(0, this.length() + start);
        }
        if(start > this.length()) {
            start = this.length();
        }
        if(deleteCount < 0) {
            deleteCount = 0;
        }
        if(deleteCount > this.length() - start) {
            deleteCount = this.length() - start;
        }
        var r = new JSArray<T>();
        for(int i = start; i < start + deleteCount; i++) {
            r.push(this.get(i));
        }
        var next = this.__arr(this.length() - deleteCount + (addElements == null ? 0 : addElements.length));
        for(int i = 0; i < start; i++) {
            next[i] = this.get(i);
        }
        if(addElements != null) {
            for(int i = 0; i < addElements.length; i++) {
                next[start + i] = addElements[i];
            }
        }
        int ii = start + (addElements == null ? 0 : addElements.length);
        for(int i = start + deleteCount; i < this.length(); i++) {
            next[ii + (i - (start + deleteCount))] = this.get(i);
        }
        this.arr = next;
        return r;
    }
    public JSArray<T> slice() {
        return this.slice(0);
    }
    public JSArray<T> slice(int start) {
        return this.slice(start, this.length());
    }
    @SuppressWarnings("unchecked")
    public JSArray<T> slice(int start, int end) {
        if(start >= this.length() || start >= end) return new JSArray<>();
        var s = new JSArray<T>();
        for(int i = start; i < end; i++) {
            s.push(this.get(i));
        }
        return s;
    }
    private <R> R __reduc(Func2<R, T, R> rd, R iv, int i0, BoolFunc<Integer> test, int ix) {
        R acc = iv;
        for(int i = i0; test.run(i); i += ix) {
            acc = rd.run(acc, this.get(i));
        }
        return acc;
    }
    public <R> R reduce(Func2<R, T, R> reducer, R initalValue) {
        return this.__reduc(reducer, initalValue, 0, i -> i < this.length(), 1);
    }
    public <R> R reduceRight(Func2<R, T, R> reducer, R initalValue) {
        return this.__reduc(reducer, initalValue, this.length() - 1, i -> i >= 0, -1);
    }
    public JSArray<T> reverse() {
        var x = this.__arr(this.length());
        for(int i = this.length() - 1; i >= 0; i--) {
            x[this.length() - 1 - i] = this.get(i);
        }
        this.arr = x;
        return this;
    }
    public JSArray<T> flat() {
        return this.flat(1);
    }
    @SuppressWarnings("unchecked")
    public JSArray<T> flat(int depth) {
        if(depth < 0) depth = 0;
        var out = new JSArray<T>();
        for (int i = 0; i < this.length(); i++) {
            var v = this.get(i);
            if(v instanceof JSArray<?> arr && depth > 0) {
                for(int j = 0; j < arr.length(); j++) {
                    var child = arr.get(j);
                    if(child instanceof JSArray<?> nested && depth > 1) {
                        // recurse for deeper nesting
                        var sub = ((JSArray<?>)nested).flat(depth - 1);
                        for(int k = 0; k < sub.length(); k++) {
                            out.push((T)sub.get(k));
                        }
                    } else {
                        out.push((T)child);
                    }
                }
            } else {
                out.push((T)v);
            }
        }
        return out;
    }
    public <R> JSArray<R> flatMap(Cb<T, R> callback) {
        return this.map((v, i) -> callback.run(v)).flat();
    }
    public <R> JSArray<R> flatMap(Cb1<T, R> callback) {
        return this.map((v, i, s) -> callback.run(v, i)).flat();
    }
    @SuppressWarnings("unchecked")
    public <R> JSArray<R> flatMap(Cb2<T, R> callback) {
        var x = new JSArray<R>();
        this.forEach((v, i) -> x.push(callback.run(v, i, this)));
        return x.flat();
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public JSArray<T> sort() {
        return this.sort((a, b) -> ((Comparable)a).compareTo((Comparable)b));
    }
    public JSArray<T> sort(Func2<T, T, Integer> compareFn) {
        throw new UnsupportedOperationException();
    }
}