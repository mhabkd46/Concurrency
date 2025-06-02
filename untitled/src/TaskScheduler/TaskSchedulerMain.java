package TaskScheduler;

public class TaskSchedulerMain {
    
    public static void main(String[] args) {
        TaskScheduler taskScheduler = new TaskSchedulerImpl(2,  5);
        OneTimeTask oneTimeTask = new OneTimeTask(System.currentTimeMillis() );
        RecurringTask recurringTask = new RecurringTask(System.currentTimeMillis(), 1000L, )
    }
}
