package apzpzpi215myronovmaksymtask2.secondhandsync.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

public class ItemException extends Exception {

    private final ItemExceptionProfile itemExceptionProfile;

    public ItemException(ItemExceptionProfile itemExceptionProfile) {
        super(itemExceptionProfile.exceptionMessage);
        this.itemExceptionProfile = itemExceptionProfile;
    }

    public String getName() {
        return itemExceptionProfile.exceptionName;
    }

    public HttpStatus getResponseStatus() {
        return itemExceptionProfile.responseStatus;
    }

    @AllArgsConstructor
    public enum ItemExceptionProfile {

        ITEM_NOT_FOUND("item_not_found",
                "Item with such id is not found", HttpStatus.BAD_REQUEST),

        ITEMS_NOT_FOUND("items_not_found",
                               "Items were not found in the database", HttpStatus.BAD_REQUEST);

        private final String exceptionName;
        private final String exceptionMessage;
        private final HttpStatus responseStatus;
    }
}
