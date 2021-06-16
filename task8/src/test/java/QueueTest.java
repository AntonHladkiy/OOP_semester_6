import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueueTest {
    @Getter
    private static MichaelScottQueueNonBlocking<Integer> queue;
    @Getter
    private static final CopyOnWriteArrayList values = new CopyOnWriteArrayList<Integer>();
    @Test
    void EnqueueAndDequeueTest(){
        queue = new MichaelScottQueueNonBlocking<>();
        enqueueWith8Threads();
        dequeueWith8Threads();
        for (int i = 0; i < 800; i++) {
            assertTrue(values.contains(i));
        }
        assertNull(queue.dequeue());
    }
    private void enqueueWith8Threads() {
        Thread[] threads = new Thread[8];
        for (int i = 0; i < 8; i++) {
            int bottomBound = 100 * i;
            int topBound = 100 * (i + 1);
            threads[i] = new Thread(new EnqueueRunnable(bottomBound,topBound));
            threads[i].start();
        }
        try {
            for (int i = 0; i < 8; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void dequeueWith8Threads() {
        Thread[] threads = new Thread[8];
        for (int i = 0; i < 8; i++) {
            int bottomBound = 100 * i;
            int topBound = 100 * (i + 1);
            threads[i] = new Thread(new DequeueRunnable(bottomBound,topBound));
            threads[i].start();
        }
        try {
            for (int i = 0; i < 8; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
