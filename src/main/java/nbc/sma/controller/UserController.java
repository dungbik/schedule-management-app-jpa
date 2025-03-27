package nbc.sma.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nbc.sma.dto.request.LoginRequest;
import nbc.sma.dto.request.RegisterRequest;
import nbc.sma.dto.request.UpdateUserRequest;
import nbc.sma.dto.response.UserResponse;
import nbc.sma.dto.response.UsersResponse;
import nbc.sma.security.SessionConst;
import nbc.sma.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest req
    ) {
        UserResponse res = userService.register(req);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> signIn(
            @Valid @RequestBody LoginRequest req,
            HttpSession session
    ) {
        UserResponse user = userService.login(req);
        session.setAttribute("userId", user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UsersResponse> findUsers() {
        UsersResponse res = userService.findUsers();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findUser(
            @PathVariable Long id
    ) {
        UserResponse res = userService.findUser(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(
            @Valid @RequestBody UpdateUserRequest req,
            @SessionAttribute(name = SessionConst.LOGIN_USER) Long userId
    ) {
        userService.update(userId, req);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(
            @SessionAttribute(name = SessionConst.LOGIN_USER) Long userId
    ) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
