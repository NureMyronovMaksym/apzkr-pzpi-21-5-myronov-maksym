package apzpzpi215myronovmaksymtask2.secondhandsync.service;

import apzpzpi215myronovmaksymtask2.secondhandsync.exception.DatabaseException;
import apzpzpi215myronovmaksymtask2.secondhandsync.exception.LocationException;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.dto.LocationDTO;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.dto.LocationsDTO;
import apzpzpi215myronovmaksymtask2.secondhandsync.model.entity.Location;
import apzpzpi215myronovmaksymtask2.secondhandsync.repository.LocationRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void addLocation(LocationDTO locationDTO) throws DatabaseException {
        validateLocationDTO(locationDTO);
        Location location = convertDTOToEntity(locationDTO);

        try {
            locationRepository.save(location);
        } catch (DataAccessException e) {
            throw new DatabaseException(DatabaseException.DatabaseExceptionProfile.DATABASE_ERROR);
        }
    }

    public void updateLocation(LocationDTO locationDTO) throws DatabaseException, LocationException {
        Long updateId = locationDTO.getId();
        if (updateId == null) {
            throw new DatabaseException(DatabaseException.DatabaseExceptionProfile.DATABASE_ERROR);
        }

        Location existingLocation;
        try {
            existingLocation = locationRepository.findById(updateId).orElseThrow(
                    () -> new LocationException(LocationException.LocationExceptionProfile.LOCATION_NOT_FOUND));
        } catch (DataAccessException e) {
            throw new DatabaseException(DatabaseException.DatabaseExceptionProfile.DATABASE_ERROR);
        }

        updateEntityFromDTO(existingLocation, locationDTO);
        try {
            locationRepository.save(existingLocation);
        } catch (DataAccessException e) {
            throw new DatabaseException(DatabaseException.DatabaseExceptionProfile.DATABASE_ERROR);
        }
    }

    public LocationsDTO getAllLocations() throws LocationException {
        List<Location> locations;
        try {
            locations = locationRepository.findAll();
        } catch (Exception e) {
           throw new LocationException(LocationException.LocationExceptionProfile.LOCATIONS_NOT_FOUND);
        }
        List<LocationDTO> locationDTOs = new ArrayList<>();
        for(Location location: locations) {
            locationDTOs.add(convertEntityToDTO(location));
        }

        LocationsDTO locationsDTO = new LocationsDTO();
        locationsDTO.setLocationsDTO(locationDTOs);
        return locationsDTO;
    }

    public void deleteLocation(Long locationId) throws LocationException {
        Location location;
        try {
            location = locationRepository.findById(locationId).get();
        } catch (Exception e) {
            throw new LocationException(LocationException.LocationExceptionProfile.LOCATION_NOT_FOUND);
        }

        locationRepository.delete(location);
    }

    public Location convertDTOToEntity(LocationDTO locationDTO) {
        Location location = new Location();
        location.setId(locationDTO.getId());
        location.setPlace(locationDTO.getPlace());
        location.setTimeArrival(locationDTO.getTimeArrival());
        location.setTimeDeparture(locationDTO.getTimeDeparture());

        return location;
    }

    private LocationDTO convertEntityToDTO(Location location) {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setId(location.getId());
        locationDTO.setPlace(location.getPlace());
        locationDTO.setTimeArrival(new Timestamp(location.getTimeArrival().getTime()));
        locationDTO.setTimeDeparture(new Timestamp(location.getTimeDeparture().getTime()));

        return locationDTO;
    }

    private void validateLocationDTO(LocationDTO locationDTO) throws DatabaseException {
        if (locationDTO.getPlace() == null || locationDTO.getTimeArrival() == null || locationDTO.getTimeDeparture() == null) {
            throw new DatabaseException(DatabaseException.DatabaseExceptionProfile.INVALID_DATA);
        }
    }

    private void updateEntityFromDTO(Location location, LocationDTO locationDTO) {
        location.setPlace(locationDTO.getPlace());
        location.setTimeArrival(locationDTO.getTimeArrival());
        location.setTimeDeparture(locationDTO.getTimeDeparture());
    }
}