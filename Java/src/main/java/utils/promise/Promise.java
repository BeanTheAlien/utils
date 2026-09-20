package utils.promise;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Promise {
    private static class Promised<T> {
        CompletableFuture<T> async;
        public Promised(Supplier<T> x) {
            this.async = CompletableFuture.supplyAsync(x);
        }
        private Promised(CompletableFuture<?> x) {
            this.async = (CompletableFuture<T>)x;
        }
        @SuppressWarnings("unused")
        public Promised<T> then(Consumer<? super T> func) {
            return new Promised<>(this.async.thenAccept(func));
        }
        @SuppressWarnings("unused")
        public Promised<T> then(Function<? super T, ?> func) {
            return new Promised<>(this.async.thenApply(func));
        }
        @SuppressWarnings("unused")
        public Promised<T> catchx(Function<Throwable, ? extends T> func) {
            return new Promised<>(this.async.exceptionally(func));
        }
        @SuppressWarnings("unused")
        public Promised<T> finallyx(Supplier<Void> func) {
            return new Promised<>(this.async.whenComplete((a, b) -> func.get()));
        }
    }
    public static <T> Promised<T> nw(Supplier<T> x) {
        return new Promised<>(x);
    }
}