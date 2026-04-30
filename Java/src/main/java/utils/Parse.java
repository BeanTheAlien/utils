package utils;
import java.util.function.Function;
import java.util.function.BiFunction;

/**
 * A convenient wrapper around parsing methods.
 */
public class Parse {
    private static <T> T toT(String in, Function<String, T> parser) {
        return parser.apply(in);
    }
    private static <T> T toT(String in, int radix, BiFunction<String, Integer, T> parser) {
        return parser.apply(in, radix);
    }
    /**
     * Parses a string to an integer.
     * @param str The string to parse.
     * @return The int representation, base 10.
     */
    public static int toInt(String str) {
        return Parse.toInt(str, 10);
    }
    /**
     * Parses a string to an integer.
     * @param str The string to parse.
     * @return The int representation, base {@code radix}.
     */
    public static int toInt(String str, int radix) {
        return toT(str, radix, Integer::parseInt);
    }
    /**
     * Parses a string to a floating point decimal.
     * @param str The string to parse.
     * @return The float representation.
     */
    public static float toFloat(String str) {
        return toT(str, Float::parseFloat);
    }
    /**
     * Parses a string to a double.
     * @param str The string to parse.
     * @return The double representation.
     */
    public static double toDouble(String str) {
        return toT(str, Double::parseDouble);
    }
}
