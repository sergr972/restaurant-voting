package ru.sergr972.restaurantvoting.common.error;

import static ru.sergr972.restaurantvoting.common.error.ErrorType.DATA_CONFLICT;

public class DataConflictException extends AppException {
    public DataConflictException(String msg) {
        super(msg, DATA_CONFLICT);
    }
}