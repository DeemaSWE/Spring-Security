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

    public void addTodos(Todo todo, Integer userId){
        User user = authRepository.findUserById(userId);
        
    }
}
