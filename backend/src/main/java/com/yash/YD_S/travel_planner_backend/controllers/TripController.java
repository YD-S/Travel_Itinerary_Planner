package com.yash.YD_S.travel_planner_backend.controllers;

import com.yash.YD_S.travel_planner_backend.dto.TripDTO;
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

import java.util.List;

import static com.yash.YD_S.travel_planner_backend.mapper.TripMapper.toDTO;


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
                                content = @Content(schema = @Schema(implementation = String.class))),
               })
    @PostMapping("/create")
    public ResponseEntity<?> createTrip(@RequestBody TripDTO createTrip) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Trip createdTrip = tripService.createTrip(createTrip, user);
            return new ResponseEntity<>(toDTO(createdTrip), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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

            Trip trip = tripService.getTripById(tripId);
            if (!trip.getUser().equals(user)) {
                return new ResponseEntity<>("You do not have permission to view this trip", HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(toDTO(trip), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{tripId}")
    @Operation(summary = "Update a trip",
               description = "Updates the details of an existing trip by its ID.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Trip updated successfully",
                                content = @Content(schema = @Schema(implementation = Trip.class))),
                   @ApiResponse(responseCode = "404", description = "Trip not found or user does not have permission",
                                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
               })
    public ResponseEntity<TripDTO> updateTrip(@PathVariable Long tripId, @RequestBody TripDTO UpdateTrip) {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Trip updatedTrip = tripService.updateTrip(tripId, UpdateTrip, user);
            return new ResponseEntity<>(toDTO(updatedTrip), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    @Operation(summary = "Get all trips for the authenticated user",
               description = "Retrieves all trips associated with the authenticated user.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "Trips retrieved successfully",
                                content = @Content(schema = @Schema(implementation = TripDTO.class))),
                   @ApiResponse(responseCode = "404", description = "User not found",
                                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
               })
    public ResponseEntity<?> getAllTrips() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : principal.toString();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            List<TripDTO> trips = tripService.getAllTrips(user);
            return ResponseEntity.ok(trips);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
