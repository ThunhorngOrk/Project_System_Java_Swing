package library_management_system.service;

import library_management_system.dto.LoginRequest;
import library_management_system.dto.RegisterRequest;
import library_management_system.entity.User;
import library_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String login(LoginRequest request) {
        return userRepository.findByUsernameAndPassword(request.getUsername(), request.getPassword())
                .map(User::getRole)
                .orElse(null);
    }

    public boolean register(RegisterRequest request) {
        if (userRepository.existsByUsernameOrEmail(request.getUsername(), request.getEmail())) {
            return false;
        }
        User user = new User(request.getUsername(), request.getEmail(),
                request.getPassword(), request.getRole());
        userRepository.save(user);
        return true;
    }
}
