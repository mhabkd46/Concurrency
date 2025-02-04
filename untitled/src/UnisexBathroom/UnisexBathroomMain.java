package UnisexBathroom;
public class UnisexBathroomMain {
    public static void main(String[] args) {
//        UnisexBathroomWithSynchronization unisexBathroomWithSynchronization = new UnisexBathroomWithSynchronization();
        UnisexBathroomWithReentrantLock unisexBathroomWithReentrantLock = new UnisexBathroomWithReentrantLock();

        for (int i = 0 ; i < 10; i++) {
            new Thread(unisexBathroomWithReentrantLock::acquireMaleBathroom, "Thread - " + i).start();
        }

        for (int i = 11 ; i < 12; i++) {
            new Thread(unisexBathroomWithReentrantLock::acquireFemaleBathroom, "Thread - " + i).start();
        }
    }
}
