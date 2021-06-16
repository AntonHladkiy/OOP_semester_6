import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SkipListRemoveRunnable implements Runnable{
    private final int bottomBound;
    private final int topBound;
    @Override
    public void run( ) {
        for (int i = bottomBound; i < topBound; i++) {
            SkipListTest.getList().remove(i);
        }
    }
}
