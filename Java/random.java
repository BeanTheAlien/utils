public class Random {
    public static int random() { return (int)(Math.random() * 101); }
    public static int random(int a) { return (int)(Math.random() * a); }
    public static int random(int a, int b) {
        if(a > b) {
            return (int)((Math.random() * (b - a)) + a);
        } else if(a < b) {
            return (int)((Math.random() * (a - b)) + b);
        } else {
            return (int)(Math.random() * a);
        }
    }
    public static boolean chance(int floor) { return ((int)(Math.random() * 101)) <= floor; }
    public static boolean ranBool() { return (int)(Math.random() * 101) <= 50; }
}
