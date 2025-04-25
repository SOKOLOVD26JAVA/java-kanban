package ru.yandex.practicum;

import ru.yandex.practicum.manager.TaskManager;
import ru.yandex.practicum.model.Epic;
import ru.yandex.practicum.model.Status;
import ru.yandex.practicum.model.SubTask;
import ru.yandex.practicum.model.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
//        ТАСКИ
        Task task1 = new Task("Задача 1","Описание1");
        Task task2 = new Task("Задача 2","Описание2");
        Task task3 = new Task(2,"Задача 3","Описание3");
        manager.addTAsk(task1);
        manager.addTAsk(task2);
//        Печатаем список задач
        for (Task task: manager.printAllTasks()){
            System.out.println(task);
        }
        System.out.println("___");
//        Обновил задачу, печатаю заново
        manager.updateTask(task3);
        for (Task task: manager.printAllTasks()){
            System.out.println(task);
        }
        System.out.println("___");
//        Получение таска по ID
        System.out.println(manager.getTaskById(task1.getId()));
        System.out.println("___");
//        САБТАСКИ И ЭПИКИ
        Epic epic1 = new Epic("Имя эпика 1","Описание эпика 1");
        Epic epic2 = new Epic("Имя эпика 2","Описание эпика 2");
        Epic epic3 = new Epic("Имя эпика 3","Описание эпика 3");
        Epic epic4 = new Epic(5,"Имя эпика 4","Описание эпика 4");
        manager.addEpic(epic1);
        manager.addEpic(epic2);
        manager.addEpic(epic3);
//        Печатаем эпики
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
//        Заново выводим эпики и сбатаски, что бы убедиться что все верно добавилось
        System.out.println("__Epics__");
        for (Epic epic:manager.printAllEpics()){
            System.out.println(epic);
        }
        System.out.println("__SubTasks__");
        for(SubTask subTask: manager.printAllSubTasks()){
            System.out.println(subTask);
        }
        System.out.println("___");
//        Делаем апдейт эпика и снова печатаем.
        manager.updateEpic(epic4);
        for (Epic epic:manager.printAllEpics()){
            System.out.println(epic);
        }
//        Обновляем сабтаски 1 эпика и опять печатаем эпики, в надежде что у него поменялся статус)))).
        manager.updateSubTaskStatus(subTask8);
        manager.updateSubTaskStatus(subTask9);
        manager.updateSubTaskStatus(subTask10);
        System.out.println("___");
        for (Epic epic:manager.printAllEpics()){
            System.out.println(epic);
        }
//        Вроде как сработало, теперь удаляем эпик и печатаем списки, дабы убедиться, что при удалении эпика удаляется и
//        его сабтаск с айди 12.
        System.out.println("___");
        manager.removeEpicById(epic4.getId());
        for (Epic epic:manager.printAllEpics()){
            System.out.println(epic);
        }
        System.out.println("___");
        for(SubTask subTask: manager.printAllSubTasks()){
            System.out.println(subTask);
        }
//        Теперь удаляем сабтаск и смотрим что в эпике он так же удалился(Удалил сабтаск4 задача 2_1, его айди 9)
        manager.removeSubTaskById(subTask4.getId());
        System.out.println("___");
        for (Epic epic:manager.printAllEpics()){
            System.out.println(epic);
        }
//        Ну и печатаем сабтаски по айди эпика.
        System.out.println("___");
        for (SubTask subTask: manager.getSubTaskByEpicId(epic1)){
            System.out.println(subTask);
        }































    }
}
