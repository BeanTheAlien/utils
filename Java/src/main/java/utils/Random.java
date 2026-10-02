package utils;
public class Random {
    public static double randFloat() {
        return (Math.random() * 101);
    }
    public static double randFloat(int a) {
        return (Math.random() * a);
    }
    public static double randFloat(int a, int b) {
        if(a > b) {
            int t = a;
            a = b;
            b = t;
        }
        return (Math.random() * (b - a)) + a;
    }
    public static int random() {
        return (int)Random.randFloat();
    }
    public static int random(int a) {
        return (int)Random.randFloat(a);
    }
    public static int random(int a, int b) {
        return (int)Random.randFloat(a, b);
    }
    public static Array<Integer> randoms(int count) {
        return Random.randoms(101, count);
    }
    public static Array<Integer> randoms(int max, int count) {
        return Random.randoms(0, max, count);
    }
    public static Array<Integer> randoms(int min, int max, int count) {
        var arr = new Array<Integer>();
        for(int i = 0; i < count; i++) arr.add(Random.random(min, max));
        return arr;
    }
    public static Array<Double> randFloats(int count) {
        return Random.randFloats(101, count);
    }
    public static Array<Double> randFloats(int max, int count) {
        return Random.randFloats(0, max, count);
    }
    public static Array<Double> randFloats(int min, int max, int count) {
        var arr = new Array<Double>();
        for(int i = 0; i < count; i++) arr.add(Random.randFloat(min, max));
        return arr;
    }
    public static boolean chance(int floor, int ceil) {
        return Random.random(ceil) <= floor;
    }
    public static boolean chance(int floor) {
        return Random.chance(floor, 101);
    }
    public static boolean randBool() {
        return Random.chance(50);
    }
    public static Array<Boolean> randBools(int count) {
        var arr = new Array<Boolean>();
        for(int i = 0; i < count; i++) arr.add(Random.randBool());
        return arr;
    }
    public static int index(Object[] array) {
        return Random.random(0, array.length);
    }
    public static <T> T item(T[] array) {
        return array[Random.index(array)];
    }
}
