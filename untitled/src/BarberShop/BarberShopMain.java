package BarberShop;
public class BarberShopMain {
    public static void main(String[] args) {
        BarberShopWithSemaphores barberShopWithSemaphores = new BarberShopWithSemaphores(5);

        Thread barber = new Thread(() -> {
            while (true) {
                barberShopWithSemaphores.hairCut();
            }
        }, "Barber");

        barber.start();

        for (int i = 0; i < 10; i ++) {
            new Thread(barberShopWithSemaphores::takeSeat, "Customer " + i).start();
        }
    }
}
