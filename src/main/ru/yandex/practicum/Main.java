package main.ru.yandex.practicum;

import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.manager.Managers;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.Status;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        Task task1 = new Task("Задача 1","Описание1");
        Task task2 = new Task("Задача 2","Описание2");
        Task task3 = new Task(2,"Задача 3","Описание3");
        manager.addTask(task1);
        manager.addTask(task2);

        for (Task task: manager.printAllTasks()){
            System.out.println(task);
        }
        System.out.println("___");

        manager.updateTask(task3);
        for (Task task: manager.printAllTasks()){
            System.out.println(task);
        }
        System.out.println("___");

        System.out.println("___");

        Epic epic1 = new Epic("Имя эпика 1","Описание эпика 1");
        Epic epic2 = new Epic("Имя эпика 2","Описание эпика 2");
        Epic epic3 = new Epic("Имя эпика 3","Описание эпика 3");
        Epic epic4 = new Epic(5,"Имя эпика 4","Описание эпика 4");
        manager.addEpic(epic1);
        manager.addEpic(epic2);
        manager.addEpic(epic3);

        for (Epic epic:manager.printAllEpics()){
            System.out.println(epic);
        }
        SubTask subTask1 = new SubTask("Подзадача 1_1","Описание 1_1", epic1.getId());
        SubTask subTask2 = new SubTask("Подзадача 1_2","Описание 1_2", epic1.getId());
        SubTask subTask3 = new SubTask("Подзадача 1_3","Описание 1_3", epic1.getId());
        SubTask subTask4 = new SubTask("Подзадача 2_1","Описание 2_1", epic2.getId());
        SubTask subTask5 = new SubTask("Подзадача 2_2","Описание 2_2", epic2.getId());
        SubTask subTask6 = new SubTask("Подзадача 2_3","Описание 2_3", epic2.getId());
        SubTask subTask7 = new SubTask("Подзадача 3_1","Описание 3_1", epic3.getId());
        SubTask subTask8 = new SubTask(6,"Подзадача 4_1","Описание 4_1", epic1.getId(),Status.DONE);
        SubTask subTask9 = new SubTask(7,"Подзадача 4_2","Описание 4_2", epic1.getId(),Status.DONE);
        SubTask subTask10 = new SubTask(8,"Подзадача 4_3","Описание 4_3", epic1.getId(),Status.DONE);
        manager.addSubTask(subTask1);
        manager.addSubTask(subTask2);
        manager.addSubTask(subTask3);
        manager.addSubTask(subTask4);
        manager.addSubTask(subTask5);
        manager.addSubTask(subTask6);
        manager.addSubTask(subTask7);

        System.out.println("__Epics__");
        for (Epic epic:manager.printAllEpics()){
            System.out.println(epic);
        }
        System.out.println("__SubTasks__");
        for(SubTask subTask: manager.printAllSubTasks()){
            System.out.println(subTask);
        }
        System.out.println("___");

        manager.updateEpic(epic4);
        for (Epic epic:manager.printAllEpics()){
            System.out.println(epic);
        }

        manager.updateSubTask(subTask8);
        manager.updateSubTask(subTask9);
        manager.updateSubTask(subTask10);
        System.out.println("___");
        for (Epic epic:manager.printAllEpics()){
            System.out.println(epic);
        }

        System.out.println("___");
        manager.removeEpicById(epic4.getId());
        for (Epic epic:manager.printAllEpics()){
            System.out.println(epic);
        }
        System.out.println("___");
        for(SubTask subTask: manager.printAllSubTasks()){
            System.out.println(subTask);
        }

        manager.removeSubTaskById(subTask4.getId());
        System.out.println("___");
        for (Epic epic:manager.printAllEpics()){
            System.out.println(epic);
        }

        System.out.println("___");
        for (SubTask subTask: manager.getSubTaskByEpicId(epic1.getId())){
            System.out.println(subTask);
        }
        Epic epic8 = new Epic(8,"epic8","epic8");
        manager.addEpic(epic8);
        manager.getSubTaskById(subTask2.getId());
        manager.getEpicById(epic8.getId());
        manager.getEpicById(epic8.getId());
        manager.getEpicById(epic8.getId());
        manager.getEpicById(epic8.getId());
        manager.getEpicById(epic8.getId());
        manager.getEpicById(epic8.getId());
        manager.getEpicById(epic8.getId());
        manager.getEpicById(epic8.getId());
        manager.getEpicById(epic8.getId());
        manager.getEpicById(epic8.getId());
        System.out.println("____");
        for (Task task: manager.getHistory()){
            System.out.println(task);
        }
        System.out.println("");




































    }
}
