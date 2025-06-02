package TaskScheduler;

public class OneTimeTask implements Task {
    public Long scheduledTime;
    private Runnable runnable;

    public OneTimeTask(Long scheduledTime, Runnable runnable){
        this.scheduledTime = scheduledTime;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        runnable.run();
    }
    
}
