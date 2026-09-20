package utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import utils.fn.VoidFunc;
import utils.fn.VoidFunc2;
import utils.fn.VoidFunc3;

public class ChildProcess {
    public static void execp(String command, VoidFunc2<Exception, Process> handle) {
        Exception E = null;
        var b = new ProcessBuilder(command);
        Process p = null;
        try {
            p = b.start();
        } catch(Exception e) {
            E = e;
        }
        handle.run(E, p);
    }
    public static void execs(String command, VoidFunc3<Exception, InputStream, InputStream> handle) {
        Exception E = null;
        var b = new ProcessBuilder(command);
        InputStream os = null;
        InputStream is = null;
        try {
            var p = b.start();
            os = p.getInputStream();
            is = p.getErrorStream();
        } catch(Exception e) {
            E = e;
        }
        handle.run(E, os, is);
    }
    private static String __drain(InputStream stream) {
        return (new BufferedReader(new InputStreamReader(stream))).lines().collect(Collectors.joining("\n"));
    }
    public static void exec(String command, VoidFunc3<Exception, String, String> handle) {
        ChildProcess.execp(command, (e, p) -> handle.run(e, __drain(p.getInputStream()), __drain(p.getErrorStream())));
    }
    public static Process spawn(String command, String... args) throws IOException {
        var al = new ArrayList<String>();
        al.add(command);
        Arrays.stream(args).forEach(al::add);
        return (new ProcessBuilder(al)).start();
    }
    public static void spawnSync(String command, VoidFunc<Integer> handle) {
        try {
            var p = ChildProcess.spawn(command);
            int code = p.waitFor();
            handle.run(code);
        } catch(Exception e) {}
    }
    public static void exect(String command) throws IOException {
        (new ProcessBuilder(command)).inheritIO().start();
    }
}
