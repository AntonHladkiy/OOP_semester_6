import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DequeueRunnable implements Runnable {
    private final int bottomBound;
    private final int topBound;
    @Override
    public void run( ) {
        for (int i = bottomBound; i < topBound; i++) {
            int value=QueueTest.getQueue().dequeue();
            QueueTest.getValues().add(value);
        }
    }
}
