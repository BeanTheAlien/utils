package utils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class regex {
    private static Pattern pattern;
    private static Matcher matcher;

    public static boolean match(String patternStr, String input) {
        try {
            pattern = Pattern.compile(patternStr);
        } catch (PatternSyntaxException e) {
            System.out.println("Invalid pattern: " + e);
            return false;
        }
        matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static String replace(String patternStr, String input, String replacement) {
        try {
            pattern = Pattern.compile(patternStr);
        } catch (PatternSyntaxException e) {
            System.out.println("Invalid pattern: " + e);
            return null;
        }
        matcher = pattern.matcher(input);
        return matcher.replaceAll(replacement);
    }
}