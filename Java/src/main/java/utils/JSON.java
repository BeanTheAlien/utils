package utils;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import utils.JSON.JSONError.*;
import utils.JSON.JSONObject.*;
import utils.fn.Func2;

public class JSON {
    public static void main(String[] args) throws JSONError {
        String ja1 = "[\"this is a test\", [\"hello\", \"world\"]]";
        String jd1 = "{ \"this\": \"is\", \"a\": \"test\" }";
        String ja2 = "[\"this\", \"errors\",]";
        JSONDictionary jd2 = JSON.parsed("{ \"hello\": \"world\" }");
        System.out.println(JSON.stringify(jd2));
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
    public static interface JSONReviver extends Func2<String, Object, Object> {}
    public static JSONObject parse(String json) throws JSONSyntaxError {
        return JSON.parse(json, null);
    }
    public static JSONObject parse(String json, JSONReviver reviver) throws JSONSyntaxError {
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
        if(jo instanceof JSONDictionary jd) parser.parseAsDictionary(jd, reviver);
        else if(jo instanceof JSONArray ja) parser.parseAsArray(ja, reviver);
        return jo;
    }
    public static JSONDictionary parsed(String json) throws JSONSyntaxError {
        return JSON.parsed(json, null);
    }
    public static JSONDictionary parsed(String json, JSONReviver reviver) throws JSONSyntaxError {
        return (JSONDictionary)(JSON.parse(json, reviver));
    }
    public static JSONArray parsea(String json) throws JSONSyntaxError {
        return JSON.parsea(json);
    }
    public static JSONArray parsea(String json, JSONReviver reviver) throws JSONSyntaxError {
        return (JSONArray)(JSON.parse(json, reviver));
    }
    public static class JSONParser {
        String json;
        public JSONParser(String jsonString) {
            this.json = jsonString.strip();
        }
        public void cleanJSON() {
            this.json = this.json.substring(1, this.json.length() - 1);
        }
        public void parseAsDictionary(JSONDictionary dict, JSONReviver reviver) throws JSONSyntaxError {
            // clean JSON
            this.cleanJSON();
            // parsing it as a dictionary
            // parsing it as an dict is harder
            // we know it has closure, so we can
            // skip the check for object closure
            // temporary JSON key/value (reset after comma)
            String jk = "";
            String jv = "";
            // position tracker
            int p = 0;
            boolean inStr = false;
            boolean inKey = true;
            while(p < this.json.length()) {
                char c = this.json.charAt(p);
                // found a comma
                // (check if we're in a string)
                if(c == ',' && !inStr) {
                    // guard against empty keys
                    if(jk.length() == 0) {
                        // ...then the key is blank
                        throw new JSONSyntaxError(c, p);
                    }
                    // otherwise, put the pair
                    dict.put(jk, jv);
                    jk = "";
                    jv = "";
                }
                // found a colon
                // (check if we're in a string)
                else if(c == ':' && !inStr) {
                    // then swap off to putting value
                    inKey = false;
                    p++;
                    continue;
                }
                // entering string
                else if(c == '"') {
                    inStr = !inStr;
                    if(inKey) jk += c;
                    else jv += c;
                }
                // else, consume
                else {
                    if(inKey) jk += c;
                    else jv += c;
                }
                p++;
            }
            // insert final result
            dict.put(jk, jv);
            // now that we've collected everything
            // we need to parse the array elements
            // to potentially parse sub-elements
            // first pass sent to reviver
            if(reviver != null) {
                JSONDictionary out = new JSONDictionary(this.json);
                dict.keySet().forEach(k -> out.put(k, reviver.run(k, dict.get(k))));
                // now we can set it back into the dictionary
                out.keySet().forEach(k -> dict.put(k, out.get(k)));
            }
            // second pass sent to standard parser
            JSONDictionary out = new JSONDictionary(this.json);
            dict.keySet().forEach(k -> out.put(k, this.parseGeneric((String)(dict.get(k)))));
            out.keySet().forEach(k -> dict.put(k, out.get(k)));
        }
        public void parseAsArray(JSONArray arr, JSONReviver reviver) throws JSONSyntaxError {
            // clean JSON
            this.cleanJSON();
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
                    // or entering string
                    inStr = !inStr;
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
            // first pass sent to reviver
            if(reviver != null) {
                JSONArray out = new JSONArray(this.json);
                arr.forEach((v, i) -> out.add(reviver.run(String.valueOf(i), v)));
                // now we can set it back into the array
                out.forEach(arr::set);
            }
            // second pass sent to standard parser
            arr.replaceAll(o -> this.parseGeneric((String)o));
        }
        public Object parseGeneric(String object) {
            char ch = object.charAt(0);
            // get a null string check out of the way
            if(object.equals("null")) return null;
            // then this would be a number
            if(Character.isDigit(ch) || (ch == '-' && Character.isDigit(object.charAt(1)))) {
                return object.contains(".") ? Double.parseDouble(object) : Integer.parseInt(object);
            }
            // recursively call parse
            else if(ch == '{' || ch == '[') {
                final JSONParser parse = new JSONParser(object);
                try {
                    if(ch == '{') {
                        JSONDictionary jd = new JSONDictionary(object);
                        parse.parseAsDictionary(jd, null);
                        return jd;
                    }
                    else if(ch == '[') {
                        JSONArray ja = new JSONArray(object);
                        parse.parseAsArray(ja, null);
                        return ja;
                    }
                } catch(JSONSyntaxError e) {
                    e.printStackTrace();
                }
                return null;
            }
            return object;
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
            // or ends with comma
            // replace spaces (we don't care about content)
            String us = this.json.replaceAll(" ", "");
            char jot3 = us.charAt(us.length() - 1);
            if(jot3 == ',') throw new JSONSyntaxError(jot3, this.json.lastIndexOf(","));
        }
        public Object stringifyFromGeneric(Object value) {
            // if value is number/boolan, return as a regular number
            // null returns null
            if(value instanceof Number || value instanceof Boolean || value == null) return String.valueOf(value);
            // test a string
            if(value instanceof String) return value;
            // otherwise, looks like JSON?
            char vc = String.valueOf(value).strip().charAt(0);
            if(vc == '{' || vc == '[') {
                // then stringify it
                try { return JSON.stringify((JSONObject)value); } catch(JSONTypeError e) { e.printStackTrace(); }
            }
            return null;
        }
        public Object stringifyGenericFromArray(JSONArray arr, int key, JSONReviver replacer) {
            if(replacer != null) {
                // replacer handles everything
                return replacer.run(String.valueOf(key), arr.get(key));
            }
            Object value = arr.get(key);
            return this.stringifyFromGeneric(value);
        }
        public Object stringifyGenericFromDictionary(JSONDictionary dict, String key, JSONReviver replacer) {
            if(replacer != null) {
                // replacer handles everything
                return replacer.run(key, dict.get(key));
            }
            Object value = dict.get(key);
            return this.stringifyFromGeneric(value);
        }
        public String stringifyAsDictionary(JSONDictionary dict, JSONReviver replacer) throws JSONTypeError {
            // we can easily key, value stringification
            Set<String> keys = dict.keySet();
            String out = "{";
            for(int i = 0; i < keys.size(); i++) {
                String k = String.valueOf(keys.toArray()[i]);
                out += keys.toArray()[i] + ": " + this.stringifyGenericFromDictionary(dict, k, replacer);
                if(i < keys.size() - 1) out += ", ";
            }
            return out + " }";
        }
        public String stringifyAsArray(JSONArray arr, JSONReviver replacer) throws JSONTypeError {
            // we can't just convert everything to a string
            // (so no List.toString())
            String out = "[ ";
            for(int i = 0; i < arr.len(); i++) {
                out += this.stringifyGenericFromArray(arr, i, replacer);
                if(i < arr.len() - 1) out += ", ";
            }
            return out + " ]";
        }
    }
    public static String stringify(JSONObject json) throws JSONTypeError {
        return JSON.stringify(json, null);
    }
    public static String stringify(JSONObject json, JSONReviver replacer) throws JSONTypeError {
        // since each structure is handled differently, pass it on to the other stringifier
        final JSONParser parser = new JSONParser(String.valueOf(json));
        if(json instanceof JSONDictionary jd) return parser.stringifyAsDictionary(jd, replacer);
        else if(json instanceof JSONArray ja) return parser.stringifyAsArray(ja, replacer);
        return null;
    }
}
