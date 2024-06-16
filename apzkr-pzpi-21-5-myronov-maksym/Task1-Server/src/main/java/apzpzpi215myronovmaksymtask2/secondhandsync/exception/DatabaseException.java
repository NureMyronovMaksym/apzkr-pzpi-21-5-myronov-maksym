package apzpzpi215myronovmaksymtask2.secondhandsync.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

public class DatabaseException extends Exception {

    private final DatabaseException.DatabaseExceptionProfile databaseExceptionProfile;

    public DatabaseException(DatabaseException.DatabaseExceptionProfile databaseExceptionProfile) {
        super(databaseExceptionProfile.exceptionMessage);
        this.databaseExceptionProfile = databaseExceptionProfile;
    }

    public String getName() {
        return databaseExceptionProfile.exceptionName;
    }

    public HttpStatus getResponseStatus() {
        return databaseExceptionProfile.responseStatus;
    }

    @AllArgsConstructor
    public enum DatabaseExceptionProfile {

        DATABASE_ERROR("database_error",
                "An error occurred while accessing the database.", HttpStatus.INTERNAL_SERVER_ERROR),

        INVALID_DATA("invalid_data",
                "Provided data is invalid.", HttpStatus.BAD_REQUEST);

        private final String exceptionName;
        private final String exceptionMessage;
        private final HttpStatus responseStatus;
    }
}
