import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EnqueueRunnable implements Runnable {
    private final int bottomBound;
    private final int topBound;
    @Override
    public void run( ) {
        for (int i = bottomBound; i < topBound; i++) {
            QueueTest.getQueue().enqueue(i);
        }
    }
}
