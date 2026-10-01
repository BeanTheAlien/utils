package utils.jsarray;
import utils.fn.VoidFunc1;

public class JSArray<T> {
    public static interface Cb<T, R> extends Func1<T, R> {}
    public static interface Cb1<T, R> extends Func2<T, Integer, R> {}
    public static interface Cb2<T, R> extends Func<T, Integer, JSArray<T>, R> {}
    public static interface ArrayIterator<T> {
        public ArrayIteratorResult<T> next();
        public default ArrayIteratorResult<T> returns(T val) {
            return new ArrayIteratorResult() {
                public T value() {
                    return val;
                }
                public boolean done() {
                    return true;
                }
            };
        }
        public default ArrayIteratorResult<T> throws(Exception exception) {
            return new ArrayIteratorResult() {
                public T value() {
                    throw exception;
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
    public T at(int index) {
        return index < 0 ? this.get(this.length() + index) : (index >= this.length() ? null : this.get(index));
    }
    public T get(int index) {
        return this.arr[index];
    }
    public JSArray<T> concat(JSArray<T>... arrays) {
        var x = this.clone();
        for(int i = 0; i < arrays.length; i++) {
            arrays[i].forEach(v -> x.push(v));
        }
        return x;
    }
    public JSArray<T> clone() {
        var x = new JSArray<T>();
        this.forEach(v -> x.push(v));
        return x;
    }
    public int push(T... elements) {
        return 0;
    }
    public void forEach(Cb<T, Void> callback) {
        this.forEach((x, i) -> callback.run(x));
    }
    public void forEach(Cb1<T, Void> callback) {
        this.forEach((x, i, s) -> callback.run(x, i));
    }
    public void forEach(Cb2<T, Void> callback) {
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
    public ArrayIterator<Tuple2<Integer, T>> entries() {
        return new ArrayIterator() {
            int i = 0;
            public ArrayIteratorResult<Tuple2<Integer, T>> next() {
                return new ArrayIteratorResult() {
                    public Tuple2<Integer, T> value() {
                        return new Tuple2(i, JSArray.this.at(i));
                    }
                    public boolean done() {
                        return this.i < JSArray.this.length();
                    }
                };
            }
        };
    }
}