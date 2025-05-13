package Barrier;

public class BarrierImpl {
    private int currentThreadCount;
    private int size;

    public BarrierImpl(int size) {
        this.currentThreadCount  = 0;
        this.size = size;
    }

    public synchronized void approachBarrier() {
        currentThreadCount++;
        System.out.println("Current Thread - " + Thread.currentThread().getName() + "; Count - " + currentThreadCount);
        while (currentThreadCount < size) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("released Thread - " + Thread.currentThread().getName());
        notifyAll();
    }
}
