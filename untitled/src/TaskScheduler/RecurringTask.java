package TaskScheduler;

public class RecurringTask implements Task{
    public Long scheduledTime;
    public Long recurringTime;
    private Runnable runnable;

    public RecurringTask(Long scheduledTime, Long recurringTime, Runnable runnable){
        this.scheduledTime = scheduledTime;
        this.recurringTime = recurringTime;
        this.runnable = runnable;
    }

    @Override
    public void run() {
        runnable.run();
    }
}
