package nbc.sma.service;

import lombok.RequiredArgsConstructor;
import nbc.sma.dto.mapper.UserMapper;
import nbc.sma.dto.request.CreateUserRequest;
import nbc.sma.dto.request.UpdateUserRequest;
import nbc.sma.dto.response.UserResponse;
import nbc.sma.dto.response.UsersResponse;
import nbc.sma.entity.User;
import nbc.sma.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse createUser(CreateUserRequest req) {
        User user = userMapper.toEntity(req);
        userRepository.save(user);

        return userMapper.toResponse(user);
    }


    public UsersResponse findUsers() {
        List<User> users = userRepository.findAll();

        return userMapper.toResponse(users);
    }

    public UserResponse findUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toResponse(user);
    }

    @Transactional
    public void update(Long id, UpdateUserRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.update(req.name(), req.email());
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
