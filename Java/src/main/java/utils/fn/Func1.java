package utils.fn;

@FunctionalInterface
public interface Func1<A, B> {
    B run(A arg0);
}