package LinkedList;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class SortedLinkedList <T extends Comparable<? super T>> implements SortedListService<T> {
    private Header header;

    private final class Header {
        private Node first;
        private Node last;
        private int size;

        public Header() {
            first = last = null;
            size = 0;
        }
    }

    private final class Node {
        private T data;
        private Node next;

        private Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }

        private Node insert(T data, boolean[] rta) {
            if (this.data.compareTo(data) == 0) {
                System.err.printf("Insertion failed %s%n", data);
                rta[0] = false;
                return this;
            }
            if (this.data.compareTo(data) < 0) {
                if (next == null) {
                    rta[0] = true;
                    next = new Node(data, null);
                    return this;
                }
                next = next.insert(data, rta);
                return this;
            }
            this.next = this.next.insertRec(data, rta);
            return this;
        }

        private Node remove(T data, boolean[] rta) {
            Node current = this;
            Node prev = null;
            if (data == null) {
                rta[0] = false;
                return null;
            }
            if (data.compareTo(this.data) == 0) {
                rta[0] = true;
                return this.next;
            }
            while (data.compareTo(current.data) > 0) {
                prev = current;
                current = current.next;
            }
            if (data.compareTo(current.data) == 0) {
                assert prev != null;
                prev.next = current.next;
                rta[0] = true;
            }
            return this;
        }

        private Node insertRec(T data, boolean[] rta){
            if (this.data.compareTo(data) > 0) {
                return new Node(data, this);
            }
            if (this.data.compareTo(data) == 0) {
                System.err.println(String.format("Insertion failed. %s repeated", data));
                rta[0] = false;
                return this;
            }
            if (this.next == null) {
                this.next = new Node(data, null);
                return this;
            }
            rta[0] = true;
            return new Node(data, this);
        }
    }

    /// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /// ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public SortedLinkedList() {
        header = new Header();
    }

    @Override
    public boolean insert(T data) {
        Node prev = null;
        Node current = header.first;
        int c;
        while (current != null && (c = data.compareTo(current.data)) >= 0) {
            if (c == 0) {
                return false;
            }
            prev = current;
            current = current.next;
        }
        Node newNode = new Node(data, current);
        if (current == header.first) {
            header.first = newNode;
        } else {
            prev.next = newNode;
        }
        if (current == header.last) {
            header.last = newNode;
        }
        header.size++;
        return true;
    }

    public boolean insert2(T data) {
        if (data == null)
            throw new IllegalArgumentException("data cannot be null");

        boolean[] rta = new boolean[1];
        header.first = insertRec(data, header.first, rta);
        if (rta[0]) {
            header.size++;
        }
        return rta[0];
    }

    public Node insertRec(T data, Node current, boolean[] rta) {
        if (current != null && current.data.compareTo(data) == 0) {
            System.err.println(String.format("Insertion failed. %s repeated", data));
            rta[0] = false;
            return current;
        }
        if (current != null && current.data.compareTo(data) < 0) {
            current.next = insertRec(data, current.next, rta);
            return current;
        }
        rta[0] = true;
        return new Node(data, current);
    }

    public boolean insert3(T data) {
        if (data == null)
            throw new IllegalArgumentException("data cannot be null");

        if (header.first == null) {
            header.first = new Node(data, null);
            return true;
        }

        boolean[] rta = {false};
        header.first = header.first.insert(data, rta);

        if (rta[0]) {
            header.size++;
        }
        return rta[0];
    }

    @Override
    public boolean remove(T data) {
        Node prev = null;
        Node current = header.first;
        while (current != null && current.data.compareTo(data) < 0) {
            prev = current;
            current = current.next;
        }
        if (current != null && current.data.compareTo(data) == 0) {
            if (current == header.first)
                header.first = header.first.next;
            else
                prev.next = current.next;
            header.size--;
            return true;
        }
        return false;
    }

    // delete resuelto tod0 en la clase SortedLinkedList, recursivo
    //	@Override
    public boolean remove2(T data) {
        if (data == null) {
            return false;
        }
        boolean[] ans = {false};
        header.first = removeRec(data, header.first, ans);
        if (ans[0]) {
            header.size--;
        }
        return ans[0];
    }

    private Node removeRec(T data, Node current, boolean[] rta) {
        if (current == null) {
            return null;
        }
        int c;
        if ((c = data.compareTo(current.data)) == 0) {
            rta[0] = true;
            return current.next;
        }
        if (c > 0) {
            current.next = removeRec(data, current.next, rta);
        }
        return current;
    }

    // delete resuelto delegando al nodo
    public boolean remove3(T data) {
        if (header.first == null) {
            return false;
        }
        boolean[] ans = {false};
        header.first = header.first.remove(data, ans);
        if (ans[0]) {
            header.size--;
        }
        return ans[0];
    }

    @Override
    public boolean isEmpty() {
        return header.size == 0;
    }

    @Override
    public int size() {
        return header.size;
    }

    @Override
    public T getMin() {
        return header.first.data;
    }

    @Override
    public T getMax() {
        return header.last.data;
    }

    @Override
    public void dump() {
        Node current = header.first;

        while (current != null) {
            // avanzo
            System.out.println(current.data);
            current = current.next;
        }
    }

    @Override
    public boolean find(T data) {
        return getPos(data) != -1;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || !(other instanceof SortedLinkedList<?>))
            return false;

        @SuppressWarnings("unchecked")
        SortedLinkedList<T> auxi = (SortedLinkedList<T>) other;

        Node current = header.first;
        Node currentOther = auxi.header.first;
        while (current != null && currentOther != null) {
            if (current.data.compareTo(currentOther.data) != 0)
                return false;

            // por ahora si, avanzo ambas
            current = current.next;
            currentOther = currentOther.next;
        }

        return current == null && currentOther == null;

    }

    // -1 si no lo encontro
    protected int getPos(T data) {
        Node current = header.first;
        int pos = 0;

        while (current != null) {
            if (current.data.compareTo(data) == 0)
                return pos;

            // avanzo
            current = current.next;
            pos++;
        }
        return -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new SortedLinkedListIterator();
    }

    private class SortedLinkedListIterator implements Iterator<T> {
        private Node iter;
        private boolean canRemove;
        private Node prev;
        private Node toDel;

        private Stack<Node> stack;

        SortedLinkedListIterator() {
            iter = header.first;
        }

        public boolean hasNext() {
            return iter != null;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T aux = iter.data;
            prev = toDel;
            toDel = iter;
            iter = iter.next;
            canRemove = true;
            return aux;
        }

        public void remove() {
            if (!canRemove)
                throw new IllegalStateException();
            canRemove = false;
            if (toDel.equals(header.first))
                header.first = iter;
            else prev.next = iter;
        }

    }

    public static void main(String[] args) {
        SortedLinkedList<Integer> l = new SortedLinkedList<Integer>();
       l.insert2(3);
        l.insert(1);
        l.insert(2);
        l.insert3(4);
        l.insert2(5);
        l.insert(0);
        l.insert3(6);
        l.insert(7);
        l.insert(8);
        l.insert(9);
        l.insert3(10);

    }
}
