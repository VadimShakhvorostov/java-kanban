package test;

import manager.FileBackedTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTaskManagerTest {

    FileBackedTaskManager managers;
    FileBackedTaskManager testManagers;
    File file;

    @BeforeEach
    void createTestFile() {
        try {
            file = File.createTempFile("testFile",null);
            managers = new FileBackedTaskManager(file);
            testManagers = new FileBackedTaskManager(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @AfterEach
    void clearTestFile() {
        file.deleteOnExit();
    }
    @Test
    void shouldReturnTrueIfEqualsTask() throws FileNotFoundException {
        Task task1 = new Task("task-1", "task-1",
                LocalDateTime.of(2024,1,1,10,10,0),30);
        managers.createTask(task1);
        testManagers.loadFromFile(file);
        assertEquals(testManagers.getTasks(), managers.getTasks());
    }

    @Test
    void shouldReturnTrueIfEqualsEpic() throws FileNotFoundException {
        Epic epic1 = new Epic("epic-1", "epic-1");
        managers.createEpic(epic1);
        testManagers.loadFromFile(file);
        assertEquals(testManagers.getEpics(), managers.getEpics());
    }

    @Test
    void shouldReturnTrueIfEqualsSubtask() throws FileNotFoundException {
        Epic epic1 = new Epic("epic-1", "epic-1");
        managers.createEpic(epic1);
        Subtask subtask1 = new Subtask("subtask-1", "subtask-1",
                LocalDateTime.of(2024,1,1,10,10,0),30, epic1.getId());
        Subtask subtask2 = new Subtask("subtask-1", "subtask-1",
                LocalDateTime.of(2024,1,1,11,10,0),30, epic1.getId());
        managers.createSubtask(subtask1);
        managers.createSubtask(subtask2);
        testManagers.loadFromFile(file);
        assertEquals(testManagers.getSubtasks(), managers.getSubtasks());
    }
}


