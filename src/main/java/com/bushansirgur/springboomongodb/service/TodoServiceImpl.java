package com.bushansirgur.springboomongodb.service;

import com.bushansirgur.springboomongodb.exception.ToDoCollectionException;
import com.bushansirgur.springboomongodb.model.TodoDTO;
import com.bushansirgur.springboomongodb.repository.TodoRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

//    @Override
//    public ResponseEntity<?> findAll() {
//        List<TodoDTO> todos = todoRepository.findAll();
//
//        if (todos.size() > 0) {
//            return new ResponseEntity<>(todos, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("No todos available", HttpStatus.NOT_FOUND);
//        }
//    }

    @Override
    public List<TodoDTO> getAllTodos() {
        List<TodoDTO> todos = todoRepository.findAll();

        if (todos.size() > 0) {
            return todos;
        } else {
            return new ArrayList<>();
        }
    }

//    @Override
//    public ResponseEntity<?> createTodo(TodoDTO todo) {
//        try {
//            todo.setCreatedAt(new Date(System.currentTimeMillis()));
//            todoRepository.save(todo);
//            return new ResponseEntity<>(todo, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @Override
    public void createTodo(TodoDTO todo) throws ConstraintViolationException, ToDoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepository.findByTodo(todo.getTodo());
        if (todoOptional.isPresent()) {
            throw new ToDoCollectionException(ToDoCollectionException.TodoAlreadyExists());
        } else {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todo);
        }
    }

//    @Override
//    public ResponseEntity<?> findTodoById(String id) {
//        Optional<TodoDTO> todo = todoRepository.findById(id);
//
//        if (todo.isPresent()) {
//            return new ResponseEntity<>(todo.get(), HttpStatus.OK);
//        } else {
//            String emptyMessage = String.format("todo with specific id = %s was not found", id);
//            return new ResponseEntity<>(emptyMessage, HttpStatus.NOT_FOUND);
//        }
//    }

    @Override
    public TodoDTO getSingleTodo(String id) throws ToDoCollectionException {
        Optional<TodoDTO> todo = todoRepository.findById(id);

        if (todo.isPresent()) {
            return todo.get();
        } else {
            throw new ToDoCollectionException(ToDoCollectionException.NotFoundException(id));
        }
    }

//    @Override
//    public ResponseEntity<?> updateById(String id, TodoDTO todoDTO) {
//        Optional<TodoDTO> todo = todoRepository.findById(id);
//
//        if (todo.isPresent()) {
//            TodoDTO todoToSave = todo.get();
//
//            todoToSave.setCompleted(todoDTO.getCompleted() != null ? todoDTO.getCompleted() : todoToSave.getCompleted());
//            todoToSave.setDescription(todoDTO.getDescription() != null ? todoDTO.getDescription() : todoToSave.getDescription());
//            todoToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
//            todoRepository.save(todoToSave);
//
//            return new ResponseEntity<>(todoToSave, HttpStatus.OK);
//        } else {
//            String emptyMessage = String.format("todo with specific id = %s was not found", id);
//            return new ResponseEntity<>(emptyMessage, HttpStatus.NOT_FOUND);
//        }
//    }

    @Override
    public void updateById(String id, TodoDTO todoDTO) throws ConstraintViolationException, ToDoCollectionException {
        Optional<TodoDTO> todo = todoRepository.findById(id);
        Optional<TodoDTO> todoWithSameName = todoRepository.findByTodo(todoDTO.getTodo());

        if (todo.isPresent()) {

            if (todoWithSameName.isPresent() && !todoWithSameName.get().getId().equals(id)) {
                throw new ToDoCollectionException(ToDoCollectionException.TodoAlreadyExists());
            }

            TodoDTO todoToSave = todo.get();

            todoToSave.setTodo(todoDTO.getTodo() != null ? todoDTO.getTodo() : todoToSave.getTodo());
            todoToSave.setCompleted(todoDTO.getCompleted() != null ? todoDTO.getCompleted() : todoToSave.getCompleted());
            todoToSave.setDescription(todoDTO.getDescription() != null ? todoDTO.getDescription() : todoToSave.getDescription());
            todoToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todoToSave);
        } else {
            throw new ToDoCollectionException(ToDoCollectionException.NotFoundException(id));
        }
    }

//    @Override
//    public ResponseEntity<?> deleteById(String id) {
//        Optional<TodoDTO> todo = todoRepository.findById(id);
//
//        if (todo.isPresent()) {
//            TodoDTO todoToDelete = todo.get();
//            todoRepository.delete(todoToDelete);
//            String successDeletedMessage = String.format("todo with specific id = %s was successfully deleted", id);
//            return new ResponseEntity<>(successDeletedMessage, HttpStatus.OK);
//        } else {
//            String emptyMessage = String.format("todo with specific id = %s was not found", id);
//            return new ResponseEntity<>(emptyMessage, HttpStatus.NOT_FOUND);
//        }
//    }

    @Override
    public void deleteById(String id) throws ToDoCollectionException {
        Optional<TodoDTO> todo = todoRepository.findById(id);

        if (todo.isPresent()) {
            TodoDTO todoToDelete = todo.get();
            todoRepository.delete(todoToDelete);
        } else {
            throw new ToDoCollectionException(ToDoCollectionException.NotFoundException(id));
        }
    }

//    @Override
//    public ResponseEntity<?> deleteById(String id) {
//        try {
//            todoRepository.deleteById(id);
//            String successMessage = String.format("todo with specific id = %s was successfully deleted", id);
//            return new ResponseEntity<>(successMessage, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
//        }
//    }

}
