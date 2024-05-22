//import manager.Managers;
//import manager.TaskManager;
//import manager.Managers;
//import manager.TaskManager;
//import org.junit.jupiter.api.Test;
//import tasks.Epic;
//import tasks.Subtask;
//import tasks.Task;
//import tasks.TaskStatus;
//
//import java.io.FileNotFoundException;
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class TestTime {
//    TaskManager inMemoryTaskManager = Managers.getDefault();
//
//
//    @Test
//    void shouldReturnTrueIfUpdateTaskWithTime() throws FileNotFoundException {
//        Task taskOne = new Task("1", "1", LocalDateTime.of(2024, 5, 8, 4, 0), 30);
//        Task taskTwo = new Task("2", "2", LocalDateTime.of(2024, 5, 8, 4, 0), 30, 1);
//        inMemoryTaskManager.createTask(taskOne);
//        inMemoryTaskManager.updateTask(taskTwo);
//        assertEquals(taskTwo, inMemoryTaskManager.getTasksById(1));
//        assertEquals(taskTwo.getStartTime(), taskOne.getStartTime());
//    }
//
//    @Test
//    void shouldReturnTrueIfUpdateTaskWithoutTime() throws FileNotFoundException {
//        Task taskOne = new Task("task-1", "task-1", LocalDateTime.of(2024, 5, 8, 4, 0), 30);
//        Task taskTwo = new Task("task-2", "task-2", 1);
//        inMemoryTaskManager.createTask(taskOne);
//        inMemoryTaskManager.updateTask(taskTwo);
//        assertEquals(taskTwo, inMemoryTaskManager.getTasksById(1));
//    }
//
//    @Test
//    void shouldReturnTrueIfUpdateSubtaskWithTime() {
//        Epic epicOne = new Epic("epic-1", "epic-1");
//        Subtask subtaskOne = new Subtask("subtask-1", "subtask-1", LocalDateTime.of(2024, 5, 8, 4, 0), 30);
//        Subtask subtaskTwo = new Subtask("subtask-2", "subtask-2", LocalDateTime.of(2024, 5, 8, 4, 0), 30, 1);
//        inMemoryTaskManager.createSubtask(subtaskOne);
//        inMemoryTaskManager.updateSubtask(subtaskTwo);
//        assertEquals(subtaskTwo, inMemoryTaskManager.getSubTasksById(1));
//    }
//
//    @Test
//    void shouldReturnTrueIfUpdateSubtaskWithoutTime() {
//        Epic epicOne = new Epic("epic-1", "epic-1");
//        Subtask subtaskOne = new Subtask("subtask-1", "subtask-1", LocalDateTime.of(2024, 5, 8, 4, 0), 30);
//        Subtask subtaskTwo = new Subtask("subtask-2", "subtask-2", 1);
//        inMemoryTaskManager.createSubtask(subtaskOne);
//        inMemoryTaskManager.updateSubtask(subtaskTwo);
//        assertEquals(subtaskTwo, inMemoryTaskManager.getSubTasksById(1));
//    }
//
//    @Test
//    void shouldReturnTrueStartTimeBeforeSubtaskStartTimeEpicEndTimeAfterSubtaskEndTimeEpic() {
//        Epic epicOne = new Epic("epic-1", "epic-1");
//        Subtask subtaskOne = new Subtask("subtask-1", "subtask-1", LocalDateTime.of(2024, 5, 8, 4, 0), 30);
//        Subtask subtaskTwo = new Subtask("subtask-2", "subtask-2", LocalDateTime.of(2024, 5, 9, 4, 0), 30);
//        inMemoryTaskManager.createEpic(epicOne);
//        inMemoryTaskManager.createSubtask(subtaskOne);
//        inMemoryTaskManager.createSubtask(subtaskTwo);
//        assertEquals(subtaskOne.getStartTime(), epicOne.getStartTime());
//        assertEquals(subtaskTwo.getEndTime(), epicOne.getEndTime());
//    }
//
//    @Test
//    void shouldReturnTrueStartTimeBeforeSubtaskStartTimeEpicEndTimeAfterSubtaskEndTimeEpicUpdateSubtask() {
//        Epic epicOne = new Epic("epic-1", "epic-1");
//        Subtask subtaskOne = new Subtask("subtask-1", "subtask-1", LocalDateTime.of(2024, 5, 8, 4, 0), 30);
//        Subtask subtaskTwo = new Subtask("subtask-2", "subtask-2", LocalDateTime.of(2024, 5, 9, 4, 0), 30);
//        Subtask subtaskThree = new Subtask("subtask-3", "subtask-3", LocalDateTime.of(2024, 5, 10, 4, 0), 30, 2);
//        inMemoryTaskManager.createEpic(epicOne);
//        inMemoryTaskManager.createSubtask(subtaskOne, epicOne);
//        inMemoryTaskManager.createSubtask(subtaskTwo, epicOne);
//        inMemoryTaskManager.updateSubtask(subtaskThree);
//        assertEquals(subtaskTwo.getStartTime(), epicOne.getStartTime());
//        assertEquals(subtaskThree.getEndTime(), epicOne.getEndTime());
//    }
//
//    void shouldReturnTrueStartTimeBeforeSubtaskStartTimeEpicEndTimeAfterSubtaskEndTimeEpicUpdateSubtaskWithoutTime() {
//        Epic epicOne = new Epic("epic-1", "epic-1");
//        Subtask subtaskOne = new Subtask("subtask-1", "subtask-1", LocalDateTime.of(2024, 5, 8, 4, 0), 30);
//        Subtask subtaskTwo = new Subtask("subtask-2", "subtask-2", LocalDateTime.of(2024, 5, 9, 4, 0), 30);
//        Subtask subtaskThree = new Subtask("subtask-3", "subtask-3", 2);
//        inMemoryTaskManager.createEpic(epicOne);
//        inMemoryTaskManager.createSubtask(subtaskOne, epicOne);
//        inMemoryTaskManager.createSubtask(subtaskTwo, epicOne);
//        inMemoryTaskManager.updateSubtask(subtaskThree);
//        assertEquals(subtaskTwo.getStartTime(), epicOne.getStartTime());
//        assertEquals(subtaskTwo.getEndTime(), epicOne.getEndTime());
//    }
//}
//
