package history;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final HashMap<Integer, Node<Task>> history = new HashMap<>();

    private Node<Task> head;
    private Node<Task> tail;

    @Override
    public void add(Task task) {
        if (task == null)
            return;
        remove(task.getId());
        linkLast(task);
        history.put(task.getId(), tail);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        if (history.containsKey(id)) {
            removeNode(history.get(id));
        }
    }

    private void removeNode(Node<Task> node) {
        Node<Task> previousNode = node.getPrevious();
        Node<Task> nextNode = node.getNext();
        if (previousNode == null) {
            head = nextNode;
        } else {
            previousNode.setNext(nextNode);
            node.setPrevious(null);
        }
        if (nextNode == null) {
            tail = previousNode;
        } else {
            nextNode.setPrevious(previousNode);
            node.setNext(null);
        }
    }

    private void linkLast(Task task) {
        Node<Task> newNode = new Node<>(tail, task, null);
        if (head == null) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
    }

    private List<Task> getTasks() {
        List<Task> list = new ArrayList<>();
        Node<Task> node = head;
        while (node != null) {
            list.add(node.getElement());
            node = node.getNext();
        }
        return list;
    }
}
