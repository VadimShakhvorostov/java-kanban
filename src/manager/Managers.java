package manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import history.HistoryManager;
import history.InMemoryHistoryManager;
import http.LocalDateTimeAdapter;

import java.time.LocalDateTime;

public class Managers {

    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .serializeNulls()
            .create();

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

}
