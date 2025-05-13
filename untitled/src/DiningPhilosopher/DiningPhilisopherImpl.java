package DiningPhilosopher;

import java.util.concurrent.Semaphore;

public class DiningPhilisopherImpl {
    private int totalPhilosophers;
    private Semaphore[] forks;
    private Semaphore maxDiners;

    public DiningPhilisopherImpl(int totalPhilosophers) {
        this.totalPhilosophers = totalPhilosophers;
        forks = new Semaphore[totalPhilosophers];
        maxDiners = new Semaphore(totalPhilosophers/2);

        for (int i = 0; i < totalPhilosophers; i++) {
            forks[i] = new Semaphore(1);
        }
    }

    public void doPhilosopherThings(int philoshoperPosition) throws InterruptedException{
        int nextPosition = (philoshoperPosition + 1) % totalPhilosophers;
        contemplate();
            
        maxDiners.acquire();
        System.out.println(Thread.currentThread().getName() + " acquired MaxDiner; maxDiner = " + maxDiners.availablePermits());

        forks[philoshoperPosition].acquire();
        forks[nextPosition].acquire();

        eating();

        forks[philoshoperPosition].release();
        forks[nextPosition].release();

        maxDiners.release();
        System.out.println(Thread.currentThread().getName() + " released MaxDiner; maxDiner = " + maxDiners.availablePermits());
    }

    private void contemplate() throws InterruptedException{
        System.out.println(Thread.currentThread().getName() + " is contemplating");
        Thread.sleep(1000);

    }

    private void eating() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " is eating");
        Thread.sleep(5000);
        System.out.println(Thread.currentThread().getName() + " ate");
    }
    
}
