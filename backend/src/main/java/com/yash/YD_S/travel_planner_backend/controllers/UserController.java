package com.yash.YD_S.travel_planner_backend.controllers;

import com.yash.YD_S.travel_planner_backend.dto.UserDTO;
import com.yash.YD_S.travel_planner_backend.model.User;
import com.yash.YD_S.travel_planner_backend.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.yash.YD_S.travel_planner_backend.mapper.UserMapper.toDTO;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "User", description = "Endpoints for user management and profile operations")
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Operation(
            summary = "Get user profile",
            description = "Retrieve the profile of the currently authenticated user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User profile retrieved successfully",
                            content = @Content(schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getProfile() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        UserDTO userProfile = toDTO(user);
        return ResponseEntity.ok(userProfile);
    }

    @Operation(
            summary = "Delete a user",
            description = "Delete a user account by the username if the user has permission.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User deleted successfully",
                            content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
                    @ApiResponse(responseCode = "404", description = "User not found or user does not have permission")
            }
    )
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> delete(@PathVariable long userId) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();

            User AuthUser = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (AuthUser.getUsername().equals(user.getUsername())) {
                userRepository.delete(user);
                return ResponseEntity.ok().body("User deleted successfully");
            }
            else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not have permission to delete this account");
            }
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateProfile(@RequestBody Map<String, Object> updates) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (updates.containsKey("username")) {
            user.setUsername((String) updates.get("username"));
        }
        if (updates.containsKey("password")) {
            String newPassword = (String) updates.get("password");
            user.setPassword(passwordEncoder.encode(newPassword));
        }
        if (updates.containsKey("email")) {
            user.setEmail((String) updates.get("email"));
        }

        userRepository.save(user);
        return ResponseEntity.ok(toDTO(user));
    }
}
