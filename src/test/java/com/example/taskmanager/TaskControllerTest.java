package com.example.taskmanager;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.filter.TaskFilter;
import com.example.taskmanager.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>Resource: {@link com.example.taskmanager.controller.TaskController}</p>
 * <p>Description: This class checks if {@code TaskController} works properly</p>
 */
@SpringBootTest(classes = TaskManagerApplication.class)
public class TaskControllerTest {

    private final TaskRepository taskRepository;
    private final WebApplicationContext applicationContext;
    private final ObjectMapper objectMapper;
    private MockMvc mockMvc;
    private Task task;

    @Autowired
    public TaskControllerTest(
            TaskRepository taskRepository,
            WebApplicationContext applicationContext,
            ObjectMapper objectMapper) {
        this.taskRepository = taskRepository;
        this.applicationContext = applicationContext;
        this.objectMapper = objectMapper;
    }

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
        task = createEntity();
    }

    private Task createEntity() {
        return taskRepository.findById(1L).get();
    }

    /**
     * <p><strong>Given</strong>: A task filter</p>
     * <p><strong>Expecting</strong>: {@code findTaskByFilterTest} should find the task by its title and user email and return it.
     * All fields should be the same as the task which is stored in db</p>
     */
    @Test
    void findTaskByFilterTest() throws Exception {
        TaskFilter filter = new TaskFilter();
        filter.setEmail(task.getUser().getEmail());
        filter.setTitle(task.getTitle());

        mockMvc.perform(MockMvcRequestBuilders.post("/todo/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(task.getId()))
                .andExpect(jsonPath("$.[0].userId").value(task.getUser().getId()))
                .andExpect(jsonPath("$.[0].completed").value(task.getCompleted()))
                .andExpect(jsonPath("$.[0].title").value(task.getTitle()));
    }

    /**
     * <p><strong>Given</strong>: A task filter</p>
     * <p><strong>Expecting</strong>: {@code findTaskByTitleTest} should find the task by its title and return it.
     * All fields should be the same as the task which is stored in db</p>
     */
    @Test
    void findTaskByTitleTest() throws Exception {
        TaskFilter filter = new TaskFilter();
        filter.setTitle(task.getTitle());

        mockMvc.perform(MockMvcRequestBuilders.post("/todo/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(task.getId()))
                .andExpect(jsonPath("$.[0].userId").value(task.getUser().getId()))
                .andExpect(jsonPath("$.[0].completed").value(task.getCompleted()))
                .andExpect(jsonPath("$.[0].title").value(task.getTitle()));
    }

    /**
     * <p><strong>Given</strong>: A task filter</p>
     * <p><strong>Expecting</strong>: {@code findTaskByUserEmailTest} should find the task by its user email and return it.
     * All fields should be the same as the task which is stored in db</p>
     */
    @Test
    void findTaskByUserEmailTest() throws Exception {
        TaskFilter filter = new TaskFilter();
        filter.setEmail(task.getUser().getEmail());

        mockMvc.perform(MockMvcRequestBuilders.post("/todo/filter")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(filter)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(task.getId()))
                .andExpect(jsonPath("$.[0].userId").value(task.getUser().getId()))
                .andExpect(jsonPath("$.[0].completed").value(task.getCompleted()))
                .andExpect(jsonPath("$.[0].title").value(task.getTitle()));
    }

    /**
     * <p><strong>Expecting</strong>: {@code findAllTasks} should find all tasks stored in db and return them.
     * All fields should be the same as the task which is stored in db</p>
     */
    @Test
    void findAllTasks() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/todo/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize((int) taskRepository.count())))
                .andExpect(jsonPath("$.[0].id").value(task.getId()))
                .andExpect(jsonPath("$.[0].userId").value(task.getUser().getId()))
                .andExpect(jsonPath("$.[0].completed").value(task.getCompleted()))
                .andExpect(jsonPath("$.[0].title").value(task.getTitle()));
    }
}
