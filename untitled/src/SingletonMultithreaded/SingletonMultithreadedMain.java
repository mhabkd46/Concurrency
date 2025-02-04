package SingletonMultithreaded;

public class SingletonMultithreadedMain {
    public static void main(String[] args) {

        for (int i = 0; i < 10000000; i++) {
            new Thread(() -> {
                SingletonMultithreaded instance = SingletonMultithreaded.getInstance();
                System.out.println(instance.value);
            }).start();
        }
    }
}
