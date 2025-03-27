package nbc.sma.dto.mapper;

import nbc.sma.dto.request.RegisterRequest;
import nbc.sma.dto.response.UserResponse;
import nbc.sma.dto.response.UsersResponse;
import nbc.sma.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    public User toEntity(RegisterRequest req) {
        return User.builder()
                .name(req.name())
                .email(req.email())
                .password(req.password())
                .build();
    }

    public UsersResponse toResponse(List<User> users) {
        return new UsersResponse(
                users.stream()
                        .map(this::toResponse)
                        .toList()
        );
    }
}
