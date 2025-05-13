package DiningPhilosopher;

import java.util.*;

public class DiningPhilosopherMain {

    public static void main (String[] args) {
        int totalPhilosophers = 5;
        DiningPhilisopherImpl diningPhilisopherImpl = new DiningPhilisopherImpl(totalPhilosophers);

        List<Thread> philosophers = new ArrayList<Thread>();
        for (int i = 0; i < totalPhilosophers; i++) {
            final int position = i;
            
            Thread phiThread = new Thread(() -> {
                try {
                    diningPhilisopherImpl.doPhilosopherThings(position);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            },
            "P" + position);
            philosophers.add(phiThread);
            phiThread.start();
        }


        for (Thread phiThread: philosophers){
            try {
                phiThread.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
}
 