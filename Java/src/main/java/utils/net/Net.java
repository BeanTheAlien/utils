package utils.net;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;

import utils.promise.Promise;
import utils.promise.Promise.Promised;

import java.net.http.HttpResponse;

public class Net {
    private static HttpClient client;
    private static void __cli() {
        Net.client = Net.client == null ? HttpClient.newHttpClient() : Net.client;
    }
    private static Builder __prim(String url, String... header) {
        return HttpRequest.newBuilder().uri(URI.create(url)).header("Content-Type", "application/json").headers(header);
    }
    public static HttpRequest mkGet(String url, String... header) {
        return Net.__prim(url, header).GET().build();
    }
    public static HttpRequest mkPost(String url, String payload, String... header) {
        return Net.__prim(url, header).POST(HttpRequest.BodyPublishers.ofString(payload)).build();
    }
    private static HttpResponse<String> __send(HttpRequest rq) throws IOException, InterruptedException {
        Net.__cli();
        return Net.client.send(rq, BodyHandlers.ofString());
    }
    private static Promised<CompletableFuture<HttpResponse<String>>> __sendAsync(HttpRequest rq) throws IOException, InterruptedException {
        Net.__cli();
        return Promise.nw(() -> Net.client.sendAsync(rq, BodyHandlers.ofString()));
    }
    public static HttpResponse<String> get(String url, String... header) throws IOException, InterruptedException {
        return Net.__send(Net.mkGet(url, header));
    }
    public static HttpResponse<String> post(String url, String payload, String... header) throws IOException, InterruptedException {
        return Net.__send(Net.mkPost(url, payload, header));
    }
    public static Promised<CompletableFuture<HttpResponse<String>>> getAsync(String url, String... header) throws IOException, InterruptedException {
        return Net.__sendAsync(Net.mkGet(url, header));
    }
    public static Promised<CompletableFuture<HttpResponse<String>>> postAsync(String url, String payload, String... header) throws IOException, InterruptedException {
        return Net.__sendAsync(Net.mkPost(url, payload, header));
    }
}