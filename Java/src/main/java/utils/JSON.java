package utils;
import java.util.HashMap;

import utils.JSON.JSONError.JSONSyntaxError;
import utils.JSON.JSONObject.JSONArray;
import utils.JSON.JSONObject.JSONDictionary;

public class JSON {
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
    public static JSONObject parse(String json) throws JSONError.JSONSyntaxError {
        JSONObject jo;
        // run parsing algorithim
        // RULES:
        // keys must be double quoted
        // no trailing comma
        // start with '{' or '['

        // JSON object type; '{' for Dictionary, '[' for Array
        char jot = json.charAt(0);
        // invalid JSON object!
        if(jot != '{' && jot != '[') throw new JSONError.JSONSyntaxError(jot, 0);
        // now check the back
        char jot2 = json.charAt(json.length() - 1);
        // invalid JSON object!
        if((jot == '{' && jot2 != '}') || (jot == '[' && jot2 != ']')) throw new JSONError.JSONSyntaxError(jot2, json.length() - 1);
        // just test now for single quotes
        if(json.contains("'")) throw new JSONError.JSONSyntaxError("'", json.indexOf("'"));
        // determine JSON type
        jo = jot == '{' ? new JSONObject.JSONDictionary(json) : new JSONObject.JSONArray(json);
        // since each structure is handled differently, pass it on to the other parser
        final JSONParser parser = new JSONParser(json);
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
        public void parseAsArray(JSONArray arr) throws JSONError.JSONSyntaxError {
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
                        throw new JSONError.JSONSyntaxError(c, p);
                    }
                    // otherwise, push the element
                    arr.add(jv);
                    jv = "";
                } else if(c == '"') {
                    // out of the string
                    if(inStr) inStr = false;
                    // else, entering a string
                    else inStr = true;
                } else {
                    // otherwise, we can consume the character
                    jv += c;
                }
                // make sure to increment
                p++;
            }
        }
    }
    public static String stringify(JSONObject json) throws JSONError.JSONTypeError {
        return "";
    }
}
