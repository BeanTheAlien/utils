package utils.fn;

@FunctionalInterface
public interface Func4<A, B, C, D, E> {
    E run(A arg0, B arg1, C arg2, D arg3);
}