package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Stream;

public class IO {
    public static Path path(String pathString) {
        return Path.of(pathString);
    }
    public static String read(String path) throws IOException {
        return IO.read(IO.path(path));
    }
    public static List<String> readlns(String path) throws IOException {
        return IO.readlns(IO.path(path));
    }
    public static String read(Path path) throws IOException {
        return Files.readString(path);
    }
    public static List<String> readlns(Path path) throws IOException {
        return Files.readAllLines(path);
    }
    public static Stream<String> lines(String path) throws IOException {
        return IO.lines(IO.path(path));
    }
    public static Stream<String> lines(Path path) throws IOException {
        return Files.lines(path);
    }
    public static void write(String path, String cont) throws IOException {
        IO.write(IO.path(path), cont);
    }
    public static void write(String path, List<String> cont) throws IOException {
        IO.write(IO.path(path), cont);
    }
    public static void write(Path path, String cont) throws IOException {
        Files.writeString(path, cont);
    }
    public static void write(Path path, List<String> cont) throws IOException {
        Files.write(path, cont);
    }
    public static void append(String path, String cont) throws IOException {
        IO.append(IO.path(path), cont);
    }
    public static void append(String path, List<String> cont) throws IOException {
        IO.append(IO.path(path), cont);
    }
    public static void append(Path path, String cont) throws IOException {
        Files.writeString(path, cont, StandardOpenOption.APPEND);
    }
    public static void append(Path path, List<String> cont) throws IOException {
        Files.write(path, cont, StandardOpenOption.APPEND);
    }
}
