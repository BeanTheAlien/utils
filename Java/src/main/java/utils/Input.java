package utils;
import java.io.InputStream;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class Input {
    public Scanner in;
    public Input(InputStream stream) {
        this.in = new Scanner(stream);
    }
    public Input() {
        this(System.in);
    }
    private <T> T promptCore(String question, String fail, Function<String, T> parser, Predicate<T> validator) {
        if(fail.equals("last")) fail = question;
        while(true) {
            System.out.println(question);
            String input = in.nextLine();
            try {
                T value = parser.apply(input);
                if(validator == null || validator.test(value)) {
                    return value;
                }
            } catch(Exception ignored) {}
            System.out.println(fail);
        }
    }
    public int promptInt() {
        return promptCore("", "", Integer::parseInt, null);
    }
    public int promptInt(String q, String fail, int[] allowed) {
        return promptCore(q, fail, Integer::parseInt, x -> {
                for(int i : allowed) if(i == x) return true;
                return false;
            }
        );
    }
    public float promptFloat() {
        return promptCore("", "", Float::parseFloat, null);
    }
    public float promptFloat(String q, String fail, float[] allowed) {
        return promptCore(q, fail, Float::parseFloat, x -> {
                for(float i : allowed) if(i == x) return true;
                return false;
            }
        );
    }
    public char promptChar() {
        return promptCore("", "", s -> {
                if(s.length() != 1) throw new RuntimeException();
                return s.charAt(0);
            },
            null
        );
    }
    public String prompt() {
        return promptCore("", "", s -> s, null);
    }
    public String prompt(String q, String fail, String[] allowed) {
        return promptCore(q, fail, s -> s, x -> {
                for(String a : allowed) if(Utils.is(x, a)) return true;
                return false;
            }
        );
    }
}