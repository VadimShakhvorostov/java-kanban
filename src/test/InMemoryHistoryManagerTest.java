package test;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Task;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    TaskManager inMemoryTaskManager = Managers.getDefault();

    @Test
    void shouldReturnTrueIfTaskGetById() throws FileNotFoundException {
        Task taskOne = new Task("1", "1");
        inMemoryTaskManager.createTask(taskOne);
        inMemoryTaskManager.getTasksById(1);
        assertTrue(inMemoryTaskManager.getHistory().contains(taskOne));
    }

    @Test
    void shouldReturnTrueIfTaskGetByIdAndRemoveTask() throws FileNotFoundException {
        Task taskOne = new Task("1", "1");
        inMemoryTaskManager.createTask(taskOne);
        inMemoryTaskManager.getTasksById(1);
        inMemoryTaskManager.removeTaskById(1);
        assertTrue(inMemoryTaskManager.getHistory().contains(taskOne));
    }

    @Test
    void shouldReturnTrueIfTaskCheckAgain() throws FileNotFoundException {
        Task taskOne = new Task("1", "1");
        inMemoryTaskManager.createTask(taskOne);
        inMemoryTaskManager.getTasksById(1);
        inMemoryTaskManager.getTasksById(1);
        assertEquals(1, inMemoryTaskManager.getHistory().size());
    }
}



