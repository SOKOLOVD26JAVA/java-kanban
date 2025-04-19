//Приветствую!) В общем кое-как, что то вроде как намудрил, как будто даже на правду похоже, однако я так и не понял из ТЗ
// как мы статусы обновляем. В моей голове созрело лишь 2 идеи и я их постарался максимально информативно отобразить.
// Первое по поводу тасков - 1 логика обновления(мы в ручную меняем статус при создании объекта, типо таким образом
// показываем что корректно выполнен конструктор.) 2 логика - (мы как бы берем какой то таск и на его позицию записывается
// другой таск с корректным айди.) А что касается эпиков и сабтасков, 1 логика - (все эпики и сабтаски изначально создаются
// со статусом NEW, затем мы можем вызвать метод обновления до статуса DONE, либо IN_PROGRESS, и соответственно потом вызываем
// метод который пересчитывает статус эпика отталкиваясь от статусов подзадач) ну и 2 логика такая же как и с таском - полностью
// переписываем подзадачу... Я ТЗ уже тысячу раз перечитал, почти наизусть его помню, но больше вариантов нет) Однако не
// удивлюсь если правильный вариант окажется максимально банальным:) Хорошего дня!
public class Main {
    public static void main(String[] args) {
        TaskManager taskManager= new TaskManager();
//        Таски
        Task task1 = new Task(taskManager.setId(), "Задача 1","Описание 1",Status.NEW);
        Task task2 = new Task(taskManager.setId(), "Задача 2","Описание 2",Status.DONE);
        Task task3 = new Task(taskManager.setId(), "Задача 3","Описание 3",Status.IN_PROGRESS);
        taskManager.addTAsk(task1);
        taskManager.addTAsk(task2);
        taskManager.addTAsk(task3);
        taskManager.printAllTasks();
        System.out.println("Обновление статуса второй логикой.");
        taskManager.updateTaskStatus(task1,task2);
        taskManager.printAllTasks();
        System.out.println("Удаление по айди.");
        taskManager.removeTaskById(task2.getId());
        taskManager.printAllTasks();
        System.out.println("получение по айди.");
        taskManager.getTaskById(task3.getId());
        System.out.println("Очистка списка.");
        taskManager.removeAllTasks();
        taskManager.printAllTasks();
//      Эпики и сабтаски
        Epic epic1 = new Epic("Эпик1","Описание эпика1", taskManager.setId());
        Epic epic2 = new Epic("Эпик2","Описание эпика2", taskManager.setId());
        taskManager.addEpic(epic1);
        taskManager.addEpic(epic2);
        SubTask subTask1_1 = new SubTask(taskManager.setId(), "Подзадача 1_1","описание1", epic1.getId());
        SubTask subTask1_2 = new SubTask(taskManager.setId(), "Подзадача 1_2","описание2", epic1.getId());
        SubTask subTask2_1 = new SubTask(taskManager.setId(), "Подзадача 2_1","описание3", epic2.getId());
        SubTask subTask2_2 = new SubTask(task2.getId(), "Подзадача 2_2","описание 4", epic2.getId());
        taskManager.addSubTask(subTask1_1);
        taskManager.addSubTask(subTask1_2);
        taskManager.addSubTask(subTask2_1);
        taskManager.addSubTask(subTask2_2);
        System.out.println("Печать всех эпиков");
        taskManager.printAllEpics();
        System.out.println("Печать всех сабтасков");
        taskManager.printAllSubTasks();
        System.out.println("Обновление статуса эпика и сабтасков первой логикой.");
        taskManager.updateStatusForTaskToDONE(subTask1_1);
        taskManager.updateStatusForTaskToDONE(subTask1_2);
        taskManager.updateEpicStatus(epic1);
        System.out.println("Статус 1 эпика - "+ epic1.getStatus());
        System.out.println("Статус 1_1 сабтаска - "+ subTask1_1.getStatus());
        System.out.println("Статус 1_2 сабтаска - "+ subTask1_2.getStatus());
        System.out.println("Демонстрация, что статус эпика меняется в зависимости от статусов сабтаска.");
        System.out.println("Статус эпика 2 до обновления - "+epic2.getStatus());
        System.out.println("Статус сабтаска2_1 до обновления - "+ subTask2_1.getStatus());
        System.out.println("Статус сабтаска2_2 до обновления - "+ subTask2_2.getStatus());
        taskManager.updateStatusForSubTaskToDONE(subTask2_1);
        taskManager.updateStatusForSubTaskToIN_PROGRESS(subTask2_2);
        taskManager.updateEpicStatus(epic2);
        System.out.println("Статус эпика 2 после обновления - "+epic2.getStatus());
        System.out.println("Статус сабтаска2_1 после обновления - "+ subTask2_1.getStatus());
        System.out.println("Статус сабтаска2_2 после обновления - "+ subTask2_2.getStatus());
        System.out.println("Обновление эпика второй логикой.");
        Epic epic3 = new Epic("Эпик3","Описание эпика3", taskManager.setId());
        SubTask subTask3_1 = new SubTask(taskManager.setId(), "Подзадача 3_1","описание 4", epic3.getId(),Status.DONE );
        SubTask subTask3_2 = new SubTask(taskManager.setId(), "Подзадача 3_2","описание 5", epic3.getId(),Status.DONE);
        SubTask subTask3_3 = new SubTask(taskManager.setId(), "Подзадача 3_3","описание 6", epic3.getId(),Status.NEW);
        taskManager.addEpic(epic3);
        taskManager.addSubTask(subTask3_1);
        taskManager.addSubTask(subTask3_2);
        taskManager.addSubTask(subTask3_3);
        System.out.println("Статус эпика 3 до обновления - "+epic3.getStatus());
        taskManager.updateSubTaskStatus(subTask3_3,subTask3_2,epic3);
        taskManager.updateEpicStatus(epic3);
        System.out.println("Статус эпика 3 до обновления - "+ epic3.getStatus());
        System.out.println("Получение подзадач по эпику.");
        taskManager.printSubTaskByEpicId(epic2);
        System.out.println("Остальные методы Эпиков и Сабтасков аналогичны обычному таску, я думаю нету смысла их " +
                "выводить только глаз замылится.) ");
























    }
}
