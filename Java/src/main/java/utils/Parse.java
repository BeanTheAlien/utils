package utils;
import java.util.function.Function;

public class Parse {
    private static <T> T toT(String in, Function<String, T> parser) {
        return parser.apply(in);
    }
    public static int toInt(String str) {
        return toT(str, Integer::parseInt);
    }
    public static float toFloat(String str) {
        return toT(str, Float::parseFloat);
    }
}
