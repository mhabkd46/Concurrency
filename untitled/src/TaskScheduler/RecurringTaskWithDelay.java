package TaskScheduler;

public class RecurringTaskWithDelay implements Task{
    public Long scheduledTime;
    public Long recurringTime;
    public Long delay;
    private Runnable runnable;

    public RecurringTaskWithDelay(Long scheduledTime, Long recurringTime, Long delay, Runnable runnable){
        this.scheduledTime = scheduledTime;
        this.recurringTime = recurringTime;
        this.delay = delay;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        runnable.run();
    }
}
