package utils;
public class Random {
    public static int random() {
        return (int)(Math.random() * 101);
    }
    public static int random(int a) {
        return (int)(Math.random() * a);
    }
    public static int random(int a, int b) {
        if(a > b) {
            int t = a;
            a = b;
            b = t;
        }
        return (int)(Math.random() * (b - a)) + a;
    }
    public static boolean chance(int floor, int ceil) {
        return Random.random(ceil) <= floor;
    }
    public static boolean chance(int floor) {
        return Random.chance(floor, 101);
    }
    public static boolean ranBool() {
        return Random.chance(50);
    }
}
