import lombok.Getter;

import java.util.Random;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class SkipList<T> {
        @Getter
        private static final int maxHeight = 16;
        private static final Random random = new Random();

        private final Node<T> firstNode = new Node<>(Integer.MIN_VALUE);
        private final Node<T> lastNode = new Node<>(Integer.MAX_VALUE);

        public SkipList() {
            for (int i = 0; i < firstNode.getNext().length; i++) {
                firstNode.getNext()[i] = new AtomicMarkableReference<>(lastNode, false);
            }
        }

        public boolean add(T value) {
            int topLevel = getRandomHeight();
            int bottomLevel = 0;
            Node<T>[] prev = (Node<T>[])new Node[maxHeight + 1];
            Node<T>[] successors =(Node<T>[]) new Node[maxHeight + 1];
            while (true) {
                boolean found = findPrevAndSuccessors(value, prev, successors);
                if (found) {
                    return false;
                } else {
                    Node<T> newNode = new Node<>(value, topLevel);
                    for (int level = bottomLevel; level <= topLevel; level++) {
                        Node<T> successor = successors[level];
                        newNode.getNext()[level].set(successor, false);
                    }
                    Node<T> previousNode = prev[bottomLevel];
                    Node<T> successor = successors[bottomLevel];
                    newNode.getNext()[bottomLevel].set(successor, false);
                    if (!previousNode.getNext()[bottomLevel].compareAndSet(successor, newNode,
                            false, false)) {
                        continue;
                    }
                    for (int level = bottomLevel+1; level <= topLevel; level++) {
                        while (true) {
                            previousNode = prev[level];
                            successor = successors[level];
                            if (previousNode.getNext()[level].compareAndSet(successor, newNode, false, false))
                                break;
                            findPrevAndSuccessors(value, prev, successors);
                        }
                    }
                    return true;
                }
            }
        }

        public boolean remove(T value) {
            int bottomLevel = 0;
            Node<T>[] prev = (Node<T>[]) new Node[maxHeight + 1];
            Node<T>[] successors = (Node<T>[]) new Node[maxHeight + 1];
            Node<T> successor;
            boolean found = findPrevAndSuccessors(value, prev, successors);
            if (!found) {
                return false;
            } else {
                Node<T> nodeToRemove = successors[bottomLevel];
                for (int level = nodeToRemove.getTowerHeight();
                     level >= bottomLevel+1; level--) {
                    boolean[] marked = {false};
                    successor = nodeToRemove.getNext()[level].get(marked);
                    while (!marked[0]) {
                        nodeToRemove.getNext()[level].attemptMark(successor, true);
                        successor = nodeToRemove.getNext()[level].get(marked);
                    }
                }
                boolean[] marked = {false};
                successor = nodeToRemove.getNext()[bottomLevel].get(marked);
                while (true) {
                    boolean iMarkedIt =
                            nodeToRemove.getNext()[bottomLevel].compareAndSet(successor, successor,
                                    false, true);
                    successor = successors[bottomLevel].getNext()[bottomLevel].get(marked);
                    if (iMarkedIt) {
                        findPrevAndSuccessors(value, prev, successors);
                        return true;
                    }
                    else if (marked[0]) return false;
                }
            }
        }

        private boolean findPrevAndSuccessors(T value, Node<T>[] prev, Node<T>[] successors) {
            int bottomLevel = 0;
            int key = value.hashCode();
            boolean[] marked = {false};
            boolean snip;
            Node<T> previousNode, curr = null, successor;
            outer:
            while (true) {
                previousNode = firstNode;
                for (int level = maxHeight; level >= bottomLevel; level--) {
                    curr = previousNode.getNext()[level].getReference();
                    while (true) {
                        successor = curr.getNext()[level].get(marked);
                        while (marked[0]) {
                            snip = previousNode.getNext()[level].compareAndSet(curr, successor,
                                    false, false);
                            if (!snip) continue outer;
                            curr = previousNode.getNext()[level].getReference();
                            successor = curr.getNext()[level].get(marked);
                        }
                        if (curr.getKey() < key){
                            previousNode = curr; curr = successor;
                        } else {
                            break;
                        }
                    }
                    prev[level] = previousNode;
                    successors[level] = curr;
                }
                return (curr.getKey() == key);
            }
        }

        public boolean contains(T value) {
            int valueHash = value.hashCode();
            boolean[] marked = {false};
            Node<T> pred = firstNode, curr = null, successor;
            for (int level = maxHeight; level >= 0; level--) {
                curr = pred.getNext()[level].getReference();
                while (true) {
                    successor = curr.getNext()[level].get(marked);
                    while (marked[0]) {
                        curr = pred.getNext()[level].getReference();
                        successor = curr.getNext()[level].get(marked);
                    }
                    if (curr.getKey() < valueHash){
                        pred = curr;
                        curr = successor;
                    } else {
                        break;
                    }
                }
            }
            return (curr.getKey() == valueHash);
        }

        private int getRandomHeight() {
            for (int i = 0; i < maxHeight; i++) {
                if (random.nextBoolean()) {
                    return i;
                }
            }
            return maxHeight - 1;
        }

    }
