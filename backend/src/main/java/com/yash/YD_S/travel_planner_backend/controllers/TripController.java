package com.yash.YD_S.travel_planner_backend.controllers;

import com.yash.YD_S.travel_planner_backend.dto.CreateTrip;
import com.yash.YD_S.travel_planner_backend.model.Trip;
import com.yash.YD_S.travel_planner_backend.model.User;
import com.yash.YD_S.travel_planner_backend.repository.UserRepository;
import com.yash.YD_S.travel_planner_backend.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;


@RestController
@RequestMapping("/api/trip")
@RequiredArgsConstructor
@Tag(name = "Trips", description = "Endpoints for getting trip details, creating trips, and managing trip-related operations")
public class TripController {
    private final TripService tripService;
    private final UserRepository userRepository;

    @Operation(summary = "Create a new trip",
               description = "Creates a new trip with the provided details.",
               responses = {
                   @ApiResponse(responseCode = "201", description = "Trip created successfully",
                                content = @Content(schema = @Schema(implementation = Trip.class))),
                   @ApiResponse(responseCode = "400", description = "Invalid input data",
                                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
               })
    @PostMapping("/create")
    public ResponseEntity<Trip> createTrip(@RequestBody CreateTrip createTrip) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Trip createdTrip = tripService.createTrip(createTrip, user);
        return new ResponseEntity<>(createdTrip, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a trip",
               description = "Deletes a trip and the destinations in it by its ID if the user has permission.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Trip deleted successfully"),
                   @ApiResponse(responseCode = "404", description = "Trip not found or user does not have permission",
                                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
               })
    @DeleteMapping("/delete/{tripId}")
    public ResponseEntity<String> deleteTrip(@PathVariable Long tripId) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            tripService.deleteTrip(user, tripId);
        } catch (Exception e) {
            return new ResponseEntity<>("Trip not found or you do not have permission to delete it", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Trip deleted successfully", HttpStatus.OK);
    }

    @Operation(summary = "Get trip details if the user has permission",
               description = "Retrieves the details of a trip by its ID.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Trip details retrieved successfully",
                                content = @Content(schema = @Schema(implementation = Trip.class))),
                   @ApiResponse(responseCode = "404", description = "Trip not found",
                                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
               })
    @GetMapping("/{tripId}")
    public ResponseEntity<?> getTripDetails(@PathVariable Long tripId) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!tripService.getTripById(tripId).getUser().equals(user)) {
                return new ResponseEntity<>("You do not have permission to view this trip", HttpStatus.FORBIDDEN);
            }
            Trip trip = tripService.getTripById(tripId);
            return new ResponseEntity<>(trip, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}
