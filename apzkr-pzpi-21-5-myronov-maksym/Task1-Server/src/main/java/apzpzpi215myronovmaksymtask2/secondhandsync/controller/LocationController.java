package apzpzpi215myronovmaksymtask2.secondhandsync.controller;

import apzpzpi215myronovmaksymtask2.secondhandsync.exception.DatabaseException;
import apzpzpi215myronovmaksymtask2.secondhandsync.exception.LocationException;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.dto.LocationDTO;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.dto.LocationsDTO;
import apzpzpi215myronovmaksymtask2.secondhandsync.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location-manager")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/setLocation")
    public ResponseEntity<?> addLocation(@RequestBody LocationDTO locationDTO) throws DatabaseException {
        locationService.addLocation(locationDTO);
        return ResponseEntity.ok("Location added successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateLocation")
    public ResponseEntity<?> updateLocation(@RequestBody LocationDTO locationRequest) throws LocationException, DatabaseException {
        locationService.updateLocation(locationRequest);
        return ResponseEntity.ok("Location updated successfully");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getLocations")
    public ResponseEntity<LocationsDTO> getAllLocations() throws LocationException {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteLocation/{locationId}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long locationId) throws LocationException {
        locationService.deleteLocation(locationId);
        return ResponseEntity.ok("Location deleted successfully");
    }
}
