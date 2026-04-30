package utils;
import java.io.PrintStream;

public class Output {
    public PrintStream stream;
    public Output(PrintStream stream) {
        this.stream = stream;
    }
    private String str(String d, Object... x) {
        return (new Array<Object>(x)).join(d);
    }
    /**
     * Prints a line to the console, joined by a space.
     * <br><br>
     * Carriage return.
     * @param x The elements to print.
     */
    public void println(Object... x) {
        this.stream.println(this.str(" ", x));
    }
    /**
     * Prints a blank line to the console.
     */
    public void println() {
        this.stream.println();
    }
    /**
     * Prints multiple lines to the console.
     * @param x The lines to print.
     */
    public void printlns(Object... x) {
        for(Object i : x) {
            this.println(i);
        }
    }
    /**
     * Prints elements to the console, seperated by a space.
     * <br><br>
     * No carriage return.
     * @param x The elements to print.
     */
    public void print(Object... x) {
        this.stream.print(this.str(" ", x));
    }
    /**
     * Prints mutliple elements to the console.
     * @param x The elements to print.
     */
    public void prints(Object... x) {
        for(Object i : x) {
           this.print(i);
        }
    }
}
