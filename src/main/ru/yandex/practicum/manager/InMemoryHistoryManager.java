package main.ru.yandex.practicum.manager;
import main.ru.yandex.practicum.model.Node;
import main.ru.yandex.practicum.model.Task;
import java.util.*;

public class InMemoryHistoryManager<T extends Task> implements HistoryManager {

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
            remove(task.getId());
            linkLast(task);
            return;
        }
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        if (historyMap.get(id) == null) {
            return;
        } else {
            removeNode(historyMap.get(id));
            historyMap.remove(id);
        }

    }

    @Override
    public ArrayList<Task> getHistory() {
        return getTasks();
   }

    private void linkLast(Task task) {
        Node<T> newNode = new Node<>(task);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        historyMap.put(task.getId(), newNode);
    }

    private ArrayList<Task>  getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node<T> current = head;
        while (current != null) {
            tasks.add(current.value);
            current = current.next;
        }
        return new ArrayList<>(tasks);
    }

    private void removeNode(Node<T> node) {
        if (node == null) {
            return;
        }

        if (node == head) {
            if (head.next == null) {
                head = null;
                tail = null;
            } else {
                head = node.next;
                head.prev = null;
            }
        } else if (node == tail) {
            tail = node.prev;
            if (tail != null) {
                tail.next = null;
            }
        } else {
            if (node.next != null) {
                node.next.prev = node.prev;
            }
            if (node.prev != null) {
                node.prev.next = node.next;
            }
        }

        historyMap.remove(node.value.getId());
    }
}
