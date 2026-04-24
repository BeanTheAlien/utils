import java.io.InputStream;
import java.util.Scanner;
import java.util.function.Supplier;

public class Input {
    public Scanner in;
    public Input(InputStream stream) {
        this.in = new Scanner(stream);
    }
    public Input() {
        this(System.in);
    }
    public String prompt() {
        return this.prompt("");
    }
    public String prompt(String question) {
        System.out.println(question);
        return this.in.nextLine();
    }
    public String prompt(String question, String fail, String[] responses) {
        boolean a = false;
        String resp = "";
        if(Utils.is(fail, "last")) fail = question;
        while(!a) {
            System.out.println(question);
            resp = in.nextLine();
            for(String b : responses) {
                if(Utils.is(resp, b)) a = true;
            }
            if(!a) System.out.println(fail);
        }
        return resp;
    }
    public int promptInt() {
        int x = 0;
        String resp;
        while(true) {
            resp = in.nextLine();
            try {
                x = Integer.parseInt(resp);
                break;
            } catch(NumberFormatException e) {}
        }
        return x;
    }
    public static int promptInt(Object a) {
        int x = 0;
        String resp;
        while(true) { 
            System.out.println(a);
            resp = in.nextLine();
            try {
                x = Integer.parseInt(resp);
                break;
            } catch(NumberFormatException e) {}
        }
        return x;
    }
    public static int promptInt(Object x, Object y, int[] z) {
        boolean a = false;
        String resp;
        int n = 0;
        if(y.equals("last")) y = x;
        while(!a) {
            System.out.println(x);
            resp = in.nextLine();
            for(int b : z) {
                try {
                    int r = Integer.parseInt(resp);
                    if(r == b) {
                        a = true;
                        n = b;
                    }
                } catch(NumberFormatException e) {}
            }
            if(!a) System.out.println(y);
        }
        return n;
    }
    public static float promptFloat() {
        float x = 0f;
        String resp;
        while(true) {
            resp = in.nextLine();
            try {
                x = Float.parseFloat(resp);
                break;
            } catch(NumberFormatException e) {}
        }
        return x;
    }
    public static float promptFloat(Object a) {
        float x = 0f;
        String resp;
        while(true) { 
            System.out.println(a);
            resp = in.nextLine();
            try {
                x = Float.parseFloat(resp);
                break;
            } catch(NumberFormatException e) {}
        }
        return x;
    }
    public static float promptFloat(Object x, Object y, float[] z) {
        boolean a = false;
        String resp;
        float n = 0f;
        if(y.equals("last")) y = x;
        while(!a) {
            System.out.println(x);
            resp = in.nextLine();
            for(float b : z) {
                try {
                    float r = Float.parseFloat(resp);
                    if(r == b) {
                        a = true;
                        n = b;
                    }
                } catch(NumberFormatException e) {}
            }
            if(!a) System.out.println(y);
        }
        return n;
    }
    public static double promptDouble() {
        double x = 0;
        String resp;
        while(true) {
            resp = in.nextLine();
            try {
                x = Double.parseDouble(resp);
                break;
            } catch(NumberFormatException e) {}
        }
        return x;
    }
    public static double promptDouble(Object a) {
        double x = 0;
        String resp;
        while(true) { 
            System.out.println(a);
            resp = in.nextLine();
            try {
                x = Double.parseDouble(resp);
                break;
            } catch(NumberFormatException e) {}
        }
        return x;
    }
    public static double promptDouble(Object x, Object y, double[] z) {
        boolean a = false;
        String resp;
        double n = 0;
        if(y.equals("last")) y = x;
        while(!a) {
            System.out.println(x);
            resp = in.nextLine();
            for(double b : z) {
                try {
                    double r = Double.parseDouble(resp);
                    if(r == b) {
                        a = true;
                        n = b;
                    }
                } catch(NumberFormatException e) {}
            }
            if(!a) System.out.println(y);
        }
        return n;
    }
    public static char promptChar() {
        String resp;
        while(true) {
            resp = in.nextLine();
            if(resp.length() == 1) break;
        }
        return resp.toCharArray()[0];
    }
    public static char promptChar(Object a) {
        String resp;
        while(true) { 
            System.out.println(a);
            resp = in.nextLine();
            if(resp.length() == 1) break;
        }
        return resp.toCharArray()[0];
    }
    public static char promptChar(Object x, Object y, char[] z) {
        boolean a = false;
        String resp = "";
        if(y.equals("last")) y = x;
        while(!a) {
            System.out.println(x);
            resp = in.nextLine();
            for(char b : z) {
                if(resp.length() == 1 && resp.toCharArray()[0] == b) a = true;
            }
            if(!a) System.out.println(y);
        }
        return resp.toCharArray()[0];
    }
    public static char promptChar(Object x, Object y, char[] z, boolean truncate) {
        boolean a = false;
        String resp = "";
        if(y.equals("last")) y = x;
        while(!a) {
            System.out.println(x);
            resp = in.nextLine();
            for(char b : z) {
                if(truncate) {
                    if(resp.toCharArray()[0] == b) a = true;
                } else {
                    if(resp.length() == 1 && resp.toCharArray()[0] == b) a = true;
                }
            }
            if(!a) System.out.println(y);
        }
        return resp.toCharArray()[0];
    }
    public static void writelinef(String m, Object... x) {
        System.out.printf(m, x);
        System.out.println();
    }
    public static void writef(String m, Object... x) { System.out.printf(m, x); }
}