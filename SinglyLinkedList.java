import java.util.*;

public class SinglyLinkedList<E extends Comparable<E>> {

    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;

    private static class Node<E> {

        private E element;
        private Node<E> next;

        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> n) {
            next = n;
        }
    }

    public SinglyLinkedList() {
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public E first() {

        if (isEmpty()) {
            return null;
        }

        return head.getElement();
    }

    public E last() {

        if (isEmpty()) {
            return null;
        }

        return tail.getElement();
    }

    public void addFirst(E e) {

        head = new Node<>(e, head);

        if (isEmpty()) {
            tail = head;
        }

        size++;
    }

    public void addLast(E e) {

        Node<E> newest =
            new Node<>(e, null);

        if (isEmpty()) {
            head = newest;
        } else {
            tail.setNext(newest);
        }

        tail = newest;
        size++;
    }

    public E removeFirst() {

        // if (isEmpty()) {
        //     return null;
        // }

        // E answer = head.getElement();

        // head = head.getNext();

        // size--;

        // if (isEmpty()) {
        //     tail = null;
        // }

        // return answer;
    }

    public String toString() {

        StringBuilder sb =
            new StringBuilder();

        Node<E> current = head;

        while (current != null) {

            sb.append(
                current.getElement()
            );

            sb.append(" ");

            current =
                current.getNext();
        }

        return sb.toString();
    }

    public void swap() {

        if (size <= 1) {
            return;
        }

        /*
         * Keep the original node sequence.
         */
        List<Node<E>> original =
            new ArrayList<>();

        Node<E> current = head;

        while (current != null) {

            original.add(current);

            current =
                current.getNext();
        }

        /*
         * Sort references to the SAME nodes
         * according to their element values.
         */
        List<Node<E>> sorted =
            new ArrayList<>(original);

        sorted.sort(
            Comparator.comparing(
                Node<E>::getElement
            )
        );

        /*
         * Map each node to the node with the
         * opposite rank:
         *
         * smallest <-> largest
         * second smallest <-> second largest
         * etc.
         */
        Map<Node<E>, Node<E>> opposite =
            new IdentityHashMap<>();

        int n = sorted.size();

        for (int i = 0; i < n; i++) {

            opposite.put(
                sorted.get(i),
                sorted.get(n - 1 - i)
            );
        }

        /*
         * Rebuild the sequence using the
         * corresponding opposite-ranked nodes.
         */
        Node<E> newHead =
            opposite.get(
                original.get(0)
            );

        Node<E> previous =
            newHead;

        for (
            int i = 1;
            i < original.size();
            i++
        ) {

            Node<E> nextNode =
                opposite.get(
                    original.get(i)
                );

            previous.setNext(
                nextNode
            );

            previous =
                nextNode;
        }

        previous.setNext(null);

        head = newHead;
        tail = previous;
    }
}