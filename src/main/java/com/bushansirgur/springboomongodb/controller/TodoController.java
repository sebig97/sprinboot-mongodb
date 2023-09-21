package com.bushansirgur.springboomongodb.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.bushansirgur.springboomongodb.exception.ToDoCollectionException;
import com.bushansirgur.springboomongodb.model.TodoDTO;
import com.bushansirgur.springboomongodb.service.TodoService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TodoController {

    @Autowired
    private TodoService todoService;

//    @GetMapping(path = "/todos")
//    public ResponseEntity<?> getAllTodos() {
//        return todoService.findAll();
//    }

    @GetMapping(path = "/todos")
    public ResponseEntity<?> getAllTodos() {
        List<TodoDTO> todos = todoService.getAllTodos();
        return new ResponseEntity<>(todos, todos.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO) {
        try {
            todoService.createTodo(todoDTO);
            return new ResponseEntity<>(todoDTO, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (ToDoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    //    @GetMapping(path = "/todos/{id}")
//    public ResponseEntity<?> getTodoById(@PathVariable String id) {
//        return todoService.findTodoById(id);
//    }

    @GetMapping(path = "/todos/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable String id) {
        try {
            return new ResponseEntity<>(todoService.getSingleTodo(id), HttpStatus.OK);
        } catch (ToDoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping(path = "/todos/{id}")
    public ResponseEntity<?> updateById(@PathVariable String id, @RequestBody TodoDTO todo) {
        try {
            todoService.updateById(id, todo);
            return new ResponseEntity<>("Update todo with id " + id, HttpStatus.OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (ToDoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping(path = "/todos/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        try {
            todoService.deleteById(id);
            return new ResponseEntity<>("Todo wih id " + id + " was successfully deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
