package com.example.security.Service;

import com.example.security.Api.ApiException;
import com.example.security.Model.Todo;
import com.example.security.Model.User;
import com.example.security.Repository.AuthRepository;
import com.example.security.Repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final AuthRepository authRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public List<Todo> getMyTodos(Integer userId){
        User user = authRepository.findUserById(userId);

        // مايحتاج اشيك لان اليوزر اكيد موجود لانه سوا لوق ان
        //        if(user == null)
        //        throw new ApiException("User not found");

        return todoRepository.findAllByUser(user);
    }

    public void addTodos(Integer userId, Todo todo){
        User user = authRepository.findUserById(userId);

        todo.setUser(user);

        todoRepository.save(todo);
    }

    public void updateTodo(Integer userId, Integer todoId, Todo updatedTodo){
        Todo todo = todoRepository.findTodoById(todoId);

        if(todo == null)
            throw new ApiException("Todo not found");

        if(!todo.getUser().getId().equals(userId))
            throw new ApiException("Unauthorized to update this todo");

        todo.setTitle(updatedTodo.getTitle());

        todoRepository.save(todo);
    }

    public void deleteTodo(Integer userId, Integer todoId){
        Todo todo = todoRepository.findTodoById(todoId);

        if(todo == null)
            throw new ApiException("Todo not found");

        if(!todo.getUser().getId().equals(userId))
            throw new ApiException("Unauthorized to delete this todo");

        todoRepository.delete(todo);
    }
}
