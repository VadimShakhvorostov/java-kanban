package history;

import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final HashMap<Integer, Node<Task>> history = new HashMap<>();

    private Node<Task> head;
    private Node<Task> tail;

    @Override
    public void add(Task task) {
        Node<Task> oldTail = tail;
        Node<Task> newNode = new Node<>(oldTail, task, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.setNext(newNode);
        }
        remove(task.getId());
        history.put(task.getId(), newNode);
    }

    @Override
    public List<Task> getHistory() {
        List<Task> list = new ArrayList<>();
        Node<Task> node = head;
        while (node != null){
            list.add(node.getElement());
            node = node.getNext();
        }
        return list;
    }

    @Override
    public void remove(int id) {
        if(history.containsKey(id)){
            removeNode(history.get(id));
        }
    }

    private void removeNode(Node<Task> node) {
        if (node.getNext() == null) {
            node.getPrevious().setNext(null);
            tail = node.getPrevious();
        } else if (node.getPrevious() == null) {
            node.getNext().setPrevious(null);
            head = node.getNext();
        } else {
            node.getPrevious().setNext(node.getNext());
            node.getNext().setPrevious(node.getPrevious());
        }
    }
}
