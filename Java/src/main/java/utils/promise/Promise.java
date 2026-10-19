package utils.promise;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Promise<T> {
    private CompletableFuture<T> async;
    public Promise(Supplier<T> x) {
        this.async = CompletableFuture.supplyAsync(x);
    }
    private Promise(CompletableFuture<?> x) {
        this.async = (CompletableFuture<T>)x;
    }
    @SuppressWarnings("unused")
    public Promise<T> then(Consumer<? super T> func) {
        return new Promise<>(this.async.thenAccept(func));
    }
    @SuppressWarnings("unused")
    public Promise<T> then(Function<? super T, ?> func) {
        return new Promise<>(this.async.thenApply(func));
    }
    @SuppressWarnings("unused")
    public Promise<T> catchx(Function<Throwable, ? extends T> func) {
        return new Promise<>(this.async.exceptionally(func));
    }
    @SuppressWarnings("unused")
    public Promise<T> finallyx(Supplier<Void> func) {
        return new Promise<>(this.async.whenComplete((a, b) -> func.get()));
    }
}