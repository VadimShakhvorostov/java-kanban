public class Main {

    public static void main(String[] args) {

        Manager m = new Manager();


        Task task1 = new Task("Мыть", "Мыть пол");
        m.createTask(task1);
        Task task2 = new Task("Пить", "Пить пиво");
        m.createTask(task2);

        Epic epic1 = new Epic("Купить", "Купить планету");
        m.createEpic(epic1);

        Subtask subtask1 = new Subtask("Взять", "Взять деньги");
        m.createSubtask(subtask1, epic1);
        Subtask subtask2 = new Subtask("Сесть", "Сесть в ракету");
        m.createSubtask(subtask2, epic1);

        Epic epic2 = new Epic("Украсть", "Украсть луну");
        m.createEpic(epic2);
        Subtask subtask02 = new Subtask("Сколотить", "Сколотить лестницу");
        m.createSubtask(subtask02, epic2);

        System.out.println(m.getEpics());
        System.out.println(m.getSubtasks());
        System.out.println(m.getTasks());

        System.out.println("______________________________________");

        m.changeStatusToProgress(task1);
        m.changeStatusToProgress(task2);

        m.changeStatusToProgress(subtask1);
        m.changeStatusToDone(subtask02);

        System.out.println(m.getEpics());
        System.out.println(m.getSubtasks());
        System.out.println(m.getTasks());

        System.out.println("______________________________________");

        m.removeTaskById(1);
        m.removeEpicById(3);

        System.out.println(m.getEpics());
        System.out.println(m.getSubtasks());
        System.out.println(m.getTasks());

    }
}