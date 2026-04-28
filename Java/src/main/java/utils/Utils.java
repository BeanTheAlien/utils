package utils;
public class Utils {
    public static int count(String input, String target) {
        int res = 0;
        for(int i = 0; i < input.length(); i++) {
            if(is(str(input.charAt(i)), target)) res++;
        }
        return res;
    }
    public static int count(String input, char target) {
        return count(input, str(target));
    }
    public static int count(Object[] input, char target) {
        return count(input, String.valueOf(target));
    }
    public static int count(Object[] input, String target) {
        int res = 0;
        for(Object item : input) {
            if(String.valueOf(item).equals(target)) res++;
        }
        return res;
    }
    public static String toUpper(String s) {
        return s.toUpperCase();
    }
    public static String toLower(String s) {
        return s.toLowerCase();
    }
    public static String str(Object obj) {
        return String.valueOf(obj);
    }
    public static <T> boolean is(T a, T b) {
        return a.equals(b);
    }
}