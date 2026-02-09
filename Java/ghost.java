import java.util.Arrays;
import java.util.Collections;

public class ghost {
    public static int toInt(Object val) {
        try {
            return Integer.parseInt(String.valueOf(val));
        } catch(NumberFormatException e) {
            System.out.println(e);
            return 0;
        }
    }
    public static int toInt(Object val, int fb) {
        try {
            return Integer.parseInt(String.valueOf(val));
        } catch(NumberFormatException e) {
            System.out.println(e);
            return fb;
        }
    }
    public static float toFloat(Object val) {
        try {
            return Float.parseFloat(String.valueOf(val));
        } catch(NumberFormatException e) {
            System.out.println(e);
            return 0f;
        }
    }
    public static float toFloat(Object val, float fb) {
        try {
            return Float.parseFloat(String.valueOf(val));
        } catch(NumberFormatException e) {
            System.out.println(e);
            return fb;
        }
    }
    public static double toDouble(Object val) {
        try {
            return Double.parseDouble(String.valueOf(val));
        } catch(NumberFormatException e) {
            System.out.println(e);
            return 0;
        }
    }
    public static double toDouble(Object val, double fb) {
        try {
            return Double.parseDouble(String.valueOf(val));
        } catch(NumberFormatException e) {
            System.out.println(e);
            return fb;
        }
    }
    public static double tempConvert(int temp, char to) {
        return switch(to) {
            case 'c', 'C' -> (temp - 32) * (5.0 / 9.0);
            case 'f', 'F' -> (temp * (9.0 / 5.0)) + 32;
            default -> 0;
        };
    }
    public static double tempConvert(int temp, String to) {
        return switch(to) {
            case "c", "C" -> (temp - 32) * (5.0 / 9.0);
            case "f", "F" -> (temp * (9.0 / 5.0)) + 32;
            default -> 0;
        };
    }
    public static double tempConvert(float temp, char to) {
        return switch(to) {
            case 'c', 'C' -> (temp - 32) * (5.0 / 9.0);
            case 'f', 'F' -> (temp * (9.0 / 5.0)) + 32;
            default -> 0;
        };
    }
    public static double tempConvert(float temp, String to) {
        return switch(to) {
            case "c", "C" -> (temp - 32) * (5.0 / 9.0);
            case "f", "F" -> (temp * (9.0 / 5.0)) + 32;
            default -> 0;
        };
    }
    public static double tempConvert(double temp, char to) {
        return switch(to) {
            case 'c', 'C' -> (temp - 32) * (5.0 / 9.0);
            case 'f', 'F' -> (temp * (9.0 / 5.0)) + 32;
            default -> 0;
        };
    }
    public static double tempConvert(double temp, String to) {
        return switch(to) {
            case "c", "C" -> (temp - 32) * (5.0 / 9.0);
            case "f", "F" -> (temp * (9.0 / 5.0)) + 32;
            default -> 0;
        };
    }
    public static boolean isValidIndex(Object[] arr, int index) {
        return (0 <= index && index < arr.length);
    }
    public static boolean is(Object a, Object b) { return a.equals(b); }
    public static boolean eqls(double a, double b, double tolerance) { return Math.abs(a - b) < tolerance; }
    public static void sort(Object[] arr) { Arrays.sort(arr); }
    public static void sort(Object[] arr, boolean reverse) {
        sort(arr);
        if(reverse) Collections.reverse(Arrays.asList(arr));
    }
    public static boolean has(Object[] arr, Object target) {
        for(int i = 0; i < arr.length; i++) if(String.valueOf(arr[i]).equals(String.valueOf(target))) return true;
        return false;
    }
}
