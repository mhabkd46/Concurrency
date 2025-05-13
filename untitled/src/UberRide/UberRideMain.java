package UberRide;
import java.util.*;


public class UberRideMain {
    public static void main(String[] args) {
        UberRideImpl uberRideImpl = new UberRideImpl();
        List<Thread> democrats = new ArrayList<>();
        List<Thread> republicans = new ArrayList<>();

        for (int i = 0 ; i < 4; i++) {
            Thread a = new Thread(uberRideImpl::takeSeat, "Democrat - " + i);
            democrats.add(a);
        }

        for (int i = 0 ; i < 4; i++) {
            Thread a = new Thread(uberRideImpl::takeSeat, "Republican - " + i);
            republicans.add(a);
        }

        for (int i = 0 ; i < 4; i++) {
            democrats.get(i).start();
            republicans.get(i).start();
        }

        for (int i = 0 ; i < 4; i++) {
            try {
                democrats.get(i).join();
                republicans.get(i).join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        
        System.out.println("=================END=================");
    }
}
