public class T {
    public static void main(String[] args) {
        utils.array.BArray<Integer> b = new utils.array.BArray<>();
        for (int i = 0; i < 12; i++) {
            System.out.println("adding " + i);
            b.add(i);
            System.out.println("size=" + b.length());
        }
    }
}
