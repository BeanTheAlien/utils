package utils.fn;

@FunctionalInterface
public interface Func3<A, B, C, D> {
    D run(A arg0, B arg1, C arg2);
}