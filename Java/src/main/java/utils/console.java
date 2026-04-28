package utils;
/**
 * A convenient wrapper around {@code System.out}.
 */
public class console {
    private static String str(String d, Object... x) {
        return (new Array<Object>(x)).join(d);
    }
    /**
     * Prints a line to the console, joined by a space.
     * <br><br>
     * Carriage return.
     * @param x The elements to print.
     */
    public static void println(Object... x) {
        System.out.println(console.str(" ", x));
    }
    /**
     * Prints a blank line to the console.
     */
    public static void println() {
        System.out.println();
    }
    /**
     * Prints multiple lines to the console.
     * @param x The lines to print.
     */
    public static void printlns(Object... x) {
        for(Object i : x) {
            console.println(i);
        }
    }
    /**
     * Prints elements to the console, seperated by a space.
     * <br><br>
     * No carriage return.
     * @param x The elements to print.
     */
    public static void print(Object... x) {
        System.out.print(console.str(" ", x));
    }
    /**
     * Prints mutliple elements to the console.
     * @param x The elements to print.
     */
    public static void prints(Object... x) {
        for(Object i : x) {
            System.out.print(i);
        }
    }
}