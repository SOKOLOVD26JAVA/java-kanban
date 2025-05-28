package main.ru.yandex.practicum.model;

public class Node<T extends Task> {
    public Task value;
    public Node<T> next;
    public Node<T> prev;

    public Node(Task value) {
     this.value = value;
     this.next = null;
     this.prev = null;
    }
}
