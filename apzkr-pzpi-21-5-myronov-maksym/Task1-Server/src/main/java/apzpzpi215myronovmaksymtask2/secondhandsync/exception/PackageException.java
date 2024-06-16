package apzpzpi215myronovmaksymtask2.secondhandsync.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

public class PackageException extends Exception {

    private final PackageException.PackageExceptionProfile packageExceptionProfile;

    public PackageException(PackageException.PackageExceptionProfile packageExceptionProfile) {
        super(packageExceptionProfile.exceptionMessage);
        this.packageExceptionProfile = packageExceptionProfile;
    }

    public String getName() {
        return packageExceptionProfile.exceptionName;
    }

    public HttpStatus getResponseStatus() {
        return packageExceptionProfile.responseStatus;
    }


    @AllArgsConstructor
    public enum PackageExceptionProfile {

        PACKAGE_NOT_FOUND("package_not_found",
                "Package with such id is not found", HttpStatus.BAD_REQUEST),

        PACKAGES_NOT_FOUND("packages_not_found",
                "Packages were not found in the database", HttpStatus.BAD_REQUEST);

        private final String exceptionName;
        private final String exceptionMessage;
        private final HttpStatus responseStatus;
    }
}
