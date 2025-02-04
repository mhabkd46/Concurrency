package ProducerConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        int size = 10;
        Random random = new Random();
        SharedResource sharedResource = new SharedResource(new ReentrantLock());

        Thread producer = new Thread(() -> {
            while (true){
                int item = random.nextInt(10);
                sharedResource.add(item);
            }
        }, "ProducerThread");

        List<Thread> consumers = new ArrayList<Thread>();

        for (int i = 0; i < 10; i++){
            consumers.add(
                    new Thread(() -> {
                        while (true){
                            sharedResource.poll();
                        }
                    }, "ConsumerThread - " + i)
            );
        }

        producer.start();
        for (Thread consumer: consumers){
            consumer.start();
        }

    }
}