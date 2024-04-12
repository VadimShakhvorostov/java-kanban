package history;

public class Node<T> {

    private T element;
    private Node<T> next;
    private Node<T> previous;

    public Node(Node<T> previous, T element, Node<T> next) {
        this.element = element;
        this.next = next;
        this.previous = previous;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public void setPrevious(Node<T> previous) {
        this.previous = previous;
    }

    public T getElement() {
        return element;
    }

    public Node<T> getNext() {
        return next;
    }

    public Node<T> getPrevious() {
        return previous;
    }
}
