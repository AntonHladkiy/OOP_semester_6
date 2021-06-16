
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicMarkableReference;

@AllArgsConstructor
@Getter
@Setter
public class Node<T> {
    private int key;
    private T value;
    private int towerHeight;

    private AtomicMarkableReference<Node<T>>[] next;

    public Node(int key) {
        this.key = key;
        this.value = null;
        this.next = (AtomicMarkableReference<Node<T>>[]) new AtomicMarkableReference[SkipList.getMaxHeight() + 1];
        this.towerHeight = SkipList.getMaxHeight();
        for (int i = 0; i < next.length; i++) {
            next[i] = new AtomicMarkableReference<Node<T>>(null, false);
        }
    }

    public Node(T value, int height) {
        this.value = value;
        this.key = value.hashCode();
        this.towerHeight = height;
        this.next = new AtomicMarkableReference[towerHeight + 1];
        for (int i = 0; i < next.length; i++) {
            next[i] = new AtomicMarkableReference<Node<T>>(null,false);
        }
    }
}
