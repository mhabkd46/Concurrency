package SingletonMultithreaded;

public class SingletonMultithreaded {
    static SingletonMultithreaded instance;
    int value = 0;
    private SingletonMultithreaded() {

    }

    public static SingletonMultithreaded getInstance() {
        if (instance == null) {
            synchronized (SingletonMultithreaded.class) {
                if (instance == null) {
                    instance = new SingletonMultithreaded();
                }
            }
        }

        return instance;
    }
}
