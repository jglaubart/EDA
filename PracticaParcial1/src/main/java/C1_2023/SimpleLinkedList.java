package C1_2023;

public class SimpleLinkedList <T>{
    private Node root = null;
    private Node tail = null;    //agrego puntero a tail

    public void dump() {
        Node current = root;

        while (current!=null ) {
            // avanzo
            System.out.println(current.data);
            current= current.next;
        }
    }

    public void add(T data) {   //Agrego el metodo add que actualiza tambien la tail
        if(data == null) throw new IllegalArgumentException("Cannot add null data");
        Node newNode = new Node(data, null);
        if(root == null) {
            root = newNode;
            tail = newNode;
        } else{
            tail.next = newNode;
            tail = newNode;
        }
    }

    private final class Node {
        private T data;
        private Node next;

        private Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

}
