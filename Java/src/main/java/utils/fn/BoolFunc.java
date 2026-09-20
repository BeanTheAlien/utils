package utils.fn;

@FunctionalInterface
public interface BoolFunc<A> {
    boolean run(A arg0);
}