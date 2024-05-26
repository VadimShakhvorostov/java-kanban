package test;

import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    TaskManager inMemoryTaskManager = Managers.getDefault();

    @Test
    void shouldReturnTrueIfEqualsEpic() {
        Epic epicOne = new Epic(1, "1", "1");
        Epic epicTwo = new Epic(1, "1", "1");
        inMemoryTaskManager.createEpic(epicOne);
        assertEquals(epicTwo, inMemoryTaskManager.getEpicsById(1));
    }

    @Test
    void shouldReturnTrueCreateAndSaveTask() throws FileNotFoundException {
        Task taskOne = new Task("1", "1");
        inMemoryTaskManager.createTask(taskOne);
        assertEquals(taskOne, inMemoryTaskManager.getTasksById(1));
    }

    @Test
    void shouldReturnTrueCreateAndSaveEpic() {
        Epic epicOne = new Epic("1", "1");
        inMemoryTaskManager.createEpic(epicOne);
        assertEquals(epicOne, inMemoryTaskManager.getEpicsById(1));
    }

    @Test
    void shouldReturnTrueCreateAndSaveSubtask() {
        Epic epicOne = new Epic("1", "1");
        inMemoryTaskManager.createEpic(epicOne);
        Subtask subtaskOne = new Subtask("1", "1", 1);
        inMemoryTaskManager.createSubtask(subtaskOne);
        assertEquals(subtaskOne, inMemoryTaskManager.getSubTasksById(2));
    }

    @Test
    void shouldReturnTrueRemoveAllTask() throws FileNotFoundException {
        Task taskOne = new Task("1", "1");
        inMemoryTaskManager.createTask(taskOne);
        inMemoryTaskManager.removeAllTask();
        assertEquals(0, inMemoryTaskManager.getTasks().size());
    }

    @Test
    void shouldReturnTrueRemoveAllSubtask() {
        Epic epicOne = new Epic("1", "1");
        Subtask subtaskOne = new Subtask("1", "1", 1);
        inMemoryTaskManager.createEpic(epicOne);
        inMemoryTaskManager.createSubtask(subtaskOne);
        inMemoryTaskManager.removeAllSubtask();
        assertEquals(0, inMemoryTaskManager.getSubtasks().size());
    }

    @Test
    void shouldReturnTrueRemoveAllEpic() {
        Epic epicOne = new Epic("1", "1");
        inMemoryTaskManager.createEpic(epicOne);
        inMemoryTaskManager.removeAllEpics();
        assertEquals(0, inMemoryTaskManager.getEpics().size());

    }

    @Test
    void shouldReturnTrueWhenRemoveEpicNeedRemoveHisSubtask() {
        Epic epicOne = new Epic("1", "1");
        Subtask subtaskOne = new Subtask("1", "1", 1);
        inMemoryTaskManager.createEpic(epicOne);
        inMemoryTaskManager.createSubtask(subtaskOne);
        inMemoryTaskManager.removeAllEpics();
        assertEquals(0, inMemoryTaskManager.getSubtasks().size());
    }

    @Test
    void shouldReturnTrueIfSubtaskStatusNewHisEpicStatusNew() {
        Epic epicOne = new Epic("1", "1");
        Subtask subtaskOne = new Subtask("1", "1", 1);
        inMemoryTaskManager.createEpic(epicOne);
        inMemoryTaskManager.createSubtask(subtaskOne);
        assertEquals(epicOne.getStatus(), subtaskOne.getStatus());
    }

    @Test
    void shouldReturnTrueIfSubtaskStatusInProgressHisEpicStatusInProgress() {
        Epic epicOne = new Epic("1", "1");
        Subtask subtaskOne = new Subtask("1", "1", 1);
        inMemoryTaskManager.createEpic(epicOne);
        inMemoryTaskManager.createSubtask(subtaskOne);
        inMemoryTaskManager.changeStatusToProgress(subtaskOne);
        assertEquals(epicOne.getStatus(), subtaskOne.getStatus());
    }

    @Test
    void shouldReturnTrueIfSubtaskStatusDoneHisEpicStatusDone() {
        Epic epicOne = new Epic("1", "1");
        Subtask subtaskOne = new Subtask("1", "1", 1);
        inMemoryTaskManager.createEpic(epicOne);
        inMemoryTaskManager.createSubtask(subtaskOne);
        inMemoryTaskManager.changeStatusToDone(subtaskOne);
        assertEquals(epicOne.getStatus(), TaskStatus.DONE);
    }

    @Test
    void shouldReturnTrueIfSubtaskStatusDoneAndSubtaskStatusNewHisEpicStatusInProgress() {
        Epic epicOne = new Epic("1", "1");
        Subtask subtaskOne = new Subtask("1", "1", 1);
        Subtask subtaskTwo = new Subtask("2", "2", 1);
        inMemoryTaskManager.createEpic(epicOne);
        inMemoryTaskManager.createSubtask(subtaskOne);
        inMemoryTaskManager.createSubtask(subtaskTwo);
        inMemoryTaskManager.changeStatusToDone(subtaskTwo);
        assertEquals(epicOne.getStatus(), TaskStatus.IN_PROGRESS);
    }

    @Test
    void shouldReturnTrueIfSubtaskStatusDoneAndSubtaskStatusInProgressHisEpicStatusInProgress() {
        Epic epicOne = new Epic("1", "1");
        Subtask subtaskOne = new Subtask("1", "1", 1);
        Subtask subtaskTwo = new Subtask("2", "2", 1);
        inMemoryTaskManager.createEpic(epicOne);
        inMemoryTaskManager.createSubtask(subtaskOne);
        inMemoryTaskManager.createSubtask(subtaskTwo);
        inMemoryTaskManager.changeStatusToProgress(subtaskTwo);
        assertEquals(epicOne.getStatus(), TaskStatus.IN_PROGRESS);
    }

    @Test
    void shouldReturnTrueIfSubtasksStatusInProgressHisEpicStatusInProgress() {
        Epic epicOne = new Epic("1", "1");
        Subtask subtaskOne = new Subtask("1", "1", 1);
        Subtask subtaskTwo = new Subtask("2", "2", 1);
        inMemoryTaskManager.createEpic(epicOne);
        inMemoryTaskManager.createSubtask(subtaskOne);
        inMemoryTaskManager.createSubtask(subtaskTwo);
        inMemoryTaskManager.changeStatusToProgress(subtaskOne);
        inMemoryTaskManager.changeStatusToProgress(subtaskTwo);
        assertEquals(epicOne.getStatus(), TaskStatus.IN_PROGRESS);
    }

    @Test
    void shouldReturnTrueIfSubtasksStatusDoneHisEpicStatusDone() {
        Epic epicOne = new Epic("1", "1");
        Subtask subtaskOne = new Subtask("1", "1", 1);
        Subtask subtaskTwo = new Subtask("2", "2", 1);
        inMemoryTaskManager.createEpic(epicOne);
        inMemoryTaskManager.createSubtask(subtaskOne);
        inMemoryTaskManager.createSubtask(subtaskTwo);
        inMemoryTaskManager.changeStatusToDone(subtaskOne);
        inMemoryTaskManager.changeStatusToDone(subtaskTwo);
        assertEquals(epicOne.getStatus(), TaskStatus.DONE);
    }

    @Test
    void shouldReturnTrueWhenGetTaskByID() throws FileNotFoundException {
        Task taskOne = new Task("1", "1");
        inMemoryTaskManager.createTask(taskOne);
        assertEquals(taskOne, inMemoryTaskManager.getTasksById(1));
    }

    @Test
    void shouldReturnTrueWhenGetSubtaskByID() {
        Epic epicOne = new Epic("1", "1");
        Subtask subtaskOne = new Subtask("1", "1", 1);
        inMemoryTaskManager.createEpic(epicOne);
        inMemoryTaskManager.createSubtask(subtaskOne);
        assertEquals(subtaskOne, inMemoryTaskManager.getSubTasksById(2));
    }

    @Test
    void shouldReturnTrueWhenGetEpicByID() {
        Epic epicOne = new Epic("1", "1");
        inMemoryTaskManager.createEpic(epicOne);
        assertEquals(epicOne, inMemoryTaskManager.getEpicsById(1));
    }

    @Test
    void shouldReturnTrueIfUpdateTask() throws FileNotFoundException {
        Task taskOne = new Task("1", "1");
        Task taskTwo = new Task(1, "2", "2");
        inMemoryTaskManager.createTask(taskOne);
        inMemoryTaskManager.updateTask(taskTwo);
        assertEquals(taskTwo, inMemoryTaskManager.getTasksById(1));
    }


}