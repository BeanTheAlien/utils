package utils;
import java.util.HashMap;

import utils.JSON.JSONError.*;
import utils.JSON.JSONObject.*;

public class JSON {
    public static void main(String[] args) throws JSONError {
        System.out.println(JSON.parse("[\"this is a test\", [\"hello\", \"world\"]]"));
    }
    public static interface JSONObject {
        public static class JSONDictionary extends HashMap<String, Object> implements JSONObject {
            String src;
            public JSONDictionary(String sourceString) {
                this.src = sourceString;
            }
        }
        public static class JSONArray extends Array<Object> implements JSONObject {
            String src;
            public JSONArray(String sourceString) {
                this.src = sourceString;
            }
        }
    }
    public static class JSONError extends Exception {
        public JSONError(String type, String message) {
            super(type + "Error" + ": " + message);
        }
        public static class JSONSyntaxError extends JSONError {
            public JSONSyntaxError(char token, int position) {
                this(String.valueOf(token), position);
            }
            public JSONSyntaxError(String token, int position) {
                super("Syntax", "Unexpected token " + token + " in JSON at position " + position);
            }
        }
        public static class JSONTypeError extends JSONError {
            public JSONTypeError() {
                super("Type", "Converting circular structure to JSON");
            }
        }
    }
    public static JSONObject parse(String json) throws JSONSyntaxError {
        JSONObject jo;
        // run parsing algorithim
        // RULES:
        // keys must be double quoted
        // no trailing comma
        // start with '{' or '['

        // since each structure is handled differently, pass it on to the other parser
        final JSONParser parser = new JSONParser(json);
        // JSON object type; '{' for Dictionary, '[' for Array
        char jot = json.charAt(0);
        // validate JSON now
        parser.validateJSON();
        // determine JSON type
        jo = jot == '{' ? new JSONDictionary(json) : new JSONArray(json);
        if(jo instanceof JSONDictionary jd) parser.parseAsDictionary(jd);
        else if(jo instanceof JSONArray ja) parser.parseAsArray(ja);
        return jo;
    }
    public static class JSONParser {
        String json;
        public JSONParser(String jsonString) {
            this.json = jsonString;
        }
        public void parseAsDictionary(JSONDictionary dict) {
            // parsing it as a dictionary
        }
        public void parseAsArray(JSONArray arr) throws JSONSyntaxError {
            // clean JSON
            this.json = this.json.substring(1, this.json.length() - 1);
            // parsing it as an array is easy
            // we know it has closure, so we can
            // skip the check for array closure
            // temporary JSON value (reset after comma)
            String jv = "";
            // position tracker
            int p = 0;
            boolean inStr = false;
            while(p < this.json.length()) {
                char c = this.json.charAt(p);
                // found a comma
                // (make sure to check if we're in a string)
                if(c == ',' && !inStr) {
                    // guard against sparse arrays
                    if(jv.length() == 0) {
                        // ...then we have't collected anything
                        throw new JSONSyntaxError(c, p);
                    }
                    // otherwise, push the element
                    arr.add(jv);
                    jv = "";
                } else if(c == '"') {
                    // out of the string
                    if(inStr) inStr = false;
                    // else, entering a string
                    else inStr = true;
                    // consume the character
                    jv += c;
                } else {
                    // otherwise, we can consume the character
                    jv += c;
                }
                // make sure to increment
                p++;
            }
            // append final result
            arr.add(jv);
            // now that we've collected everything
            // we need to parse the array elements
            // to potentially parse sub-elements
            arr.replaceAll(o -> {
                String os = (String)o;
                // then this would be a number
                if(Character.isDigit(os.charAt(0))) {
                    return os.contains(".") ? Double.parseDouble(os) : Integer.parseInt(os);
                }
                // recursively call parse
                else if(os.startsWith("{") || os.startsWith("[")) {
                    char ch = os.charAt(0);
                    final JSONParser parse = new JSONParser(os);
                    try {
                        if(ch == '{') {
                            JSONDictionary jd = new JSONDictionary(os);
                            parse.parseAsDictionary(jd);
                            return jd;
                        }
                        else if(ch == '[') {
                            JSONArray ja = new JSONArray(os);
                            parse.parseAsArray(ja);
                            return ja;
                        }
                    } catch(JSONSyntaxError e) {
                        e.printStackTrace();
                    }
                    return null;
                }
                return o;
            });
        }
        public void validateJSON() throws JSONSyntaxError {
            // JSON object type; '{' for Dictionary, '[' for Array
            char jot = this.json.charAt(0);
            // invalid JSON object!
            if(jot != '{' && jot != '[') throw new JSONSyntaxError(jot, 0);
            // now check the back
            char jot2 = this.json.charAt(this.json.length() - 1);
            // invalid JSON object!
            if((jot == '{' && jot2 != '}') || (jot == '[' && jot2 != ']')) throw new JSONSyntaxError(jot2, this.json.length() - 1);
            // just test now for single quotes
            if(this.json.contains("'")) throw new JSONSyntaxError("'", this.json.indexOf("'"));
        }
    }
    public static String stringify(JSONObject json) throws JSONTypeError {
        return "";
    }
}
