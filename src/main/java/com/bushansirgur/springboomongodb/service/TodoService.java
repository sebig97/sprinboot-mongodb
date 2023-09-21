package com.bushansirgur.springboomongodb.service;

import com.bushansirgur.springboomongodb.exception.ToDoCollectionException;
import com.bushansirgur.springboomongodb.model.TodoDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TodoService {

//    ResponseEntity<?> findAll();

    List<TodoDTO> getAllTodos();

//    ResponseEntity<?> createTodo(TodoDTO todo);

    void createTodo(TodoDTO todo) throws ConstraintViolationException, ToDoCollectionException;

//    ResponseEntity<?> findTodoById(String id);

    TodoDTO getSingleTodo(String id) throws ToDoCollectionException;

    //    ResponseEntity<?> updateById(String id, TodoDTO todoDTO) throws ToDoCollectionException;
    void updateById(String id, TodoDTO todoDTO) throws ToDoCollectionException;

//    ResponseEntity<?> deleteById(String id);

    void deleteById(String id) throws ToDoCollectionException;


}
