package apzpzpi215myronovmaksymtask2.secondhandsync.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

public class LocationException extends Exception {

    private final LocationException.LocationExceptionProfile locationExceptionProfile;

    public LocationException(LocationException.LocationExceptionProfile locationExceptionProfile) {
        super(locationExceptionProfile.exceptionMessage);
        this.locationExceptionProfile = locationExceptionProfile;
    }

    public String getName() {
        return locationExceptionProfile.exceptionName;
    }

    public HttpStatus getResponseStatus() {
        return locationExceptionProfile.responseStatus;
    }

    @AllArgsConstructor
    public enum LocationExceptionProfile {

        LOCATION_NOT_FOUND("location_not_found",
                "Location with such id is not found", HttpStatus.BAD_REQUEST),

        LOCATIONS_NOT_FOUND("locations_not_found",
                                   "Locations were not found in the database", HttpStatus.BAD_REQUEST);

        private final String exceptionName;
        private final String exceptionMessage;
        private final HttpStatus responseStatus;
    }
}
