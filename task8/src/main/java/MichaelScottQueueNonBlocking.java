import java.util.concurrent.atomic.AtomicReference;

public class MichaelScottQueueNonBlocking<T> {
    private final AtomicReference<Node> queueHead;
    private final AtomicReference<Node> queueTail;

    public MichaelScottQueueNonBlocking() {
        Node n = new Node();
        queueHead = new AtomicReference<>(n);
        queueTail = new AtomicReference<>(n);
    }

    public void enqueue(T data) {
        Node n = new Node(data);
        Node tail = null;
        Node next = null;

        while (true) {
            tail = queueTail.get();
            next = tail.next.get();

            if (tail == queueTail.get()) {
                if (next == null) {
                    if (tail.next.compareAndSet(next, n)) {
                        break;
                    }
                } else {
                    queueTail.compareAndSet(tail, next);
                }
            }
        }
        queueTail.compareAndSet(tail, n);
    }

    public T dequeue() {
        Node head = null;
        Node tail = null;
        Node next = null;
        T data = null;

        while (true) {
            head = queueHead.get();
            tail = queueTail.get();
            next = head.next.get();

            if (head == queueHead.get()) {
                if (head == tail) {
                    if (next == null) {
                        return null;
                    }
                    queueTail.compareAndSet(tail, next);
                } else {
                    data = next.data;
                    if (queueHead.compareAndSet(head, next)) {
                        break;
                    }
                }
            }
        }

        return data;
    }

    private class Node {
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
}
