import manager.Manager;

public class Main {

    public static void main(String[] args) {

       Manager m = new manager.Manager();

        tasks.Epic epic1 = new tasks.Epic("Купить", "Купить планету");
        m.createEpic(epic1);
        tasks.Subtask subtask1 = new tasks.Subtask("Взять", "Взять деньги");
        m.createSubtask(subtask1, epic1);
        tasks.Subtask subtask2 = new tasks.Subtask("Сесть", "Сесть в ракету");
        m.createSubtask(subtask2, epic1);



    }
}