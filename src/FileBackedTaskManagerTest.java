
import manager.FileBackedTaskManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTaskManagerTest {


    FileBackedTaskManager managers;
    File file;

    @BeforeEach
    void creatM() {
        try {
            managers = new FileBackedTaskManager(File.createTempFile("_000", null));
            file = File.createTempFile("_111111111",null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @AfterEach
    void clearTestFile() {
        file.deleteOnExit();
    }
    @Test
    void shouldReturnTrueIfEqualsEpi() throws FileNotFoundException {
        Task task1 = new Task("task-1", "task-1");
        managers.createTask(task1);
        FileBackedTaskManager testManagers = new FileBackedTaskManager(file);
        testManagers.loadFromFile(file);
        assertEquals(testManagers.getTasks(), managers.getTasks());
    }
}

