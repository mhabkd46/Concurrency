package Barrier;

public class BarrierMain {
    public static void main(String[] args) {

        ReusableBarrier reusableBarrier = new ReusableBarrier(5);

        for (int i = 0 ; i < 5; i++) {
            new Thread(reusableBarrier::approachBarrier, "Thread - " + i).start();
        }
    }
}
