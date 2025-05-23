package main.ru.yandex.practicum.manager;
import main.ru.yandex.practicum.model.Node;
import main.ru.yandex.practicum.model.Task;
import java.util.*;

public class InMemoryHistoryManager<T> implements HistoryManager {
    private List<Task> tasks = new ArrayList<>();
    private Map<Integer,Node> historyMap = new HashMap<>();
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    @Override
    public void addTask(Task task) {
        if (task == null) {
            return;
        }

        if (historyMap.containsKey(task.getId())) {
            removeNode(historyMap.get(task.getId()));
            remove(task.getId());
        }

        Node<Task> newNode = new Node<>(task);
        linkLast((T) newNode.value);
        historyMap.put(task.getId(), newNode);
    }

    @Override
    public void remove(int id) {
        historyMap.remove(id);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<>(getTasks());
    }

    private void linkLast(T value) {
        Node<T> newNode = new Node<>(value);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    private ArrayList<Task>  getTasks() {
        Node<T> current = head;
        while (current != null) {
            tasks.add((Task) current.value);
            current = current.next;
        }
        return new ArrayList<>(tasks);
    }

    private void removeNode(Node<T> node) {
        Node<T> current = head;

        while (current != null) {
            if (current.value.equals(node.value)) {
                if (current == head) {
                    head = current.next;
                    if (head != null) {
                        head.prev = null;
                    } else {
                        tail = null;
                    }
                } else if (current == tail) {
                    tail = current.prev;
                    if (tail != null) {
                        tail.next = null;
                    } else {
                        head = null;
                    }
                } else {
                    current.prev.next = current.next;
                    current.next.prev = current.prev;
                }
                return;
            }
            current = current.next;
        }
    }
}
