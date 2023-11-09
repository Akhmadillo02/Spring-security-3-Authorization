package uz.ahmadillo02.springsecurity3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ahmadillo02.springsecurity3.domein.User;
import uz.ahmadillo02.springsecurity3.service.UserService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity create(@RequestBody User user) {
        User result = userService.save(user);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/users")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(userService.findAll());
    }
}
