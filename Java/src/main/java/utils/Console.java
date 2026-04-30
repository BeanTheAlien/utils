package utils;
/**
 * A convenient wrapper around {@code System.out}.
 */
public class Console extends Output {
    public Console() {
        super(System.out);
    }
}