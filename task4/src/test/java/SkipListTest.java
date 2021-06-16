import lombok.Getter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SkipListTest {
    @Getter
    private static SkipList<Integer> list;
    @Test
    void insertAndCheckPresence() {
        list = new SkipList<>();
        insertWith8Threads();
        for (int i = 0; i < 800; i++) {
            assertTrue(list.contains(i));
        }
        for (int i = 800; i < 1600; i++) {
            assertFalse(list.contains(i));
        }
    }
    @Test
    void removeAndCheckAbsence() {
        list = new SkipList<>();
        insertWith8Threads();
        removeWith8Threads();
        for (int i = 0; i < 800; i++) {
            assertFalse(list.contains(i));
        }
        for (int i = 800; i < 1600; i++) {
            assertFalse(list.contains(i));
        }
    }
    @Test
    void insertAfterRemoveAndCheck() {
        list = new SkipList<>();
        insertWith8Threads();
        removeWith8Threads();
        insertWith8ThreadsOtherValues();
        for (int i = 0; i < 800; i++) {
            assertFalse(list.contains(i));
        }
        for (int i = 800; i < 1600; i++) {
            assertTrue(list.contains(i));
        }
    }
    private void insertWith8Threads(){
        Thread[] threads = new Thread[8];
        for(int i=0;i<8;i++){
            int bottomBound=100*i;
            int topBound=100*(i+1);
            threads[i]=new Thread(new SkipListAddRunnable(bottomBound,topBound));
            threads[i].start();
        }
        try {
            for(int i=0;i<8;i++) {
                threads[i].join( );
            }
        } catch (InterruptedException e) {
            e.printStackTrace( );
        }
    }
    private void insertWith8ThreadsOtherValues(){
        Thread[] threads = new Thread[8];
        for(int i=0;i<8;i++){
            int bottomBound=100*i+800;
            int topBound=100*(i+1)+800;
            threads[i]=new Thread(new SkipListAddRunnable(bottomBound,topBound));
            threads[i].start();
        }
        try {
            for(int i=0;i<8;i++) {
                threads[i].join( );
            }
        } catch (InterruptedException e) {
            e.printStackTrace( );
        }
    }
    private void removeWith8Threads(){
        Thread[] threads = new Thread[8];
        for(int i=0;i<8;i++){
            int bottomBound=100*i;
            int topBound=100*(i+1);
            threads[i]=new Thread(new SkipListRemoveRunnable(bottomBound,topBound));
            threads[i].start();
        }
        try {
            for(int i=0;i<8;i++) {
                threads[i].join( );
            }
        } catch (InterruptedException e) {
            e.printStackTrace( );
        }
    }
}
