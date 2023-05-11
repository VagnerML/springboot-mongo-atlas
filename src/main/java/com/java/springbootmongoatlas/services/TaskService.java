package com.java.springbootmongoatlas.services;

import com.java.springbootmongoatlas.model.Task;
import com.java.springbootmongoatlas.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository repository;

    @Autowired
    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }


    //crud create , read , update , delete

    public Task addTask(Task task){
        task.setTaskId(UUID.randomUUID().toString().split("_")[0]);
        repository.save(task);
        return task;
    }


    public List<Task> findAllTasks(){
        return repository.findAll();
}
    public Task getTaskByTaskId(String taskId) {
        return repository.findById(taskId)
                .orElse(null);
    }

    public List<Task> getTaskBySeverity(int severity){
        return  repository.findBySeverity(severity);
    }

    public List<Task> getTaskByAssignee(String assignee){
        return repository.getTasksByAssignee(assignee);
    }

    public Task updateTask(Task taskRequest) {
        Optional<Task> optionalTask = repository.findById(taskRequest.getTaskId());
        if (optionalTask.isPresent()) {
            Task existingTask = optionalTask.get();
            existingTask.setDescription(taskRequest.getDescription());
            existingTask.setSeverity(taskRequest.getSeverity());
            existingTask.setAssignee(taskRequest.getAssignee());
            existingTask.setStoryPoint(taskRequest.getStoryPoint());
            return repository.save(existingTask);
        } else {
            // Handle the situation when the Task is not found
            // You can throw a custom exception, return a default value, or perform any other action
            return null; // Example: returning null if the Task is not found
        }
    }


    public String deleteTask(String taskId){
        repository.deleteById(taskId);
        return taskId+" task deleted from dashboard ";
    }
}
