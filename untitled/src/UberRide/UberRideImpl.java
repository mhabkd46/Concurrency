package UberRide;


public class UberRideImpl {
    int republicans = 0;
    int democrats = 0;

    int size = 4;
    int currentSize = 0;
    int exitingSize = 0;

    String carState = "Stopped";

    public synchronized void takeSeat() {

        while (!carState.equals("Stopped")) {
            System.out.println(Thread.currentThread().getName() + " car is driving. waiting");
            try {
                wait();
            }
            catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (Thread.currentThread().getName().startsWith("Democrat")) {
            
            System.out.println(Thread.currentThread().getName());

            while (republicans != 0 && republicans != 2) {
                try {
                    System.out.println(Thread.currentThread().getName() + " is waiting as republicans = " + republicans + " and democrats = " + democrats);
                    wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            democrats++;
            currentSize++;
        }
        else {
            System.out.println(Thread.currentThread().getName());
            while (democrats != 0 && democrats != 2) {
                try {
                    System.out.println(Thread.currentThread().getName() + " is waiting as republicans = " + republicans + " and democrats = " + democrats);
                    wait();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            republicans++;
            currentSize++;
        }
        
        System.out.println(Thread.currentThread().getName() + " : " + "Republicans = " + republicans + " and democrats = " + democrats);
        notifyAll();
        // Apply a barrier; Only allow the threads to exit when currentSize == size

        while (currentSize < size) {
            try {
                System.out.println(Thread.currentThread().getName() + " : " + "Waiting for car to fill; Republicans = " + republicans + " and democrats = " + democrats);
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        notifyAll();

        drive();

        // Exiting the car
        exitingSize++;
        
        while (exitingSize < size) {
            try {
                wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (!carState.equals("Stopped")) {
            System.out.println("=================STOPPED=================");
            System.out.println(Thread.currentThread().getName() + " : " + "Stopping the car");
            carState = "Stopped";
        }

        if (Thread.currentThread().getName().startsWith("Democrat")) {
            
            democrats--;
            currentSize--;
        }
        else {
            republicans--;
            currentSize--;
        }
        System.out.println(Thread.currentThread().getName() + " : " + "Exited the Car; currentSize = " + currentSize);

        if (currentSize == 0) {
            notifyAll();
        }
    }

    private synchronized void drive() {
        if (!carState.equals("Drive")) {
            carState = "Drive";
            System.out.println("=================DRIVING=================");
            System.out.println(Thread.currentThread().getName() + " started the drive");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
