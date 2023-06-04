package peaksoft.springrestproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.springrestproject.dto.ChangeRoleRequest;
import peaksoft.springrestproject.dto.TeacherResponse;
import peaksoft.springrestproject.dto.UserRequest;
import peaksoft.springrestproject.dto.UserResponse;
import peaksoft.springrestproject.entity.Role;
import peaksoft.springrestproject.entity.User;
import peaksoft.springrestproject.repository.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserResponse create(UserRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRoleName()));
        user.setLocalDate(LocalDate.now());
        userRepository.save(user);
        return mapToResponse(user);
    }

    public UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .roleName(user.getRole().name())
                .localDate(user.getLocalDate()).build();

    }

    public List<UserResponse> getAll() {
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            userResponses.add(mapToResponse(user));
        }
        return userResponses;
    }

    public UserResponse getUserById(Long userId) {
        User user = userRepository.findById(userId).get();
        return mapToResponse(user);
    }

    public UserResponse update(Long userId, UserRequest request) {
        User user = userRepository.findById(userId).get();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.valueOf(request.getRoleName()));
        userRepository.save(user);
        return mapToResponse(user);
    }

    public String delete(Long userId) {
        userRepository.deleteById(userId);
        return "Successfully deleted user with id: " + userId;
    }
    public UserResponse changeRole(Long userId, ChangeRoleRequest request) {
        User user = userRepository.findById(userId).get();
        user.setRole(Role.valueOf(request.getRolName()));
        userRepository.save(user);
        return mapToResponse(user);
    }
}
