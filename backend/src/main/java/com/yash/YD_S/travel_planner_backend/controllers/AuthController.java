package com.yash.YD_S.travel_planner_backend.controllers;

import com.yash.YD_S.travel_planner_backend.dto.AuthResponse;
import com.yash.YD_S.travel_planner_backend.dto.LoginRequest;
import com.yash.YD_S.travel_planner_backend.dto.RegisterRequest;
import com.yash.YD_S.travel_planner_backend.model.User;
import com.yash.YD_S.travel_planner_backend.repository.UserRepository;
import com.yash.YD_S.travel_planner_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.concurrent.RecursiveTask;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user registration and login")
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account and returns a JWT token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully",
                            content = @Content(schema = @Schema(implementation = AuthResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            if (response != null)
                return ResponseEntity.ok(response);
            else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Operation(
            summary = "Login an existing user",
            description = "Authenticates a user and returns a JWT token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful",
                            content = @Content(schema = @Schema(implementation = AuthResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            if (response != null) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
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
    public ResponseEntity<?> delete(@PathVariable long userId) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();

            User AuthUser = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (AuthUser.getUsername().equals(username)) {
                userRepository.delete(user);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
