package library_management_system.controller;

import library_management_system.dto.ApiResponse;
import library_management_system.dto.LoginRequest;
import library_management_system.dto.RegisterRequest;
import library_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String role = userService.login(request);
        if (role != null) {
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Login successful",
                    "role", role
            ));
        }
        return ResponseEntity.ok(Map.of(
                "success", false,
                "message", "Invalid username or password"
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        boolean success = userService.register(request);
        if (success) {
            return ResponseEntity.ok(ApiResponse.success("Registration successful"));
        }
        return ResponseEntity.ok(ApiResponse.error("Username or email already taken"));
    }
}
