package UnisexBathroom;
/*
1. Can cause thread starvation. If there are many males, then females will keep waiting for long time causing starvation.
 */
public class UnisexBathroomWithSynchronization {
    int people = 0;
    String occupyingGender = "None";
    int size = 3;

    public UnisexBathroomWithSynchronization() {}

    public void acquireMaleBathroom() {
        synchronized (this) {
            while (occupyingGender.equals("Female") || people >= size) {
                try {
                    System.out.println(Thread.currentThread().getName() + " Waiting for Male Bathroom");
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            occupyingGender = "Male";
            people++;
            System.out.println(Thread.currentThread().getName() + " acquired Male Bathroom");
            System.out.println("Total people - " + people);
        }

        performBathroom();
        releaseBathroom();
    }

    public void acquireFemaleBathroom() {
        synchronized (this) {
            while (occupyingGender.equals("Male") || people >= size) {
                try {
                    System.out.println(Thread.currentThread().getName() + " Waiting for Female Bathroom ");
                    wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            occupyingGender = "Female";
            people++;
            System.out.println(Thread.currentThread().getName() + " acquired Female Bathroom");
            System.out.println("Total people - " + people);
        }

        performBathroom();
        releaseBathroom();
    }

    public void releaseBathroom() {
        synchronized (this) {
            people--;
            System.out.println(Thread.currentThread().getName() + " releasing Bathroom");
            System.out.println("Total people - " + people);
            if (people == 0) {
                System.out.println(Thread.currentThread().getName() + " empty bathroom");
                occupyingGender = "None";
            }
            notifyAll();
        }
    }

    private void performBathroom() {
        try {
            System.out.println(Thread.currentThread().getName()  + " performing bathroom");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
