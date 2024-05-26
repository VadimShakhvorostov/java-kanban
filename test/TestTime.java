package test;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TestTime {
    TaskManager inMemoryTaskManager = Managers.getDefault();

    @Test
    void testTimeEpic() {
        Epic epicOne = new Epic("epic-1", "epic-1");
        inMemoryTaskManager.createEpic(epicOne);
        Subtask subtaskOne = new Subtask("subtask-1", "subtask-1", LocalDateTime.of(2024, 5, 8, 4, 0), 30, epicOne.getId());
        Subtask subtaskTwo = new Subtask("subtask-2", "subtask-2", LocalDateTime.of(2024, 5, 9, 4, 0), 30, epicOne.getId());
        inMemoryTaskManager.createSubtask(subtaskOne);
        inMemoryTaskManager.createSubtask(subtaskTwo);
        assertEquals(subtaskOne.getStartTime(), epicOne.getStartTime());
        assertEquals(subtaskTwo.getEndTime(), epicOne.getEndTime());
    }

    @Test
    void testUpdateTimeEpicRemoveSubtask() {
        Epic epicOne = new Epic("epic-1", "epic-1");
        inMemoryTaskManager.createEpic(epicOne);
        Subtask subtaskOne = new Subtask("subtask-1", "subtask-1", LocalDateTime.of(2024, 5, 8, 4, 0), 30, epicOne.getId());
        Subtask subtaskTwo = new Subtask("subtask-2", "subtask-2", LocalDateTime.of(2024, 5, 9, 4, 0), 30, epicOne.getId());
        inMemoryTaskManager.createSubtask(subtaskOne);
        inMemoryTaskManager.createSubtask(subtaskTwo);
        inMemoryTaskManager.removeSubtaskById(subtaskOne.getId());
        assertEquals(subtaskTwo.getStartTime(), epicOne.getStartTime());
        assertEquals(subtaskTwo.getEndTime(), epicOne.getEndTime());
    }

    @Test
    void testUpdateTimeEpicUpdateSubtask() throws FileNotFoundException {
        Epic epicOne = new Epic("epic-1", "epic-1");
        inMemoryTaskManager.createEpic(epicOne);
        Subtask subtaskOne = new Subtask("subtask-1", "subtask-1", LocalDateTime.of(2024, 5, 8, 4, 0), 30, epicOne.getId());
        Subtask subtaskTwo = new Subtask("subtask-2", "subtask-2", LocalDateTime.of(2024, 5, 9, 4, 0), 30, epicOne.getId());
        inMemoryTaskManager.createSubtask(subtaskOne);
        inMemoryTaskManager.createSubtask(subtaskTwo);
        Subtask subtaskThree = new Subtask(subtaskTwo.getId(), "subtask-2", "subtask-2", LocalDateTime.of(2024, 5, 10, 4, 0), 30);
        inMemoryTaskManager.updateSubtask(subtaskThree);
        assertEquals(subtaskOne.getStartTime(), epicOne.getStartTime());
        assertEquals(subtaskThree.getEndTime(), epicOne.getEndTime());
    }
}

