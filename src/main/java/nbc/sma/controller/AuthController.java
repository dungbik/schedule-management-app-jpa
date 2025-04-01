package nbc.sma.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nbc.sma.dto.request.LoginRequest;
import nbc.sma.dto.response.UserResponse;
import nbc.sma.security.SessionConst;
import nbc.sma.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(
            @Valid @RequestBody LoginRequest req,
            HttpSession session
    ) {
        UserResponse user = userService.login(req);
        session.setAttribute(SessionConst.LOGIN_USER, user.id());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
