package utils;
/**
 * A convenient wrapper around {@code System.out}.
 */
public class console extends Output {
    private static Output cons;
    private console() {
        super(System.out);
    }
    /**
     * Prints a line to the console, joined by a space.
     * <br><br>
     * Carriage return.
     * @param x The elements to print.
     */
    public static void println(Object... x) {
        if(cons == null) cons = new console();
        cons.println(x);
    }
    /**
     * Prints a blank line to the console.
     */
    public static void println() {
        if(cons == null) cons = new console();
        cons.println();
    }
    /**
     * Prints multiple lines to the console.
     * @param x The lines to print.
     */
    public static void printlns(Object... x) {
        if(cons == null) cons = new console();
        cons.printlns(x);
    }
    /**
     * Prints elements to the console, seperated by a space.
     * <br><br>
     * No carriage return.
     * @param x The elements to print.
     */
    public static void print(Object... x) {
        if(cons == null) cons = new console();
        cons.print(x);
    }
    /**
     * Prints mutliple elements to the console.
     * @param x The elements to print.
     */
    public static void prints(Object... x) {
        if(cons == null) cons = new console();
        cons.prints(x);
    }
}