package utils;
import java.util.function.Function;
import java.util.function.BiFunction;

public class Parse {
    private static <T> T toT(String in, Function<String, T> parser) {
        return parser.apply(in);
    }
    private static <T> T toT(String in, int radix, BiFunction<String, Integer, T> parser) {
        return parser.apply(in, radix);
    }
    public static int toInt(String str) {
        return Parse.toInt(str, 10);
    }
    public static int toInt(String str, int radix) {
        return toT(str, radix, Integer::parseInt);
    }
    public static float toFloat(String str) {
        return toT(str, Float::parseFloat);
    }
    public static double toDouble(String str) {
        return toT(str, Double::parseDouble);
    }
}
