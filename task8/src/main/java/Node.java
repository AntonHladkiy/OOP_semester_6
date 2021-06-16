import java.util.concurrent.atomic.AtomicReference;

public class Node<T> {
    T data;
    AtomicReference<Node> next;

    Node() {
        this(null);
    }

    Node(T data) {
        this.data = data;
        this.next = new AtomicReference<>();
    }
}