package com.example.security.Controller;

import com.example.security.Api.ApiResponse;
import com.example.security.Model.Todo;
import com.example.security.Model.User;
import com.example.security.Service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/get-all")
    public ResponseEntity getAllTodos(){
        return ResponseEntity.status(200).body(todoService.getAllTodos());
    }


    @GetMapping("/get-my-todos")
    public ResponseEntity getMyTodos(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(todoService.getMyTodos(user.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity addTodos(@AuthenticationPrincipal User user, @RequestBody @Valid Todo todo){
        return ResponseEntity.status(200).body(todoService.getMyTodos(user.getId()));
    }
}
