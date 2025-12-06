package Ratelimiters;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        FixedWindow fixedWindow = new FixedWindow();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> fixedWindow.canServeRequest("userId1"), "Thread" + i).start();
            Thread.sleep(10000L);
        }
    }
}
