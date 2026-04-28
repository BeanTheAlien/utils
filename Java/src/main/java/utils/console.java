package utils;
public class console {
    private static String str(String d, Object... x) {
        return (new Array<Object>(x)).join(d);
    }
    public static void println(Object... x) { System.out.println(console.str(" ", x)); }
    public static void println() { System.out.println(); }
    public static void printlns(Object... x) {
        for(Object i : x) {
            console.println(i);
        }
    }
    public static void print() { System.out.print(""); }
    public static void print(Object... x) { System.out.print(console.str(" ", x)); }
    public static void prints(Object... x) {
        for(Object i : x) {
            System.out.print(i);
        }
    }
}